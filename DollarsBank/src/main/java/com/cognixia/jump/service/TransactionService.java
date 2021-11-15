package com.cognixia.jump.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.repository.CustomerRepository;
import com.cognixia.jump.repository.TransactionRepository;

@Service
public class TransactionService {
	
	
	@Autowired
	TransactionRepository repository;

	@Autowired
	CustomerRepository customerRepo;

	public List<Transaction> findAllTransactions() {
		return repository.findAll();
	}

	public Transaction findTransactionById(int id) throws ResourceNotFoundException {
		Optional<Transaction> found = repository.findById(id);
		if(found.isPresent()) {
			return found.get();
		}
		return null;
	}
	public Transaction makeWithdrawl(double amount, int customerId) {
		// make sure the Customer exists
		Optional<Customer> customerFound = customerRepo.findById(customerId);
		// if customerFound is null, then the Customer is invalid
		if(!customerFound.isPresent()) {
			// Customer ID invalid
			return null;
		}
		Customer customer = customerFound.get();
		// make sure the amount isn't zero
		if(amount == 0.0) {
			return null;
		}
		// make sure the amount is positive
		if(amount < 0) {
			amount = amount * -1;
		}
		double currentBalance = customer.getCurrentBalance();
		
		
		double balanceAfter = currentBalance - amount;

//		cannot make the transaction
		if(balanceAfter < 0) {
			return null;
		}
		
		Transaction withdrawl = new Transaction(-1, new Date(), amount, currentBalance, balanceAfter, 
								"Withdrawl", customer);
		return repository.save(withdrawl);
	}
	
	//goog\d
	public Transaction makeDeposit(double amount, int customerId) {
		// make sure the Customer exists
		Optional<Customer> customerFound = customerRepo.findById(customerId);
		// if customerFound is null, then the Customer is invalid
		if(!customerFound.isPresent()) {
			// Customer ID invalid
			return null;
		}
		Customer customer = customerFound.get();
		// make sure the amount isn't zero
		if(amount == 0.0) {
			return null;
		}
		// make sure the amount is positive
		if(amount < 0) {
			amount = amount * -1;
		}
		double currentBalance = customer.getCurrentBalance();
		double balanceAfter = currentBalance + amount;
		Transaction deposit = new Transaction(-1, new Date(), amount, currentBalance, balanceAfter, 
								"Deposit", customer);
		// update the Customer with the new balance
		customer.setCurrentBalance(balanceAfter);
		// save the customer
		customerRepo.save(customer);
		// save and return the transaction
		return repository.save(deposit);
	}
	
	
	
	
	

	public Transaction createTransaction(Transaction transaction) {		
		// make sure the Customer exists
		Integer customerId = transaction.getCustomer().getId();
		Optional<Customer> customerFound = customerRepo.findById(customerId);
		// if customerFound is null, then the Customer is invalid
		if(!customerFound.isPresent()) {
			// Customer ID invalid
			return null;
		}
		// otherwise, the Customer is found
		transaction.setId(-1);
		Transaction created = repository.save(transaction);
		
		return created;	
	}

}
