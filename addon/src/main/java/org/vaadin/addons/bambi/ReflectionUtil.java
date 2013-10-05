package org.vaadin.addons.bambi;

import java.lang.reflect.Field;

/**
 * @author Michael Fazio
 */
class ReflectionUtil {

	static <T> T get(Field field, Object object, Class<T> type) {
		try {
			field.setAccessible(true);
			return type.cast(field.get(object));
		} catch (IllegalAccessException e) {
			throw new BindingException(e);
		} catch (ClassCastException e) {
			throw new BindingException(e);
		}
	}

	static <T> T get(String name, Object object, Class<T> type) {
		try {
			Field field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return get(field, object, type);
		} catch (NoSuchFieldException e) {
			throw new BindingException(e);
		}
	}

	@SuppressWarnings("unchecked")
	static <T> T newInstance(Class<?> clazz) {
		try {
			return (T) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new BindingException(e);
		} catch (IllegalAccessException e) {
			throw new BindingException(e);
		}
	}

	/**
	 * Private constructor to prevent instantiation.
	 */
	private ReflectionUtil() {

	}

}
