package org.vaadin.addons.bambi.demo;

import static org.vaadin.addons.bambi.demo.Address.AREACODE_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.CITY_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.COUNTRY_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.LINE1_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.LINE2_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.NAME_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.STATE_PROPERTY;
import static org.vaadin.addons.bambi.demo.Address.SURNAME_PROPERTY;

import org.vaadin.addons.bambi.ActionBound;
import org.vaadin.addons.bambi.ContainerBound;
import org.vaadin.addons.bambi.PropertyBound;
import org.vaadin.addons.bambi.ValueChangeBound;
import org.vaadin.addons.bambi.View;

import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

/**
 * Represents the user interface view class that is bound to the view model.
 * 
 * @author Michael Fazio
 */
@View(model = AddressBookViewModel.class)
public class AddressBookView extends CustomComponent implements Handler {

	private static final long serialVersionUID = 1L;

	@ActionBound(to = "addAddress")
	private final Button addButton = new Button("New Address");

	@ActionBound(to = "removeAddress")
	private final Button removeButton = new Button("Remove Address");

	@PropertyBound(to = "selected")
	private final AddressBookForm form = new AddressBookForm();

	@ContainerBound(to = "addresses", columns = { NAME_PROPERTY,
			SURNAME_PROPERTY, LINE1_PROPERTY, LINE2_PROPERTY, CITY_PROPERTY,
			STATE_PROPERTY, AREACODE_PROPERTY, COUNTRY_PROPERTY })
	@ValueChangeBound(to = "selectAddress")
	private final Table table = new Table();

	private final Action commitAction = new ShortcutAction("ENTER",
			ShortcutAction.KeyCode.ENTER, null);

	public AddressBookView() {
		VerticalSplitPanel splitPanel = new VerticalSplitPanel();
		VerticalLayout topLayout = new VerticalLayout();
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);
		buttonPanel.setMargin(true);
		buttonPanel.addComponent(addButton);
		buttonPanel.addComponent(removeButton);
		topLayout.addComponent(buttonPanel);
		topLayout.addComponent(table);
		topLayout.setExpandRatio(table, 1.0f);
		topLayout.setSizeFull();
		table.setSizeFull();
		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true);
		splitPanel.setFirstComponent(topLayout);
		splitPanel.setSecondComponent(form);
		splitPanel.setSplitPosition(60.0f, UNITS_PERCENTAGE);
		setCompositionRoot(splitPanel);
		setSizeFull();
	}

	/** {@inheritDoc} */
	@Override
	public void attach() {
		super.attach();
		form.addActionHandler(this);
	}

	/** {@inheritDoc} */
	@Override
	public void detach() {
		form.removeActionHandler(this);
		super.detach();
	}

	/** {@inheritDoc} */
	@Override
	public Action[] getActions(Object target, Object sender) {
		return new Action[] { commitAction };
	}

	/** {@inheritDoc} */
	@Override
	public void handleAction(Action action, Object sender, Object target) {
		if (action == commitAction) {
			form.commit();
		}
	}

}
