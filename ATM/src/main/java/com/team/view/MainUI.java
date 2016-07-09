package com.team.view;

import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
@SpringUI(path = "/")
@Theme("valo")
public class MainUI extends UI {

	public static Logger logger = LoggerFactory.getLogger(MainUI.class);
	// private ATMForm form = new ATMForm();

	@Override
	protected void init(VaadinRequest request) {

		new Navigator(this, this);

		this.getNavigator().addView(LoginPage.NAME, LoginPage.class);
		this.getNavigator().setErrorView(LoginPage.class);

		Page.getCurrent().addUriFragmentChangedListener(new UriFragmentChangedListener() {
			@Override
			public void uriFragmentChanged(UriFragmentChangedEvent event) {

				routeTo(event.getUriFragment());
			}
		});
		
		routeTo("");
		/*
		 * final VerticalLayout layout = new VerticalLayout();
		 * form.setVisible(true); layout.addComponent(form);
		 * layout.setMargin(true); layout.setSpacing(true);
		 * this.setContent(layout);
		 */

	}

	private void routeTo(String route) {

		if ("!Register".equals(route)) {
			this.getNavigator().addView(RegisterPage.NAME, RegisterPage.class);
			this.getNavigator().navigateTo(RegisterPage.NAME);
		} else if ("!Atm".equals(route)) {
			this.getNavigator().addView(ATMPage.NAME, ATMPage.class);
			this.getNavigator().navigateTo(ATMPage.NAME);
		} else {
			
			this.getNavigator().navigateTo(LoginPage.NAME);
		}

	}
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

}
