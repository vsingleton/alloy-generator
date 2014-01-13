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

package com.liferay.alloy.util;

import com.germinus.easyconf.ComponentConfiguration;
import com.germinus.easyconf.ComponentProperties;
import com.germinus.easyconf.EasyConf;

public class PropsUtil {

	public static String getString(String key) {
		return _getProperties().getString(key);
	}

	public static String getString(String key, String defaultValue) {
		return _getProperties().getString(key, defaultValue);
	}
	
	public static String[] getStringArray(String key) {
		return  _getProperties().getStringArray(key);
	}
	
	public static String[] getStringArray(String key, String[] defaultValue) {
		return _getProperties().getStringArray(key, defaultValue);
	}

	private static ComponentProperties _getProperties() {
		if (_properties == null) {
			ComponentConfiguration conf = EasyConf.getConfiguration(
				_DEFAULT_COMPONENT);

			_properties = conf.getProperties();
		}

		return _properties;
	}

	private static final String _DEFAULT_COMPONENT = "build";

	private static ComponentProperties _properties = null;

}