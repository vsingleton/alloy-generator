<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.base.AUIRenderer;
import com.liferay.faces.alloy.renderkit.BufferedResponseWriter;
import com.liferay.faces.util.lang.StringPool;


/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
public abstract class ${component.getCamelizedName()}RendererBase extends AUIRenderer {

	// Private Constants
	private static final String AUI_MODULE_NAME = "${component.getModule()}";

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent component) throws IOException {

		${component.getCamelizedName()} ${component.getUncapitalizedName()} = (${component.getCamelizedName()}) component;

		BufferedResponseWriter bufferedResponseWriter = (BufferedResponseWriter) facesContext.getResponseWriter();

		bufferedResponseWriter.write("var ${component.getUncapitalizedName()} = new A.${component.getCamelizedName()}");
		bufferedResponseWriter.write(StringPool.OPEN_PARENTHESIS);
		bufferedResponseWriter.write(StringPool.OPEN_CURLY_BRACE);

		ArrayList<String> renrederedAttributes = new ArrayList<String>();

		<#list component.getAttributes() as attribute>
		<#if attribute.isGettable()>
		render${attribute.getCapitalizedName()}(renrederedAttributes, ${component.getUncapitalizedName()});
		</#if>
		</#list>

		Iterator<String> it = renrederedAttributes.iterator();

		while (it.hasNext()) {
			bufferedResponseWriter.write(it.next());

			if (it.hasNext()) {
				bufferedResponseWriter.write(StringPool.COMMA);
			}
		}

		bufferedResponseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		bufferedResponseWriter.write(StringPool.CLOSE_PARENTHESIS);
		bufferedResponseWriter.write(".render()");
		bufferedResponseWriter.write(StringPool.SEMICOLON);
	}

	protected String getModule() {
		return AUI_MODULE_NAME;
	}

	<#list component.getAttributes() as attribute>
	<#if attribute.isGettable()>
	protected void render${attribute.getCapitalizedName()}(ArrayList<String> renrederedAttributes, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException {
		if (${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}() != null) {
			renrederedAttributes.add(render${attribute.getJavaScriptType()}("${attribute.getUncapitalizedName()}", ${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}()));
		}
	}

	</#if>
	</#list>
}