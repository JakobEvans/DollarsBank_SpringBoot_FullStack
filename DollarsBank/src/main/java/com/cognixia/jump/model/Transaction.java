package com.cognixia.jump.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "DATE")
	private LocalDate date;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "BALANCE_BEFORE")
	private Double balanceBefore;
	
	@Column(name = "BALANCE_AFTER")
	private Double balanceAfter;
	
	@Column(name = "STATUS_MESSAGE")
	private String statusMessage;

	public Transaction(int id, LocalDate date, Double amount, Double balanceBefore, Double balanceAfter,
			String statusMessage) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.balanceBefore = balanceBefore;
		this.balanceAfter = balanceAfter;
		this.statusMessage = statusMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(Double balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public Double getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(Double balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", amount=" + amount + ", balanceBefore=" + balanceBefore
				+ ", balanceAfter=" + balanceAfter + ", statusMessage=" + statusMessage + "]";
	}
	
	

	
}
