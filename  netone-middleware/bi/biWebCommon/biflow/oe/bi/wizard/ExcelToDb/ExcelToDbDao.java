package oe.bi.wizard.ExcelToDb;

import java.net.URL;
import java.util.List;

public interface ExcelToDbDao {
	/**
	 * 获取Excel的sheet名称
	 * @param url
	 * @return
	 */
	public String[] getSheetName(URL url);
	/**
	 * 获取数据集合
	 * @param url
	 * @param sheetname
	 * @return
	 */
	public List getList(URL url, String sheetname);

}
