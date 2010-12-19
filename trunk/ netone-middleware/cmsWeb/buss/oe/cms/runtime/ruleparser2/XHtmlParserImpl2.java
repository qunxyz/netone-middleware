package oe.cms.runtime.ruleparser2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.cms.runtime.ruleparser.SyntaxInfo2;
import oe.cms.runtime.ruleparser.XHtmlParser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * 脚本解析处理
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class XHtmlParserImpl2 implements XHtmlParser, SyntaxInfo2 {
	Log log = LogFactory.getLog(XHtmlParserImpl2.class);

	public String xHtmlParser(String context, HttpServletRequest request) {
		if (context == null || context.trim().equals("")) {
			return "";
		}
		// 获得全部的脚本
		List list = fetchAllScript(context);
		if (list == null || list.size() == 0) {
			return context;
		}
		// 脚本
		String expression = reBuildeScript(list);
		log.debug("scriptfull:" + expression);

		Interpreter itp = new Interpreter();
		try {
			itp.set("request", request);
			Object obj = itp.eval(expression);
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map map = (Map) itr.next();
				String returnVar = (String) map.get(_SCRPIT_OUTPUT);
				String returnValueInfo = "";
				if (!returnVar.equals("")) {
					Object returnValue = itp.get(returnVar);
					returnValueInfo = returnValue == null ? "" : returnValue
							.toString();

				} else if (obj != null) {
					returnValueInfo = obj.toString();
				}
				context = StringUtils.replace(context, (String) map
						.get(_SCRPIT_REAL_EXP), returnValueInfo);
			}
			return context;

		} catch (EvalError e) {
			e.printStackTrace();
			throw new RuntimeException("<br>##J++语法错误:" + expression + " 具体细节:"
					+ e.getMessage());
		}
	}

	/**
	 * 解析脚本中的java脚本
	 * 
	 * @param context
	 * @return
	 */
	private List fetchAllScript(String context) {
		if (context == null || context.trim().length() == 0) {
			return new ArrayList();
		}
		int indexScriptMark = StringUtils.indexOf(context, XSCRIPT_END);
		List script = new ArrayList();
		while (indexScriptMark > 0) {
			String scriptReal = StringUtils.substringBetween(context,
					XSCRIPT_STA, XSCRIPT_END);
			if (scriptReal == null) {
				throw new RuntimeException("J++语法错误！没有可匹配的<%jpp 和 %>");
			}
			log.debug("preScript:" + scriptReal);
			// 预处理脚本
			Map scriptinfo = preScript(scriptReal);
			script.add(scriptinfo);
			// 寻找下一个代码片断
			context = StringUtils.substring(context, indexScriptMark + 2);
			indexScriptMark = StringUtils.indexOf(context, XSCRIPT_END);
		}
		return script;
	}

	/**
	 * 处理代码脚本
	 * 
	 * @param expression
	 * @return
	 */
	private Map preScript(String expression) {
		if (expression == null || expression.trim().length() == 0) {
			return new HashMap();
		}
		String expressionNext = expression.trim();
		int haveReturnValue = StringUtils.indexOf(expressionNext,
				XSCRIPT_OUT_STA);
		String output = "";
		String realBody = expression;
		if (haveReturnValue == 0) {
			int nextBlank = StringUtils
					.indexOf(expressionNext, XSCRIPT_OUT_END);
			String nextBlankTmp = StringUtils.substring(expressionNext, 1);
			int checkBlankOk = StringUtils.indexOf(nextBlankTmp,
					XSCRIPT_OUT_STA);
			if (checkBlankOk != -1 && checkBlankOk < nextBlank) {
				// 返回类型中存在括号嵌套
				throw new RuntimeException("语法错误，类型中存在括号嵌");
			}
			output = StringUtils.substring(expressionNext, haveReturnValue + 1,
					nextBlank);
			realBody = StringUtils.substring(expressionNext, nextBlank + 1);
		}
		log.debug("preScript_return:" + output);
		log.debug("preScript_body:" + realBody);
		Map map = new HashMap();
		map.put(_SCRPIT_REAL_EXP, XSCRIPT_STA + expression + XSCRIPT_END);
		map.put(_SCRPIT_USE_EXP, realBody);
		map.put(_SCRPIT_OUTPUT, output);
		return map;
	}

	/**
	 * 将所有的脚本重建出一个完整的Java程序
	 * 
	 * @param list
	 * @return
	 */
	private String reBuildeScript(List list) {
		StringBuffer buf = new StringBuffer();
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map map = (Map) itr.next();
			buf.append((String) map.get(_SCRPIT_USE_EXP));
		}

		return _DEFAULT_IMPORT + _CORE_OBJECT + buf.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
