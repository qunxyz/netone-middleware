package oe.bi.web.etl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import oe.bi.BiEntry;
import oe.bi.datasource.DimensionAct;
import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;


/**
 * MyEclipse Struts Creation date: 06-21-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/etlMainAction" name="EtlMainForm"
 *                input="/bi/etl/etlmain.jsp" scope="request" validate="true"
 */
public class ETSMainAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables

	static Log log = LogFactory.getLog(ETSMainAction.class);

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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {

			/*******************************************************************
			 * ******* 不再有用 String dmid = request.getParameter("datamodelid");
			 * 
			 * if(dmid == null||dmid.equals("")){ dmid = "saleModel"; }
			 * 
			 * //获取datamodel DataModelDao dmdao = null; try { dmdao =
			 * (DataModelDao) BiEntry.fetchBi("dataModelDao"); } catch
			 * (Exception e) { e.printStackTrace(); }
			 * 
			 * DataModel dmobj = dmdao.fetchDataModel(dmid);
			 * 
			 * 
			 * String[][] tgfun = TargetFunction._TF_ALL ; ArrayList tgfunlist =
			 * new ArrayList(); for(int i=0 ; i<tgfun.length ; i++){
			 * LabelValueBean lv = new LabelValueBean();
			 * lv.setValue(tgfun[i][0]); lv.setLabel(tgfun[i][1]);
			 * tgfunlist.add(lv); }
			 ******************************************************************/

			WizardDao wd = (WizardDao) RmiEntry.iv("bihandle");
			String[][] dims = wd.listTree();
			request.setAttribute("dims", dims);

			// <!-- forecastdiv 供forecastselect页面使用 -->
			TargetFiltObj tfo = new TargetFiltObj();

			request.setAttribute("_CHOICE_TOP", tfo._CHOICE_TOP);
			request.setAttribute("_CHOICE_TOP_VALUE", tfo._CHOICE_TOP_VALUE);
			request.setAttribute("_CHOICE_ORDER", tfo._CHOICE_ORDER);
			request.setAttribute("_CHOICE_ALARM", tfo._CHOICE_ALARM);

			/*******************************************************************
			 * ******* 不再有用 request.setAttribute("tgfunlist",tgfunlist);
			 * request.setAttribute("tgmap",dmobj.getTargetColumns());
			 * request.setAttribute("dimmap",dmobj.getDimColumns());
			 * request.setAttribute("datamodelid",dmid);
			 ******************************************************************/

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
