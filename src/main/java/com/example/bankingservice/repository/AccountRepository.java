package com.example.bankingservice.repository;

import com.example.bankingservice.entity.Account;
//import com.example.bankingservice.domain.view.AccountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findById(@Param("id") UUID id);

    Page<Account> findAllBy(Pageable pageable);
}