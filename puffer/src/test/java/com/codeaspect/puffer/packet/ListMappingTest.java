package com.codeaspect.puffer.packet;

import com.codeaspect.puffer.annotations.PacketList;
import com.codeaspect.puffer.annotations.PacketMessage;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ListMappingTest {

	public static class Message extends AbstractPacket {

		@PacketMessage(length = 10, position = 1)
		String details;

		@PacketMessage(position = 2, length=6)
		@PacketList
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
