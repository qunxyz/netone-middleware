package com.jl.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.SpringBeanUtilHg;
import com.jl.common.TimeUtil;
import com.jl.common.TimesTool;
import com.jl.dao.CommonDAO;
import com.jl.service.AppService;
import com.jl.service.DepartmentService;
import com.jl.service.UserService;

public class AppAction extends AbstractAction {

	private CommonDAO getHgDAO() {
		CommonDAO dao = (CommonDAO) SpringBeanUtilHg.getInstance().getBean(
				"commonDAO");
		return dao;
	}

	// ���ݷ�����Ҳ�Ʒ��Ϣ
	public void onFindProductSetByPC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProductSetByPC(request, response);
	}

	// ��ѯ����������
	public void onFindCategoriesTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findCategoriesTree(request, response);
	}

	// ��ѯ��
	public void onFindPartTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findPartTree(request, response);
	}

	// ��ѯ�����빫˾������ϵ
	public void onFindPartAProductRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.savePartAProductRelation(request, response);
	}

	// ���������빫˾ҳ��
	public ActionForward onPartAProductMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");// Ӧ�÷�����Ŀ¼
		String forward = "/app/partAProductRelation.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// �������������빫˾
	public void onRelatePartAProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.relatePartAProduct(request, response);
	}

	// ͬ����Ʒ����
	public void syncProductData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.syncProductData(request, response);
	}

	// ���������Ʒ�����
	public ActionForward exportPartAndProductViewMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/partAndProductReport.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ���������Ʒ�����
	public void exportPartAndProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.exportPartAndProduct(request, response);
	}

	// HG����ѡ��
	public ActionForward clientMultiSelectMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/clientMultiSelect.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��Ʒѡ��
	public ActionForward productMultiSelectMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/productMultiSelect.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��Ʒ�б����ݸ�ID���˲�Ʒ����
	public void findProductSetByParentId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProductSetByParentId(request, response);
	}

	// ͬ���û�
	public void syncK3User(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService service = (UserService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("userService");
		service.syncUserFromK3(request, response);
	}

	// ͬ������
	public void syncK3Dept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.syncDeptFromK3(request, response);
	}

	// ���ݱ����ȡְԱ��Ϣ
	public void getEmpByNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String usercode = request.getParameter("usercode");
		Map emp = (Map) getHgDAO().findForObject("HG.selectEmpByNumber",
				usercode);
		Integer FDepartmentID = null;
		if (emp != null) {
			FDepartmentID = (Integer) emp.get("FDepartmentID");
		}
		JSONObject json = new JSONObject();
		json = JSONObject.fromObject(emp);
		super.writeJsonStr(response, json.toString());
	}

	// ��ѯ����͸��ͼҳ��
	public ActionForward querySellPivotTableView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("beginDate", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");
		request.setAttribute("endDate", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		String forward = "/app/querySellPivotTableView.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��ѯ����͸��ͼ
	public void querySellPivotTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.querySellPivotTable(request, response);
	}

	// ��ѯ�������
	public ActionForward queryNetpointView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/netpoint.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��ѯ�������
	public void queryNetpoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryNetPointManage(request, response);
	}

	// ����
	public void updateNetPoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.updateNetPoint(request, response);
	}

	
	
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimesTool tt = new TimesTool();  
		request.setAttribute("beginTime", tt.getMondayOFWeek());
		request.setAttribute("endTime", tt.getCurrentWeekday());
		request.setAttribute("curTime", tt.getNowTime("yyyy-MM-dd"));//��������
		request.setAttribute("curWeeks", tt.getCurWeeks());//��ǰ��
		
		this.queryTJ(request);//���ò�ѯ����չʾ

		String forward = "/xreport/xreportLH.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	/*******------------------------�����ֻ�------------------------------------********/
	// ���� �ֻ���������ͳ�Ʊ�
	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
		.getRequiredWebApplicationContext(servlet.getServletContext())
		.getBean("appService");
		service.queryHGReport(request, response);
	}
	
	//���ò���
	private  void queryTJ(HttpServletRequest request) throws Exception{
		// ��������
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT lsh, column5 wdmc FROM dyform.DY_961338295639576 ORDER BY lsh");
		List list = (List) getCommonDAO().select("App.selectDySql", sb.toString());
		request.setAttribute("list", list);
		//����Ա
		StringBuffer sb1 = new StringBuffer();
		sb1.append(" SELECT DISTINCT t.PARTICIPANT ywybm,IFNULL(t1.NAME,'') ywyname ");
		sb1.append(" FROM dyform.DY_211340244752515 t  ");
		sb1.append(" LEFT JOIN netone.t_cs_user t1 ON t.PARTICIPANT = t1.USERCODE ");
		sb1.append(" ORDER BY  t.PARTICIPANT ");
		List list1 =(List) getCommonDAO().select("App.selectDySql",sb1.toString());
		request.setAttribute("ywy", list1);
		//Ʒ��
		StringBuffer sb2 = new StringBuffer();
		sb2.append("SELECT column3 px,column4 pxm FROM  dyform.DY_171345119981583");
		List list2 = (List) getCommonDAO().select("App.selectDySql",sb2.toString());
		request.setAttribute("px", list2);
		
	}
}