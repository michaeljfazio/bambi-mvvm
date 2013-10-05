package org.vaadin.addons.bambi;

import com.vaadin.data.Property;
import com.vaadin.event.MethodEventSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import static org.vaadin.addons.bambi.ReflectionUtil.get;

/**
 * Marks a {@link Property} to be bound to a target method that is defined in an associated view model. The target
 * method will be called whenever the property value is changed.
 * <p/>
 * Example:
 * <pre>
 * {@code @ValueChangeBound(to = "changed")
 *   private final TextField field = new TextField();
 * }
 * </pre>
 * The view model method must be declared with public scope and method name equivalent to that specified as the
 * "to" field. {@code Property.ValueChangeEvent} parameter is optional.
 * <p/>
 * Example:
 * <pre>
 * {@code public void changed(Propert.ValueChangeEvent event) {
 *     // event handling logic here
 *   }
 * }
 * </pre>
 * <p/>
 *
 * @author Michael Fazio
 * @see ValueChangeBound
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValueChangeBound {

    String to();

    static class ValueChangeWiring implements Wiring {

        @Override
        public void wire(Object view, Object viewModel, Collection<Binding> bindings) {
            for (Field field : view.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(ValueChangeBound.class)) {
                    ValueChangeBound valueChangeBound = field.getAnnotation(ValueChangeBound.class);
                    MethodEventSource source = get(field, view, MethodEventSource.class);
                    bindings.add(new MethodEventSourceBinding(source, view, viewModel, Property.ValueChangeEvent.class, valueChangeBound.to()));
                }
            }
        }

    }
}
