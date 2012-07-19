package oe.midware.doc.word.core.symbolanalysis.loops;

import java.util.Map;

import oe.frame.bus.res.doc.WordHandler;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author hotchaoyi,chen.jia.xun(Robanco)
 * 
 */
public class LoopTemplateMaker {
	private static Log log = LogFactory.getLog(LoopTemplateMaker.class);

	/**
	 * 抽离Loop循环的整个
	 * <tr></tr>
	 * 信息作为模板
	 * 
	 * @param sourceStr
	 *            源文档
	 * @param templateBuffer
	 *            抽离出的Loop模板
	 * @return 被修改过的源文档
	 */
	public static String fetchForTemplate(String docSource, Map templateBuffer) {
		// 为了防止意外出现死循环,每次要切断源文件(把之前查询过的部分强行去掉,而不是依赖替换(担心有时没有替换成功导致死循环))
		String thisDocSource = docSource;
		int keyIndex = 0;// 用于构建循环变量
		int checkLoop = StringUtils.indexOf(thisDocSource, WordHandler.LOOPS);
		while (checkLoop != -1) {
			keyIndex++;
			String keyLoop = WordHandler.LOOP_SYMBOL_TMP
					+ WordHandler.TYPE_LOOPS + keyIndex
					+ WordHandler._LOOP_END_MARK;
			// 抽离出文档中，从开始位置离Loop标志最近的<tr>到Loop标志之间字符信息
			String currentLoopBeforeInfos = parseTrBeforeInfo(thisDocSource);
			log.debug("loopBefore>>>>>>");
			log.debug(currentLoopBeforeInfos);
			String currentLoopFullInfo = null;
			boolean haveTable = true;
			if (currentLoopBeforeInfos == null) {
				haveTable = false;
				currentLoopBeforeInfos = StringUtils.substringBetween(
						thisDocSource, WordHandler.LOOPS,
						WordHandler._LOOP_END_MARK);
				String loopEndInfo = parseLoopEndInfo(thisDocSource);
				// 完整的被抽离出的Loop信息
				currentLoopFullInfo = WordHandler.LOOPS + loopEndInfo
						+ WordHandler._LOOP_END_MARK;
				log.debug("loopFull>>>>>>");
				log.debug(currentLoopFullInfo);
			} else {
				// 抽离出文档中，从第一个找到的Loop标志位置到最近的一个</tr>标志为止的全部字符串
				String currentLoopAfterInfo = parseTrAfterInfo(thisDocSource);
				log.debug("loopAfter>>>>>>");
				log.debug(currentLoopAfterInfo);
				// 完整的被抽离出的Loop信息
				currentLoopFullInfo = currentLoopBeforeInfos
						+ WordHandler.LOOPS + currentLoopAfterInfo + "</tr>";
				log.debug("loopFull>>>>>>");
				log.debug(currentLoopFullInfo);
			}

			// 把文档中的Loop循环信息，替换成Key
			docSource = StringUtils.replaceOnce(docSource, currentLoopFullInfo,
					keyLoop);
			// 把模板信息中的Loop开始标志去掉
			currentLoopFullInfo = StringUtils.replaceOnce(currentLoopFullInfo,
					WordHandler.LOOPS, "");
			// 把模板信息中的Loop结束标志去掉
			currentLoopFullInfo = StringUtils.replaceOnce(currentLoopFullInfo,
					WordHandler._LOOP_END_MARK, "");
			log.debug("LoopPre:>>>" + keyLoop);
			log.debug(currentLoopFullInfo);
			if (!haveTable) {
				// 如果循环变量不是在表格中，那么需要增加<br>符号，来换行，因为循环要求纵向增加
				templateBuffer.put(keyLoop, currentLoopFullInfo + "<br>");
			} else {
				templateBuffer.put(keyLoop, currentLoopFullInfo);
			}

			thisDocSource = thisDocSource.substring(checkLoop
					+ WordHandler.LOOPS_LEN);

			checkLoop = StringUtils.indexOf(thisDocSource, WordHandler.LOOPS);
		}
		return docSource;
	}

