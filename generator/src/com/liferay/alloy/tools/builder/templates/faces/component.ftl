<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

<#if !component.isComponentBaseClassRequired()>
import ${component.getParentClass()};
</#if>
import javax.faces.component.FacesComponent;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
@FacesComponent(value = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}")
public class ${component.getCamelizedName()} <#if component.isComponentBaseClassRequired()>extends ${component.getCamelizedName()}Base <#else>extends ${component.getUnqualifiedParentClass()} implements Styleable </#if>{

	// Public Constants
	public static final String COMPONENT_FAMILY = "${packagePath}.${component.getUncamelizedName(BLANK)}";
	public static final String COMPONENT_TYPE = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}";
	<#if !component.isComponentBaseClassRequired()>

	<#list component.getAttributesAndEvents() as attribute>
	public static final String ${attribute.getConstantName()} = "${attribute.getName()}";
	</#list>
	public static final String CSS_CLASS = "cssClass";
	public static final String STYLE_CLASS = "styleClass";
	</#if>

	// Private Constants
	private static final String RENDERER_TYPE = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}Renderer";
	
	public ${component.getCamelizedName()}() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	<#if !component.isComponentBaseClassRequired()>
	<#list component.getAttributesAndEvents() as attribute>
	<#if attribute.isGettable()>
	public ${attribute.getJSFInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJSFInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>

	<#if attribute.isSettable()>
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJSFInputType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}

	</#if>
	</#list>
	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}
	</#if>
}