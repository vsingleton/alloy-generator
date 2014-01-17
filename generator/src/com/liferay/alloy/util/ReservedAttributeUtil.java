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

import com.liferay.alloy.tools.model.Attribute;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import jodd.util.StringPool;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public class ReservedAttributeUtil {

	public static final Set<String> JAVA_RESERVED_WORDS_SET = 
		new HashSet<String>(Arrays.asList(
			new String[] {
				"abstract", "assert", "boolean", "break", "byte", "case",
				"catch", "char", "class", "const", "continue", "default",
				"do", "double", "else", "enum", "extends", "final",
				"finally", "float", "for", "goto", "if", "implements",
				"import", "instanceof", "int", "interface", "long",
				"native", "new", "package", "private", "protected",
				"public", "return", "short", "static", "strictfp", "super",
				"switch", "synchronized", "this", "throw", "throws",
				"transient", "try", "void", "volatile", "while" 
			}
		));

	public static final List<String> RESERVED_ATTRIBUTES = Arrays.asList(
		new String[] {
			"values", "value", "servletRequest", "servletResponse",
			"servletContext", "scopedAttribute", "scopedAttributes",
			"previousOut", "parent", "namespacedAttribute",
			"attributeNamespace", "bodyContent", "class", "dynamicAttribute",
			"dynamicAttributes", "id", "scriptPosition", "page", "locale",
			"attributes", "children", "clientId"
		}
	);

	public static boolean isJavaReserved(Attribute attribute) {
		return JAVA_RESERVED_WORDS_SET.contains(attribute.getName());
	}

	public static String getOriginalName(
		String componentName, String attributeName) {

		String originalName = StringUtil.uncapitalize(
			StringUtil.replaceFirst(
				attributeName, componentName.toLowerCase(), StringPool.EMPTY));

		if (isReserved(originalName)) {
			attributeName = originalName;
		}

		return attributeName;
	}

	public static String getSafeName(Attribute attribute) {
		String name = attribute.getName();
		
		if (isJavaReserved(attribute)) {
			name = name.concat(StringPool.UNDERSCORE);
		}
		else if(isReserved(attribute)) {
			String componentUncapitalizedName = attribute.getComponent().getUncapitalizedName();
			String capitalizedAttributeName = StringUtils.capitalize(name);
			name = componentUncapitalizedName.concat(capitalizedAttributeName);
		}

		return name;
	}

	public static boolean isReserved(Attribute attribute) {
		return isReserved(attribute.getName());
	}

	public static boolean isReserved(String attributeName) {
		return RESERVED_ATTRIBUTES.contains(attributeName);
	}

}