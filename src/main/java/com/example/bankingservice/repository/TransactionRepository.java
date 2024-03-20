package com.example.bankingservice.repository;

import com.example.bankingservice.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findById(@Param("id") UUID id);
    Page<Transaction> findByFromAccountOrToAccount(UUID fromAccount, UUID toAccount, Pageable pageable);
}