package oe.midware.doc.word;

import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.HtmlHandler;
import oe.frame.bus.res.doc.common.XmlHandler;
import oe.midware.doc.common.ReadIoInfo;
import oe.midware.doc.word.core.DocAnalysis;
import oe.midware.doc.word.core.DocPrepare;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 实现html模板的处理
 * 
 * @author hotchaoyi,chen.jia.xun(Robanco)
 * 
 */
public class HtmlHandlerImp implements HtmlHandler {
	private static Log _log = LogFactory.getLog(HtmlHandlerImp.class);

	private XmlHandler xmlHandler;

	public XmlHandler getXmlHandler() {
		return xmlHandler;
	}

	public void setXmlHandler(XmlHandler xmlHandler) {
		this.xmlHandler = xmlHandler;
	}

	public String[] generateMultiDocByTemplate(List bussObjs, String templateId) {
		List varsList = xmlHandler.readXml(templateId);
		String templatepath = xmlHandler.fetchFileRootInfo()
				.getDocwebtemplatepath()
				+ templateId + ".htm";

		String sourceStr = ReadIoInfo.getFileContenStr(templatepath).toString();
		if (sourceStr == null || sourceStr.equals("")) {
			_log.error("文件模板不存在！");
			return new String[0];
		}
		String[] sourceSplit = sourceStr.split("</head>");
		if (sourceSplit != null && sourceSplit.length == 2) {
			return DocAnalysis.generateDocUseBussObjs(bussObjs, varsList,
					sourceSplit[1], sourceSplit[0]);
		} else {
			return new String[0];
		}
	}

	public String generateDocByTemplate(Map bussObj, String templateId) {
		List varsList = xmlHandler.readXml(templateId);
		String templatepath = xmlHandler.fetchFileRootInfo()
				.getDocwebtemplatepath()
				+ templateId + ".htm";
		String sourceStr = ReadIoInfo.getFileContenStr(templatepath).toString();
		if (sourceStr == null || sourceStr.equals("")) {
			_log.error("文件模板不存在！");
			return "";
		}
		if (sourceStr != null) {
			String[] str = sourceStr.split("</head>");
			String sourceStrFinal = DocAnalysis.generateDocUseBussObj(bussObj,
					varsList, str[1], str[0]);
			return str[0] + sourceStrFinal;
		} else {
			return "";
		}
	}

	public void generateXmlReference(String arg0) {
		List list = DocPrepare.fetchColumnInfo(arg0, xmlHandler
				.fetchFileRootInfo());
		xmlHandler.writeXml(arg0, list);
	}

	public List fetchColumnInfo(String arg0) {
		return xmlHandler.readXml(arg0);
	}

	public String templateModelExtFile(String templateId) {
		return xmlHandler.fetchFileRootInfo().getDocwebtemplatepath()
				+ templateId + ".files/";
	}

}
