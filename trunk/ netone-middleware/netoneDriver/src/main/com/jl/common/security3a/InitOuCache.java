package com.jl.common.security3a;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.WebCache;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 初始化OU的CAHCE 可提高安全鉴权的速度
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class InitOuCache extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitOuCache() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		String rs[] = { "DEPT.DEPT", "BUSSWF.BUSSWF","BUSSENV.BUSSENV","APPFRAME.APPFRAME","BUSSFORM.BUSSFORM","APPFRAME.APPFRAME","FRAMEPG.FRAMEPG","CSSFILE.CSSFILE" };
		initcore(rs);
	}

	private void initcore(String rsinfo[]) {
		try {
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			for (int i = 0; i < rsinfo.length; i++) {
				UmsProtectedobject upo = new UmsProtectedobject();
				List list = rs.queryObjectProtectedObj(upo, null, 0, 100000,
						" and naturalname like '" + rsinfo[i] + "%'");
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					UmsProtectedobject object = (UmsProtectedobject) iterator
							.next();
					WebCache.setCache("NAME_OU:"
							+ object.getNaturalname().toUpperCase(), object
							.getOu(), null);
				}
				System.out.println("-- 安全资源缓存-建立资源" + rsinfo[i] + "的OU映射"
						+ list.size());
			}
			// 增加人员登录名与组织机构目录的映射
			WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
			String sql = "select usercode clientcode,naturalname naturalname from netone.t_cs_user b,netone.ums_protectedobject c where c.ID=b.SYSTEMID";
			List listCache = wfview.coreSqlview(sql);
			for (Iterator iterator = listCache.iterator(); iterator
					.hasNext();) {
				Map object = (Map) iterator.next();
				String clientid = (String) object.get("clientcode");
				String naturalname = (String) object.get("naturalname");
				WebCache.setCache("USERID_NATURALNAME:" + clientid,
						naturalname, null);
			}
			System.out.println("-- 安全资源缓存-客户登录名与隶属部门的Naturalname的映射 "
					+ listCache.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
