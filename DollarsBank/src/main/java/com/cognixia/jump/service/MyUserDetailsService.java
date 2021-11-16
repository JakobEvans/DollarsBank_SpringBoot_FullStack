package com.cognixia.jump.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.MyUserDetails;
import com.cognixia.jump.repository.CustomerRepository;


// ONLY EXIST FOR LOGIN 
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

	@Override
	public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByUsername(username);
		// turn it into a MyUserDetails
		return new MyUserDetails( customer );
	}  
}