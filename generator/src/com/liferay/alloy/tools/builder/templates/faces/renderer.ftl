<#include "../base/init.ftl">
<#include "../base/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

<#if !component.isAlloyComponent()>
import ${component.getRendererBaseClass()};
</#if>
import javax.faces.application.ResourceDependency;
import javax.faces.render.FacesRenderer;

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@FacesRenderer(componentFamily = ${component.getCamelizedName()}.COMPONENT_FAMILY, rendererType = ${component.getCamelizedName()}.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
public class ${component.getCamelizedName()}${RENDERER_CLASS_SUFFIX} extends ${component.getUnqualifiedRendererBaseClass()} {
}
