package com.discovery.dao;

import org.springframework.data.repository.CrudRepository;

import com.discovery.domain.CreditCard;

public interface CreditCardDao extends CrudRepository<CreditCard, Long>
{
	CreditCard findByAccountNumber(int accountNumber);
}
