package com.codeaspect.puffer.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.codeaspect.puffer.annotations.PacketList;
import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.exceptions.PufferException;
import com.codeaspect.puffer.packet.AbstractPacket;

public class MessageListParser {

	private AbstractPacket baseObject;
	private String packet;
	private List<Field> fields;
	private ObjectReflection reflection;

	public MessageListParser(AbstractPacket baseObject, String packet) {
		super();
		this.baseObject = baseObject;
		this.packet = packet;
		this.fields = FieldMetadataHelper.getOrderedFieldList(baseObject
				.getClass());
		this.reflection = new ObjectReflection(baseObject);
	}

	protected int findListCount(Field fld, int start) {
		PacketList packetList = fld.getAnnotation(PacketList.class);

		int packetCount = 0;
		if (packetList.packetCount() > 0) {
			packetCount = packetList.packetCount();
		} else if (!StringUtils.isEmpty(packetList.packetCountField())) {
			final String packetCountFieldName = packetList.packetCountField();
			Field packetCountField = (Field) CollectionUtils.find(fields,
					new Predicate() {
						public boolean evaluate(Object obj) {
							Field fld = (Field) obj;
							return fld.getName().equals(packetCountFieldName);
						}
					});

			try {
				packetCount = Integer.parseInt(reflection.getFieldValue(
						packetCountField).toString());
			} catch (NumberFormatException e) {
				throw new PufferException(
						"The field specified in @PacketList.packetCountField does not contain a valid numeric value");
			}
		} else if (fields.indexOf(fld) == fields.size() - 1) {// Last
			PacketMessage packetMessage = fld
					.getAnnotation(PacketMessage.class);
			packetCount = (packet.length() - start) / packetMessage.length();
		} else {
			throw new PufferException(
					"@PacketList must ddefine attributes packetCount, packetCountField or be the last @PacketMessage annotated field in the class");
		}

		return packetCount;
	}

	protected int findPacketLength(Field fld) {
		PacketList packetList = fld.getAnnotation(PacketList.class);

		int packetLength = 0;
		if (packetList.packetLength() > 0) {
			packetLength = packetList.packetLength();
		} else if (!StringUtils.isEmpty(packetList.packetLengthField())) {
			final String packetLengthFieldName = packetList.packetLengthField();
			Field packetLengthField = (Field) CollectionUtils.find(fields,
					new Predicate() {
						public boolean evaluate(Object obj) {
							Field fld = (Field) obj;
							return fld.getName().equals(packetLengthFieldName);
						}
					});

			try {
				packetLength = Integer.parseInt(reflection.getFieldValue(
						packetLengthField).toString());
			} catch (NumberFormatException e) {
				throw new PufferException(
						"The field specified in @PacketList.packetLengthField does not contain a valid numeric value");
			}

		} else {
			packetLength = fld.getAnnotation(PacketMessage.class).length();
		}

		return packetLength;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends AbstractPacket> Class<T> getListItemClass(Field fld){
		try{
			if(fld.getGenericType() instanceof ParameterizedType){
				ParameterizedType pt = (ParameterizedType) fld.getGenericType();
				return (Class<T>)pt.getActualTypeArguments()[0];
			}else{
				//TODO: Replace with Exception
				return null;
			}
		}catch (Exception e){
			throw new PufferException(fld.getName()+" annotated with @PacketList must have generic type List", e);
		}
	}
	
	public <T extends AbstractPacket> List<T> parseList(Field fld, int start){
		int listSize = findListCount(fld, start);
		int packetLength = findPacketLength(fld);
		List<T> lst = new ArrayList<T>();
		
		for(int listIndex=0; listIndex<listSize; listIndex++){
			String packetStringValue = packet.substring(start, start+packetLength);
			Class<T> listItemClass = getListItemClass(fld);
			T listItem = AbstractPacket.parse(listItemClass, packetStringValue);
			
			start+=packetLength;
			lst.add(listItem);
		}
		
		return lst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((baseObject == null) ? 0 : baseObject.hashCode());
		result = prime * result + ((packet == null) ? 0 : packet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageListParser other = (MessageListParser) obj;
		if (baseObject == null) {
			if (other.baseObject != null)
				return false;
		} else if (!baseObject.equals(other.baseObject))
			return false;
		if (packet == null) {
			if (other.packet != null)
				return false;
		} else if (!packet.equals(other.packet))
			return false;
		return true;
	}

}
