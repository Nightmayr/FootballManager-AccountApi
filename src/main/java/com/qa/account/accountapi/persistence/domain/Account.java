package com.qa.account.accountapi.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
	@Id
	@GeneratedValue
	private Long accountId;
	private String email;
	private String fullName;
	private Boolean playing;

	public Account() {
		// Empty constructor
	}

	public Account(Long accountId, String fullName, String email, Boolean playing) {
		this.accountId = accountId;
		this.fullName = fullName;
		this.email = email;
		this.playing = playing;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(Boolean playing) {
		this.playing = playing;
	}
	
	@Override
	public String toString() {
		return this.accountId + this.email + this.fullName;
	}
}