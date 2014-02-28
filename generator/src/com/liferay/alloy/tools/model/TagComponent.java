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

public class TagComponent extends Component {

	public void setClassName(String className) {
		_className = className;
	}

	public String getClassName() {
		String className = _className;

		if (StringUtil.isBlank(className)) {
			className = getSafeName().concat(_CLASS_NAME_SUFFIX);
		}

		return className;
	}

	public void setWriteJSP(boolean writeJSP) {
		_writeJSP = writeJSP;
	}

	public boolean getWriteJSP() {
		return _writeJSP;
	}

	private final static String _CLASS_NAME_SUFFIX = "Tag";

	private String _className;
	private boolean _writeJSP;
}
