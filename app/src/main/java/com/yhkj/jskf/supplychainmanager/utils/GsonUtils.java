package com.yhkj.jskf.supplychainmanager.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtils {
	private static Gson gson = null;
	
	static{
		gson = new Gson();
	}
	
	private GsonUtils(){
		throw new UnsupportedOperationException("cannot install this class");
	}

	/**
	 * Object -> JsonString
	 * @param t
	 * @return
	 */
	public static <T> String toJson(T t) {
		return gson.toJson(t);
	}

	/**
	 *JsonString -> 指定类型的object
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		return gson.fromJson(jsonString, clazz);
	}

	/**
	 * jsonString -> 指定类型的object
	 * @param jsonString
	 * @param typeToken
	 * @return
	 */
	public static <T> T fromJson(String jsonString,TypeToken<T> typeToken) {
		return gson.fromJson(jsonString, typeToken.getType());
	}
	
	
}
