package com.team.web_api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/atm")
public class AtmController {
	
	@RequestMapping(value = "/fillup")
	public boolean fillUp() {
		//TODO implement
		return true;
	}
}
