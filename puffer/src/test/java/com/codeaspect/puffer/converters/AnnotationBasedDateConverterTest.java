package com.codeaspect.puffer.converters;

import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.annotations.TemporalFormat;
import com.codeaspect.puffer.packet.Packet;
import com.codeaspect.puffer.testutils.TestUtils;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

public class AnnotationBasedDateConverterTest {
	
	@Ignore
	public static class TestPacket implements Packet {
		@PacketMessage(position=1, length=7)
		@TemporalFormat("dd-MM-yyyy")
		public Date date;
	};
	
	private Converter<Date> converter = new AnnotationBasedDateConverter();
	
	private static final Date TEST_DATE = TestUtils.createDate(18,8,2014);
	private static final String TEST_STRING = "18-08-2014";
	
	@Test
	public void testHydrate() throws NoSuchFieldException, SecurityException{
		Field field = TestPacket.class.getDeclaredField("date");
		Date date = converter.hydrate(field, TEST_STRING);
		Assert.assertEquals(TEST_DATE, date);
	}
	
	@Test
	public void testStringify() throws NoSuchFieldException, SecurityException{
		Field field = TestPacket.class.getDeclaredField("date");
		String date = converter.stringify(field, TEST_DATE);
		Assert.assertEquals(TEST_STRING, date);
	}

}
