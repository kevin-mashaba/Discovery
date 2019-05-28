package com.discovery.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.discovery.domain.CreditCard;
import com.discovery.domain.PrimaryAccount;
import com.discovery.domain.SavingsAccount;
import com.discovery.domain.User;
import com.discovery.service.AccountService;
import com.discovery.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		model.addAttribute("primaryAccount",primaryAccount);
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount(Model model,Principal princiapl)
	{
		User user = userService.findByUsername(princiapl.getName());
		SavingsAccount savingsAccount = user.getSavingsAccount();
		model.addAttribute("savingsAccount",savingsAccount);
		return "savingsAccount";
	}
	
	@RequestMapping("/creditCard")
	public String creditCard(Model model,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		CreditCard creditCard =user.getCreditCard();
		model.addAttribute("creditCard",creditCard);
		return "creditCard";
	}
	
	@RequestMapping(value="/deposit", method = RequestMethod.GET)
	public String deposit(Model model)
	{
		model.addAttribute("accountType","");
		model.addAttribute("amount", "");
		
		return "deposit";
	}
	
	@RequestMapping(value="/deposit",method=RequestMethod.POST)
	public String depositPOST(@ModelAttribute("amount") String amount,@ModelAttribute("accountType") String accountType,Principal principal)
	{
		try
		{
			accountService.deposit(accountType,Double.parseDouble(amount), principal);
		}
		catch(Exception e)
		{
			System.out.println("Deposit error: " + e.getMessage());
		}
			
		
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public String withdraw(Model model)
	{
		model.addAttribute("accountType","");
		model.addAttribute("amount","");
		return "withdraw";
	}
	
	@RequestMapping(value="/withdraw",method=RequestMethod.POST)
	public String withdrawPOST(@ModelAttribute("amount") String amount,
			@ModelAttribute("accountType") String accountType,Principal principal,Model model)
	{
		User user = userService.findByUsername(principal.getName());
		try
		{
			if(Double.parseDouble(amount) <= 10000)
			{
				if(Double.parseDouble(amount) >user.getPrimaryAccount().getAccountBalance().doubleValue())
				{
					model.addAttribute("funds",true);
					System.out.println("no money!");			
				}
				else if(Double.parseDouble(amount) >user.getSavingsAccount().getAccountBalance().doubleValue())
				{		
					System.out.println("no money in savings!");
					model.addAttribute("funds",true);		
				}
				else if(Double.parseDouble(amount) >user.getCreditCard().getAccountBalance().doubleValue())
				{
					System.out.println("no money in credit!");
					model.addAttribute("funds",true);

				}
				else
				{
					accountService.withdraw(accountType,Double.parseDouble(amount),principal);
					
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Withdraw error: " + e.getMessage());

		}
		
		
		return "redirect:/dashboard";
	}
}
