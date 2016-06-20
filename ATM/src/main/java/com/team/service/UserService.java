package com.team.service;

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
	
	public User registerUser(String login, String password) throws ErrorResponseException {
		
		logger.info(">> registerUser()");
		
		return userRepository.registerUser(login, password);
	}
	
	public boolean createUserAccount(User user) {
		
		return userRepository.createUserAccount(user);
	}
}
