package com.team.utils;

import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.team.model.BanknotePack;

public class AtmUtils {

	public static Logger logger = LoggerFactory.getLogger(AtmUtils.class);
	
	/**
	 * try to satisfy demand amountToWithraw having banknotPacks in ATM
	 * 	Algorithm: greater notes come first 
	 * 
	 * @param banknotePacks  list of banknotePack that ATM has
	 * @param amountToWithdraw   demanding amount of money from ATM
	 * @return list of banknotePack with overall amount lower or equal to 
	 * 		amountToWithdraw
	 */

	public static List<BanknotePack> getBanknotePacksBestTry(
			List<BanknotePack> initialBanknotePacks,
			int amountToWithdraw) {
		
		//convert list of BanknotPack to Map<note, amount>
		logger.info(">> into getBanknotePacksBestTry()");
		Map<Integer, Integer> initialMap = convertBanknotePackListToMap(initialBanknotePacks);
		
		Map<Integer, Integer> resultMap = new HashMap<>();
		
		for(int banknote: initialMap.keySet()) {
			int banknoteAmount = initialMap.get(banknote);
			
			while((banknoteAmount > 0) && (amountToWithdraw >= banknote )) {
				amountToWithdraw -= banknote;
				banknoteAmount --;
				
				int a = 0;
				resultMap.put(
					banknote,
					 a = (resultMap.get(banknote) == null)? 1: a+1
				);
			}
		}
			
		return convertMapToBanknotePackList(resultMap);
	}

	private static List<BanknotePack> convertMapToBanknotePackList(Map<Integer, Integer> map) {
		
		List<BanknotePack> list = new ArrayList<>();
		
		for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
			
			BanknotePack bp = new BanknotePack(
					entry.getKey(),
					entry.getValue());
			list.add(bp);
		}
		
			return list;
	}

	private static Map<Integer, Integer> convertBanknotePackListToMap(List<BanknotePack> initialBanknotePacks) {
	
		Map<Integer, Integer> map = new TreeMap<>(Comparator.reverseOrder());
		for(BanknotePack banknotePack : initialBanknotePacks) {
			map.put(banknotePack.getNote(), banknotePack.getAmount());
		}
		return map;
	}

	

	/**
	 * Suggest max amount to withdraw when can not satisfy demand
	 * @param amountToWithdraw user demand
	 * @param banknotePacks     notes in ATM
	 * @param banknotePacksBestTry  notes offer for user
	 * @return maximum value
	 */
	public static int getMaxAmountSuggestion(int amountToWithdraw, 
			List<BanknotePack> banknotePacks,
			List<BanknotePack> banknotePacksBestTry) {

		int amountBestTry = countAmountInListOfBanknotePack(banknotePacksBestTry);
		
		Map<Integer, Integer> initialMap = convertBanknotePackListToMap(banknotePacks);
		
		Map<Integer, Integer> bestTryMap = convertBanknotePackListToMap(banknotePacksBestTry);
		
		//subtract bestTryMap from initialmap
		
		for(Entry<Integer,Integer> e: bestTryMap.entrySet()) {
			
			int banknote =  e.getKey();
			int amount = e.getValue();
			
			initialMap.put(banknote, initialMap.get(banknote) - amount);
		}
		
		//determine minimum banknote left in initialMap
		Map<Integer,Integer> sortedInitialMap = new TreeMap<>(initialMap);
		
		int minBanknote = 0;
		
		for(int banknote : sortedInitialMap.keySet()) {
			
			int amount = sortedInitialMap.get(banknote);
			if(amount != 0) {
				minBanknote = banknote;
				break;
			}
		}
		
		return (minBanknote < amountToWithdraw)
				? amountBestTry + minBanknote
				: minBanknote;
	}

	public static int countAmountInListOfBanknotePack(List<BanknotePack> banknotePacks) {
		int sum = 0;
		
		for(BanknotePack bp : banknotePacks) {
			
			sum += (bp.getNote() * bp.getAmount());
		}
		return sum;
	}

}
