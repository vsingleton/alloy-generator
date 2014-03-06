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

import com.liferay.alloy.util.StringUtil;

public class Event extends Attribute {

	public Event(Attribute attribute) {
		setComponent(attribute.getComponent());
		setDefaultValue(attribute.getDefaultValue());
		setDescription(attribute.getDescription());
		setGettable(attribute.isGettable());
		setInputType(attribute.getInputType());
		setJavaScriptType(attribute.getJavaScriptType());
		setName(attribute.getName());
		setOutputType(attribute.getOutputType());
		setRequired(attribute.isRequired());
		setSettable(attribute.isSettable());
		setBeanPropertyRequired(true);
	}

	public Event(Attribute attribute, boolean isAfter) {
		this(attribute);

		setAfter(isAfter);
	}

	public boolean isAfter() {
		return _isAfter;
	}

	public boolean isOn() {
		return _isOn;
	}

	public String getUnprefixedName() {
		return _unprefixedName;
	}
	
	public boolean createConstant() {
		return _isOn;
	}

	public void setAfter(boolean after) {
		_isAfter = after;
		_isOn = !after;
	}

	public String getConstantUnprefixedName() {
		return StringUtil.fromCamelCase(_unprefixedName, '_').toUpperCase();
	}

	public void setOn(boolean on) {
		_isAfter = !on;
		_isOn = on;
	}

	public void setUnprefixedName(String unprefixedName) {
		this._unprefixedName = unprefixedName;
	}

	private boolean _isAfter = false;
	private boolean _isOn = false;
	private String _unprefixedName;

}