package com.team.view;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class RegisterPage extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger = LoggerFactory.getLogger(RegisterPage.class);
	public static final String NAME = "Register";

	@Override
	public void enter(ViewChangeEvent event) {

		Panel panel = new Panel("Register");
		panel.setSizeUndefined();
		
		this.addComponent(panel);
		this.setSizeFull();
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		FormLayout form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);
		form.setSizeUndefined();
	
		TextField firstNameTxtFld = new TextField("First Name");
		TextField lastNameTxtFld = new TextField("Last Name");
		TextField emailTxtFld = new TextField("Email");
		TextField passwordTxtFld = new TextField("Password");
		TextField repeatPasswordTxtFld = new TextField("Repeat Password");
		
		form.addComponents(firstNameTxtFld, lastNameTxtFld, emailTxtFld, 
				passwordTxtFld, repeatPasswordTxtFld);

		Button registerBtn = new Button("Register");
		registerBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		registerBtn.setClickShortcut(KeyCode.ENTER);
		registerBtn.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				RestTemplate restTemplate = new RestTemplate();
				
				String url = "http://localhost:8082/user/register";
				
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> requestMap = new HashMap<>();
				requestMap.put("login", emailTxtFld.getValue());
				requestMap.put("password", passwordTxtFld.getValue());
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
	
				@SuppressWarnings("unchecked")
				Map<String, String> responseMap = restTemplate.postForObject(url, entity, HashMap.class);
			
				logger.info(">> responseMap = " + responseMap);
				String responseStatus = responseMap.get("status");
				logger.info(">> get status from responseMap = " + responseStatus );
				
				if("OK".equals(responseStatus)) {
					Page.getCurrent().setUriFragment("!" + LoginPage.NAME);
				} else {
					Notification.show(responseMap.get("message"),
							Notification.Type.ERROR_MESSAGE);
				}
			}
		});

		Button loginBtn = new Button("Login");
		loginBtn.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Page.getCurrent().setUriFragment("!" + LoginPage.NAME);
				
			}
		});
		
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		buttonsLayout.setSizeUndefined();
		buttonsLayout.addComponents(registerBtn, loginBtn);
		
		form.addComponent(buttonsLayout);
		
		panel.setContent(form);

	}

}
