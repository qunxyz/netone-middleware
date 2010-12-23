package oe.bi.wizard.ExcelToDb;

import java.net.URL;
import java.util.List;

public interface ExcelToDbDao {
	/**
	 * ��ȡExcel��sheet����
	 * @param url
	 * @return
	 */
	public String[] getSheetName(URL url);
	/**
	 * ��ȡ���ݼ���
	 * @param url
	 * @param sheetname
	 * @return
	 */
	public List getList(URL url, String sheetname);

}
