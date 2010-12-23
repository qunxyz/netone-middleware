//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DataSet;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.dataModel.obj.TargetColumn;
import oe.bi.datasource.DataModelDao;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * MyEclipse Struts Creation date: 07-19-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class Design_ShowdatamodelAction extends Action {
	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String modelId = request.getParameter("datamodelid");
		DataModelDao dmDao = (DataModelDao) BiEntry.fetchBi("dataModelDao");
		
		
		try {
			DataModel datamodel = dmDao.fetchDataModel(modelId);

			// optimize结点
			StringBuffer optimizeSB = new StringBuffer();
			Map optimizeMap = datamodel.getOptimizes();
			for (Iterator it = optimizeMap.keySet().iterator(); it.hasNext();) {
				String name = it.next().toString();
				OptimizeTable optimize = (OptimizeTable) optimizeMap.get(name);
				optimizeSB.append("<font size=\"2\" title=\""+optimize.getId()+"\">"+optimize.getName() + "</font>&nbsp;&nbsp;&nbsp;");
			}


			// dataset结点
			StringBuffer datasetSB = new StringBuffer();
			Map datasetMap = datamodel.getDataSets();
			for (Iterator it = datasetMap.keySet().iterator(); it.hasNext();) {
				String name = it.next().toString();
				DataSet dataset = (DataSet) datasetMap.get(name);
				datasetSB.append("<font size=\"2\" title=\""+dataset.getId()+"\">"+dataset.getName() + "</font>&nbsp;&nbsp;&nbsp;");
			}
	

			// TargetColumn结点
			StringBuffer targetSB = new StringBuffer();
			Map targetMap = datamodel.getTargetColumns();
			for (Iterator it = targetMap.keySet().iterator(); it.hasNext();) {
				String name = it.next().toString();
				TargetColumn target = (TargetColumn) targetMap.get(name);
				targetSB.append("<font size=\"2\" title=\""+target.getId()+"\">"+target.getName() + "</font>&nbsp;&nbsp;&nbsp;");
			}
			
			
//			 DimColumn结点
			StringBuffer dimSB = new StringBuffer();
			Map dimMap= datamodel.getDimColumns();
			for (Iterator it = dimMap.keySet().iterator(); it.hasNext();) {
				dimSB.append("<td align=\"center\">");
				String name = it.next().toString();
				DimColumn dimcolumn = (DimColumn) dimMap.get(name);
				String extendattribute=dimcolumn.getExtendattribute();
				StringBuffer dimSBTemp = new StringBuffer();
				dimSBTemp.append(dimcolumn.getName());
				if(!extendattribute.equals("")){
				String[]  extendattributeField=extendattribute.split(";");
			
				
				for(int i=0;i<extendattributeField.length;i++){
					
					StringTokenizer stL = new StringTokenizer(extendattributeField[i], "[");
					if(stL.hasMoreTokens()){
						String table=stL.nextToken();
						String id=stL.nextToken().split(",")[0];
						dimSBTemp.append("<br><font color=red>↑</font>"+id+"</br>");
					}				
					
				}
				dimSB.append(dimSBTemp+"</td>");
				}
			}
			

			request.setAttribute("optimizeSB",optimizeSB);
			request.setAttribute("datasetSB",datasetSB);
			request.setAttribute("targetSB",targetSB);
			request.setAttribute("dimSB",dimSB);
			
		} catch (UnableLoadDataModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return mapping.getInputForward();
	}

}
