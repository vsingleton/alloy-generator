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

package com.liferay.alloy.tools.model;

public class FacesAttribute extends Attribute {

	public String getGetterMethodPrefix() {

		String getterMethodPrefix = "get";

		if (getJavaWrapperInputType().equals("Boolean")) {
			getterMethodPrefix = "is";
		}

		return getterMethodPrefix;
	}

	public boolean isBeanPropertyRequired() {
		return _beanPropertyRequired;
	}

	public void setBeanPropertyRequired(boolean beanPropertyRequired) {
		_beanPropertyRequired = beanPropertyRequired;
	}

	public String getMethodSignature() {
		return _methodSignature;
	}

	public void setMethodSignature(String methodSignature) {
		_methodSignature = methodSignature;
	}

	private boolean _beanPropertyRequired;
	private String _methodSignature;
}
