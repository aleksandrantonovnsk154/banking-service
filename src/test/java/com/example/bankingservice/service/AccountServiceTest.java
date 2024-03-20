package com.example.bankingservice.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import com.example.bankingservice.api.request.CreateAccountRequest;
import com.example.bankingservice.api.response.AccountResponse;
import com.example.bankingservice.entity.Account;
import com.example.bankingservice.mapper.ResponseMapper;
import com.example.bankingservice.mapper.entity.AccountEntityMapper;
import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountEntityMapper accountEntityMapper;

    @Mock
    private ResponseMapper responseMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void whenCreateAccount_thenAccountIsCreated() {
        CreateAccountRequest request = CreateAccountRequest.builder()
                .beneficiaryName("TestUser1")
                .pinCode("0000")
                .build();
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        UUID accountId = UUID.randomUUID();
        account.setId(accountId);

        AccountResponse expectedResponse = new AccountResponse(accountId, "TestUser1", BigDecimal.ZERO, LocalDateTime.now());

        when(accountEntityMapper.toEntity(request)).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(responseMapper.mapAccount(account)).thenReturn(expectedResponse);

        AccountResponse actualResponse = accountService.create(request);

        assertThat(actualResponse.id()).isEqualTo(account.getId());
        assertThat(actualResponse.beneficiaryName()).isEqualTo(request.beneficiaryName());
        verify(accountRepository).save(account);
        verify(responseMapper).mapAccount(account);
        assertThat(actualResponse.balance()).isEqualByComparingTo(new BigDecimal("0.00"));
    }
}