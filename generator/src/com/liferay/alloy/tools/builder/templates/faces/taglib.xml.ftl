<#include "../base/init.ftl">
<?xml version='1.0' encoding='UTF-8'?>
<#-- If the JSF version is 2.1, the Facelet Taglib version is 2.0. See -->
<#-- https://issues.liferay.com/browse/FACES-2109#commentauthor_590915_verbose for more details. -->
<#assign defaultXMLNamespace = "http://xmlns.jcp.org/xml/ns/javaee">
<#assign faceletTaglibVersion = facesVersion>
<#assign vdldocNamespace = "http://vdldoc.omnifaces.org" />
<#if faceletTaglibVersion == "2.1">
	<#assign defaultXMLNamespace = "http://java.sun.com/xml/ns/javaee" />
	<#assign faceletTaglibVersion = "2.0" />
	<#assign vdldocNamespace = "http://vdldoc.org/vdldoc" />
</#if>
<facelet-taglib xmlns="${defaultXMLNamespace}" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:vdldoc="${vdldocNamespace}"
	xsi:schemaLocation="${defaultXMLNamespace} ${defaultXMLNamespace}/web-facelettaglibrary_${faceletTaglibVersion?replace(".", "_")}.xsd ${vdldocNamespace} https://raw.githubusercontent.com/omnifaces/vdldoc/master/src/main/resources/org/omnifaces/vdldoc/resources/vdldoc.taglib.xml.xsd"
	version="${faceletTaglibVersion}">
	<#if description??>
	<description><![CDATA[${description}]]></description>
	</#if>
	<namespace>${namespaceURI}</namespace>
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
			<#if component.isGenerateRenderer()>
			<renderer-type>${packagePath}.${component.getUncamelizedName()}.internal.${component.getCamelizedName()}Renderer</renderer-type>
			</#if>
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
			<#if attribute.getMethodSignature()??>
			<method-signature><#compress>${attribute.getMethodSignature()?xml}</#compress></method-signature>
			<#else>
			<type>${attribute.getType()?xml}</type>
			</#if>
		</attribute>
		</#list>
		<tag-extension>
			<vdldoc:since>${component.getSince()}</vdldoc:since>
		</tag-extension>
	</tag>
</#list>
</facelet-taglib>
