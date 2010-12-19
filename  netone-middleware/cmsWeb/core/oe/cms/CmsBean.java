package oe.cms;

public interface CmsBean {
	// ////////////公共接口部分////////////////
	/**
	 * 文件处理器
	 */
	String _FILE_HANDLER = "fileHandler";

	/**
	 * XML处理器
	 */
	String _XML_HANDLER = "xmlHandler";

	/**
	 * XML解析器
	 */
	String _XML_PARSER = "xmlParser";

	// /////////////////////////////////////////////////

	// ////////////数据元读取驱动器////////////////////////
	/**
	 * SQL数据元读取器
	 */
	String _SQL_DATA_ACCESS = "sqlDataAccess";

	String SQL_DATA_ACCESS_CORE = "sqlDataAccessCore";

	/**
	 * 文件数据元读取器
	 */
	String _FILE_DATA_ACCESS = "fileDataAccess";

	// //////////////////////////////////////////////////
	/**
	 * 数据元的查值器
	 */
	String _LOACTE_ELEMENT = "locateElement";

	// ////////////SQL数据元配置管理相关////////////
	/**
	 * SQL解析器，用于获得字段信息
	 */
	String _SQL_PARSER = "sqlParser";

	/**
	 * Html流处理器
	 */
	String _HTML_STREAM_HANDLER = "htmlStreamHandler";

	/**
	 * 信息元解析器
	 */
	String _INFO_CELL_PARSER = "infoCellParser";

	/**
	 * 规则解析器
	 */
	String _RULE_PARSER = "ruleParser";

	/**
	 * 规则解析器
	 */
	String _VAR_HANDLER = "varHandler";

	/**
	 * 规则解析器
	 */
	String _XHTML_PARSER = "xHtmlParser";

	/**
	 * WEB信息解析器
	 */
	String _WEB_INFO_PARSER = "wiParser";

	String _GROUP_INFO = "groupinfo";

	String _INFO_BODY_ACCESS = "infobodyAccess";

	String _WI_DIV_ACCESS = "widivaccess";

	String _INTIME_TRIGGER = "intimeTrigger";
}
