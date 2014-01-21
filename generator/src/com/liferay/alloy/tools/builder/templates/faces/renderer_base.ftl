<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.base.RendererBase;
import com.liferay.faces.util.lang.StringPool;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 * @generated
 */
@ResourceDependency(library = "aui", name = "liferay-faces.js")
public abstract class ${component.getCamelizedName()}RendererBase extends RendererBase {

	// Private Constants
	private static final String AUI_MODULE_NAME = "${component.getModule()}";

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		${component.getCamelizedName()} ${component.getUncapitalizedName()} = (${component.getCamelizedName()}) uiComponent;

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.write("var ");
		responseWriter.write(${component.getUncapitalizedName()}.getWidgetVar());
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write("LF.component('");
		responseWriter.write(${component.getUncapitalizedName()}.getWidgetVar());
		responseWriter.write("', function() { if (!");
		responseWriter.write(${component.getUncapitalizedName()}.getWidgetVar());
		responseWriter.write(") {");
		responseWriter.write(${component.getUncapitalizedName()}.getWidgetVar());
		responseWriter.write("= new A.${component.getCamelizedName()}");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		boolean isFirst = true;

		<#list component.getAttributes() as attribute>
		<#if attribute.isGettable()>
		${attribute.getJSFInputType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}();
		
		if (${attribute.getJavaSafeName()} != null) {

			render${attribute.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}, ${attribute.getJavaSafeName()}, isFirst);
			isFirst = false;
		}

		</#if>
		</#list>
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(StringPool.NEW_LINE);

		responseWriter.write("after");
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		isFirst = true;

		<#list component.getAfterEvents() as event>
		${event.getJSFInputType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}.get${event.getCapitalizedName()}();

		if (${event.getJavaSafeName()} != null) {

			render${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}, ${event.getJavaSafeName()}, isFirst);
			isFirst = false;
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

		isFirst = true;

		<#list component.getOnEvents() as event>
		${event.getJSFInputType()} ${event.getJavaSafeName()} = ${component.getUncapitalizedName()}.get${event.getCapitalizedName()}();

		if (${event.getJavaSafeName()} != null) {

			render${event.getCapitalizedName()}(responseWriter, ${component.getUncapitalizedName()}, ${event.getJavaSafeName()}, isFirst);
			isFirst = false;
		}

		</#list>
		responseWriter.write("}}).render();} return ");
		responseWriter.write(${component.getUncapitalizedName()}.getWidgetVar());
		responseWriter.write(";}); LF.component('");
		responseWriter.write(${component.getUncapitalizedName()}.getWidgetVar());
		responseWriter.write("');");
	}

	protected String getModule() {
		return AUI_MODULE_NAME;
	}

	<#list component.getAttributes() as attribute>
	protected void render${attribute.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()} ${component.getUncapitalizedName()}, ${attribute.getJSFInputType()} ${attribute.getJavaSafeName()}, boolean isFirst) throws IOException {
		render${attribute.getJavaScriptType()}(responseWriter, ${component.getCamelizedName()}.${attribute.getConstantName()}, ${attribute.getJavaSafeName()}, isFirst);
	}

	</#list>
	<#list component.getEvents() as event>
	protected void render${event.getCapitalizedName()}(ResponseWriter responseWriter, ${component.getCamelizedName()} ${component.getUncapitalizedName()}, ${event.getJSFInputType()} ${event.getJavaSafeName()}, boolean isFirst) throws IOException {
		render${event.getJavaScriptType()}(responseWriter, ${component.getCamelizedName()}.${event.getConstantName()}, ${event.getJavaSafeName()}, isFirst);
	}

	</#list>
}