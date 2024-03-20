package com.example.bankingservice.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateAccountRequest(
        @NotBlank(message = "Имя получателя не должно быть пустым")
        @Size(min = 1, max = 100, message = "Длина имени получателя должна быть от 1 до 100 символов")
        String beneficiaryName,
        @NotBlank(message = "PIN-код не должен быть пустым")
        @Size(min = 4, max = 4, message = "Длина PIN-кода должна составлять 4 символа")
        @Pattern(regexp = "\\d+", message = "PIN-код должен состоять только из цифр")
        String pinCode) {

}
