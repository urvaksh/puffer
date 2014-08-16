package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

import com.codeaspect.puffer.packet.AbstractPacket;

public class AbstractPacketConverter implements SingeltonConverter<AbstractPacket> {

	public AbstractPacket hydrate(Field field, String message) {
		@SuppressWarnings("unchecked")
		Class<? extends AbstractPacket> clazz = (Class<? extends AbstractPacket>)field.getType();
		return AbstractPacket.parse(clazz, message);
	}

	public String stringify(Field field, AbstractPacket message) {
		return message.toString();
	}

}
