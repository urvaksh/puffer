package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.codeaspect.puffer.exceptions.PufferException;
import com.codeaspect.puffer.packet.AbstractPacket;

public class DefaultConverter implements SingeltonConverter<Object> {

	private static Map<Class<?>, Converter<?>> converters = new HashMap<Class<?>, Converter<?>>(6);

	static {
		converters.put(Number.class, new NumberConverter());
		converters.put(Character.class, new CharacterConverter());
		converters.put(String.class, new StringConverter());
		converters.put(Date.class, new AnnotationBasedDateConverter());
		converters.put(Boolean.class, new BooleanConverter());
		converters.put(AbstractPacket.class, new AbstractPacketConverter());
	}

	@SuppressWarnings("unchecked")
	private Converter<Object> getConverter(Class<?> clazz) {
		Class<?> superClazz = clazz;
		while (superClazz != null) {
			if (converters.containsKey(superClazz))
				return (Converter<Object>) converters.get(superClazz);
			superClazz = superClazz.getSuperclass();
		}
		throw new PufferException(
				String.format(
						"Unable to convert the field %s, please use a Custom Converter",
						clazz.getCanonicalName()));
	}

	public Object hydrate(Field field, String message) {
		return getConverter(field.getType()).hydrate(field, message);
	}

	public String stringify(Field field, Object entity) {
		return getConverter(field.getType()).stringify(field, entity);
	}

}
