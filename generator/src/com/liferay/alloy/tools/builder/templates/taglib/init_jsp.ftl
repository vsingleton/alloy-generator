<%--
<#include "../base/init.ftl">
<#include "../base/copyright.ftl">

@generated
--%>

<#compress>
<#function isValidAttribute attributeName>
	<#return ((attributeName != "boundingBox") && (attributeName != "contentBox") && (attributeName != "srcNode")) />
</#function>
</#compress>
<%@ include file="${jspCommonInitPath}" %>

<%
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("${namespace}dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("${namespace}scopedAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}
<#if component.isAlloyComponent()>
%>

<%@ include file="/html/taglib/aui/init-alloy.jspf" %>

<%
<#else>

</#if>
<#compress>
<#list component.getAttributesAndEvents() as attribute>
	<#assign defaultValueSuffix = BLANK>
	<#assign outputSimpleClassName = attribute.getTypeSimpleClassName()>
	<#if !attribute.isEvent()>
		<#assign outputSimpleClassName = attribute.getOutputTypeSimpleClassName()>
	</#if>
	
	<#if attribute.getDefaultValue()??>
		<#assign defaultValueSuffix = getDefaultValueSuffix(outputSimpleClassName, attribute.getDefaultValue())>
	</#if>

	<#if isValidAttribute(attribute.getName())>
		<#assign namespacedName = QUOTE + namespace + attribute.getSafeName() + QUOTE>

		<#if (isPrimitiveType(outputSimpleClassName) || isNumericAttribute(outputSimpleClassName))>
			<#assign value = "String.valueOf(request.getAttribute(" + namespacedName + "))">
		<#else>
			<#assign rawInputType = attribute.getType()>
			<#if !attribute.isEvent()>
				<#assign rawInputType = attribute.getRawInputType()>
			</#if>
			<#assign value = "(" + rawInputType + ")request.getAttribute(" + namespacedName + ")">
		</#if>

		<#if outputSimpleClassName == "ArrayList">
			<#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getOutputType()}</#if> ${attribute.getSafeName()} = _toArrayList(GetterUtil.getObject(${value}${defaultValueSuffix}));
		<#elseif outputSimpleClassName == "HashMap">
			<#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getOutputType()}</#if> ${attribute.getSafeName()} = _toHashMap(GetterUtil.getObject(${value}${defaultValueSuffix}));
		<#elseif hasGetter(outputSimpleClassName)>
			<#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getOutputType()}</#if> ${attribute.getSafeName()} = GetterUtil.get${getGetterSuffix(outputSimpleClassName)}(${value}${defaultValueSuffix});
		<#else>
			<#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getRawOutputType()}</#if> ${attribute.getSafeName()} = (<#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getRawOutputType()}</#if>)request.getAttribute(${namespacedName});
		</#if>
	</#if>
</#list>
</#compress>


<#list component.getAttributesAndEvents() as attribute>
_updateOptions(_options, "${attribute.getSafeName()}", ${attribute.getSafeName()});
</#list>
%>

<%@ include file="${jspRelativePath}/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "${namespace}";
%>