//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel.optimize;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
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
public class OptimizeReflectAction extends Action {

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
		ExtractDataset exDataSet = (ExtractDataset) BiEntry
		.fetchBi("extractDataset");

		String flag=request.getParameter("flag");
		String reflet=request.getParameter("reflet");
		String dataset=request.getParameter("dataset");
		
		//³õÊ¼»¯¹é²¢×Ö¶Î
		List optimizeList=new ArrayList();
		String[] optimizeTarget=exDataSet.fetchDataSetColumnKey(new Datasource(),dataset);
		for(int i=0;i<optimizeTarget.length;i++){
			LabelValueBean lb = new LabelValueBean(optimizeTarget[i], optimizeTarget[i]);
			optimizeList.add(lb);			
		}
		request.setAttribute("optimizeList",optimizeList);
		if("targetReflect".equals(flag)){
			List targetList=new ArrayList();
			String[] targetReflet=reflet.split(",");
			for(int i=0;i<targetReflet.length;i++){
				LabelValueBean lb = new LabelValueBean( targetReflet[1],targetReflet[0]);
				targetList.add(lb);			
			}
			request.setAttribute("targetList",targetList);
			return mapping.getInputForward();
		}else {
			
			
		}
		
		
		return null;
	}

}

