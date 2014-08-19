package com.codeaspect.puffer.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.annotations.TemporalFormat;
import com.codeaspect.puffer.converters.CharacterConverter;
import com.codeaspect.puffer.converters.Converter;
import com.codeaspect.puffer.converters.SingeltonConverter;
import com.codeaspect.puffer.enums.NumericSign;
import com.codeaspect.puffer.enums.Side;
import com.codeaspect.puffer.testutils.TestUtils;

public class ConverterHelperTest {

	@Test
	public void testSingeltonConverter() {
		Class<? extends Converter<?>> converterClazz = CharacterConverter.class;
		Converter<?> conv1 = ConverterHelper.getConverter(converterClazz);
		Converter<?> conv2 = ConverterHelper.getConverter(converterClazz);

		// Assert converterClazz is a SingeltonConverter
		assertTrue(SingeltonConverter.class.isAssignableFrom(converterClazz));
		assertTrue(conv1 == conv2);
		assertEquals(conv1, conv2);
	}

	@Test
	public void testHydrate_NegNumber() throws NoSuchFieldException, SecurityException {
		Field field = TestClass.class.getDeclaredField("amount");
		Long value = (Long) ConverterHelper.hydrate(field, "-100");
		assertEquals(new Long(-100), value);
	}

	@Test
	public void testStringify_NegNumber() throws NoSuchFieldException, SecurityException {
		Field field = TestClass.class.getDeclaredField("amount");
		String value = ConverterHelper.stringifyAndPad(field, new Long(-100));
		assertEquals("-000000100", value);
	}

	@Test
	public void testHydrate_Number() throws NoSuchFieldException, SecurityException {
		Field field = TestClass.class.getDeclaredField("amount");
		Long value = (Long) ConverterHelper.hydrate(field, "100");
		assertEquals(new Long(100), value);
	}

	@Test
	public void testStringify_Number() throws NoSuchFieldException, SecurityException {
		Field field = TestClass.class.getDeclaredField("amount");
		String value = ConverterHelper.stringifyAndPad(field, new Long(100));
		assertEquals("0000000100", value);
	}

	@Test
	public void testHydrate_Date() throws NoSuchFieldException, SecurityException {
		Field field = TestClass.class.getDeclaredField("date");
		Date value = (Date) ConverterHelper.hydrate(field, "18082014");
		assertEquals(TestUtils.createDate(18, 8, 2014), value);
	}

	@Test
	public void testStringify_Date() throws NoSuchFieldException, SecurityException {
		Field field = TestClass.class.getDeclaredField("date");
		String value = ConverterHelper.stringifyAndPad(field, TestUtils.createDate(18, 8, 2014));
		assertEquals("18082014", value);
	}


	private static class TestClass {
		@PacketMessage(position = 1, length = 10, numericSign = NumericSign.DEFAULT, padChar = '0', paddingSide = Side.LEFT)
		Long amount;

		@PacketMessage(position = 2, length = 8)
		@TemporalFormat("ddMMyyyy")
		Date date;
	}

}
