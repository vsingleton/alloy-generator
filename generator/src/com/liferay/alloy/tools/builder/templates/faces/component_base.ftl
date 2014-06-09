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
<#if component.isYui()>
import com.liferay.faces.util.component.ClientComponent;
</#if>

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ${component.getCamelizedName()}${BASE_CLASS_SUFFIX} extends ${component.getUnqualifiedParentClass()} implements Styleable<#if component.isYui()>, ClientComponent</#if> {

	// Public Constants
	<#list component.getAttributes()?sort_by("constantName") as attribute>
	<#if attribute.isGenerateJava()>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#if>
	</#list>
	<#list component.getAttributes()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGenerateJava()>

	<#if attribute.isOverride()>
	@Override
	</#if>
	public ${attribute.getUnprefixedType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJavaWrapperType()}) getStateHelper().eval(${attribute.getConstantName()}, ${attribute.getDefaultValue()});
	}

	<#if attribute.isOverride()>
	@Override
	</#if>
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getUnprefixedType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}
	</#if>
	</#list>
}
//J+