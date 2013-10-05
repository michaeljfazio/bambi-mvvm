package org.vaadin.addons.bambi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.ComponentContainer;

/**
 * Implementation of {@link ViewBinding} that binds an annotated view to a view
 * model.
 * 
 * @author Michael Fazio
 */
public class AnnotatedViewBinding implements ViewBinding {

	private static final Set<Wiring> WIRINGS = new ImmutableSet.Builder<Wiring>()
			.add(new PropertyBound.PropertyBoundWiring())
			.add(new ContainerBound.ContainerBoundWiring())
			.add(new ItemBound.ItemBoundWiring())
			.add(new EventBound.EventWiring())
			.add(new ActionBound.ActionWiring())
			.add(new ValueChangeBound.ValueChangeWiring()).build();

	private final List<Binding> bindings = new LinkedList<Binding>();

	private transient final ComponentContainer view;

	private transient final Object viewModel;

	/**
	 * Constructor
	 * 
	 * @param view
	 *            an instance of a class that defines a view with bindable UI
	 *            elements.
	 * @param viewModel
	 *            the view model that backs the UI elements presented in the
	 *            bound view.
	 */
	public AnnotatedViewBinding(ComponentContainer view, Object viewModel) {
		this.view = view;
		this.viewModel = viewModel;
		discover();
	}

	/**
	 * Discovers all the declared bindings in the view and wires them up.
	 */
	private void discover() {
		for (Wiring wiring : WIRINGS) {
			wiring.wire(view, viewModel, bindings);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind() {
		synchronized (bindings) {
			for (Binding binding : bindings) {
				binding.bind();
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unbind() {
		synchronized (bindings) {
			Iterator<Binding> iterator = bindings.iterator();
			while (iterator.hasNext()) {
				iterator.next().unbind();
				iterator.remove();
			}
		}
	}

}
