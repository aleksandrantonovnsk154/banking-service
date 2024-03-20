package com.example.bankingservice.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TransactionWithdrawRequest(
        @NotNull(message = "Номер счета не должен быть пустым")
        UUID fromAccount,
        @NotNull(message = "Сумма не должна быть null")
        @DecimalMin(value = "0.01", message = "Значение суммы должно быть больше 0")
        BigDecimal amount,
        @NotBlank(message = "PIN-код не должен быть пустым")
        @Size(min = 4, max = 4, message = "Длина PIN-кода должна составлять 4 символа")
        @Pattern(regexp = "\\d+", message = "PIN-код должен состоять только из цифр")
        String pinCode) {

}

