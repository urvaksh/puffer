package com.codeaspect.puffer.converters;

import org.junit.Assert;
import org.junit.Test;

public class CharacterConverterTest {
	
	private Converter<Character> converter = new CharacterConverter();
	
	@Test
	public void testHydrate(){
		Assert.assertEquals(new Character('A'), converter.hydrate(null, "A"));
	}
	
	@Test
	public void testStringify(){
		Assert.assertEquals("A", converter.stringify(null, 'A'));
	}

}