	/**
	 * 抽离出文档中，从开始位置离Loop标志最近的
	 * <tr>到Loop标志之间字符信息
	 * 
	 * @param docSource
	 * @return
	 */
	private static String parseTrBeforeInfo(String docSource) {
		String currentToFirstLoopBeforeTmp = StringUtils.substringBefore(
				docSource, WordHandler.LOOPS);
		// 获得抽离出的文档的长度
		int lastIndexRecordlength = currentToFirstLoopBeforeTmp.length();
		// 获得离Loop标志最近的'<tr'位置
		int lastIndexRecordFrom = StringUtils.lastIndexOf(
				currentToFirstLoopBeforeTmp, "<tr");
		if (lastIndexRecordFrom == -1) {
			return null;// 说明循环不在表格中
		}
		// 精确抽离出整段Loop模板信息（从<tr>到Loop开始位置）
		String trToLoop = StringUtils.substring(currentToFirstLoopBeforeTmp,
				lastIndexRecordFrom, lastIndexRecordlength);
		// 检查Loop变量是否包含在tr中
		if (StringUtils.indexOf(trToLoop, "</tr>") != -1) {
			return null;// 如果tr到Loop之间存在</tr>这将意味着该Loop变量不是在Table中
		} else {
			return trToLoop;
		}
	}

	/**
	 * 抽离出文档中，从第一个找到的Loop标志位置到最近的一个</tr>
	 * 标志为止的全部字符串
	 * 
	 * @param docSource
	 * @return
	 */
	private static String parseTrAfterInfo(String docSource) {

		String currentToFirstLoopAfterTmp = StringUtils.substringAfter(
				docSource, WordHandler.LOOPS);
		if (currentToFirstLoopAfterTmp == null
				|| currentToFirstLoopAfterTmp.equals("")) {
			log.warn("文档中模板定义不规范，取少Loops变量后续信息");
			return null;
		}
		String currentToFirstLoopAfter = StringUtils.substringBefore(
				currentToFirstLoopAfterTmp, "</tr>");
		if (currentToFirstLoopAfter == null
				|| currentToFirstLoopAfter.equals("")) {
			log.warn("文档中模板定义不规范，缺少</tr>匹配");
			return null;
		}
		if (StringUtils.indexOf(currentToFirstLoopAfter, WordHandler.LOOPS) != -1) {
			log.error("Loop不允许嵌套定义");
			throw new RuntimeException("Loop不允许嵌套定义");
		}
		if (StringUtils.indexOf(currentToFirstLoopAfter, "<tr>") != -1) {
			log.warn("文档中模板定义不规范，存在<tr>信息嵌套");
			return null;
		}

		return currentToFirstLoopAfter;
	}

	/**
	 * 抽离出文档中，从第一个找到的Loop标志位置到最近的一个</tr>
	 * 标志为止的全部字符串
	 * 
	 * @param docSource
	 * @return
	 */
	private static String parseLoopEndInfo(String docSource) {

		String currentToFirstLoopAfterTmp = StringUtils.substringAfter(
				docSource, WordHandler.LOOPS);
		if (currentToFirstLoopAfterTmp == null
				|| currentToFirstLoopAfterTmp.equals("")) {
			log.error("Loop缺少结束符号");
			throw new RuntimeException("Loop缺少结束符号");
		}
		String currentToFirstLoopAfter = StringUtils.substringBefore(
				currentToFirstLoopAfterTmp, WordHandler._LOOP_END_MARK);
		if (currentToFirstLoopAfter == null
				|| currentToFirstLoopAfter.equals("")) {
			log.warn("无效循环缺少循环变量");
			return null;
		}
		if (StringUtils.indexOf(currentToFirstLoopAfter, WordHandler.LOOPS) != -1) {
			log.error("Loop不允许嵌套定义");
			throw new RuntimeException("Loop不允许嵌套定义");
		}
		return currentToFirstLoopAfter;
	}

}
