package com.codeaspect.puffer.helpers;

import java.lang.reflect.Field;
import java.util.List;

import com.codeaspect.puffer.cache.Cache;
import com.codeaspect.puffer.packet.AbstractPacket;

public class CacheSettings {

	public static void setFieldCache(Cache<Class<?>,List<Field>> fieldCache) {
		FieldMetadataHelper.setFieldCache(fieldCache);
	}
	
	public static void setDiscriminatorFieldCache(Cache<String, Object> discriminatorFieldCache) {
		DiscriminatorHelper.setDiscriminatorFieldCache(discriminatorFieldCache);
	}

	public static void setPacketSizeCache(
			Cache<Class<? extends AbstractPacket>, Integer> packetSize) {
		DiscriminatorHelper.setPacketSize(packetSize);
	}
}
