<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};
//J-

import javax.annotation.Generated;
import javax.faces.FacesWrapper;

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@Generated(value="com.liferay.alloy.tools.builder.FacesBuilder")
public class ${component.getCamelizedName()}ComponentWrapper implements ${component.getCamelizedName()}Component, FacesWrapper<${component.getCamelizedName()}Component> {

	// Private Data Members
	private ${component.getCamelizedName()}Component wrapped${component.getCamelizedName()}Component;

	public ${component.getCamelizedName()}ComponentWrapper(${component.getCamelizedName()}Component ${component.getUncapitalizedName()}Component) {
		this.wrapped${component.getCamelizedName()}Component = ${component.getUncapitalizedName()}Component;
	}

	@Override
	public ${component.getCamelizedName()}Component getWrapped() {
		return wrapped${component.getCamelizedName()}Component;
	}
	<#list component.getFacesAttributesAndEvents()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired()) && (attribute.getSafeName() != "styleClass") && (attribute.getSafeName() != "widgetVar")>

	@Override
	public ${attribute.getJavaWrapperInputType()} <#if attribute.isEvent()>get<#else>${attribute.getGetterMethodPrefix()}</#if>${attribute.getJavaBeanPropertyName()}() {
		return getWrapped().<#if attribute.isEvent()>get<#else>${attribute.getGetterMethodPrefix()}</#if>${attribute.getJavaBeanPropertyName()}();
	}
	</#if>
	<#if attribute.isSettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired()) && (attribute.getSafeName() != "styleClass") && (attribute.getSafeName() != "widgetVar")>

	@Override
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}) {
		getWrapped().set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaSafeName()});
	}
	</#if>
	</#list>
}
//J+
