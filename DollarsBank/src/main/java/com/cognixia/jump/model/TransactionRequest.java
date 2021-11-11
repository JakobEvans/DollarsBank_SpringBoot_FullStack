package com.cognixia.jump.model;

public class TransactionRequest {
    private int customerId;
    private double amount;

    public TransactionRequest(int customerId, double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
            " customerId='" + getCustomerId() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }

}
