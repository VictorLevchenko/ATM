package com.team.web_api;
import java.util.Map;

import org.slf4j.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team.utils.ResponseUtils;

@ControllerAdvice
public class GlobalExceptionController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionController.class);
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Map<String, String> handle(Exception e) {
		
		logger.error(">> GlobalController Exception: ", e);
		return ResponseUtils.buildErrorResponse(e);
	}
	
}
