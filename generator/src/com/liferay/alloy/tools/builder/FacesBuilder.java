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

import com.liferay.alloy.tools.model.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

import jodd.typeconverter.Convert;
import jodd.util.StringPool;

import org.dom4j.Document;
import org.dom4j.Element;
public class FacesBuilder extends BaseBuilder {

	public static void main(String[] args) throws Exception {
		String baseOutputDir = System.getProperty("builder.faces.output.dir");
		String version = System.getProperty("builder.faces.version");

		new FacesBuilder(baseOutputDir, version);
	}

	public FacesBuilder(String baseOutputDir, String version) throws Exception {
		_baseOutputDir = baseOutputDir;

		_tplComponent = getTemplatesDir() + "component.ftl";
		_tplComponentBase = getTemplatesDir() + "component_base.ftl";
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
			Map<String, Object> context = getTemplateContext(component);

			_buildComponent(component, context);
			_buildComponentBase(component, context);
			_buildRenderer(component, context);
			_buildRendererBase(component, context);
		}

		_buildTaglibsXML();
	}

	@Override
	public String getTemplatesDir() {
		return _TEMPLATES_DIR;
	}

	@Override
	protected String getComponentDefaultInterface() {
		return _COMPONENT_DEFAULT_INTERFACE;
	}

	@Override
	protected String getComponentDefaultParentClass() {
		return _COMPONENT_DEFAULT_PARENT_CLASS;
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
		StringBuilder sb = new StringBuilder(3);

		sb.append(_baseOutputDir);
		sb.append(StringPool.SLASH);
		sb.append(_TAGLIBS_XML_DIR);

		return sb.toString();
	}

	private void _buildComponent(
			Component component, Map<String, Object> context)
		throws Exception {

		String path = getComponentOutputDir(component);

		String componentContent = processTemplate(_tplComponent, context);

		File componentFile = new File(
			path.concat(component.getSafeName().concat(_JAVA_EXT)));

		writeFile(componentFile, componentContent);
	}

	private void _buildComponentBase(
			Component component, Map<String, Object> context)
		throws Exception {

		String path = getComponentOutputDir(component);

		String componentBaseContent = processTemplate(
			_tplComponentBase, context);

		StringBuilder fileNameSb = new StringBuilder(4);

		fileNameSb.append(path);
		fileNameSb.append(_BASE_CLASS_PREFIX);
		fileNameSb.append(component.getSafeName());
		fileNameSb.append(_JAVA_EXT);

		File componentBaseFile = new File(fileNameSb.toString());

		writeFile(componentBaseFile, componentBaseContent);
	}

	private void _buildRenderer(
			Component component, Map<String, Object> context)
		throws Exception {

		String path = getComponentOutputDir(component);

		String rendererContent = processTemplate(_tplRenderer, context);

		StringBuilder fileNameSb = new StringBuilder(4);

		fileNameSb.append(path);
		fileNameSb.append(component.getSafeName());
		fileNameSb.append(_RENDERER_CLASS_SUFIX);
		fileNameSb.append(_JAVA_EXT);

		File rendererFile = new File(fileNameSb.toString());

		writeFile(rendererFile, rendererContent);
	}
	
	private void _buildRendererBase(
			Component component, Map<String, Object> context)
		throws Exception {

		String path = getComponentOutputDir(component);

		String rendererBaseContent = processTemplate(_tplRendererBase, context);

		StringBuilder fileNameSb = new StringBuilder(4);

		fileNameSb.append(path);
		fileNameSb.append(_BASE_CLASS_PREFIX);
		fileNameSb.append(component.getSafeName());
		fileNameSb.append(_RENDERER_CLASS_SUFIX);
		fileNameSb.append(_JAVA_EXT);

		File rendererBaseFile = new File(fileNameSb.toString());

		writeFile(rendererBaseFile, rendererBaseContent);
	}

	private void _buildTaglibsXML() throws Exception {
		Map<String, Object> context = getDefaultTemplateContext();

		for (Document doc : getComponentDefinitionDocs()) {
			Element root = doc.getRootElement();

			String shortName = Convert.toString(
				root.attributeValue("short-name"));

			context.put("components", getComponents(doc));

			String rendererContent = processTemplate(_tplTaglibsXML, context);

			String path = getTaglibsXMLOutputDir();

			File rendererFile = new File(
				path.concat(shortName).concat(_XML_EXT));

			writeFile(rendererFile, rendererContent, true);
		}
	}

	private static final String _BASE_CLASS_PREFIX = "Base";

	private static final String _COMPONENT_DEFAULT_INTERFACE = null;

	private static final String _COMPONENT_DEFAULT_PARENT_CLASS =
		"javax.faces.component.UIComponentBase";

	private static final String _COMPONENTS_PACKAGE =
		"com.liferay.faces.alloy.component";

	private static final String _JAVA_EXT = ".java";

	private static final String _RENDERER_CLASS_SUFIX = "Renderer";

	private static final String _TAGLIBS_XML_DIR = "com/liferay/faces/alloy/";

	private static final String _TEMPLATES_DIR =
		"com/liferay/alloy/tools/builder/templates/faces/";

	private static final String _XML_EXT = ".xml";

	private String _baseOutputDir;
	private String _tplComponent;
	private String _tplComponentBase;
	private String _tplRenderer;
	private String _tplRendererBase;
	private String _tplTaglibsXML;
	private String _version;

}