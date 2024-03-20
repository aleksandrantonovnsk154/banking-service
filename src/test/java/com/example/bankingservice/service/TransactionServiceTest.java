package com.example.bankingservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bankingservice.api.request.TransactionDepositRequest;
import com.example.bankingservice.api.request.TransactionTransferRequest;
import com.example.bankingservice.api.request.TransactionWithdrawRequest;
import com.example.bankingservice.api.response.TransactionResponse;
import com.example.bankingservice.entity.Account;
import com.example.bankingservice.entity.Transaction;
import com.example.bankingservice.enums.TransactionType;
import com.example.bankingservice.mapper.ResponseMapper;
import com.example.bankingservice.mapper.entity.TransactionEntityMapper;
import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.repository.TransactionRepository;
import com.example.bankingservice.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionEntityMapper transactionEntityMapper;

    @Mock
    private ResponseMapper responseMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private UUID fromAccountId;
    private UUID toAccountId;
    private Account fromAccount;
    private Account toAccount;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        fromAccountId = UUID.randomUUID();
        toAccountId = UUID.randomUUID();

        fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        fromAccount.setBalance(new BigDecimal("100.00"));
        fromAccount.setPinCode(BCrypt.hashpw("0000", BCrypt.gensalt()));

        toAccount = new Account();
        toAccount.setId(toAccountId);
        toAccount.setBalance(new BigDecimal("110.00"));

    }

    @Test
    void whenTransfer_thenSuccess() {
        var request = TransactionTransferRequest.builder()
                .fromAccount(fromAccountId)
                .toAccount(toAccountId)
                .amount(new BigDecimal("20.00"))
                .pinCode("0000")
                .build();

        transaction = new Transaction();
        UUID transactionId = UUID.randomUUID();
        transaction.setId(transactionId);
        transaction.setTransactionType(TransactionType.TRANSFER);

        TransactionResponse expectedResponse = new TransactionResponse(transactionId, fromAccountId, toAccountId, new BigDecimal("20.00"), TransactionType.TRANSFER, LocalDateTime.now());

        when(accountRepository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(toAccountId)).thenReturn(Optional.of(toAccount));
        when(transactionEntityMapper.toEntityFromTransferRequest(request)).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(responseMapper.mapTransaction(transaction)).thenReturn(expectedResponse);

        TransactionResponse actualResponse = transactionService.transfer(request);

        assertThat(actualResponse.id()).isEqualTo(transaction.getId());
        assertThat(actualResponse.fromAccount()).isEqualTo(request.fromAccount());
        assertThat(actualResponse.toAccount()).isEqualTo(request.toAccount());
        assertThat(actualResponse.amount()).isEqualTo(request.amount());
        assertThat(actualResponse.transactionType()).isEqualTo(TransactionType.TRANSFER);
        verify(accountRepository).save(fromAccount);
        verify(accountRepository).save(toAccount);
        verify(transactionRepository).save(transaction);
        verify(responseMapper).mapTransaction(transaction);
        assertThat(fromAccount.getBalance()).isEqualByComparingTo(new BigDecimal("80.00"));
        assertThat(toAccount.getBalance()).isEqualByComparingTo(new BigDecimal("130.00"));
    }

    @Test
    void whenDeposit_thenSuccess() {

        TransactionDepositRequest request = TransactionDepositRequest.builder()
                .toAccount(toAccountId)
                .amount(new BigDecimal("50.00"))
                .build();

        transaction = new Transaction();
        UUID transactionId = UUID.randomUUID();
        transaction.setId(transactionId);
        transaction.setTransactionType(TransactionType.DEPOSIT);

        TransactionResponse expectedResponse = new TransactionResponse(transactionId, null, toAccountId, new BigDecimal("50.00"), TransactionType.DEPOSIT, LocalDateTime.now());

        when(accountRepository.findById(toAccountId)).thenReturn(Optional.of(toAccount));
        when(transactionEntityMapper.toEntityFromDepositRequest(request)).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(responseMapper.mapTransaction(transaction)).thenReturn(expectedResponse);

        TransactionResponse actualResponse = transactionService.deposit(request);

        assertThat(actualResponse.id()).isEqualTo(transaction.getId());
        assertThat(actualResponse.toAccount()).isEqualTo(request.toAccount());
        assertThat(actualResponse.amount()).isEqualTo(request.amount());
        assertThat(actualResponse.transactionType()).isEqualTo(TransactionType.DEPOSIT);
        verify(accountRepository).save(toAccount);
        verify(transactionRepository).save(transaction);
        verify(responseMapper).mapTransaction(transaction);
        assertThat(toAccount.getBalance()).isEqualByComparingTo(new BigDecimal("160.00"));
    }

    @Test
    void whenWithdraw_thenSuccess() {

        TransactionWithdrawRequest request = TransactionWithdrawRequest.builder()
                .fromAccount(fromAccountId)
                .amount(new BigDecimal("30.00"))
                .pinCode("0000")
                .build();

        transaction = new Transaction();
        UUID transactionId = UUID.randomUUID();
        transaction.setId(transactionId);
        transaction.setTransactionType(TransactionType.WITHDRAW);

        TransactionResponse expectedResponse = new TransactionResponse(transactionId, fromAccountId, null, new BigDecimal("30.00"), TransactionType.WITHDRAW, LocalDateTime.now());

        when(accountRepository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(transactionEntityMapper.toEntityFromWithdrawRequest(request)).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(responseMapper.mapTransaction(transaction)).thenReturn(expectedResponse);

        TransactionResponse actualResponse = transactionService.withdraw(request);

        assertThat(actualResponse.id()).isEqualTo(transaction.getId());
        assertThat(actualResponse.fromAccount()).isEqualTo(request.fromAccount());
        assertThat(actualResponse.amount()).isEqualTo(request.amount());
        assertThat(actualResponse.transactionType()).isEqualTo(TransactionType.WITHDRAW);
        verify(transactionRepository).save(transaction);
        verify(responseMapper).mapTransaction(transaction);
        assertThat(fromAccount.getBalance()).isEqualByComparingTo(new BigDecimal("70.00"));
    }
}