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
 * �򵥵�Jxtl����ģ����  Excel ����Ŀǰ�Ѹ�JSPģʽ����
 * 
 * @Title:
 * @Description:
 * @date Apr 3, 2007
 * @author zhang.chao.yi
 * @version 1.0
 */
public final class JxlUtilsTemplate {

	// ------------------------------------------- Public variables

	/** COLUMN �ֶ����� */
	public final static String NAME_MAP_KEY = "name";

	/** COLUMN �ֶ����� */
	public final static String TYPE_MAP_KEY = "type";

	/** ��ǩ����(�ַ�������) */
	public static final String LABEL_TYPE = CellType.LABEL.toString();

	/** �������� */
	public static final String NUMBER_TYPE = CellType.NUMBER.toString();

	/** ʱ������ */
	public static final String DATE_TYPE = CellType.DATE.toString();

	// ------------------------------------------- Private variables

	/** ��ʼ����־ */
	private static Logger logger = Logger.getLogger(JxlUtilsTemplate.class);

	/** ������� */
	private HttpServletResponse response;

	/** ʱ���ʽ��ȫ�ֳ��� */
	private DateFormat df1 = new DateFormat("yyy-MM-dd");

	private DateFormat df2 = new DateFormat("yyy-MM-dd hh:mm:ss");

	/** ���ָ�ʽȫ�ֳ��� */
	private NumberFormat nf = new NumberFormat("#,##0.00");

	/** �û����ݵı��⼰�����ͼ���,һ��Ԫ���Ǹ�Map���� */
	private List header = new ArrayList();

	/** �û����� */
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
	 * ����EXEL�ļ�
	 * 
	 * @param sheetName
	 *            ҳ����
	 * @param index
	 *            ҳλ��
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 * @throws ParseException
	 */
	public void exportTable(String sheetName, int index) throws IOException,
			RowsExceededException, WriteException, ParseException {
		// if (this.header.size() == 0 || this.content.size() == 0) {
		if (this.header.size() == 0) {
			throw new RuntimeException("�Բ���,���������ú�Ҫ����������!");
		}

		OutputStream out = response.getOutputStream();

		WritableWorkbook book = Workbook.createWorkbook(out);
		WritableSheet sheet = book.createSheet(sheetName, index);

		// ----------------- �ڲ�����
		int x = 1, y = 0;
		// ��ӡͷ
		for (Iterator itr = this.header.iterator(); itr.hasNext();) {
			Map map = (Map) itr.next();
			String title = (String) map.get(NAME_MAP_KEY);

			Label label = new Label(y++, 0, title);
			sheet.addCell(label);
		}

		// ��ӡ����
		WritableCell cell = null;
		for (int i = 0; i < this.content.size(); i++) {
			Object[] obj = (Object[]) this.content.get(i);
			for (y = 0; y < obj.length; y++) {
				Map map = (Map) this.header.get(y);
				String type = (String) map.get(TYPE_MAP_KEY);

				if (obj[y] == null) {// ʹ�õ��������ֶβ�ΪNULL
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
	 * ��excel�ļ�
	 * 
	 * @param in
	 *            excel�ļ���
	 * @return
	 * @throws IOException
	 */
	public List read(InputStream in) throws IOException {
		return read(in, 0);
	}

	/**
	 * ��excel�ļ�
	 * 
	 * @param in
	 *            excel�ļ���
	 * @param sheetIndex
	 *            excel�ļ��е�ҳ��
	 * @return
	 * @throws IOException
	 */
	public List read(InputStream in, int sheetIndex) throws IOException {
		List list = new ArrayList();// �������ݶ���

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
	 * ���� �û����ݵı��⼰�����ͼ���
	 * 
	 * @param header
	 */
	public void setHeader(List header) {
		this.header = header;
	}

	/**
	 * �����û�����
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
		String value = "";// ����ֵ

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
