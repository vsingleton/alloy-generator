<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import javax.faces.component.FacesComponent;

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
@FacesComponent(value = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}")
public class ${component.getCamelizedName()} extends ${component.getCamelizedName()}Base {
}