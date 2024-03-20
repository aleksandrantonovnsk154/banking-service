package com.example.bankingservice.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TransactionDepositRequest(
        @NotNull(message = "Номер счета не должен быть пустым")
        UUID toAccount,
        @NotNull(message = "Сумма  не должна быть null")
        @DecimalMin(value = "0.01", message = "Значение суммы должно быть больше 0")
        BigDecimal amount) {

}
