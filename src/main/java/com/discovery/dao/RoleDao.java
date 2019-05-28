package com.discovery.dao;

import org.springframework.data.repository.CrudRepository;

import com.discovery.security.Role;

public interface RoleDao extends  CrudRepository<Role, Integer>
{
	Role findByName(String name);
	
}
