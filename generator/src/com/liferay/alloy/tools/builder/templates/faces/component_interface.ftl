<#include "../base/init.ftl">
<#include "../base/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};
//J-

import javax.annotation.Generated;

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface ${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX} {

	// Public Constants
	<#list component.getAttributesAndEvents()?sort_by("constantName") as attribute>
	<#if attribute.isGenerateJava()>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#if>
	</#list>
	<#list component.getAttributesAndEvents()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGenerateJava()>
	<#if attribute.isGettable()>

	public <#if !attribute.isEvent() && attribute.isJSFReservedAttribute()>${attribute.getJSFReservedAttributeType()}<#else>${attribute.getJavaWrapperType()}</#if> ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();
	</#if>
	<#if attribute.isSettable()>

	public void set${attribute.getJavaBeanPropertyName()}(<#if !attribute.isEvent() && attribute.isJSFReservedAttribute()>${attribute.getJSFReservedAttributeType()}<#else>${attribute.getJavaWrapperType()}</#if> ${attribute.getJavaSafeName()});
	</#if>
	</#if>
	</#list>
}
//J+
