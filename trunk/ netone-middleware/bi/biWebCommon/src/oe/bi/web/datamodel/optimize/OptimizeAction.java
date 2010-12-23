//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel.optimize;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.dataModel.obj.ext.SqlTypes;
import oe.bi.datasource.bus.ExtractDataset;
import oe.bi.datasource.obj.Datasource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;


/**
 * MyEclipse Struts Creation date: 07-16-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class OptimizeAction extends Action {

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

		String flag = request.getParameter("flag");// 判断是新建指标还是编辑指标
          OptimizeForm form=(OptimizeForm)actionform;
		// 初始化数据集
		List dataSetList = new ArrayList();
		String dataSetStr = request.getParameter("datasetAll");
		String[] dataSetField = dataSetStr.split(",");

		for (int i = 0; i < dataSetField.length; i++) {
			LabelValueBean lb = new LabelValueBean(dataSetField[i],
					dataSetField[i]);
			dataSetList.add(lb);
		}
		request.setAttribute("dataSetList", dataSetList);

		// TODO Auto-generated method stub

		if ("edit".equals(flag)){
			String dataset=request.getParameter("dataset");
			form.setDatasetsel(dataset);
			return mapping.findForward("edit");
		}
		return mapping.getInputForward();
	}

}
