package com.team.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.team.model.BanknotePack;

@Repository
public class AtmRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(AtmRepository.class);
	
	/**
	 * 
	 * @return list of banknotepackes, like [{5:0},{10:1},...]
	 */
	public List<BanknotePack> getListOfAllAtmBanknotePack() {
		
		String query = "SELECT banknote_value, banknote_num FROM atm_banknote_packs ";

		return jdbcTemplate.query(query,
		            new RowMapper<BanknotePack>() {
		                @Override
		                public BanknotePack mapRow(ResultSet rs, int rowNum)
		                		throws SQLException  {
		                    return new BanknotePack(
		                        rs.getInt("banknote_value"),
		                        rs.getInt("banknote_num")
		                    );
		                }
		            }
		        );
	}

	/** withdraw bank notes from ATM after user has got his money
	 * 
	 * @param banknotePacks  money given to user
	 */

	public void withdrawBanknotesFromAtm(List<BanknotePack> banknotePacks) {
	
		logger.info(">> into  withdrawBanknotesFromAtm()");
		for(BanknotePack banknotePack: banknotePacks) {
			String query = "UPDATE atm_banknote_packs "
					      + "SET banknote_num = banknote_num - ? "
					      + "WHERE banknote_value = ?";
			jdbcTemplate.update(query, banknotePack.getAmount(), banknotePack.getNote());
					      
		}
		
		logger.info(">> out of withdrawBanknotesFromAtm()");
	}

	/**
	 * fill up atm with default number of notes
	 */
	public void fillUpAtm() {
	
		logger.info(">> into fillUpAtm()");
		String query = "UPDATE atm_banknote_packs "
					+	 "SET banknote_num = ?";
		int rowsNum = jdbcTemplate.update(query, BanknotePack.DEFAULT_BANKNOTE_AMOUNT);
		logger.info(">> rowsNum = " + rowsNum );
		
		logger.info(">> out of fillUpAtm()");
	
	}
	
}