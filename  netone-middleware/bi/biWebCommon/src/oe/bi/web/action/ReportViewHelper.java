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
 * 模型分析视图辅助类
 * 实现：导出excel
 * @author zhengjr
 *
 */

public class ReportViewHelper {
	// -------------------------------------------------参数定义

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
			// 创建工作簿
			workbook = Workbook.createWorkbook(new File(filename));

			// 创建工作表
			WritableSheet sheet = workbook.createSheet("datalist", 0);

			response.setContentType("application/vnd.ms-excel");

			response.setHeader("Content-disposition",
					"attachment; filename=dataexcel");

			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 14, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BROWN);

			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
					wfc);

			// 维度列名
			String[] dimName = viewModel.getDimensionname();

			// 指标列名
			String[] targetName = viewModel.getTargetname();

			// 维度值
			String[][] dimValue = viewModel.getDimensionvalue();

			// 指标值
			double[][] targetValue = viewModel.getTargetvalue();

			/** 添加标题列--维度标题 */
			for (int i = 0; i < dimName.length; i++) {

				jxl.write.Label label = new jxl.write.Label(i, 0, dimName[i]
						.toString(), wcfFC);

				sheet.addCell(label);

			}

			/** 添加标题列--指标标题 */
			for (int j = 0; j < targetName.length; j++) {

				jxl.write.Label label = new jxl.write.Label(j + dimName.length,
						0, targetName[j].toString(), wcfFC);

				sheet.addCell(label);
			}

			/** 添加数据列 */
			for (int i = 0; i < dimValue.length; i++) {

				// ----- 打印每行中的维度列的值 START------
				for (int j = 0; j < dimName.length; j++) {

					jxl.write.Label label = new jxl.write.Label(j, i + 1,
							dimValue[i][j]);

					sheet.addCell(label);

				}

				// ----- 打印每行中的指标列的值 START------
				for (int k = 0; k < targetName.length; k++) {

					jxl.write.Label label = new jxl.write.Label(k
							+ dimName.length, i + 1, "" + targetValue[i][k]);

					sheet.addCell(label);

				}

			}

			workbook.write();

			workbook.close();

			/** 下载excel */

			try {

				SmartUpload su = new SmartUpload();

				su.initialize(config, request, response);

				su.downloadFile(filename);

			} catch (IOException ex) {

				log.error("下载excel文件发生异常:", ex);

			} catch (SmartUploadException ex) {

				log.error("下载excel文件发生异常:", ex);

			} catch (ServletException ex) {

				log.error("下载excel文件发生异常:", ex);

			}

		} catch (Exception ex) {

			log.error("生成excel文件发生异常：", ex);

		}
	}

}
