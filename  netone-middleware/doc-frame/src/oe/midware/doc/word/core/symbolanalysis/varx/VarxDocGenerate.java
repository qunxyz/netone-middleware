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
	 * 创建多份文档
	 * 
	 * @param multilvalue
	 *            针对于多份文档的值（业务对象）
	 * @param docSource
	 *            源文档
	 * @param varXList
	 *            变量Varx对象List（XmlObj）
	 * @param aheadSource
	 *            源文档的头信息（</head>之前的信息）
	 * @return
	 */
	public static String[] generateMultiDoc(List bussObjs, String docSource,
			List varxObjs, String aheadSource) {
		List dataStrList = new ArrayList();
		for (Iterator iter = bussObjs.iterator(); iter.hasNext();) {
			// 拷贝一份源文档的副本
			String tempSourceStr = docSource;
			Map dataObj = (Map) iter.next();
			// 循环varXList列表，依次给源文档副本中的相关变量赋值//
			for (Iterator iterator = varxObjs.iterator(); iterator.hasNext();) {
				XmlObj xmlObj = (XmlObj) iterator.next();
				tempSourceStr = VarxEvaluate.evaluate(tempSourceStr, xmlObj,
						dataObj);
			}
			// 生成一份完整的，已经被赋值的文档
			String docPre = aheadSource + tempSourceStr;
			dataStrList.add(docPre);
		}
		return (String[]) dataStrList.toArray(new String[0]);
	}
}
