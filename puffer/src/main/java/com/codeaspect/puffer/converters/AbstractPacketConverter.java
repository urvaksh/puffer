/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

import com.codeaspect.puffer.packet.AbstractPacket;

/**
 * The Class AbstractPacketConverter is a {@link com.codeaspect.puffer.converters.Converter} for {@link com.codeaspect.puffer.packet.AbstractPacket}<i>s</i><br />.
 * The framework does not give any special treatment to Composite Abstract Packets in a mapped entity, hence this converter should be used then mapping a sub-packet.<br />
 * This is the default converter used by the framework when it finds an Object that is an instance of AbstractPacket.
 */
public class AbstractPacketConverter implements SingeltonConverter<AbstractPacket> {

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#hydrate(java.lang.reflect.Field, java.lang.String)
	 */
	public AbstractPacket hydrate(Field field, String message) {
		@SuppressWarnings("unchecked")
		Class<? extends AbstractPacket> clazz = (Class<? extends AbstractPacket>)field.getType();
		return AbstractPacket.parse(clazz, message);
	}

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#stringify(java.lang.reflect.Field, java.lang.Object)
	 */
	public String stringify(Field field, AbstractPacket message) {
		return message.stringify();
	}

}
