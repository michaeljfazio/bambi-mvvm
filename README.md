# Introduction

Bambi is a lightweight [MVVM](http://en.wikipedia.org/wiki/Model_View_ViewModel#Java_MVVM_frameworks) add-on for Vaadin web applications. Bambi uses a declarative style annotation-driven approach to UI binding. The framework attempts to adhere to basic MVVM principles and allows users to develop expressive UI with highly testable view models. One of the primary design goals of the Bambi framework is to enable the creation of minimalist, and highly readable code whilst not impeding the ability to deliver rich client user interfaces.

The overall structure borrows heavily from existing MVC/MVVM frameworks, including:

 * [Clara](http://vaadin.com/directory#addon/)
 * [Panantir Cinch](https://github.com/palantir/Cinch)
 * [Microsoft WPF MVVM](http://msdn.microsoft.com/en-us/magazine/dd419663.aspx)
 * [Vaadin FormBinder](http://code.google.com/p/formbinder/)
 * [Magellan Framework](http://code.google.com/p/magellan-framework/)

# Current Build Status

Bambi-MVVM uses Travis CI for continuous integration.

[![Build Status](https://travis-ci.org/S73417H/bambi-mvvm.png)](https://travis-ci.org/S73417H/bambi-mvvm)

# Download

This add-on is made available via the official Vaadin add-on directory using the following link: https://vaadin.com/directory#addon/bambi-mvvm.

Alternatively see the [releases section](https://github.com/S73417H/bambi-mvvm/releases) or checkout the [source code](https://github.com/S73417H/bambi-mvvm.git) for yourself.

# Getting Started

## Prerequisites

  - Java 5
  - Maven 2.2
  - A working internet connection

## Editing the code in an IDE

  The project-name add-on project can be imported in any IDE that supports Maven.

## Trying out the demo

  1. Install the parent pom.

    `mvn install -f parent/pom.xml`

  2. Compile and install the entire project:

    `mvn install`

  3. Start the built-in Jetty web server:

    `cd demo`

    `mvn jetty:run`

  4. Open your favorite web browser and point it to:

    http://localhost:8080/demo/

## Reading the manual

  1. Generate the manual:

    `cd manual`
    `mvn docbkx:generate-html`

  2. Open the file manual/target/docbkx/html/manual.html
     in your favorite web browser.

## Building the add-on

  1. Run the following command:

    `mvn package assembly:assembly`

# Supported Annotations

The following table contains a list of currently supported Bambi MVVM annotations:

| Annotations                                                                                                                              | Description                                                                                                                                                                                |
| ---------------------------------------------------------------------------------------------------------------------------------------- |:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:| 
| [@View](https://github.com/S73417H/bambi-mvvm/blob/master/addon/src/main/java/org/vaadin/addons/bambi/View.java)                         | Applied to a view class that shall be bound to a view model.                                                                                                                               |
| [@PropertyBound](https://github.com/S73417H/bambi-mvvm/blob/master/addon/src/main/java/org/vaadin/addons/bambi/PropertyBound.java)       | Applied to any UI element that implements the `Property.Viewer` interface (e.g. `TextField`). Binds the element to a corresponding property declared in the view model.                    |
| [@ItemBound](https://github.com/S73417H/bambi-mvvm/blob/master/addon/src/main/java/org/vaadin/addons/bambi/ItemBound.java)               | Applied to any UI element that implements the `Item.Viewer` interface (e.g. `Form`). Binds the element to a corresponding `Item` declared in the view model.                               |
| [@ContainerBound](https://github.com/S73417H/bambi-mvvm/blob/master/addon/src/main/java/org/vaadin/addons/bambi/ContainerBound.java)     | Applied to any UI element that implements the `Container.Viewer` interface (e.g. `Table`). Binds the element to a corresponding `Container` declared in the view model.                    |
| [@ActionBound](https://github.com/S73417H/bambi-mvvm/blob/master/addon/src/main/java/org/vaadin/addons/bambi/ActionBound.java)           | Applied to `Button` elements in the UI. Binds the button click event to a method in the corresponding view model.                                                                          |
| [@EventBound](https://github.com/S73417H/bambi-mvvm/blob/master/addon/src/main/java/org/vaadin/addons/bambi/EventBound.java)             | Applied to any UI `Component`. Binds a specified event to a corresponding event handler method in the corresponding view model.                                                            |
| [@ValueChangeBound](https://github.com/S73417H/bambi-mvvm/blob/master/addon/src/main/java/org/vaadin/addons/bambi/ValueChangeBound.java) | Applied to any UI element that implements the `ValueChangeListener` interface (e.g. `TextField`, `Table`, etc). Binds the value change event to a method in the corresponding view model.  |

# Example

## View

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

	...
	}

## View Model

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

## Binding View to ViewModel

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

# Authors

[Michael Fazio](http://www.linkedin.com/pub/michael-fazio/b/b20/a23)

# License

Bambi is released under Apache 2.0 license.

Copyright 2012 Michael Fazio
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
file except in compliance with the License. You may obtain a copy of the License at
 
http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the specific language governing
permissions and limitations under the License.
