package com.example.bankingservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends BankingException{
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
