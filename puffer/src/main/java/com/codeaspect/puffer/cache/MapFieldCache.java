package com.codeaspect.puffer.cache;

import java.lang.reflect.Field;
import java.util.List;

public class MapFieldCache extends MapCache<Class<?>,List<Field>>{
	
    private MapFieldCache() {
    	super();
    }
 
    private static class LazyHolder {
        private static final MapFieldCache INSTANCE = new MapFieldCache();
    }
    
    public static MapFieldCache getInstance() {
    	return LazyHolder.INSTANCE;
    }

}
