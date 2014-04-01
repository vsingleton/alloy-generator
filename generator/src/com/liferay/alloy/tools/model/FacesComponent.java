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

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringPool;

public class FacesComponent extends Component {

	public List<FacesAttribute> getFacesAttributes() {
		List<Attribute> attributes = super.getAttributes();
		List<FacesAttribute> facesAttributes = new ArrayList<FacesAttribute>();

		for (Attribute attribute : attributes) {
			facesAttributes.add((FacesAttribute) attribute);
		}
		
		return facesAttributes;
	}

	public List<Attribute> getFacesAttributesAndEvents() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		attributes.addAll(getFacesAttributes());
		attributes.addAll(getEvents());

		return attributes;
	}

	public String getRendererBaseClass() {
		StringBuilder sb = new StringBuilder(4);
		
		sb.append(getPackage());
		sb.append(StringPool.DOT);
		sb.append(getCamelizedName());
		sb.append("RendererBase");

		return sb.toString();
	}

	public String getRendererParentClass() {
		return _rendererParentClass;
	}
	
	public String getUnqualifiedRendererParentClass() {
		return getRendererParentClass().substring(_rendererParentClass.lastIndexOf(StringPool.DOT) + 1);
	}
	
	public boolean isRendererBaseClassRequired() {
		return getRendererParentClass().equals(getRendererBaseClass());
	}

	public void setRendererParentClass(String rendererParentClass) {
		_rendererParentClass = rendererParentClass;
	}

	@Override
	public String getDescription() {
		return removeCDATATag(super.getDescription());
	}

	protected String removeCDATATag(String string) {
		return string.replace("<![CDATA[", "").replace("]]>", "");
	}

	private String _rendererParentClass;
}
