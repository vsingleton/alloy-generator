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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringPool;

import org.apache.commons.lang.StringUtils;

import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Component;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public class ReservedAttributeUtil {

	public static String getJavaSafeName(Attribute attribute) {
		String name = attribute.getName();

		name = attribute.getSafeName();

		Component component = attribute.getComponent();

		String componentUncapitalizedName = component.getUncapitalizedName();

		if (isJavaReserved(attribute) ||
			name.equals(componentUncapitalizedName)) {

			name = name.concat(StringPool.UNDERSCORE);
		}

		return name;
	}

	public static String getJSFReservedAttributeType(String name) {

		String reservedAttributeType = null;

		if (isJSFReservedAttribute(name)) {
			reservedAttributeType = JSF_RESERVED_ATTRIBUTES_MAP.get(name);
		}

		return reservedAttributeType;
	}

	public static String getOriginalName(
		String componentName, String attributeName) {

		String originalName = StringUtil.uncapitalize(
			StringUtil.replaceFirst(
				attributeName, componentName.toLowerCase(), StringPool.EMPTY));

		if (isNamespaced(originalName)) {
			attributeName = originalName;
		}

		return attributeName;
	}

	public static String getSafeName(Attribute attribute) {
		String name = attribute.getName();

		if (isNamespaced(attribute)) {

			String capitalizedAttributeName = StringUtils.capitalize(name);

			if (NAMESPACE != null) {
				name = NAMESPACE.concat(capitalizedAttributeName);
			} else {
				Component component = attribute.getComponent();

				String componentUncapitalizedName =
						component.getUncapitalizedName();
				name = componentUncapitalizedName.concat(capitalizedAttributeName);
			}
		}

		return name;
	}

	public static boolean isJavaReserved(Attribute attribute) {
		return JAVA_RESERVED_WORDS.contains(attribute.getName());
	}

	public static boolean isJSFReservedAttribute(String name) {
		return JSF_RESERVED_ATTRIBUTES_MAP.containsKey(name);
	}

	public static boolean isNamespaced(Attribute attribute) {
		return isNamespaced(attribute.getName());
	}

	public static boolean isNamespaced(String attributeName) {
		return NAMESPACED_ATTRIBUTES.contains(attributeName);
	}

	public static final List<String> JAVA_RESERVED_WORDS = Arrays.asList(
		PropsUtil.getStringArray("builder.java.reserved.words"));

	public static final Map<String, String> JSF_RESERVED_ATTRIBUTES_MAP;

	public static final String NAMESPACE = PropsUtil.getString("builder.namespace", null);

	public static final List<String> NAMESPACED_ATTRIBUTES = Arrays.asList(
		PropsUtil.getStringArray("builder.namespaced.attributes"));

	private static final List<String> _JSF_RESERVED_ATTRIBUTES_AND_TYPES = Arrays.asList(
			PropsUtil.getStringArray("builder.jsf.reserved.attributes"));

	static {

		Map<String, String> jsfReservedAttributesMap = null;

		if (_JSF_RESERVED_ATTRIBUTES_AND_TYPES != null) {

			jsfReservedAttributesMap = new HashMap<String, String>();

			for (String keyValuePair : _JSF_RESERVED_ATTRIBUTES_AND_TYPES) {
				String[] pair = keyValuePair.split("\\|");
				jsfReservedAttributesMap.put(pair[0], pair[1]);
			}
		}

		JSF_RESERVED_ATTRIBUTES_MAP = jsfReservedAttributesMap;
	}

}