package com.example.bankingservice.controller;

import com.example.bankingservice.api.contract.AccountApi;
import com.example.bankingservice.api.request.CreateAccountRequest;
import com.example.bankingservice.api.response.AccountResponse;
import com.example.bankingservice.service.AccountService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {
    private final AccountService accountService;

    @Override
    public AccountResponse create(@RequestBody @Valid CreateAccountRequest request) {
        log.info("Получен запрос на создание учетной записи: {}", request);
        return accountService.create(request);
    }

    @Override
    public Page<AccountResponse> getAllAccounts(Pageable pageable) {
        return accountService.getAllAccounts(pageable);
    }
}
