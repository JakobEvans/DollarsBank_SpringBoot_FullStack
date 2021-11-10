package com.cognixia.jump.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Status;
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

	public Status login(Customer customer)  {
		List<Customer> customers = repository.findAll();
		
        for (Customer other : customers) {
            if (other.equals(customer)) {
                customer.setLoggedIn(true);
                repository.save(customer);
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
//		return customer;
        
	}
	
	public Status logout(Customer customer)  {
		 List<Customer> customers = repository.findAll();
	      for (Customer other : customers) {
	          if (other.equals(customer)) {
	          	customer.setLoggedIn(false);
	              repository.save(customer);
	              return Status.SUCCESS;
	          }
	      }
	      return Status.FAILURE;
//		return customer;
	  }
	
	
	
	
	
	
	public Status createCustomer(Customer newCustomer) {
//	Customer.setId(-1);
//	Customer created = repository.save(Customer);
//	return created;
	
		
	
	List<Customer> customers = repository.findAll();
    System.out.println("New customer: " + newCustomer.toString());
    for (Customer customer : customers) {
        System.out.println("Registered customer: " + newCustomer.toString());
        if (customer.equals(newCustomer)) {
            System.out.println("Customer Already exists!");
            return Status.USER_ALREADY_EXISTS;
//            	return new Customer();
        }
    }

    
	Customer created = repository.save(newCustomer);

    return Status.SUCCESS;
}
	
//	public Customer updateCustomer(int id, Customer Customer) throws ResourceNotFoundException {
//		findCustomerById(id);
//		Customer updated = repository.save(Customer);
//		return updated;
//	}
}
