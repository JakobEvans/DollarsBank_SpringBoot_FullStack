
package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognixia.jump.model.Customer;

@Repository
@EntityScan(basePackages = "com.cognixia.jump.model")
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    Optional<Customer> findByUsername(String username);
}


