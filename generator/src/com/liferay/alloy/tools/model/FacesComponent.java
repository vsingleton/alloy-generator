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

	public boolean isComponentBaseClassRequired() {
		return _componentBaseClassRequired;
	}

	public void setComponentBaseClassRequired(boolean componentBaseClassRequired) {
		this._componentBaseClassRequired = componentBaseClassRequired;
	}

	public void setRendererParentClass(String rendererParentClass) {
		_rendererParentClass = rendererParentClass;
	}

	private boolean _componentBaseClassRequired;
	private String _rendererParentClass;
}
