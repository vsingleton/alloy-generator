<#include "../base/init.ftl">
<?xml version='1.0' encoding='UTF-8'?>
<facelet-taglib xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:vdldoc="http://vdldoc.org/vdldoc"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
http://java.sun.com/xml/ns/javaee/web-facelettaglibrary_2_0.xsd http://vdldoc.org/vdldoc https://vdldoc.googlecode.com/hg/src/org/omnifaces/vdldoc/resources/vdldoc.taglib.xml.xsd"
	version="2.0">
	<#if description??>
	<description><![CDATA[${description}]]></description>
	</#if>
	<namespace>http://liferay.com/faces/${namespace}</namespace>
	<#if extensionElements??>
	<#list extensionElements as element>
	${element.asXML()?replace("\n\t", "\n")}
	</#list>
	</#if>
	<#list components?sort_by("uncapitalizedName") as component>
	<tag>
		<description><![CDATA[${component.getCleanDescription()}]]></description>
		<tag-name>${component.getUncapitalizedName()}</tag-name>
		<#if component.getValidatorId()??>
		<validator>
			<validator-id>${component.getValidatorId()}</validator-id>
		</validator>
		<#elseif component.isHandlerClassOnly()>
		<handler-class>${component.getHandlerClass()}</handler-class>
		<#else>
		<component>
			<component-type>${packagePath}.${component.getUncamelizedName()}.${component.getCamelizedName()}</component-type>
			<renderer-type>${packagePath}.${component.getUncamelizedName()}.internal.${component.getCamelizedName()}Renderer</renderer-type>
			<#if component.getHandlerClass()??>
			<handler-class>${component.getHandlerClass()}</handler-class>
			</#if>
		</component>
		</#if>
		<#list component.getAttributes()?sort_by("safeName") as attribute>
		<attribute>
			<#if attribute.getDescription()??>
			<description><![CDATA[${attribute.getCleanDescription()}]]></description>
			</#if>
			<name>${attribute.getSafeName()}</name>
			<required>${attribute.isRequired()?string("true", "false")}</required>
			<type>${attribute.getType()?xml}</type>
			<#if attribute.getMethodSignature()??>
			<method-signature><#compress>${attribute.getMethodSignature()}</#compress></method-signature>
			</#if>
		</attribute>
		</#list>
		<tag-extension>
			<vdldoc:since>${version}</vdldoc:since>
		</tag-extension>
	</tag>
</#list>
</facelet-taglib>