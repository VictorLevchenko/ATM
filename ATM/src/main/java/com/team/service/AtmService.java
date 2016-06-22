package com.team.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.exeptions.ErrorResponseException;
import com.team.model.BanknotePack;
import com.team.model.User;
import com.team.repository.AtmRepository;
import com.team.utils.AtmUtils;

@Service
public class AtmService {

	@Autowired
	private AtmRepository atmRepository;
	
	
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(AtmService.class);
	/**
	 * 
	 * @param user
	 * @param amount
	 * @return
	 * @throws ErrorResponseException 
	 */
	public List<BanknotePack> 
		withdrawAmountFromAtm(User user, int amountToWithdraw) 
				throws ErrorResponseException {
	
		logger.info(">> into withdrawAmountFromUserAccount()");
		
		logger.info(">> getting all banknote packs in ATM");
		List<BanknotePack> banknotePacks = atmRepository.getListOfAllAtmBanknotePack();
		logger.info(">> list of all banknotepacks = " + banknotePacks);
		
		logger.info(">> counting all money in ATM");
		int amountInAtm = AtmUtils.countAmountInListOfBanknotePack(banknotePacks);
		logger.info(">> amount in Atm = " + amountInAtm);
		
		if(amountInAtm == 0) {
			
			throw new ErrorResponseException("Atm is empty");
			
		} 
		
		logger.info(">> getting equal or lower amount");
		List<BanknotePack> banknotePacksBestTry = 
				AtmUtils.getBanknotePacksBestTry(banknotePacks, amountToWithdraw);
		logger.info(">> banknotePacksBestTry  = " + banknotePacksBestTry.toString());
		
		logger.info(">> calculating amount of best try");
		int minAmountSuggestion = AtmUtils.countAmountInListOfBanknotePack(banknotePacksBestTry);
		logger.info(">> minAmountSuggestion = " + minAmountSuggestion);
		
		if(amountToWithdraw == minAmountSuggestion) {
			
			logger.info(">> success, we can give money to user");
			logger.info(">> banknotePacksBestTry = " + banknotePacksBestTry.toString());
			atmRepository.withdrawBanknotesFromAtm(banknotePacksBestTry);
			
			logger.info("<< successful return from withdrawAmountFromAtm()");
			return banknotePacksBestTry;
		}
		
		logger.info(">> calculating maxAmountSuggestion");
		int maxAmountSuggestion = AtmUtils.getMaxAmountSuggestion(amountToWithdraw,
				banknotePacks, banknotePacksBestTry);
		logger.info(">> maxAmountSuggestion = " + maxAmountSuggestion);
		
		logger.info(">> throw exception with suggestions");
		throw new ErrorResponseException("Fail to withdraw amount",
				(Arrays.toString(new Integer[]
						{minAmountSuggestion, maxAmountSuggestion}).toString()));
	}

	/** fill up ATM 
	 *  just call method on repository level
	 */
	public  void fillUpAtm() {
		
		logger.info(">> into fillUpAtm()");
		atmRepository.fillUpAtm();
		
		logger.info(">> out of fillUpAtm()");
	}
}
