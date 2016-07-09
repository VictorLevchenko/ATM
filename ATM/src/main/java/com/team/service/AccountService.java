package com.team.service;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.exeptions.ErrorResponseException;
import com.team.model.User;
import com.team.repository.AccountRepository;

@Service
public class AccountService {
	private Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired 
	private AccountRepository accountRepository;
	
	public void createUserAccount(User user) {
	
		accountRepository.createUserAccount(user);
	}
	
	public Integer getBalanseFromUserAccount(User user) throws ErrorResponseException {
		
		return accountRepository.getBalanceFromUserAccount(user);
	}
	
	public boolean checkIfUserHaveAmountOnAccount(User user, int amount) 
			throws ErrorResponseException {
		Integer balance = accountRepository.getBalanceFromUserAccount(user);
		logger.info(">> balance = " + balance);
		if(balance == null) {
			throw new ErrorResponseException("User has no account");
		}
		if(balance < amount) {
			throw new ErrorResponseException("User has not enough money on account");
		}
		return balance >= amount;
	}
/**
 * withdraw amount from user account after user get his money
 * @param user
 * @param amount
 * @return
 * @throws ErrorResponseException 
 */
	public boolean withdrawAmountFromUserAccount(User user, int amount) 
			throws ErrorResponseException {
		
		if(accountRepository.withdrawAmountFromUserAccount(user, amount)) {
			
			return true;
		} else {
			throw new ErrorResponseException("Failed to withdraw from user account");
			
		}

	}
}
