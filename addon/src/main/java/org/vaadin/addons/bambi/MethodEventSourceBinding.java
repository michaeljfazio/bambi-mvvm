package org.vaadin.addons.bambi;

import com.vaadin.event.MethodEventSource;

/**
 * A {@link Binding} implementation for binding {@link MethodEventSource} to methods in an associated view model.
 *
 * @author Michael Fazio
 */
class MethodEventSourceBinding implements Binding {

    private final MethodEventSource source;
    private final Object viewModel;
    private final Class<?> event;
    private final String method;

    MethodEventSourceBinding(MethodEventSource source, Object view, Object viewModel, Class<?> event, String method) {
        this.source = source;
        this.viewModel = viewModel;
        this.event = event;
        this.method = method;
    }

    /** {@inheritDoc} */
    @Override
    public void bind() {
        source.addListener(event, viewModel, method);
    }

    /** {@inheritDoc} */
    @Override
    public void unbind() {
        source.removeListener(event, method);
    }

}
