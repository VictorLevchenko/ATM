package com.team.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.team.exeptions.ErrorResponseException;
import com.team.model.Account;
import com.team.model.User;
import com.team.service.UserService;

@Repository
public class AccountRepository {
//TODO implement
	private static Logger logger = LoggerFactory.getLogger(AccountRepository.class);
	@Autowired private JdbcTemplate jdbcTemplate;
	
	public Account createUserAccount(User user) {
		 
		logger.info(">> create user account");
		String query = "INSERT INTO user_accounts (user_id, balance) VALUES (?, ?)";
		Object[] args = new Object[] {user.getId(), Account.DEFAULT_BALANCE};
		int rowNum = jdbcTemplate.update(query, args);
		//TODO return account
		return null;
	}
	
	public Integer getBalanceFromUserAccount(User user) throws ErrorResponseException {
		
		logger.info(">> getBalanceFromUserAccount");
		Integer balance = null;
		String query = "SELECT (balance) FROM user_accounts "
				+ "WHERE user_id = ?";
		try {
				balance = jdbcTemplate.queryForObject(query, new Object[]{user.getId()},
				new RowMapper<Integer>() {
			 		@Override
			 		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			 			return new Integer(rs.getInt("balance"));
			 		}});
	
    	} catch(Exception e) {
    		logger.info(">>" + e.getMessage());
    		throw new ErrorResponseException("User not regestered");
    	}
		logger.info("<< getBalanceFromUserAccount");
    	return balance;
	}
}
