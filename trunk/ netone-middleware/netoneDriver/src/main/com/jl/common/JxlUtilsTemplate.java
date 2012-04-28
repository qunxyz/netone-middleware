package com.jl.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;

/**
 * 简单的Jxtl操作模板类  Excel 处理目前已改JSP模式处理
 * 
 * @Title:
 * @Description:
 * @date Apr 3, 2007
 * @author zhang.chao.yi
 * @version 1.0
 */
public final class JxlUtilsTemplate {

	// ------------------------------------------- Public variables

	/** COLUMN 字段名称 */
	public final static String NAME_MAP_KEY = "name";

	/** COLUMN 字段类型 */
	public final static String TYPE_MAP_KEY = "type";

	/** 标签类型(字符串类型) */
	public static final String LABEL_TYPE = CellType.LABEL.toString();

	/** 数字类型 */
	public static final String NUMBER_TYPE = CellType.NUMBER.toString();

	/** 时间类型 */
	public static final String DATE_TYPE = CellType.DATE.toString();

	// ------------------------------------------- Private variables

	/** 初始化日志 */
	private static Logger logger = Logger.getLogger(JxlUtilsTemplate.class);

	/** 回响对象 */
	private HttpServletResponse response;

	/** 时间格式化全局常量 */
	private DateFormat df1 = new DateFormat("yyy-MM-dd");

	private DateFormat df2 = new DateFormat("yyy-MM-dd hh:mm:ss");

	/** 数字格式全局常量 */
	private NumberFormat nf = new NumberFormat("#,##0.00");

	/** 用户数据的标题及其类型集合,一个元素是个Map类型 */
	private List header = new ArrayList();

	/** 用户数据 */
	private List content = new ArrayList();

	// ------------------------------------------- constructs

	private static JxlUtilsTemplate jxtlUtil;

	private JxlUtilsTemplate() {

	}

	public static JxlUtilsTemplate newInstanct() {
		if (jxtlUtil == null) {
			jxtlUtil = new JxlUtilsTemplate();
		}
		return jxtlUtil;
	}

	public JxlUtilsTemplate(HttpServletResponse response) {
		this.response = response;
		this.response.reset();
		this.response.setHeader("Content-type", "application/octet-stream");
		this.response.setHeader("Accept-Ranges", "bytes");
		this.response.setContentType("application/x-msdownload");

		this.response.setContentType("application nd.ms-execl; charset=gbk");
		this.response.setHeader("Content-Disposition", "attachment;filename="
				+ (new Date()).getTime() + ".xls");

	}

	// ---------------------------------------------Public methods

	/**
	 * 导出EXEL文件
	 * 
	 * @param sheetName
	 *            页名称
	 * @param index
	 *            页位置
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 * @throws ParseException
	 */
	public void exportTable(String sheetName, int index) throws IOException,
			RowsExceededException, WriteException, ParseException {
		// if (this.header.size() == 0 || this.content.size() == 0) {
		if (this.header.size() == 0) {
			throw new RuntimeException("对不起,请你先设置好要导出的数据!");
		}

		OutputStream out = response.getOutputStream();

		WritableWorkbook book = Workbook.createWorkbook(out);
		WritableSheet sheet = book.createSheet(sheetName, index);

		// ----------------- 内部变量
		int x = 1, y = 0;
		// 打印头
		for (Iterator itr = this.header.iterator(); itr.hasNext();) {
			Map map = (Map) itr.next();
			String title = (String) map.get(NAME_MAP_KEY);

			Label label = new Label(y++, 0, title);
			sheet.addCell(label);
		}

		// 打印数据
		WritableCell cell = null;
		for (int i = 0; i < this.content.size(); i++) {
			Object[] obj = (Object[]) this.content.get(i);
			for (y = 0; y < obj.length; y++) {
				Map map = (Map) this.header.get(y);
				String type = (String) map.get(TYPE_MAP_KEY);

				if (obj[y] == null) {// 使得导出数据字段不为NULL
					cell = new Label(y, x, "");
				} else {
					if (NUMBER_TYPE.equals(type)) {
						WritableCellFormat wfc = new WritableCellFormat(nf);
						wfc.setAlignment(Alignment.CENTRE);
						cell = new jxl.write.Number(y, x, Double
								.parseDouble(obj[y].toString()), wfc);

					} else if (DATE_TYPE.equals(type)
							&& !String.valueOf(obj[y]).equals("")) {
						WritableCellFormat wfc = new WritableCellFormat(df1);
						wfc.setAlignment(Alignment.CENTRE);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						Date date = sdf.parse(obj[y].toString());
						cell = new jxl.write.DateTime(y, x, date, wfc);

					} else {
						cell = new Label(y, x, obj[y].toString());
					}
				}

				sheet.addCell(cell);
			}
			x++;
		}
		book.write();
		book.close();
		out.flush();
		out.close();
	}

	/**
	 * 读excel文件
	 * 
	 * @param in
	 *            excel文件流
	 * @return
	 * @throws IOException
	 */
	public List read(InputStream in) throws IOException {
		return read(in, 0);
	}

	/**
	 * 读excel文件
	 * 
	 * @param in
	 *            excel文件流
	 * @param sheetIndex
	 *            excel文件中的页号
	 * @return
	 * @throws IOException
	 */
	public List read(InputStream in, int sheetIndex) throws IOException {
		List list = new ArrayList();// 返回数据对象

		try {
			Workbook book = Workbook.getWorkbook(in);

			Sheet sheet = book.getSheet(sheetIndex);
			int columns = sheet.getColumns();
			int rows = sheet.getRows();

			for (int i = 0; i < rows; i++) {
				Object[] obj = new Object[columns];

				for (int j = 0; j < columns; j++) {
					Cell cell = sheet.getCell(j, i);
					obj[j] = this.stringValue(cell);
				}

				list.add(obj);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}

		return list;
	}

	public Object[] readAll(InputStream in) throws Exception {
		Workbook book = Workbook.getWorkbook(in);
		Sheet[] sheets = book.getSheets();
		Object[] object = new Object[sheets.length];

		for (int i = 0; i < sheets.length; i++) {
			List sheetSet = new ArrayList();

			int columns = sheets[i].getColumns();
			int rows = sheets[i].getRows();

			for (int k = 0; k < rows; k++) {
				Object[] obj = new Object[columns];

				for (int j = 0; j < columns; j++) {
					Cell cell = sheets[i].getCell(j, k);
					obj[j] = this.stringValue(cell);
				}

				sheetSet.add(obj);
			}

			object[i] = sheetSet;
		}

		return object;
	}

	/**
	 * 设置 用户数据的标题及其类型集合
	 * 
	 * @param header
	 */
	public void setHeader(List header) {
		this.header = header;
	}

	/**
	 * 设置用户数据
	 * 
	 * @param content
	 */
	public void setContent(List content) {
		this.content = content;
	}

	/**
	 * @return the content
	 */
	public List getContent() {
		return content;
	}

	/**
	 * @return the header
	 */
	public List getHeader() {
		return header;
	}

	// ---------------------------------------------------------Private Methods

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private DecimalFormat dnf = new DecimalFormat("###,###.##########");

	private String stringValue(Cell cell) {
		String value = "";// 返回值

		value = cell.getContents() == null ? "" : cell.getContents().trim();
		if ("".equals(value)) {
			return value;
		}

		CellType type = cell.getType();
		if (CellType.DATE == type || CellType.DATE_FORMULA == type) {
			Date date = ((DateCell) cell).getDate();
			value = sdf.format(date);
		}

		return value;
	}
}
