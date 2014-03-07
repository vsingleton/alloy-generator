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
	<#list functions as function>
	<function>
		<description><![CDATA[${function["description"]}]]></description>
		<function-name>${function["name"]}</function-name>
		<function-class>${function["class"]}</function-class>
		<function-signature>${function["signature"]}</function-signature>
	</function>
	</#list>
	<#list components as component>
	<tag>
		<description>${component.getDescription()}</description>
		<tag-name>${component.getUncapitalizedName()}</tag-name>
		<component>
			<component-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}</component-type>
			<renderer-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}Renderer</renderer-type>
		</component>
		<#list component.getFacesAttributes() as attribute>
		<attribute>
			<#if attribute.getDescription()??>
			<description><![CDATA[${attribute.getDescription()}]]></description>
			</#if>
			<name>${attribute.getSafeName()}</name>
			<required>${attribute.isRequired()?string("true", "false")}</required>
			<type>${attribute.getJavaWrapperInputType()}</type>
			<#if attribute.getMethodSignature()??>
			<method-signature>${attribute.getMethodSignature()}</method-signature>
			</#if>
		</attribute>
		</#list>
		<#list component.getEvents() as event>
		<attribute>
			<#if event.getDescription()??>
			<description><![CDATA[${event.getDescription()}]]></description>
			</#if>
			<name>${event.getSafeName()}</name>
			<required>${event.isRequired()?string("true", "false")}</required>
			<type>${event.getJavaWrapperInputType()}</type>
		</attribute>
		</#list>
		<attribute>
			<description>The name of a CSS class that is to be rendered within the class attribute (same as styleClass).</description>
			<name>cssClass</name>
			<required>false</required>
			<type>java.lang.String</type>
		</attribute>
		<attribute>
			<description>The name of a CSS class that is to be rendered within the class attribute.</description>
			<name>styleClass</name>
			<required>false</required>
			<type>java.lang.String</type>
		</attribute>
		<tag-extension>
			<vdldoc:since>4.1.0</vdldoc:since>
		</tag-extension>
	</tag>
</#list>
</facelet-taglib>