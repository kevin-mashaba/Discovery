package com.discovery.serviceimpl;

import java.math.BigDecimal;
import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discovery.dao.CreditCardDao;
import com.discovery.dao.PrimaryAccountDao;
import com.discovery.dao.SavingsAccountDao;
import com.discovery.domain.CreditCard;
import com.discovery.domain.PrimaryAccount;
import com.discovery.domain.SavingsAccount;
import com.discovery.domain.User;
import com.discovery.service.AccountService;
import com.discovery.service.UserService;

@Service
public class AccountSerivceImpl implements AccountService{

	private static int nextAccountNumber = 11223145;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	@Autowired
	private CreditCardDao creditCardDao;
	@Autowired
	private UserService userService;
	
	@Override
	public PrimaryAccount createPrimaryAccount() 
	{
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAcountNumber(accountGen());
		
		primaryAccountDao.save(primaryAccount);
		
		return primaryAccountDao.findByAccountNumber(primaryAccount.getAcountNumber());
	}

	@Override
	public SavingsAccount createSavingsAccount() 
	{
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());
		
		savingsAccountDao.save(savingsAccount);
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}
	
	@Override
	public CreditCard creatCreditCard() 
	{
		CreditCard creditCard = new CreditCard();
		creditCard.setAccountBalance(new BigDecimal(0.0));
		creditCard.setAccountNumber(accountGen());
		
		return creditCardDao.save(creditCard);
	}
	
	public void deposit(String accountType,double amount,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		
		if(accountType.equalsIgnoreCase("Primary"))
		{
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			
		}
		else if(accountType.equalsIgnoreCase("Savings"))
		{
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
		}
		else if(accountType.equalsIgnoreCase("CreditCard"))
		{
			CreditCard creditCard = user.getCreditCard();
			creditCard.setAccountBalance(creditCard.getAccountBalance().add(new BigDecimal(amount)));
			creditCardDao.save(creditCard);
		}
	}
	
	public void withdraw(String accountType,double amount,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		
		if(accountType.equalsIgnoreCase("Primary"))
		{
			if(user.getPrimaryAccount().getAccountBalance().compareTo(BigDecimal.valueOf(amount)) <amount)
			{
				
			}
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			
		}
		else if(accountType.equalsIgnoreCase("Savings"))
		{
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
		
		}
		else if(accountType.equalsIgnoreCase("CreditCard"))
		{
			CreditCard creditCard = user.getCreditCard();
			creditCard.setAccountBalance(creditCard.getAccountBalance().subtract(new BigDecimal(amount)));
			creditCardDao.save(creditCard);
		}
	}
	
	private int accountGen()
	{
		return ++nextAccountNumber;
	}

	

}
