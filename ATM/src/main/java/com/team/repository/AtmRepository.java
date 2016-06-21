package com.team.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.team.model.BanknotePack;

@Repository
public class AtmRepository {
//TODO implement
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * @return list of banknotepackes, like [{5:0},{10:1},...]
	 */
	public List<BanknotePack> getListOfAllAtmBanknotePack() {
		
		String query = "SELECT banknote_value, banknote_num FROM atm_banknote_packs ";

		return jdbcTemplate.query(query,
		            new RowMapper<BanknotePack>() {
		                @Override
		                public BanknotePack mapRow(ResultSet rs, int rowNum) throws SQLException  {
		                    return new BanknotePack(
		                        rs.getInt("banknote_value"),
		                        rs.getInt("banknote_num")
		                    );
		                }
		            }
		        );
	}
	
}