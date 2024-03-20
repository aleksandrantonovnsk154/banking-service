package com.example.bankingservice.mapper.entity;

import com.example.bankingservice.api.request.TransactionDepositRequest;
import com.example.bankingservice.api.request.TransactionTransferRequest;
import com.example.bankingservice.api.request.TransactionWithdrawRequest;
import com.example.bankingservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionEntityMapper {
    Transaction toEntityFromDepositRequest(TransactionDepositRequest request);
    Transaction toEntityFromWithdrawRequest(TransactionWithdrawRequest request);
    Transaction toEntityFromTransferRequest(TransactionTransferRequest request);
}
