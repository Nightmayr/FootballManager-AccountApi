// package com.qa.account.test;

// import static org.junit.Assert.assertEquals;

// import java.util.ArrayList;
// import java.util.List;

// import org.junit.FixMethodOrder;
// import org.junit.Rule;
// import org.junit.Test;
// import org.junit.rules.ExpectedException;
// import org.junit.runner.RunWith;
// import org.junit.runners.MethodSorters;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.junit4.SpringRunner;

// import com.qa.account.accountapi.persistence.domain.Account;
// import com.qa.account.accountapi.persistence.domain.SentAccount;
// import com.qa.account.accountapi.rest.AccountRest;
// import com.qa.account.accountapi.service.AccountService;
// import com.qa.account.accountapi.util.exceptions.AccountNotFoundException;

// @RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
// public class RepoIntegrationTesting {
	
// 	@Rule
// 	public final ExpectedException exception = ExpectedException.none();
	
// 	SentAccount sentAccount = new SentAccount();

// 	@Autowired
// 	private AccountRest rest;
	
// 	@Autowired
// 	private AccountService service;

// 	private static final Account MOCK_ACCOUNT = new Account("example1@hotmail.com", "Ben", "Taylor", false);
// 	private static final Account MOCK_UPDATED_ACCOUNT = new Account("example2@hotmail.com", "Alvin", "Joseph",true); 
	
// 	@Test
// 	public void aAddAccountTest() {
// 		service.addAccount(MOCK_ACCOUNT);
// 		assertEquals(MOCK_ACCOUNT.toString(), service.getAccount(1L).toString());
// 	}
	
// 	@Test
// 	public void bGetAccountTest() {
// 		assertEquals(MOCK_ACCOUNT.toString(), rest.getAccount(1L).toString());
		
// 		exception.expect(AccountNotFoundException.class);
// 		rest.getAccount(2L);
// 	}
	
// 	@Test
// 	public void cGetAccountsTest() {
// 		List<Account> accounts = new ArrayList<>();
// 		accounts.add(MOCK_ACCOUNT);
		
// 		assertEquals(accounts.toString(), rest.getAccounts().toString());
// 	}
	
// 	@Test
// 	public void dUpdateAccountTest() {
// 		assertEquals(new ResponseEntity<Object>(HttpStatus.OK), rest.updateAccount(MOCK_UPDATED_ACCOUNT, 1L));
// 		assertEquals(new ResponseEntity<Object>(HttpStatus.NOT_FOUND), rest.updateAccount(MOCK_UPDATED_ACCOUNT, 2L));
// 		assertEquals(MOCK_UPDATED_ACCOUNT.toString(), service.getAccount(1L).toString());
// 	}
	
// //	@Test
// //	public void eDeleteAccountTest() {
// //		List<Account> emptyList = new ArrayList<>();
// //		assertEquals(new ResponseEntity<Object>(HttpStatus.OK), rest.deleteAccount(1L));
// //		assertEquals(new ResponseEntity<Object>(HttpStatus.NOT_FOUND), rest.deleteAccount(2L));
// //		assertEquals(emptyList, rest.getAccounts());
// //	}
	
// }
