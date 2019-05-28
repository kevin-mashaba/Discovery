package com.discovery.service;

import java.security.Principal;

import com.discovery.domain.CreditCard;
import com.discovery.domain.PrimaryAccount;
import com.discovery.domain.SavingsAccount;

public interface AccountService 
{
	PrimaryAccount createPrimaryAccount();
	SavingsAccount createSavingsAccount();
	CreditCard creatCreditCard();
	void deposit(String accountType, double amount,Principal principal);
	void withdraw(String accountType,double amount,Principal principal);
}
