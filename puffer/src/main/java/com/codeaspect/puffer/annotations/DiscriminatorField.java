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
 * The Annotation DiscriminatorField allows setting of the field name and
 * {@link com.codeaspect.puffer.annotations.DiscriminatorValue} used to Dynamically create classes based on
 * Discriminator field values.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscriminatorField {

	/**
	 * The name of the field  to be used as a discriminator.
	 */
	public String fieldName();

	/**
	 * List of {@link DiscriminatorValue} to map allowed values and target classes.
	 */
	public DiscriminatorValue[] values();

}
