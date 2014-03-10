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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.typeconverter.Convert;
import jodd.util.StringPool;

import org.dom4j.Document;
import org.dom4j.Element;

import com.liferay.alloy.tools.model.Attribute;
import com.liferay.alloy.tools.model.Component;
import com.liferay.alloy.tools.model.FacesAttribute;
import com.liferay.alloy.tools.model.FacesComponent;
import com.liferay.alloy.util.PropsUtil;

public class FacesBuilder extends BaseBuilder {

	public static void main(String[] args) throws Exception {
		String baseOutputDir = PropsUtil.getString("builder.faces.output.dir");
		String taglibXMLOutputDir = PropsUtil
				.getString("builder.faces.taglib.xml.output.dir");
		String version = PropsUtil.getString("builder.faces.version");

		new FacesBuilder(baseOutputDir, taglibXMLOutputDir, version);
	}

	public FacesBuilder(String baseOutputDir, String taglibXMLOutputDir,
			String version) throws Exception {

		_baseOutputDir = baseOutputDir;
		_taglibXMLOutputDir = taglibXMLOutputDir;

		_tplComponent = getTemplatesDir() + "component.ftl";
		_tplComponentBase = getTemplatesDir() + "component_base.ftl";
		_tplComponentInterface = getTemplatesDir() + "component_interface.ftl";
		_tplComponentWrapper = getTemplatesDir() + "component_wrapper.ftl";
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
			Map<String, Object> context = getTemplateContext(component);

			_buildComponent(facesComponent, context);

			if (facesComponent.isComponentBaseClassRequired()) {
				_buildComponentBase(facesComponent, context);
			}

			_buildComponentInterface(facesComponent, context);
			_buildComponentWrapper(facesComponent, context);

			_buildRenderer(facesComponent, context);

			if (facesComponent.isRendererBaseClassRequired()) {
				_buildRendererBase(facesComponent, context);
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
	protected List<Attribute> getAttributes(Element componentNode,
			String group, String nodeName) {
		List<Element> nodes = Collections.emptyList();

		List<Attribute> attributes = new ArrayList<Attribute>();

		Element node = componentNode.element(group);

		if (node != null) {
			nodes = node.elements(nodeName);
		}

		for (Element attributeNode : nodes) {
			FacesAttribute facesAttribute = new FacesAttribute();
			setAttributeBaseProperties(attributeNode, facesAttribute);

			boolean componentPropertyRequired = Convert.toBoolean(
					attributeNode.elementText("componentPropertyRequired"), true);
			facesAttribute.setComponentPropertyRequired(componentPropertyRequired);
			String methodSignature = attributeNode
					.elementText("method-signature");
			facesAttribute.setMethodSignature(methodSignature);

			attributes.add(facesAttribute);
		}

		return attributes;
	}

	@Override
	protected List<Component> getComponents(Document doc) throws Exception {
		Element root = doc.getRootElement();

		List<Component> facesComponents = new ArrayList<Component>();

		String defaultPackage = root.attributeValue("short-name");
		List<Element> allComponentNodes = root.elements("component");

		for (Element node : allComponentNodes) {
			FacesComponent facesComponent = new FacesComponent();

			setComponentBaseProperties(node, facesComponent, defaultPackage);

			boolean generateComponentBaseClass = Convert.toBoolean(
					node.attributeValue("componentBaseClassRequired"), true);

			String rendererParentClass = Convert.toString(
					node.attributeValue("rendererParentClass"),
					facesComponent.getRendererBaseClass());

			facesComponent
					.setComponentBaseClassRequired(generateComponentBaseClass);
			facesComponent.setRendererParentClass(rendererParentClass);

			facesComponents.add(facesComponent);
		}

		return facesComponents;
	}

	@Override
	protected String getComponentDefaultInterface() {
		return null;
	}

	@Override
	protected String getComponentDefaultParentClass() {
		return _FACES_COMPONENT_DEFAULT_PARENT_CLASS;
	}

	protected String getComponentOutputDir(FacesComponent facesComponent) {
		StringBuilder sb = new StringBuilder(6);

		sb.append(_baseOutputDir);
		sb.append(StringPool.SLASH);
		sb.append(_COMPONENTS_PACKAGE.replaceAll("\\.", StringPool.SLASH));
		sb.append(StringPool.SLASH);
		sb.append(facesComponent.getUncamelizedName(StringPool.EMPTY));
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

	protected Document mergeExtendedXML(Document mergeDoc, Document extensionDoc) {

		if (extensionDoc != null) {

			List<Element> extensionDocFunctions = extensionDoc.getRootElement()
					.elements("functions");

			for (Element extensionDocFunction : extensionDocFunctions) {

				Element function = extensionDocFunction.createCopy();
				mergeDoc.getRootElement().add(function);
			}
		}

		return mergeDoc;
	}

	private void _buildComponent(FacesComponent facesComponent,
			Map<String, Object> context) throws Exception {

		String path = getComponentOutputDir(facesComponent);

		String componentContent = processTemplate(_tplComponent, context);

		File componentFile = new File(path.concat(facesComponent
				.getCamelizedName().concat(_JAVA_EXT)));

		writeFile(componentFile, componentContent, false);
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

	private void _buildComponentWrapper(FacesComponent facesComponent,
			Map<String, Object> context) throws Exception {

		String path = getComponentOutputDir(facesComponent);

		String componentWrapperContent = processTemplate(_tplComponentWrapper,
				context);

		StringBuilder fileNameSb = new StringBuilder(4);

		fileNameSb.append(path);
		fileNameSb.append(facesComponent.getCamelizedName());
		fileNameSb.append(_WRAPPER_CLASS_SUFFIX);
		fileNameSb.append(_JAVA_EXT);

		File componentWrapperFile = new File(fileNameSb.toString());

		writeFile(componentWrapperFile, componentWrapperContent);
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

			String description = Convert.toString(root
					.attributeValue("description"));

			Element functionsElement = root.element("functions");

			if (functionsElement != null) {

				List<Element> functions = functionsElement.elements("function");
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

			context.put("components", getComponents(doc));
			context.put("namespaceURI", namespaceURI);
			context.put("description", description);

			String rendererContent = processTemplate(_tplTaglibsXML, context);

			String path = getTaglibsXMLOutputDir();

			File rendererFile = new File(path.concat(namespace).concat(
					_TAGLIB_XML_EXT));

			writeFile(rendererFile, rendererContent, true);
		}
	}

	private static final String _BASE_CLASS_SUFFIX = "Base";

	private static final String _FACES_COMPONENT_DEFAULT_PARENT_CLASS = "javax.faces.component.UIPanel";

	private static final String _INTERFACE_CLASS_SUFFIX = "Component";

	private static final String _WRAPPER_CLASS_SUFFIX = "ComponentWrapper";

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
	private String _tplComponentWrapper;
	private String _tplRenderer;
	private String _tplRendererBase;
	private String _tplTaglibsXML;
	private String _version;

}