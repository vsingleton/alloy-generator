<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 * @generated
 */
public interface ${component.getCamelizedName()}Component {

	// Public Constants
	<#list component.getAttributesAndEvents()?sort_by("constantName") as attribute>
	<#if (attribute.getName() != "cssClass") && (attribute.getName() != "styleClass") && (attribute.getName() != "widgetVar")>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#if>
	</#list>
	<#list component.getAttributesAndEvents()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired()) && (attribute.getName() != "cssClass") && (attribute.getName() != "styleClass") && (attribute.getName() != "widgetVar")>

	public ${attribute.getJavaWrapperInputType()} <#if attribute.isEvent()>get<#else>${attribute.getGetterMethodPrefix()}</#if>${attribute.getJavaBeanPropertyName()}();
	</#if>
	<#if attribute.isSettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired()) && (attribute.getName() != "cssClass") && (attribute.getName() != "styleClass") && (attribute.getName() != "widgetVar")>

	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()});
	</#if>
	</#list>
}