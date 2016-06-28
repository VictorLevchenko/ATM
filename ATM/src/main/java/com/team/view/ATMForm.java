package com.team.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

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

			@Override
			public void buttonClick(ClickEvent event) {

				RestTemplate restTemplate = new RestTemplate();
				// TODO use Controller path
				String url = "http://localhost:8082/user/register";
				String requestJson =
						"{\"login\":\"" + emailTxt.getValue() + "\"," + "\"password\":\""
						+ passwordTxt.getValue() + "\"}";
				System.out.println(requestJson);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
	
				String answer = restTemplate.postForObject(url, entity, String.class);
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
