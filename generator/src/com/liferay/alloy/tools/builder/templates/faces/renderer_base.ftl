<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

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
		render${attribute.getCapitalizedName()}(renderedAttributes, ${component.getUncapitalizedName()});
		</#if>
		</#list>

		Iterator<String> it = renderedAttributes.iterator();

		while (it.hasNext()) {
			responseWriter.write(it.next());

			if (it.hasNext()) {
				responseWriter.write(StringPool.COMMA);
			}
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(".render()");
		responseWriter.write(StringPool.SEMICOLON);
	}

	protected String getModule() {
		return AUI_MODULE_NAME;
	}

	<#list component.getAttributes() as attribute>
	<#if attribute.isGettable()>
	protected void render${attribute.getCapitalizedName()}(List<String> renderedAttributes, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException {
		${attribute.getJSFInputType()} ${attribute.getJavaSafeName()} = ${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}();

		if (${attribute.getJavaSafeName()} != null) {
			renderedAttributes.add(render${attribute.getJavaScriptType()}(${component.getCamelizedName()}.${attribute.getConstantName()}, ${attribute.getJavaSafeName()}));
		}
	}

	</#if>
	</#list>
}