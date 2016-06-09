package com.codeaspect.puffer.converters;

import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class NumberConverterTest {

	@SuppressWarnings("unused")
	private static class TestClass {
		private Integer intFld;
		private Long longFld;
		private Double doubleFld;
		private Float floatFld;
		private Short shortFld;
		private Byte byteFld;
		private BigDecimal bigDecimalFld;
		private BigInteger bigIntegerFld;
	}

	Converter<Number> converter = new NumberConverter();
	
	private Field getField(String name){
		try {
			return TestClass.class.getDeclaredField(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void test(String fieldName, Number entity, String numberString){
		Field field = getField(fieldName);
		assertEquals(entity, converter.hydrate(field, numberString));
		assertEquals(numberString, converter.stringify(field, entity));
	}
	
	@Test
	public void testInteger(){
		test("intFld", new Integer(100), "100");
	}
	
	@Test
	public void testLong(){
		test("longFld", new Long(200), "200");
	}
	
	@Test
	public void testFloat(){
		test("floatFld", new Float(100.25), "100.25");
	}
	
	@Test
	public void testShort(){
		test("shortFld", (short) 50, "50");
	}
	
	@Test
	public void testByte(){
		test("byteFld", (byte)10, "10");
	}
	
	@Test
	public void testDouble(){
		test("doubleFld", new Double(100.25), "100.25");
	}
	
	@Test
	public void testBigDecimal(){
		test("bigDecimalFld", new BigDecimal("100.25"), "100.25");
	}
	
	@Test
	public void testBigInteger(){
		test("bigIntegerFld", new BigInteger("300"), "300");
	}
}
