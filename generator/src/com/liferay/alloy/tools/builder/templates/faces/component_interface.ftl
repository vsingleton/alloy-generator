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
	<#list component.getAttributes()?sort_by("constantName") as attribute>
	<#if attribute.isGenerateJava() && attribute.isJavaScript()>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#if>
	</#list>
	<#list component.getAttributes()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGenerateJava() && attribute.isJavaScript()>

	public <#if attribute.isJSFReservedAttribute()>${attribute.getJSFReservedAttributeType()?replace('java.lang.','')}<#else>${attribute.getJavaWrapperType()}</#if> ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();

	public void set${attribute.getJavaBeanPropertyName()}(<#if attribute.isJSFReservedAttribute()>${attribute.getJSFReservedAttributeType()?replace('java.lang.','')}<#else>${attribute.getJavaWrapperType()}</#if> ${attribute.getJavaSafeName()});
	</#if>
	</#list>
}
//J+