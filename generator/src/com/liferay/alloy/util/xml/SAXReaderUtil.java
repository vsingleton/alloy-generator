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

package com.liferay.alloy.util.xml;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import org.xml.sax.InputSource;
public class SAXReaderUtil {

	public static DocumentFactory getDocumentFactory() {
		return getSAXReader().getDocumentFactory();
	}

	public static SAXReader getSAXReader() {
		if (_saxReader == null) {
			_saxReader = new SAXReader();
		}

		return _saxReader;
	}

	public static Document read(ByteArrayInputStream byteArrayInputStream)
		throws DocumentException {

		return getSAXReader().read(byteArrayInputStream);
	}

	public static Document read(File file) throws DocumentException {
		return getSAXReader().read(file);
	}

	public static Document read(InputSource inputSource)
		throws DocumentException {

		return getSAXReader().read(inputSource);
	}

	private static SAXReader _saxReader;

}