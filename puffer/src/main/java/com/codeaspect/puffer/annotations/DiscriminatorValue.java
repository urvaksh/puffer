/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.annotations;

import com.codeaspect.puffer.packet.AbstractPacket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation DiscriminatorValue allows specification of which values should map to which target classes.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscriminatorValue {
	
	/**
	 * The String values of the Field in the fixed length message.
	 */
	public String[] fieldValues();

	/**
	 * The target class if any of the values match.
	 */
	public Class<? extends AbstractPacket> targetClass();
}
