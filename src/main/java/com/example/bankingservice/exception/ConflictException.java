package com.example.bankingservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ConflictException extends BankingException{
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

