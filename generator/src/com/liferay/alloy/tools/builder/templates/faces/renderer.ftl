<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import ${packagePath}.${component.getPackage()}.base.Base${component.getCamelizedName()};

<#compress>
<#assign componentInterface = "${component.getInterface()!}">
</#compress>

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
@FacesRenderer(
	componentFamily = "javax.faces.Panel", rendererType = "${packagePath}.${component.getPackage()}.${component.getCamelizedName()}"
)
public class ${component.getCamelizedName()}Renderer extends Base${component.getCamelizedName()}Renderer${(componentInterface?? && (componentInterface != BLANK))?string(' implements ' + componentInterface, BLANK)} {
}