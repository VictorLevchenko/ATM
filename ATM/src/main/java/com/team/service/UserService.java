package com.team.service;

import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.exeptions.ErrorResponseException;
import com.team.model.User;
import com.team.repository.UserRepository;

@Service
public class UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private HttpSession session;
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @return
	 * @throws ErrorResponseException
	 */
	public User registerUser(String login, String password) throws ErrorResponseException {
		
		logger.info(">> into registerUser()");
		
		logger.info(">> regestering user");
		User userRegistered =  userRepository.registerUser(login, password);
		logger.info(">> userRegistered = " + userRegistered);
		
		logger.info("<< out of registerUser()");
		
		return userRegistered;
	}
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @return
	 * @throws ErrorResponseException
	 */
	public User loginUser(String login, String password) throws ErrorResponseException {
		logger.info(">>loginUser()");
		User user = userRepository.getUserByLogin(login);
		return user;
	}
	
/**
 * 
 * @param user
 */
	
	public boolean saveAutorizedUserToSession(User user) {
		
		logger.info(">> into saveAutorizedUserToSession()");
		
		logger.info(">> save user to session");
		session.setAttribute("user", user);
		logger.info(">> user saved to session");
		
		logger.info(">> reading user from session");
		User userReadFromSession = (User) session.getAttribute("user");
		logger.info(">> user = " + user);
		
		logger.info("<< out of saveAutorizedUserToSession()");
		
		return userReadFromSession.equals(user);
	}
	
	/**
	 * 
	 * @return
	 * @throws ErrorResponseException
	 */
	public User getAuthorizedUserFromSession() throws ErrorResponseException {
		
		logger.info(">> into getAuthorizedUserFromSession");
		
		logger.info(">> reading user from session");
		User user = (User) session.getAttribute("user");
		logger.info(">> user = " + user);
		
		if(user == null) {
			throw new ErrorResponseException("Not authorized user");
		}
		
		logger.info("<< out of getAuthorizedUserFromSession()");
		
		return user;
	}
	
	/**
	 * 
	 */
	
	public void deleteAuthorizedUserFromSession() {
	
		logger.info(">> into deleteAuthorizedUserFromSession");
		
		logger.info(">> deleting user from session");
		session.setAttribute("user", null);
		User userDeleted = (User) session.getAttribute("user");
		logger.info(">> userDeleted = " + userDeleted);
	}
	
}
