package com.codeaspect.puffer.converters;

import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.enums.Side;
import com.codeaspect.puffer.packet.Packet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;

public class PacketConverterTest {
	
	@Ignore
	public static class TestPacket implements Packet {
		@PacketMessage(position=1, length=7)
		public String field1;
		
		@PacketMessage(position=2, length=5, padChar='0', paddingSide=Side.LEFT)
		public Integer amount;
	};
	
	@Ignore
	public static class TestOuterPacket implements Packet {
		@PacketMessage(position=1, length=7)
		public TestPacket packet;
	};
	
	private AbstractPacketConverter converter = new AbstractPacketConverter();	

	
	@Test
	public void testHydrate() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Field field = TestOuterPacket.class.getDeclaredField("packet");
		TestPacket innerPacket = (TestPacket)converter.hydrate(field, "MESSAGE00123");
		Assert.assertEquals("MESSAGE", innerPacket.field1);
		Assert.assertEquals(new Integer(123), innerPacket.amount);
	}
	
	@Test
	public void testStringify(){
		TestPacket packet = new TestPacket();
		packet.field1="MESSAGE";
		packet.amount=5000;
		TestOuterPacket outer = new TestOuterPacket();
		outer.packet=packet;
		Assert.assertEquals("MESSAGE05000", outer.stringify());
	}

}
