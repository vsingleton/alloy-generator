<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.util.render.AUIRendererBase;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Widget;
import com.liferay.faces.util.lang.StringPool;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
@ResourceDependency(library = "liferay-faces", name = "liferay-faces.js")
public abstract class ${component.getCamelizedName()}RendererBase extends AUIRendererBase {

	// Private Constants
	private static final String A_CLASS_NAME = "A.${component.getCamelizedName()}";
	private static final String AUI_MODULE_NAME = ${component.getModuleString()};
	<#list component.getOnEvents() as event>
	<#if event.createConstant()>
	private static final String ${event.getConstantUnprefixedName()} = "${event.getUnprefixedName()}";
	</#if>
	</#list>

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		${component.getCamelizedName()}Component ${component.getUncapitalizedName()}Component = (${component.getCamelizedName()}Component) uiComponent;
		String widgetVar = ComponentUtil.resolveWidgetVar(facesContext, (Widget) ${component.getUncapitalizedName()}Component);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.write(VAR);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(LF_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(FUNCTION);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(IF);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.EXCLAMATION);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.EQUAL);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(NEW);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(A_CLASS_NAME);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		boolean first = true;

		<#list component.getAttributes() as attribute>
		<#if attribute.isGettable() && attribute.isBeanPropertyRequired()>
		${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}Component.${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();

		if (${attribute.getJavaSafeName()} != null) {

			encode${attribute.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}Component, ${attribute.getJavaSafeName()}, first);
			first = false;
		}

		</#if>
		</#list>
		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(StringPool.NEW_LINE);

		responseWriter.write(AFTER);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		first = true;

		<#list component.getAfterEvents() as event>
		${event.getJavaWrapperInputType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}Component.get${event.getJavaBeanPropertyName()}();

		if (${event.getJavaSafeName()} != null) {

			encode${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}Component, ${event.getJavaSafeName()}, first);
			first = false;
		}

		</#list>
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(StringPool.NEW_LINE);

		responseWriter.write(ON);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		first = true;

		<#list component.getOnEvents() as event>
		${event.getJavaWrapperInputType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}Component.get${event.getJavaBeanPropertyName()}();

		if (${event.getJavaSafeName()} != null) {

			encode${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}Component, ${event.getJavaSafeName()}, first);
			first = false;
		}

		</#list>
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(RETURN);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(LF_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
	}

	protected String getModule() {
		return AUI_MODULE_NAME;
	}

	<#list component.getAttributes() as attribute>
	<#if attribute.isGettable() && attribute.isBeanPropertyRequired()>
	protected void encode${attribute.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()}Component ${component.getUncapitalizedName()}Component, ${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}, boolean first) throws IOException {
		encode${attribute.getJavaScriptType()}(responseWriter, ${component.getCamelizedName()}Component.${attribute.getConstantName()}, ${attribute.getJavaSafeName()}, first);
	}

	</#if>
	</#list>
	<#list component.getEvents() as event>
	protected void encode${event.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()}Component ${component.getUncapitalizedName()}Component, ${event.getJavaWrapperInputType()} ${event.getJavaSafeName()}, boolean first) throws IOException {
		encodeEvent(responseWriter, ${event.getConstantUnprefixedName()}, ${event.getJavaSafeName()}, first);
	}

	</#list>
}