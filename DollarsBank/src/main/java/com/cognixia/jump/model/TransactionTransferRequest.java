package com.cognixia.jump.model;

public class TransactionTransferRequest {
	private int customerId;

	private int recieverId;

	private double amount;

	public TransactionTransferRequest(int customerId, int recieverId, double amount) {
		this.setCustomerId(customerId);
		this.recieverId = recieverId;

		this.amount = amount;
	}

	

	public int getRecieverId() {
		return recieverId;
	}

	public void setRecieverId(int recieverId) {
		this.recieverId = recieverId;
	}
	
	
	public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	  @Override
	    public String toString() {
	        return "{" +
	            " senderId='" + getCustomerId() + "'" +
	            " recieverId='" + getRecieverId() + "'" +

	            ", amount='" + getAmount() + "'" +
	            "}";
	    }



	

}
