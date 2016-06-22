package com.team.web_api;


import java.util.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.exeptions.ErrorResponseException;
import com.team.model.User;
import com.team.service.AccountService;
import com.team.service.UserService;
import com.team.utils.RequestUtils;
import com.team.utils.ResponseUtils;
import com.team.utils.ValidateUtils;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired 
	private UserService userService;
	@Autowired 
	private AccountService accountService;
	
	/*
	 * @param requestParams parameters in request in format
	 * {
	 * 		"login": "email@com"
	 * 		"password": "12345"
	 * }
	 */
	@RequestMapping(value = "/register",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE
					)
	public Map<String, String> registerUserAction(@RequestBody Map<String, String>requestParams) throws ErrorResponseException {
		
		logger.info(">> into registerAction()");
		
		logger.info(">> gettig login and pasword from request");
		String login = RequestUtils.getLogin(requestParams);
		String password = RequestUtils.getPassword(requestParams);
		logger.info(">> login = " + login + "password = " + password);
		
		logger.info(">> validating email");
		 boolean isValidEmail = ValidateUtils.validateEMail(login);
		 boolean isValidPassword = ValidateUtils.validatePassword(password);
		 logger.info("isValidEmail = " + isValidEmail + "isValidPassword = " + isValidPassword);
		
		logger.info(">> regesterign user");
		User userRegistered = userService.registerUser(login, password);
		logger.info(">> userRegistered = " + userRegistered);
		
		logger.info(">> creating user account");
		accountService.createUserAccount(userRegistered);
		
		
		logger.info(">> logining user");
		User userLogined = userService.loginUser(login, password);
		logger.info(">> userLoggined = " + userLogined);
		
		logger.info(">> saving user to session");
		boolean isSuccess = userService.saveAutorizedUserToSession(userLogined);
		logger.info(">> sucessfully saved user to session = " + isSuccess);
		
		logger.info("<< out of registerAction()");
		
		return ResponseUtils.buildSuccessfulResponse();
		
	}
	
	/*
	 * @param requestParams parameters in request in format
	 * {
	 * 		"login": "email@com"
	 * 		"password": "12345"
	 * }
	 */
	@RequestMapping(value = "/login", 
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE
					)
	public Map<String, String> loginUserAction(@RequestBody Map<String, String>requestParams) throws ErrorResponseException {
		
		logger.info(">> into loginAction()");
		
		logger.info(">> getting login, password from request");
		String login = RequestUtils.getLogin(requestParams);
		String password = RequestUtils.getPassword(requestParams);
		logger.info(">> login = " + login + "password = " + password);
		
		logger.info(">> logining user"); 
		User userLogined = userService.loginUser(login, password);
		logger.info(">> userLogined = " + userLogined);
		
		logger.info(">> saving Authorized user to session");
		boolean isSucsess = userService.saveAutorizedUserToSession(userLogined);
		logger.info(">> sucessfully saved user to session = " + isSucsess);
		
		logger.info("<< out of  loginUserAction()");
		
		return ResponseUtils.buildSuccessfulResponse();
		
	}
	
	@RequestMapping(value = "/logout",
					produces = MediaType.APPLICATION_JSON_VALUE
					)
	public Map<String, String> logoutAction() {
		
		logger.info(">> into logoutAction()");
		
		logger.info(">> deleting user from session");
		userService.deleteAuthorizedUserFromSession();
		logger.info(">> user delete from session");
		
		return ResponseUtils.buildSuccessfulResponse();
	}
	
	//TODO only for test
	@RequestMapping (value = "/check_session", 
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE
					)
	Map<String, String> checkSession() throws ErrorResponseException {
		logger.info("user = " + userService.getAuthorizedUserFromSession());
		
		return ResponseUtils.buildSuccessfulResponse();
	}

}
