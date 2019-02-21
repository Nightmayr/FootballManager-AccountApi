package com.qa.account.accountapi.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${mongoClient}")
	private String mongoClient;
	
//	@GetMapping("${path.getAccounts}")
//	public List<Account> getAccounts() {
//		return service.getAccounts();
//	}
	
	@GetMapping("${path.getAccounts}")
	public Object getAccounts() {
		Object obj = restTemplate.getForObject(mongoClient, Object.class);
		LinkedHashMap objMap = (LinkedHashMap) obj;
		LinkedHashMap sentAccounts= (LinkedHashMap) objMap.get("_embedded");
		ArrayList<Object> sentAccounts1 = (ArrayList) sentAccounts.get("sentAccount");
		ArrayList<Object> sentAccounts2 = new ArrayList<Object>();
		
		for(int i =0  ; i < sentAccounts1.size() ; i++) {
		LinkedHashMap account = (LinkedHashMap) sentAccounts1.get(i);
		account.remove("_links");
		sentAccounts2.add(account);
		}
		return sentAccounts2;
		
	}

	@GetMapping("${path.getAccountById}")
	public Account getAccount(@PathVariable Long accountId) {
		return service.getAccount(accountId);
	}
	
	@PostMapping("${path.createAccount}")
	public Account createAccount(@RequestBody Account account) {
		sendToQueue(account);
		return service.addAccount(account);
	}
	
	@PutMapping("${path.updateAccount}")
	public ResponseEntity<Object> updateAccount(@RequestBody Account account, @PathVariable Long accountId) {
		return service.updateAccount(account, accountId);
	}

	@DeleteMapping("${path.deleteAccount}")
	public ResponseEntity<Object> deleteAccount(@PathVariable Long accountId) {
		return service.deleteAccount(accountId);
	}

	@PutMapping("${path.changeBoolean}")
	private Account recievingNewBoolean(@RequestBody Account account) {
		Boolean booleanToSend = restTemplate.getForObject(
				playerManagerURL + basePath + playerManagerPath + account.getPlaying(), Boolean.class);
		account.setPlaying(booleanToSend);
		return account;
	}

	private void sendToQueue(Account account) {
		SentAccount accountToStore = new SentAccount(account);
		jmsTemplate.convertAndSend("AccountQueue", accountToStore);
	}

}