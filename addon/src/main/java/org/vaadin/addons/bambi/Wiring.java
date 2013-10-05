package org.vaadin.addons.bambi;

import java.util.Collection;

/**
 * Defines the logic required to bind a view element to its view-model counterpart as defined by a binding annotation.
 *
 * @author Michael Fazio
 */
interface Wiring {

    /**
     * Wires the specified view element to its view-model counterpart.
     *
     * @param view the view.
     * @param viewModel the view model.
     * @param bindings a collection to which applicable bindings will be added.
     */
    void wire(Object view, Object viewModel, Collection<Binding> bindings);

}
