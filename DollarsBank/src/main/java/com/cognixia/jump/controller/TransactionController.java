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

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.service.TransactionService;

public class TransactionController {
	
	@Autowired
	TransactionService service;

	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/Transaction")
	public ResponseEntity<List<Transaction>> getAllTransactions() {
		return new ResponseEntity<>(service.findAllTransactions(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/Transaction/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) throws ResourceNotFoundException {
		return new ResponseEntity<>(service.findTransactionById(id),HttpStatus.OK);
	}
	
	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/Customer")
	public ResponseEntity<Transaction> createCustomer(@RequestBody Transaction transaction){
		return new ResponseEntity<>(service.createTransaction(transaction), HttpStatus.CREATED);

	}
}
