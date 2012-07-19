package oe.midware.doc.word.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.FileRootInfo;
import oe.frame.bus.res.doc.common.XmlObj;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.susebox.jtopas.Flags;
import de.susebox.jtopas.ReaderSource;
import de.susebox.jtopas.StandardTokenizer;
import de.susebox.jtopas.StandardTokenizerProperties;
import de.susebox.jtopas.Token;
import de.susebox.jtopas.Tokenizer;
import de.susebox.jtopas.TokenizerException;
import de.susebox.jtopas.TokenizerProperties;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class DocPrepare {

	private static Log log = LogFactory.getLog(DocPrepare.class);

	/**
	 * �����ĵ��е��ֶ���Ϣ
	 * 
	 * @param sourcedoc
	 * @param fileRootInfo
	 * @return
	 */
	public static List fetchColumnInfo(String templatename,
			FileRootInfo fileRootInfo) {
		List list = new ArrayList();
		try {
			// �����ĵ��ж����html��־��Ϣ
			String textConent = extraTextContent(templatename, fileRootInfo);
			log.debug(textConent);
			// ������ȡforԪ��
			textConent = fetchForList(textConent, list);
			// �������ĵ������
			textConent = fetchVar(textConent, list, WordHandler.TYPE_VARX,
					WordHandler.VARX, WordHandler._END_MARK);
			// ������¼�����
			textConent = fetchVar(textConent, list, WordHandler.TYPE_VARS,
					WordHandler.VARS, WordHandler._END_MARK);
			// ������ͨ���λ
			textConent = fetchVar(textConent, list, WordHandler.TYPE_VAR,
					WordHandler.VAR, WordHandler._END_MARK);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TokenizerException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �ĵ��еĴ���ѭ����Ϣ
	 * 
	 * @param textConent
	 *            �ĵ���Ϣ
	 * @param list
	 *            ��ȡ�ı�������
	 * @return �������ѭ����Ϣ���ĵ���Ϣ
	 */
	private static String fetchForList(String textConent, List list) {

		String tempFor = StringUtils.substringBetween(textConent,
				WordHandler.LOOPS, WordHandler._LOOP_END_MARK);
		int i = 1;
		while (StringUtils.isNotBlank(tempFor)) {
			log.debug("FetchKey:" + tempFor);
			textConent = StringUtils.replaceOnce(textConent, WordHandler.LOOPS
					+ tempFor + WordHandler._LOOP_END_MARK, "");
			log.debug("Relace:" + textConent);
			XmlObj obj = new XmlObj();
			obj.setId(WordHandler.TYPE_LOOPS + String.valueOf(i));
			obj.setType(WordHandler.TYPE_LOOPS);
			List childList = new ArrayList();
			// ��ȡfor����ر���
			fetchVarInLoop(tempFor, childList, WordHandler.TYPE_VAR,
					WordHandler.VAR, WordHandler._END_MARK);
			obj.setChildVars(childList);

			list.add(obj);
			i++;
			tempFor = StringUtils.substringBetween(textConent,
					WordHandler.LOOPS, WordHandler._LOOP_END_MARK);
		}
		return textConent;
	}

	/**
	 * �������
	 * 
	 * @param sourceStr
	 * @param list
	 * @param startTag
	 * @param endTag
	 */
	private static String fetchVar(String textConent, List list, String type,
			String startTag, String endTag) {

		String values = StringUtils.substringBetween(textConent, startTag,
				endTag);
		Map checkIfRepeat = new HashMap();
		while (StringUtils.isNotBlank(values)) {
			if (!checkIfRepeat.containsKey(values)) {
				XmlObj objTag = new XmlObj();
				objTag.setId(values);
				objTag.setType(type);
				list.add(objTag);
				checkIfRepeat.put(values, values);
			}
			String replaceSymbol = startTag + values + endTag;
			textConent = StringUtils.replaceOnce(textConent, replaceSymbol, "");
			values = StringUtils.substringBetween(textConent, startTag, endTag);
		}
		return textConent;
	}

	/**
	 * �������
	 * 
	 * @param sourceStr
	 * @param list
	 * @param startTag
	 * @param endTag
	 */
	private static String fetchVarInLoop(String textConent, List list,
			String type, String startTag, String endTag) {

		String values = StringUtils.substringBetween(textConent, startTag,
				endTag);
		while (StringUtils.isNotBlank(values)) {
			XmlObj objTag = new XmlObj();
			objTag.setId(values);
			objTag.setType(type);
			list.add(objTag);
			String replaceSymbol = startTag + values + endTag;
			textConent = StringUtils.replaceOnce(textConent, replaceSymbol, "");
			values = StringUtils.substringBetween(textConent, startTag, endTag);
		}
		return textConent;
	}

	/**
	 * ����html�ĵ��ж������Ϣ
	 * 
	 * @param sourcedoc
	 * @param fileRootInfo
	 * @return
	 * @throws FileNotFoundException
	 * @throws TokenizerException
	 */
	private static String extraTextContent(String sourcedoc,
			FileRootInfo fileRootInfo) throws FileNotFoundException,
			TokenizerException {
		String filePathName = fileRootInfo.getDocwebtemplatepath() + sourcedoc
				+ ".htm";
		FileInputStream stream = new FileInputStream(filePathName);
		InputStreamReader reader = new InputStreamReader(stream);
		TokenizerProperties props = new StandardTokenizerProperties();
		Tokenizer tokenizer = new StandardTokenizer();
		Token token;
		props.setParseFlags(Flags.F_NO_CASE | Flags.F_TOKEN_POS_ONLY
				| Flags.F_RETURN_WHITESPACES);
		props.setSeparators(null);

		props.addBlockComment("<", ">");
		props.addBlockComment("<HEAD>", "</HEAD>");
		props.addBlockComment("<!--", "-->");
		props.addSpecialSequence("&lt;", "<");
		props.addSpecialSequence("&gt;", ">");
		props.addSpecialSequence("&nbsp;", "");

		tokenizer.setTokenizerProperties(props);
		tokenizer.setSource(new ReaderSource(reader));
		String str = "";
		while (tokenizer.hasMoreToken()) {
			token = tokenizer.nextToken();
			switch (token.getType()) {
			case Token.NORMAL:
				str += tokenizer.currentImage();
				break;
			case Token.SPECIAL_SEQUENCE:
				str += (String) token.getCompanion();
				break;
			}
		}
		return str;
	}

}
