package oe.cms.runtime.ruleparser;

import javax.servlet.http.HttpServletRequest;

public interface XHtmlParser {


	/**
	 * 解析XHtml脚本
	 * 
	 * 算法说明： 1:检查脚本语法是否正确，匹配符号%是否一致 3:将
	 * 
	 * @param context
	 * @return
	 */
	String xHtmlParser(String context,HttpServletRequest request);

}
