package com.team.exeptions;

/*
 * we  throw that exceptions from all places and catch in
 *  global controller. 
 */
public class ErrorResponseException extends Exception {
	String extraMessage;

	public ErrorResponseException(String extraMessage) {
		
		this.extraMessage = extraMessage;
	}

	public String getExtraMessage() {
		return extraMessage;
	}

	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}
	
	
}
