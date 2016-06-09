package com.codeaspect.puffer.converters;

import com.codeaspect.puffer.annotations.BooleanFormat;
import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.packet.AbstractPacket;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;

public class BooleanConverterTest {
	
	@Ignore
	public static class TestPacket extends AbstractPacket {
		@PacketMessage(position=1, length=1)
		@BooleanFormat(defaultValue=false, trueValue="Y", falseValue="N")
		public Boolean bool;
	};
	
	private Converter<Boolean> converter = new BooleanConverter();
	
	
	@Test
	public void testHydrate() throws NoSuchFieldException, SecurityException{
		Field field = TestPacket.class.getDeclaredField("bool");
		assertEquals(Boolean.TRUE, converter.hydrate(field, "Y"));
		assertEquals(Boolean.FALSE, converter.hydrate(field, "N"));
		assertEquals(Boolean.FALSE, converter.hydrate(field, "X"));
	}
	
	@Test
	public void testStringify() throws NoSuchFieldException, SecurityException{
		Field field = TestPacket.class.getDeclaredField("bool");
		assertEquals("Y", converter.stringify(field, true));
		assertEquals("N", converter.stringify(field, false));
	}

}
