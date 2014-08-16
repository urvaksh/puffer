package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.codeaspect.puffer.annotations.PacketMessage;

public class StringConverter implements SingeltonConverter<String> {

	public String hydrate(Field field, String message) {
		return message.toString();
	}

	public String stringify(Field field, String message) {
		PacketMessage msg = field.getAnnotation(PacketMessage.class);
		int allowedLength = msg.length();
		if(StringUtils.isEmpty(message) && message.length()>allowedLength){
			return message.substring(0, allowedLength);
		}else{
			return message==null?"":message;
		}
	}

}
