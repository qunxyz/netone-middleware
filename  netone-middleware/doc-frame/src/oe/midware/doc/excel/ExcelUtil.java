package oe.midware.doc.excel;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.common.XmlObj;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/*******************************************************************************
 * Excel工具箱
 * 
 * @author hotcaoyi
 * 
 */
public class ExcelUtil {

	public static final int _SRING_INDEX = 0;

	public static final int _NUM_INDEX = 1;

	public static final int _SQL_DATE_INDEX = 2;

	public static final int _UTIL_DATE_INDEX = 3;

	public static final int _BOOL_INDEX = 4;

	// 字符串类型
	protected static String _STRING = "java.lang.String";

	// 布尔类型
	protected static String _BOOLEAN = "java.lang.Boolean";

	// sql date type
	protected static String _SQL_DATE = "java.sql.Date";

	// util date type
	protected static String _UTTL_DATE = "java.util.Date";

	// timestamp date type
	protected static String _TIME = "java.sql.Timestamp";

	// 数据类型列表
	protected static List numTypeList = new ArrayList();

	private static Log _log = LogFactory.getLog(ExcelUtil.class);

	static {
		numTypeList.add("java.lang.Long");
		numTypeList.add("java.lang.Double");
		numTypeList.add("java.lang.Integer");
		numTypeList.add("java.lang.Float");
	}

	/**
	 * 将具体excel表中的所有字段构建成 XmlObj对象数组
	 * 
	 * @param wb
	 * @param i
	 * @return
	 */
	public static List excelObj(HSSFWorkbook wb, String name) {
		HSSFSheet sheet = null;
		if (name == null) {
			sheet = wb.getSheetAt(0);
		} else {
			sheet = wb.getSheet(name);
		}

		return excelObjCore(wb, sheet);
	}

	public static List excelObj(HSSFWorkbook wb, int i) {
		HSSFSheet sheet = wb.getSheetAt(i);

		return excelObjCore(wb, sheet);
	}

	private static List excelObjCore(HSSFWorkbook wb, HSSFSheet sheet) {
		int rownum = sheet.getPhysicalNumberOfRows();

		List listColumn = new ArrayList();
		if (rownum >= 2) {// 必须至少有两行,第一行是标题,第二行是模拟数据(决定类型)
			HSSFRow rowColumn = sheet.getRow((short) 0);
			HSSFRow rowValue = sheet.getRow((short) 1);
			int rowCount = rowColumn.getPhysicalNumberOfCells();

			for (short j = 0; j < rowCount; j++) {
				HSSFCell cell=rowColumn.getCell(j);
				HSSFCell cellx=rowValue.getCell(j);
				if(cell!=null){
					String columnid = cell.getStringCellValue();
					int columnTypeInt =1;
					if(cellx!=null){
						columnTypeInt=cellx.getCellType();
					}
					 
					String columnType = HSSFCell.CELL_TYPE_NUMERIC == columnTypeInt ? "number"
							: "string";
					XmlObj xmlobj = new XmlObj();
					xmlobj.setId(columnid);
					xmlobj.setName(columnid);
					xmlobj.setType(columnType);
					xmlobj.setHtmltype(columnType);
					listColumn.add(xmlobj);
				}

			}
		}
		return listColumn;
	}

	/**
	 * 判断数据类型
	 * 
	 * @param type数据类型
	 * @return
	 */
	public static int checkType(String type) {
		if (isNumType(type))
			return _NUM_INDEX;
		if (_SQL_DATE.equalsIgnoreCase(type))
			return _SQL_DATE_INDEX;
		if (_UTTL_DATE.equalsIgnoreCase(type))
			return _UTIL_DATE_INDEX;
		if (_TIME.equalsIgnoreCase(type))
			return _UTIL_DATE_INDEX;
		if (_BOOLEAN.equalsIgnoreCase(type))
			return _BOOL_INDEX;
		return _SRING_INDEX;
	}

