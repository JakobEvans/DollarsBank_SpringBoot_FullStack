package com.cognixia.jump.model;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "DATE")
	private Date date;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "BALANCE_BEFORE")
	private Double balanceBefore;
	
	@Column(name = "BALANCE_AFTER")
	private Double balanceAfter;
	
	@Column(name = "STATUS_MESSAGE")
	private String statusMessage;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	Customer customer;
	
	

	public Transaction(int id, Date date, Double amount, Double balanceBefore, Double balanceAfter,
			String statusMessage, @NotNull Customer customer) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.balanceBefore = balanceBefore;
		this.balanceAfter = balanceAfter;
		this.statusMessage = statusMessage;
		this.customer = customer;
	}
	
	public Transaction() {
		super();
//		this.id = id;
		this.date = new Date();
		this.amount = 0.0;
		this.balanceBefore = 0.0;
		this.balanceAfter = 0.0;
		this.statusMessage = "N/A";
		this.customer = new Customer();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
