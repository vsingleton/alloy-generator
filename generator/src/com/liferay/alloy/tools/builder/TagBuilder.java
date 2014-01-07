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
import com.liferay.alloy.util.StringUtil;
import com.liferay.alloy.util.xml.SAXReaderUtil;
import com.liferay.alloy.util.xml.xpath.AlloyGeneratorNamespaceContext;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.io.FileUtil;

import jodd.typeconverter.Convert;

import jodd.util.StringPool;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

import org.jaxen.NamespaceContext;
public class TagBuilder extends BaseBuilder {

	public static void main(String[] args) throws Exception {
		String componentsXML = System.getProperty("tagbuilder.components.xml");
		String copyrightYear = System.getProperty("tagbuilder.copyright.year");
		String docrootDir = System.getProperty("tagbuilder.docroot.dir");
		String javaDir = System.getProperty("tagbuilder.java.dir");
		String javaPackage = System.getProperty("tagbuilder.java.package");
		String jspCommonInitPath = System.getProperty(
			"tagbuilder.jsp.common.init.path");
		String jspDir = System.getProperty("tagbuilder.jsp.dir");
		String templatesDir = System.getProperty("tagbuilder.templates.dir");
		String tldDir = System.getProperty("tagbuilder.tld.dir");

		Calendar calendar = Calendar.getInstance();

		_copyrightYear = String.valueOf(calendar.get(Calendar.YEAR));

		if (StringUtil.isNotBlank(copyrightYear)) {
			_copyrightYear = copyrightYear;
		}

		new TagBuilder(
			componentsXML, templatesDir, javaDir, docrootDir, javaPackage,
			jspDir, jspCommonInitPath, tldDir);
	}

	public TagBuilder(
			String componentsDefinitionList, String templatesDir,
			String javaDir, String docrootDir, String javaPackage,
			String jspDir, String jspCommonInitPath, String tldDir)
		throws Exception {

		_componentsDefinitionList = Arrays.asList(
			componentsDefinitionList.split(StringPool.COMMA));

		_docrootDir = docrootDir;
		_javaDir = javaDir;
		_javaPackage = javaPackage;
		_jspCommonInitPath = jspCommonInitPath;
		_jspDir = jspDir;
		_templatesDir = templatesDir;
		_tldDir = tldDir;

		_tplCommonInitJsp = _templatesDir + "common_init_jsp.ftl";
		_tplInitJsp = _templatesDir + "init_jsp.ftl";
		_tplJsp = _templatesDir + "jsp.ftl";
		_tplStartJsp = _templatesDir + "start_jsp.ftl";
		_tplTag = _templatesDir + "tag.ftl";
		_tplTagBase = _templatesDir + "tag_base.ftl";
		_tplTld = _templatesDir + "tld.ftl";
		_tplComponentJava = _templatesDir + "component_java.ftl";

		build();
	}

	@Override
	public void build() throws Exception {
		List<Component> components = getAllComponents();

		for (Component component : components) {
			Map<String, Object> context = getTemplateContext(component);

			_createBaseTag(component, context);

			if (component.getWriteJSP()) {
				_createPageJSP(component, context);
			}

			_createTag(component, context);
		}

		_createCommonInitJSP();
		_createTld();
	}

	@Override
	public List<String> getComponentDefinitionsList() {
		return _componentsDefinitionList;
	}

	@Override
	protected String getDefaultParentClass() {
		return _DEFAULT_PARENT_CLASS;
	}

	protected HashMap<String, Object> getDefaultTemplateContext() {
		HashMap<String, Object> context = new HashMap<String, Object>();

		context.put("copyrightYear", _copyrightYear);
		context.put("jspCommonInitPath", _jspCommonInitPath);
		context.put("jspDir", _jspDir);
		context.put("packagePath", _javaPackage);

		return context;
	}

