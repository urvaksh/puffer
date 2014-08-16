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

import com.codeaspect.puffer.converters.Converter;
import com.codeaspect.puffer.converters.DefaultConverter;
import com.codeaspect.puffer.enums.NumericSign;
import com.codeaspect.puffer.enums.Side;

/**
 * The Annotation PacketMessage allows specification of the Mapping metadata for the Packet.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketMessage {

	/**
	 * The position of the packet in the fixed length message.
	 */
	public int position();

	/**
	 * The length of the packet in the fixed length message.
	 */
	public int length();

	/**
	 * Description. (optional, purely for code documentation)
	 */
	public String description() default "";

	/**
	 * The padding character used to pad the field when not completely filled.
	 * Defaults to ' ' => a space
	 */
	public char padChar() default ' ';

	/**
	 * The {@link com.codeaspect.puffer.enums.Side} on which the padding character is applied.<br />
	 * Numbers will usually be padded on the left with a '0' character, Strings will be padded on the right with a ' '
	 * character.
	 * Defaults to right padding
	 */
	public Side paddingSide() default Side.RIGHT;

	/**
	 * The {@link com.codeaspect.puffer.converters.Converter} class to convert the value from a String to the datataype
	 * and back.<br />
	 * Most of the basic conversions are handled in {@link com.codeaspect.puffer.converters.DefaultConverter} which is
	 * the default value and hence may not need to be specified. If the conversion is not supported, you may need to
	 * write your own {@link com.codeaspect.puffer.converters.Converter}
	 */
	public Class<? extends Converter<?>> converter() default DefaultConverter.class;

	/**
	 * The {@link com.codeaspect.puffer.enums.NumericSign} in case of a {@link java.lang.Number}
	 */
	public NumericSign numericSign() default NumericSign.DEFAULT;
}
