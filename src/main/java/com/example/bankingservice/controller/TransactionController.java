package com.example.bankingservice.controller;

import com.example.bankingservice.api.contract.TransactionApi;
import com.example.bankingservice.api.request.TransactionDepositRequest;
import com.example.bankingservice.api.request.TransactionTransferRequest;
import com.example.bankingservice.api.request.TransactionWithdrawRequest;
import com.example.bankingservice.api.response.TransactionResponse;
import com.example.bankingservice.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {
    private final TransactionService transactionService;

    @Override
    public TransactionResponse transfer(TransactionTransferRequest request) {
        log.info("Получен запрос на перевод денежных средств: {}", request);
        return transactionService.transfer(request);
    }

    @Override
    public TransactionResponse deposit(@RequestBody @Valid TransactionDepositRequest request) {
        log.info("Получен запрос на внесение денег на счет: {}", request);
        return transactionService.deposit(request);
    }

    @Override
    public TransactionResponse withdraw(TransactionWithdrawRequest request) {
        log.info("Получен запрос на снятие денег со счета: {}", request);
        return transactionService.withdraw(request);
    }

    @Override
    public Page<TransactionResponse> getAllTransactionsByAccountId(UUID accountId, Pageable pageable) {
        log.info("Выборка транзакций по идентификатору учетной записи: {}", accountId);
        return transactionService.getAllTransactionsByAccountId(accountId, pageable);
    }
}
