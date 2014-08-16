package com.codeaspect.puffer.converters;

import java.lang.reflect.Field;

import com.codeaspect.puffer.annotations.BooleanFormat;

public class BooleanConverter implements SingeltonConverter<Boolean> {
	
	

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

	public String stringify(Field field, Boolean message) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private static class BooleanHelper{
		private static String DEFAULT_TRUE = "Y";
		private static String DEFAULT_FLASE = "N";
		private static boolean DEFAULT_VALUE = false;
		
		private static String getTrueValue(Field fld){
			if(fld.isAnnotationPresent(BooleanFormat.class)){
				return fld.getAnnotation(BooleanFormat.class).trueValue();
			}else{
				return DEFAULT_TRUE;
			}
		}
		
		private static String getFalseValue(Field fld){
			if(fld.isAnnotationPresent(BooleanFormat.class)){
				return fld.getAnnotation(BooleanFormat.class).falseValue();
			}else{
				return DEFAULT_FLASE;
			}
		}
		
		private static boolean getDefaultValue(Field fld){
			if(fld.isAnnotationPresent(BooleanFormat.class)){
				return fld.getAnnotation(BooleanFormat.class).defaultValue();
			}else{
				return DEFAULT_VALUE;
			}
		}
	}
}
