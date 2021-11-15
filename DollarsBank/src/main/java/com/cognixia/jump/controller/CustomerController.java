package com.cognixia.jump.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.service.CustomerService;
import com.cognixia.jump.service.TransactionService;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerService service;

	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/customer/data/{id}")
	public ResponseEntity<String> getCustomerDataById(@PathVariable int id) throws ResourceNotFoundException {
		return new ResponseEntity<>(service.customerDataById(id), HttpStatus.OK);
	}

	
	

	// gets all the other customers (all other customers except current passed in id)
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/customer/other/{id}")
	public ResponseEntity<List<Customer>> getAllOtherCustomers(@PathVariable int id) throws ResourceNotFoundException {
		return new ResponseEntity<>(service.getOtherCustomers(id), HttpStatus.OK);
	}
	
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<>(service.findAllCustomers(), HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) throws ResourceNotFoundException {
		return new ResponseEntity<>(service.findCustomerById(id), HttpStatus.OK);
	}

	
	
	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/customer")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
		return new ResponseEntity<>(service.createCustomer(customer), HttpStatus.CREATED);

	}

	
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PostMapping("/customer/login")
//	public Status loginCustomer(@RequestBody Customer customer)throws ResourceNotFoundException {
////		return new ResponseEntity<>(service.login(customer), HttpStatus.OK);
//		return service.login(customer);
//
//	
//	}
//	
//	
//	
//	@CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/customer/logout")
//    public Status logoutCustomer(@RequestBody Customer customer) {
//		return new ResponseEntity<>(service.logout(customer), HttpStatus.OK);
////		return service.logout(customer);
//
//    }
	
	
	
//


//	
//	@CrossOrigin(origins= "http://localhost:3000")
//	@DeleteMapping("/customer/{id}")
//	public ResponseEntity<Customer> deleteCustomerById(@PathVariable int id) throws ResourceNotFoundException {
////		return new ResponseEntity<>(service.deleteCustomerById(id),HttpStatus.OK);
//	}
//	
//	

}
