<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Widget;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererBase;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
@ResourceDependency(library = "liferay-faces", name = "liferay-faces.js")
public abstract class ${component.getCamelizedName()}RendererBase extends RendererBase {

	// Private Constants
	private static final String AUI_MODULE_NAME = ${component.getModuleString()};
	<#list component.getOnEvents() as event>
	<#if event.createConstant()>
	private static final String ${event.getConstantUnprefixedName()} = "${event.getUnprefixedName()}";
	</#if>
	</#list>

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		${component.getCamelizedName()} ${component.getUncapitalizedName()} = (${component.getCamelizedName()}) uiComponent;

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		String widgetVar = ComponentUtil.resolveWidgetVar(facesContext, (Widget) ${component.getUncapitalizedName()});

		responseWriter.write("var ");
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("LF.component('");
		responseWriter.write(widgetVar);
		responseWriter.write("', function() {");
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("if (!");
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(widgetVar);
		responseWriter.write("= new A.${component.getCamelizedName()}");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		boolean first = true;

		<#list component.getAttributes() as attribute>
		<#if attribute.isGettable()>
		${attribute.getJSFInputType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}.${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}();

		if (${attribute.getJavaSafeName()} != null) {

			encode${attribute.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}, ${attribute.getJavaSafeName()}, first);
			first = false;
		}

		</#if>
		</#list>
		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(StringPool.NEW_LINE);

		responseWriter.write("after");
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		first = true;

		<#list component.getAfterEvents() as event>
		${event.getJSFInputType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}.${event.getGetterMethodPrefix()}${event.getJavaBeanPropertyName()}();

		if (${event.getJavaSafeName()} != null) {

			encode${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}, ${event.getJavaSafeName()}, first);
			first = false;
		}

		</#list>
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(StringPool.NEW_LINE);

		responseWriter.write("on");
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		first = true;

		<#list component.getOnEvents() as event>
		${event.getJSFInputType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}.${event.getGetterMethodPrefix()}${event.getJavaBeanPropertyName()}();

		if (${event.getJavaSafeName()} != null) {

			encode${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}, ${event.getJavaSafeName()}, first);
			first = false;
		}

		</#list>
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("});");
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("return ");
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("});");
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("LF.component('");
		responseWriter.write(widgetVar);
		responseWriter.write("');");
		responseWriter.write(StringPool.NEW_LINE);
	}

	protected String getModule() {
		return AUI_MODULE_NAME;
	}

	<#list component.getAttributes() as attribute>
	protected void encode${attribute.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()} ${component.getUncapitalizedName()}, ${attribute.getJSFInputType()} ${attribute.getJavaSafeName()}, boolean first) throws IOException {
		encode${attribute.getJavaScriptType()}(responseWriter, ${component.getCamelizedName()}.${attribute.getConstantName()}, ${attribute.getJavaSafeName()}, first);
	}

	</#list>
	<#list component.getEvents() as event>
	protected void encode${event.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()} ${component.getUncapitalizedName()}, ${event.getJSFInputType()} ${event.getJavaSafeName()}, boolean first) throws IOException {
		encodeEvent(responseWriter, ${event.getConstantUnprefixedName()}, ${event.getJavaSafeName()}, first);
	}

	</#list>
}