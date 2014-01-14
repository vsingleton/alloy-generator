<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import java.io.IOException;

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
	private static final String  ${component.getModuleConstantName()} = "${component.getModule()}";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);
		${component.getCamelizedName()} ${component.getUncapitalizedName()} = (${component.getCamelizedName()}) uiComponent;
		encodeHTML(facesContext, ${component.getUncapitalizedName()});
		encodeJavaScript(facesContext, ${component.getUncapitalizedName()});
	}

	protected abstract void encodeHTML(FacesContext facesContext, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException;

	protected void encodeJavaScript(FacesContext facesContext, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException {

		ResponseWriter backupResponseWriter = facesContext.getResponseWriter();
		
		BufferedResponseWriter bufferedResponseWriter = (BufferedResponseWriter) facesContext.getResponseWriter();

		beginJavaScript(facesContext, ${component.getUncapitalizedName()}, ${component.getModuleConstantName()});

		bufferedResponseWriter.write("var ${component.getUncapitalizedName()} = new Y.${component.getCamelizedName()}");
		bufferedResponseWriter.write(StringPool.OPEN_PARENTHESIS);
		bufferedResponseWriter.write(StringPool.OPEN_CURLY_BRACE);
		bufferedResponseWriter.write(StringPool.NEW_LINE);

		<#list component.getAttributes() as attribute>
		<#if attribute.isGettable()>
		if(${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}() != null)
		{

			bufferedResponseWriter.write("${attribute.getUncapitalizedName()}: ");
			bufferedResponseWriter.write(StringPool.APOSTROPHE);
			bufferedResponseWriter.write(${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}().toString());
			bufferedResponseWriter.write(StringPool.APOSTROPHE);
			bufferedResponseWriter.write(StringPool.COMMA);
			bufferedResponseWriter.write(StringPool.NEW_LINE);
		}

		</#if>
		</#list>
		bufferedResponseWriter.write(StringPool.NEW_LINE);
		bufferedResponseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		bufferedResponseWriter.write(StringPool.CLOSE_PARENTHESIS);
		bufferedResponseWriter.write(StringPool.SEMICOLON);
		
		endJavaScript(facesContext);
		
		handleBuffer(facesContext, ${component.getUncapitalizedName()}, ${component.getModuleConstantName()});
		
		facesContext.setResponseWriter(backupResponseWriter);
	}

}