	/**
	 * 判断是否数字类型
	 * 
	 * @param paramType数据类型
	 * @return
	 */
	public static boolean isNumType(String paramType) {
		boolean flag = false;
		for (Iterator itr = numTypeList.iterator(); itr.hasNext();) {
			if (paramType.equalsIgnoreCase((String) itr.next())) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 把obj写到row中去
	 * 
	 * @param obj
	 *            Object行对象
	 * @param column
	 *            输出的字段(字段名，样式属性)
	 * @param row
	 *            HSSFRow行对象
	 * @param eStyle
	 *            excel样式
	 */
	public static void writeRow(Object obj, List column, HSSFRow row,
			ExcelStyle eStyle) {
		short temp = 0;
		try {
			for (Iterator itr = column.iterator(); itr.hasNext();) {
				XmlObj xmlobj = (XmlObj) itr.next();
				HSSFCell cell = row.createCell(temp++);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				_log.debug(xmlobj.getId());
				_log.debug(obj.getClass().getName());
				Object v = BeanUtils.getProperty(obj, xmlobj.getId());
				if (v == null) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(eStyle.getDetCell());
					cell.setCellValue("");
				} else {
					if ("number".equals(xmlobj.getHtmltype())) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(eStyle.getNumCell());
						cell.setCellValue(Double.parseDouble(v.toString()));
					} else if ("string".equals(xmlobj.getHtmltype())) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(eStyle.getDetCell());
						cell.setCellValue(v.toString());
					}
					// switch (ExcelUtil.checkType(xmlobj.getHtmltype())) {
					// case ExcelUtil._SRING_INDEX: {
					// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// cell.setCellStyle(eStyle.getDetCell());
					// cell.setCellValue(v.toString());
					// break;
					// }
					// case ExcelUtil._NUM_INDEX: {
					// cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					// cell.setCellStyle(eStyle.getNumCell());
					// cell.setCellValue(Double.parseDouble(v.toString()));
					// break;
					// }
					// case ExcelUtil._SQL_DATE_INDEX: {
					// cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					// cell.setCellStyle(eStyle.getDateCell());
					// cell.setCellValue(java.sql.Date.valueOf(v.toString()));
					// break;
					// }
					// case ExcelUtil._UTIL_DATE_INDEX: {
					// cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					// cell.setCellStyle(eStyle.getDateCell());
					// SimpleDateFormat format = new SimpleDateFormat(
					// "yyyy-MM-dd hh:mm:ss");
					// cell.setCellValue(format.parse(v.toString()));
					// break;
					// }
					// case ExcelUtil._BOOL_INDEX: {
					// cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
					// cell.setCellStyle(eStyle.getDetCell());
					// cell.setCellValue(Boolean.getBoolean(v.toString()));
					// break;
					// }
					// }
				}

			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据字段名查找该字段所在列的索引号。
	 * 
	 * @param name
	 * @param cell
	 * @return
	 */
	public static short findCellByCName(String name, HSSFSheet sheet) {
		short i = -1;

		HSSFRow row = sheet.getRow((short) 0);
		short j = (short) ((short) row.getPhysicalNumberOfCells() - 1);
		for (Iterator itr = row.cellIterator(); itr.hasNext();) {
			HSSFCell cell = (HSSFCell) itr.next();
			if (name.equalsIgnoreCase(cell.getStringCellValue())) {
				i = j;
				break;
			}
			j--;
		}

		return i;
	}

	/**
	 * 给bean对象中的成员变量赋值
	 * 
	 * @param obj
	 * @param name
	 * @param cell
	 */
	public static void setProperty(Map objx, String name, String type,
			HSSFCell cell) {
		String realValue = "";
		if (cell != null) {
			int celltype = cell.getCellType();
			System.out.print(celltype+",");
			if (HSSFCell.CELL_TYPE_BOOLEAN == celltype) {
				boolean value = cell.getBooleanCellValue();
				realValue = String.valueOf(value);
			} else if (type.equals("date")) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					realValue = format.format(cell.getDateCellValue());
				} catch (Exception e) {
					realValue = cell.getStringCellValue();
				}
			} else if (HSSFCell.CELL_TYPE_NUMERIC == celltype) {			
				double value = cell.getNumericCellValue();
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					 Date date = HSSFDateUtil.getJavaDate(value);
					 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					 realValue=format.format(date);
				}else{
					realValue = String.valueOf(value);
					if(realValue.equals("41004.0")){
						 Date date = HSSFDateUtil.getJavaDate(value);
						 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						 realValue=format.format(date);
					}
				}
				
			} else {
				realValue = cell.getStringCellValue();
			}
			
		}
		System.out.println(realValue);
		objx.put(name, realValue);

		// switch (ExcelUtil.checkType(type)) {
		//
		// case ExcelUtil._SRING_INDEX: {
		// BeanUtils.setProperty(obj, name, cell.getStringCellValue());
		// break;
		// }
		// case ExcelUtil._SQL_DATE_INDEX: {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// String date = format.format(cell.getDateCellValue());
		// if (date != null)
		// BeanUtils.setProperty(obj, name, java.sql.Date.valueOf(date
		// .toString()));
		// break;
		// }
		// case ExcelUtil._UTIL_DATE_INDEX: {
		// BeanUtils.setProperty(obj, name, cell.getDateCellValue());
		// break;
		// }
		// case ExcelUtil._NUM_INDEX: {
		// Double dValue = new Double(cell.getNumericCellValue());
		// String value = dValue.toString();
		// if (isFloat(value)) {
		//
		// BeanUtils.setProperty(obj, name, new Float(value));
		// }
		//
		// else
		// BeanUtils.setProperty(obj, name, getLong(value));
		// }
		// }

	}

	/**
	 * 判断是否是浮点数
	 * 
	 * @param value
	 */
	private static boolean isFloat(String value) {
		if (value == null)
			return false;
		int n = value.indexOf('.');
		if (n == -1)
			return false;
		boolean flag = false;
		for (int i = n + 1; i < value.length(); i++) {
			if (value.charAt(i) != '0')
				flag = true;
			break;
		}
		return flag;
	}

	/**
	 * 取"200.00"整数及 200
	 * 
	 * @param value
	 * @return
	 */
	private static Long getLong(String value) {
		Long lValue = null;
		String lStr = value.substring(0, value.indexOf('.'));
		lValue = new Long(lStr);
		return lValue;
	}
}
