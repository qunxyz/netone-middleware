package oe.midware.doc.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.html.HtmlParser;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import oe.frame.bus.res.doc.PdfHandler;

/**
 * PDF读取
 * 
 * @author hotcaoyi
 * 
 */
public class PdfHandlerImp implements PdfHandler {
	private Log _log = LogFactory.getLog(PdfHandlerImp.class);

	/**
	 * 写PDF
	 * 
	 * @param value
	 *            填写数据
	 * @param column
	 *            输出的字段
	 * @param tablename
	 *            表格正题
	 * @param pStyle
	 *            字段名，样式属性
	 * @param outstream
	 *            输出流
	 */
	public void writePdf(List value, List column, Object pStyleVar,
			OutputStream outstream) {
		_log.debug(value);
		if (column == null)
			throw new RuntimeException("输入字段属性列表为null");

		PdfStyle pStyle = null;

		if (pStyleVar == null || !(pStyle instanceof PdfStyle))
			pStyle = PdfStyle.getIntance();

		Document doc = new Document(pStyle.getRectPageSize(), 20, 20, 10, 20);
		try {
			PdfWriter.getInstance(doc, outstream);
			doc.open();
			
			PdfPTable table = new PdfPTable(column.size());
			table.setWidthPercentage(90.0f);
			PdfUtil.writeColumnName(column, table, pStyle);
			PdfUtil.writeRow(value, column, table, pStyle);
			doc.add(table);
		} catch (DocumentException de) {
			de.printStackTrace();
		} finally {
			doc.close();
			try {
				outstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
