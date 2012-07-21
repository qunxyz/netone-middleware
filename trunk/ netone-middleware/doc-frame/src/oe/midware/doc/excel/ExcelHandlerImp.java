package oe.midware.doc.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import oe.frame.bus.res.doc.ExcelHandler;
import oe.frame.bus.res.doc.common.XmlObj;
import oe.frame.bus.res.doc.common.XmlObjListAndMDD;
import oe.frame.bus.res.doc.common.obj.MultiDimData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Excel����
 * 
 * @author hotcaoyi
 * 
 */
public class ExcelHandlerImp implements ExcelHandler {

	private Log _log = LogFactory.getLog(ExcelHandlerImp.class);

	/**
	 * дExcel
	 * 
	 * @param value
	 *            ��д������
	 * @param column
	 *            ������ֶ�(�ֶ�������ʽ����)
	 * 
	 * @param output
	 *            �����
	 * @param style
	 *            ��ʽ
	 */
	public void writeExcel(List value, List column, Object eStyleVar,
			String tablename, OutputStream output) throws Exception {

		ExcelStyle eStyle = null;
		if (eStyleVar == null || !(eStyleVar instanceof ExcelStyle))
			eStyle = new ExcelStyle();

		// ����Excel����
		HSSFSheet sheet = eStyle.getWb().createSheet(tablename);

		short columnindex = 0;
		for (Iterator iter = column.iterator(); iter.hasNext();) {
			XmlObj xmlobj = (XmlObj) iter.next();
			if (xmlobj.getWidth() <= 0) {
				xmlobj.setWidth((short) 0x1000);
			}
			sheet.setColumnWidth(columnindex++, xmlobj.getWidth());
		}

		HSSFRow row = sheet.createRow(0);
		short i = 0;
		// ����Excel������Ϣ
		for (Iterator itr = column.iterator(); itr.hasNext();) {
			XmlObj xmlobj = (XmlObj) itr.next();
			HSSFCell cell = row.createCell(i++);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(eStyle.getTitleCell());
			String name = xmlobj.getName();
			if (name == null || name.equals("")) {
				cell.setCellValue(xmlobj.getId());
			} else {
				cell.setCellValue(name);
			}
		}
		// ����Excel����������Ϣ
		i = 1;
		for (Iterator itr0 = value.iterator(); itr0.hasNext();) {
			Object valueObj = itr0.next();
			row = sheet.createRow(i++);
			ExcelUtil.writeRow(valueObj, column, row, eStyle);
		}
		try {
			eStyle.getWb().write(output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
		}
	}

	/**
	 * ��Excel
	 * 
	 * @param input
	 *            excel�ļ�������
	 * @param column
	 *            ������ֶ�(�ֶ�������ʽ����)
	 * @param recordObj
	 *            ��¼����(���excel��һ����¼����Ϣ)���ö����о����ҵ��ϵͳ�ṩ
	 * @return Excel�е�ֵ��List�еĶ�����recordObj,ҵ�����,�����Ӧ�ð󶨣�
	 */
	public List readExcel(InputStream input, List column, String tablename)
			throws Exception {

		List rList = new ArrayList();
		try {
			// ͨ��Excel�ļ���������Excel�ĵ�
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			// ���Excel�ĵ��еľ�������
			HSSFSheet sheet = wb.getSheet(tablename);

			// �ӵڶ��п�ʼ������һ��Ĭ���Ǳ�����Ϣ
			HSSFCell cell;
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow row = sheet.getRow(i);
				Map beaxn = new HashMap();
				// ��bean�����еĳ�Ա������ֵ
				short k = 0;
				for (Iterator itr = column.iterator(); itr.hasNext();) {
					XmlObj xmlobj = (XmlObj) itr.next();
					String id = xmlobj.getId();
					String type = xmlobj.getHtmltype();
					_log.debug("ID:" + id + ",type:" + type);

					cell = row.getCell(k++);
					ExcelUtil.setProperty(beaxn, id, type, cell);

				}
				rList.add(beaxn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rList;
	}

	public Map metaData(InputStream input) {
		Map metaData = new HashMap();
		try {
			// ͨ��Excel�ļ���������Excel�ĵ�
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			int sheetsnum = wb.getNumberOfSheets();

			for (int i = 0; i < sheetsnum; i++) {
				String sheetName = wb.getSheetName(i);
				List listColumn = ExcelUtil.excelObj(wb, i);
				metaData.put(sheetName, listColumn);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		return metaData;
	}

	public List readExcel(InputStream input, String sheetname) throws Exception {

		List rList = new ArrayList();
		try {
			// ͨ��Excel�ļ���������Excel�ĵ�
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			List column = ExcelUtil.excelObj(wb, sheetname);

			// ���Excel�ĵ��еľ�������
			HSSFSheet sheet = wb.getSheetAt(0);

			int i = 1;// �ӵڶ��п�ʼ������һ��Ĭ���Ǳ�����Ϣ
			HSSFCell cell;

			for (; i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow row = sheet.getRow(i);
				Map bean = new HashMap();
				// ��bean�����еĳ�Ա������ֵ
				short k = 0;
				for (ListIterator itr = column.listIterator(column.size()); itr
						.hasPrevious();) {
					XmlObj xmlobj = (XmlObj) itr.previous();

					cell = row.getCell(k++);
					ExcelUtil.setProperty(bean, xmlobj.getId(), xmlobj
							.getHtmltype(), cell);

				}
				rList.add(bean);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rList;
	}



	public String[] readSheetName(InputStream input) throws Exception {
		// TODO Auto-generated method stub
		POIFSFileSystem fs = new POIFSFileSystem(input);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		int sheetnum = wb.getNumberOfSheets();
		String[] sheetname = new String[sheetnum];
		for (int i = 0; i < sheetname.length; i++) {
			sheetname[i] = wb.getSheetName(i);
		}
		return sheetname;
	}

	@Override
	public Map readExcelAllSheet(InputStream input)throws Exception {
		Map all=new HashMap();
		
		try {
				Map meta = new HashMap();
				// ͨ��Excel�ļ���������Excel�ĵ�
				POIFSFileSystem fs = new POIFSFileSystem(input);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				int sheetsnum = wb.getNumberOfSheets();

				for (int i = 0; i < sheetsnum; i++) {
					String sheetName = wb.getSheetName(i);
					List listColumn = ExcelUtil.excelObj(wb, i);
					meta.put(sheetName, listColumn);
				}		
			
			for (Iterator iterator = meta.keySet().iterator(); iterator.hasNext();) {
				String tablename = (String) iterator.next();
				List column = (List) meta.get(tablename);
				if(column==null||column.size()==0)
					continue;
				
				// ���Excel�ĵ��еľ�������
				HSSFSheet sheet = wb.getSheet(tablename);

				// �ӵڶ��п�ʼ������һ��Ĭ���Ǳ�����Ϣ
				HSSFCell cell;
				List rList = new ArrayList();
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					HSSFRow row = sheet.getRow(i);
					if(row==null){
						continue;
					}
					Map beaxn = new HashMap();
					// ��bean�����еĳ�Ա������ֵ
					short k = 0;
					for (Iterator itr = column.iterator(); itr.hasNext();) {
						XmlObj xmlobj = (XmlObj) itr.next();
						String id = xmlobj.getId();
						String type = xmlobj.getHtmltype();
						_log.debug("ID:" + id + ",type:" + type);

						cell = row.getCell(k++);
						ExcelUtil.setProperty(beaxn, id, type, cell);

					}
					rList.add(beaxn);
				}
				MultiDimData newDmm = XmlObjListAndMDD.toMDD(column, rList);
				all.put(tablename, newDmm);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			input.close();
		}
		return all;
	}

	@Override
	public List readExcel(InputStream input) throws Exception {
		return readExcel(input, null);
	}
}
