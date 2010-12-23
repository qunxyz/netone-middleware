//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel.dim;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.dataModel.obj.ext.TreeModel;
import oe.bi.datasource.bus.ExtractDataset;
import oe.bi.datasource.obj.Datasource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;


/** 
 * MyEclipse Struts
 * Creation date: 07-16-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DimtreeAction extends Action {

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

		// TODO Auto-generated method stub
	
	DimtreeForm form=(DimtreeForm)actionform;
	
	//钻取时间树
	List timetreeList=new ArrayList();
	String[][] timetree=TreeModel._TIME_TREE;
	for(int i=0;i<timetree.length;i++){
		LabelValueBean lb = new LabelValueBean(timetree[i][1], timetree[i][0]);
		timetreeList.add(lb);		
	}
	//数据集
	List treeDatasetList=new ArrayList();
	ExtractDataset exDataSet = (ExtractDataset) BiEntry.fetchBi("extractDataset");
	 Datasource ds=new Datasource();
	 String[] dataSet=exDataSet.fetchDataSetKey(ds);
	 for(int i=0;i<dataSet.length;i++){
			LabelValueBean lb = new LabelValueBean(dataSet[i], dataSet[i]);
			treeDatasetList.add(lb);		
		}
	
	 
	 //指标集
	 String[] target;
	 if(form.getTreedataset()==null){
		  target=exDataSet.fetchDataSetColumnKey(ds,dataSet[0]);
		
	 }else{
		 target=exDataSet.fetchDataSetColumnKey(ds,form.getTreedataset());
		
		 
	 }
	 
	request.setAttribute("timetreeList",timetreeList);
	request.setAttribute("treeDatasetList",treeDatasetList);
	request.setAttribute("target",target);
		return mapping.getInputForward();
	}

}

