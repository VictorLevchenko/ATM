package com.team.service;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.exeptions.ErrorResponseException;
import com.team.model.Account;
import com.team.model.User;
import com.team.repository.AccountRepository;

@Service
public class AccountService {
	private Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired 
	private AccountRepository accountRepository;
	
	public Account createUserAccount(User user) {
	
		return accountRepository.createUserAccount(user);
	}
	
	public boolean checkIfUserHaveAmountOnAccount(User user, int amount) throws ErrorResponseException {
		Integer balance = accountRepository.getBalanceFromUserAccount(user);
		logger.info(">> balance = " + balance);
		if(balance == null) {
			throw new ErrorResponseException("User has no account");
		}
		if(balance < amount) {
			throw new ErrorResponseException("User has not enough money no account");
		}
		return balance >= amount;
	}
/**
 * 
 * @param user
 * @param amount
 * @return
 */
	public boolean withdrawAmountFromUserAccount(User user, int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
