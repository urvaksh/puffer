package com.codeaspect.puffer.cache;

import org.junit.Assert;
import org.junit.Test;

public class MapFieldCacheTest {
	
	@Test
	public void testSingleton(){
		MapFieldCache cache1 = MapFieldCache.getInstance();
		MapFieldCache cache2 = MapFieldCache.getInstance();
		Assert.assertEquals("MapFieldCache is not a Singleton. This makes the cache useless", cache1, cache2);
	}

}
