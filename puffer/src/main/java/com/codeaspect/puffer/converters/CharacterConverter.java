package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

public class CharacterConverter implements SingeltonConverter<Character> {

	public Character hydrate(Field field, String message) {
		return message.charAt(0);
	}

	public String stringify(Field field, Character message) {
		return message==null?" ":message.toString();
	}

}
