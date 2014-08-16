/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

import com.codeaspect.puffer.annotations.BooleanFormat;

/**
 * The Class BooleanConverter is a {@link com.codeaspect.puffer.converters.Converter} that converts between Strings and Boolean.<br />.
 * It uses the @{@link com.codeaspect.puffer.annotations.BooleanFormat} annotation to deduce how the Boolean is reperesented as a String.<br />
 */
public class BooleanConverter implements SingeltonConverter<Boolean> {
	
	

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#hydrate(java.lang.reflect.Field, java.lang.String)
	 */
	public Boolean hydrate(Field field, String message) {
		if(message!=null){
			if(BooleanHelper.getTrueValue(field).equalsIgnoreCase(message)){
				return true;
			}else if(BooleanHelper.getFalseValue(field).equalsIgnoreCase(message)){
				return false;
			}else{
				return BooleanHelper.getDefaultValue(field);
			}
		}else{
			return BooleanHelper.getDefaultValue(field);
		}
	}

	/* (non-Javadoc)
	 * @see com.codeaspect.puffer.converters.Converter#stringify(java.lang.reflect.Field, java.lang.Object)
	 */
	public String stringify(Field field, Boolean message) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * The Class BooleanHelper.
	 */
	private static class BooleanHelper{
		
		/** The default true. */
		private static String DEFAULT_TRUE = "Y";
		
		/** The default flase. */
		private static String DEFAULT_FLASE = "N";
		
		/** The default value. */
		private static boolean DEFAULT_VALUE = false;
		
		/**
		 * Gets the true value.
		 *
		 * @param fld the fld
		 * @return the true value
		 */
		private static String getTrueValue(Field fld){
			if(fld.isAnnotationPresent(BooleanFormat.class)){
				return fld.getAnnotation(BooleanFormat.class).trueValue();
			}else{
				return DEFAULT_TRUE;
			}
		}
		
		/**
		 * Gets the false value.
		 *
		 * @param fld the fld
		 * @return the false value
		 */
		private static String getFalseValue(Field fld){
			if(fld.isAnnotationPresent(BooleanFormat.class)){
				return fld.getAnnotation(BooleanFormat.class).falseValue();
			}else{
				return DEFAULT_FLASE;
			}
		}
		
		/**
		 * Gets the default value.
		 *
		 * @param fld the fld
		 * @return the default value
		 */
		private static boolean getDefaultValue(Field fld){
			if(fld.isAnnotationPresent(BooleanFormat.class)){
				return fld.getAnnotation(BooleanFormat.class).defaultValue();
			}else{
				return DEFAULT_VALUE;
			}
		}
	}
}
