/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

/**
 * The Interface Converter allows specification of how an Object (of type
 * specified using the generic type T) should be converted to and from a String.
 */
public interface Converter<T> {

	/**
	 * The hydrate method provides the implementation to convert the String into the Object.
	 *
	 * @param field the field to which the string will be mapped
	 * @param message the string that needs to be converted
	 * @return the converted Object
	 */
	public T hydrate(Field field, String message);

	
	/**
	 * The stringify method converts the Object back to a String.
	 *
	 * @param field the field to which the contains the Object
	 * @param entity the value
	 * @return a string representation of the Object
	 */
	public String stringify(Field field, T entity);
}