	protected String getJavaOutputBaseDir(Component component) {
		StringBuilder sb = new StringBuilder();

		sb.append(getJavaOutputDir(component));
		sb.append(_BASE);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getJavaOutputDir(Component component) {
		StringBuilder sb = new StringBuilder();

		sb.append(_javaDir);
		sb.append(component.getPackage());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getJspDir(Component component) {
		StringBuilder sb = new StringBuilder();

		sb.append(_jspDir);
		sb.append(component.getPackage());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getJspOutputDir(Component component) {
		StringBuilder sb = new StringBuilder();

		sb.append(_docrootDir);
		sb.append(StringPool.SLASH);
		sb.append(_jspDir);
		sb.append(component.getPackage());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected Map<String, Object> getTemplateContext(Component component) {
		HashMap<String, Object> context = getDefaultTemplateContext();

		String jspRelativePath = getJspDir(component).concat(
			component.getUncamelizedName(StringPool.UNDERSCORE));

		context.put("component", component);
		context.put("namespace", component.getAttributeNamespace());
		context.put("jspRelativePath", jspRelativePath);

		return context;
	}

	protected Document mergeTlds(Document sourceDoc, Document targetDoc) {
		Element targetRoot = targetDoc.getRootElement();

		DocumentFactory factory = SAXReaderUtil.getDocumentFactory();

		XPath xpathTags = factory.createXPath("//tld:tag");

		Map<String, String> namespaceContextMap = new HashMap<String, String>();

		namespaceContextMap.put(_TLD_XPATH_PREFIX, _TLD_XPATH_URI);

		NamespaceContext namespaceContext = new AlloyGeneratorNamespaceContext(
			namespaceContextMap);

		xpathTags.setNamespaceContext(namespaceContext);

		List<Node> sources = xpathTags.selectNodes(sourceDoc);

		for (Node source : sources) {
			Element sourceElement = (Element)source;

			String sourceName = sourceElement.elementText("name");

			String xpathTagValue = "//tld:tag[tld:name='" + sourceName + "']";

			XPath xpathTag = factory.createXPath(xpathTagValue);

			xpathTag.setNamespaceContext(namespaceContext);

			List<Node> targets = xpathTag.selectNodes(targetDoc);

			if (targets.size() > 0) {
				Element targetElement = (Element)targets.get(0);

				XPath xpathAttributes = factory.createXPath(
					xpathTagValue + "//tld:attribute");

				Map<String, String> namespaces = new HashMap<String, String>();

				namespaces.put("tld", StringPool.EMPTY);

				xpathAttributes.setNamespaceURIs(namespaces);

				List<Node> sourceAttributes = xpathAttributes.selectNodes(
					source);

				for (Node sourceAttribute : sourceAttributes) {
					Element sourceAttributeElement = (Element)sourceAttribute;

					String attributeName = sourceAttributeElement.elementText(
						"name");

					String xpathAttributeValue = "//tld:attribute[tld:name='" +
							attributeName + "']";

					XPath xpathAttribute = factory.createXPath(
						xpathTagValue + xpathAttributeValue);

					xpathAttribute.setNamespaceContext(namespaceContext);

					Node targetAttribute = xpathAttribute.selectSingleNode(
						targetElement);

					if (targetAttribute != null) {
						targetAttribute.detach();
					}

					targetElement.add(sourceAttributeElement.createCopy());
				}

				Element dynamicAttrElement = targetElement.element(
					"dynamic-attributes");

				if (dynamicAttrElement != null) {
					targetElement.add(dynamicAttrElement.detach());
				}
			}
			else {
				targetRoot.add(sourceElement.createCopy());
			}
		}

		return targetDoc;
	}

	protected Document mergeXMLAttributes(Document doc1, Document doc2) {
		Element doc1Root = doc1.getRootElement();

		Element docRoot = doc1Root.createCopy();
		docRoot.clearContent();

		DocumentFactory factory = _saxReader.getDocumentFactory();

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

	private void _create() throws Exception {
		List<Component> components = getAllComponents();

		for (Component component : components) {
			Map<String, Object> context = getTemplateContext(component);

			_createBaseTag(component, context);

			if (component.getWriteJSP()) {
				_createPageJSP(component, context);
			}
			
			_createComponentJAVA(component, context);

			_createTag(component, context);
		}

		_createCommonInitJSP();
		_createTld();
	}

	private void _createBaseTag(
			Component component, Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(getJavaOutputBaseDir(component));
		sb.append(_BASE_CLASS_PREFIX);
		sb.append(component.getClassName());
		sb.append(_CLASS_SUFFIX);

		String content = processTemplate(_tplTagBase, context);

		File tagFile = new File(sb.toString());

		writeFile(tagFile, content);
	}

	private void _createCommonInitJSP() throws Exception {
		HashMap<String, Object> context = getDefaultTemplateContext();

		String contentCommonInitJsp = processTemplate(
			_tplCommonInitJsp, context);

		StringBuilder sb = new StringBuilder();

		sb.append(_docrootDir);
		sb.append(StringPool.SLASH);
		sb.append(_jspCommonInitPath);

		File commonInitFile = new File(sb.toString());

		writeFile(commonInitFile, contentCommonInitJsp, false);
	}

	private void _createPageJSP(
			Component component, Map<String, Object> context)
		throws Exception {

		String pathName = component.getUncamelizedName(StringPool.UNDERSCORE);
		String path = getJspOutputDir(component).concat(pathName);

		String contentJsp = processTemplate(_tplJsp, context);
		String contentInitJsp = processTemplate(_tplInitJsp, context);

		File initFile = new File(path.concat(_INIT_PAGE));
		File initExtFile = new File(path.concat(_INIT_EXT_PAGE));

		writeFile(initFile, contentInitJsp);
		writeFile(initExtFile, StringPool.EMPTY, false);

		if (component.isBodyContent()) {
			String contentStart = processTemplate(_tplStartJsp, context);

			File startFile = new File(path.concat(_START_PAGE));

			writeFile(startFile, contentStart, false);
		}
		else {
			File pageFile = new File(path.concat(_PAGE));

			writeFile(pageFile, contentJsp, false);
		}
	}

	private void _createTag(Component component, Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(getJavaOutputDir(component));
		sb.append(component.getClassName());
		sb.append(_CLASS_SUFFIX);

		String content = processTemplate(_tplTag, context);

		File tagFile = new File(sb.toString());

		writeFile(tagFile, content, false);
	}

	private void _createTld() throws Exception {
		HashMap<String, Object> context = getDefaultTemplateContext();

		for (Document doc : getComponentDefinitionDocs()) {
			Element root = doc.getRootElement();

			String shortName = Convert.toString(
				root.attributeValue("short-name"), _DEFAULT_TAGLIB_SHORT_NAME);
			String version = Convert.toString(
				root.attributeValue("tlib-version"), _DEFAULT_TAGLIB_VERSION);
			String uri = Convert.toString(
				root.attributeValue("uri"), _DEFAULT_TAGLIB_URI);

			context.put("alloyComponent", shortName.equals(_DEFAULT_NAMESPACE));
			context.put("components", getComponents(doc));
			context.put("shortName", shortName);
			context.put("uri", uri);
			context.put("version", version);

			String tldFilePath = _tldDir.concat(
				shortName).concat(_TLD_EXTENSION);

			File tldFile = new File(tldFilePath);

			String content = processTemplate(_tplTld, context);

			Document source = SAXReaderUtil.read(
				new ByteArrayInputStream(content.getBytes()));

			if (tldFile.exists()) {
				Document target = SAXReaderUtil.read(
					new ByteArrayInputStream(FileUtil.readBytes(tldFile)));

				source = mergeTlds(source, target);
			}

			writeFile(tldFile, content, true);
		}
	}
	
	protected String getKyleJavaOutputDir(Component component) {
		StringBuilder sb = new StringBuilder();

		sb.append("/Users/kylestiemann/Projects/liferay.com/alloy-generator/build");
		sb.append(StringPool.SLASH);
		sb.append("faces/components/com/liferay/faces/alloy/component/");

		return sb.toString();
	}
	
	private void _createComponentJAVA(
			Component component, Map<String, Object> context)
		throws Exception {

		//String pathName = component.getName() + "/";
		String path = getKyleJavaOutputDir(component);//.concat(pathName);

		String contentComponentJava = processTemplate(_tplComponentJava, context);

		File componentFile = new File(path.concat(component.getName().concat(".java")));

		System.err.println(componentFile.getPath());
		
		writeFile(componentFile, contentComponentJava);
	}
	
	private String _tplComponentJava;

	private static final String _BASE = "base";

	private static final String _BASE_CLASS_PREFIX = "Base";

	private static final String _CLASS_SUFFIX = ".java";

	private static final String _DEFAULT_NAMESPACE = "alloy";

	private static final String _DEFAULT_PARENT_CLASS =
		"com.liferay.taglib.util.IncludeTag";

	private static final String _DEFAULT_TAGLIB_SHORT_NAME = "alloy";

	private static final String _DEFAULT_TAGLIB_URI =
		"http://alloy.liferay.com/tld/alloy";

	private static final String _DEFAULT_TAGLIB_VERSION = "1.0";

	private static final String _INIT_EXT_PAGE = "/init-ext.jspf";

	private static final String _INIT_PAGE = "/init.jsp";

	private static final String _PAGE = "/page.jsp";

	private static final String _START_PAGE = "/start.jsp";

	private static final String _TLD_EXTENSION = ".tld";

	private static final String _TLD_XPATH_PREFIX = "tld";

	private static final String _TLD_XPATH_URI =
		"http://java.sun.com/xml/ns/j2ee";

	private static String _copyrightYear;

	private List<String> _componentsDefinitionList;
	private String _docrootDir;
	private String _javaDir;
	private String _javaPackage;
	private String _jspCommonInitPath;
	private String _jspDir;
	private String _templatesDir;
	private String _tldDir;
	private String _tplCommonInitJsp;
	private String _tplInitJsp;
	private String _tplJsp;
	private String _tplStartJsp;
	private String _tplTag;
	private String _tplTagBase;
	private String _tplTld;

}