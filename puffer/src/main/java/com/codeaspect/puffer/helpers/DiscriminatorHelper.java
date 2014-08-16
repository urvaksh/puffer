package com.codeaspect.puffer.helpers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.codeaspect.puffer.annotations.DiscriminatorField;
import com.codeaspect.puffer.annotations.DiscriminatorValue;
import com.codeaspect.puffer.annotations.PacketList;
import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.cache.Cache;
import com.codeaspect.puffer.cache.MapCache;
import com.codeaspect.puffer.packet.AbstractPacket;

public class DiscriminatorHelper {

	private static Cache<String, FieldLocation> discriminatorFieldCache = new MapCache<String, DiscriminatorHelper.FieldLocation>();

	private static Cache<Class<? extends AbstractPacket>, Integer> packetSize = new MapCache<Class<? extends AbstractPacket>, Integer>();

	public static int getPacketLength(Class<? extends AbstractPacket> clazz) {
		if (packetSize.contains(clazz)) {
			return packetSize.get(clazz);
		} else {
			synchronized (clazz) {
				List<Field> fields = FieldMetadataHelper
						.getOrderedFieldList(clazz);
				int packetLength = 0;

				for (Field fld : fields) {
					packetLength += fld.getAnnotation(PacketList.class)
							.packetLength();
				}

				packetSize.put(clazz, packetLength);
				return packetLength;
			}
		}
	}

	public static <T extends AbstractPacket> T getConcreteObject(
			Class<T> clazz, String packet) {
		try {
			Class<T> resolvedCalzz = resolveClass(clazz, packet);
			return resolvedCalzz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create instance of class "
					+ clazz.getName(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends AbstractPacket> Class<T> resolveClass(
			Class<T> clazz, String packet) {
		if (clazz.isAnnotationPresent(DiscriminatorField.class)) {
			DiscriminatorField discFld = clazz
					.getAnnotation(DiscriminatorField.class);

			if (discFld.values().length > 0) {
				String fieldValue = getFieldValue(clazz, discFld.fieldName(),
						packet);

				for (DiscriminatorValue discValue : discFld.values()) {
					if (Arrays.asList(discValue.fieldValues()).contains(
							fieldValue)) {
						return (Class<T>) resolveClass(discValue.targetClass(),packet);
					}
				}

			}
		}
		return clazz;
	}

	private static String getFieldValue(Class<?> clazz, String fieldName,
			String packet) {
		FieldLocation loc = findFieldLocation(clazz,fieldName);
		return packet.substring(loc.start, loc.end);
	}

	private static FieldLocation findFieldLocation(Class<?> clazz,
			String fieldName) {
		String fieldCanonicalName = new StringBuilder(clazz.getCanonicalName())
				.append(".").append(fieldName).toString();
		if(discriminatorFieldCache.contains(fieldCanonicalName)){
			return discriminatorFieldCache.get(fieldCanonicalName);
		}else{
			synchronized (clazz) {
				List<Field> fields = FieldMetadataHelper.getOrderedFieldList(clazz);
				
				int startIndex=0;
				for(Field fld: fields){
					PacketMessage packetMessage = fld.getAnnotation(PacketMessage.class);
					if(fld.getName().equals(fieldName)){
						FieldLocation loc = new FieldLocation(startIndex, startIndex+packetMessage.length());
						discriminatorFieldCache.put(fieldCanonicalName, loc);
						return loc;
					}
					startIndex+=packetMessage.length();
				}
			}
		}
		
		throw new RuntimeException(String.format("Field %s not found in class %s", fieldName, clazz.getCanonicalName()));
	}

	private static class FieldLocation {
		private int start;
		private int end;

		private FieldLocation(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void setDiscriminatorFieldCache(Cache discriminatorFieldCache) {
		DiscriminatorHelper.discriminatorFieldCache = (Cache<String, FieldLocation>)discriminatorFieldCache;
	}

	static void setPacketSize(
			Cache<Class<? extends AbstractPacket>, Integer> packetSize) {
		DiscriminatorHelper.packetSize = packetSize;
	}
	
}
