package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.codeaspect.puffer.annotations.TemporalFormat;
import com.codeaspect.puffer.exceptions.PufferException;

public class AnnotationBasedDateConverter implements SingeltonConverter<Date>{

	private static String DEFAULT_DATE_FORMAT = "YYmmDD";
	
	private DateFormat getDateFormat(Field fld){
		String formatString = DEFAULT_DATE_FORMAT;
		TemporalFormat format = fld.getAnnotation(TemporalFormat.class);
		if(format!=null && format.value()!=null){
			formatString=format.value();
		}
		return new SimpleDateFormat(formatString);
	} 
	
	
	public Date hydrate(Field field, String message) {
		// For dates with all 0000s
		try{
			if(Long.parseLong(message)==0){
				return new Date(0);
			}
		}catch(NumberFormatException ex){
			//Do nothing, let it fall through
		}
		
		
		try{
			return getDateFormat(field).parse(message);
		}catch(ParseException e){
			//TODO : Make exception descriptive
			throw new PufferException("Could not parse date with format",e);
		}
	}

	public String stringify(Field field, Date entity) {
		if(entity==null || entity.getTime()==0){
			TemporalFormat format = field.getAnnotation(TemporalFormat.class);
			return StringUtils.rightPad("", format.value().length(),'0');
		}else{
			return getDateFormat(field).format(entity);
		}
	}

}
