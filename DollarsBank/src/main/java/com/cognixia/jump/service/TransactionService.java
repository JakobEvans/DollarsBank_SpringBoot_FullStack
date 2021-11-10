package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Transaction;
import com.cognixia.jump.repository.TransactionRepository;

public class TransactionService {
	
	TransactionRepository repository;

	public List<Transaction> findAllTransactions() {
		return repository.findAll();
	}

	public Object findTransactionById(int id) throws ResourceNotFoundException {
		Optional<Transaction> found = repository.findById(id);
		return found;
	}

	public Object createTransaction(Transaction transaction) {
		transaction.setId(-1);
		Transaction created = repository.save(transaction);
		return created;
	}

}
