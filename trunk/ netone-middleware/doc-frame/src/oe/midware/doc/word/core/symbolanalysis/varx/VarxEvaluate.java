package oe.midware.doc.word.core.symbolanalysis.varx;

import java.util.Map;

import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.XmlObj;
import oe.midware.doc.word.core.symbolanalysis.utils.ValueReader;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author hotchaoyi, chen.jia.xun(Robanco)
 * 
 */
public class VarxEvaluate {
	/**
	 * 给Varx变量赋值
	 * 
	 * @param sourceStr
	 *            源文档
	 * @param obj
	 *            VarX对象
	 * @param valueObj
	 *            业务对象
	 * @return
	 */
	public static String evaluate(String sourceStr, XmlObj obj, Map valueObj) {
		String valueCore = ValueReader.fetchValueBySingle(valueObj, obj);
		String varOrial = WordHandler.VARX + obj.getId()
				+ WordHandler._END_MARK;
		return StringUtils.replaceOnce(sourceStr, varOrial, valueCore);
	}
}
