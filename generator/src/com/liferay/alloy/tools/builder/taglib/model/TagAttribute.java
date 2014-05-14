/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

	@Override
	public void initialize(Element attributeElement, Component component) {

		super.initialize(attributeElement, component);

		String javaScriptType = getJavaScriptType();
		String defaultInputType = getType();
		String defaultOutputType = getType();
		if (javaScriptType != null) {
			defaultInputType = TypeUtil.getInputJavaType(
						javaScriptType, true);
			defaultOutputType = TypeUtil.getOutputJavaType(
						javaScriptType, true);
		}

		_inputType = Convert.toString(
			attributeElement.elementText("inputType"), defaultInputType);
		_outputType = Convert.toString(
			attributeElement.elementText("outputType"), defaultOutputType);
	}

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

	public void setInputType(String inputType) {
		_inputType = inputType;
	}

	public void setOutputType(String outputType) {
		_outputType = outputType;
	}
	
	private String _inputType;
	private String _outputType;
	
}
