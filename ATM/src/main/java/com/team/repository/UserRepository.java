package com.team.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.team.exeptions.ErrorResponseException;
import com.team.model.Account;
import com.team.model.User;



@Repository
public class UserRepository {
	
	private static Logger logger = LoggerFactory.getLogger(UserRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User registerUser(String login, String password) throws ErrorResponseException {
		
		logger.info(">> registerUser");
		logger.info(">> writing to database");
		try {
				String query = "INSERT INTO users (login, password) VALUES (?, ?)";
				Object[] args = new Object[] {login, password};
				int rowNum = jdbcTemplate.update(query, args);
		
		} catch(Exception e) {
			throw new ErrorResponseException("User already exists");
		}
		logger.info(">> user have registered");
		User user = null;
		try {
				String query = "SELECT * FROM users WHERE login = ?";
				user = jdbcTemplate.queryForObject(query, new Object[]{login},
						new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new User(
								rs.getInt("id"),
								rs.getString("login"),
								rs.getString("password"),
								rs.getBoolean("is_admin"));
					}
				});
		logger.info(">> get registered user from database");
		} catch(Exception e) {
			throw new ErrorResponseException("Unsuccessful attempt writing to database");
		}
		return user;
	}
	
	public boolean createUserAccount(User user) {
		 
		logger.info(">> create user account");
		String query = "INSERT INTO user_accounts (user_id, balance) VALUES (?, ?)";
		Object[] args = new Object[] {user.getId(), Account.DEFAULT_BALANCE};
		int rowNum = jdbcTemplate.update(query, args);
		
		return (rowNum != 0);
	}

}
