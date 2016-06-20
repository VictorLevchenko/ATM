package com.team.web_api;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/atm")
public class AtmController {
	
	@RequestMapping(value = "/fillup")
	public boolean fillUpAction() {
		//TODO implement
		return true;
	}
	/*
	 * @param 
	 */
	@RequestMapping(value = "/withdraw")
	public Map<String, String> withdrawAction(@RequestBody Map<String,String> requestParam) {
		
		//TODO implement
		
		return ResponseUtils.buildSuccessfulResponse();
	}
}
