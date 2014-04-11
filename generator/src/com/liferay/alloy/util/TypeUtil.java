/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.alloy.util;

import java.util.ArrayList;
import java.util.HashMap;

import jodd.util.StringPool;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public class TypeUtil {

	public static final String COMPLEX_BOOLEAN = "ComplexBoolean";
	public static final String COMPLEX_NUMBER = "ComplexNumber";

	public static final String ARRAY_NOTATION = "[]";

	public static final String[] ARRAYS = {
		"array", "[]"
	};

	public static final String BOOLEAN = "boolean";

	public static final String[] BOOLEANS = {
		"boolean", "bool"
	};

	public static final String DOUBLE = "double";

	public static final String[] DOUBLES = {
		"double"
	};

	public static final String FLOAT = "float";

	public static final String[] FLOATS = {
		"float"
	};

	public static final String INT = "int";

	public static final String[] INTEGERS = {
		"integer", "int", "int | string"
	};

	public static final String LONG = "long";

	public static final String[] LONGS = {
		"long"
	};

	public static final String NUMBER ="number";

	public static final String[] NUMBERS = {
		"num", "number"
	};

	public static final String[] OBJECTS = {
		"object", "{}"
	};

	public static final String SHORT = "short";

	public static final String[] SHORTS = {
		"short"
	};

	public static final String STRING = "string";

	public static final String[] STRINGS = {
		"node | string", "string", "string | node", "string | int"
	};

	public static String getInputJavaType(
		String type, boolean removeGenericsType) {

		if (_instance == null) {
			_instance = new TypeUtil();
		}

		return _instance._getInputJavaType(type, removeGenericsType);
	}

	public static String getJavaWrapperType(String type) {

		String javaWrapperType = type;

		if (TypeUtil.isPrimitiveType(javaWrapperType)) {
			if (javaWrapperType.equals("int")) {
				javaWrapperType = "Integer";
			}
			else {
				javaWrapperType = StringUtil.capitalize(javaWrapperType);
			}
		}
		
		javaWrapperType = removeJavaPrefix(javaWrapperType);
		
		return javaWrapperType;
	}

	public static String getFacesJavaScriptType(String javaScriptType) {

		String facesJavaScriptType = javaScriptType;
		if (facesJavaScriptType != null) {

			if (facesJavaScriptType.contains("|")) {

				// TODO maybe add logic for functions
				boolean containsString = facesJavaScriptType.contains(StringUtil.capitalize(STRING)) || facesJavaScriptType.contains(STRING);
				boolean containsBoolean = facesJavaScriptType.contains(StringUtil.capitalize(BOOLEAN)) || facesJavaScriptType.contains(BOOLEAN);
				boolean containsNumber = facesJavaScriptType.contains(StringUtil.capitalize(NUMBER)) || facesJavaScriptType.contains(NUMBER);

				if (containsString && containsBoolean) {	
					facesJavaScriptType = COMPLEX_BOOLEAN;
				} else if (containsString && containsNumber) {
					facesJavaScriptType = COMPLEX_NUMBER;
				} else if (containsString) {
					facesJavaScriptType = getJavaScriptType(StringUtil.capitalize(STRING));
				} else if (containsBoolean) {
					facesJavaScriptType = getJavaScriptType(StringUtil.capitalize(BOOLEAN));
				} else if (containsNumber) {
					facesJavaScriptType = getJavaScriptType(StringUtil.capitalize(NUMBER));
				} else {
					facesJavaScriptType = getJavaScriptType("Object");
				}
			} else {
				facesJavaScriptType = getJavaScriptType(facesJavaScriptType.replace("java.lang.", ""));
			}
		}

		return facesJavaScriptType;
	}

	public static String getJavaScriptType(String type) {
		if (_instance == null) {
			_instance = new TypeUtil();
		}

		return _instance._getJavaScriptType(type);
	}

	public static String getOutputJavaType(
		String type, boolean removeGenericsType) {

		if (_instance == null) {
			_instance = new TypeUtil();
		}

		return _instance._getOutputJavaType(type, removeGenericsType);
	}

	public static String removeJavaPrefix(String type) {
		return type.replace("java.lang.", "");
	}

	public static boolean isPrimitiveType(String type) {
		return (TypeUtil.BOOLEAN.equals(type) || TypeUtil.DOUBLE.equals(type) ||
				TypeUtil.FLOAT.equals(type) || TypeUtil.INT.equals(type) ||
				TypeUtil.LONG.equals(type) || TypeUtil.SHORT.equals(type));
	}

	private TypeUtil() {
		_INPUT_TYPES = new HashMap<String, String>();
		_JAVASCRIPT_TYPES = new HashMap<String, String>();
		_OUTPUT_TYPES = new HashMap<String, String>();

		_registerTypes(_INPUT_TYPES, ARRAYS, Object.class.getName());
		_registerTypes(_INPUT_TYPES, BOOLEANS, boolean.class.getName());
		_registerTypes(_INPUT_TYPES, FLOATS, float.class.getName());
		_registerTypes(_INPUT_TYPES, INTEGERS, int.class.getName());
		_registerTypes(_INPUT_TYPES, DOUBLES, double.class.getName());
		_registerTypes(_INPUT_TYPES, LONGS, long.class.getName());
		_registerTypes(_INPUT_TYPES, SHORTS, short.class.getName());
		_registerTypes(_INPUT_TYPES, NUMBERS, Object.class.getName());
		_registerTypes(_INPUT_TYPES, OBJECTS, Object.class.getName());
		_registerTypes(_INPUT_TYPES, STRINGS, String.class.getName());

		_registerTypes(_JAVASCRIPT_TYPES, ARRAYS, "Array");
		_registerTypes(
			_JAVASCRIPT_TYPES, BOOLEANS, Boolean.class.getSimpleName());
		_registerTypes(_JAVASCRIPT_TYPES, FLOATS, Number.class.getSimpleName());
		_registerTypes(
			_JAVASCRIPT_TYPES, INTEGERS, Number.class.getSimpleName());
		_registerTypes(
			_JAVASCRIPT_TYPES, DOUBLES, Number.class.getSimpleName());
		_registerTypes(_JAVASCRIPT_TYPES, LONGS, Number.class.getSimpleName());
		_registerTypes(_JAVASCRIPT_TYPES, SHORTS, Number.class.getSimpleName());
		_registerTypes(
			_JAVASCRIPT_TYPES, NUMBERS, Number.class.getSimpleName());
		_registerTypes(
			_JAVASCRIPT_TYPES, OBJECTS, Object.class.getSimpleName());
		_registerTypes(
			_JAVASCRIPT_TYPES, STRINGS, String.class.getSimpleName());

		_registerTypes(_OUTPUT_TYPES, ARRAYS, ArrayList.class.getName());
		_registerTypes(_OUTPUT_TYPES, BOOLEANS, boolean.class.getName());
		_registerTypes(_OUTPUT_TYPES, FLOATS, float.class.getName());
		_registerTypes(_OUTPUT_TYPES, INTEGERS, int.class.getName());
		_registerTypes(_OUTPUT_TYPES, DOUBLES, double.class.getName());
		_registerTypes(_OUTPUT_TYPES, LONGS, long.class.getName());
		_registerTypes(_OUTPUT_TYPES, SHORTS, short.class.getName());
		_registerTypes(_OUTPUT_TYPES, NUMBERS, Number.class.getName());
		_registerTypes(_OUTPUT_TYPES, OBJECTS, HashMap.class.getName());
		_registerTypes(_OUTPUT_TYPES, STRINGS, String.class.getName());
	}

	private String _removeCurlyBraces(String type) {
		return type.replace("{", "").replace("}", "");
	}

	private String _getGenericsType(String type) {
		int begin = type.indexOf(StringPool.LEFT_CHEV);
		int end = type.indexOf(StringPool.RIGHT_CHEV);

		String genericsType = null;

		if ((begin > -1) && (end > -1)) {
			genericsType = type.substring(begin + 1, end);
		}

		return genericsType;
	}

	private String _getInputJavaType(String type, boolean removeGenericsType) {
		if (removeGenericsType) {
			type = _removeGenericsType(type);
		}

		if (_isJavaClass(type)) {
			return type;
		}

		String javaType = _INPUT_TYPES.get(type.toLowerCase());

		if (StringUtil.isBlank(javaType)) {
			javaType = Object.class.getName();
		}

		return javaType;
	}

	private String _getJavaScriptType(String type) {
		String javaScriptType = _JAVASCRIPT_TYPES.get(_removeCurlyBraces(type.toLowerCase()));

		if (StringUtil.isBlank(javaScriptType)) {
			javaScriptType = String.class.getSimpleName();
		}

		return javaScriptType;
	}

	private String _getOutputJavaType(String type, boolean removeGenericsType) {
		if (removeGenericsType) {
			type = _removeGenericsType(type);
		}

		if (_isJavaClass(type)) {
			return type;
		}

		String javaType = _OUTPUT_TYPES.get(type.toLowerCase());

		if (StringUtil.isBlank(javaType)) {
			javaType = Object.class.getName();
		}

		return javaType;
	}

	private boolean _isJavaClass(String type) {
		if (isPrimitiveType(type)) {
			return true;
		}
		else {
			try {
				String genericsType = _getGenericsType(type);

				if (StringUtil.isNotBlank(genericsType)) {
					String[] genericsTypes = genericsType.split(
						StringPool.COMMA);

					for (int i = 0; i < genericsTypes.length; i++) {
						String curType = genericsTypes[i].trim();

						if (!curType.equals(StringPool.QUESTION_MARK)) {
							Class.forName(_removeArrayNotation(curType));
						}
					}

					type = _removeGenericsType(type);
				}

				Class.forName(_removeArrayNotation(type));

				return true;
			}
			catch (ClassNotFoundException e) {
				return false;
			}
		}
	}

	private void _registerTypes(
		HashMap<String, String> map, String[] types, String javaType) {

		for (String type : types) {
			map.put(type.toLowerCase(), javaType);
		}
	}

	private String _removeArrayNotation(String type) {
		return type.replace(ARRAY_NOTATION, StringPool.EMPTY);
	}

	private String _removeGenericsType(String type) {
		String genericsType = _getGenericsType(type);

		if (StringUtil.isNotBlank(genericsType)) {
			type = type.replace(
				StringPool.LEFT_CHEV.concat(genericsType).concat(
					StringPool.RIGHT_CHEV), StringPool.EMPTY);
		}

		return type;
	}

	private static TypeUtil _instance = null;

	private static HashMap<String, String> _INPUT_TYPES = null;

	private static HashMap<String, String> _JAVASCRIPT_TYPES = null;

	private static HashMap<String, String> _OUTPUT_TYPES = null;

}