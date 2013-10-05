package org.vaadin.addons.bambi.demo;

import static org.vaadin.addons.bambi.demo.Address.AREACODE_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.CITY_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.COUNTRY_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.LINE1_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.LINE2_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.NAME_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.STATE_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.SURNAME_PROPERTY;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

/**
 * @author Michael Fazio
 */
public class AddressBookForm extends Form implements FormFieldFactory {

	private static final long serialVersionUID = 1L;

	private final GridLayout layout = new GridLayout(4, 5);

	public AddressBookForm() {
		setCaption("Address");
		setLayout(layout);
		setFormFieldFactory(this);
		layout.setSpacing(true);
		layout.setMargin(true);
	}

	/** {@inheritDoc} */
	@Override
	protected void attachField(Object propertyId, Field field) {
		if (propertyId.equals(NAME_PROPERTY)) {
			layout.addComponent(field, 0, 0, 1, 0);
		} else if (propertyId.equals(SURNAME_PROPERTY)) {
			layout.addComponent(field, 2, 0, 3, 0);
		} else if (propertyId.equals(LINE1_PROPERTY)) {
			layout.addComponent(field, 0, 1, 3, 1);
		} else if (propertyId.equals(LINE2_PROPERTY)) {
			layout.addComponent(field, 0, 2, 3, 2);
		} else if (propertyId.equals(CITY_PROPERTY)) {
			layout.addComponent(field, 0, 3);
		} else if (propertyId.equals(STATE_PROPERTY)) {
			layout.addComponent(field, 1, 3);
		} else if (propertyId.equals(AREACODE_PROPERTY)) {
			layout.addComponent(field, 2, 3);
		} else if (propertyId.equals(COUNTRY_PROPERTY)) {
			layout.addComponent(field, 0, 4, 2, 4);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		if (propertyId.equals(NAME_PROPERTY)) {
			TextField field = new TextField("Name");
			field.setNullRepresentation("");
			field.setRequired(true);
			return field;
		} else if (propertyId.equals(SURNAME_PROPERTY)) {
			TextField field = new TextField("Surname");
			field.setNullRepresentation("");
			field.setRequired(true);
			return field;
		} else if (propertyId.equals(LINE1_PROPERTY)) {
			TextField field = new TextField("Line 1");
			field.setNullRepresentation("");
			return field;
		} else if (propertyId.equals(LINE2_PROPERTY)) {
			TextField field = new TextField("Line 2");
			field.setNullRepresentation("");
			return field;
		} else if (propertyId.equals(CITY_PROPERTY)) {
			TextField field = new TextField("City");
			field.setNullRepresentation("");
			return field;
		} else if (propertyId.equals(STATE_PROPERTY)) {
			TextField field = new TextField("State");
			field.setNullRepresentation("");
			return field;
		} else if (propertyId.equals(AREACODE_PROPERTY)) {
			TextField field = new TextField("Area Code");
			field.setNullRepresentation("");
			return field;
		} else if (propertyId.equals(COUNTRY_PROPERTY)) {
			TextField field = new TextField("Country");
			field.setNullRepresentation("");
			return field;
		}
		return null;
	}

}
