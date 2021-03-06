<#include "../base/init.ftl">
<#include "../base/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getPackage()}.base;

<#if component.isWriteJSP() == true>
import javax.servlet.http.HttpServletRequest;
</#if>
import javax.servlet.jsp.JspException;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
public class Base${component.getClassName()} extends ${component.getParentClass()} {

	@Override
	public int doStartTag() throws JspException {
		<#if isChildClassOfAttributeTagSupport == true>
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		</#if>
		return super.doStartTag();
	}

	<#list component.getAttributesAndEvents()?sort_by("capitalizedName") as attribute>
	<#if attribute.isGettable()>
	public <#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getRawInputType()}</#if> get${attribute.getCapitalizedName()}() {
		return _${attribute.getSafeName()};
	}

	</#if>
	</#list>
	<#list component.getAttributesAndEvents()?sort_by("capitalizedName") as attribute>
	<#if attribute.isSettable()>
	public void set${attribute.getCapitalizedName()}(<#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getRawInputType()}</#if> ${attribute.getSafeName()}) {
		_${attribute.getSafeName()} = ${attribute.getSafeName()};
		<#if isChildClassOfAttributeTagSupport == true>

		setScopedAttribute("${attribute.getSafeName()}", ${attribute.getSafeName()});
		</#if>
	}

	</#if>
	</#list>
	<#if isChildClassOfIncludeTag == true>
	@Override
	</#if>
	protected void cleanUp() {
	<#list component.getAttributesAndEvents() as attribute>
		<#compress>
		<#assign outputSimpleClassName = attribute.getTypeSimpleClassName()>
		<#if !attribute.isEvent()>
			<#assign outputSimpleClassName = attribute.getOutputTypeSimpleClassName()>
		</#if>

		<#assign defaultValue = "">

		<#if attribute.getDefaultValue()??>
			<#assign defaultValue = attribute.getDefaultValue()>
		</#if>

		<#assign defaultValue = getCleanUpValue(outputSimpleClassName, defaultValue)>

		</#compress>
		<#if attribute.isGettable() || attribute.isSettable()>
		_${attribute.getSafeName()} = ${defaultValue};
		</#if>
	</#list>
	}

	<#if component.isBodyContent() == true>
	<#if isChildClassOfIncludeTag == true>
	@Override
	</#if>
	protected String getEndPage() {
		return _END_PAGE;
	}

	<#if isChildClassOfIncludeTag == true>
	@Override
	</#if>
	protected String getStartPage() {
		return _START_PAGE;
	}
	<#else>
	<#if isChildClassOfIncludeTag == true>
	@Override
	</#if>
	protected String getPage() {
		return _PAGE;
	}
	</#if>

	<#if component.isWriteJSP() == true>
	<#if isChildClassOfIncludeTag == true>
	@Override
	</#if>
	protected void setAttributes(HttpServletRequest request) {
		<#list component.getAttributesAndEvents() as attribute>
		<#if isChildClassOfAttributeTagSupport == true>
		setNamespacedAttribute(request, "${attribute.getSafeName()}", _${attribute.getSafeName()});
		<#else>
		request.setAttribute("${attribute.getName()}", _${attribute.getSafeName()});
		</#if>
		</#list>
	}

	</#if>
	<#if isChildClassOfAttributeTagSupport == true>
	protected static final String _ATTRIBUTE_NAMESPACE = "${namespace}";

	</#if>
	<#if component.isBodyContent() == true>
	private static final String _END_PAGE =
		"${jspRelativePath}/end.jsp";

	private static final String _START_PAGE =
		"${jspRelativePath}/start.jsp";
	<#else>
	private static final String _PAGE =
		"${jspRelativePath}/page.jsp";
	</#if>

	<#list component.getAttributesAndEvents()?sort_by("safeName") as attribute>
	<#compress>
	<#assign outputSimpleClassName = attribute.getTypeSimpleClassName()>
	<#if !attribute.isEvent()>
		<#assign outputSimpleClassName = attribute.getOutputTypeSimpleClassName()>
	</#if>

	<#assign defaultValue = "">

	<#if attribute.getDefaultValue()??>
		<#assign defaultValue = attribute.getDefaultValue()>
	</#if>

	<#assign defaultValue = getCleanUpValue(outputSimpleClassName, defaultValue)>

	</#compress>
	<#if attribute.isGettable() || attribute.isSettable()>
	private <#if attribute.isEvent()>${attribute.getType()}<#else>${attribute.getRawInputType()}</#if> _${attribute.getSafeName()} = ${defaultValue};
	</#if>
	</#list>

}