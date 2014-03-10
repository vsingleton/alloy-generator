<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

<#if !component.isRendererBaseClassRequired()>
import ${component.getRendererParentClass()};
</#if>
import javax.faces.render.FacesRenderer;

/**
<#list component.getAuthors() as author>
 * @author  ${author}
</#list>
 */
@FacesRenderer(
	componentFamily = ${component.getCamelizedName()}.COMPONENT_FAMILY,
	rendererType = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}Renderer"
)
public class ${component.getCamelizedName()}Renderer extends ${component.getUnqualifiedRendererParentClass()} {
}