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
 * The Annotation PacketList allows specification of the Mapping data for the underlying {@link java.util.List}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketList {

	/**
	 * The number of Packets.
	 */
	public int packetCount() default -1;
	
	/**
	 * The field specifying the number of packets.
	 */
	public String packetCountField() default "";
	
	/**
	 * The length of each Packet.
	 */
	public int packetLength() default -1;
	
	/**
	 * The field specifying the length of each packet.
	 */
	public String packetLengthField() default "";
	
}

