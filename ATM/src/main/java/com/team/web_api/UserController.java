package com.team.web_api;


import java.util.*;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.exeptions.ErrorResponseException;
import com.team.model.User;
import com.team.service.UserService;
import com.team.utils.RequestUtils;
import com.team.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired 
	private UserService userService;
	
	/*
	 * @param requestParams parameters in request in format
	 * {
	 * 		"login": "email@com"
	 * 		"password": "12345"
	 * }
	 */
	@RequestMapping(value = "/register", 
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> registerUserAction(@RequestBody Map<String, String>requestParams) throws ErrorResponseException {
		
		logger.info(">> into registerAction()");
		
		//get login and pasword from request
		String login = RequestUtils.getLogin(requestParams);
		String password = RequestUtils.getPassword(requestParams);
		
		//register user 
		User user = userService.registerUser(login, password);
		
		//create user account
		userService.createUserAccount(user);
		
		logger.info("<< out of registerAction()");
		
		return ResponseUtils.buildSuccessfulResponse();
		
	}

}
