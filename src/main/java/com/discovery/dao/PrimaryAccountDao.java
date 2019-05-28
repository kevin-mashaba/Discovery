package com.discovery.dao;

import org.springframework.data.repository.CrudRepository;

import com.discovery.domain.PrimaryAccount;

public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount, Long>
{

	PrimaryAccount findByAccountNumber (int accountNumber);

	
}
