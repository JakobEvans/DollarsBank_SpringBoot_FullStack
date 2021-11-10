package com.cognixia.jump.model;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;

import antlr.collections.List;

public class ActiveCustomersStorage {

    public ArrayList<Customer> customers;

    public ActiveCustomersStorage() {
        customers = new ArrayList<Customer>();
    }

    // standard getter and setter
    
    
    @Bean
    public ActiveCustomersStorage activeCustomers(){
        return new ActiveCustomersStorage();
    }
}

