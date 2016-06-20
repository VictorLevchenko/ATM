package com.team.model;

public class Account {
	
	public static final int DEFAULT_BALANCE = 50000;
	
	private int id;
	private int userId;
	private int balance;
	
	public Account(int id, int userId, int balance) {
		super();
		this.id = id;
		this.userId = userId;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", userId=" + userId + ", balance=" + balance + "]";
	}
	
	

}
