//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel.dim;

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
 * MyEclipse Struts
 * Creation date: 07-15-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DimeditAction extends Action {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm actionform,
		HttpServletRequest request,
		HttpServletResponse response) {

		DimeditForm form=(DimeditForm)actionform;
		
		ExtractDataset exDataSet = (ExtractDataset) BiEntry
		.fetchBi("extractDataset");
 
String changeflag = request.getParameter("changeflag");//�Ƿ����������¼�

String flag = request.getParameter("flag");//�ж����½�ָ�껹�Ǳ༭ָ��

List targetList = new ArrayList();

//��ʼ�����ݼ�
List dataSetList = new ArrayList();
String dataSetStr = request.getParameter("datasetAll");
String[] dataSetField = dataSetStr.split(",");

for (int i = 0; i < dataSetField.length; i++) {
	LabelValueBean lb = new LabelValueBean(dataSetField[i], dataSetField[i]);
	dataSetList.add(lb);
}
request.setAttribute("dataSetList", dataSetList);


//��ʼ��ά������
List sqlTypeList = new ArrayList();
String[][] sqlTypeField =SqlTypes._DIM_TYPE_ALL;

for (int i = 0; i < sqlTypeField.length; i++) {
	LabelValueBean lb = new LabelValueBean(sqlTypeField[i][1],sqlTypeField[i][0]);
	sqlTypeList.add(lb);
}
request.setAttribute("sqlTypeList", sqlTypeList);

//��ʼ��sqltypeList

 List sqltypeList=new ArrayList();
 request.setAttribute("sqltypeList", sqltypeList);
if (changeflag == null) {  //���û�������¼�,��ָ��Ĭ��ѡ���һ�����ݼ�
	

	
	if ("new".equals(flag)) {
		form.setDatasetsel(dataSetField[0]);
		String[] target = exDataSet.fetchDataSetColumnKey(
				new Datasource(), dataSetField[0]);

		for (int i = 0; i < target.length; i++) {
			LabelValueBean lb = new LabelValueBean(target[i], target[i]);
			targetList.add(lb);
		}
		form.setDimname("");
		form.setDesc("");
	}else if("edit".equals(flag)){
		String dataset=request.getParameter("dataset");
		String[] target = exDataSet.fetchDataSetColumnKey(
				new Datasource(), dataset);

		for (int i = 0; i < target.length; i++) {
			LabelValueBean lb = new LabelValueBean(target[i], target[i]);
			targetList.add(lb);
		}
	}
} else if (changeflag.equals("change")) {  //�����¼�
	String[] target = exDataSet.fetchDataSetColumnKey(new Datasource(),
			form.getDatasetsel());

	for (int i = 0; i < target.length; i++) {
		LabelValueBean lb = new LabelValueBean(target[i], target[i]);
		targetList.add(lb);
	}
	request.setAttribute("change", "change");
}

request.setAttribute("targetList", targetList);
// TODO Auto-generated method stub

if ("edit".equals(flag))
	return mapping.findForward("edit");
return mapping.getInputForward();
	}

}

