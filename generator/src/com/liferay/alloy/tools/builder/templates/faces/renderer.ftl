<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import javax.faces.component.UIPanel;
import javax.faces.render.FacesRenderer;

<#compress>
<#assign componentInterface = "${component.getInterface()!}">
</#compress>

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
@FacesRenderer(
	componentFamily = ${component.getUnqualifiedParentClass()}.COMPONENT_FAMILY, rendererType = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}Renderer"
)
public class ${component.getCamelizedName()}Renderer extends ${component.getCamelizedName()}RendererBase${(componentInterface?? && (componentInterface != BLANK))?string(' implements ' + componentInterface, BLANK)} {
}