package oe.midware.doc.word;

import java.io.OutputStream;

import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.HtmlHandler;
import oe.frame.bus.res.doc.WordHandler;
import oe.midware.doc.common.ReadIoInfo;
import oe.midware.doc.word.core.ZipWriter;

/**
 * 根据模板创建Word文件
 * 
 * @author hotchaoyi,chen.jia.xun(Robanco)
 * 
 */
public class WordHandlerImp implements WordHandler {

	private HtmlHandler htmlHandler;

	/**
	 * 编写文档
	 * 
	 * @param bussObj
	 *            业务对象
	 * @param templateId
	 *            htm文档模板
	 * @param output
	 *            输出流
	 * 
	 * 补充说明:如果 output来自WEB response
	 * response.setContentType("application/msword;charset=GBK");//
	 * 设置ContentType String expfileName = System.currentTimeMillis() + .doc;
	 * response.setHeader("Content-Disposition", "attachment; filename=\"" +
	 * expfileName + "\"");
	 */
	public void writeDocUseBussObj(Map bussObj, String templateId,
			OutputStream output) {
		String docNew = htmlHandler.generateDocByTemplate(bussObj, templateId);
		ReadIoInfo.printFileWord(output, docNew);
	}

	/**
	 * 批量编写文档
	 * 
	 * @param bussObjs
	 *            业务对象List
	 * @param templateId
	 *            htm文档模板
	 * @param output
	 *            输出流 补充说明:如果 output来自WEB response
	 *            response.setContentType("application/msword;charset=GBK");//
	 *            设置ContentType String expfileName = System.currentTimeMillis() +
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
