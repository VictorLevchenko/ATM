package com.team.exeptions;

/*
 * we  throw that exceptions from all places and catch in
 *  global controller. 
 */
public class ErrorResponseException extends Exception {
	
	private static final long serialVersionUID = 2552367499609311658L;
	
	
	String extraMessage;

	public ErrorResponseException(String message) {
		super(message);
	}
	
	public ErrorResponseException(String message,String extraMessage) {
		super(message);
		this.extraMessage = extraMessage;
	}

	public String getExtraMessage() {
		return extraMessage;
	}

	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}
	
	
}
