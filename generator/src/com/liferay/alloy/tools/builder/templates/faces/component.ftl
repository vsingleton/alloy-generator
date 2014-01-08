<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
@FacesComponent(value = "${packagePath}.${component.getPackage()}.${component.getCamelizedName()}")
public class ${component.getCamelizedName()} extends Base${component.getCamelizedName()} {
}