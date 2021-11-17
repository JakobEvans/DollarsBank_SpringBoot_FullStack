package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.model.TransactionRequest;
import com.cognixia.jump.model.TransactionTransferRequest;
import com.cognixia.jump.service.TransactionService;
import com.cognixia.jump.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;

	@Autowired
	private JwtUtil jwtUtil;

	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/transaction")
	public ResponseEntity<List<?>> getAllTransactions() {
		return new ResponseEntity<>(transactionService.findAllTransactions(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/transaction/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable int id) throws ResourceNotFoundException {
		return new ResponseEntity<>(transactionService.findTransactionById(id),HttpStatus.OK);
	}


	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/transaction/deposit")
	public ResponseEntity<?> makeDeposit(@RequestBody TransactionRequest request, 
								@RequestHeader (name = "Authorization") String token) {

		// extract the Customer ID from the token
		int customerId = getCustomerIdFromToken(token);
		// if -1 is returned, then no Customer with that username exists
		if(customerId == -1) {
			return ResponseEntity.status(404).body("Error in Deposit: Customer with this username not found.");
		}

		Transaction result = transactionService.makeDeposit(request.getAmount(), customerId);
		// if the result is null, return an error
		if(result == null) {
			return ResponseEntity.status(404).body("Error in Deposit: transaction failed.");
		}
		// success
		return ResponseEntity.status(201).body(result);		
		
	}

	/**
	 * Gets a Customer ID by their username. Returns the ID or -1 if 
	 * no Customer is found.
	 * @param token String - The user's JSON Web Token, sent in the Authorization header.
	 * @return int - The Customer's ID, or -1 if no Customer with that username is found.
	 */
	private int getCustomerIdFromToken(String token) {
		// remove the 'Bearer ' prefix
		token = token.split(" ")[1];
		String username = jwtUtil.extractUsername(token);
		return transactionService.getCustomerIdByUsername(username);
	}
	

	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/transaction/withdraw")
	public ResponseEntity<?> makeWithdrawl(@RequestBody TransactionRequest request, 
							@RequestHeader (name = "Authorization") String token) {

		// extract the Customer ID from the token
		int customerId = getCustomerIdFromToken(token);
		// if -1 is returned, then no Customer with that username exists
		if(customerId == -1) {
			return ResponseEntity.status(404).body("Error in Withdraw: Customer with this username not found.");
		}

		Transaction result = transactionService.makeWithdrawal(request.getAmount(), customerId);
		// if the result is null, return an error
		if(result == null) {
			return ResponseEntity.status(404).body("Error in Withdraw: Insufficient funds.");
		}
		// success
		return ResponseEntity.status(201).body(result);		
	}
	
	
	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/transaction/history")
	public ResponseEntity<?> last5Transactions(
							@RequestHeader (name = "Authorization") String token) {

		// extract the Customer ID from the token
		int customerId = getCustomerIdFromToken(token);
		// if -1 is returned, then no Customer with that username exists
		if(customerId == -1) {
			return ResponseEntity.status(404).body("Error in Withdraw: Customer with this username not found.");
		}
		
		return new ResponseEntity<>(transactionService.viewLastFiveTransactions(customerId),HttpStatus.OK);
	}
	
	@CrossOrigin(origins= "http://localhost:3000")
	@PostMapping("/transaction/transfer")
	public ResponseEntity<?> makeTransfer(@RequestBody TransactionTransferRequest request,
											@RequestHeader (name = "Authorization") String token) {
		
		
		// extract the Customer ID from the token
		int customerId = getCustomerIdFromToken(token);
		// if -1 is returned, then no Customer with that username exists
		if(customerId == -1) {
			return ResponseEntity.status(404).body("Error in Withdraw: Customer with this username not found.");
		}
		
		
		
		Transaction result = transactionService.makeTransfer(request.getAmount(), customerId, request.getRecieverId());
		// **SHould do error handling for withdrawing to much when you dont have funds**
		// if the result is null, return an error
		if(result == null) {
			return ResponseEntity.status(404).body("Error: Customer with ID " + request.getCustomerId() + " not found. or invalid funds");
		}
		// success
		return ResponseEntity.status(201).body(result);
		
	}
	
	
	
	
}
