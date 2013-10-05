package org.vaadin.addons.bambi;

import com.vaadin.event.MethodEventSource;
import com.vaadin.ui.Button;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Marks a {@link com.vaadin.ui.Button} to be bound to a target method that is defined in an associated view model.
 * <p/>
 * Example:
 * <pre>
 * {@code @ActionBound(to = "save")
 *   private final Button button = new Button("Save");
 * }
 * </pre>
 * The controller method must be declared with public scope and method name equivalent to that specified as the
 * "to" field. {@code Button.ClickEvent} parameter is optional.
 * <p/>
 * Example:
 * <pre>
 * {@code public void save(Button.ClickEvent event) {
 *     // event handling logic here
 *   }
 * }
 * </pre>
 * <p/>
 * For binding other UI component events, see {@link EventBound}.
 *
 * @author Michael Fazio
 * @see EventBound
 * @see com.vaadin.ui.Button.ClickEvent
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ActionBound {

    /**
     * @return the name of the method declared in the controller that will receive click events whenever the bound
     *         button clicked.
     */
    String to();

    static class ActionWiring implements Wiring {

        @Override
        public void wire(Object view, Object viewModel, Collection<Binding> bindings) {
            for (Field field : view.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(ActionBound.class)) {
                    ActionBound actionBound = field.getAnnotation(ActionBound.class);
                    MethodEventSource source = ReflectionUtil.get(field, view, MethodEventSource.class);
                    bindings.add(new MethodEventSourceBinding(source, view, viewModel, Button.ClickEvent.class, actionBound.to()));
                }
            }
        }

    }

}
