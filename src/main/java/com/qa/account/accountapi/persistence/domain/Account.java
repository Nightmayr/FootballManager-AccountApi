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
	}

	public Account(Long accountId, String fullName, String email, Boolean playing) {
		this.fullName = fullName;
		this.email = email;
		this.accountId = accountId;
		this.playing = playing;
	}

	public Long getId() {
		return accountId;
	}

	public void setId(Long id) {
		this.accountId = id;
	}

	public String getFirstName() {
		return fullName;
	}

	public void setFirstName(String firstName) {
		this.fullName = firstName;
	}

	public String toString() {
		return this.accountId + this.email + this.fullName;
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
}
