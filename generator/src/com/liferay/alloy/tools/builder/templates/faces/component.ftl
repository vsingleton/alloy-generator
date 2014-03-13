<#include "../common/init.ftl">
<#include "../common/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import javax.faces.component.FacesComponent;

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@FacesComponent(value = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}")
public class ${component.getCamelizedName()} extends ${component.getCamelizedName()}Base {

	// Public Constants
	public static final String COMPONENT_FAMILY = "${packagePath}.${component.getUncamelizedName(BLANK)}";
	public static final String COMPONENT_TYPE = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}";

	// Private Constants
	private static final String RENDERER_TYPE = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}Renderer";

	public ${component.getCamelizedName()}() {

		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
}