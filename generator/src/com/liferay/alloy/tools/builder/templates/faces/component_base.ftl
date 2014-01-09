<#include "../common/init.ftl">
<#include "../common/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName(BLANK)};

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
public abstract class ${component.getCamelizedName()}Base extends ${component.getParentClass()} {

	<#list component.getAttributesAndEvents() as attribute>
	private static final String ${attribute.getConstantName()} = "${attribute.getSafeName()}";
	</#list>

	<#list component.getAttributesAndEvents() as attribute>
	<#if attribute.isGettable()>
	protected ${attribute.getJSFInputType()} get${attribute.getCapitalizedName()}() {
		return (${attribute.getJSFInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>

	<#if attribute.isSettable()>
	protected void set${attribute.getCapitalizedName()}(${attribute.getJSFInputType()} ${attribute.getSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getSafeName()});
	}

	</#if>
	</#list>
}