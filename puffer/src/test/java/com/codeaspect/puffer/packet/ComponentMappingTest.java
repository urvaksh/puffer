package com.codeaspect.puffer.packet;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.codeaspect.puffer.annotations.PacketList;
import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.packet.ListMappingTest.InnerMessage;
import com.codeaspect.puffer.packet.ListMappingTest.Message;

public class ComponentMappingTest {
	
	public static class Message extends AbstractPacket {

		@PacketMessage(length = 10, position = 1)
		String details;

		@PacketMessage(length = 6, position = 2)
		InnerMessage messages;
	}

	public static class InnerMessage extends AbstractPacket {

		@PacketMessage(length = 1, position = 1)
		private String identifier;

		@PacketMessage(length = 5, position = 2)
		private String name;
	}
	
	@Test
	public void testMapping() {
		Message msg = AbstractPacket.parse(Message.class, "0123456789AXXXXX");
		assertEquals("0123456789", msg.details);
		assertEquals("A", msg.messages.identifier);
		assertEquals("XXXXX", msg.messages.name);
	}

}
