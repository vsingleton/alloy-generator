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
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 * @generated
 */
public abstract class ${component.getCamelizedName()}Base extends ${component.getUnqualifiedParentClass()} implements Styleable, <#if component.isRendererBaseClassRequired()>Widget, </#if>${component.getCamelizedName()}Component {
	<#list component.getFacesAttributesAndEvents()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired())>
	<#if (attribute.getName() != "widgetVar") || (attribute.getName() == "widgetVar") && component.isRendererBaseClassRequired()>

	@Override
	public ${attribute.getJavaWrapperInputType()} <#if attribute.isEvent()>get<#else>${attribute.getGetterMethodPrefix()}</#if>${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJavaWrapperInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>
	</#if>
	<#if attribute.isSettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired())>
	<#if (attribute.getName() != "widgetVar") || (attribute.getName() == "widgetVar") && component.isRendererBaseClassRequired()>

	@Override
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}
	</#if>
	</#if>
	</#list>
}