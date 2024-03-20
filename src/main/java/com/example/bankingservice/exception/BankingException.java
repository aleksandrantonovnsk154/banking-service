package com.example.bankingservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BankingException extends RuntimeException{
    private final HttpStatus httpStatus;

    public BankingException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
