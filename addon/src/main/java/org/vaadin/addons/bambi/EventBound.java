package org.vaadin.addons.bambi;

import com.vaadin.event.MethodEventSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import static org.vaadin.addons.bambi.ReflectionUtil.get;

/**
 * Marks a {@link MethodEventSource} to be bound to a target method that is defined in an associated view model. This
 * particular binding annotation is intended for use where no other binding type applies.
 * <p/>
 * Example:
 * <pre>
 * {@code @EventBound(to = "doSomething", class = MyComponentEvent.class)
 *   private final MyComponent myComponent = new MyComponent();
 * }
 * </pre>
 * The controller method must be declared with public scope and method name equivalent to that specified as the
 * "to" field. The specified event parameter is optional.
 * <p/>
 * Example:
 * <pre>
 * {@code public void doSomething(MyComponentEvent event) {
 *     // event handling logic here
 *   }
 * }
 * </pre>
 *
 * @author Michael Fazio
 * @see EventBound
 * @see com.vaadin.ui.Button.ClickEvent
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EventBound {

    String to();

    Class<?> event();

    static class EventWiring implements Wiring {

        @Override
        public void wire(Object view, Object viewModel, Collection<Binding> bindings) {
            for (Field field : view.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(EventBound.class)) {
                    EventBound eventBound = field.getAnnotation(EventBound.class);
                    MethodEventSource source = get(field, view, MethodEventSource.class);
                    bindings.add(new MethodEventSourceBinding(source, view, viewModel, eventBound.event(), eventBound.to()));
                }
            }
        }

    }

}
