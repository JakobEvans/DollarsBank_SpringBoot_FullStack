package com.cognixia.jump.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Transaction;

@Repository
@EntityScan(basePackages = "com.cognixia.jump.model")
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
