package oe.midware.doc.word;

import java.io.OutputStream;

import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.HtmlHandler;
import oe.frame.bus.res.doc.WordHandler;
import oe.midware.doc.common.ReadIoInfo;
import oe.midware.doc.word.core.ZipWriter;

/**
 * ����ģ�崴��Word�ļ�
 * 
 * @author hotchaoyi,chen.jia.xun(Robanco)
 * 
 */
public class WordHandlerImp implements WordHandler {

	private HtmlHandler htmlHandler;

	/**
	 * ��д�ĵ�
	 * 
	 * @param bussObj
	 *            ҵ�����
	 * @param templateId
	 *            htm�ĵ�ģ��
	 * @param output
	 *            �����
	 * 
	 * ����˵��:��� output����WEB response
	 * response.setContentType("application/msword;charset=GBK");//
	 * ����ContentType String expfileName = System.currentTimeMillis() + .doc;
	 * response.setHeader("Content-Disposition", "attachment; filename=\"" +
	 * expfileName + "\"");
	 */
	public void writeDocUseBussObj(Map bussObj, String templateId,
			OutputStream output) {
		String docNew = htmlHandler.generateDocByTemplate(bussObj, templateId);
		ReadIoInfo.printFileWord(output, docNew);
	}

	/**
	 * ������д�ĵ�
	 * 
	 * @param bussObjs
	 *            ҵ�����List
	 * @param templateId
	 *            htm�ĵ�ģ��
	 * @param output
	 *            ����� ����˵��:��� output����WEB response
	 *            response.setContentType("application/msword;charset=GBK");//
	 *            ����ContentType String expfileName = System.currentTimeMillis() +
	 *            .zip; response.setHeader("Content-Disposition", "attachment;
	 *            filename=\"" + expfileName + "\"");
	 */
	public void writeDocUseMultiBussObj(List bussObjs, String templateId,
			OutputStream output) {
		String[] docNew = htmlHandler.generateMultiDocByTemplate(bussObjs,
				templateId);
		for (int i = 0; i < docNew.length; i++) {

			String templateExtFile = htmlHandler
					.templateModelExtFile(templateId);

			ZipWriter.printFileZip(".doc", docNew[i], templateExtFile,
					templateId, output);
		}

	}

	public void generateXmlReference(String arg0) {
		htmlHandler.generateXmlReference(arg0);
	}

	public List fetchColumnInfo(String arg0) {
		return htmlHandler.fetchColumnInfo(arg0);
	}

	public HtmlHandler getHtmlHandler() {
		return htmlHandler;
	}

	public void setHtmlHandler(HtmlHandler htmlHandler) {
		this.htmlHandler = htmlHandler;
	}

}
