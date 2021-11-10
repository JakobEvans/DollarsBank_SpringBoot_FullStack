package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository repository;
	
	public List<Customer> findAllCustomers(){
		return repository.findAll();
	}
	
	public Customer findCustomerById(int id) throws ResourceNotFoundException {
		Optional<Customer> found = repository.findById(id);
//		
//		if(found.isEmpty()) {
//			throw new ResourceNotFoundException("Customer with id " + id + "  not found.");
//		}
		
		return found.get();	
	}
	
	
	
	
	public Customer createCustomer(Customer Customer) {
	Customer.setId(-1);
	Customer created = repository.save(Customer);
	return created;
}
//	
//	public Customer deleteCustomerById(int id) throws ResourceNotFoundException {
//		Customer deleted = findCustomerById(id);
//		repository.deleteById(id);
//		return deleted;
//	}
//	
	public Customer updateCustomer(int id, Customer customer) throws ResourceNotFoundException {
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
