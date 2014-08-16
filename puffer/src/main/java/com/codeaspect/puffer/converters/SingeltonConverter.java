/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.converters;

/**
 * The Interface SingeltonConverter is simply a marker interface that indicates that the {@link com.codeaspect.puffer.converters.Converter} object once created can be reused.<br />
 * Any class implementing this interface should ensure that no mutable state is stored.
 * If this interface is not implemented a new Converter object is created <b>every</b> time.
 */
public interface SingeltonConverter<T> extends Converter<T> {

}
