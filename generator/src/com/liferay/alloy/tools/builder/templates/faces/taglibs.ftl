<#include "../common/init.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<facelet-taglib
	version="2.0"
	xmlns:vdldoc="http://vdldoc.org/vdldoc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facelettaglibrary_2_0.xsd http://vdldoc.org/vdldoc https://vdldoc.googlecode.com/hg/src/org/omnifaces/vdldoc/resources/vdldoc.taglib.xml.xsd">
	<description><![CDATA[${description}]]></description>
	<namespace>${namespaceURI}</namespace>
	<#list functions?sort_by("name") as function>
	<function>
		<description><![CDATA[${function["description"]}]]></description>
		<function-name>${function["name"]}</function-name>
		<function-class>${function["class"]}</function-class>
		<function-signature>${function["signature"]}</function-signature>
	</function>
	</#list>
	<#list components?sort as component>
	<tag>
		<description>${component.getDescription()}</description>
		<tag-name>${component.getUncapitalizedName()}</tag-name>
		<component>
			<component-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}</component-type>
			<renderer-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}Renderer</renderer-type>
		</component>
		<#list component.getFacesAttributesAndEvents()?sort_by("safeName") as attribute>
		<attribute>
			<#if attribute.getDescription()??>
			<description><![CDATA[${attribute.getDescription()}]]></description>
			</#if>
			<name>${attribute.getSafeName()}</name>
			<required>${attribute.isRequired()?string("true", "false")}</required>
			<type>${attribute.getJavaWrapperInputType()}</type>
			<#if !attribute.isEvent() && attribute.getMethodSignature()??>
			<method-signature>${attribute.getMethodSignature()}</method-signature>
			</#if>
		</attribute>
		</#list>
		<tag-extension>
			<vdldoc:since>${version}</vdldoc:since>
		</tag-extension>
	</tag>
</#list>
</facelet-taglib>