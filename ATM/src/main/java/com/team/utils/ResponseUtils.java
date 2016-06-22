package com.team.utils;

import java.util.*;

import org.slf4j.*;

import com.team.exeptions.ErrorResponseException;

public class ResponseUtils {
	
	public static Logger logger = LoggerFactory.getLogger(ResponseUtils.class);
	
	/**
	 * 
	 * @return
	 */
	public static Map<String, String> buildSuccessfulResponse() {
		
		Map<String, String> responseParams = new HashMap<>();
		
		responseParams.put("status", "OK");
		
		return responseParams;
	}
	
	/** 
	 * 
	 * @param e
	 * @return
	 */
	public static Map<String, String>
		buildSuccessfulResponse(Map<String, String> responseParams) {
		
		logger.info(">> into buildSucessfulResponse()");
		
		responseParams.put("status", "OK");
		
		return responseParams;
		
	}
	
	public static Map<String, String> buildErrorResponse(Exception e) {
		
		logger.info("Building error response");
		
		Map<String, String> responseParams = new HashMap<>();
		
		responseParams.put("status", "ERROR");
		responseParams.put("message", e.getMessage());
	
		//if it's our exceptions
		if(e instanceof ErrorResponseException) {
			logger.info(">>Exception: " +((ErrorResponseException)e).getExtraMessage());
			String s = ((ErrorResponseException) e).getExtraMessage();
			if(s != null)
			responseParams.put("You can take", ((ErrorResponseException) e).getExtraMessage());
		}
		else {
			responseParams.put("message", e.getMessage());
		}
		
		return responseParams;
		
	}
}
