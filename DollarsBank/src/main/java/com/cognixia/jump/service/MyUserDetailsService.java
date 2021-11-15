package com.cognixia.jump.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.MyUserDetails;
import com.cognixia.jump.repository.CustomerRepository;

import java.util.Optional;


// ONLY EXIST FOR LOGIN 
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Optional<Customer> customer = customerRepository.findByUsername(username);

	        customer.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

//	        return user.map(MyUserDetails::new).get();
			return new MyUserDetails( customer.get() );
	}
	
	

  
}