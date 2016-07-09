package com.team.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.service.UserService;
import com.team.web_api.UserController;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ATMForm extends FormLayout {

	private TextField firstNameTxt = new TextField("First name");
	private TextField lastNameTxt = new TextField("Last name");
	private TextField emailTxt = new TextField("Email");
	private TextField passwordTxt = new TextField("Password");
	private TextField repeatPasswordTxt = new TextField("Repeat password");
	private Button loginBtn = new Button("Login");
	private Button logoutBtn = new Button("Logout");
	private Button registerBtn = new Button("Register");
	private Label messageLbl = new Label();
	@Autowired
	UserController userController;
	@Autowired
	UserService userService;

	@SuppressWarnings({ })
	public ATMForm() {

		loginBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		loginBtn.setClickShortcut(KeyCode.ENTER);
		
		registerBtn.addClickListener(new ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {

				RestTemplate restTemplate = new RestTemplate();
				// TODO use Controller path
				String url = "http://localhost:8082/user/register";
				/*String requestJson =
						"{\"login\":\"" + emailTxt.getValue() + "\"," + "\"password\":\""
						+ passwordTxt.getValue() + "\"}";*/
				ObjectMapper mapper = new ObjectMapper();
				Map requestMap = new HashMap();
				requestMap.put("login", emailTxt.getValue());
				requestMap.put("password", passwordTxt.getValue());
				String requestJson = null;
				try {
					requestJson = mapper.writeValueAsString(requestMap);
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(requestJson);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
	
				String answer = restTemplate.postForObject(url, entity, String.class);
				//ObjectMapper mapper = new ObjectMapper();
				Map<String, String> map = null;
				try {
						map = mapper.readValue(answer, Map.class);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(">>>map >> " + ((HashMap)map).get("status"));
				System.out.println(answer);
				messageLbl.setCaption(answer);
			}
		});

		logoutBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				RestTemplate restTemplate = new RestTemplate();
				// TODO use Controller path
				String url = "http://localhost:8082/user/logout";
				String requestJson = "";
				System.out.println(requestJson);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
				String answer = restTemplate.postForObject(url, entity, String.class);
				System.out.println(answer);
				messageLbl.setCaption(answer);
			}
		});

		loginBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				RestTemplate restTemplate = new RestTemplate();
				// TODO use Controller path
				String url = "http://localhost:8082/user/login";
				String requestJson = 
					"{\"login\":\"" + emailTxt.getValue() + "\"," + 
					"\"password\":\"" + passwordTxt.getValue() + "\"}";
				System.out.println(requestJson);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
				String answer = restTemplate.postForObject(url, entity, String.class);
				System.out.println(answer);
				messageLbl.setCaption(answer);
			}
		});

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.addComponents(loginBtn, logoutBtn, registerBtn);

		setSizeUndefined();
		firstNameTxt.setVisible(false);
		lastNameTxt.setVisible(false);
		repeatPasswordTxt.setVisible(false);
		
		addComponents(firstNameTxt, lastNameTxt, emailTxt, passwordTxt, repeatPasswordTxt, 
				buttonsLayout, messageLbl);
	}
}
