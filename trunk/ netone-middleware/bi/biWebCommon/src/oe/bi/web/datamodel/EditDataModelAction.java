//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.RootPath;
import oe.bi.common.FileHandler;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.datasource.DataModelDao;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * MyEclipse Struts Creation date: 07-11-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/editDataModel" name="dataModelForm" scope="request"
 *                validate="true"
 */
public class EditDataModelAction extends Action {

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

		String flag = request.getParameter("flag");

		DataModelForm form = (DataModelForm) actionform;

		String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
				.getDatamodelpath();

		FileHandler filehandler = (FileHandler) BiEntry.fetchBi("fileHandler");
		DataModelDao dmDao = (DataModelDao) BiEntry.fetchBi("dataModelDao");
		DigTreeBuilder dimtree = (DigTreeBuilder) BiEntry
				.fetchBi("digTreeBuilder");

		if ("init".equals(flag)) { // 初始化ID和DataModelText
			String id = request.getParameter("id");
			form.setDataModelId(id);

			String dataModelText = filehandler.readFileStr(rootpath + id
					+ ".xml");

			form.setDataModelText(dataModelText);

			request.setAttribute("editFlag", "true");
		} else {
			// 提交新增或修改
			String submitFlag = request.getParameter("submitFlag");

			String xmlStr = form.getDataModelText();
			String modelId = "";

			if ("newModel".equals(submitFlag)) { // 新建模型

				modelId = String.valueOf(System.currentTimeMillis());

			} else if ("editModel".equals(submitFlag)) { // 编辑更新模型

				modelId = form.getDataModelId();

			}
			// 先保存文件,再通过此文件获取DataModel对象,更改此对象,再保存此对象
			filehandler.writeFile(rootpath + modelId + ".xml", xmlStr);
			try {
				DataModel datamodel = dmDao.fetchDataModel(modelId);
				datamodel.setModelid(modelId);
				dmDao.create(datamodel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return mapping.getInputForward();
	}
}
