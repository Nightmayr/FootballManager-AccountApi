package com.qa.account.accountapi.util.exceptions;

public class AccountNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -2576769156049948174L;

	public AccountNotFoundException(String exception){
        super("Id supplied does not exist. Id: " + exception);
    }

}
