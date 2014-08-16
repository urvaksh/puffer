/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.codeaspect.puffer.exceptions.PufferException;
import com.codeaspect.puffer.packet.AbstractPacket;

/**
 * The Class DefaultConverter as its name suggests is the Default implementation of the
 * {@link com.codeaspect.puffer.converters.Converter} interface. It contains all the basic conversions.
 */
public class DefaultConverter implements SingeltonConverter<Object> {

	/** The converters. */
	private static Map<Class<?>, SingeltonConverter<?>> converters = new HashMap<Class<?>, SingeltonConverter<?>>(6);

	static {
		converters.put(Number.class, new NumberConverter());
		converters.put(Character.class, new CharacterConverter());
		converters.put(String.class, new StringConverter());
		converters.put(Date.class, new AnnotationBasedDateConverter());
		converters.put(Boolean.class, new BooleanConverter());
		converters.put(AbstractPacket.class, new AbstractPacketConverter());
	}

	/**
	 * Gets the converter.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the converter
	 */
	@SuppressWarnings("unchecked")
	private Converter<Object> getConverter(Class<?> clazz) {
		Class<?> superClazz = clazz;
		while (superClazz != null) {
			if (converters.containsKey(superClazz))
				return (Converter<Object>) converters.get(superClazz);
			superClazz = superClazz.getSuperclass();
		}
		throw new PufferException(String.format("Unable to convert the field %s, please use a Custom Converter",
				clazz.getCanonicalName()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.codeaspect.puffer.converters.Converter#hydrate(java.lang.reflect. Field, java.lang.String)
	 */
	public Object hydrate(Field field, String message) {
		return getConverter(field.getType()).hydrate(field, message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.codeaspect.puffer.converters.Converter#stringify(java.lang.reflect .Field, java.lang.Object)
	 */
	public String stringify(Field field, Object entity) {
		return getConverter(field.getType()).stringify(field, entity);
	}

}
