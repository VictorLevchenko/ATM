package com.team.model;

/** 
 * Pack of bank notes (e.g. 10pcs of "100" notes)
 */
public class BanknotePack {

	private int note;
	private int amount;
	
	
	
	public BanknotePack(int note, int amount) {
		super();
		this.note = note;
		this.amount = amount;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "BanknotePack [note=" + note + ", amount=" + amount + "]";
	}
	
	

}
