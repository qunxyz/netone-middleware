package oe.midware.doc.word.core.symbolanalysis.loops;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.XmlObj;
import oe.midware.doc.word.core.symbolanalysis.var.VarEvaluate;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author hotchaoyi,chen.jia.xun(Robanco)
 * 
 */
public class LoopReplacer {

	/**
	 * 替换生成Loop循环信息
	 * 
	 * @param outvalue
	 *            数值
	 * @param sourceStr
	 *            源文档
	 * @param varFList
	 *            循环对象模板
	 * @return
	 */
	public static String replaceLoopInDoc(List outvalue, String sourceStr,
			List loopElement) {
		if (loopElement != null && loopElement.size() > 0) {
			Map loopTemplate = new HashMap();
			sourceStr = LoopTemplateMaker.fetchForTemplate(sourceStr,
					loopTemplate);
			for (Iterator iterator = loopElement.iterator(); iterator.hasNext();) {
				StringBuffer totalForStr = new StringBuffer();
				XmlObj xmlObj = (XmlObj) iterator.next();
				List childList = xmlObj.getChildVars();
				String idkey = xmlObj.getId();
				String loopTemplateValue = (String) loopTemplate
						.get(WordHandler.LOOP_SYMBOL_TMP + idkey
								+ WordHandler._LOOP_END_MARK);
				// 生成循环列表信息<tr>列表
				for (Iterator iter = outvalue.iterator(); iter.hasNext();) {
					Map dataObj = (Map) iter.next();
					String tempSourceStr = loopTemplateValue;
					// 取出记录进行替换
					for (Iterator iter2 = childList.iterator(); iter2.hasNext();) {
						XmlObj xmlObjchild = (XmlObj) iter2.next();
						tempSourceStr = VarEvaluate.evaluate(tempSourceStr,
								xmlObjchild, dataObj);
					}
					totalForStr.append(tempSourceStr);
				}
				sourceStr = StringUtils.replaceOnce(sourceStr,
						WordHandler.LOOP_SYMBOL_TMP + idkey
								+ WordHandler._LOOP_END_MARK, totalForStr
								.toString());
			}
		}
		return sourceStr;
	}

}
