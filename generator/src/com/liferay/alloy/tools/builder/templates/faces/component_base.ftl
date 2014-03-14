<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};
//J-

import javax.annotation.Generated;
import ${component.getParentClass()};

import com.liferay.faces.util.component.Styleable;
<#if component.isRendererBaseClassRequired()>
import com.liferay.faces.util.component.Widget;
</#if>

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ${component.getCamelizedName()}Base extends ${component.getUnqualifiedParentClass()} implements Styleable, <#if component.isRendererBaseClassRequired()>Widget, </#if>${component.getCamelizedName()}Component {
	<#list component.getFacesAttributesAndEvents()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired())>

	@Override
	public ${attribute.getJavaWrapperInputType()} <#if attribute.isEvent()>get<#else>${attribute.getGetterMethodPrefix()}</#if>${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJavaWrapperInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>
	<#if attribute.isSettable() && (attribute.isEvent() || attribute.isComponentPropertyRequired())>

	@Override
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}
	</#if>
	</#list>
}
//J+
