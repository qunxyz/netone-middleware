//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.datasource.bus.ExtractDataset;
import oe.bi.datasource.obj.Datasource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/** 
 * MyEclipse Struts
 * Creation date: 07-13-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class UnitcolumnAction extends Action {

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
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		 ExtractDataset exDataSet = (ExtractDataset) BiEntry.fetchBi("extractDataset");
		
		 Datasource ds=new Datasource();
	    //连接线的结束数据集节点名称  节点名称内容为：数据源ID + "." + 数据集ID(即表名)
	     String toDsName = request.getParameter("toDsName").toString();
	     
	     //连接线的开始数据集节点名称
	     String fromDsName = request.getParameter("fromDsName").toString();

	     String toDsId =toDsName.substring(toDsName.indexOf(".")+1,toDsName.length());
	     
	     String fromDsId =fromDsName.substring(fromDsName.indexOf(".")+1,fromDsName.length());
          
	    String[]  toDsIdField= exDataSet.fetchDataSetColumnKey(ds,toDsId);
	    String[]  fromDsIdField= exDataSet.fetchDataSetColumnKey(ds,fromDsId);
	     request.setAttribute("toDsName",toDsName);
	     request.setAttribute("fromDsName",fromDsName);
	     request.setAttribute("toDsIdField",toDsIdField);
	     request.setAttribute("fromDsIdField",fromDsIdField);
		return mapping.getInputForward();
	}

}

