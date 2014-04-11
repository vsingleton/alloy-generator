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

import jodd.util.StringPool;

public class StringUtil extends jodd.util.StringUtil {

	public static String removeNewlinesAndCDATA(String string) {
		return string.replace("<![CDATA[", "").replace("]]>", "").replace("\n",
				" ");
	}

	public static String toConstantName(String name) {
		return StringUtil.fromCamelCase(name, '_').toUpperCase();
	}

	public static String unquote(String s) {
		if (isBlank(s)) {
			return s;
		}

		char singleQuoteChar = StringPool.SINGLE_QUOTE.charAt(0);
		char quoteChar = StringPool.QUOTE.charAt(0);

		if ((s.charAt(0) == singleQuoteChar) &&
			(s.charAt(s.length() - 1) == singleQuoteChar)) {

			return s.substring(1, s.length() - 1);
		}
		else if ((s.charAt(0) == quoteChar) &&
				 (s.charAt(s.length() - 1) == quoteChar)) {

			return s.substring(1, s.length() - 1);
		}

		return s;
	}

}