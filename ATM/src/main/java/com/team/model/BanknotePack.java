package com.team.model;

/** 
 * Pack of bank notes (e.g. 10pcs of "100" notes)
 */
public class BanknotePack {

	public static final int DEFAULT_BANKNOTE_AMOUNT = 2;
	
	private int note;
	private int amount;
	
	public BanknotePack() {
		
	}
	
	public BanknotePack(int note, int amount) {
		
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + note;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BanknotePack other = (BanknotePack) obj;
		if (amount != other.amount)
			return false;
		if (note != other.note)
			return false;
		return true;
	}
	
	
	

}
