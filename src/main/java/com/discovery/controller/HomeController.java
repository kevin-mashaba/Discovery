package com.discovery.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.discovery.dao.RoleDao;
import com.discovery.domain.CreditCard;
import com.discovery.domain.PrimaryAccount;
import com.discovery.domain.SavingsAccount;
import com.discovery.domain.User;
import com.discovery.security.UserRole;
import com.discovery.service.UserService;

@Controller
public class HomeController 
{
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserService userService; 
	
	@RequestMapping(value="/")
	public String home()
	{
		return  "redirect:/index";
	}
	
	@RequestMapping(value="/index")
	public String index()
	{
		return "index";
	}
	
	@RequestMapping(value="/register",method =RequestMethod.GET)
	public String signUp(Model model)
	{
		User user = new User();
		model.addAttribute("user", user);
		
		return "register";
	}
	
	@RequestMapping(value= "/register",method=RequestMethod.POST)
	public String signUpPost(@ModelAttribute("user") User user, Model model)
	{
		try
		{
			
			if(userService.checkUserExists(user.getUsername(), user.getEmail()))
			{
				if(userService.checkEmailExists(user.getEmail()))
				{
					model.addAttribute("emailExists",true);
				}
			
				if(userService.checkUsernameExists(user.getUsername()))
				{
					model.addAttribute("usernameExists",true);
				}
				
				return "register";
			}
			else
			{
			//	Criteria c = getSession()
				
			
					Set<UserRole> userRoles = new HashSet<>();
					
					userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
					userService.createUser(user,userRoles);
					System.out.println("Do we get in here?");
					
//				userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
//				userService.createUser(user,userRoles);
				
			}
		}
		catch(Exception e)
		{
			System.out.println("Register error: " + e.getMessage());

		}
		return "redirect:/";
		
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Principal principal, Model model)
	{
		BigDecimal usdPrimary;
		BigDecimal audPrimary;
		BigDecimal usdSavings;
		BigDecimal audSavings;
		BigDecimal usdCredit;
		BigDecimal audCredit;
		
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		CreditCard creditCard = user.getCreditCard();
		
		model.addAttribute("names", user.getFirstName() + " " + user.getLastName());
		
		usdPrimary = primaryAccount.getAccountBalance().divide(new BigDecimal(11.6167),2,RoundingMode.HALF_UP) ;
		audPrimary = primaryAccount.getAccountBalance().divide(new BigDecimal(8.818342),2,RoundingMode.HALF_UP) ;
		
		usdSavings = savingsAccount.getAccountBalance().divide(new BigDecimal(11.6167),2,RoundingMode.HALF_UP) ;
		audSavings = savingsAccount.getAccountBalance().divide(new BigDecimal(8.818342),2,RoundingMode.HALF_UP) ;
		
		usdCredit = creditCard.getAccountBalance().divide(new BigDecimal(11.6167),2,RoundingMode.HALF_UP) ;
		audCredit = creditCard.getAccountBalance().divide(new BigDecimal(8.818342),2,RoundingMode.HALF_UP) ;

		model.addAttribute("usd",usdPrimary.setScale(2, RoundingMode.CEILING));
		model.addAttribute("aud",audPrimary.setScale(2, RoundingMode.CEILING));
		
		model.addAttribute("usdSavings",usdSavings.setScale(2, RoundingMode.CEILING));
		model.addAttribute("audSavings",audSavings.setScale(2, RoundingMode.CEILING));
		
		model.addAttribute("usdCredit",usdCredit.setScale(2, RoundingMode.CEILING));
		model.addAttribute("audCredit",audCredit.setScale(2, RoundingMode.CEILING));
		
		model.addAttribute("primaryAccount",primaryAccount);
		model.addAttribute("savingsAccount",savingsAccount);
		model.addAttribute("creditCard",creditCard);
		//System.out.println("account number " + usd);
		return "dashboard";
	}
}
