package com.example.bankingservice.mapper;

import com.example.bankingservice.api.response.AccountResponse;
import com.example.bankingservice.api.response.TransactionResponse;
import com.example.bankingservice.entity.Account;
import com.example.bankingservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResponseMapper {
    TransactionResponse mapTransaction(Transaction source);

    AccountResponse mapAccount(Account source);
}
