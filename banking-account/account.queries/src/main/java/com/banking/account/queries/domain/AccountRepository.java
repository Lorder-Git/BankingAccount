package com.banking.account.queries.domain;

import com.banking.cqrs.core.domain.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolder(String accountHolder);

    List<BaseEntity> findByBalanceGreaterThan(double balance);

    List<BaseEntity> findByBalanceLessThan(double balance);

    List<BaseEntity> findByBalanceBetween(double min, double max);

    List<BaseEntity> findByBalanceGreaterThanEqual(double balance);

    List<BaseEntity> findByBalanceLessThanEqual(double balance);

}
