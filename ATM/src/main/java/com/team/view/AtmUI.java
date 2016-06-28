package com.team.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringUI(path = "/")
@Theme("valo")
public class AtmUI extends UI {

	private ATMForm form = new ATMForm();
	
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		form.setVisible(true);
		layout.addComponent(form);
		layout.setMargin(true);
		layout.setSpacing(true);
		this.setContent(layout);
		
	}

}
