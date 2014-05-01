<#include "../base/init.ftl">
<#include "../base/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};
//J-

import javax.annotation.Generated;
import ${component.getParentClass()};

import com.liferay.faces.util.component.Styleable;
<#if component.isAlloyComponent()>
import com.liferay.faces.util.component.ClientComponent;
</#if>

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ${component.getCamelizedName()}${BASE_CLASS_SUFFIX} extends ${component.getUnqualifiedParentClass()} implements Styleable<#if component.isAlloyComponent()>, ClientComponent, ${component.getCamelizedName()}Alloy</#if> {
	<#if !component.isAlloyComponent()>

	// Public Constants
	<#list component.getAttributesAndEvents()?sort_by("constantName") as attribute>
	<#if attribute.isGenerateJava()>
	private static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#if>
	</#list>
	</#if>
	<#list component.getAttributesAndEvents()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGenerateJava() && (attribute.isEvent() || !attribute.isJSFReservedAttribute())>
	<#if attribute.isGettable()>

	<#if component.isAlloyComponent()>
	@Override
	</#if>
	public ${attribute.getJavaWrapperType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJavaWrapperType()}) getStateHelper().eval(${attribute.getConstantName()}, <#if attribute.isEvent()>null<#else>${attribute.getGetterDefaultReturnValue()}</#if>);
	}
	</#if>
	<#if attribute.isSettable()>

	<#if component.isAlloyComponent()>
	@Override
	</#if>
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}
	</#if>
	</#if>
	</#list>
}
//J+
