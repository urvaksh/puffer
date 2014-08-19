package com.codeaspect.puffer.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

import com.codeaspect.puffer.converters.CharacterConverter;
import com.codeaspect.puffer.converters.Converter;
import com.codeaspect.puffer.converters.SingeltonConverter;

public class ConverterHelperTest {
	
	@Test
	public void testSingeltonConverter(){
		Class<? extends Converter<?>> converterClazz = CharacterConverter.class;
		Converter<?> conv1 = ConverterHelper.getConverter(converterClazz);
		Converter<?> conv2 = ConverterHelper.getConverter(converterClazz);
		
		//Assert converterClazz is a SingeltonConverter
		assertTrue(SingeltonConverter.class.isAssignableFrom(converterClazz));
		assertTrue(conv1==conv2);
		assertEquals(conv1, conv2);
	}

}
