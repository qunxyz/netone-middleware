package oe.midware.doc.pdf;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.res.doc.common.XmlObj;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * PDF工具
 * 
 * @author hotcaoyi
 * 
 */
public class PdfUtil {
	private static Log _log = LogFactory.getLog(PdfUtil.class);

	/**
	 * write table column name
	 * 
	 * @param column
	 * @param table
	 * @param doc
	 * @param pStyle
	 * @throws DocumentException
	 */
	public static void writeColumnName(List column, PdfPTable table,
			PdfStyle pStyle) throws DocumentException {
		PdfPCell cell = null;
		for (Iterator itr = column.iterator(); itr.hasNext();) {
			XmlObj xmlObj = (XmlObj) itr.next();
			String name = xmlObj.getName();
			if (name == null || name.equals("")) {
				name = xmlObj.getId();
			}
			_log.debug(xmlObj.getId());
			cell = new PdfPCell(new Paragraph(name, pStyle.getTitleFont()));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5.0f);
			table.addCell(cell);
		}
	}

	/**
	 * write table row
	 * 
	 * @param column
	 * @param table
	 * @param pStyle
	 */
	public static void writeRow(List value, List column, PdfPTable table,
			PdfStyle pStyle) {
		PdfPCell cell = null;

		for (Iterator itr1 = value.iterator(); itr1.hasNext();) {
			// 行对象
			Map rValue = (Map) itr1.next();
			for (Iterator itr2 = column.iterator(); itr2.hasNext();) {
				XmlObj xObj = (XmlObj) itr2.next();
				Object mValue = rValue.get(xObj.getId());
				_log.debug("ID:" + xObj.getId());
				_log.debug("Value:" + mValue);
				mValue = mValue == null ? "" : mValue.toString();
				cell = new PdfPCell(new Paragraph(mValue.toString(), pStyle
						.getDetFont()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
		}

	}
}
