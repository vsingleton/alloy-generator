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

package com.liferay.alloy.tools.builder.faces;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.typeconverter.Convert;
import jodd.util.StringPool;

import org.dom4j.Document;
import org.dom4j.Element;

import com.liferay.alloy.tools.builder.base.BaseBuilder;
import com.liferay.alloy.tools.builder.faces.model.FacesAttribute;
import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.tools.builder.faces.model.FacesComponent;
import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Event;
import com.liferay.alloy.util.PropsUtil;

public class FacesBuilder extends BaseBuilder {

	public static void main(String[] args) throws Exception {
		String baseOutputDir = PropsUtil.getString("builder.faces.output.dir");
		String taglibXMLOutputDir = PropsUtil.getString(
			"builder.faces.taglib.xml.output.dir");
		String version = PropsUtil.getString("builder.faces.version");

		new FacesBuilder(baseOutputDir, taglibXMLOutputDir, version);
	}

	public FacesBuilder(
			String baseOutputDir, String taglibXMLOutputDir, String version)
		throws Exception {

		_baseOutputDir = baseOutputDir;
		_taglibXMLOutputDir = taglibXMLOutputDir;

		_tplComponent = getTemplatesDir() + "component.ftl";
		_tplComponentBase = getTemplatesDir() + "component_base.ftl";
		_tplComponentInterface = getTemplatesDir() + "component_interface.ftl";
		_tplRenderer = getTemplatesDir() + "renderer.ftl";
		_tplRendererBase = getTemplatesDir() + "renderer_base.ftl";
		_tplTaglibsXML = getTemplatesDir() + "taglibs.ftl";

		_version = version;

		build();
	}

	@Override
	public void build() throws Exception {
		List<Component> components = getAllComponents();

		for (Component component : components) {
			FacesComponent facesComponent = (FacesComponent) component;
			
			if (facesComponent.isGenerateJava()) {

				Map<String, Object> context = getTemplateContext(component);

				context.put("BASE_CLASS_SUFFIX", _BASE_CLASS_SUFFIX);
				context.put("RENDERER_CLASS_SUFFIX", _RENDERER_CLASS_SUFFIX);
				context.put("INTERFACE_CLASS_SUFFIX", _INTERFACE_CLASS_SUFFIX);
				context.put("RENDERER_BASE_CLASS_SUFFIX", _RENDERER_CLASS_SUFFIX + _BASE_CLASS_SUFFIX);

				List<FacesAttribute> additionalAttributes = _getAdditionalAttributes(facesComponent);

				_buildComponent(facesComponent, context);

				if (additionalAttributes.size() > 0) {
					facesComponent.getAttributes().addAll(additionalAttributes);
				}

				_buildComponentBase(facesComponent, context);

				if (additionalAttributes.size() > 0) {
					facesComponent.getAttributes().removeAll(additionalAttributes);
				}
	
				if (facesComponent.isAlloyComponent()) {
					_buildComponentInterface(facesComponent, context);
				}
	
				_buildRenderer(facesComponent, context);
	
				if (facesComponent.isAlloyComponent()) {
					_buildRendererBase(facesComponent, context);
				}
			}
		}

		_buildTaglibsXML();

		System.out.println("Finished looping over " + components.size()
				+ " components.");
	}

	@Override
	public String getTemplatesDir() {
		return _TEMPLATES_DIR;
	}

	@Override
	protected List<Component> getComponents(Document doc) throws Exception {
		Element root = doc.getRootElement();
		return getComponents(doc, false);
	}

	protected List<Component> getComponents(Document doc, boolean addAdditionalAttributes) throws Exception {
		Element root = doc.getRootElement();

		Map<String, Component> facesComponentsMap = new HashMap<String, Component>();

		String defaultPackage = root.attributeValue("short-name");
		List<Element> allComponentNodes = root.elements("component");

		for (Element node : allComponentNodes) {
			FacesComponent facesComponent = new FacesComponent();
			facesComponent.initialize(node, defaultPackage);

			if (addAdditionalAttributes) {
				List<FacesAttribute> additionalAttributes = new ArrayList<FacesAttribute>();
				additionalAttributes = _getAdditionalAttributes(facesComponent);

				if (additionalAttributes.size() > 0) {
					facesComponent.getAttributes().addAll(additionalAttributes);
				}
			}

			facesComponentsMap.put(facesComponent.getName(), facesComponent);
		}

		List<Component> facesComponents = new ArrayList<Component>(facesComponentsMap.values());

		for (Component facesComponent : facesComponents) {
			recursivelyAddExtensionAttributesAndEvents(facesComponent, facesComponentsMap);
		}

		return facesComponents;
	}

