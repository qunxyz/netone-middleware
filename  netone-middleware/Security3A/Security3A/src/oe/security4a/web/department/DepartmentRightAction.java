package oe.security4a.web.department;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.WebCache;
import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.Encryption;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class DepartmentRightAction extends Action {
	
	private static int _PRE_PAGE = 10;// 默认分页

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr.getOnlineUser(request);
			String code =oluser.getBelongto();
		
			
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			String id = request.getParameter("id");
			UmsProtectedobject upo = rmi.loadResourceById(id);
			request.setAttribute("upo", upo);
			
			
			PageInfo pageinfo = null;// 分页信息
			Clerk clerk = new Clerk();
			clerk.setDeptment(request.getParameter("id"));
			if (PageInfo.isPageEvent(request)) {
				pageinfo = new PageInfo(request, "userlist");
			} else {
				long dataNumber = rmi.queryObjectsNumberClerk(code, clerk, null);// 总记录数
				pageinfo = new PageInfo((int) dataNumber, _PRE_PAGE,
						request, "userlist");// 分页信息
			}
			// 根据分页的参数查询本页的相关数据
			List list = rmi.queryObjectsClerk(code, clerk, null,
					pageinfo.getPageStartIndex(), pageinfo
							.getPageEndIndex() + 1-pageinfo.getPageStartIndex());
			List<Clerk> newlist = new ArrayList<Clerk>();
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Clerk cl = (Clerk) iter.next();
				if (StringUtils.isNotEmpty(cl.getCompany())) {
					try {

						UmsProtectedobject upobject = rmi
								.loadResourceByNatural(cl.getCompany());
						if (upobject != null) {
							cl.setCompany(upobject.getName());
						}

						
					} catch (Exception e) {
						System.out.println(cl.getNaturalname()
								+ "-----------------");
						e.printStackTrace();
					}

				}
				// 将密码明文读到，写到密码中，用于在页面上确认是否为禁用帐户
				// 禁用帐户的密码为 9$9$
				String pass = cl.getPassword();
				String key = cupmRmi.fetchConfig("EncrypKey");
				cl.setPassword(Encryption
						.encry(pass, key, false));// 修改密码1
				//判断用户是否在线
				Object online=WebCache.getCache(cl.getNaturalname());
				
				if(online!=null){
					cl.setActive(true);
				}else{
					cl.setActive(false);
				}
				
				newlist.add(cl);
				
			}
			request.setAttribute("listinfo", newlist);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("departmentright");
	}
}