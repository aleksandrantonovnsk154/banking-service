package com.example.bankingservice.entity;

import com.example.bankingservice.entity.base.AuditingBaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
@AttributeOverride(name = "id", column = @Column(name = "account_number"))
public class Account extends AuditingBaseEntity {

    @Column(name = "beneficiary_name", nullable = false)
    private String beneficiaryName;
    @Column(name = "pin_code", nullable = false)
    private String pinCode;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