	protected void recursivelyAddExtensionAttributesAndEvents(Component component, Map<String, Component> facesComponentsMap) {

		String extensionName = component.getExtends();

		if (extensionName != null) {

			Component extensionComponent = facesComponentsMap.get(extensionName);
			recursivelyAddExtensionAttributesAndEvents(extensionComponent, facesComponentsMap);
			List<Attribute> extensionAttributes = extensionComponent.getAttributesAndEvents();

			if (extensionAttributes.size() > 0) {

				List<Attribute> attributes = component.getAttributes();
				List<Event> events = component.getEvents();

				for (Attribute extensionAttribute : extensionAttributes) {

					if (extensionAttribute instanceof Event) {

						Event event = (Event) extensionAttribute;

						if (!events.contains(event)) {
							events.add(event);
						}
					} else {

						if (!attributes.contains(extensionAttribute)) {
							attributes.add(extensionAttribute);
						}
					}
				}
			}	
		}
	} 

	protected String getComponentOutputDir(Component component) {
		StringBuilder sb = new StringBuilder(6);

		sb.append(_baseOutputDir);
		sb.append(StringPool.SLASH);
		sb.append(_COMPONENTS_PACKAGE.replaceAll("\\.", StringPool.SLASH));
		sb.append(StringPool.SLASH);
		sb.append(component.getUncamelizedName(StringPool.EMPTY));
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	@Override
	protected Map<String, Object> getDefaultTemplateContext() {
		Map<String, Object> context = super.getDefaultTemplateContext();

		context.put("packagePath", _COMPONENTS_PACKAGE);
		context.put("version", _version);

		return context;
	}

	protected String getTaglibsXMLOutputDir() {
		StringBuilder sb = new StringBuilder(2);

		sb.append(_taglibXMLOutputDir);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	private void _buildComponent(FacesComponent facesComponent,
			Map<String, Object> context) throws Exception {

		String path = getComponentOutputDir(facesComponent);

		String componentContent = processTemplate(_tplComponent, context);

		File componentFile = new File(path.concat(facesComponent
				.getCamelizedName().concat(_JAVA_EXT)));

		writeFile(componentFile, componentContent, false);
	}

	private List<FacesAttribute> _getAdditionalAttributes(FacesComponent facesComponent) {

		List<FacesAttribute> additionalAttributes = new ArrayList<FacesAttribute>();
		
		for (Document doc : getComponentDefinitionDocs()) {
			Element root = doc.getRootElement();

			Element extensionElement = root.element("extension");

			if (extensionElement != null) {

				if (facesComponent.isAlloyComponent()) {
					List<FacesAttribute> clientComponentAttributes =
						_getInterfaceAttributes(extensionElement, "clientComponentAttributes", facesComponent);
					additionalAttributes.addAll(clientComponentAttributes);
				}

				if (facesComponent.isStyleable()) {
					List<FacesAttribute> styleableAttributes =
						_getInterfaceAttributes(extensionElement, "styleableAttributes", facesComponent);
					additionalAttributes.addAll(styleableAttributes);
				}
			}
		}

		return additionalAttributes;
	}

	private List<FacesAttribute> _getInterfaceAttributes(
		Element extensionElement, String interfaceAttributesElementName,
			FacesComponent facesComponent) {

		Element interfaceAttributesElement = extensionElement.element(interfaceAttributesElementName);

		List<Element> interfaceAttributeElementList = interfaceAttributesElement.elements("attribute");
		List<FacesAttribute> facesAttributeList = new ArrayList<FacesAttribute>();

		for (Element interfaceAttributeElement : interfaceAttributeElementList) {
			FacesAttribute facesAttribute = new FacesAttribute();
			facesAttribute.initialize(interfaceAttributeElement, facesComponent);
			facesAttributeList.add(facesAttribute);
		}

		return facesAttributeList;
	}

	private void _buildComponentBase(FacesComponent facesComponent,
			Map<String, Object> context) throws Exception {

		String path = getComponentOutputDir(facesComponent);

		String componentBaseContent = processTemplate(_tplComponentBase,
				context);

		StringBuilder fileNameSb = new StringBuilder(4);

		fileNameSb.append(path);
		fileNameSb.append(facesComponent.getCamelizedName());
		fileNameSb.append(_BASE_CLASS_SUFFIX);
		fileNameSb.append(_JAVA_EXT);

		File componentBaseFile = new File(fileNameSb.toString());

		writeFile(componentBaseFile, componentBaseContent);
	}

	private void _buildComponentInterface(FacesComponent facesComponent,
			Map<String, Object> context) throws Exception {

		String path = getComponentOutputDir(facesComponent);

		String componentBaseContent = processTemplate(_tplComponentInterface,
				context);

		StringBuilder fileNameSb = new StringBuilder(4);

		fileNameSb.append(path);
		fileNameSb.append(facesComponent.getCamelizedName());
		fileNameSb.append(_INTERFACE_CLASS_SUFFIX);
		fileNameSb.append(_JAVA_EXT);

		File componentInterfaceFile = new File(fileNameSb.toString());

		writeFile(componentInterfaceFile, componentBaseContent);
	}

	private void _buildRenderer(FacesComponent facesComponent,
			Map<String, Object> context) throws Exception {

		String path = getComponentOutputDir(facesComponent);

		String rendererContent = processTemplate(_tplRenderer, context);

		StringBuilder fileNameSb = new StringBuilder(4);

		fileNameSb.append(path);
		fileNameSb.append(facesComponent.getCamelizedName());
		fileNameSb.append(_RENDERER_CLASS_SUFFIX);
		fileNameSb.append(_JAVA_EXT);

		File rendererFile = new File(fileNameSb.toString());

		writeFile(rendererFile, rendererContent, false);
	}

	private void _buildRendererBase(FacesComponent facesComponent,
			Map<String, Object> context) throws Exception {

		String path = getComponentOutputDir(facesComponent);

		String rendererBaseContent = processTemplate(_tplRendererBase, context);

		StringBuilder fileNameSb = new StringBuilder(5);

		fileNameSb.append(path);
		fileNameSb.append(facesComponent.getCamelizedName());
		fileNameSb.append(_RENDERER_CLASS_SUFFIX);
		fileNameSb.append(_BASE_CLASS_SUFFIX);
		fileNameSb.append(_JAVA_EXT);

		File rendererBaseFile = new File(fileNameSb.toString());

		writeFile(rendererBaseFile, rendererBaseContent);
	}

	private void _buildTaglibsXML() throws Exception {
		Map<String, Object> context = getDefaultTemplateContext();

		for (Document doc : getComponentDefinitionDocs()) {
			Element root = doc.getRootElement();

			String namespace = Convert.toString(root
					.attributeValue("namespace"));

			String namespaceURI = Convert.toString(root
					.attributeValue("namespaceURI"));

			Element descriptionElement = root.element("description");

			String description = null;

			if (descriptionElement != null) {
				description = descriptionElement.getTextTrim();
			}

			Element extensionElement = root.element("extension");

			if (extensionElement != null) {

				List<Element> functions = extensionElement.elements("function");
				List<Map<String, String>> functionsList = new ArrayList<Map<String, String>>();

				for (Element function : functions) {

					Map<String, String> functionMap = new HashMap<String, String>();

					String functionDescription = function
							.element("description").getText();
					functionMap.put("description", functionDescription);
					String functionName = function.element("function-name")
							.getText();
					functionMap.put("name", functionName);
					String functionClass = function.element("function-class")
							.getText();
					functionMap.put("class", functionClass);
					String functionSignature = function.element(
							"function-signature").getText();
					functionMap.put("signature", functionSignature);

					functionsList.add(functionMap);
				}

				context.put("functions", functionsList);
			}

			context.put("components", getComponents(doc, true));
			context.put("namespaceURI", namespaceURI);
			context.put("description", description);
			context.put("version", _version);
			context.put("BASE_CLASS_SUFFIX", _BASE_CLASS_SUFFIX);
			context.put("RENDERER_CLASS_SUFFIX", _RENDERER_CLASS_SUFFIX);
			context.put("INTERFACE_CLASS_SUFFIX", _INTERFACE_CLASS_SUFFIX);
			context.put("RENDERER_BASE_CLASS_SUFFIX", _RENDERER_CLASS_SUFFIX + _BASE_CLASS_SUFFIX);

			String rendererContent = processTemplate(_tplTaglibsXML, context);

			String path = getTaglibsXMLOutputDir();

			File rendererFile = new File(path.concat(namespace).concat(
					_TAGLIB_XML_EXT));

			writeFile(rendererFile, rendererContent, true);
		}
	}

	private static final String _BASE_CLASS_SUFFIX = "Base";

	private static final String _INTERFACE_CLASS_SUFFIX = "Alloy";

	private static final String _COMPONENTS_PACKAGE = "com.liferay.faces.alloy.component";

	private static final String _JAVA_EXT = ".java";

	private static final String _RENDERER_CLASS_SUFFIX = "Renderer";

	private static final String _TAGLIB_XML_EXT = ".taglib.xml";

	private static final String _TEMPLATES_DIR = "com/liferay/alloy/tools/builder/templates/faces/";

	private String _baseOutputDir;
	private String _taglibXMLOutputDir;
	private String _tplComponent;
	private String _tplComponentBase;
	private String _tplComponentInterface;
	private String _tplRenderer;
	private String _tplRendererBase;
	private String _tplTaglibsXML;
	private String _version;

}