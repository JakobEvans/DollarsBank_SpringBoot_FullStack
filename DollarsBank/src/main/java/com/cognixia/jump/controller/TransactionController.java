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

	/**
	 * Returns all Transactions in the database.
	 * @return List<Transaction> - A collection of all Transactions in the database.
	 */
	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/transaction")
	public ResponseEntity<List<?>> getAllTransactions() {
		return new ResponseEntity<>(transactionService.findAllTransactions(),HttpStatus.OK);
	}
	
	/**
	 * Returns a Transaction by its unique integer ID. Returns a 404 if no Transaction 
	 * is found.
	 * @param id int - The Transaction's unique integer ID.
	 * @return Transaction - The specified Transaction.
	 * @throws ResourceNotFoundException - Returns a 404 if no Transaction with that ID is found.
	 */
	@CrossOrigin(origins= "http://localhost:3000")
	@GetMapping("/transaction/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable int id) throws ResourceNotFoundException {
		return new ResponseEntity<>(transactionService.findTransactionById(id),HttpStatus.OK);
	}

	/**
	 * Allows a Customer to make a Deposit transaction. The Customer's account ID is extracted from 
	 * their JSON Web Token in the Authorization header. The Transaction requires an amount, which 
	 * cannot be zero.
	 * @param request TransactionRequest - A JSON object containing the amount to deposit, which looks like:
	 * 										{
	 * 											"amount": 50.0
	 * 										}
	 * @param token String - The Customer's JWT, stored in the Authorization header.
	 * @return Transaction - The newly created Transaction, or a 404 error if the operation failed.
	 */
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
	
	/**
	 * Allows a Customer to make a Withdrawl transaction. The Customer's account ID is extracted from 
	 * their JSON Web Token in the Authorization header. The Transaction requires an amount, which 
	 * cannot be zero.
	 * @param request TransactionRequest - A JSON object containing the amount to withdraw, which looks like:
	 * 										{
	 * 											"amount": 50.0
	 * 										}
	 * @param token String - The Customer's JWT, stored in the Authorization header.
	 * @return Transaction - The newly created Transaction, or a 404 error if the operation failed.
	 */
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
	
	/**
	 * Returns a Customer's 5 most recent Transactions, or all Transactions if there are fewer than 5.
	 * @param token String - The Customer's JWT, stored in the Authorization header.
	 * @return List<Transaction> - A collection of the Customer's 5 recent Transactions.
	 */
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
	
	/**
	 * Allows a Customer to transfer funds from their own account to another account.
	 * @param request TransactionTransferRequest - A JSON object specifying the target Customer's ID 
	 * 											and the amount to be transferred. Looks like:
	 * 					{
	 * 						"recieverId": 2,
	 * 						"amount": 50.0
	 * 					}
	 * @param token String - The Customer's JWT, stored in the Authorization header.
	 * @return Transaction - The resulting Transaction, or a 404 error if the source account has 
	 * 						insufficient funds.
	 */
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
			return ResponseEntity.status(404).body("Error: Customer with ID " + request.getRecieverId() + " not found or insufficient funds");
		}
		// success
		return ResponseEntity.status(201).body(result);
		
	}
	
	
	
	
}
