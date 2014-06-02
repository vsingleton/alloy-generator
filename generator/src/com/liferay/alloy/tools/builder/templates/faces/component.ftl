<#include "../base/init.ftl">
<#include "../base/copyright.ftl">

package ${packagePath}.${component.getUncamelizedName(BLANK)};

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.lang.StringPool;

/**
<#list component.getAuthors()?sort as author>
 * @author  ${author}
</#list>
 */
@FacesComponent(value = ${component.getCamelizedName()}.COMPONENT_TYPE)
public class ${component.getCamelizedName()} extends ${component.getCamelizedName()}${BASE_CLASS_SUFFIX} {

	// Public Constants
	public static final String COMPONENT_TYPE = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}";
	public static final String RENDERER_TYPE = "${packagePath}.${component.getUncamelizedName(BLANK)}.${component.getCamelizedName()}${RENDERER_CLASS_SUFFIX}";
	public static final String STYLE_CLASS_NAME = "${component.getUncamelizedName()}";

	public ${component.getCamelizedName()}() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		return ComponentUtil.concatAllCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
