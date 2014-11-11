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

import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.util.TypeUtil;

import jodd.typeconverter.Convert;

import org.dom4j.Element;

/**
 *
 * @author kylestiemann
 */
public class TagAttribute extends Attribute {

	public String getInputType() {
		return TypeUtil.getInputJavaType(_inputType, true);
	}

	public String getInputTypeSimpleClassName() {
		return getTypeSimpleClassName(getRawInputType());
	}

	public String getOutputType() {
		return TypeUtil.getOutputJavaType(_outputType, true);
	}

	public String getOutputTypeSimpleClassName() {
		return getTypeSimpleClassName(getRawOutputType());
	}

	public String getRawInputType() {
		return TypeUtil.getInputJavaType(_inputType, false);
	}

	public String getRawOutputType() {
		return TypeUtil.getOutputJavaType(_outputType, false);
	}

	@Override
	public void initialize(Element attributeElement, Component component) {
		super.initialize(attributeElement, component);

		String javaScriptType = getJavaScriptType();
		String defaultInputType = getType();
		String defaultOutputType = getType();

		if (javaScriptType != null) {
			defaultInputType = TypeUtil.getInputJavaType(javaScriptType, true);
			defaultOutputType = TypeUtil.getOutputJavaType(
						javaScriptType, true);
		}

		_inputType = Convert.toString(
			attributeElement.elementText("inputType"), defaultInputType);
		_outputType = Convert.toString(
			attributeElement.elementText("outputType"), defaultOutputType);
	}

	public void setInputType(String inputType) {
		_inputType = inputType;
	}

	public void setOutputType(String outputType) {
		_outputType = outputType;
	}

	private String _inputType;
	private String _outputType;

}