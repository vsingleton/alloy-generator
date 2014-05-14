<#include "../base/init.ftl">
<#include "../base/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

<#if !component.isAlloyComponent()>
import ${component.getRendererBaseClass()};
<#else>
import javax.faces.application.ResourceDependencies;
</#if>
import javax.faces.application.ResourceDependency;
import javax.faces.render.FacesRenderer;

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@FacesRenderer(componentFamily = ${component.getCamelizedName()}.COMPONENT_FAMILY, rendererType = ${component.getCamelizedName()}.RENDERER_TYPE)
<#if component.isAlloyComponent()>
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
<#else>
@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
</#if>
public class ${component.getCamelizedName()}${RENDERER_CLASS_SUFFIX} extends ${component.getUnqualifiedRendererBaseClass()} {
}