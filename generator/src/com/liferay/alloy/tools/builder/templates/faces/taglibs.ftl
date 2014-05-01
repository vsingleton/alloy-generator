<#include "../base/init.ftl">
<?xml version='1.0' encoding='UTF-8'?>
<facelet-taglib xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:vdldoc="http://vdldoc.org/vdldoc"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
   http://java.sun.com/xml/ns/javaee/web-facelettaglibrary_2_0.xsd http://vdldoc.org/vdldoc https://vdldoc.googlecode.com/hg/src/org/omnifaces/vdldoc/resources/vdldoc.taglib.xml.xsd"
	version="2.0">
	<#if description??>
	<description><![CDATA[${description}]]></description>
	</#if>
	<namespace>${namespaceURI}</namespace>
	<#if functions??>
	<#list functions?sort_by("name") as function>
	<function>
		<description><![CDATA[${function["description"]}]]></description>
		<function-name>${function["name"]}</function-name>
		<function-class>${function["class"]}</function-class>
		<function-signature>${function["signature"]}</function-signature>
	</function>
	</#list>
	</#if>
	<#list components?sort_by("uncapitalizedName") as component>
	<tag>
		<description><![CDATA[${component.getDescription()}]]></description>
		<tag-name>${component.getUncapitalizedName()}</tag-name>
		<component>
			<component-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}</component-type>
			<renderer-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}${RENDERER_CLASS_SUFFIX}</renderer-type>
		</component>
		<#list component.getAttributesAndEvents()?sort_by("safeName") as attribute>
		<#if attribute.isSettable() || attribute.isGettable()>
		<attribute>
			<#if attribute.getDescription()??>
			<description><![CDATA[${attribute.getDescription()}]]></description>
			</#if>
			<name>${attribute.getSafeName()}</name>
			<required>${attribute.isRequired()?string("true", "false")}</required>
			<#if !attribute.isEvent() && attribute.isJSFReservedAttribute()>
			<type>${attribute.getJSFReservedAttributeType()}</type>
			<#else>
			<type>${attribute.getJavaWrapperType()}</type>
			</#if>
			<#if !attribute.isEvent() && attribute.getMethodSignature()??>
			<method-signature>${attribute.getMethodSignature()}</method-signature>
			</#if>
		</attribute>
		</#if>
		</#list>
		<tag-extension>
			<vdldoc:since>${version}</vdldoc:since>
		</tag-extension>
	</tag>
</#list>
</facelet-taglib>