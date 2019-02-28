package com.qa.account.test;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.account.accountapi.persistence.domain.Account;
import com.qa.account.accountapi.persistence.domain.SentAccount;
import com.qa.account.accountapi.rest.AccountRest;
import com.qa.account.accountapi.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepoIntegrationTesting {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	SentAccount sentAccount = new SentAccount();
	@Autowired
	private AccountRest rest;
	@Autowired
	private AccountService service;
	private static final Account MOCK_ACCOUNT = new Account("example1@hotmail.com", "Ben", "Taylor", false);
	private static final Account MOCK_UPDATED_ACCOUNT = new Account("example2@hotmail.com", "Alvin", "Joseph", true);

	@Test
	public void aAddAccountTest() {
		service.addAccount(MOCK_ACCOUNT);
		assertEquals(MOCK_ACCOUNT.toString(), service.getAccount(1L).toString());
	}
}
