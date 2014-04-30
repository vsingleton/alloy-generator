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
		setGenerateJava(attribute.isGenerateJava());
		setGettable(attribute.isGettable());
		setInputType(attribute.getInputType());
		setJavaScriptType(attribute.getJavaScriptType());
		setName(attribute.getName());
		setOutputType(attribute.getOutputType());
		setRequired(attribute.isRequired());
		setSettable(attribute.isSettable());
	}

	public Event(Attribute attribute, boolean isAfter) {
		this(attribute);
		setAfter(isAfter);

		setUnprefixedName(getSafeName());
		String capitalizedName = StringUtil.capitalize(getSafeName());

		if (isAfter) {
			setName(_AFTER + capitalizedName);
		} else {
			setName(_ON + capitalizedName);
		}
	}

	public boolean createConstant() {
		return _isOn || !(_isOn || _isAfter);
	}

	public boolean isAfter() {
		return _isAfter;
	}

	public String getConstantUnprefixedName() {
		return StringUtil.toConstantName(_unprefixedName);
	}

	public boolean isOn() {
		return _isOn;
	}

	public String getUnprefixedName() {
		return _unprefixedName;
	}

	public void setUnprefixedName(String _unprefixedName) {
		this._unprefixedName = _unprefixedName;
	}

	@Override
	public String getJavaWrapperType() {
		return "String";
	}

	public void setAfter(boolean after) {
		_isAfter = after;
		_isOn = !after;
	}

	public void setOn(boolean on) {
		_isAfter = !on;
		_isOn = on;
	}

	private static final String _AFTER = "after";
	private static final String _ON = "on";

	private boolean _isAfter = false;
	private boolean _isOn = false;
	private String _unprefixedName;
}