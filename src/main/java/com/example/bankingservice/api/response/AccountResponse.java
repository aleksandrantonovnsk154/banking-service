package com.example.bankingservice.api.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(UUID id, String beneficiaryName, BigDecimal balance, LocalDateTime created) {
}
