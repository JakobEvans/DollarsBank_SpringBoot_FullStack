package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.model.TransactionRequest;
import com.cognixia.jump.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {
	
	@Autowired
	TransactionService service;

	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/transaction")
	public ResponseEntity<List<?>> getAllTransactions() {
		return new ResponseEntity<>(service.findAllTransactions(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/transaction/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable int id) throws ResourceNotFoundException {
		return new ResponseEntity<>(service.findTransactionById(id),HttpStatus.OK);
	}
	
//	@CrossOrigin(origins= "http://localhost:3000")
//	@PostMapping("/customer")
//	public ResponseEntity<?> createCustomer(@RequestBody Transaction transaction){
//		return new ResponseEntity<>(service.createTransaction(transaction), HttpStatus.CREATED);
//
//	}
	
	
	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/transaction")
	public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction){
		return new ResponseEntity<>(service.createTransaction(transaction), HttpStatus.CREATED);

	}

	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/transaction/deposit")
	public ResponseEntity<?> makeDeposit(@RequestBody TransactionRequest request) {
		Transaction result = service.makeDeposit(request.getAmount(), request.getCustomerId());
		// if the result is null, return an error
		if(result == null) {
			return ResponseEntity.status(404).body("Error: Customer with ID " + request.getCustomerId() + " not found.");
		}
		// success
		return ResponseEntity.status(201).body(result);		
		
		
		
		
		
	}
	

	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/transaction/withdraw")
	public ResponseEntity<?> makeWithdrawl(@RequestBody TransactionRequest request) {
		Transaction result = service.makeWithdrawal(request.getAmount(), request.getCustomerId());
		// if the result is null, return an error
		if(result == null) {
			return ResponseEntity.status(404).body("Error: Customer with ID " + request.getCustomerId() + " not found.");
		}
		// success
		return ResponseEntity.status(201).body(result);		
		
		
		
		
		
	}
	
	
	
	
}
