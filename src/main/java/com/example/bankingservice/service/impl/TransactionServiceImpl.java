package com.example.bankingservice.service.impl;

import com.example.bankingservice.api.request.TransactionDepositRequest;
import com.example.bankingservice.api.request.TransactionTransferRequest;
import com.example.bankingservice.api.request.TransactionWithdrawRequest;
import com.example.bankingservice.api.response.TransactionResponse;
import com.example.bankingservice.entity.Account;
import com.example.bankingservice.enums.TransactionType;
import com.example.bankingservice.exception.ConflictException;
import com.example.bankingservice.exception.ForbiddenException;
import com.example.bankingservice.exception.NotFoundException;
import com.example.bankingservice.mapper.ResponseMapper;
import com.example.bankingservice.mapper.entity.TransactionEntityMapper;
import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.repository.TransactionRepository;
import com.example.bankingservice.service.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionEntityMapper transactionEntityMapper;
    private final ResponseMapper responseMapper;

    @Override
    @Transactional
    public TransactionResponse deposit(@NonNull TransactionDepositRequest request) {
        final var toAccount = accountRepository.findById(request.toAccount())
                .orElseThrow(() -> new NotFoundException("Аккаунт не найден: " + request.toAccount()));

        toAccount.setBalance(toAccount.getBalance().add(request.amount()));
        accountRepository.save(toAccount);

        final var transaction = transactionEntityMapper.toEntityFromDepositRequest(request);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transactionRepository.save(transaction);

        return responseMapper.mapTransaction(transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Транзакция %s не найдена", transaction.getId()))));
    }

    @Override
    @Transactional
    public TransactionResponse withdraw(@NonNull TransactionWithdrawRequest request) {
        final var fromAccount = accountRepository.findById(request.fromAccount())
                .orElseThrow(() -> new NotFoundException("Аккаунт не найден: " + request.fromAccount()));

        validatePinCode(request.pinCode(), fromAccount.getPinCode());
        checkSufficientFunds(fromAccount, request.amount());

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.amount()));

        final var transaction = transactionEntityMapper.toEntityFromWithdrawRequest(request);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transactionRepository.save(transaction);

        return responseMapper.mapTransaction(transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Транзакция %s не найдена", transaction.getId()))));
    }

    @Override
    @Transactional
    public TransactionResponse transfer(@NonNull TransactionTransferRequest request) {

        final var fromAccount = accountRepository.findById(request.fromAccount())
                .orElseThrow(() -> new NotFoundException("Аккаунт не найден: " + request.fromAccount()));

        validatePinCode(request.pinCode(), fromAccount.getPinCode());
        checkSufficientFunds(fromAccount, request.amount());

        final var toAccount = accountRepository.findById(request.toAccount())
                .orElseThrow(() -> new NotFoundException("Аккаунт не найден: " + request.toAccount()));

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.amount()));
        toAccount.setBalance(toAccount.getBalance().add(request.amount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        final var transaction = transactionEntityMapper.toEntityFromTransferRequest(request);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transactionRepository.save(transaction);


        return responseMapper.mapTransaction(transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Транзакция %s не найдена", transaction.getId()))));
    }

    @Override
    public Page<TransactionResponse> getAllTransactionsByAccountId(UUID accountId, Pageable pageable) {
        return transactionRepository.findByFromAccountOrToAccount(accountId, accountId, pageable)
                .map(responseMapper::mapTransaction);
    }

    private void validatePinCode(String providedPin, String storedHash) {
        if (!BCrypt.checkpw(providedPin, storedHash)) {
            throw new ForbiddenException("Неверный PIN-код");
        }
    }

    private void checkSufficientFunds(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new ConflictException("Недостаточно средств");
        }
    }
}

