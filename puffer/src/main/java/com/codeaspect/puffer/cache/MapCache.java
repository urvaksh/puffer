package com.codeaspect.puffer.cache;

import java.util.HashMap;
import java.util.Map;

public class MapCache<K,V> implements Cache<K,V>{

	private Map<K,V> cache;
	
    public MapCache() {
    	cache = new HashMap<K,V>();
    }

	public boolean contains(K key) {
		return cache.containsKey(key);
	}

	public void put(K key, V value) {
		cache.put(key,value);
	}

	public V get(K key) {
		return cache.get(key);
	}
}
