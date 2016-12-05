/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import com.codeaspect.puffer.packet.Packet;

import java.lang.reflect.Field;

/**
 * The Class AbstractPacketConverter is a {@link com.codeaspect.puffer.converters.Converter} for {@link Packet}<i>s</i><br />.
 * The framework does not give any special treatment to Composite Abstract Packets in a mapped entity, hence this converter should be used then mapping a sub-packet.<br />
 * This is the default converter used by the framework when it finds an Object that is an instance of Packet.
 */
public class AbstractPacketConverter implements SingeltonConverter<Packet> {

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#hydrate(java.lang.reflect.Field, java.lang.String)
	 */
	public Packet hydrate(Field field, String message) {
		@SuppressWarnings("unchecked")
		Class<? extends Packet> clazz = (Class<? extends Packet>)field.getType();
		return Packet.parse(clazz, message);
	}

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#stringify(java.lang.reflect.Field, java.lang.Object)
	 */
	public String stringify(Field field, Packet message) {
		return message.stringify();
	}

}
