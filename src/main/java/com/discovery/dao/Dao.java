package com.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.discovery.domain.User;
@Repository
public interface Dao extends CrudRepository<User, Long> 
{
	User findByUsername(String username);
	User findByEmail(String email);
}
