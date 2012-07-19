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
	 * �滻����Loopѭ����Ϣ
	 * 
	 * @param outvalue
	 *            ��ֵ
	 * @param sourceStr
	 *            Դ�ĵ�
	 * @param varFList
	 *            ѭ������ģ��
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
				// ����ѭ���б���Ϣ<tr>�б�
				for (Iterator iter = outvalue.iterator(); iter.hasNext();) {
					Map dataObj = (Map) iter.next();
					String tempSourceStr = loopTemplateValue;
					// ȡ����¼�����滻
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
