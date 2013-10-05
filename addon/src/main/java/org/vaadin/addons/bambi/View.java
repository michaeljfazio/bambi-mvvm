package org.vaadin.addons.bambi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a component as a view that is bound to a specified view model.
 * <p/>
 * Example:
 * 
 * <pre>
 * {@code @View(model = CustomerViewModel.class, cached = false)
 * public class CustomerView extends CustomComponent {
 *   ...
 * }
 * }
 * </pre>
 * 
 * @see ViewFactory
 * @author Michael Fazio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface View {

	/**
	 * The view model class that this view shall be bound to.
	 * 
	 * @return the view model class.
	 */
	Class<?> model();

}
