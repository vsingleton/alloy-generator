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

package com.liferay.alloy.util.converter;

import java.math.BigDecimal;

import jodd.typeconverter.TypeConverter;
public class NumberTypeConverter implements TypeConverter<Number> {

	@Override
	public Number convert(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof String) {
			String valueString = (String)value;

			try {
				return new BigDecimal(valueString.trim());
			}
			catch (NumberFormatException nfe) {
				return null;
			}
		}

		Class<?> clazz = value.getClass();

		if (Byte.class.isAssignableFrom(clazz)) {
			return (Byte)value;
		}
		else if (Double.class.isAssignableFrom(clazz)) {
			return (Double)value;
		}
		else if (Float.class.isAssignableFrom(clazz)) {
			return (Float)value;
		}
		else if (Integer.class.isAssignableFrom(clazz)) {
			return (Integer)value;
		}
		else if (Long.class.isAssignableFrom(clazz)) {
			return (Long)value;
		}
		else if (Short.class.isAssignableFrom(clazz)) {
			return (Short)value;
		}

		if (value instanceof Number) {
			return (Number)value;
		}

		return null;
	}

}