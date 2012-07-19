package oe.midware.doc.word.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.XmlObj;
import oe.midware.doc.word.core.symbolanalysis.loops.LoopReplacer;
import oe.midware.doc.word.core.symbolanalysis.var.VarEvaluate;
import oe.midware.doc.word.core.symbolanalysis.vars.VarsEvaluate;
import oe.midware.doc.word.core.symbolanalysis.varx.VarxDocGenerate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class DocAnalysis {

	private static Log log = LogFactory.getLog(DocAnalysis.class);

	/**
	 * 创建新文档
	 * 
	 * @param bussObjs
	 *            业务对象数组
	 * @param xmlModelList
	 *            业务模板变量
	 * @param docSource
	 *            源文档正文
	 * @param docHead
	 *            源文档头信息
	 * @return 新的文档
	 */
	public static String[] generateDocUseBussObjs(List bussObjs,
			List xmlModelList, String docSource, String docHead) {
		Map map = analysisTemplate(xmlModelList);
		// 处理循环Loop变量
		if (map.containsKey(WordHandler.TYPE_LOOPS)) {
			List listLoop = (List) map.get(WordHandler.TYPE_LOOPS);
			docSource = LoopReplacer.replaceLoopInDoc(bussObjs, docSource,
					listLoop);
		}
		// 处理数组变量
		if (map.containsKey(WordHandler.TYPE_VARS)) {
			List listvars = (List) map.get(WordHandler.TYPE_VARS);
			docSource = VarsEvaluate.evaluate(docSource, listvars, bussObjs);
		}
		// 处理普通变量
		if (map.containsKey(WordHandler.TYPE_VAR)) {
			List listvar = (List) map.get(WordHandler.TYPE_VAR);
			docSource = VarEvaluate.evaluate(docSource, listvar, bussObjs);
		}
		// 对于多份的处理
		if (map.containsKey(WordHandler.TYPE_VARX)) {
			List listMulti = (List) map.get(WordHandler.TYPE_VARX);
			return VarxDocGenerate.generateMultiDoc(bussObjs, docSource,
					listMulti, docHead);
		}
		return new String[] { docHead + docSource };
	}

	/**
	 * 创建新文档
	 * 
	 * @param bussObj
	 * @param varsList
	 * @param sourceStr
	 * @return
	 */
	public static String generateDocUseBussObj(Map bussObj, List xmlModelList,
			String docSource, String docHead) {
		Map map = analysisTemplate(xmlModelList);

		// 构成单个变量成数组List
		List bussList = new ArrayList();
		bussList.add(bussObj);

		// 处理循环Loop变量
		if (map.containsKey(WordHandler.TYPE_LOOPS)) {
			List listLoop = (List) map.get(WordHandler.TYPE_LOOPS);
			docSource = LoopReplacer.replaceLoopInDoc(bussList, docSource,
					listLoop);
		}
		// 处理数组变量
		if (map.containsKey(WordHandler.TYPE_VARS)) {
			List listvars = (List) map.get(WordHandler.TYPE_VARS);
			docSource = VarsEvaluate.evaluate(docSource, listvars, bussList);
		}
		// 处理普通变量
		if (map.containsKey(WordHandler.TYPE_VAR)) {
			List listvar = (List) map.get(WordHandler.TYPE_VAR);
			docSource = VarEvaluate.evaluate(docSource, listvar, bussObj);
		}
		return docHead + docSource;
	}

	private static Map analysisTemplate(List xmlModelList) {
		List varVarxList = new ArrayList();
		List varVarList = new ArrayList();
		List varVarsList = new ArrayList();
		List varLoopList = new ArrayList();
		for (Iterator iter = xmlModelList.iterator(); iter.hasNext();) {
			XmlObj element = (XmlObj) iter.next();
			String type = element.getType();
			log.debug("analysis:" + type);
			if (WordHandler.TYPE_VARS.equals(type)) {
				varVarsList.add(element);
			} else if (WordHandler.TYPE_VARX.equals(type)) {
				varVarxList.add(element);
			} else if (WordHandler.TYPE_LOOPS.equals(type)) {
				varLoopList.add(element);
			} else if (WordHandler.TYPE_VAR.equals(type)) {
				varVarList.add(element);
			}
		}
		Map map = new HashMap();
		if (varVarxList.size() > 0) {
			map.put(WordHandler.TYPE_VARX, varVarxList);
		}
		if (varVarsList.size() > 0) {
			map.put(WordHandler.TYPE_VARS, varVarsList);
		}
		if (varVarList.size() > 0) {
			map.put(WordHandler.TYPE_VAR, varVarList);
		}
		if (varLoopList.size() > 0) {
			map.put(WordHandler.TYPE_LOOPS, varLoopList);
		}
		return map;
	}

}
