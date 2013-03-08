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

	// 根据分类查找产品信息
	public void onFindProductSetByPC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProductSetByPC(request, response);
	}

	// 查询分类树数据
	public void onFindCategoriesTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findCategoriesTree(request, response);
	}

	// 查询树
	public void onFindPartTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findPartTree(request, response);
	}

	// 查询区域与公司关联关系
	public void onFindPartAProductRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.savePartAProductRelation(request, response);
	}

	// 关联区域与公司页面
	public ActionForward onPartAProductMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		String forward = "/app/partAProductRelation.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 关联行政区划与公司
	public void onRelatePartAProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.relatePartAProduct(request, response);
	}

	// 同步产品数据
	public void syncProductData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.syncProductData(request, response);
	}

	// 导出网点产品情况表
	public ActionForward exportPartAndProductViewMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/partAndProductReport.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 导出网点产品情况表
	public void exportPartAndProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.exportPartAndProduct(request, response);
	}

	// HG网点选择
	public ActionForward clientMultiSelectMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/clientMultiSelect.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 产品选择
	public ActionForward productMultiSelectMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/productMultiSelect.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 产品列表根据父ID过滤产品数据
	public void findProductSetByParentId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProductSetByParentId(request, response);
	}

	// 同步用户
	public void syncK3User(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService service = (UserService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("userService");
		service.syncUserFromK3(request, response);
	}

	// 同步部门
	public void syncK3Dept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.syncDeptFromK3(request, response);
	}

	// 根据编码获取职员信息
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

	// 查询销售透视图页面
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
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 查询销售透视图
	public void querySellPivotTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.querySellPivotTable(request, response);
	}

	// 查询网点管理
	public ActionForward queryNetpointView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("beginDate", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		String forward = "/app/netpoint.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 显示网点扩展信息
	public ActionForward queryNetpointExtendInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryNetpointExtendInfo(request, response);
		String forward = "/app/netpointExtend.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 查询网点管理
	public void queryNetpoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryNetPointManage(request, response);
	}

	// 查询网点管理2
	public ActionForward queryNetpointView2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/netpoint2.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 查询网点管理2
	public void queryNetpoint2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryNetPointManage2(request, response);
	}

	// 评分
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
		request.setAttribute("curTime", tt.getNowTime("yyyy-MM-dd"));// 当天日期
		request.setAttribute("curWeeks", tt.getCurWeeks());// 当前周

		this.queryTJ(request);// 调用查询条件展示

		String forward = "/xreport/xreportLH.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	/** *****------------------------航港手机------------------------------------******* */
	// 航港 手机理货分析统计表
	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryHGReport(request, response);
	}

	// 公用参数
	private void queryTJ(HttpServletRequest request) throws Exception {
		// 网点名称
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT DISTINCT lsh, column5 wdmc FROM dyform.DY_961338295639576 ORDER BY lsh");
		List list = (List) getCommonDAO().select("App.selectDySql",
				sb.toString());
		request.setAttribute("list", list);
		// 理货员
		StringBuffer sb1 = new StringBuffer();
		sb1
				.append(" SELECT DISTINCT t.PARTICIPANT ywybm,IFNULL(t1.NAME,'') ywyname ");
		sb1.append(" FROM dyform.DY_211340244752515 t  ");
		sb1
				.append(" LEFT JOIN netone.t_cs_user t1 ON t.PARTICIPANT = t1.USERCODE ");
		sb1.append(" ORDER BY  t.PARTICIPANT ");
		List list1 = (List) getCommonDAO().select("App.selectDySql",
				sb1.toString());
		request.setAttribute("ywy", list1);
		// 品牌
		StringBuffer sb2 = new StringBuffer();
		sb2
				.append("SELECT column3 px,column4 pxm FROM  dyform.DY_171345119981583");
		List list2 = (List) getCommonDAO().select("App.selectDySql",
				sb2.toString());
		request.setAttribute("px", list2);

	}

	// 查询网点管理
	public ActionForward queryNetPointView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/netpointManage2.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 查询网点管理
	public void queryNetPoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryNetPoint(request, response);
	}

	// 查询网点管理Grid
	public ActionForward queryNetPointGridView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/netpointManageGrid.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 查询网点管理Grid
	public void queryNetPointGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryNetPointGrid(request, response);
	}

	// 理货频率配置主界面
	public ActionForward onAppOBRelationMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		String forward = "/app/appOBRelation.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 理货员树级展示
	public void onFindProTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProTree(request, response);
	}

	// 根据品牌查询相应网点、频率、理货员等信息
	public void onFindOutletSet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findOutletSetByLshId(request, response);
	}

	// 理货频率配置新增
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lshId = request.getParameter("lshId");
		request.setAttribute("lshId", lshId);

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		String forward = "/app/editOBRelation.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 根据品牌编号 加载未选择网点信息
	public void onLoadOutletsInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.loadOutletsInfo(request, response);
	}

	// 保存选择结果
	public void onSaveOutlets(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.saveOutlets(request, response);
	}

	// 更新频率信息
	public void onUpdateTimes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.updateTimes(request, response);
	}

	// 删除网点频率配置信息 (批量)
	public void onDeleteOT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.deleteOutlets(request, response);
	}

	// 导出excel 频率配置
	public void onExportAppOBR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.exportAppOBR(request, response);
	}

	// 保存
	public void onSaveOrUpdateStoreJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.saveOrUpdateStoreJson(request, response);
	}

	// 删除
	public void onDeleteStoreJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.deleteStoreJson(request, response);
	}

	// 查询
	public void onQueryStoreJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryStoreJson(request, response);
	}

	// 加载
	public void onLoadStoreJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.loadStoreJson(request, response);
	}

	// 查询销售或销售发票列表
	public ActionForward onQueryIcsaleMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("limit", 20);
		String forward = "/app/saleMain.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 查询销售或销售发票列表
	public void onQueryIcsale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.queryIcsale(request, response);
	}

	// 生成发票
	public void onSaveIcsale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.saveIcsale(request, response);
	}

	// 一键生成发票
	public void onSaveAllIcsale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.saveAllIcsale(request, response);
	}
	
	// 查询导出销售信息
	public ActionForward onExportsellMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/exportsellmain.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}
	
	//导出
	public void exportsellinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.exportsellinfo(request, response);
	}

}
