/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import com.codeaspect.puffer.annotations.TemporalFormat;
import com.codeaspect.puffer.exceptions.PufferException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class AnnotationBasedDateConverter is a {@link com.codeaspect.puffer.converters.Converter<Date>} that converts between Strings and Dates.<br />
 * It uses the @{@link com.codeaspect.puffer.annotations.TemporalFormat} annotation to deduce the format of the Date.<br />
 * If no annotation is placed the default format is YYmmDD<br />
 */
public class AnnotationBasedDateConverter implements SingeltonConverter<Date>{

	/** The default date format. */
	private static String DEFAULT_DATE_FORMAT = "YYmmDD";
	
	/**
	 * Gets the date format.
	 *
	 * @param fld the fld
	 * @return the date format
	 */
	private DateFormat getDateFormat(Field fld){
		String formatString = DEFAULT_DATE_FORMAT;
		TemporalFormat format = fld.getAnnotation(TemporalFormat.class);
		if(format!=null && format.value()!=null){
			formatString=format.value();
		}
		return new SimpleDateFormat(formatString);
	} 
	
	
	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#hydrate(java.lang.reflect.Field, java.lang.String)
	 */
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
			throw new PufferException("Could not parse date",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#stringify(java.lang.reflect.Field, java.lang.Object)
	 */
	public String stringify(Field field, Date entity) {
		if(entity==null || entity.getTime()==0){
			TemporalFormat format = field.getAnnotation(TemporalFormat.class);
			return StringUtils.rightPad("", format.value().length(),'0');
		}else{
			return getDateFormat(field).format(entity);
		}
	}

}
