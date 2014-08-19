package com.codeaspect.puffer.converters;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.codeaspect.puffer.annotations.PacketMessage;

public class StringConverterTest {

	private Converter<String> converter = new StringConverter();

	private static class TestClass {
		@PacketMessage(position = 1, length = 10)
		private String strFld;
	}

	private Field stringField;

	@Before
	public void setup() throws NoSuchFieldException, SecurityException {
		stringField = TestClass.class.getDeclaredField("strFld");
	}

	@Test
	public void testHydrate() {
		final String MESSAGE = "SOME MESSAGE";
		assertEquals(MESSAGE, converter.hydrate(stringField, MESSAGE));
	}
	
	@Test
	public void testStringify_Null() {
		assertEquals("",converter.stringify(stringField, null));
	}
	
	@Test
	public void testStringify() {
		final String MESSAGE = "Message";
		assertEquals(MESSAGE,converter.stringify(stringField, MESSAGE));
	}
	
	@Test
	public void testStringify_Truncate() {
		final String LONG_MESSAGE = "A Long Message";
		assertEquals(LONG_MESSAGE.substring(0, 10),converter.stringify(stringField, LONG_MESSAGE));
	}

}
