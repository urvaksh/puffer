/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.codeaspect.puffer.exceptions.ReflectionException;

/**
 * The Class ObjectReflection provides an easy way to perform reflective calls on onjects.
 */
public class ObjectReflection {
	
	/** The log. */
	private static Logger LOG = Logger.getLogger(ObjectReflection.class.getName()); 

	/** The delegate object on which the reflection calls will be performed. */
	private Object delegate;

	/**
	 * Instantiates a new ObjectReflection.
	 *
	 * @param delegate the delegate
	 */
	public ObjectReflection(Object delegate) {
		this.delegate = delegate;
	}

	private String makeErrorMessage(String errorMsg, Field field, String... errors) {
		StringBuilder builder = new StringBuilder(errorMsg)
				.append(field.getType().getCanonicalName()).append(".")
				.append(field.getName());
		
		for(String error : errors){
			builder.append(error);
		}
		
		return builder.toString();
	}
	
	/**
	 * Sets the field value.
	 *
	 * @param fld the field
	 * @param value the value
	 */
	public void setFieldValue(Field fld, Object value){
		if(fld.isAccessible()){
			try {
				fld.set(delegate,value);
				return;
			} catch (Exception e) {
				throw new ReflectionException(makeErrorMessage("Unable to set value of", fld),e);
			}
		}else{
			String setterMethodName = "set" + StringUtils.capitalize(fld.getName());
			try{
				Method setterMethod = fld.getDeclaringClass().getMethod(setterMethodName, fld.getType());
				setterMethod.invoke(delegate, value);
			}catch(NoSuchMethodException e){
				//Do nothing, skip to attempt direct field access
				LOG.log(Level.WARNING, String.format("No Setter for non accessable field %s. Attempting direct field access",fld.getName()));
			}catch(Exception e){
				throw new ReflectionException(makeErrorMessage("Unable to invoke setter on field", fld));
			}
			
			//Direct Field access
			try {
				fld.setAccessible(true);
				fld.set(delegate, value);
			} catch (Exception e) {
				throw new ReflectionException(makeErrorMessage("Unable to directly set value in ", fld),e);
			}
		}
	}
	
	/**
	 * Gets the field value.
	 *
	 * @param fld the field
	 * @return the field value
	 */
	public Object getFieldValue(Field fld){
		if(fld.isAccessible()){
			try {
				return fld.get(delegate);
			} catch (Exception e) {
				throw new ReflectionException(makeErrorMessage("Unable to set value of", fld),e);
			}
		}else{
			String getterMethodName = "get" + StringUtils.capitalize(fld.getName());
			try{
				Method getterMethod = fld.getDeclaringClass().getMethod(getterMethodName, fld.getType());
				return getterMethod.invoke(delegate);
			}catch(NoSuchMethodException e){
				//Do nothing, skip to attempt direct field access
				LOG.log(Level.WARNING, "No getter for non accessable field. Attempting direct field access");
			}catch(Exception e){
				throw new ReflectionException(makeErrorMessage("Unable to invoke getter on field", fld));
			}
			
			//Direct Field access
			try {
				fld.setAccessible(true);
				return fld.get(delegate);
			} catch (Exception e) {
				throw new ReflectionException(makeErrorMessage("Unable to directly get value from ", fld),e);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectReflection other = (ObjectReflection) obj;
		if (delegate == null) {
			if (other.delegate != null)
				return false;
		} else if (!delegate.equals(other.delegate))
			return false;
		return true;
	}
}
