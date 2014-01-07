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

package com.liferay.alloy.tools.builder;

import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.util.FreeMarkerUtil;
import com.liferay.alloy.util.StringUtil;
import com.liferay.alloy.util.xml.SAXReaderUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jodd.io.FileUtil;

import jodd.typeconverter.Convert;

import jodd.util.StringPool;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import org.xml.sax.InputSource;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public abstract class BaseBuilder {

	public static final String[] DEFAULT_AUTHORS = new String[] {
		"Eduardo Lundgren", "Bruno Basto", "Nathan Cavanaugh"
	};

	public abstract void build() throws Exception;

	public List<Document> getComponentDefinitionDocs() {
		if (_componentsDefinitionDocs == null) {
			_componentsDefinitionDocs = new ArrayList<Document>();

			for (String componentExtXML : getComponentDefinitionsList()) {
				File extFile = new File(componentExtXML);

				if (extFile.exists()) {
					try {
						_componentsDefinitionDocs.add(
							SAXReaderUtil.read(extFile));
					}
					catch (DocumentException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return _componentsDefinitionDocs;
	}

	public abstract List<String> getComponentDefinitionsList();

	protected List<Component> getAllComponents() throws Exception {
		DocumentFactory factory = SAXReaderUtil.getDocumentFactory();

		Document doc = factory.createDocument();

		String taglibsXML = "<components></components>";

		Document taglibsDoc = SAXReaderUtil.read(
			new InputSource(
				new ByteArrayInputStream(taglibsXML.getBytes("utf-8"))));

		Element root = taglibsDoc.getRootElement();

		for (Document extDoc : getComponentDefinitionDocs()) {
			Element extRoot = extDoc.getRootElement();

			String defaultPackage = extRoot.attributeValue("short-name");
			List<Element> extComponentNodes = extRoot.elements("component");

			for (Element extComponent : extComponentNodes) {
				String extComponentPackage = Convert.toString(
					extComponent.attributeValue("package"), defaultPackage);

				extComponent.addAttribute("package", extComponentPackage);
			}

			Document parentDoc = getComponentsDocByShortName(
				extDoc.getRootElement().attributeValue("extends"));

			if (parentDoc != null) {
				extDoc = mergeXMLAttributes(extDoc, parentDoc);
			}

			Element authors = extRoot.element(_AUTHORS);

			List<Element> components = extRoot.elements("component");

			for (Element component : components) {
				Element copy = component.createCopy();
				Element componentAuthors = copy.element("authors");

				if ((authors != null) && (componentAuthors == null)) {
					copy.add(authors.createCopy());
				}

				root.add(copy);
			}
		}

		doc.add(root.createCopy());

		return getComponents(doc);
	}

	protected List<Attribute> getAttributes(Element componentNode) {
		return getAttributes(componentNode, "attributes", "attribute");
	}

	protected List<Attribute> getAttributes(
		Element componentNode, String group, String nodeName) {

		List<Element> nodes = Collections.emptyList();

		List<Attribute> attributes = new ArrayList<Attribute>();

		Element node = componentNode.element(group);

		if (node != null) {
			nodes = node.elements(nodeName);
		}

		for (Element attributeNode : nodes) {
			String defaultValue = attributeNode.elementText("defaultValue");
			String description = attributeNode.elementText("description");
			String name = attributeNode.elementText("name");
			String type = Convert.toString(
				attributeNode.elementText("type"), _DEFAULT_TYPE);
			String inputType = Convert.toString(
				attributeNode.elementText("inputType"), type);
			String outputType = Convert.toString(
				attributeNode.elementText("outputType"), type);

			boolean gettable = Convert.toBoolean(
				attributeNode.elementText("gettable"), true);
			boolean required = Convert.toBoolean(
				attributeNode.elementText("required"), false);
			boolean settable = Convert.toBoolean(
				attributeNode.elementText("settable"), true);

			Attribute attribute = new Attribute();

			attribute.setDefaultValue(defaultValue);
			attribute.setDescription(description);
			attribute.setGettable(gettable);
			attribute.setInputType(inputType);
			attribute.setName(name);
			attribute.setOutputType(outputType);
			attribute.setRequired(required);
			attribute.setSettable(settable);

			attributes.add(attribute);
		}

		return attributes;
	}

	protected String[] getAuthorList(Element element) {
		List<String> authors = new ArrayList<String>();

		if (element != null) {
			Element elAuthors = element.element(_AUTHORS);

			if (elAuthors != null) {
				List<Element> authorList = elAuthors.elements(_AUTHOR);

				for (Element author : authorList) {
					authors.add(author.getText());
				}
			}
		}

		if (authors.isEmpty()) {
			return DEFAULT_AUTHORS;
		}
		else {
			return authors.toArray(new String[authors.size()]);
		}
	}

	protected Element getComponentNode(Document doc, String name) {
		List<Element> components = doc.getRootElement().elements(_COMPONENT);

		for (Element component : components) {
			if (component.attributeValue("name").equals(name)) {
				return component;
			}
		}

		return null;
	}

	protected List<Component> getComponents(Document doc) throws Exception {
		Element root = doc.getRootElement();

		List<Component> components = new ArrayList<Component>();

		String defaultPackage = root.attributeValue("short-name");
		List<Element> allComponentNodes = root.elements("component");

		for (Element node : allComponentNodes) {
			String componentPackage = Convert.toString(
				node.attributeValue("package"), defaultPackage);

			String name = node.attributeValue("name");

			boolean alloyComponent = Convert.toBoolean(
				node.attributeValue("alloyComponent"));

			boolean bodyContent = Convert.toBoolean(
				node.attributeValue("bodyContent"));

			String className = Convert.toString(
				node.attributeValue("className"));

			boolean dynamicAttributes = Convert.toBoolean(
				node.attributeValue("dynamicAttributes"), true);

			String module = Convert.toString(node.attributeValue("module"));

			String parentClass = Convert.toString(
				node.attributeValue("parentClass"), getDefaultParentClass());

			boolean writeJSP = Convert.toBoolean(
				node.attributeValue("writeJSP"), true);

			String[] authors = getAuthorList(node);
			List<Attribute> attributes = getAttributes(node);
			List<Attribute> events = getPrefixedEvents(node);

			Component component = new Component();

			component.setAlloyComponent(alloyComponent);
			component.setAttributes(attributes);
			component.setAuthors(authors);
			component.setBodyContent(bodyContent);
			component.setClassName(className);
			component.setDynamicAttributes(dynamicAttributes);
			component.setEvents(events);
			component.setModule(module);
			component.setName(name);
			component.setPackage(componentPackage);
			component.setParentClass(parentClass);
			component.setWriteJSP(writeJSP);

			components.add(component);
		}

		return components;
	}

	protected Document getComponentsDocByShortName(String name) {
		for (Document doc : getComponentDefinitionDocs()) {
			Element root = doc.getRootElement();

			if (root.attributeValue("short-name").equals(name)) {
				return doc;
			}
		}

		return null;
	}

	protected abstract String getDefaultParentClass();

	protected Element getElementByName(List<Element> elements, String name) {
		for (Element element : elements) {
			if (name.equals(element.elementText("name"))) {
				return element;
			}
		}

		return null;
	}

	protected List<Attribute> getPrefixedEvents(Element componentNode) {
		List<Attribute> afterEvents = getAttributes(
			componentNode, "events", "event");

		List<Attribute> onEvents = getAttributes(
			componentNode, "events", "event");

		List<Attribute> prefixedEvents = new ArrayList<Attribute>();

		for (Attribute event : afterEvents) {
			String name = _AFTER.concat(
				org.apache.commons.lang.StringUtils.capitalize(
					event.getSafeName()));

			event.setName(name);

			prefixedEvents.add(event);
		}

		for (Attribute event : onEvents) {
			String name = _ON.concat(
				org.apache.commons.lang.StringUtils.capitalize(
					event.getSafeName()));

			event.setName(name);

			prefixedEvents.add(event);
		}

		return prefixedEvents;
	}

	protected Document mergeXMLAttributes(Document doc1, Document doc2) {
		Element doc1Root = doc1.getRootElement();

		Element docRoot = doc1Root.createCopy();
		docRoot.clearContent();

		DocumentFactory factory = SAXReaderUtil.getDocumentFactory();

		Document doc = factory.createDocument();
		doc.setRootElement(docRoot);

		List<Element> doc1Components = doc1Root.elements(_COMPONENT);

		for (Element doc1Component : doc1Components) {
			String name = doc1Component.attributeValue("name");

			Element doc2Component = getComponentNode(doc2, name);

			if (doc2Component != null) {
				Element doc2AttributesNode = doc2Component.element(_ATTRIBUTES);

				if (doc2AttributesNode != null) {
					List<Element> doc2Attributes = doc2AttributesNode.elements(
						_ATTRIBUTE);

					Element doc1AttributesNode = doc1Component.element(
						_ATTRIBUTES);

					for (Element doc2Attribute : doc2Attributes) {
						Element doc1Attribute = getElementByName(
							doc1AttributesNode.elements("attribute"),
							doc2Attribute.elementText("name"));

						if (doc1Attribute == null) {
							doc1AttributesNode.add(doc2Attribute.createCopy());
						}
					}
				}

				Element doc2EventsNode = doc2Component.element(_EVENTS);

				if (doc2EventsNode != null) {
					List<Element> doc2Events = doc2EventsNode.elements(_EVENT);

					Element doc1EventsNode = doc1Component.element(_EVENTS);

					for (Element doc2Event : doc2Events) {
						Element doc1Event = getElementByName(
							doc1EventsNode.elements("event"),
							doc2Event.elementText("name"));

						if (doc1Event == null) {
							doc1EventsNode.add(doc2Event.createCopy());
						}
					}
				}
			}

			doc.getRootElement().add(doc1Component.createCopy());
		}

		return doc;
	}

	protected String processTemplate(String name, Map<String, Object> context)
		throws Exception {

		return StringUtil.replace(
			FreeMarkerUtil.process(name, context), "\r", StringPool.EMPTY);
	}

	protected void writeFile(File file, String content) {
		writeFile(file, content, true);
	}

	protected void writeFile(File file, String content, boolean overwrite) {
		try {
			file.getParentFile().mkdirs();

			if (overwrite || !file.exists()) {
				String oldContent = StringPool.EMPTY;

				if (file.exists()) {
					oldContent = FileUtil.readString(file);
				}

				if (!file.exists() || !content.equals(oldContent)) {
					System.out.println("Writing " + file);

					FileUtil.writeString(file, content);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final String _AFTER = "after";

	private static final String _ATTRIBUTE = "attribute";

	private static final String _ATTRIBUTES = "attributes";

	private static final String _AUTHOR = "author";

	private static final String _AUTHORS = "authors";

	private static final String _COMPONENT = "component";

	private static final String _DEFAULT_TYPE = "java.lang.Object";

	private static final String _EVENT = "event";

	private static final String _EVENTS = "events";

	private static final String _ON = "on";

	private List<Document> _componentsDefinitionDocs;

}