/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.helpers;

import com.codeaspect.puffer.cache.Cache;
import com.codeaspect.puffer.packet.Packet;

import java.lang.reflect.Field;
import java.util.List;

/**
 * The Class CacheSettings.
 */
public class CacheSettings {

	/**
	 * Sets the field cache used to cache the list of mapped fields in an {@link Packet}.
	 *
	 * @param fieldCache the field cache
	 */
	public static void setFieldCache(Cache<Class<?>,List<Field>> fieldCache) {
		FieldMetadataHelper.setFieldCache(fieldCache);
	}
	
	/**
	 * Sets the discriminator field cache which is used to cache the discriminator fields when mapping classes use @{@link com.codeaspect.puffer.annotations.DiscriminatorField}.
	 *
	 * @param discriminatorFieldCache the discriminator field cache
	 */
	public static void setDiscriminatorFieldCache(Cache<String, Object> discriminatorFieldCache) {
		DiscriminatorHelper.setDiscriminatorFieldCache(discriminatorFieldCache);
	}

	/**
	 * Sets the packet size cache which is used to cache the packet sizes of discriminator target classes when mapping classes use @{@link com.codeaspect.puffer.annotations.DiscriminatorField}.
	 *
	 * @param packetSize the packet size
	 */
	public static void setPacketSizeCache(
			Cache<Class<? extends Packet>, Integer> packetSize) {
		DiscriminatorHelper.setPacketSize(packetSize);
	}
}
