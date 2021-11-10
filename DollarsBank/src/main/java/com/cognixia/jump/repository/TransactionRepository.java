package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
