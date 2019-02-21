package com.qa.account.accountapi.service;

import org.springframework.http.ResponseEntity;

import com.qa.account.accountapi.persistence.domain.Account;

import java.util.List;

public interface AccountService {

	List<Account> getAccounts();

	Account getAccount(Long accountId);

	Account addAccount(Account account);
	
	ResponseEntity<Object> updateAccount(Account account, Long accountId);

	ResponseEntity<Object> deleteAccount(Long accountId);
}