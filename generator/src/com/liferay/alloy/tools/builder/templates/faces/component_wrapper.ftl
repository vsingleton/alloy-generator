<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import javax.faces.FacesWrapper;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
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

	<#list component.getFacesAttributes() as attribute>
	<#if attribute.isGettable() && attribute.isBeanPropertyRequired()>

	@Override
	public ${attribute.getJavaWrapperInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return getWrapped().${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();
	}
	</#if>
	<#if attribute.isSettable() && attribute.isBeanPropertyRequired()>

	@Override
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}) {
		getWrapped().set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaSafeName()});
	}
	</#if>
	</#list>
	<#list component.getEvents() as event>
	<#if event.isGettable()>

	@Override
	public ${event.getJavaWrapperInputType()} get${event.getJavaBeanPropertyName()}() {
		return getWrapped().get${event.getJavaBeanPropertyName()}();
	}
	</#if>
	<#if event.isSettable()>

	@Override
	public void set${event.getJavaBeanPropertyName()}(${event.getJavaWrapperInputType()} ${event.getJavaSafeName()}) {
		getWrapped().set${event.getJavaBeanPropertyName()}(${event.getJavaSafeName()});
	}
	</#if>
	</#list>
}