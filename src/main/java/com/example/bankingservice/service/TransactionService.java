package com.example.bankingservice.service;

import com.example.bankingservice.api.request.TransactionDepositRequest;
import com.example.bankingservice.api.request.TransactionTransferRequest;
import com.example.bankingservice.api.request.TransactionWithdrawRequest;
import com.example.bankingservice.api.response.TransactionResponse;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionService {
    TransactionResponse deposit(@NonNull TransactionDepositRequest request);
    TransactionResponse withdraw(@NonNull TransactionWithdrawRequest request);
    TransactionResponse transfer(@NonNull TransactionTransferRequest request);
    Page<TransactionResponse> getAllTransactionsByAccountId(@NonNull UUID accountId, Pageable pageable);
}
