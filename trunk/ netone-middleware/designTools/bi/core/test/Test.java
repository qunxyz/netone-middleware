package test;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;

public class Test {
	public Test() {
	}

	public static void get(int[] i) {
		int[] temp = (int[]) i.clone();
		Arrays.sort(temp);
		for (int j = 0; j < temp.length; j++) {
			System.out.println("" + temp[j]);
		}

	}

	private static String getHexStr(int i) {
		String result = Integer.toHexString(i);
		if (i <= 0xf) {
			result = "0" + result;
		}
		return result;
	}

	private static String getRGBHexStr(java.awt.Color c) {
		String result = "#";

		result += getHexStr(c.getRed());
		result += getHexStr(c.getGreen());
		result += getHexStr(c.getBlue());
		return result;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		// 设置样式表的属性值

		wb.setSheetName(0, "报表");
		HSSFFont fontNormal = wb.createFont();
		fontNormal.setFontHeightInPoints((short) 10);
		fontNormal.setFontName("宋体");
		
		HSSFCellStyle style=wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFont(fontNormal);
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell c0 = row.createCell((short) 0);
		c0.setCellValue("liwei");
		c0.setCellStyle(style);
		HSSFCell c1 = row.createCell((short) 1);
		c1.setCellStyle(style);
		HSSFCellUtil.setCellStyleProperty(c1,wb,"fillForegroundColor",HSSFColor.YELLOW.index);
		//style.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		c1.setCellValue("bible");
		
		//System.out.println("font count:"+wb.getNumberOfFonts()+" style count:"+wb.getNumCellStyles());
		FileOutputStream fo = new FileOutputStream("test.xls");

		wb.write(fo);
	}

}