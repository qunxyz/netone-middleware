//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datasource;

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
 * Creation date: 07-12-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DatasourceListAction extends Action {

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
		
		 StringBuffer buf = new StringBuffer();
		 
		 ExtractDataset exDataSet = (ExtractDataset) BiEntry.fetchBi("extractDataset");
		 
		 Datasource ds=new Datasource();
		 String[] str=exDataSet.fetchDataSetKey(ds);
		 if(str!=null){
		 for(int i=0;i<str.length;i++){			 
		 buf.append("<tr><td>&nbsp;&nbsp;");
		 buf.append("<a name="+str[i]+" href=\"#\" onclick=\"new trackFactory('dataNode','"+str[i]+"','"+str[i]+"','ds1');\">");
		 buf.append(str[i]+"</a></tr></td>");
		 }
		 }
		 
           request.setAttribute("str",buf);
		return mapping.getInputForward();
	}

}

