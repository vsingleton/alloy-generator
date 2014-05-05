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

package com.liferay.alloy.tools.builder.faces.model;

import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.util.ReservedAttributeUtil;
import com.liferay.alloy.util.StringUtil;
import com.liferay.alloy.util.TypeUtil;
import jodd.typeconverter.Convert;
import org.dom4j.Element;

public class FacesAttribute extends Attribute {

	@Override
	public void initialize(Element facesAttributeElement, Component component) {
		super.initialize(facesAttributeElement, component);

		_getterDefaultReturnValue = Convert.toString(
			facesAttributeElement.elementText("getterDefaultReturnValue"),
			"null");
		_methodSignature = facesAttributeElement
				.elementText("method-signature");
		_outputUnsafe = Convert.toBoolean(facesAttributeElement
			.elementText("outputUnsafe"), false);
		
		String type = Convert.toString(
				facesAttributeElement.elementText("type"), DEFAULT_TYPE);
		String rawJavaScriptType = Convert.toString(
			facesAttributeElement.elementText("rawJavaScriptType"), type);
		setJavaScriptType(rawJavaScriptType);

		boolean jsfReservedAttributeDefault =
			ReservedAttributeUtil.isJSFReservedAttribute(getName());
		_jsfReservedAttribute = Convert.toBoolean(facesAttributeElement
			.elementText("jsfReservedAttribute"), jsfReservedAttributeDefault);

		_jsfReservedAttributeType = type;

		if (_jsfReservedAttribute) {

			String jsfReservedAttributeTypeDefault =
				ReservedAttributeUtil.getJSFReservedAttributeType(getName());
			_jsfReservedAttributeType = Convert.toString(facesAttributeElement
				.elementText("jsfReservedAttributeType"),
				jsfReservedAttributeTypeDefault);
			_jsfReservedAttributeType = TypeUtil.removeJavaPrefix(_jsfReservedAttributeType);
		}
	}

	public String getCapitalizedJSFReservedAttributeType() {
		return StringUtil.capitalize(_jsfReservedAttributeType);
	}

	@Override
	public String getJavaScriptType() {
		return TypeUtil.getFacesJavaScriptType(getRawJavaScriptType());
	}

	@Override
	public String getJavaWrapperType() {

		String javaWrapperType;

		if (_jsfReservedAttribute) {
			javaWrapperType = getCapitalizedJSFReservedAttributeType();
		} else {
			javaWrapperType = getJavaScriptType();

			if (javaWrapperType.equals(TypeUtil.COMPLEX_BOOLEAN)
					|| javaWrapperType.equals(TypeUtil.COMPLEX_NUMBER)) {
				javaWrapperType = "Object";
			}

			javaWrapperType = TypeUtil.getInputJavaType(javaWrapperType, true);
			javaWrapperType = TypeUtil.getJavaWrapperType(javaWrapperType);
		}

		return javaWrapperType;
	}

	public String getJSFReservedAttributeType() {
		return _jsfReservedAttributeType;
	}

	public String getMethodSignature() {
		return _methodSignature;
	}

	@Override
	public String getSafeName() {
		if (_outputUnsafe || !isGenerateJava() || isJSFReservedAttribute()) {
			return super.getName();
		} else {
			return super.getSafeName();
		}
	}

	public boolean isJSFReservedAttribute() {
		return _jsfReservedAttribute;
	}

	public boolean isOutputUnsafe() {
		return _outputUnsafe;
	}

	public void setJSFReservedAttribute(boolean _jsfReservedAttribute) {
		this._jsfReservedAttribute = _jsfReservedAttribute;
	}

	public void setJSFReservedAttributeType(String _jsfReservedAttributeType) {
		this._jsfReservedAttributeType = _jsfReservedAttributeType;
	}

	public void setMethodSignature(String methodSignature) {
		_methodSignature = methodSignature;
	}

	public void setOutputUnsafe(boolean _outputUnsafe) {
		this._outputUnsafe = _outputUnsafe;
	}

	public String getGetterDefaultReturnValue() {
		return _getterDefaultReturnValue;
	}

	public void setGetterDefaultReturnValue(String _getterDefaultReturnValue) {
		this._getterDefaultReturnValue = _getterDefaultReturnValue;
	}
	
	private String _getterDefaultReturnValue;
	private boolean _jsfReservedAttribute;
	private String _jsfReservedAttributeType;
	private String _methodSignature;
	private boolean _outputUnsafe;
}
