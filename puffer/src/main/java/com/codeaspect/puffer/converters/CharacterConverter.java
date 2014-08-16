/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

/**
 * The Class CharacterConverter is a {@link com.codeaspect.puffer.converters.Converter} that converts between Strings and Characters.<br />
 */
public class CharacterConverter implements SingeltonConverter<Character> {

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#hydrate(java.lang.reflect.Field, java.lang.String)
	 */
	public Character hydrate(Field field, String message) {
		return message.charAt(0);
	}

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#stringify(java.lang.reflect.Field, java.lang.Object)
	 */
	public String stringify(Field field, Character message) {
		return message==null?" ":message.toString();
	}

}
