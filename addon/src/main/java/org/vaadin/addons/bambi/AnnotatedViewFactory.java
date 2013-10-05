package org.vaadin.addons.bambi;

import static org.vaadin.addons.bambi.ReflectionUtil.newInstance;

import com.vaadin.ui.ComponentContainer;

/**
 * Implementation of {@link ViewFactory} that reflectively instantiates views
 * that have been annotated with the {@link View} annotation. The factory also
 * instantiates an instance of the view's associated view model. A new instance
 * of both the view and view model are instantiated with each call to
 * {@link #materialize(Class)}.
 * 
 * @author Michael Fazio
 */
public class AnnotatedViewFactory implements ViewFactory {

	/** {@inheritDoc} */
	@Override
	public <T extends ComponentContainer> ViewContainer<T> materialize(
			Class<T> viewClazz) {
		if (!viewClazz.isAnnotationPresent(View.class)) {
			throw new IllegalArgumentException(
					"The specified view class is invalid. Did you forget to add the @View annotation?");
		}

		Class<?> modelClazz = viewClazz.getAnnotation(View.class).model();
		T view = newInstance(viewClazz);
		Object model = newInstance(modelClazz);
		return ViewContainer.create(view, model);
	}
}
