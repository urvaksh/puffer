package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

public interface Converter<T> {
	
	public T hydrate(Field field, String message);
	
	public String stringify(Field field, T entity);
}
