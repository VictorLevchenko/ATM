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


@Repository
public class AccountRepository {

	private static Logger logger = LoggerFactory.getLogger(AccountRepository.class);
	@Autowired private JdbcTemplate jdbcTemplate;
	/**
	 * create user account and set balance to default
	 * @param user
	 * @return
	 */
	
	public boolean createUserAccount(User user) {
		 
		logger.info(">> create user account");
		String query = "INSERT INTO user_accounts (user_id, balance) VALUES (?, ?)";
		Object[] args = new Object[] {user.getId(), Account.DEFAULT_BALANCE};
		int rowNum = jdbcTemplate.update(query, args);
		
		return rowNum != 0;
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

	public boolean withdrawAmountFromUserAccount(User user, int amount) {

		String query = " UPDATE user_account SET balance = balance - ?";
		int rowsNum = jdbcTemplate.update(query, amount);
		return rowsNum != 0;
	}
}
