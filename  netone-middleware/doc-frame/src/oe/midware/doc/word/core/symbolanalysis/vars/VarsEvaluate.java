package oe.midware.doc.word.core.symbolanalysis.vars;

import java.util.Iterator;
import java.util.List;

import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.XmlObj;
import oe.midware.doc.word.core.symbolanalysis.utils.ValueReader;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author hotchaoyi, chen.jia.xun(Robanco)
 * 
 */
public class VarsEvaluate {

	/**
	 * �����������ֵ
	 * 
	 * @param docSource
	 *            �ĵ�����Դ
	 * @param obj
	 *            ģ�����
	 * @param value
	 *            ֵ�б�
	 * @return ���滻�����ĵ�����Դ
	 */
	public static String evaluate(String docSource, List varsObjs, List bussObjs) {
		for (Iterator itr = varsObjs.iterator(); itr.hasNext();) {
			XmlObj obj = (XmlObj) itr.next();
			String[] valueCore = ValueReader.fetchValueByMulti(bussObjs, obj);
			String varOrial = WordHandler.VARS + obj.getId()
					+ WordHandler._END_MARK;
			for (int i = 0; i < valueCore.length; i++) {
				docSource = StringUtils.replaceOnce(docSource, varOrial,
						valueCore[i]);
			}
			// ����ĵ��л���δ���滻�ı�������ôȫ���ÿ�
			docSource = StringUtils.replace(docSource, varOrial, "");
		}
		return docSource;
	}

}
