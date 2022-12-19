package com.digital.v3.utils;

public class StringUtils {

	public static String isEmpty(String value) {
		
		if (value == null) {
			return null;
		}
		
		value = value.trim();
		if ("".equals(value)) {
			return null;
		}
		
		if ("null".equals(value)) {
			return null;
		}
		
		return value;
	}
}
