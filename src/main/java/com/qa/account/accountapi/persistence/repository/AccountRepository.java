package com.qa.account.accountapi.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.account.accountapi.persistence.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
