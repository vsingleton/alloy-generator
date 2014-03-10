<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import com.liferay.faces.util.component.Styleable;
<#if component.isRendererBaseClassRequired()>
import com.liferay.faces.util.component.Widget;
</#if>
import ${component.getParentClass()};

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
public abstract class ${component.getCamelizedName()}Base extends ${component.getUnqualifiedParentClass()} implements Styleable, <#if component.isRendererBaseClassRequired()>Widget, </#if>${component.getCamelizedName()}Component {

	<#list component.getFacesAttributes() as attribute>
	<#if attribute.isGettable() && attribute.isComponentPropertyRequired()>
	@Override
	public ${attribute.getJavaWrapperInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJavaWrapperInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}

	</#if>
	<#if attribute.isSettable() && attribute.isComponentPropertyRequired()>
	@Override
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}

	</#if>
	</#list>
	<#list component.getEvents() as event>
	<#if event.isGettable()>
	@Override
	public ${event.getJavaWrapperInputType()} get${event.getJavaBeanPropertyName()}() {
		return (${event.getJavaWrapperInputType()}) getStateHelper().eval(${event.getConstantName()}, null);
	}

	</#if>
	<#if event.isSettable()>
	@Override
	public void set${event.getJavaBeanPropertyName()}(${event.getJavaWrapperInputType()} ${event.getJavaSafeName()}) {
		getStateHelper().put(${event.getConstantName()}, ${event.getJavaSafeName()});
	}

	</#if>
	</#list>
	<#if component.isRendererBaseClassRequired()>
	@Override
	public String getWidgetVar() {
		return (String) getStateHelper().eval(WIDGET_VAR, null);
	}

	@Override
	public void setWidgetVar(String widgetVar) {
		getStateHelper().put(WIDGET_VAR, widgetVar);
	}

	</#if>
	@Override
	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	@Override
	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}
}