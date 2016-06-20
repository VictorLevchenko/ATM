package com.team.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.team.model.Account;
import com.team.model.User;
import com.team.service.UserService;

@Repository
public class AccountRepository {
//TODO implement
	private static Logger logger = LoggerFactory.getLogger(AccountRepository.class);
	@Autowired private JdbcTemplate jdbcTemplate;
	
	public boolean createUserAccount(User user) {
		 
		logger.info(">> create user account");
		String query = "INSERT INTO user_accounts (user_id, balance) VALUES (?, ?)";
		Object[] args = new Object[] {user.getId(), Account.DEFAULT_BALANCE};
		int rowNum = jdbcTemplate.update(query, args);
		
		return (rowNum != 0);
}
}
