/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import com.codeaspect.puffer.annotations.PacketMessage;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

/**
 * The Class StringConverter converts from a String to a String - as is.<br />
 */
public class StringConverter implements SingeltonConverter<String> {

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#hydrate(java.lang.reflect.Field, java.lang.String)
	 */
	public String hydrate(Field field, String message) {
		return message.toString();
	}

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#stringify(java.lang.reflect.Field, java.lang.Object)
	 */
	public String stringify(Field field, String message) {
		PacketMessage msg = field.getAnnotation(PacketMessage.class);
		int allowedLength = msg.length();
		if(!StringUtils.isEmpty(message) && message.length()>allowedLength){
			return message.substring(0, allowedLength);
		}else{
			return message==null?"":message;
		}
	}

}
