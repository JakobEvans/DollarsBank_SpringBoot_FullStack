package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.repository.CustomerRepository;

@Service
public class CustomerService {

//	HashMap<Integer, Customer> allLoggedInCustomer = new HashMap<>();

	@Autowired
	CustomerRepository customerRepository;

	public Customer createCustomer(Customer customer) {
		customer.setId(-1);
		Customer created = customerRepository.save(customer);
		return created;
	}

	public List<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}

	public List<Customer> getOtherCustomers(int id) throws ResourceNotFoundException {
//		Optional<Customer> found = repository.findById(id);
		
		Optional<Customer> found = customerRepository.findById(id);
		
		if (found.isPresent()) {
			List<Customer> allOtherCustomers = customerRepository.findAll();
			
			
			for(int i = 0; i < allOtherCustomers.size()-1; i++) {
				if(allOtherCustomers.get(i).getId() == id) {
					allOtherCustomers.remove(i);
					
				}
				
			}
//			if(allOtherCustomers.contains(found)) {
//				System.out.println("\n\nCONTAINS CONTAINS CONTAINS\n\n");
//				allOtherCustomers.remove(found);
//
//			}

			return allOtherCustomers;
		}
		return null;

	}
	
	
	
	/**
	 * Gets the Customer ID from an account using its username. 
	 * Returns -1 if no customer is found.
	 * @param username String - The Customer's unique username.
	 * @return int - The Customer ID, or -1 if none is found.
	 */
	public int getCustomerIdByUsername(String username) {
		Customer found = customerRepository.findByUsername(username);
		// if no customer is found, return -1
		if(found == null) {
			return -1;
		}
		return found.getId();
	}


	public Customer findCustomerById(int id) throws ResourceNotFoundException {
		Optional<Customer> found = customerRepository.findById(id);

		if(found.isPresent()) {
			return found.get();
		}
		return null;
	}
	
	
	public String customerDataById(int id) throws ResourceNotFoundException {
		Optional<Customer> found = customerRepository.findById(id);

//		if(found.isEmpty()) {
//			throw new ResourceNotFoundException("Customer with id " + id + "  not found.");
//		}
		if(!found.isPresent()) {
			// Customer ID invalid
			return null;
		}
		Customer customer = found.get();

		

		return customer.customerData();
	}

	


}


