<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import com.liferay.faces.util.component.Widget;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
public abstract class ${component.getCamelizedName()}Base extends ${component.getParentClass()} implements Widget {

	<#list component.getAttributesAndEvents() as attribute>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#list>

	public static final String WIDGET_VAR = "widgetVar";

	<#list component.getAttributesAndEvents() as attribute>
	<#if attribute.isGettable()>
	public ${attribute.getJSFInputType()} get${attribute.getCapitalizedName()}() {
		return (${attribute.getJSFInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>

	<#if attribute.isSettable()>
	public void set${attribute.getCapitalizedName()}(${attribute.getJSFInputType()} ${attribute.getJavaSafeName()}) {
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
}