package oe.frame.bus.res.doc;

import java.io.OutputStream;
import java.util.List;

/**
 * дPDF
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface PdfHandler {
	/**
	 * дPDF
	 * 
	 * @param value
	 *            ��д����
	 * @param column
	 *            ������ֶ�(�ֶ�������ʽ����)
	 * @param eStyle
	 *            ��ʽ
	 * @param outstream
	 *            �����
	 */
	public void writePdf(List value, List column, Object eStyle,
			OutputStream outstream);

}
