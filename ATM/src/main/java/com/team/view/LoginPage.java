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
@SuppressWarnings("serial")
public class LoginPage extends VerticalLayout implements View {

	public static Logger logger = LoggerFactory.getLogger(LoginPage.class);
	public static final String NAME = "";

	@Override
	public void enter(ViewChangeEvent event) {
		
		Panel panel = new Panel("Login");
		panel.setSizeUndefined();
		this.addComponent(panel);
		
		FormLayout form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);
		form.setSizeUndefined();;
		TextField emailTxtFld = new TextField("Email");
		TextField passwordTxtFld = new TextField("Password");
		form.addComponents(emailTxtFld, passwordTxtFld);
		
		Button loginBtn = new Button("Login");
		loginBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		loginBtn.setClickShortcut(KeyCode.ENTER);
		loginBtn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				RestTemplate restTemplate = new RestTemplate();
				// TODO use Controller path
				//String url = linkTo(UserController.class).toString();
				//logger.info(">> url = " + url );
				String url = "http://localhost:8082/user/login";

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
				logger.info(">> requestJson = " + requestJson);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				

				HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
	
				@SuppressWarnings("unchecked")
				Map<String, String> responseMap = restTemplate.postForObject(url, entity, HashMap.class);
			
				logger.info(">> responseMap = " + responseMap);
				String responseStatus = responseMap.get("status");
				logger.info(">> get status from responseMap = " + responseStatus );
				
				if("OK".equals(responseStatus)) {
					Page.getCurrent().setUriFragment("!" + ATMPage.NAME);
				} else {
					Notification.show(responseMap.get("message"),
							Notification.Type.ERROR_MESSAGE);
				}
				
				
			}
		});
		
		
		Button registerBtn = new Button("Register");
		registerBtn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Page.getCurrent().setUriFragment("!" + RegisterPage.NAME);
				
			}
		});
		
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		buttonsLayout.setSizeUndefined();;
		buttonsLayout.addComponents(loginBtn, registerBtn);
		
		form.addComponent(buttonsLayout);

		panel.setContent(form);
		this.setSizeFull();
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		
	}
}
