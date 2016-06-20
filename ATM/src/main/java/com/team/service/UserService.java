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
	
	public User registerUser(String login, String password) throws ErrorResponseException {
		
		logger.info(">> registerUser()");
		
		return userRepository.registerUser(login, password);
	}
	
	public User loginUser(String login, String password) throws ErrorResponseException {
		logger.info(">>validateUser()");
		User user = userRepository.getUserByLogin(login);
		return user;
	}
	public void saveAutorizedUsertToSession(User user) {
		session.setAttribute("user", user);
	}
	public User getAuthorizedUserFromSession() throws ErrorResponseException {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			throw new ErrorResponseException("Not authorized user");
		}
		return user;
	}
	public void deleteUserFromSession() {
		session.setAttribute("user", null);
	}
	
}
