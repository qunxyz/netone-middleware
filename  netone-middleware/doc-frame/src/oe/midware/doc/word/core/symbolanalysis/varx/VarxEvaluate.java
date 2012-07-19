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
	 * ��Varx������ֵ
	 * 
	 * @param sourceStr
	 *            Դ�ĵ�
	 * @param obj
	 *            VarX����
	 * @param valueObj
	 *            ҵ�����
	 * @return
	 */
	public static String evaluate(String sourceStr, XmlObj obj, Map valueObj) {
		String valueCore = ValueReader.fetchValueBySingle(valueObj, obj);
		String varOrial = WordHandler.VARX + obj.getId()
				+ WordHandler._END_MARK;
		return StringUtils.replaceOnce(sourceStr, varOrial, valueCore);
	}
}
