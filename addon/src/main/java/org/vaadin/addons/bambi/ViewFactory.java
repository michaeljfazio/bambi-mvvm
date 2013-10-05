package org.vaadin.addons.bambi;

import com.vaadin.ui.ComponentContainer;

/**
 * Defines a factory that materializes view instances that are bound to a view
 * model. Only components annotated with the {@link View} annotation are able to
 * be materialized with a {@link ViewFactory}.
 * 
 * @author Michael Fazio
 */
public interface ViewFactory {

	/**
	 * Materializes a view.
	 * 
	 * @param viewClazz
	 *            the view class.
	 * @return the materialized view instance.
	 * @throws BindingException
	 *             if the view cannot be materialized for any reason.
	 */
	public <T extends ComponentContainer> ViewContainer<T> materialize(
			Class<T> viewClazz);

}
