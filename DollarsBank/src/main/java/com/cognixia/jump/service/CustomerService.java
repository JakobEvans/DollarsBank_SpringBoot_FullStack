package com.cognixia.jump.service;

import java.util.HashMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.repository.CustomerRepository;

@Service
public class CustomerService {
	
	
	
	
	HashMap<Integer, Customer> allLoggedInCustomer = new HashMap<>();
	
	
	
	
	@Autowired
	CustomerRepository repository;
	
	
	


	public boolean login(String username, String password)  {
		List<Customer> customers = repository.findAll();
		
		
		Customer customer = repository.findByUsername(username);
		
        return false;
        
        
	}
	
	
	public int logout(Customer customer)  {

		
		
		allLoggedInCustomer.remove(customer);

		return 0;

//		return customer;
	  }
	
	
	
	
	
	public List<Customer> findAllCustomers(){
		return repository.findAll();
	}
	
	public Customer findCustomerById(int id) throws ResourceNotFoundException {
		Optional<Customer> found = repository.findById(id);
		
//		if(found.isEmpty()) {
//			throw new ResourceNotFoundException("Customer with id " + id + "  not found.");
//		}
		
		return found.get();	
	}
	
	

	public Customer updateCustomer(int id, Customer customer) throws ResourceNotFoundException {
		findCustomerById(id);
		Customer updated = repository.save(customer);
		return updated;	
	}


	public Customer depositCustomer(int id, Customer customer)throws ResourceNotFoundException {
		findCustomerById(id);
		Customer updated = repository.save(customer);
		return updated;	
	}
	
	
	public Customer withdrawCustomer(int id, Customer customer)throws ResourceNotFoundException {
		findCustomerById(id);
		Customer updated = repository.save(customer);
		return updated;	
	}
	
	
	
	
	
//	public Customer updateCustomer(int id, Customer Customer) throws ResourceNotFoundException {
//		findCustomerById(id);
//		Customer updated = repository.save(Customer);
//		return updated;
//	}
}
