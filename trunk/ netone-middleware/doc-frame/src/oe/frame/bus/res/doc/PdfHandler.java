package oe.frame.bus.res.doc;

import java.io.OutputStream;
import java.util.List;

/**
 * 写PDF
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface PdfHandler {
	/**
	 * 写PDF
	 * 
	 * @param value
	 *            填写数据
	 * @param column
	 *            输出的字段(字段名，样式属性)
	 * @param eStyle
	 *            样式
	 * @param outstream
	 *            输出流
	 */
	public void writePdf(List value, List column, Object eStyle,
			OutputStream outstream);

}
