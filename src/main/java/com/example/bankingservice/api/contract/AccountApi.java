package com.example.bankingservice.api.contract;

import com.example.bankingservice.api.request.CreateAccountRequest;
import com.example.bankingservice.api.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/accounts")
@Tag(name = "Account API", description = "Операции с аккаунтом")
public interface AccountApi {
    @Operation(summary = "Создать новую учетную запись", description = "Создайте новую учетную запись с именем получателя и PIN-кодом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account creation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping()
    AccountResponse create(@Parameter(description = "Запрос на создание учетной записи")
                           @RequestBody @Valid CreateAccountRequest userRequest);

    @Operation(summary = "Получить все учетные записи", description = "Получить все учетные записи с разбивкой по страницам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accounts successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping()
    Page<AccountResponse> getAllAccounts(@ParameterObject @PageableDefault Pageable pageable);
}
