package com.discovery.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.discovery.dao.Dao;
import com.discovery.domain.User;

@Service
public class UserSecurityService implements UserDetailsService
{
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);
	
	@Autowired
	private Dao dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = dao.findByUsername(username);
		if(user == null)
		{
			LOG.warn("Username {} not found",username);
			throw new UsernameNotFoundException("User: " +username+ " not found!");
		}
		return user;
	}
	
}
