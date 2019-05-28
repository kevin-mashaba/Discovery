package com.discovery.dao;

import org.springframework.data.repository.CrudRepository;

import com.discovery.domain.SavingsAccount;

public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long>
{
	SavingsAccount findByAccountNumber(int accountNumber);
}
