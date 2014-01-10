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
import com.liferay.alloy.tools.model.Component;

import java.util.Arrays;
import java.util.List;

import jodd.util.StringPool;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public class ReservedAttributeUtil {

	public static final List<String> RESERVED_ATTRIBUTES = Arrays.asList(
		new String[] {
			"values", "value", "servletRequest", "servletResponse",
			"servletContext", "scopedAttribute", "scopedAttributes",
			"previousOut", "parent", "namespacedAttribute",
			"attributeNamespace", "bodyContent", "class", "dynamicAttribute",
			"dynamicAttributes", "id", "scriptPosition", "page", "locale", 
			"attributes", "children"
		}
	);

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

		Component component = attribute.getComponent();

		if (isReserved(attribute) && (component != null)) {
			String componentName = component.getName();

			name = componentName.toLowerCase().concat(
				StringUtil.capitalize(name));
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