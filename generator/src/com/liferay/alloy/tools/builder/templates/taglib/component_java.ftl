<#include "init.ftl">
<#include "copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName()};

<#if component.getWriteJSP() == true>
import javax.servlet.http.HttpServletRequest;
</#if>
import javax.servlet.jsp.JspException;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
public class ${component.getClassName()} extends HtmlInputText {

	private static final Logger logger = LoggerFactory.getLogger(${component.getClassName()}.class);

	<#list component.getAttributesAndEvents() as attribute>
	private static final String ${attribute.getConstantName()} = "${attribute.getSafeName()}";
	</#list>
	
	<#list component.getAttributesAndEvents() as attribute>
	<#if attribute.isGettable()>
	<#if (attribute.getDefaultValue()??) && attribute.getDefaultValue() != "">
		<#assign defaultValue = attribute.getDefaultValue()>
	<#else>
		<#assign defaultValue = "null">
	</#if>
	public ${attribute.getJSFInputType()} get${attribute.getCapitalizedName()}() {
		return (${attribute.getJSFInputType()}) getStateHelper().eval(${attribute.getConstantName()}, ${defaultValue});
	}
	</#if>
	
	<#if attribute.isSettable()>
	public void set${attribute.getCapitalizedName()}(${attribute.getJSFInputType()} ${attribute.getSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getSafeName()});
	}

	</#if>
	</#list>

}