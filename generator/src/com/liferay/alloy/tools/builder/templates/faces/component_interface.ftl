<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

public interface ${component.getCamelizedName()}Component {

	// Public Constants
	<#list component.getAttributesAndEvents() as attribute>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#list>
	<#list component.getAttributesAndEvents() as attribute>
	<#if attribute.isGettable()>

	public ${attribute.getJSFInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();
	</#if>
	<#if attribute.isSettable()>

	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJSFInputType()} ${attribute.getJavaSafeName()});
	</#if>
	</#list>
}