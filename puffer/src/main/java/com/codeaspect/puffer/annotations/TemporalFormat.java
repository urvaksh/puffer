/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation TemporalFormat allows specification of the Date Format and is used by {@link com.codeaspect.puffer.converters.AnnotationBasedDateConverter} and {@link com.codeaspect.puffer.converters.DefaultConverter}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TemporalFormat {
	
	/**
	 * The date format as specified in {@link java.text.DateFormat}.
	 */
	public String value();
}
