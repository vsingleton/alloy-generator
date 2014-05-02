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
	<#list component.getOnEvents()?sort_by("constantUnprefixedName") as event>
	<#if event.createConstant()>
	private static final String ${event.getConstantUnprefixedName()} = "${event.getUnprefixedName()}";
	</#if>
	</#list>

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX} ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX} = (${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX}) uiComponent;
		boolean first = true;
		<#list component.getAttributes()?sort_by("javaSafeName") as attribute>
		<#if attribute.isGettable() && attribute.isGenerateJava()>

		${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}.${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();

		if (${attribute.getJavaSafeName()} != null) {

			encode${attribute.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}Alloy, ${attribute.getJavaSafeName()}, first);
			first = false;
		}
		</#if>
		</#list>

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;
		<#list component.getAfterEvents()?sort_by("javaSafeName") as event>

		${event.getJavaWrapperType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}.get${event.getJavaBeanPropertyName()}();

		if (${event.getJavaSafeName()} != null) {

			encode${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}, ${event.getJavaSafeName()}, first);
			first = false;
		}
		</#list>

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;
		<#list component.getOnEvents()?sort_by("javaSafeName") as event>

		${event.getJavaWrapperType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}.get${event.getJavaBeanPropertyName()}();

		if (${event.getJavaSafeName()} != null) {

			encode${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}, ${event.getJavaSafeName()}, first);
			first = false;
		}
		</#list>

		// End encoding "on" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}
	<#list component.getAttributesAndEvents()?sort_by("capitalizedName") as attribute>
	<#if attribute.isEvent()>

	protected void encode${attribute.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX} ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}, ${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()}, boolean first) throws IOException {
		encodeEvent(responseWriter, ${attribute.getConstantUnprefixedName()}, ${attribute.getJavaSafeName()}, first);
	}
	<#elseif attribute.isGettable() && attribute.isGenerateJava()>

	protected void encode${attribute.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX} ${component.getUncapitalizedName()}${INTERFACE_CLASS_SUFFIX}, ${attribute.getJavaWrapperType()} ${attribute.getJavaSafeName()}, boolean first) throws IOException {
		encode${attribute.getJavaScriptType()}(responseWriter, ${component.getCamelizedName()}${INTERFACE_CLASS_SUFFIX}.${attribute.getConstantName()}, ${attribute.getJavaSafeName()}, first);
	}
	</#if>
	</#list>
}
//J+
