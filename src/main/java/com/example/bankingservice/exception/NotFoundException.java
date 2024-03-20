package com.example.bankingservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class NotFoundException extends BankingException{
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
