package com.team.view;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

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
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ATMPage extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	public static Logger logger = LoggerFactory.getLogger(LoginPage.class);
	private static final long serialVersionUID = 1L;
	public static final String NAME = "Atm";
	 private Label balanceLbl;
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		Panel panel = new Panel("");
		panel.setSizeUndefined();
		this.addComponent(panel);
		this.setSizeFull();
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		FormLayout form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);
		form.setSizeUndefined();
		panel.setContent(form);
		
		HorizontalLayout lables = new HorizontalLayout();
		Label currentBalanceLbl = new Label("Current balance:"); 
		Label balanceLbl = new Label("xxx");
		this.updateBalnace();
		
		balanceLbl.addStyleName(ValoTheme.LABEL_BOLD);;
		lables.setSizeUndefined();
		lables.setSpacing(true);
		lables.addComponents(currentBalanceLbl, balanceLbl);
		
		form.addComponent(lables);
		
		TextField amountTxtFld = new TextField("Amount");
		
		Button withdrawBtn = new Button("Withdraw");
		withdrawBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		withdrawBtn.setClickShortcut(KeyCode.ENTER);
		
		Button logoutBtn = new Button("Logout");
		logoutBtn.addClickListener(new ClickListener() {
			
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
		buttonsLayout.setSizeUndefined();;
		buttonsLayout.addComponents(withdrawBtn, logoutBtn);
		
		form.addComponents(amountTxtFld, buttonsLayout);
				
	}
	
	private void updateBalnace() {
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8082/user/balance";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		@SuppressWarnings("unchecked")
		Map<String, String> responseMap = restTemplate.postForObject(url, entity, HashMap.class);
	
		logger.info(">> responseMap = " + responseMap);
		
		String responseStatus = responseMap.get("status");
		logger.info(">> get status from responseMap = " + responseStatus );
		
		if("OK".equals(responseStatus)) {
			String responseBalance = responseMap.get("balance");
			logger.info(">> get balance from responseMap = " + responseBalance );
			balanceLbl.setCaption(responseStatus);
		} else {
			Notification.show(responseMap.get("message"),
					Notification.Type.ERROR_MESSAGE);
		}
		
	}

}
