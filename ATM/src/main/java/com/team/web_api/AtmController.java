package com.team.web_api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.exeptions.ErrorResponseException;
import com.team.model.BanknotePack;
import com.team.model.User;
import com.team.service.AccountService;
import com.team.service.AtmService;
import com.team.service.UserService;
import com.team.utils.RequestUtils;
import com.team.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/atm")
public class AtmController {

	private Logger logger = LoggerFactory.getLogger(AtmController.class);

	@Autowired
	private AtmService atmService;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	/**
	 * 
	 * @return
	 * @throws ErrorResponseException
	 */
	@RequestMapping(value = "/fillup")
	public Map<String, String> fillUpAtmAction() throws ErrorResponseException {

		logger.info(">> into fillUpAction()");

		logger.info(">> getting user from session");
		User user = userService.getAuthorizedUserFromSession();
		logger.info(">> User = " + user);

		if (!user.isAdmin()) {
			throw new ErrorResponseException("Only admins can fill up ATM");
		}

		logger.info(">> filling atm");
		atmService.fillUpAtm();

		logger.info("<< out of fillUpAction()");
		return ResponseUtils.buildSuccessfulResponse();
	}

	/*
	 * @param requestParams parameters in request in format { "amount": "550" }
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> withdrawAction(@RequestBody Map<String, String> requestParam)
			throws ErrorResponseException {

		logger.info(">> into withdrawAction()");

		logger.info(">> getting user from session");
		User user = userService.getAuthorizedUserFromSession();
		logger.info(">> user = " + user);

		logger.info(">> getting amount from request");
		int amount = RequestUtils.getAmount(requestParam);
		logger.info(">> amount = " + amount);

		logger.info(">> checking if user have amount on account");
		boolean isHaveAmount = accountService.checkIfUserHaveAmountOnAccount(user, amount);
		logger.info(">> isHaveAmount = " + isHaveAmount);

		logger.info(">> getting list of banknote packs for user");
		List<BanknotePack> bankNotePacks = atmService.withdrawAmountFromAtm(user, amount);
		logger.info(">> bankNotePacks = " + bankNotePacks.toString());

		logger.info(">> withdrawing money from user account");
		boolean isSusscessWithdrawAmountFromAccount = accountService.withdrawAmountFromUserAccount(user, amount);
		logger.info(">> isSusscessWithdrawAmountFromAccount = " + isSusscessWithdrawAmountFromAccount);

		Map<String, String> responseParams = new HashMap<>();
		responseParams.put("banknotes", bankNotePacks.toString());

		logger.info("<< out of withdrawAction()");

		return ResponseUtils.buildSuccessfulResponse(responseParams);
	}

	
}
