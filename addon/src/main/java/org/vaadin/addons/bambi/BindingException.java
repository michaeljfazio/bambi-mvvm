package org.vaadin.addons.bambi;

/**
 * A run-time exception that is thrown in the case that a runtime binding fails for any reason.
 *
 * @author Michael Fazio
 */
public class BindingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructor
     *
     * @param cause the cause of the reflection exception.
     */
    BindingException(Throwable cause) {
        super(cause);
    }

}
