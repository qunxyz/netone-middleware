package oe.frame.bus.res.doc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.common.obj.MultiDimData;

/**
 * Excel�������
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface ExcelHandler {
	/**
	 * дExcel
	 * 
	 * @param value��д������
	 * @param column
	 *            ������ֶ�(�ֶ�������ʽ����)
	 * @param output�����
	 * @param style��ʽ
	 * 
	 */
	public void writeExcel(List value, List column, Object eStyle,
			String tablename, OutputStream output) throws Exception;

	/**
	 * ��Excel
	 * 
	 * @param input
	 *            excel�ļ�������
	 * @param column
	 *            ������ֶ�(�ֶ�������ʽ����)
	 * 
	 * @return Excel�е�ֵ��List�еĶ�����Map�� <br>
	 *         <br>
	 *         �ر�ע��: �ö���ķ�ʽ�У�����ѡ��excel�еı���ֶ�,������ֶ�������metaData������
	 *         ,metadata�����л�ر�input�����Ҫ�ر�ע��,������ϵ�ʱ��,��Ҫ��������input��
	 */
	public List readExcel(InputStream input, List column, String tablename)
			throws Exception;
	
	/**
	 * ��Excel
	 * 
	 * @param input
	 *            excel�ļ�������
	 * @param column
	 *            ������ֶ�(�ֶ�������ʽ����)
	 * 
	 * @return Excel�е�ֵ��List�еĶ�����Map�� <br>
	 *         <br>
	 *         �ر�ע��: �ö���ķ�ʽ�У�����ѡ��excel�еı���ֶ�,������ֶ�������metaData������
	 *         ,metadata�����л�ر�input�����Ҫ�ر�ע��,������ϵ�ʱ��,��Ҫ��������input��
	 */
	public Map<String,MultiDimData> readExcelAllSheet(InputStream input)
			throws Exception;

	/**
	 * ��Excel, Ĭ�϶�ȡ��һ��sheet
	 * 
	 * @param input
	 *            excel�ļ�������
	 * @return Excel�е�ֵ��List�еĶ�����Map,ҵ�����,�����Ӧ�ð󶨣� <br>
	 *         <br>
	 *         �ر�ע��: �÷�����һ���򻯵�����,ֻ��ȡexcel�еĵ�һ����,����ȫ������
	 */
	public List readExcel(InputStream input) throws Exception;

	public String[] readSheetName(InputStream input) throws Exception;

	public List readExcel(InputStream input, String name) throws Exception;

	/**
	 * ����Excel�����Excel��metaData
	 * 
	 * @param input
	 * @return ��ʽ key:tablename, value:table�е��ֶ�������List Ԫ��XmlObj
	 */
	public Map metaData(InputStream input);
}
