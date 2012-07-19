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
	 * 给数组变量赋值
	 * 
	 * @param docSource
	 *            文档数据源
	 * @param obj
	 *            模板对象
	 * @param value
	 *            值列表
	 * @return 被替换过的文档数据源
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
			// 如果文档中还有未被替换的变量，那么全部置空
			docSource = StringUtils.replace(docSource, varOrial, "");
		}
		return docSource;
	}

}
