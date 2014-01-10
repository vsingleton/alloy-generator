<#include "../common/init.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<facelet-taglib
	version="2.0"
	xmlns:vdldoc="http://vdldoc.org/vdldoc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facelettaglibrary_2_0.xsd http://vdldoc.org/vdldoc https://vdldoc.googlecode.com/hg/src/org/omnifaces/vdldoc/resources/vdldoc.taglib.xml.xsd">
	<description>The Liferay Faces Alloy facelet component tags with the &lt;code&gt;aui:&lt;/code&gt; tag name prefix.</description>
	<namespace>http://liferay.com/faces/aui</namespace>
	<function>
		<description>Returns an escaped representation of the specified client ID.</description>
		<function-name>escapeClientId</function-name>
		<function-class>com.liferay.faces.alloy.util.AlloyUtil</function-class>
		<function-signature>java.lang.String escapeClientId(java.lang.String)</function-signature>
	</function>
<#list components as component>
	<tag>
		<description>${component.getDescription()}</description>
		<tag-name>${component.getUncapitalizedName()}</tag-name>
		<component>
			<component-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}</component-type>
			<renderer-type>${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}Renderer</renderer-type>
		</component>
		<#list component.getAttributesAndEvents() as attribute>
		<attribute>
			<#if attribute.getDescription()??>
			<description><![CDATA[${attribute.getDescription()}]]></description>
			</#if>
			<name>${attribute.getSafeName()}</name>
			<required>${attribute.isRequired()?string("true", "false")}</required>
			<type>${attribute.getJSFInputType()}</type>
		</attribute>
		</#list>
		<tag-extension>
			<vdldoc:since>4.1.0</vdldoc:since>
		</tag-extension>
	</tag>
</#list>
</facelet-taglib>