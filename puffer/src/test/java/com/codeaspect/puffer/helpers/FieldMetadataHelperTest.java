package com.codeaspect.puffer.helpers;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.junit.Assert;
import org.junit.Test;

import com.codeaspect.puffer.annotations.PacketMessage;

public class FieldMetadataHelperTest {
	
	private class TestClass{
		@PacketMessage(position=4, length=5)
		private String fld4;
		
		@PacketMessage(position=1, length=5)
		private String fld1;
	}
	
	private class DerivedClass extends TestClass{
		@PacketMessage(position=2, length=5)
		private String fld2;
		
		@PacketMessage(position=3, length=5)
		private String fld3;
	} 
	
	@Test
	public void testFieldMetadata(){
		List<Field> fields = FieldMetadataHelper.getOrderedFieldList(DerivedClass.class);
		
		//Assert order
		for (int idx=2; idx<=fields.size(); idx++){
			Assert.assertEquals("fld"+idx, fields.get(idx-1).getName());
		}
	}

}
