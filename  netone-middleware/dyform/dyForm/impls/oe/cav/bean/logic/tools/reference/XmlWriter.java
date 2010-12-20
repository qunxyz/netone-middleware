package oe.cav.bean.logic.tools.reference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlWriter {

	static Log log = LogFactory.getLog(XmlWriter.class);

	static ResourceBundle message = ResourceBundle.getBundle("busscontext",
			Locale.CHINESE);

	/**
	 * д��XML�ĵ�
	 */
	public static void write(Document document, String url) {
		XMLWriter writer = null;
		if (document == null || url == null || url.equals("")
				|| document.equals("")) {
			// log.debug("dsfds");
			// log.info("");
			log.error("д��XML�ĵ�����Ϊ��");
			// log.warn("");

		} else {
			try {
				OutputFormat format = OutputFormat.createPrettyPrint();
				//��������XML charset
				format.setEncoding(message.getString("charset"));
				writer = new XMLWriter(new FileWriter(url), format);
				writer.write(document);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// �����ʾ����̨
			// OutputFormat format = OutputFormat.createPrettyPrint();
			// try {
			// writer = new XMLWriter(System.out, format);
			// try {
			// writer.write(document);
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
		}
	}
}
