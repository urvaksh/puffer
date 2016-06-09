package com.codeaspect.puffer.cache;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class MapCacheTest {
	
	private Cache<String,String> cache;
	
	@Before
	public void setup(){
		cache = new MapCache<String,String>();
		cache.put("Key", "Value");
	}

	@Test
	public void testContains(){
		assertTrue(cache.contains("Key"));
	}
	
	@Test
	public void testGet(){
		assertEquals("Value",cache.get("Key"));
	}

	@Test
	public void testPut(){
		cache.put("Key2", "Some Value");
		assertTrue(cache.contains("Key2"));
	}
}
