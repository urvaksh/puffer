package com.codeaspect.puffer.packet;

import com.codeaspect.puffer.helpers.FieldMetadataHelper;
import com.codeaspect.puffer.helpers.ObjectReflection;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.lang.reflect.Field;

/*
 * pUFFEr : A framework to allow conversions between fixed length messages and objects
 *
 * @author urvaksh.rogers
 */
public class AbstractPacket implements Packet {

    /**
     * Builds a hashCode based on the annotated fields.
     */

	@Override
	public  int hashCode() {
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
