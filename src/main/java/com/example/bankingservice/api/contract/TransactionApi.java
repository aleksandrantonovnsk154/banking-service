package com.example.bankingservice.api.contract;

import com.example.bankingservice.api.request.TransactionDepositRequest;
import com.example.bankingservice.api.request.TransactionTransferRequest;
import com.example.bankingservice.api.request.TransactionWithdrawRequest;
import com.example.bankingservice.api.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/transactions")
@Tag(name = "Transaction API", description = "Операции по снятию,внесению, переводу денежных средств / получение истории")
public interface TransactionApi {

    @Operation(summary = "Перевод денег", description = "Перевести деньги на другой счет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Invalid PIN code"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "409", description = "Insufficient funds")
    })
    @PostMapping("/transfer")
    TransactionResponse transfer(@Parameter(description = "Детали операции по переводу денег между счетами")
                                 @RequestBody @Valid TransactionTransferRequest transactionTransferRequest);


    @Operation(summary = "Внесите деньги", description = "Внести деньги на счет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PostMapping("/deposit")
    TransactionResponse deposit(@Parameter(description = "Детали операции по внесению денег на счет")
                                @RequestBody @Valid TransactionDepositRequest transactionDepositRequest);

    @Operation(summary = "Снять деньги", description = "Снять деньги со счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdraw successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Invalid PIN code"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "409", description = "Insufficient funds")
    })
    @PostMapping("/withdraw")
    TransactionResponse withdraw(@Parameter(description = "Детали операции по снятию денег со счета")
                                 @RequestBody @Valid TransactionWithdrawRequest transactionWithdrawRequest);

    @Operation(summary = "Получить все операции по счету", description = "Получить все операции с разбивкой по страницам для данного идентификатора учетной записи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of transactions successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{accountId}")
    Page<TransactionResponse> getAllTransactionsByAccountId(
            @Parameter(description = "ID of the Account to retrieve transactions for")
            @PathVariable UUID accountId,
            @ParameterObject @PageableDefault Pageable pageable);
}
