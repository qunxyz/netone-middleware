package oe.midware.workflow.engine.rule2;

import org.apache.commons.lang.StringUtils;

import bsh.EvalError;
import bsh.Interpreter;

import oe.frame.bus.workflow.rule.RuleEngine;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class RuleEngine2Impl implements RuleEngine {

	private String scripthead;

	public boolean rule(String elogicExpress, String runtimeid, String workcode) {

		// 如果没有规则那么默认为真
		if (elogicExpress == null || elogicExpress.equals("")) {
			return true;
		}
		
		elogicExpress=StringUtils.replace(elogicExpress, "‘", "'");

		Interpreter itp = new Interpreter();

		try {
			itp.set("runtimeid", runtimeid);
			itp.set("workcode", workcode);
			Object revValue = itp.eval(scripthead + elogicExpress);
			if (Boolean.class.isInstance(revValue)) {
				Boolean valueObj = (Boolean) revValue;
				return valueObj.booleanValue();
			} else {
				return true;
			}

		} catch (EvalError e) {
			System.err.print("规则语法错误:" + elogicExpress + " detail:"
					+ e.getMessage());
			throw new RuntimeException("规则语法错误:" + elogicExpress + " detail:"
					+ e.getMessage());
		}

	}

	public String todo(String elogicExpress, String runtimeid, String workcode) {
		// 如果没有规则那么默认为真
		if (elogicExpress == null || elogicExpress.equals("")) {
			return "";
		}
		elogicExpress=StringUtils.replace(elogicExpress, "‘", "'");
		Interpreter itp = new Interpreter();

		try {
			itp.set("runtimeid", runtimeid);
			itp.set("workcode", workcode);
			Object revValue = itp.eval(scripthead + elogicExpress);
			if (revValue != null) {
				return revValue.toString();
			}
			return "";

		} catch (EvalError e) {
			throw new RuntimeException("规则语法错误:" + elogicExpress + " detail:"
					+ e.getMessage());
		}
	}
	
	
	public Object todo(String elogicExpress) {
		// 如果没有规则那么默认为真
		if (elogicExpress == null || elogicExpress.equals("")) {
			return null;
		}
		elogicExpress=StringUtils.replace(elogicExpress, "‘", "'");
		Interpreter itp = new Interpreter();

		try {
			itp.set("runtimeid", "");
			itp.set("workcode", "");
			Object revValue = itp.eval(scripthead + elogicExpress);
			if (revValue != null) {
				return revValue;
			}
			return null;

		} catch (EvalError e) {
			throw new RuntimeException("规则语法错误:" + elogicExpress + " detail:"
					+ e.getMessage());
		}
	}

	public String getScripthead() {
		return scripthead;
	}

	public void setScripthead(String scripthead) {
		this.scripthead = scripthead;
	}

	@Override
	public Object todo(String elogicExpress, Object[] param) {
		// 如果没有规则那么默认为真
		if (elogicExpress == null || elogicExpress.equals("")) {
			return null;
		}
		elogicExpress=StringUtils.replace(elogicExpress, "‘", "'");
		Interpreter itp = new Interpreter();

		try {
			itp.set("_param", param);
			itp.set("runtimeid", "");
			itp.set("workcode", "");
			Object revValue = itp.eval(scripthead + elogicExpress);
			if (revValue != null) {
				return revValue;
			}
			return null;

		} catch (EvalError e) {
			throw new RuntimeException("规则语法错误:" + elogicExpress + " detail:"
					+ e.getMessage());
		}
	}

}
