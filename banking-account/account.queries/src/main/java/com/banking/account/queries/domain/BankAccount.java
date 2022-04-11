package com.banking.account.queries.domain;

import com.banking.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BankAccount extends BaseEntity {

    private String accountNumber;
    private String accountName;
    private String accountType;
    private String accountCurrency;
    private String accountStatus;
    private String accountBalance;
    private String accountBranch;
    private String accountBranchCode;
    private String accountBranchAddress;
    private String accountBranchCity;
    private String accountBranchState;
    private String accountBranchCountry;
    private String accountBranchZipCode;
    private String accountBranchPhone;
    private String accountBranchFax;
    private String accountBranchEmail;
    private String accountBranchContactPerson;
    private String accountBranchContactPersonPhone;
    private String accountBranchContactPersonEmail;
    private String accountBranchContactPersonFax;
    private String accountBranchContactPersonMobile;
    private String accountBranchContactPersonDesignation;
    private String accountBranchContactPersonDepartment;
    private String accountBranchContactPersonCompany;
    private String accountBranchContactPersonWebsite;
    private String accountBranchContactPersonAddress;
    private String accountBranchContactPersonCity;
    private String accountBranchContactPersonState;
    private String accountBranchContactPersonCountry;
    private String accountBranchContactPersonZipCode;
    private String accountBranchContactPersonContactPerson;
    private String accountBranchContactPersonContactPersonPhone;
    private String accountBranchContactPersonContactPersonEmail;
    private String accountBranchContactPersonContactPersonFax;
    private String accountBranchContactPersonContactPersonMobile;
    private String accountBranchContactPersonContactPersonDesignation;
    private String accountBranchContactPersonContactPersonDepartment;
    private String accountBranchContactPersonContactPersonCompany;
    private String accountBranchContactPersonContactPersonWebsite;
    private String accountBranchContactPersonContactPersonAddress;
    private String accountBranchContactPersonContactPersonCity;
    private String accountBranchContactPersonContactPersonState;
    private String accountBranchContactPersonContactPersonCountry;

}
