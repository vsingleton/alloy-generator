<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.Widget;
import ${component.getParentClass()};

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
public abstract class ${component.getCamelizedName()}Base extends ${component.getUnqualifiedParentClass()} implements Styleable, Widget, ${component.getCamelizedName()}Component {

	<#list component.getFacesAttributes() as attribute>
	<#if attribute.isGettable() && attribute.isBeanPropertyRequired()>
	public ${attribute.getJavaWrapperInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJavaWrapperInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>

	<#if attribute.isSettable() && attribute.isBeanPropertyRequired()>
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}

	</#if>
	</#list>
	<#list component.getEvents() as event>
	<#if event.isGettable()>
	public ${event.getJavaWrapperInputType()} get${event.getJavaBeanPropertyName()}() {
		return (${event.getJavaWrapperInputType()}) getStateHelper().eval(${event.getConstantName()}, null);
	}
	</#if>

	<#if event.isSettable()>
	public void set${event.getJavaBeanPropertyName()}(${event.getJavaWrapperInputType()} ${event.getJavaSafeName()}) {
		getStateHelper().put(${event.getConstantName()}, ${event.getJavaSafeName()});
	}

	</#if>
	</#list>
	public String getWidgetVar() {
		return (String) getStateHelper().eval(WIDGET_VAR, null);
	}

	public void setWidgetVar(String widgetVar) {
		getStateHelper().put(WIDGET_VAR, widgetVar);
	}

	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}
}