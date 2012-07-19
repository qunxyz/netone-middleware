package oe.midware.doc.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel样式对象
 * 
 * @author hotcaoyi
 * 
 */
public class ExcelStyle {

	private HSSFWorkbook wb = new HSSFWorkbook();

	private HSSFFont titleFont = wb.createFont();

	private HSSFFont detFont = wb.createFont();

	private HSSFCellStyle titleCell = wb.createCellStyle();

	private HSSFCellStyle detCell = wb.createCellStyle();

	private HSSFCellStyle dateCell = wb.createCellStyle();

	private HSSFCellStyle numCell = wb.createCellStyle();

	private HSSFDataFormat formate = wb.createDataFormat();

	private ExcelStyle style = null;

	/**
	 * 默认参数
	 */
	public ExcelStyle() {
		// 设置标题单元格信息
		titleFont.setColor(HSSFColor.DARK_BLUE.index);
		titleFont.setFontHeight((short) 0x120);
		titleCell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		titleCell.setFont(titleFont);

		// 设置内容单元格信息
		detFont.setColor(HSSFColor.BLACK.index);
		detCell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		detCell.setFont(detFont);
		// 设置数字单元格信息
		numCell.setFont(detFont);
		numCell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		numCell.setDataFormat(formate.getFormat("#,##0.0000"));
		// 设置日期单元格信息
		dateCell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		dateCell.setFont(detFont);
		dateCell.setDataFormat(formate.getBuiltinFormat("m/d/yy h:mm"));
	}

	public ExcelStyle getStyle() {
		return style;
	}

	public void setStyle(ExcelStyle style) {
		style = style;
	}

	public HSSFCellStyle getDateCell() {
		return dateCell;
	}

	public void setDateCell(HSSFCellStyle dateCell) {
		this.dateCell = dateCell;
	}

	public HSSFCellStyle getDetCell() {
		return detCell;
	}

	public void setDetCell(HSSFCellStyle detCell) {
		this.detCell = detCell;
	}

	public HSSFFont getDetFont() {
		return detFont;
	}

	public void setDetFont(HSSFFont detFont) {
		this.detFont = detFont;
	}

	public HSSFDataFormat getFormate() {
		return formate;
	}

	public void setFormate(HSSFDataFormat formate) {
		this.formate = formate;
	}

	public HSSFCellStyle getNumCell() {
		return numCell;
	}

	public void setNumCell(HSSFCellStyle numCell) {
		this.numCell = numCell;
	}

	public HSSFCellStyle getTitleCell() {
		return titleCell;
	}

	public void setTitleCell(HSSFCellStyle titleCell) {
		this.titleCell = titleCell;
	}

	public HSSFFont getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(HSSFFont titleFont) {
		this.titleFont = titleFont;
	}

	public HSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

}
