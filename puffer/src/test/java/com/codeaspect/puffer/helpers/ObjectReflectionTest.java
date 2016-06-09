package com.codeaspect.puffer.helpers;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ObjectReflectionTest {
	
	@Ignore
	public static class TestClass{
		
		private String _str;
		public String String;
		private String string;
		
		public TestClass(){}
		
		public TestClass(String _str, String string, String string2) {
			this._str = _str;
			this.String = string;
			this.string = string2;
		}
		
		public String getString() {
			return string;
		}
		public void setString(String string) {
			this.string = string;
		}
		
	}
	
	private Field getFieldByName(String name){
		try {
			return TestClass.class.getDeclaredField(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

	@Test
	public void testGetFieldValue(){
		ObjectReflection reflection = new ObjectReflection(new TestClass("1","2","3"));
		assertEquals("1", reflection.getFieldValue(getFieldByName("_str")));
		assertEquals("2", reflection.getFieldValue(getFieldByName("String")));
		assertEquals("3", reflection.getFieldValue(getFieldByName("string")));
	}
	
	@Test
	public void testSetFieldValue(){
		TestClass obj = new TestClass();
		ObjectReflection reflection = new ObjectReflection(obj);
		
		reflection.setFieldValue(getFieldByName("_str"),"A");
		assertEquals("A", obj._str);
		
		reflection.setFieldValue(getFieldByName("String"),"B");
		assertEquals("B", obj.String);
		
		reflection.setFieldValue(getFieldByName("String"),"C");
		assertEquals("C", obj.string);
		
	}
	
	@Test
	public void testEquality(){
		TestClass obj = new TestClass();
		ObjectReflection reflection = new ObjectReflection(obj);
		ObjectReflection reflection2 = new ObjectReflection(obj);
		assertEquals(reflection.hashCode(),reflection2.hashCode());
		assertTrue(reflection.equals(reflection2));
		
		TestClass obj2 = new TestClass();
		ObjectReflection reflection3 = new ObjectReflection(obj2);
		//Note TestClass does not override equals
		assertFalse(reflection3.equals(reflection2));
	}

}
