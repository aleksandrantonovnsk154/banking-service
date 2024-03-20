package com.example.bankingservice.mapper.entity;

import com.example.bankingservice.api.request.CreateAccountRequest;
import com.example.bankingservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountEntityMapper {
    Account toEntity(CreateAccountRequest request);
}
