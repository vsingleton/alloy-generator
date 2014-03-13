<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
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
@Generated(value="com.liferay.alloy.tools.builder.FacesBuilder")
public interface ${component.getCamelizedName()}Component {

	// Public Constants
	<#list component.getAttributesAndEvents()?sort_by("constantName") as attribute>
	<#if (attribute.getSafeName() != "styleClass") && (attribute.getSafeName() != "widgetVar")>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#if>
	</#list>
	<#list component.getAttributesAndEvents()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired()) && (attribute.getSafeName() != "styleClass") && (attribute.getSafeName() != "widgetVar")>

	public ${attribute.getJavaWrapperInputType()} <#if attribute.isEvent()>get<#else>${attribute.getGetterMethodPrefix()}</#if>${attribute.getJavaBeanPropertyName()}();
	</#if>
	<#if attribute.isSettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired()) && (attribute.getSafeName() != "styleClass") && (attribute.getSafeName() != "widgetVar")>

	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()});
	</#if>
	</#list>
}
//J+
