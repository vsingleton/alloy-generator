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

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringPool;
public class Component extends BaseModel {

	public List<Event> getAfterEvents() {
		List<Event> afterEvents = new ArrayList<Event>();

		for (Event event : getEvents()) {
			if (event.isAfter()) {
				afterEvents.add(event);
			}
		}

		return afterEvents;
	}

	public String getAttributeNamespace() {
		StringBuilder sb = new StringBuilder();

		sb.append(_package);
		sb.append(StringPool.COLON);
		sb.append(getUncamelizedName());
		sb.append(StringPool.COLON);

		return sb.toString().toLowerCase();
	}

	public List<Attribute> getAttributes() {
		return _attributes;
	}

	public List<Attribute> getAttributesAndEvents() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		attributes.addAll(_attributes);
		attributes.addAll(_events);

		return attributes;
	}

	public String[] getAuthors() {
		return _authors;
	}

	public String getCamelizedName() {
		return StringUtil.toCamelCase(
			getName(), true, StringPool.DASH.charAt(0));
	}

	public String getClassName() {
		String className = _className;

		if (StringUtil.isBlank(className)) {
			className = getSafeName().concat(_CLASS_NAME_SUFFIX);
		}

		return className;
	}

	public List<Event> getEvents() {
		return _events;
	}

	public String getInterface() {
		return _componentInterface;
	}

	public String getModule() {
		return _module;
	}
	
	public String getModuleString() {
		if (_module == null) {
			return "null";
		} else {
			return StringPool.QUOTE.concat(_module).concat(StringPool.QUOTE);
		}
	}

	public List<Event> getOnEvents() {
		List<Event> onEvents = new ArrayList<Event>();

		for (Event event : getEvents()) {
			if (event.isOn()) {
				onEvents.add(event);
			}
		}

		return onEvents;
	}

	public String getPackage() {
		return _package;
	}

	public String getParentClass() {
		return _parentClass;
	}

	public String getSafeName() {
		return StringUtil.replace(getName(), StringPool.DOT, StringPool.EMPTY);
	}

	public String getUncamelizedName() {
		String name = getName().replaceAll("\\.", StringPool.DASH);

		return StringUtil.fromCamelCase(name, StringPool.DASH.charAt(0));
	}

	public String getUncamelizedName(String delimiter) {
		return getUncamelizedName().replaceAll("\\-", delimiter);
	}

	public String getUncapitalizedName() {
		return StringUtil.uncapitalize(getCamelizedName());
	}

	public boolean getWriteJSP() {
		return _writeJSP;
	}

	public boolean isAlloyComponent() {
		return _alloyComponent;
	}

	public boolean isBodyContent() {
		return _bodyContent;
	}

	public boolean isChildClassOf(String className) {
		try {
			String parentClassName = getParentClass();

			if (StringUtil.isNotEmpty(parentClassName)) {
				Class<?> parentClass = Class.forName(parentClassName);

				Class<?> clazz = Class.forName(className);

				return clazz.isAssignableFrom(parentClass);
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean isDynamicAttributes() {
		return _dynamicAttributes;
	}

	public void setAlloyComponent(boolean alloyComponent) {
		_alloyComponent = alloyComponent;
	}

	public void setAttributes(List<Attribute> attributes) {
		_attributes = attributes;

		for (Attribute attribute : attributes) {
			attribute.setComponent(this);
		}
	}

	public void setAuthors(String[] authors) {
		_authors = authors;
	}

	public void setBodyContent(boolean bodyContent) {
		_bodyContent = bodyContent;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDynamicAttributes(boolean dynamicAttributes) {
		_dynamicAttributes = dynamicAttributes;
	}

	public void setEvents(List<Event> events) {
		_events = events;

		for (Attribute event : events) {
			event.setComponent(this);
		}
	}

	public void setInterface(String componentInterface) {
		_componentInterface = componentInterface;
	}

	public void setModule(String type) {
		_module = type;
	}

	public void setPackage(String componentPackage) {
		_package = componentPackage;
	}

	public void setParentClass(String parentClass) {
		_parentClass = parentClass;
	}

	public void setWriteJSP(boolean writeJSP) {
		_writeJSP = writeJSP;
	}

	private final static String _CLASS_NAME_SUFFIX = "Tag";

	private boolean _alloyComponent;
	private List<Attribute> _attributes;
	private String[] _authors;
	private boolean _bodyContent;
	private String _className;
	private String _componentInterface;
	private boolean _dynamicAttributes;
	private List<Event> _events;
	private String _module;
	private String _package;
	private String _parentClass;
	private boolean _writeJSP;

}