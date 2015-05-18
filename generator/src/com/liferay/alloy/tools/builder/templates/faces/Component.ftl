<#include "../base/init.ftl">
<#include "../base/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName()};

import javax.faces.component.FacesComponent;

/**
<#list component.getAuthors()?sort as author>
 * @author	${author}
</#list>
 */
@FacesComponent(value = ${component.getCamelizedName()}.COMPONENT_TYPE)
public class ${component.getCamelizedName()} extends ${component.getCamelizedName()}Base {
	// Initial Generation
}
