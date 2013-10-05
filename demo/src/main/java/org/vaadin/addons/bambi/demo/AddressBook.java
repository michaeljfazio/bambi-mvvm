package org.vaadin.addons.bambi.demo;

import org.vaadin.addons.bambi.AnnotatedViewFactory;
import org.vaadin.addons.bambi.ViewContainer;

import com.vaadin.Application;
import com.vaadin.ui.Window;

/**
 * Bambi MVVM Demo Application
 * 
 * @author Michael Fazio
 */
public class AddressBook extends Application {

	private static final long serialVersionUID = 1L;

	private Window window;

	/** {@inheritDoc} */
	@Override
	public void init() {
		window = new Window("Bambi Demo Application - Address Book");
		ViewContainer<AddressBookView> view = new AnnotatedViewFactory().materialize(AddressBookView.class);
		window.setContent(view);
		setMainWindow(window);
	}

}
