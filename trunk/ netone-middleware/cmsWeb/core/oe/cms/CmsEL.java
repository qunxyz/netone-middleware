package oe.cms;

/**
 * ҳ���ϵ�EL���ʽ�Ĺؼ���
 * 
 * @author ibm
 * 
 */
public interface CmsEL {
	String _EL_FUNC = "$:";

	String _EL_FUNC_PARAM_START = "{";

	String _EL_FUNC_PARAM_END = "}";

	String _EL_FUNC_SET_PARAM = ":=";

	String _EL_FUNC_PARAM_SPLIT = "&+";

	String _EL_TEMPLATE_PARAM_START = "%{";

	String _EL_TEMPLATE_PARAM_END = "}";

	String _EL_TEMPLATE_PARAM_ARR_START = "%[";

	String _EL_TEMPLATE_PARAM_ARR_END = "]";

}
