package com.qa.account.accountapi.service;

import com.qa.account.accountapi.persistence.repository.AccountRepository;
import com.qa.account.accountapi.util.exceptions.AccountNotFoundException;
import com.qa.account.accountapi.persistence.domain.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repo;

	@Override
	public List<Account> getAccounts() {
		return repo.findAll();
	}

	@Override
	public Account getAccount(Long accountId) {
		Optional<Account> account = repo.findById(accountId);
		return account.orElseThrow(() -> new AccountNotFoundException(accountId.toString()));
	}

	@Override
	public Account addAccount(Account account) {
		return repo.save(account);
	}
	
	@Override
	public ResponseEntity<Object> updateAccount(Account account, Long accountId) {
		if (accountExists(accountId)) {
			account.setAccountId(accountId);
			repo.save(account);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Object> deleteAccount(Long accountId) {
		if (accountExists(accountId)) {
			repo.deleteById(accountId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	private boolean accountExists(Long accountId) {
		Optional<Account> accountOptional = repo.findById(accountId);
		return accountOptional.isPresent();
	}

}