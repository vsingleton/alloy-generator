<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.Widget;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
public abstract class ${component.getCamelizedName()}Base extends ${component.getParentClass()} implements Styleable, Widget {

	// Public Constants
	<#list component.getAttributesAndEvents() as attribute>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#list>
	public static final String WIDGET_VAR = "widgetVar";
	public static final String CSS_CLASS = "cssClass";
	public static final String STYLE_CLASS = "styleClass";

	<#list component.getAttributesAndEvents() as attribute>
	<#if attribute.isGettable()>
	public ${attribute.getJSFInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJSFInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>

	<#if attribute.isSettable()>
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJSFInputType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
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