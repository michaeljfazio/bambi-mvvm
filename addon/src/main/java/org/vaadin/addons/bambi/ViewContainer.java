package org.vaadin.addons.bambi;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;

/**
 * A component that acts as a parent to a user-defined view that is materialized
 * using {@link ViewFactory#materialize(Class)}. The component is necessary to
 * facilitate binding and un-binding of a view to its view model when the view
 * is attached and detached.
 * <p>
 * The underlying user-defined view is accessible via the
 * {@link ViewContainer#getViewComponent()} accessor.
 * 
 * @author Michael Fazio
 * @param <T>
 *            the view class type.
 */
public final class ViewContainer<T extends ComponentContainer> extends
		CustomComponent {

	private static final long serialVersionUID = 1L;

	public static <T extends ComponentContainer> ViewContainer<T> create(
			T view, Object model) {
		return new ViewContainer<T>(view, model);
	}

	private final T view;
	private transient final ViewBinding binding;

	private ViewContainer(T view, Object model) {
		this.binding = new AnnotatedViewBinding(view, model);
		this.view = view;
		setCompositionRoot(view);
		setSizeFull();
	}

	/**
	 * @return the view instance that is held by this {@code ViewContainer}.
	 */
	public T getViewComponent() {
		return view;
	}

	/** {@inheritDoc} */
	@Override
	public void attach() {
		super.attach();
		binding.bind();
	}

	/** {@inheritDoc} */
	@Override
	public void detach() {
		binding.unbind();
		super.detach();
	}

}