package com.team.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.logging.LoggerFactory;
import com.team.model.BanknotePack;
import com.team.model.User;
import com.team.repository.AtmRepository;

@Service
public class AtmService {
//TODO implement
	@Autowired
	private AtmRepository atmRepository;
	
	//private Logger logger = LoggerFactory
	/**
	 * 
	 * @param user
	 * @param amount
	 * @return
	 */
	public List<BanknotePack> 
		withdrawAmountFromUserAccount(User user, int amountToWithdraw) {
		//TODO implement
		
		return null;	
	}

	public static boolean fillUpAtm() {
		return false;
		// TODO Auto-generated method stub
		
	}
}
