package com.example.bankingservice.service.impl;

import com.example.bankingservice.api.request.CreateAccountRequest;
import com.example.bankingservice.api.response.AccountResponse;
import com.example.bankingservice.exception.NotFoundException;
import com.example.bankingservice.mapper.ResponseMapper;
import com.example.bankingservice.mapper.entity.AccountEntityMapper;
import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountEntityMapper accountMapper;
    private final ResponseMapper responseMapper;

    @Override
    @Transactional
    public AccountResponse create(@NonNull CreateAccountRequest request) {
        final var account = accountMapper.toEntity(request);
        account.setPinCode(cryptPinCode(request.pinCode()));
        account.setBalance(BigDecimal.ZERO);
        repository.save(account);
        return responseMapper.mapAccount(repository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Аккаунт %s не найден.", account.getId()))));
    }

    @Override
    public Page<AccountResponse> getAllAccounts(@NonNull Pageable pageable) {
        return repository.findAllBy(pageable).map(responseMapper::mapAccount);
    }

    private String cryptPinCode(@NonNull String pinCode) {
        return BCrypt.hashpw(pinCode, BCrypt.gensalt());
    }
}
