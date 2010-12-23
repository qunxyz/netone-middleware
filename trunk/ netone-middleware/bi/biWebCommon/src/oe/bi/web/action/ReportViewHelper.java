package oe.bi.web.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.view.obj.ViewModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * ģ�ͷ�����ͼ������
 * ʵ�֣�����excel
 * @author zhengjr
 *
 */

public class ReportViewHelper {
	// -------------------------------------------------��������

	public static final ReportViewHelper helper = new ReportViewHelper();

	private Log log = LogFactory.getLog(ReportViewHelper.class);

	private static String path = "../conf/";


	private ReportViewHelper() {

	}

	public static ReportViewHelper getInstance() {


		return helper;
	}

	public void DataToExcelFile(ViewModel viewModel,
			HttpServletRequest request, HttpServletResponse response,
			ServletConfig config) {

		WritableWorkbook workbook = null;
		try {
			String filename=path+System.currentTimeMillis()+".xls";
			// ����������
			workbook = Workbook.createWorkbook(new File(filename));

			// ����������
			WritableSheet sheet = workbook.createSheet("datalist", 0);

			response.setContentType("application/vnd.ms-excel");

			response.setHeader("Content-disposition",
					"attachment; filename=dataexcel");

			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 14, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BROWN);

			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
					wfc);

			// ά������
			String[] dimName = viewModel.getDimensionname();

			// ָ������
			String[] targetName = viewModel.getTargetname();

			// ά��ֵ
			String[][] dimValue = viewModel.getDimensionvalue();

			// ָ��ֵ
			double[][] targetValue = viewModel.getTargetvalue();

			/** ��ӱ�����--ά�ȱ��� */
			for (int i = 0; i < dimName.length; i++) {

				jxl.write.Label label = new jxl.write.Label(i, 0, dimName[i]
						.toString(), wcfFC);

				sheet.addCell(label);

			}

			/** ��ӱ�����--ָ����� */
			for (int j = 0; j < targetName.length; j++) {

				jxl.write.Label label = new jxl.write.Label(j + dimName.length,
						0, targetName[j].toString(), wcfFC);

				sheet.addCell(label);
			}

			/** ��������� */
			for (int i = 0; i < dimValue.length; i++) {

				// ----- ��ӡÿ���е�ά���е�ֵ START------
				for (int j = 0; j < dimName.length; j++) {

					jxl.write.Label label = new jxl.write.Label(j, i + 1,
							dimValue[i][j]);

					sheet.addCell(label);

				}

				// ----- ��ӡÿ���е�ָ���е�ֵ START------
				for (int k = 0; k < targetName.length; k++) {

					jxl.write.Label label = new jxl.write.Label(k
							+ dimName.length, i + 1, "" + targetValue[i][k]);

					sheet.addCell(label);

				}

			}

			workbook.write();

			workbook.close();

			/** ����excel */

			try {

				SmartUpload su = new SmartUpload();

				su.initialize(config, request, response);

				su.downloadFile(filename);

			} catch (IOException ex) {

				log.error("����excel�ļ������쳣:", ex);

			} catch (SmartUploadException ex) {

				log.error("����excel�ļ������쳣:", ex);

			} catch (ServletException ex) {

				log.error("����excel�ļ������쳣:", ex);

			}

		} catch (Exception ex) {

			log.error("����excel�ļ������쳣��", ex);

		}
	}

}
