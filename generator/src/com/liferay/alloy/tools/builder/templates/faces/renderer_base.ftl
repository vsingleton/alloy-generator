<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

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
@ResourceDependency(library = "aui", name = "aui.js")
public abstract class ${component.getCamelizedName()}RendererBase extends RendererBase {

	// Private Constants
	private static final String AUI_MODULE_NAME = "${component.getModule()}";

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		${component.getCamelizedName()} ${component.getUncapitalizedName()} = (${component.getCamelizedName()}) uiComponent;

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.write("var ${component.getUncapitalizedName()} = new A.${component.getCamelizedName()}");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		List<String> renderedAttributes = new ArrayList<String>();

		<#list component.getAttributes() as attribute>
		<#if attribute.isGettable()>
		if (${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}() != null) {
			render${attribute.getCapitalizedName()}(renderedAttributes, ${component.getUncapitalizedName()});
		}
		
		</#if>
		</#list>

		for (String renderedAttribute : renderedAttributes) {
			responseWriter.write(renderedAttribute);
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write("after");
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		List<String> renderedAfterEvents = new ArrayList<String>();

		<#list component.getAfterEvents() as event>
		if (${component.getUncapitalizedName()}.get${event.getCapitalizedName()}() != null) {
			render${event.getCapitalizedName()}(renderedAfterEvents, ${component.getUncapitalizedName()});
		}
		
		</#list>

		Iterator<String> afterEventsIterator = renderedAfterEvents.iterator();

		while (afterEventsIterator.hasNext()) {
			responseWriter.write(afterEventsIterator.next());

			if (afterEventsIterator.hasNext()) {
				responseWriter.write(StringPool.COMMA);
			}
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.COMMA);

		responseWriter.write("on");
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		List<String> renderedOnEvents = new ArrayList<String>();

		<#list component.getOnEvents() as event>
		if (${component.getUncapitalizedName()}.get${event.getCapitalizedName()}() != null) {
			render${event.getCapitalizedName()}(renderedOnEvents, ${component.getUncapitalizedName()});
		}
		
		</#list>

		Iterator<String> onEventsIterator = renderedOnEvents.iterator();

		while (onEventsIterator.hasNext()) {
			responseWriter.write(onEventsIterator.next());

			if (onEventsIterator.hasNext()) {
				responseWriter.write(StringPool.COMMA);
			}
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(".render()");
		responseWriter.write(StringPool.SEMICOLON);
	}

	protected String getModule() {
		return AUI_MODULE_NAME;
	}

	<#list component.getAttributes() as attribute>
	protected void render${attribute.getCapitalizedName()}(List<String> renderedAttributes, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException {
		${attribute.getJSFInputType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}();
		renderedAttributes.add(render${attribute.getJavaScriptType()}(${component.getCamelizedName()}.${attribute.getConstantName()}, ${attribute.getJavaSafeName()}));
	}

	</#list>
	<#list component.getEvents() as event>
	protected void render${event.getCapitalizedName()}(List<String> renderedAttributes, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException {
		${event.getJSFInputType()} ${event.getSafeName()} = ${component.getUncapitalizedName()}.get${event.getCapitalizedName()}();
		renderedAttributes.add(render${event.getJavaScriptType()}(${component.getCamelizedName()}.${event.getConstantName()}, ${event.getSafeName()}));
	}

	</#list>
}