<#include "../base/init.ftl">
<#include "../base/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import ${component.getRendererParentClass()};
import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.lang.StringPool;

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ${component.getCamelizedName()}${RENDERER_BASE_CLASS_SUFFIX} extends ${component.getUnqualifiedRendererParentClass()} {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "${component.getCamelizedName()}";
	private static final String ALLOY_MODULE_NAME = ${component.getModuleString()};

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX} ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX} = (${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX}) uiComponent;
		boolean first = true;
		<#list component.getAttributes()?sort_by("javaSafeName") as attribute>
		<#if attribute.isGettable() && attribute.isGenerateJava() && attribute.isJavaScript()>

		${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}.${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();

		if (${attribute.getJavaSafeName()} != null) {

			encode${attribute.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}Alloy, ${attribute.getJavaSafeName()}, first);
			first = false;
		}
		</#if>
		</#list>
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
	<#if attribute.isGettable() && attribute.isGenerateJava() && attribute.isJavaScript()>

	protected void encode${attribute.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX} ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}, ${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()}, boolean first) throws IOException {
		encode${attribute.getFacesJavaScriptType()}(responseWriter, ${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX}.${attribute.getConstantName()}, ${attribute.getJavaSafeName()}, first);
	}
	</#if>
	</#list>
}
//J+