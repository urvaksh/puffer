package com.codeaspect.puffer.packet;

import com.codeaspect.puffer.annotations.PacketMessage;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ComponentMappingTest {
	
	public static class Message implements Packet {

		@PacketMessage(length = 10, position = 1)
		String details;

		@PacketMessage(length = 6, position = 2)
		InnerMessage messages;
	}

	public static class InnerMessage implements Packet {

		@PacketMessage(length = 1, position = 1)
		private String identifier;

		@PacketMessage(length = 5, position = 2)
		private String name;
	}
	
	@Test
	public void testMapping() {
		Message msg = Packet.parse(Message.class, "0123456789AXXXXX");
		assertEquals("0123456789", msg.details);
		assertEquals("A", msg.messages.identifier);
		assertEquals("XXXXX", msg.messages.name);
	}

}
