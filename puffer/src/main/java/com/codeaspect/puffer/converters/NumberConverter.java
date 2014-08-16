package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

import com.codeaspect.puffer.exceptions.PufferException;

public class NumberConverter implements SingeltonConverter<Number> {

	protected boolean checkFieldType(Field field, Class<?> clazz) {
		return field.getType().equals(clazz);
	}

	public Number hydrate(Field field, String message) {
		String msg = message.trim();
		if(StringUtils.isEmpty(msg)){
			msg="0";
		}
		
		try{
			if(checkFieldType(field, Integer.class))
				return Integer.parseInt(msg);
			else if(checkFieldType(field, Long.class))
				return Long.parseLong(msg);
			else if(checkFieldType(field, Double.class))
				return Double.parseDouble(msg);
			else if(checkFieldType(field, Float.class))
				return Float.parseFloat(msg);
			else if(checkFieldType(field, Short.class))
				return Short.parseShort(msg);
			else if(checkFieldType(field, Byte.class))
				return Byte.parseByte(msg);
			else if(checkFieldType(field, BigDecimal.class))
				return new BigDecimal(message);
			else if(checkFieldType(field, BigInteger.class))
				return new BigInteger(message);
			else
				throw new PufferException("Unknown Number subclass, please create a custom Converter for "+field.getType().getCanonicalName());
			
		}catch(NumberFormatException ex){
			throw new PufferException(String.format("Error formatting %s.%s as a number",field.getDeclaringClass().getCanonicalName(),field.getName()),ex);
		}
	}

	public String stringify(Field field, Number entity) {
		return entity==null?"0":entity.toString();
	}

}
