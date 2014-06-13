<#include "../base/init.ftl">
<#include "../base/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import ${component.getRendererParentClass()};

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ${component.getCamelizedName()}${RENDERER_BASE_CLASS_SUFFIX} extends ${component.getUnqualifiedRendererParentClass()} {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "${component.getYuiClassName()}";
	private static final String ALLOY_MODULE_NAME = "${component.getModule()}";
	<#list component.getAttributes()?sort_by("yuiName") as attribute>
	<#if attribute.isGenerateJava() && attribute.isYui() && (attribute.getName() != attribute.getYuiName())>
	protected static final String ${attribute.getYuiConstantName()} = "${attribute.getYuiName()}";
	</#if>
	</#list>

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		${component.getCamelizedName()} ${component.getUncapitalizedName()} = (${component.getCamelizedName()}) uiComponent;
		boolean first = true;
		<#list component.getAttributes()?sort_by("javaSafeName") as attribute>
		<#if attribute.isGenerateJava() && attribute.isYui()>

		${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}.${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();

		if (${attribute.getJavaSafeName()} != null) {

			encode${attribute.getYuiName()?cap_first}(responseWriter, ${component.getUncapitalizedName()}, ${attribute.getJavaSafeName()}, first);
			first = false;
		}
		</#if>
		</#list>

		encodeHiddenAttributes(responseWriter, ${component.getUncapitalizedName()}, first);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}
	<#list component.getAttributes()?sort_by("capitalizedName") as attribute>
	<#if attribute.isGenerateJava() && attribute.isYui()>

	protected void encode${attribute.getYuiName()?cap_first}(ResponseWriter responseWriter, ${component.getCamelizedName()} ${component.getUncapitalizedName()}, ${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()}, boolean first) throws IOException {
		encode${attribute.getYuiType()}(responseWriter, <#if attribute.getName() == attribute.getYuiName()>${component.getCamelizedName()}.${attribute.getConstantName()}<#else>${attribute.getYuiConstantName()}</#if>, ${attribute.getJavaSafeName()}, first);
	}
	</#if>
	</#list>

	protected void encodeHiddenAttributes(ResponseWriter responseWriter, ${component.getCamelizedName()} ${component.getUncapitalizedName()}, boolean first) throws IOException {
		// no-op
	}
}
//J+