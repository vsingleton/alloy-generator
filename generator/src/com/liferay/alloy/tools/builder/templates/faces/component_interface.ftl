<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

/**
<#list component.getAuthors() as author>
 * @author  ${author}
</#list>
 * @generated
 */
public interface ${component.getCamelizedName()}Component {

	// Public Constants
	<#list component.getAttributesAndEvents() as attribute>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#list>
	<#list component.getFacesAttributes() as attribute>
	<#if attribute.isGettable() && attribute.isComponentPropertyRequired()>

	public ${attribute.getJavaWrapperInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();
	</#if>
	<#if attribute.isSettable() && attribute.isComponentPropertyRequired()>

	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()});
	</#if>
	</#list>
	<#list component.getEvents() as event>
	<#if event.isGettable()>

	public ${event.getJavaWrapperInputType()} get${event.getJavaBeanPropertyName()}();
	</#if>
	<#if event.isSettable()>

	public void set${event.getJavaBeanPropertyName()}(${event.getJavaWrapperInputType()} ${event.getJavaSafeName()});
	</#if>
	</#list>
}