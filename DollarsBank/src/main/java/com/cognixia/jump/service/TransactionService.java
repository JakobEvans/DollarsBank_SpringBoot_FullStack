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
	TransactionRepository transactionRepo;

	@Autowired
	CustomerRepository customerRepo;

	public List<Transaction> findAllTransactions() {
		return transactionRepo.findAll();
	}

	public Transaction findTransactionById(int id) throws ResourceNotFoundException {
		Optional<Transaction> found = transactionRepo.findById(id);
		if (found.isPresent()) {
			return found.get();
		}
		return null;
	}

	public Transaction makeWithdrawal(double amount, int customerId) {

		// make sure the Customer exists
		Optional<Customer> customerFound = customerRepo.findById(customerId);
		// if customerFound is null, then the Customer is invalid
		if (!customerFound.isPresent()) {
			// Customer ID invalid
			return null;
		}
		Customer customer = customerFound.get();
		// make sure the amount isn't zero
		if (amount == 0.0) {
			return null;
		}
		// make sure the amount is positive
		if (amount < 0) {
			amount = amount * -1;
		}
		double currentBalance = customer.getCurrentBalance();
		
//		cannot make the transaction if it overdraws you
		if (currentBalance < amount) {
			return null;
		}

		double balanceAfter = currentBalance - amount;

		Transaction withdrawal = new Transaction(-1, new Date(), amount, currentBalance, balanceAfter, "Withdraw",
				customer);
		// update the Customer with the new balance
		customer.setCurrentBalance(balanceAfter);
		// save the customer
		customerRepo.save(customer);
		// save and return the transaction
		return transactionRepo.save(withdrawal);
	}

	// goog\d
	public Transaction makeDeposit(double amount, int customerId) {
		// make sure the Customer exists
		Optional<Customer> customerFound = customerRepo.findById(customerId);
		// if customerFound is null, then the Customer is invalid
		if (!customerFound.isPresent()) {
			// Customer ID invalid
			return null;
		}
		Customer customer = customerFound.get();
		// make sure the amount isn't zero
		if (amount == 0.0) {
			return null;
		}
		// make sure the amount is positive
		if (amount < 0) {
			amount = amount * -1;
		}
		double currentBalance = customer.getCurrentBalance();
		double balanceAfter = currentBalance + amount;
		Transaction deposit = new Transaction(-1, new Date(), amount, currentBalance, balanceAfter, "Deposit",
				customer);
		// update the Customer with the new balance
		customer.setCurrentBalance(balanceAfter);
		// save the customer
		customerRepo.save(customer);
		// save and return the transaction
		return transactionRepo.save(deposit);
	}
	
	
	public Transaction makeTransfer(double amount, int customerId, int otherCustomerId) {
		// make sure the Customer exists
		Optional<Customer> customerFound = customerRepo.findById(customerId);
		
		Optional<Customer> otherCustomerFound = customerRepo.findById(otherCustomerId);

		
		// if customerFound is null, then the Customer is invalid
		if (!customerFound.isPresent() || !otherCustomerFound.isPresent()) {
			// Customer ID invalid
			return null;
		}
		
		// The customer whos sending the money transfer
		Customer customer_sender = customerFound.get();
		
		// The customer whos recieving the money transfer
		Customer customer_reciever = otherCustomerFound.get();

		// make sure the amount isn't zero
		if (amount == 0.0) {
			return null;
		}
		// make sure the amount is positive
		if (amount < 0) {
			amount = amount * -1;
		}
		
		
		
		// ************ SENDING side of transfer ************

		
		double currentBalance_sender = customer_sender.getCurrentBalance();
		
//		cannot make the transaction if it overdraws you
		if (currentBalance_sender < amount) {
			return null;
		}
		
		
		// must take the amount out of their account
		double balanceAfter_sender = currentBalance_sender - amount;

	
		
		Transaction transferSend = new Transaction(-1, new Date(), amount, currentBalance_sender, balanceAfter_sender, "TransferSender",
				customer_sender);
		
		
		
		// update the Customer with the new balance
		customer_sender.setCurrentBalance(balanceAfter_sender);
		
		
		// ************ Receiving side of transfer ************
		
		double currentBalance_reciever = customer_reciever.getCurrentBalance();
		
		
		double balanceAfter_reciever = currentBalance_reciever + amount;
		
		
		Transaction transferRecieve = new Transaction(-1, new Date(), amount, currentBalance_reciever, balanceAfter_reciever, "TransferReciever",
				customer_reciever);
		
		customer_reciever.setCurrentBalance(balanceAfter_reciever);
		
		// save the customer
		customerRepo.save(customer_sender);
		customerRepo.save(customer_reciever);
		transactionRepo.save(transferRecieve);
		// save and return the transaction
		return transactionRepo.save(transferSend);
		
	}

	
	
	
	
public List<Transaction> viewLastFiveTransactions(int customerId) {
		
		List<Transaction> lastFiveTransactions = null;
		return lastFiveTransactions;
//		
//		
//		CustomerRepository.get
//		
////		if (transactions.size() > 5) {
////			lastFiveTransactions = transactions.subList(transactions.size() - 5, transactions.size() - 1);
////			
////		};
////		
////		return lastFiveTransactions;
	
			
}
	
	
	public Transaction createTransaction(Transaction transaction) {
		// make sure the Customer exists
		Integer customerId = transaction.getCustomer().getId();
		Optional<Customer> customerFound = customerRepo.findById(customerId);
		// if customerFound is null, then the Customer is invalid
		if (!customerFound.isPresent()) {
			// Customer ID invalid
			return null;
		}
		// otherwise, the Customer is found
		transaction.setId(-1);
		Transaction created = transactionRepo.save(transaction);

		return created;
	}

	/**
	 * Gets the Customer ID from an account using its username. 
	 * Returns -1 if no customer is found.
	 * @param username String - The Customer's unique username.
	 * @return int - The Customer ID, or -1 if none is found.
	 */
	public int getCustomerIdByUsername(String username) {
		Customer found = customerRepo.findByUsername(username);
		// if no customer is found, return -1
		if(found == null) {
			return -1;
		}
		return found.getId();
	}

}
