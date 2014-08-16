package com.codeaspect.puffer.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.codeaspect.puffer.annotations.PacketMessage;
import com.codeaspect.puffer.cache.Cache;
import com.codeaspect.puffer.cache.MapFieldCache;

/**
 * The Class FieldMetadataHelper is a helper that other classes use to get Field Metadata.
 */
public class FieldMetadataHelper {

	
	/** The field cache. */
	private static Cache<Class<?>,List<Field>> fieldCache = MapFieldCache.getInstance();

	/**
	 * Instantiates a new field metadata helper.
	 */
	private FieldMetadataHelper() {
	}

	/**
	 * Returns the ordered list of fields passed on the positions in @see com.codeaspect.puffer.annotations.PacketMessage .
	 *
	 * @param clazz the clazz
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	private static List<Field> createOrderedFieldList(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();

		Collections.addAll(fields, clazz.getDeclaredFields());

		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != null) {
			Collections.addAll(fields, superClazz.getDeclaredFields());
			superClazz = superClazz.getSuperclass();
		}

		Field[] orderedFields = new Field[fields.size()];
		for (Field fld : fields) {
			if (fld.isAnnotationPresent(PacketMessage.class)) {
				PacketMessage msg = fld.getAnnotation(PacketMessage.class);
				orderedFields[msg.position() - 1]= fld;
			}
		}

		return (List<Field>) CollectionUtils.select(Arrays.asList(orderedFields),
				new Predicate() {
					public boolean evaluate(Object obj) {
						return obj != null;
					}
				});
	}

	/**
	 * Returns the ordered list of fields passed on the positions in {@link com.codeaspect.puffer.annotations.PacketMessage}<br />
	 * If a Field Cache is provided through {@link com.codeaspect.puffer.helpers.CacheSettings#setFieldCache} then the cache will be used for succesive calls
	 *
	 * @param clazz the clazz
	 * @return the ordered field list
	 */
	public static List<Field> getOrderedFieldList(Class<?> clazz) {
		if(fieldCache!=null && fieldCache.contains(clazz)){
			return fieldCache.get(clazz);
		}else{
			List<Field> orderedList = createOrderedFieldList(clazz);
			if(fieldCache!=null){
				synchronized (clazz) {
					fieldCache.put(clazz, orderedList);
				}
			}
			return orderedList;
		}
	}

	/**
	 * Sets the field cache.
	 *
	 * @param fieldCache the field cache
	 */
	static void setFieldCache(Cache<Class<?>,List<Field>> fieldCache) {
		FieldMetadataHelper.fieldCache = fieldCache;
	}

}
