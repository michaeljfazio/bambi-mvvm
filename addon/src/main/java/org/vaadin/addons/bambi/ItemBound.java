package org.vaadin.addons.bambi;

import com.vaadin.data.Item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.vaadin.addons.bambi.ReflectionUtil.get;

/**
 * Marks a {@link com.vaadin.data.Item.Viewer} to be bound to a {@link com.vaadin.data.Item} defined in an
 * associated view model.
 * <p/>
 * Example:
 * <pre>
 * {@code @ItemBound(to = "item")
 *   private final Form form = new Form();
 * }
 * </pre>
 * The item must be defined in the associated view model with a public scope and member name equivelant to that
 * specific as the "to" field.
 * <p/>
 * Example:
 * <pre>
 * {@code public final BeanItem<BeanType> item = new BeanItem<BeanType>();}
 * </pre>
 *
 * @author Michael Fazio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ItemBound {

    String to();

    static class ItemBoundWiring implements Wiring {

        /**
         * {@inheritDoc}
         */
        @Override
        public void wire(Object view, Object viewModel, Collection<Binding> bindings) {
            for (Field field : view.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(ItemBound.class)) {
                    bindings.add(new ItemBinding(field, view, viewModel));
                }
            }
        }

    }

    static class ItemBinding implements Binding {

        private final Field viewerField;
        private final Object view;
        private final Object viewModel;

        ItemBinding(Field viewerField, Object view, Object viewModel) {
            this.viewerField = checkNotNull(viewerField);
            this.view = checkNotNull(view);
            this.viewModel = checkNotNull(viewModel);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void bind() {
            ItemBound itemBound = viewerField.getAnnotation(ItemBound.class);
            Item.Viewer viewer = get(viewerField, view, Item.Viewer.class);
            Item item = get(itemBound.to(), viewModel, Item.class);
            viewer.setItemDataSource(item);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unbind() {
            Item.Viewer viewer = get(viewerField, view, Item.Viewer.class);
            viewer.setItemDataSource(null);
        }

    }

}
