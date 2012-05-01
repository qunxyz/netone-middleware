package oe.mid.datatools.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class RuleEngine {

	public static String todo(String elogicExpress, String valueThis, Map map) {
		// 如果没有规则那么默认为真
		if (elogicExpress == null || elogicExpress.equals("")) {
			return "";
		}
		elogicExpress = StringUtils.replace(elogicExpress, "#value#", valueThis);

		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object valueTmp = map.get(key);
			String valueEach = "";
			if (valueTmp != null) {
				valueEach = valueTmp.toString();
			}
			elogicExpress = StringUtils.replace(elogicExpress, "#" + key + "#",
					valueEach);
			elogicExpress = StringUtils.replace(elogicExpress, "#" + key.toLowerCase() + "#",
					valueEach);
		}
		Interpreter itp = new Interpreter();

		try {

			Object revValue = itp
					.eval("import org.apache.commons.lang.StringUtils;import java.util.regex.*;import java.util.*;import java.sql.*;import oe.mid.datatools.utils.DB;"
							+ elogicExpress);
			if (revValue != null) {
				return revValue.toString();
			}
			return "";

		} catch (EvalError e) {
			throw new RuntimeException("规则语法错误:" + elogicExpress + " detail:"
					+ e.getMessage());
		}

	}

}
