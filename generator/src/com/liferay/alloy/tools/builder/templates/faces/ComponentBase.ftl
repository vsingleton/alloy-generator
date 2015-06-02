<#include "../base/init.ftl">
<#include "../base/copyright.ftl">
<#compress>

<#assign isChildClassOfAttributeTagSupport = component.isChildClassOf("com.liferay.taglib.util.AttributesTagSupport")>
<#assign isChildClassOfIncludeTag = component.isChildClassOf("com.liferay.taglib.util.IncludeTag")>

</#compress>

package ${packagePath}.${component.getUncamelizedName()};
//J-

import javax.annotation.Generated;
import ${component.getParentClass()};

<#if component.isStyleable()>
import com.liferay.faces.util.component.Styleable;
</#if>
<#if component.isYui()>
import com.liferay.faces.util.component.ClientComponent;
</#if>

/**
<#list component.getAuthors()?sort as author>
 * @author	${author}
</#list>
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ${component.getCamelizedName()}Base extends ${component.getUnqualifiedParentClass()}<#if component.isStyleable() || component.isYui()> implements<#if component.isStyleable()> Styleable</#if><#if component.isStyleable() && component.isYui()>,</#if><#if component.isYui()> ClientComponent</#if></#if> {

	// Public Constants
	public static final String COMPONENT_TYPE = "${packagePath}.${component.getUncamelizedName()}.${component.getCamelizedName()}";
	<#if component.isGenerateRenderer()>
	public static final String RENDERER_TYPE = "${packagePath}.${component.getUncamelizedName()}.internal.${component.getCamelizedName()}Renderer";
	</#if>
	<#assign enumWritten = false>
	<#list component.getAttributes()?sort_by("constantName") as attribute>
	<#if attribute.isGenerateJava() && !attribute.isInherited()>
	<#if !enumWritten>

	// Protected Enumerations
	protected enum ${component.getCamelizedName()}PropertyKeys {
		<#rt>${attribute.getJavaSafeName()}
		<#assign enumWritten = true>
	<#else>
		<#lt>,
		<#rt>${attribute.getJavaSafeName()}
	</#if>
	</#if>
	</#list>
	<#if enumWritten>

	}
	</#if>

	public ${component.getCamelizedName()}Base() {
		super();
		setRendererType(<#if component.isGenerateRenderer()>RENDERER_TYPE<#else>""</#if>);
	}
	<#list component.getAttributes()?sort_by("javaBeanPropertyName") as attribute>
	<#if attribute.isGenerateJava() && !attribute.isInherited()>

	<#if attribute.isOverride()>
	@Override
	</#if>
	<#if attribute.getJavaWrapperType()?contains("<")>
	@SuppressWarnings("unchecked")
	</#if>
	public ${attribute.getUnprefixedType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		<#if attribute.getJavaBeanPropertyName() == "StyleClass">
		// getStateHelper().eval(${component.getCamelizedName()}PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(${component.getCamelizedName()}PropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "${namespace}-${component.getUncamelizedName("-")}"<#if component.getExtraStyleClasses()??>, "${component.getExtraStyleClasses()}"</#if>);
		<#else>
		return (${attribute.getJavaWrapperType()}) getStateHelper().eval(${component.getCamelizedName()}PropertyKeys.${attribute.getJavaSafeName()}, ${attribute.getDefaultValue()});
		</#if>
	}

	<#if attribute.isOverride()>
	@Override
	</#if>
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getUnprefixedType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${component.getCamelizedName()}PropertyKeys.${attribute.getJavaSafeName()}, ${attribute.getJavaSafeName()});
	}
	</#if>
	</#list>
}
//J+
