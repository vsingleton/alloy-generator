<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
public abstract class Base${component.getCamelizedName()}Renderer extends AUIRenderer {
	
	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
	
		${component.getCamelizedName()} ${component.getUncapitalizedName()} = (${component.getCamelizedName()}) uiComponent;
		encodeHTML(facesContext, ${component.getUncapitalizedName()});
		encodeJavascript(facesContext, ${component.getUncapitalizedName()});
	}
	
	@Override
	protected abstract void encodeHTML(FacesContext facesContext, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException {}
	
	@Override
	protected void encodeJavascript(FacesContext facesContext, ${component.getCamelizedName()} ${component.getUncapitalizedName()}) throws IOException {
		
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		
		responseWriter.startElement("script", uiComponent);
		responseWriter.writeAttribute("type", "text/javascript", null);
		
		responseWriter.write(StringPool.FORWARD_SLASH);
		responseWriter.write(StringPool.FORWARD_SLASH);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.CDATA_OPEN);
		responseWriter.write(StringPool.NEW_LINE);
		
		responseWriter.write("YUI().use");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("'aui-${component.getCamelizedName()?lower_case}',");
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("function(Y) ");
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write("var ${component.getUncapitalizedName()} = new Y.${component.getCamelizedName()}");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		
		<#list component.getAttributes() as attribute>
		<#if attribute.isGettable()>
		responseWriter.write("${attribute.getUncapitalizedName()}: ");
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(${component.getUncapitalizedName()}.get${attribute.getCapitalizedName()}());
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(StringPool.NEW_LINE);
		</#if>
		</#list>
		
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.FORWARD_SLASH);
		responseWriter.write(StringPool.FORWARD_SLASH);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.CDATA_CLOSE);
		responseWriter.write(StringPool.NEW_LINE);
		
		responseWriter.endElement(SCRIPT);
		responseWriter.write(StringPool.NEW_LINE);
	}

}