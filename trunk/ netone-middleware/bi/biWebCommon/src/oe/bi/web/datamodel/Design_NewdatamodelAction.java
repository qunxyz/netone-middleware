//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.RootPath;
import oe.bi.common.FileHandler;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.datasource.DataModelDao;
import oe.bi.datasource.bus.ExtractDataset;
import oe.bi.datasource.obj.Datasource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;


/**
 * MyEclipse Struts Creation date: 07-19-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class Design_NewdatamodelAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {

		// TODO Auto-generated method stub
		DesigndatamodelForm form = (DesigndatamodelForm) actionform;

		ExtractDataset exDataSet = (ExtractDataset) BiEntry
				.fetchBi("extractDataset");

		FileHandler filehandler = (FileHandler) BiEntry.fetchBi("fileHandler");

		String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
				.getDatamodelpath();

		DataModelDao dmDao = (DataModelDao) BiEntry.fetchBi("dataModelDao");

		String flag = request.getParameter("flag");

		// 初始化数据
		String[] tablename = exDataSet.fetchDataSetKey(new Datasource());
		List tablenameList = new ArrayList(); // 数据集列表
		for (int i = 0; i < tablename.length; i++) {
			LabelValueBean lb = new LabelValueBean(tablename[i], tablename[i]);
			tablenameList.add(lb);
		}

		List fieldList = new ArrayList(); // 字段列表
		if (flag == null) {
			String[] target = exDataSet.fetchDataSetColumnKey(new Datasource(),
					tablename[0]);
			for (int i = 0; i < target.length; i++) {
				LabelValueBean lb = new LabelValueBean(target[i], target[i]);
				fieldList.add(lb);
			}
			request.getSession().setAttribute("fieldList", fieldList);

			String modelStr = filehandler.readFileStr(rootpath + "biModel.xml");
			
			form.setDatasets(modelStr.split("<DataSets>")[1].split("</DataSets>")[0].trim());
			
			form.setLinkers(modelStr.split("<Linkers>")[1].split("</Linkers>")[0].trim());
			
			form.setTargetColumns(modelStr.split("<TargetColumns>")[1].split("</TargetColumns>")[0].trim());
			
			form.setDimColumns(modelStr.split("<DimColumns>")[1].split("</DimColumns>")[0].trim());
			
			form.setOptimizes(modelStr.split("<Optimizes>")[1].split("</Optimizes>")[0].trim());
			
			try {
				DataModel dm=dmDao.fetchDataModel("biModel");
				form.setModelId("");
			    form.setModelname(dm.getModelname());	
			} catch (UnableLoadDataModel e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else if (flag.equals("changTable")) {
			//下拉数据集事件
			String[] target = exDataSet.fetchDataSetColumnKey(new Datasource(),
					form.getTablename());
			for (int i = 0; i < target.length; i++) {
				LabelValueBean lb = new LabelValueBean(target[i], target[i]);
				fieldList.add(lb);
			}
			request.getSession().setAttribute("fieldList", fieldList);
		} else if ("new".equals(flag)) {

			// 新建XML文件
			String modelId = String.valueOf(System.currentTimeMillis());

			String datamodelXmlStr = form.getDatamodelXmlStr();

			// 先保存文件,再通过此文件获取DataModel对象,更改此对象,再保存此对象
			filehandler.writeFile(rootpath + modelId + ".xml", datamodelXmlStr);
			try {
				DataModel datamodel = dmDao.fetchDataModel(modelId);
				datamodel.setModelid(modelId);
				dmDao.create(datamodel);
				request.setAttribute("booleanNewDataModel", "true");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("booleanNewDataModel", "false");
				e.printStackTrace();
			}

		}
		request.setAttribute("tablenameList", tablenameList);

		return mapping.getInputForward();
	}

}
