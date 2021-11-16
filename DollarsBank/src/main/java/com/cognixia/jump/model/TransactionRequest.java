package com.cognixia.jump.model;

public class TransactionRequest {
    private double amount;

    public TransactionRequest() {
        this.amount = 0.0;
    }

    public TransactionRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "{" +
            " amount='" + getAmount() + "'" +
            "}";
    }
    

}
