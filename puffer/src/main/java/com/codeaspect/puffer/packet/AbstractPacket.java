/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
package com.codeaspect.puffer.packet;

import com.codeaspect.puffer.annotations.PacketList;
import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.helpers.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractPacket {

	/**
	 * Parses the fixed length packet to the current object based on the
	 * {@link com.codeaspect.puffer.annotations.PacketMessage} and {@link com.codeaspect.puffer.annotations.PacketList}
	 * annotations.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param packet
	 *            the fixed length String representing the packet
	 * @param clazz
	 *            the Class<T> which is the class or a super class of the final packet.
	 */
	public <T extends AbstractPacket> void parse(String packet, Class<T> clazz) {
		List<Field> fields = FieldMetadataHelper.getOrderedFieldList(clazz);
		MessageListParser listParser = new MessageListParser(this, packet);
		ObjectReflection reflection = new ObjectReflection(this);
		int location = 0;

		for (Field fld : fields) {
			PacketMessage packetMessage = fld.getAnnotation(PacketMessage.class);

			if (fld.isAnnotationPresent(PacketList.class)) {
				List<? super AbstractPacket> list = listParser.parseList(fld, location);
				reflection.setFieldValue(fld, list);
				location=listParser.getCurrentLocation();
			} else if (AbstractPacket.class.isAssignableFrom(fld.getType())) {

				@SuppressWarnings("unchecked")
				// Safe cast since the isAssignableFrom check is passed
				Class<? extends AbstractPacket> fieldClazz = (Class<? extends AbstractPacket>) fld.getType();

				AbstractPacket component = DiscriminatorHelper.getConcreteObject(fieldClazz, packet.substring(location));
				int packetSize = DiscriminatorHelper.getPacketLength(fieldClazz);

				component.parse(packet.substring(location, location + packetSize));
				reflection.setFieldValue(fld, component);
				location += packetSize;
			} else {
				String fldStringValue = packet.substring(location, location + packetMessage.length());
				Object value = ConverterHelper.hydrate(fld, fldStringValue);
				reflection.setFieldValue(fld, value);
				location += packetMessage.length();
			}
		}
	}

	/**
	 * Parses the fixed length packet to the current object based on the
	 * {@link com.codeaspect.puffer.annotations.PacketMessage} and {@link com.codeaspect.puffer.annotations.PacketList}
	 * annotations.
	 * 
	 * @param packet
	 *            the fixed length String representing the packet
	 */
	public void parse(String packet) {
		parse(packet, getClass());
	}

	/**
	 * Parses the fixed length packet to the specified class or it's subclasses based on the specified
	 * {@link com.codeaspect.puffer.annotations.DiscriminatorField} and the
	 * {@link com.codeaspect.puffer.annotations.PacketMessage} and {@link com.codeaspect.puffer.annotations.PacketList}
	 * of the destination class annotations.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the target class - can be a base class if {@link com.codeaspect.puffer.annotations.DiscriminatorField}
	 *            is specified, the target class will automatically be looked up.
	 * @param packet
	 *            the fixed length String representing the packet
	 * @return the parsed Object
	 */
	public static <T extends AbstractPacket> T parse(Class<T> clazz, String packet) {
		T obj = DiscriminatorHelper.getConcreteObject(clazz, packet);
		obj.parse(packet);
		return obj;
	}

	/**
	 * The fixed length string created from the object based on the
	 * {@link com.codeaspect.puffer.annotations.PacketMessage} and {@link com.codeaspect.puffer.annotations.PacketList}
	 * annotations
	 */
	
	public String stringify() {
		StringBuilder packetBuilder = new StringBuilder();
		ObjectReflection reflection = new ObjectReflection(this);

		for (Field fld : FieldMetadataHelper.getOrderedFieldList(getClass())) {
			if (fld.isAnnotationPresent(PacketList.class)) {
				List<?> list = (List<?>) reflection.getFieldValue(fld);
				for (Object obj : list) {
					packetBuilder.append(obj.toString());
				}
			} else {
				Object value = reflection.getFieldValue(fld);
				String stringValue = ConverterHelper.stringifyAndPad(fld, value);
				packetBuilder.append(stringValue);
			}
		}

		return packetBuilder.toString();
	}

	/**
	 * Builds a hashCode based on the annotated fields.
	 */
	@Override
	public int hashCode() {
		ObjectReflection reflection = new ObjectReflection(this);
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();

		for (Field fld : FieldMetadataHelper.getOrderedFieldList(getClass())) {
			hashCodeBuilder.append(reflection.getFieldValue(fld));
		}

		return hashCodeBuilder.toHashCode();
	}

	/**
	 * If the objects are of the same class equals performs a field by field comparison (only of annotated fields) to
	 * dertimine equality.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		ObjectReflection other = new ObjectReflection(obj);
		ObjectReflection reflection = new ObjectReflection(this);
		EqualsBuilder equalsBuilder = new EqualsBuilder();

		for (Field fld : FieldMetadataHelper.getOrderedFieldList(getClass())) {
			equalsBuilder.append(reflection.getFieldValue(fld), other.getFieldValue(fld));
		}

		return equalsBuilder.isEquals();
	}

}
