package com.banking.account.queries.domain;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BankAccount extends BaseEntity {

    private String accountId;
    private Date accountCreatedDate;
    private String accountHolder;
    private String accountNumber;
    private String accountName;
    private AccountType accountType;
    private String accountCurrency;
    private String accountStatus;
    private Double accountBalance;

}
