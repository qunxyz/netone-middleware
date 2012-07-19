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
	 * ����Loopѭ��������
	 * <tr></tr>
	 * ��Ϣ��Ϊģ��
	 * 
	 * @param sourceStr
	 *            Դ�ĵ�
	 * @param templateBuffer
	 *            �������Loopģ��
	 * @return ���޸Ĺ���Դ�ĵ�
	 */
	public static String fetchForTemplate(String docSource, Map templateBuffer) {
		// Ϊ�˷�ֹ���������ѭ��,ÿ��Ҫ�ж�Դ�ļ�(��֮ǰ��ѯ���Ĳ���ǿ��ȥ��,�����������滻(������ʱû���滻�ɹ�������ѭ��))
		String thisDocSource = docSource;
		int keyIndex = 0;// ���ڹ���ѭ������
		int checkLoop = StringUtils.indexOf(thisDocSource, WordHandler.LOOPS);
		while (checkLoop != -1) {
			keyIndex++;
			String keyLoop = WordHandler.LOOP_SYMBOL_TMP
					+ WordHandler.TYPE_LOOPS + keyIndex
					+ WordHandler._LOOP_END_MARK;
			// ������ĵ��У��ӿ�ʼλ����Loop��־�����<tr>��Loop��־֮���ַ���Ϣ
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
				// �����ı��������Loop��Ϣ
				currentLoopFullInfo = WordHandler.LOOPS + loopEndInfo
						+ WordHandler._LOOP_END_MARK;
				log.debug("loopFull>>>>>>");
				log.debug(currentLoopFullInfo);
			} else {
				// ������ĵ��У��ӵ�һ���ҵ���Loop��־λ�õ������һ��</tr>��־Ϊֹ��ȫ���ַ���
				String currentLoopAfterInfo = parseTrAfterInfo(thisDocSource);
				log.debug("loopAfter>>>>>>");
				log.debug(currentLoopAfterInfo);
				// �����ı��������Loop��Ϣ
				currentLoopFullInfo = currentLoopBeforeInfos
						+ WordHandler.LOOPS + currentLoopAfterInfo + "</tr>";
				log.debug("loopFull>>>>>>");
				log.debug(currentLoopFullInfo);
			}

			// ���ĵ��е�Loopѭ����Ϣ���滻��Key
			docSource = StringUtils.replaceOnce(docSource, currentLoopFullInfo,
					keyLoop);
			// ��ģ����Ϣ�е�Loop��ʼ��־ȥ��
			currentLoopFullInfo = StringUtils.replaceOnce(currentLoopFullInfo,
					WordHandler.LOOPS, "");
			// ��ģ����Ϣ�е�Loop������־ȥ��
			currentLoopFullInfo = StringUtils.replaceOnce(currentLoopFullInfo,
					WordHandler._LOOP_END_MARK, "");
			log.debug("LoopPre:>>>" + keyLoop);
			log.debug(currentLoopFullInfo);
			if (!haveTable) {
				// ���ѭ�����������ڱ���У���ô��Ҫ����<br>���ţ������У���Ϊѭ��Ҫ����������
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
	 * ������ĵ��У��ӿ�ʼλ����Loop��־�����
	 * <tr>��Loop��־֮���ַ���Ϣ
	 * 
	 * @param docSource
	 * @return
	 */
	private static String parseTrBeforeInfo(String docSource) {
		String currentToFirstLoopBeforeTmp = StringUtils.substringBefore(
				docSource, WordHandler.LOOPS);
		// ��ó�������ĵ��ĳ���
		int lastIndexRecordlength = currentToFirstLoopBeforeTmp.length();
		// �����Loop��־�����'<tr'λ��
		int lastIndexRecordFrom = StringUtils.lastIndexOf(
				currentToFirstLoopBeforeTmp, "<tr");
		if (lastIndexRecordFrom == -1) {
			return null;// ˵��ѭ�����ڱ����
		}
		// ��ȷ���������Loopģ����Ϣ����<tr>��Loop��ʼλ�ã�
		String trToLoop = StringUtils.substring(currentToFirstLoopBeforeTmp,
				lastIndexRecordFrom, lastIndexRecordlength);
		// ���Loop�����Ƿ������tr��
		if (StringUtils.indexOf(trToLoop, "</tr>") != -1) {
			return null;// ���tr��Loop֮�����</tr>�⽫��ζ�Ÿ�Loop����������Table��
		} else {
			return trToLoop;
		}
	}

	/**
	 * ������ĵ��У��ӵ�һ���ҵ���Loop��־λ�õ������һ��</tr>
	 * ��־Ϊֹ��ȫ���ַ���
	 * 
	 * @param docSource
	 * @return
	 */
	private static String parseTrAfterInfo(String docSource) {

		String currentToFirstLoopAfterTmp = StringUtils.substringAfter(
				docSource, WordHandler.LOOPS);
		if (currentToFirstLoopAfterTmp == null
				|| currentToFirstLoopAfterTmp.equals("")) {
			log.warn("�ĵ���ģ�嶨�岻�淶��ȡ��Loops����������Ϣ");
			return null;
		}
		String currentToFirstLoopAfter = StringUtils.substringBefore(
				currentToFirstLoopAfterTmp, "</tr>");
		if (currentToFirstLoopAfter == null
				|| currentToFirstLoopAfter.equals("")) {
			log.warn("�ĵ���ģ�嶨�岻�淶��ȱ��</tr>ƥ��");
			return null;
		}
		if (StringUtils.indexOf(currentToFirstLoopAfter, WordHandler.LOOPS) != -1) {
			log.error("Loop������Ƕ�׶���");
			throw new RuntimeException("Loop������Ƕ�׶���");
		}
		if (StringUtils.indexOf(currentToFirstLoopAfter, "<tr>") != -1) {
			log.warn("�ĵ���ģ�嶨�岻�淶������<tr>��ϢǶ��");
			return null;
		}

		return currentToFirstLoopAfter;
	}

	/**
	 * ������ĵ��У��ӵ�һ���ҵ���Loop��־λ�õ������һ��</tr>
	 * ��־Ϊֹ��ȫ���ַ���
	 * 
	 * @param docSource
	 * @return
	 */
	private static String parseLoopEndInfo(String docSource) {

		String currentToFirstLoopAfterTmp = StringUtils.substringAfter(
				docSource, WordHandler.LOOPS);
		if (currentToFirstLoopAfterTmp == null
				|| currentToFirstLoopAfterTmp.equals("")) {
			log.error("Loopȱ�ٽ�������");
			throw new RuntimeException("Loopȱ�ٽ�������");
		}
		String currentToFirstLoopAfter = StringUtils.substringBefore(
				currentToFirstLoopAfterTmp, WordHandler._LOOP_END_MARK);
		if (currentToFirstLoopAfter == null
				|| currentToFirstLoopAfter.equals("")) {
			log.warn("��Чѭ��ȱ��ѭ������");
			return null;
		}
		if (StringUtils.indexOf(currentToFirstLoopAfter, WordHandler.LOOPS) != -1) {
			log.error("Loop������Ƕ�׶���");
			throw new RuntimeException("Loop������Ƕ�׶���");
		}
		return currentToFirstLoopAfter;
	}

}
