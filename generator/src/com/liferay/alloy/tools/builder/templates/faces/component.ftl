<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

<#if !component.isComponentBaseClassRequired()>
import com.liferay.faces.util.component.Styleable;
import ${component.getParentClass()};
</#if>
import javax.faces.component.FacesComponent;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
@FacesComponent(value = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}")
public class ${component.getCamelizedName()} <#if component.isComponentBaseClassRequired()>extends ${component.getCamelizedName()}Base <#else>extends ${component.getUnqualifiedParentClass()} implements Styleable, ${component.getCamelizedName()}Component </#if>{

	// Public Constants
	public static final String COMPONENT_FAMILY = "${packagePath}.${component.getUncamelizedName(BLANK)}";
	public static final String COMPONENT_TYPE = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}";

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
	<#list component.getFacesAttributes() as attribute>
	<#if attribute.isGettable() && attribute.isBeanPropertyRequired()>
	public ${attribute.getJavaWrapperInputType()} ${attribute.getGetterMethodPrefix()}${attribute.getJavaBeanPropertyName()}() {
		return (${attribute.getJavaWrapperInputType()}) getStateHelper().eval(${attribute.getConstantName()}, null);
	}
	</#if>

	<#if attribute.isSettable() && attribute.isBeanPropertyRequired()>
	public void set${attribute.getJavaBeanPropertyName()}(${attribute.getJavaWrapperInputType()} ${attribute.getJavaSafeName()}) {
		getStateHelper().put(${attribute.getConstantName()}, ${attribute.getJavaSafeName()});
	}

	</#if>
	</#list>
	<#list component.getEvents() as event>
	<#if event.isGettable()>
	public ${event.getJavaWrapperInputType()} get${event.getJavaBeanPropertyName()}() {
		return (${event.getJavaWrapperInputType()}) getStateHelper().eval(${event.getConstantName()}, null);
	}
	</#if>

	<#if event.isSettable()>
	public void set${event.getJavaBeanPropertyName()}(${event.getJavaWrapperInputType()} ${event.getJavaSafeName()}) {
		getStateHelper().put(${event.getConstantName()}, ${event.getJavaSafeName()});
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