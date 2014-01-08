<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

<#compress>

<#assign componentInterface = "${component.getInterface()!}">

</#compress>
package ${packagePath}.${component.getPackage()};

/**
<#list component.getAuthors() as author>
 * @author ${author}
</#list>
 */
public class ${component.getClassName()} extends Base${component.getClassName()}${(componentInterface?? && (componentInterface != BLANK))?string(' implements ' + componentInterface, BLANK)} {
}