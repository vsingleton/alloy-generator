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

package com.liferay.alloy.tools.builder.taglib.model;

import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.util.StringUtil;
import jodd.typeconverter.Convert;
import org.dom4j.Element;

public class TagComponent extends Component {

	public void initialize(Element tagComponentElement, String defaultPackage) {
		super.initialize(tagComponentElement, defaultPackage);

		String parentClass = Convert.toString(
			tagComponentElement.attributeValue("parentClass"),
			_COMPONENT_DEFAULT_PARENT_CLASS);
		setParentClass(parentClass);

		_className = Convert.toString(
			tagComponentElement.attributeValue("className"), null);
		_dynamicAttributes = Convert.toBoolean(
			tagComponentElement.attributeValue("dynamicAttributes"), true);
		_writeJSP = Convert.toBoolean(
			tagComponentElement.attributeValue("writeJSP"), true);
	}

	public void setClassName(String className) {
		this._className = className;
	}

	public String getClassName() {
		String className = _className;

		if (StringUtil.isBlank(className)) {
			className = getSafeName().concat(_CLASS_NAME_SUFFIX);
		}

		return className;
	}
	
	public void setDynamicAttributes(boolean dynamicAttributes) {
		_dynamicAttributes = dynamicAttributes;
	}

	public boolean isDynamicAttributes() {
		return _dynamicAttributes;
	}

	public void setWriteJSP(boolean writeJSP) {
		_writeJSP = writeJSP;
	}

	public boolean isWriteJSP() {
		return _writeJSP;
	}

	private final static String _CLASS_NAME_SUFFIX = "Tag";

	private static final String _COMPONENT_DEFAULT_PARENT_CLASS =
		"com.liferay.taglib.util.IncludeTag";

	private String _className;
	private boolean _dynamicAttributes;
	private boolean _writeJSP;
}
