package com.codeaspect.puffer.packet;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.codeaspect.puffer.annotations.PacketList;
import com.codeaspect.puffer.annotations.PacketMessage;

public class ListMappingTest {

	public static class Message extends AbstractPacket {

		@PacketMessage(length = 10, position = 1)
		String details;

		@PacketMessage(length=6, position = 2)
		@PacketList(packetLength=6)
		List<InnerMessage> messages;
	}

	public static class InnerMessage extends AbstractPacket {

		@PacketMessage(length = 1, position = 1)
		private String identifier;

		@PacketMessage(length = 5, position = 2)
		private String name;
	}

	@Test
	public void testMapping() {
		Message msg = AbstractPacket.parse(Message.class, "0123456789AXXXXXBYYYYY");
		assertEquals("0123456789", msg.details);
		assertEquals(2, msg.messages.size());
		assertEquals("A", msg.messages.get(0).identifier);
		assertEquals("XXXXX", msg.messages.get(0).name);
		assertEquals("B", msg.messages.get(1).identifier);
		assertEquals("YYYYY", msg.messages.get(1).name);
	}
}
