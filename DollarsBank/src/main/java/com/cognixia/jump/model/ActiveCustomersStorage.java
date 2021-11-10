package com.cognixia.jump.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;

import antlr.collections.List;

 public final class ActiveCustomersStorage {

    public static HashSet<Customer> customers;

    public ActiveCustomersStorage() {
        customers = new HashSet<Customer>();
    }

    // standard getter and setter
    
    
    @Bean
    public ActiveCustomersStorage activeCustomers(){
        return new ActiveCustomersStorage();
    }

	public HashSet<Customer> getCustomers() {
		return customers;
	}

	public static void addCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public static void removeCustomer(Customer customer) {
		customers.remove(customer);
	}
}

