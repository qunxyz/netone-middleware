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
	 * 抽离文档中的字段信息
	 * 
	 * @param sourcedoc
	 * @param fileRootInfo
	 * @return
	 */
	public static List fetchColumnInfo(String templatename,
			FileRootInfo fileRootInfo) {
		List list = new ArrayList();
		try {
			// 过滤文档中多余的html标志信息
			String textConent = extraTextContent(templatename, fileRootInfo);
			log.debug(textConent);
			// 处理提取for元素
			textConent = fetchForList(textConent, list);
			// 处理多份文档的情况
			textConent = fetchVar(textConent, list, WordHandler.TYPE_VARX,
					WordHandler.VARX, WordHandler._END_MARK);
			// 处理多记录的情况
			textConent = fetchVar(textConent, list, WordHandler.TYPE_VARS,
					WordHandler.VARS, WordHandler._END_MARK);
			// 处理普通标记位
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
	 * 文档中的处理循环信息
	 * 
	 * @param textConent
	 *            文档信息
	 * @param list
	 *            抽取的变量对象
	 * @return 被抽离过循环信息的文档信息
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
			// 抽取for中相关变量
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
	 * 处理变量
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
	 * 处理变量
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
	 * 过滤html文档中多余的信息
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
