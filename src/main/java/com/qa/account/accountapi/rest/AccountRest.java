package com.qa.account.accountapi.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qa.account.accountapi.persistence.domain.Account;
import com.qa.account.accountapi.persistence.domain.SentAccount;
import com.qa.account.accountapi.service.AccountService;

@CrossOrigin
@RequestMapping("${path.base}")
@RestController
public class AccountRest {

	@Autowired
	private AccountService service;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${path.base}")
	private String basePath;
 
	@Value("${url.playerManager}")
	private String playerManagerURL;

	@Value("${path.playerManager}")
	private String playerManagerPath;

	@Value("${mongoBase}")
	private String mongoBase;

	@Value("${mongoGetAll}")
	private String mongoGetAll;

	@Value("${mongoGetById}")
	private String mongoGetById;
	
	@Value("${mongoUpdate}")
	private String mongoUpdate;

	@Value("${mongoDelete}")
	private String mongoDelete;
	
	@SuppressWarnings("unchecked")
	@GetMapping("${path.getAccounts}")
	public List<Account> getAccounts() {
		return restTemplate.getForObject(mongoBase + mongoGetAll, List.class);
	}

	@GetMapping("${path.getAccountById}")
	public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
		return restTemplate.getForEntity(mongoBase + mongoGetById + accountId, Account.class);
	}
	
	@PostMapping("${path.createAccount}")
	public Account createAccount(@RequestBody Account account) {
		sendToQueue(account);
		return service.addAccount(account);
	}
	
	@PutMapping("${path.updateAccount}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long accountId) {
		HttpEntity<Account> entity = new HttpEntity<Account>(account);
		return restTemplate.exchange(mongoBase + mongoUpdate + account.getAccountId(), HttpMethod.PUT, entity, Account.class);
	}

	@DeleteMapping("${path.deleteAccount}")
	public void deleteAccount(@PathVariable Long accountId) {
		restTemplate.delete(mongoBase + mongoDelete + accountId);
	}

	@PutMapping("${path.changeBoolean}")
	private Account recievingNewBoolean(@RequestBody Account account) {
		Boolean booleanToSend = restTemplate.getForObject(
				playerManagerURL + basePath + playerManagerPath + account.getPlaying(), Boolean.class);
		account.setPlaying(booleanToSend);
		updateAccount(account, account.getAccountId());
		return account;
	}

	private void sendToQueue(Account account) {
		SentAccount accountToStore = new SentAccount(account);
		jmsTemplate.convertAndSend("AccountQueue", accountToStore);
	}

}