package oe.midware.doc.word.core.symbolanalysis.varx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.common.XmlObj;
/**
 * 
 * @author hotchaoyi, chen.jia.xun(Robanco)
 *
 */
public class VarxDocGenerate {

	/**
	 * ��������ĵ�
	 * 
	 * @param multilvalue
	 *            ����ڶ���ĵ���ֵ��ҵ�����
	 * @param docSource
	 *            Դ�ĵ�
	 * @param varXList
	 *            ����Varx����List��XmlObj��
	 * @param aheadSource
	 *            Դ�ĵ���ͷ��Ϣ��</head>֮ǰ����Ϣ��
	 * @return
	 */
	public static String[] generateMultiDoc(List bussObjs, String docSource,
			List varxObjs, String aheadSource) {
		List dataStrList = new ArrayList();
		for (Iterator iter = bussObjs.iterator(); iter.hasNext();) {
			// ����һ��Դ�ĵ��ĸ���
			String tempSourceStr = docSource;
			Map dataObj = (Map) iter.next();
			// ѭ��varXList�б����θ�Դ�ĵ������е���ر�����ֵ//
			for (Iterator iterator = varxObjs.iterator(); iterator.hasNext();) {
				XmlObj xmlObj = (XmlObj) iterator.next();
				tempSourceStr = VarxEvaluate.evaluate(tempSourceStr, xmlObj,
						dataObj);
			}
			// ����һ�������ģ��Ѿ�����ֵ���ĵ�
			String docPre = aheadSource + tempSourceStr;
			dataStrList.add(docPre);
		}
		return (String[]) dataStrList.toArray(new String[0]);
	}
}
