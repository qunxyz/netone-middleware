package oe.cms.datasource;

import java.util.List;



/**
 * 查询元素
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface LocateElement {
	/**
	 * SI SQL元素ID的前缀, 对应 TCmsColumn表
	 */
	String _SI = "si";

	/**
	 * FI 文件元素ID的前缀,对应 TCmsFile表
	 */
	String _FI = "fi";

	/**
	 * web 页面元素
	 */
	String _WI = "wi";

	/**
	 * 全部元素列表
	 * { _SI, "SQL数据源" }, { _FI, "文件数据源" },
		{ _WI, "页面数据源" }
	 */
	String[][] _ELEMENT = { { _SI, "SQL数据源" }, { _FI, "文件数据源" }};



}
