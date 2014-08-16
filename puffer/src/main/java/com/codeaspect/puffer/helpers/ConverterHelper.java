package com.codeaspect.puffer.helpers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.converters.Converter;
import com.codeaspect.puffer.converters.SingeltonConverter;

public class ConverterHelper {
	
	private ConverterHelper(){}
	
	private static Map<Class<?>, Converter<? super Object>> singletonConvertors = new HashMap<Class<?>, Converter<? super Object>>();
	
	@SuppressWarnings("rawtypes")
	public static Converter<? super Object> getConverter(Class<? extends Converter> clazz){
		if(singletonConvertors.containsKey(clazz)){
			return singletonConvertors.get(clazz);
		}
		try{
			@SuppressWarnings("unchecked")
			Converter<? super Object> converter = (Converter<? super Object>)clazz.newInstance();
			if(converter instanceof SingeltonConverter){
				singletonConvertors.put(clazz, converter);
			}
			return converter;
		}catch(Exception e){
			throw new RuntimeException("Unable to create converter",e);
		}
	} 
	
	public static Object hydrate(Field field, String fieldValue){
		PacketMessage msg = field.getAnnotation(PacketMessage.class);
		String value = msg.numericSign().getNumericValue(fieldValue);
		if(msg.numericSign().isNegitave(fieldValue)){
			value="-"+value;
		}
		return ConverterHelper.getConverter(msg.converter()).hydrate(field, fieldValue);
	}
	
	public static String stringifyAndPad(Field field, Object value){
		PacketMessage msg = field.getAnnotation(PacketMessage.class);
		String stringified = ConverterHelper.getConverter(msg.converter()).stringify(field, value);
		
		if(value instanceof Number){
			return msg.numericSign().getString(stringified, msg.paddingSide(), msg.padChar(), msg.length());
		}else{
			return msg.paddingSide().pad(stringified, msg.length(), msg.padChar());
		}
	}

}
