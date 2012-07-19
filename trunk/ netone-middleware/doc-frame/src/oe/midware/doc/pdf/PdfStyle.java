package oe.midware.doc.pdf;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;

/**
 * PDF样式
 * 
 * @author hotcaoyi
 * 
 */
public class PdfStyle {
	// 中文编码
	public static BaseFont bfChinese = null;

	// 表格正题（表格上方的描述）字体
	public Font headFont = null;

	// 表格字段名字体
	public Font titleFont = null;

	// 表格内容字体
	public Font detFont = null;

	// 定义A4页面大小
	public Rectangle rectPageSize = new Rectangle(PageSize.A4);

	public static PdfStyle pdfStyle = null;

	public static BaseFont getBfChinese() {
		return bfChinese;
	}

	public static void setBfChinese(BaseFont bfChinese) {
		PdfStyle.bfChinese = bfChinese;
	}

	public static PdfStyle getPdfStyle() {
		return pdfStyle;
	}

	public static void setPdfStyle(PdfStyle pdfStyle) {
		PdfStyle.pdfStyle = pdfStyle;
	}

	public Font getDetFont() {
		return detFont;
	}

	public void setDetFont(Font detFont) {
		this.detFont = detFont;
	}

	public Font getHeadFont() {
		return headFont;
	}

	public void setHeadFont(Font headFont) {
		this.headFont = headFont;
	}

	public Rectangle getRectPageSize() {
		return rectPageSize;
	}

	public void setRectPageSize(Rectangle rectPageSize) {
		this.rectPageSize = rectPageSize;
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	public PdfStyle() {

	}

	public void init() {
		if (bfChinese == null) {
			try {
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
						BaseFont.NOT_EMBEDDED);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		headFont = new Font(bfChinese, 16, Font.BOLD);
		titleFont = new Font(bfChinese, 14, Font.BOLD);
		detFont = new Font(bfChinese, 12, Font.NORMAL);

		rectPageSize.setBackgroundColor(new java.awt.Color(0xFF, 0xFF, 0xDE));
	}

	public static PdfStyle getIntance() {
		if (pdfStyle == null) {
			pdfStyle = new PdfStyle();
			pdfStyle.init();
		}
		return pdfStyle;
	}

}
