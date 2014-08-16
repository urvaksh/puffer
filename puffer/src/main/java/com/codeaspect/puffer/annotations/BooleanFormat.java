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
 * The Annotation BooleanFormat allows specification of the String values that represent true or false.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BooleanFormat {

	/**
	 * The value that represents true.
	 */
	public String trueValue() default "Y";
	
	/**
	 * The value that represents false.
	 */
	public String falseValue() default "N";
	
	/**
	 * The Default value, if neither the true or false value match.
	 */
	public boolean defaultValue() default false;
}
