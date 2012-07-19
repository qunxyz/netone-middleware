package oe.midware.doc.word.core.symbolanalysis.var;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.XmlObj;
import oe.midware.doc.word.core.symbolanalysis.utils.ValueReader;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class VarEvaluate {

	/**
	 * �滻��ͨ����
	 * 
	 * @param docSource
	 *            �ĵ�����Դ
	 * @param obj
	 *            ģ�����
	 * @param value
	 *            ֵ�б�
	 * @return ���滻�����ĵ�����Դ
	 */
	public static String evaluate(String docSource, List varObjs, List bussObjs) {
		for (Iterator itr = varObjs.iterator(); itr.hasNext();) {
			XmlObj obj = (XmlObj) itr.next();
			String[] valueCore = ValueReader.fetchValueByMulti(bussObjs, obj);
			String varOrial = WordHandler.VAR + obj.getId()
					+ WordHandler._END_MARK;
			StringBuffer valueCoreList = new StringBuffer();
			for (int i = 0; i < valueCore.length; i++) {
				valueCoreList.append(valueCore[i] + ",");
			}
			docSource = StringUtils.replace(docSource, varOrial, valueCoreList
					.toString());
		}
		return docSource;
	}

	/**
	 * �滻��ͨ����
	 * 
	 * @param docSource
	 *            �ĵ�����Դ
	 * @param obj
	 *            ģ�����
	 * @param value
	 *            ֵ�б�
	 * @return ���滻�����ĵ�����Դ
	 */
	public static String evaluate(String docSource, XmlObj varObj, Map bussObj) {
		String valueCore = ValueReader.fetchValueBySingle(bussObj, varObj);
		String varOrial = WordHandler.VAR + varObj.getId()
				+ WordHandler._END_MARK;
		return StringUtils.replaceOnce(docSource, varOrial, valueCore);
	}

	/**
	 * �滻��ͨ����
	 * 
	 * @param docSource
	 *            �ĵ�����Դ
	 * @param obj
	 *            ģ�����
	 * @param value
	 *            ֵ�б�
	 * @return ���滻�����ĵ�����Դ
	 */
	public static String evaluate(String docSource, List varObjs, Map bussObj) {
		for (Iterator itr = varObjs.iterator(); itr.hasNext();) {
			XmlObj obj = (XmlObj) itr.next();
			String valueCore = ValueReader.fetchValueBySingle(bussObj, obj);
			String varOrial = WordHandler.VAR + obj.getId()
					+ WordHandler._END_MARK;
			docSource = StringUtils.replace(docSource, varOrial, valueCore);
		}
		return docSource;
	}
}
