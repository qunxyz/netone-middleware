package oe.frame.bus.res.doc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.common.obj.MultiDimData;

/**
 * Excel处理程序
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface ExcelHandler {
	/**
	 * 写Excel
	 * 
	 * @param value填写的数据
	 * @param column
	 *            输出的字段(字段名，样式属性)
	 * @param output输出流
	 * @param style样式
	 * 
	 */
	public void writeExcel(List value, List column, Object eStyle,
			String tablename, OutputStream output) throws Exception;

	/**
	 * 读Excel
	 * 
	 * @param input
	 *            excel文件输入流
	 * @param column
	 *            输出的字段(字段名，样式属性)
	 * 
	 * @return Excel中的值（List中的对象是Map） <br>
	 *         <br>
	 *         特别注意: 该读入的方式中，可以选择excel中的表和字段,而表和字段是来自metaData方法的
	 *         ,metadata方法中会关闭input流这点要特别注意,二者配合的时候,需要创建两次input流
	 */
	public List readExcel(InputStream input, List column, String tablename)
			throws Exception;
	
	/**
	 * 读Excel
	 * 
	 * @param input
	 *            excel文件输入流
	 * @param column
	 *            输出的字段(字段名，样式属性)
	 * 
	 * @return Excel中的值（List中的对象是Map） <br>
	 *         <br>
	 *         特别注意: 该读入的方式中，可以选择excel中的表和字段,而表和字段是来自metaData方法的
	 *         ,metadata方法中会关闭input流这点要特别注意,二者配合的时候,需要创建两次input流
	 */
	public Map<String,MultiDimData> readExcelAllSheet(InputStream input)
			throws Exception;

	/**
	 * 读Excel, 默认读取第一个sheet
	 * 
	 * @param input
	 *            excel文件输入流
	 * @return Excel中的值（List中的对象是Map,业务对象,具体和应用绑定） <br>
	 *         <br>
	 *         特别注意: 该方法是一个简化的输入,只读取excel中的第一个表,并且全部的列
	 */
	public List readExcel(InputStream input) throws Exception;

	public String[] readSheetName(InputStream input) throws Exception;

	public List readExcel(InputStream input, String name) throws Exception;

	/**
	 * 根据Excel流获得Excel的metaData
	 * 
	 * @param input
	 * @return 格式 key:tablename, value:table中的字段名数组List 元素XmlObj
	 */
	public Map metaData(InputStream input);
}
