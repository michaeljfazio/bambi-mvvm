package org.vaadin.addons.bambi;

import static org.vaadin.addons.bambi.ReflectionUtil.get;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import com.vaadin.data.Container;
import com.vaadin.ui.Table;

/**
 * Marks a {@link com.vaadin.data.Container.Viewer} to be bound to a
 * {@link com.vaadin.data.Container} defined in an associated view model.
 * <p/>
 * Example:
 * 
 * <pre>
 * {
 * 	&#064;code
 * 	&#064;ContainerBound(to = &quot;container&quot;)
 * 	private final Table table = new Table();
 * }
 * </pre>
 * 
 * The container must be defined in the associated view model with a public
 * scope and member name equivelant to that specific as the "to" field.
 * <p/>
 * Example:
 * 
 * <pre>
 * {
 * 	&#064;code
 * 	public final Container container = new BeanItemContainer&lt;BeanType&gt;();
 * }
 * </pre>
 * 
 * @author Michael Fazio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ContainerBound {

	/**
	 * @return the name of the container declared in the view model that will
	 *         serve as the data source to the bound container viewer.
	 */
	String to();

	/**
	 * This is an optional setting that can be used if the container bound
	 * element is of type {@link com.vaadin.ui.Table}. It allows the visible
	 * columns and their order to be set. This feature was added to overcome
	 * idiosyncratic behavior which makes the Vaadin table sensitive to the
	 * order in which the container data source and visible columns are set. If
	 * the bound element is not of type {@link com.vaadin.ui.Table} then the
	 * parameter is ignored.
	 * <p>
	 * This feature may be removed or replaced by a more specific annotation in
	 * the future.
	 * 
	 * @return the id's of each visible column to be shown in the table.
	 */
	String[] columns() default {};

	static class ContainerBoundWiring implements Wiring {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void wire(Object view, Object viewModel,
				Collection<Binding> bindings) {
			for (Field field : view.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(ContainerBound.class)) {
					bindings.add(new ContainerBinding(field, view, viewModel));
				}
			}
		}

	}

	static class ContainerBinding implements Binding {

		private final Field viewerField;
		private final Object view;
		private final Object viewModel;

		ContainerBinding(Field viewerField, Object view, Object viewModel) {
			this.viewerField = viewerField;
			this.view = view;
			this.viewModel = viewModel;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void bind() {
			ContainerBound containerBound = viewerField
					.getAnnotation(ContainerBound.class);
			Container.Viewer viewer = get(viewerField, view,
					Container.Viewer.class);
			Container container = get(containerBound.to(), viewModel,
					Container.class);
			viewer.setContainerDataSource(container);

			// Visible columns must be set after the container data source as
			// com.vaadin.ui.Table will throw a runtime exception otherwise.
			// to be set before column visibility.
			if (viewer instanceof Table && containerBound.columns().length != 0) {
				((Table) viewer).setVisibleColumns(containerBound.columns());
			}

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void unbind() {
			Container.Viewer viewer = get(viewerField, view,
					Container.Viewer.class);
			viewer.setContainerDataSource(null);
		}

	}

}
