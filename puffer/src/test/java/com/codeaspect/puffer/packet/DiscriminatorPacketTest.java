package com.codeaspect.puffer.packet;

import com.codeaspect.puffer.annotations.*;
import com.codeaspect.puffer.cache.MapCache;
import com.codeaspect.puffer.cache.MapFieldCache;
import com.codeaspect.puffer.helpers.CacheSettings;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class DiscriminatorPacketTest {
	
	static{
		CacheSettings.setFieldCache(MapFieldCache.getInstance());
		CacheSettings.setDiscriminatorFieldCache(new MapCache<String, Object>());
		CacheSettings.setPacketSizeCache(new MapCache<Class<? extends Packet>, Integer>());
	}

	@Ignore
	@com.codeaspect.puffer.annotations.Packet(description = "Packet to test Discriminators")
	@DiscriminatorField(fieldName = "fld1", 
		values = { @DiscriminatorValue(fieldValues = {"ABC", "abc" }, targetClass = Derived1.class),
					@DiscriminatorValue(fieldValues = {"XYZ", "xyz" }, targetClass = Middle.class)})
	public static abstract class Base implements Packet {
		@PacketMessage(length = 4, position = 1)
		String dummy;

		@PacketMessage(length = 3, position = 2)
		String fld1;

		@PacketMessage(length = 1, position = 3)
		@BooleanFormat
		Boolean val;
	}
	
	@Ignore
	public static class Derived1 extends Base{
		@PacketMessage(length=10, position=4)
		@TemporalFormat("yyyy-MM-dd")
		Date date;
	}
	
	@Ignore
	@DiscriminatorField(fieldName = "subPacket", 
	values = { @DiscriminatorValue(fieldValues = {"ABC", "abc" }, targetClass = ReDerived.class),
				@DiscriminatorValue(fieldValues = {"XYZ", "xyz" }, targetClass = ReDerived2.class)})
	public static class Middle extends Base{
		@PacketMessage(length=3, position=4)
		String subPacket;
	}
	
	@Ignore
	public static class ReDerived extends Middle{
		@PacketMessage(length=5, position=5)
		Long amt;
	}
	
	@Ignore
	public static class ReDerived2 extends Middle{
		@PacketMessage(length=15, position=5)
		String desc;

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
	private Date createDate(int date, int month, int year){
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}
	
	@Test
	public void testDerived1(){
		String input = "----ABCY2014-08-15";
		Packet packet= Packet.parse(Base.class, input);
		
		assertEquals(Derived1.class, packet.getClass());
		
		Derived1 der =  (Derived1)packet;
		
		assertEquals("----", der.dummy);
		assertEquals("ABC", der.fld1);
		assertEquals(Boolean.TRUE, der.val);
		assertEquals(createDate(15,8,2014), der.date);
		
	}
	
	@Test
	public void testRederived(){
		String input = "----xyzNABC00500";
		Packet packet= Packet.parse(Base.class, input);
		
		assertEquals(ReDerived.class, packet.getClass());
		
		Middle der =  (Middle)packet;
		
		assertEquals("----", der.dummy);
		assertEquals("xyz", der.fld1);
		assertEquals(Boolean.FALSE, der.val);
		
		assertEquals(new Long(500), ((ReDerived)der).amt);
		
	}
	
	@Test
	public void testRederived2(){
		String input = "----xyzNXYZFive Hundred   ";
		Packet packet= Packet.parse(Base.class, input);
		
		assertEquals(ReDerived2.class, packet.getClass());
		
		Middle der =  (Middle)packet;
		
		assertEquals("----", der.dummy);
		assertEquals("xyz", der.fld1);
		assertEquals(Boolean.FALSE, der.val);
		
		ReDerived2 rederived = (ReDerived2)der;
		
		assertEquals("Five Hundred", rederived.desc.trim());
		
	}

}
