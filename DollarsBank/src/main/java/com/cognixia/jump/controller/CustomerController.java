package com.cognixia.jump.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.MyUserDetails;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.service.CustomerService;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.TransactionService;
import com.cognixia.jump.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authRequest) throws Exception {
		
		// will catch the exception for bad credentials and...
		try {
			// make sure we can authenticate our user based on the username and password
			authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
					);
		} catch(BadCredentialsException e) {
			
			//...then provide own message as to why user could not be authenticated
			throw new Exception("Incorrect username or password");
		}
		
		// as long as user is found, we can create the JWT
		
		// find the user
		final MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
		//User userFound = service.getUserByUsername(authRequest.getUsername());
		
		// generate token for this user
		final String jwt = jwtUtil.generateTokens(userDetails);
		
		// return token
		return ResponseEntity.status(200).body( jwt );
		
	}

	
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
