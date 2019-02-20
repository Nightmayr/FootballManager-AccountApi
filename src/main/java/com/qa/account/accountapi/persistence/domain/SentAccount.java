package com.qa.account.accountapi.persistence.domain;

public class SentAccount {

	private Long accountId;

	private String fullName;

	private String email;
	private Boolean playing;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public SentAccount() {
	}

	public SentAccount(Account account) {
		this.accountId = account.getAccountId();
		this.setFullName(account.getFullName());

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

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

}
