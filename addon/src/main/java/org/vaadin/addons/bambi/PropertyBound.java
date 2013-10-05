package org.vaadin.addons.bambi;

import com.vaadin.data.Property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.vaadin.addons.bambi.ReflectionUtil.get;

/**
 * Marks a {@link com.vaadin.data.Property.Viewer} to be bound to a {@link com.vaadin.data.Property} defined in an
 * associated view model.
 * <p/>
 * Example:
 * <pre>
 * {@code @PropertyBound(to = "property")
 *   private final TextField field = new TextField();
 * }
 * </pre>
 * The property must be defined in the associated view model with a public scope and member name equivelant to that
 * specific as the "to" field.
 * <p/>
 * Example:
 * <pre>
 * {@code public final ObjectProperty<String> property = new ObjectProperty<String>("property", String.class>());}
 * </pre>
 *
 * @author Michael Fazio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyBound {

    String to();

    static final class PropertyBoundWiring implements Wiring {

        @Override
        public void wire(Object view, Object viewModel, Collection<Binding> bindings) {
            for (Field field : view.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(PropertyBound.class)) {
                    bindings.add(new PropertyBinding(field, view, viewModel));
                }
            }
        }

    }

    static class PropertyBinding implements Binding {

        private final Field viewerField;
        private final Object view;
        private final Object viewModel;

        PropertyBinding(Field viewerField, Object view, Object viewModel) {
            this.viewerField = checkNotNull(viewerField);
            this.view = checkNotNull(view);
            this.viewModel = checkNotNull(viewModel);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void bind() {
            PropertyBound itemBound = viewerField.getAnnotation(PropertyBound.class);
            Property.Viewer viewer = get(viewerField, view, Property.Viewer.class);
            Property<?> property = get(itemBound.to(), viewModel, Property.class);
            viewer.setPropertyDataSource(property);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unbind() {
            Property.Viewer viewer = get(viewerField, view, Property.Viewer.class);
            viewer.setPropertyDataSource(null);
        }

    }

}
