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

package com.liferay.alloy.tools.transformer;

import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.util.DefaultValueUtil;
import com.liferay.alloy.util.JSONUtil;
import com.liferay.alloy.util.PropsUtil;
import com.liferay.alloy.util.StringUtil;
import com.liferay.alloy.util.TypeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jodd.typeconverter.Convert;

import jodd.util.StringPool;

import org.apache.commons.io.FileUtils;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public class AlloyDocsTransformer {

	public static void main(String[] args) throws Exception {
		String inputJSON = PropsUtil.getString("transformer.input.json");
		String outputXML = PropsUtil.getString("transformer.output.xml");
		String[] componentExcluded = PropsUtil.getStringArray(
			"transformer.components.excluded");

		new AlloyDocsTransformer(inputJSON, outputXML, componentExcluded);
	}

	public AlloyDocsTransformer(
			String inputJSON, String outputXML, String[] componentExcluded)
		throws Exception {

		_inputJSON = inputJSON;
		_outputXML = outputXML;
		_componentExcluded = Arrays.asList(componentExcluded);

		_fileJSON = new File(_inputJSON);

		_json = new JSONObject(FileUtils.readFileToString(_fileJSON));
		_classMapJSON = _json.getJSONObject("classes");
		_classItemsJSONArray = _json.getJSONArray("classitems");

		_create();
	}

	public ArrayList<Attribute> getComponentAttributes(String className) {
		return _getComponentAttributes(className, "attribute");
	}

	public ArrayList<String> getComponentHierarchy(String className) {
		return _getComponentHierarchy(className, new ArrayList<String>());
	}

	public ArrayList<Component> getComponents() {
		Set<Component> components = new HashSet<Component>();

		Iterator<String> it = _classMapJSON.keys();

		while (it.hasNext()) {
			String className = it.next();

			JSONObject componentJSON = JSONUtil.getJSONObject(
				_classMapJSON, className);

			String name = JSONUtil.getString(componentJSON, "name");

			name = _cleanName(name);

			boolean bodyContent = Convert.toBoolean(
				JSONUtil.getString(componentJSON, "bodyContent"), true);

			String description = Convert.toString(
				JSONUtil.getString(componentJSON, "description"),
				StringPool.EMPTY);

			String module = Convert.toString(
				JSONUtil.getString(componentJSON, "module"), name);

			String namespace = Convert.toString(_DEFAULT_NAMESPACE);

			List<Attribute> attributes = getComponentAttributes(className);

			List<Attribute> events = _getAttributesEvents(attributes);

			Component component = new Component();

			component.setAlloyComponent(true);
			component.setAttributes(attributes);
			component.setAuthors(null);
			component.setBodyContent(bodyContent);
			component.setDescription(description);
			component.setEvents(events);
			component.setModule(module);
			component.setName(name);
			component.setPackage(namespace);

			if (!isExcludedComponent(component)) {
				components.add(component);
			}
		}

		ArrayList<Component> sortedComponents = new ArrayList<Component>(
			components);

		Collections.sort(sortedComponents);

		return sortedComponents;
	}

	public boolean isExcludedComponent(Component component) {
		String module = component.getModule();

		return (!module.startsWith(AUI_PREFIX) ||
				_componentExcluded.contains(component.getName()));
	}

	private String _cleanName(String name) {

		if (name.startsWith(_ALLOY_CLASS_PREFIX)) {
			name = name.replace(_ALLOY_CLASS_PREFIX, StringPool.EMPTY);
		}
		else if (name.startsWith(_PLUGIN_CLASS_PREFIX)) {
			name = name.replace(_PLUGIN_CLASS_PREFIX, StringPool.EMPTY);
		}
		else if (name.startsWith(_DATA_TYPE_PREFIX)) {
			name = name.replace(_DATA_TYPE_PREFIX, StringPool.EMPTY);
		}

		return name;
	}

	private void _create() throws Exception {
		_createXML();
	}

	private void _createXML() {
		ArrayList<Component> components = getComponents();

		Document doc = DocumentFactory.getInstance().createDocument();
		Element root = doc.addElement("components");

		root.addAttribute("short-name", _DEFAULT_TAGLIB_SHORT_NAME);
		root.addAttribute("uri", _DEFAULT_TAGLIB_URI);
		root.addAttribute("tlib-version", _DEFAULT_TAGLIB_VERSION);

		for (Component component : components) {
			Element componentNode = root.addElement("component");

			componentNode.addAttribute("name", component.getName());
			componentNode.addAttribute("module", component.getModule());
			componentNode.addAttribute("package", component.getPackage());
			componentNode.addAttribute(
				"bodyContent", String.valueOf(component.isBodyContent()));

			componentNode.addAttribute(
				"alloyComponent", String.valueOf(component.isAlloyComponent()));

			Element attributesNode = componentNode.addElement("attributes");
			Element eventsNode = componentNode.addElement("events");

			for (Attribute attribute : component.getAttributes()) {
				Element attributeNode = attributesNode.addElement("attribute");
				Element nameNode = attributeNode.addElement("name");
				Element inputTypeNode = attributeNode.addElement("inputType");
				Element outputTypeNode = attributeNode.addElement("outputType");
				Element defaultValueNode = attributeNode.addElement(
					"defaultValue");

				Element descriptionNode = attributeNode.addElement(
					"description");

				nameNode.setText(attribute.getName());
				inputTypeNode.setText(attribute.getInputType());
				outputTypeNode.setText(attribute.getOutputType());
				defaultValueNode.setText(attribute.getDefaultValue());
				descriptionNode.addCDATA(_getAttributeDescription(attribute));
			}

			for (Attribute event : component.getEvents()) {
				Element eventNode = eventsNode.addElement("event");
				Element nameNode = eventNode.addElement("name");
				Element typeNode = eventNode.addElement("type");
				Element descriptionNode = eventNode.addElement("description");

				nameNode.setText(event.getName());
				typeNode.setText(event.getInputType());
				descriptionNode.addCDATA(_getAttributeDescription(event));
			}
		}

		try {
			File file = new File(_outputXML);

			file.getParentFile().mkdirs();

			FileOutputStream fos = new FileOutputStream(file);

			OutputFormat format = OutputFormat.createPrettyPrint();

			XMLWriter writer = new XMLWriter(fos, format);

			writer.write(doc);
			writer.flush();

			System.out.println("Writing " + _outputXML);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String _getAttributeDescription(Attribute attribute) {
		JSONObject descriptionJSON = new JSONObject();

		try {
			String defaultValue = attribute.getDefaultValue();

			if (StringUtil.isNotBlank(defaultValue)) {
				descriptionJSON.put("defaultValue", defaultValue);
			}

			descriptionJSON.put("event", attribute.isEvent());
			descriptionJSON.put("inputType", attribute.getInputType());
			descriptionJSON.put("outputType", attribute.getOutputType());
			descriptionJSON.put("required", attribute.isRequired());
		}
		catch (JSONException jsone) {
			jsone.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();

		if (StringUtil.isNotBlank(attribute.getDescription())) {
			sb.append(attribute.getDescription());
		}

		sb.append(_HTML_COMMENT_START);
		sb.append(descriptionJSON.toString());
		sb.append(_HTML_COMMENT_END);

		return sb.toString();
	}

	private List<Attribute> _getAttributesEvents(List<Attribute> attributes) {
		ArrayList<Attribute> events = new ArrayList<Attribute>();

		for (Attribute attribute : attributes) {
			String name = attribute.getName();

			Attribute event = new Attribute();

			event.setName(name + "Change");
			event.setInputType("String");
			event.setOutputType("String");
			event.setDefaultValue(null);
			event.setRequired(false);

			events.add(event);
		}

		return events;
	}

	private ArrayList<JSONObject> _getClassItems(
		String classname, String type) {

		ArrayList<JSONObject> items = new ArrayList<JSONObject>();

		for (int i = 0; i < _classItemsJSONArray.length(); i++) {
			try {
				JSONObject item = _classItemsJSONArray.getJSONObject(i);

				if (item.has("class") && item.has("itemtype")) {
					String itemClassName = item.getString("class");
					String itemType = item.getString("itemtype");

					if (itemClassName.equals(classname) &&
						itemType.equals(type)) {

						items.add(item);
					}
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return items;
	}

	private ArrayList<Attribute> _getComponentAttributes(
		String className, String attributeType) {

		Set<Attribute> attributes = new HashSet<Attribute>();

		ArrayList<String> hierarchy = getComponentHierarchy(className);

		try {
			for (String parentClass : hierarchy) {
				ArrayList<JSONObject> classItems = _getClassItems(
					parentClass, attributeType);

				Iterator<JSONObject> it = classItems.iterator();

				while (it.hasNext()) {
					JSONObject attributeJSON = it.next();

					String name = attributeJSON.getString("name");

					String inputType = Convert.toString(
						JSONUtil.getString(attributeJSON, "type"),
						_DEFAULT_TYPE);

					String outputType = Convert.toString(
						JSONUtil.getString(attributeJSON, "type"),
						_DEFAULT_TYPE);

					String outputJavaType = TypeUtil.getOutputJavaType(
						outputType, true);

					String defaultValue = DefaultValueUtil.getDefaultValue(
						outputJavaType,
						JSONUtil.getString(attributeJSON, "default"));

					String description = Convert.toString(
						JSONUtil.getString(attributeJSON, "description"),
						StringPool.EMPTY);

					boolean required = Convert.toBoolean(
						JSONUtil.getString(attributeJSON, "required"), false);

					Attribute attribute = new Attribute();

					attribute.setName(name);
					attribute.setInputType(inputType);
					attribute.setOutputType(outputType);
					attribute.setDefaultValue(defaultValue);
					attribute.setDescription(description);
					attribute.setRequired(required);

					attributes.add(attribute);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Attribute> sortedAttributes = new ArrayList<Attribute>(
			attributes);

		Collections.sort(sortedAttributes);

		return sortedAttributes;
	}

	private ArrayList<String> _getComponentHierarchy(
		String className, ArrayList<String> hierarchy) {

		try {
			JSONObject componentJSON = JSONUtil.getJSONObject(
				_classMapJSON, className);

			if (componentJSON != null) {
				hierarchy.add(className);

				String extendClass = JSONUtil.getString(
					componentJSON, "extends");

				if (extendClass != null) {
					_getComponentHierarchy(extendClass, hierarchy);
				}

				hierarchy.addAll(JSONUtil.getList(componentJSON, "uses"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return hierarchy;
	}

	private static final String _ALLOY_CLASS_PREFIX = "A.";

	private static final String _DATA_TYPE_PREFIX = "DataType.";

	private static final String _DEFAULT_NAMESPACE = "alloy";

	private static final String _DEFAULT_TAGLIB_SHORT_NAME = "alloy";

	private static final String _DEFAULT_TAGLIB_URI =
		"http://alloy.liferay.com/tld/alloy";

	private static final String _DEFAULT_TAGLIB_VERSION = "1.0";

	private static final String _DEFAULT_TYPE = Object.class.getName();

	private static final String _HTML_COMMENT_END = "-->";

	private static final String _HTML_COMMENT_START = "<!--";

	private static final String _PLUGIN_CLASS_PREFIX = "Plugin.";

	private static final String AUI_PREFIX = "aui-";

	private JSONArray _classItemsJSONArray;
	private JSONObject _classMapJSON;
	private List<String> _componentExcluded;
	private File _fileJSON;
	private String _inputJSON;
	private JSONObject _json;
	private String _outputXML;

}