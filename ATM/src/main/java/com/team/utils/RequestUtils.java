package com.team.utils;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.team.exeptions.ErrorResponseException;

public class RequestUtils {

	private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);
	
	public static String getLogin(Map<String, String> requestParams) 
			throws ErrorResponseException {
		
		String login;
		login = requestParams.get("login");
		if((login == null) || login.isEmpty()) {
			throw new ErrorResponseException("Invalid login");
		}
		logger.info("login = " + login);
		return login;
	}
	
	public static String getPassword(Map<String, String> requestParams)
			throws ErrorResponseException {
		
		String password = requestParams.get("password");
		if((password == null) || password.isEmpty() ) {
			throw new ErrorResponseException("Invalid password");
		}
		logger.info("password = " + password);
		return password;
	}
	
	public static int getAmount(Map<String, String> requestParams) 
			throws ErrorResponseException {
		
		int amount = Integer.parseInt(requestParams.get("amount"));
		if(amount <= 0) {
			throw new ErrorResponseException("Invalid amount");
		}
		return amount;
	}
	
}
