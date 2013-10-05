package org.vaadin.addons.bambi;

/**
 * Describes a binding between a view element and its associated view model.
 *
 * @author Michael Fazio
 */
interface Binding {

    /**
     * Activates the binding.
     */
    void bind();

    /**
     * De-activates the binding.
     */
    void unbind();

}
