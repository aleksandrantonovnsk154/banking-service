package com.example.bankingservice.api.response;

import com.example.bankingservice.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(UUID id, UUID fromAccount, UUID toAccount, BigDecimal amount,
                                  TransactionType transactionType, LocalDateTime created) {
}