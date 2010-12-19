package oe.cms.runtime.ruleparser;

public interface SyntaxInfo2 {
	String XSCRIPT_STA = "<%jpp:";

	String XSCRIPT_STA_RETURN = "<%jpp:(";

	String INCLUDE = "$include:";

	String XSCRIPT_END = "%>";

	String XSCRIPT_OUT_STA = "(";

	String XSCRIPT_OUT_END = ")";

	/**
	 * 脚本被分类时的相关常量
	 */
	String _SCRPIT_REAL_EXP = "script";

	/**
	 * 脚本被分类时的相关常量
	 */
	String _SCRPIT_USE_EXP = "scriptu";

	/**
	 * 返回
	 */
	String _SCRPIT_OUTPUT = "returnv";

	/**
	 * 默认的导入的包
	 */
	String _DEFAULT_IMPORT = "import java.util.*;"
			+ "import oe.cms.CmsEntry;"
			+ "import oe.cms.xhtml2.*;";

	/**
	 * 内部对象 <br>
	 * 1: di:数据源包括sql数据源,Html数据元的处理方法(爬虫程序) <br>
	 * 2: vi:包括所有的基础展示功能(主要是原先的html对象去掉几个程序)<br>
	 * 3: ui:包含一些基础的处理,比如数组,指标一致……<br>
	 * 4: ei:包含一些扩展功能,如 多级下拉展示,历史记录显示,……
	 */
	String _CORE_OBJECT = "AnalysisInterface ai = (AnalysisInterface)CmsEntry.fetchBean(\"ai\");"
			+ "DataInterface di = (DataInterface)CmsEntry.fetchBean(\"di\");"
			+ "PortaletInterface pi = (PortaletInterface)CmsEntry.fetchBean(\"pi\");"
			+ "UtilInterface ui = (UtilInterface)CmsEntry.fetchBean(\"ui\");"
			+ "BusinessInterface bi = (BusinessInterface)CmsEntry.fetchBean(\"bi\");"
			+ "ViewInterface vi = (ViewInterface)CmsEntry.fetchBean(\"vi\");";
}
