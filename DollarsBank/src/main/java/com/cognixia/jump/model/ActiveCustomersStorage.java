package com.cognixia.jump.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;

import antlr.collections.List;

 public final class ActiveCustomersStorage {

    public static HashMap<Integer, Customer> customers;

    public ActiveCustomersStorage() {
        customers = new HashMap<Integer, Customer>();
    }

    // standard getter and setter
    
    
    @Bean
    public ActiveCustomersStorage activeCustomers(){
        return new ActiveCustomersStorage();
    }

	public HashMap<Integer, Customer> getCustomers() {
		return customers;
	}

	public static void addCustomer(Customer customer) {
		customers.putIfAbsent(customer.getId(), customer);

	}
	
	public static void removeCustomer(Customer customer) {
		customers.remove(customer.getId());
	}
}

