package org.vaadin.addons.bambi.demo;

import static com.vaadin.ui.Window.Notification.TYPE_HUMANIZED_MESSAGE;

import java.io.Serializable;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

/**
 * The address book view model that is bound to the user interface view.
 *
 * @author Michael Fazio
 */
@SuppressWarnings("rawtypes")
public class AddressBookViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public final Container addresses = new BeanItemContainer<Address>(Address.class);
    
	public final ObjectProperty<BeanItem> selected = new ObjectProperty<BeanItem>(null, BeanItem.class);
	
    public void selectAddress(Property.ValueChangeEvent event) {
        Table table = (Table)event.getProperty();
        Item selectedItem = table.getItem(table.getValue());
        selected.setValue(selectedItem);
    }

    public void addAddress(Button.ClickEvent event) {
        Address newAddress = new Address();
        Item item = addresses.addItem(newAddress);
        selected.setValue(item);
        event.getComponent().getWindow().showNotification("Address Added",TYPE_HUMANIZED_MESSAGE);
    }

    public void removeAddress(Button.ClickEvent event) {
        BeanItem item = (BeanItem)selected.getValue();
        Address address = (Address)item.getBean();
        if(addresses.removeItem(address)) {
        	event.getComponent().getWindow().showNotification("Address Removed", TYPE_HUMANIZED_MESSAGE);
        }
    }

}
