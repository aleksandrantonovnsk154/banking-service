package com.example.bankingservice.service;

import com.example.bankingservice.api.request.CreateAccountRequest;
import com.example.bankingservice.api.response.AccountResponse;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountResponse create(@NonNull CreateAccountRequest request);
    Page<AccountResponse> getAllAccounts(@NonNull Pageable pageable);
}
