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

import jodd.util.StringPool;

public class FacesComponent extends Component {

	public String getDefaultRendererParentClass() {
		StringBuilder sb = new StringBuilder(4);
		
		sb.append(getPackage());
		sb.append(StringPool.DOT);
		sb.append(getCamelizedName());
		sb.append("RendererBase");

		return sb.toString();
	}

	public String getRendererParentClass() {
		
		if (_rendererParentClass == null) {
			_rendererParentClass = getDefaultRendererParentClass();
		}

		return _rendererParentClass;
	}
	
	public String getUnqualifiedRendererParentClass() {
		return getRendererParentClass().substring(_rendererParentClass.lastIndexOf(StringPool.DOT) + 1);
	}

	public boolean hasDefaultParentClass() {
		return getParentClass().equals(_FACES_COMPONENT_DEFAULT_PARENT_CLASS);
	}
	
	public boolean hasDefaultRendererParentClass() {
		return getRendererParentClass().equals(getDefaultRendererParentClass());
	}

	public boolean isGenerateComponentBaseClass() {
		return _generateComponentBaseClass;
	}

	public void setGenerateComponentBaseClass(boolean generateComponentBaseClass) {
		this._generateComponentBaseClass = generateComponentBaseClass;
	}

	public void setRendererParentClass(String rendererParentClass) {
		_rendererParentClass = rendererParentClass;
	}
	
	public static final String _FACES_COMPONENT_DEFAULT_PARENT_CLASS =
			"javax.faces.component.UIPanel";

	private boolean _generateComponentBaseClass;
	private String _rendererParentClass;
}
