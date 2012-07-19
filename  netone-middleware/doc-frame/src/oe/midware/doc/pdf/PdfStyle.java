package oe.midware.doc.pdf;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;

/**
 * PDF��ʽ
 * 
 * @author hotcaoyi
 * 
 */
public class PdfStyle {
	// ���ı���
	public static BaseFont bfChinese = null;

	// ������⣨����Ϸ�������������
	public Font headFont = null;

	// ����ֶ�������
	public Font titleFont = null;

	// �����������
	public Font detFont = null;

	// ����A4ҳ���С
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
