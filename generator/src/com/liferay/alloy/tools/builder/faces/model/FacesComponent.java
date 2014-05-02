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

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringPool;

import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.util.PropsUtil;
import jodd.typeconverter.Convert;
import org.dom4j.Element;

public class FacesComponent extends Component {

	@Override
	public void initialize(Element facesComponentElement,
			String defaultPackage) {
		super.initialize(facesComponentElement, defaultPackage);

		_styleable = Convert.toBoolean(
			facesComponentElement.attributeValue("styleable"), true);

		boolean alloyComponent = Convert.toBoolean(
			facesComponentElement.attributeValue("alloyComponent"), false);
		setAlloyComponent(alloyComponent);

		boolean generateJava = Convert.toBoolean(
			facesComponentElement.attributeValue("generateJava"), true);
		setGenerateJava(generateJava);

		String parentClass = Convert.toString(
			facesComponentElement.attributeValue("parentClass"),
			_COMPONENT_DEFAULT_PARENT_CLASS);
		setParentClass(parentClass);

		String preferredName = Convert.toString(
			facesComponentElement.attributeValue("preferredName"), null);

		if (preferredName != null) {
			setName(preferredName);
		}

		String defaultRendererParentClass = _DEFAULT_RENDERER_BASE_CLASS;

		if (isAlloyComponent() && _DEFAULT_ALLOY_RENDERER_PARENT_CLASS != null
				&& _DEFAULT_ALLOY_RENDERER_PARENT_CLASS.length() > 0) {
			defaultRendererParentClass = _DEFAULT_ALLOY_RENDERER_PARENT_CLASS;
		}

		_rendererParentClass = Convert.toString(
			facesComponentElement.attributeValue("rendererParentClass"),
			defaultRendererParentClass);
	}

	@Override
	protected List<Attribute> getAttributesFromElements(List<Element> attributeElements) {

		List<Attribute> attributes = new ArrayList<Attribute>();

		for (Element attributeElement : attributeElements) {
			FacesAttribute facesAttribute = new FacesAttribute();
			facesAttribute.initialize(attributeElement, this);
			attributes.add(facesAttribute);
		}

		return attributes;
	}

	private String _getDefaultAlloyRendererBaseClass() {
		StringBuilder sb = new StringBuilder(4);

		sb.append(getPackage());
		sb.append(StringPool.DOT);
		sb.append(getCamelizedName());
		sb.append("RendererBase");

		return sb.toString();
	}

	
	public String getRendererBaseClass() {

		String rendererBaseClass = getRendererParentClass();

		if (isAlloyComponent()) {
			rendererBaseClass = _getDefaultAlloyRendererBaseClass();
		}

		return rendererBaseClass;
	}

	public String getUnqualifiedRendererBaseClass() {
		return getRendererBaseClass().substring(
				getRendererBaseClass().lastIndexOf(StringPool.DOT) + 1);
	}

	public String getRendererParentClass() {
		return _rendererParentClass;
	}

	public String getUnqualifiedRendererParentClass() {
		return getRendererParentClass().substring(
				getRendererParentClass().lastIndexOf(StringPool.DOT) + 1);
	}

	public void setRendererParentClass(String rendererParentClass) {
		_rendererParentClass = rendererParentClass;
	}

	public boolean isStyleable() {
		return _styleable;
	}

	public void setStyleable(boolean _styleable) {
		this._styleable = _styleable;
	}

	private static final String _DEFAULT_RENDERER_BASE_CLASS =
		"javax.faces.render.Renderer";

	private static final String _COMPONENT_DEFAULT_PARENT_CLASS =
			PropsUtil.getString("builder.faces.component.default.parent.class");

	private static final String _DEFAULT_ALLOY_RENDERER_PARENT_CLASS =
		PropsUtil.getString("builder.faces.default.alloy.renderer.parent.class");

	private boolean _styleable;
	private String _rendererParentClass;
}
