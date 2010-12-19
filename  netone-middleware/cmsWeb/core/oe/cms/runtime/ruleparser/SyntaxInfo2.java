package oe.cms.runtime.ruleparser;

public interface SyntaxInfo2 {
	String XSCRIPT_STA = "<%jpp:";

	String XSCRIPT_STA_RETURN = "<%jpp:(";

	String INCLUDE = "$include:";

	String XSCRIPT_END = "%>";

	String XSCRIPT_OUT_STA = "(";

	String XSCRIPT_OUT_END = ")";

	/**
	 * �ű�������ʱ����س���
	 */
	String _SCRPIT_REAL_EXP = "script";

	/**
	 * �ű�������ʱ����س���
	 */
	String _SCRPIT_USE_EXP = "scriptu";

	/**
	 * ����
	 */
	String _SCRPIT_OUTPUT = "returnv";

	/**
	 * Ĭ�ϵĵ���İ�
	 */
	String _DEFAULT_IMPORT = "import java.util.*;"
			+ "import oe.cms.CmsEntry;"
			+ "import oe.cms.xhtml2.*;";

	/**
	 * �ڲ����� <br>
	 * 1: di:����Դ����sql����Դ,Html����Ԫ�Ĵ�����(�������) <br>
	 * 2: vi:�������еĻ���չʾ����(��Ҫ��ԭ�ȵ�html����ȥ����������)<br>
	 * 3: ui:����һЩ�����Ĵ���,��������,ָ��һ�¡���<br>
	 * 4: ei:����һЩ��չ����,�� �༶����չʾ,��ʷ��¼��ʾ,����
	 */
	String _CORE_OBJECT = "AnalysisInterface ai = (AnalysisInterface)CmsEntry.fetchBean(\"ai\");"
			+ "DataInterface di = (DataInterface)CmsEntry.fetchBean(\"di\");"
			+ "PortaletInterface pi = (PortaletInterface)CmsEntry.fetchBean(\"pi\");"
			+ "UtilInterface ui = (UtilInterface)CmsEntry.fetchBean(\"ui\");"
			+ "BusinessInterface bi = (BusinessInterface)CmsEntry.fetchBean(\"bi\");"
			+ "ViewInterface vi = (ViewInterface)CmsEntry.fetchBean(\"vi\");";
}
