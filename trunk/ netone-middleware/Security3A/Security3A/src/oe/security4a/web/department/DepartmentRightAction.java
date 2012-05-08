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
	
	private static int _PRE_PAGE = 10;// Ĭ�Ϸ�ҳ

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
			
			
			PageInfo pageinfo = null;// ��ҳ��Ϣ
			Clerk clerk = new Clerk();
			clerk.setDeptment(request.getParameter("id"));
			if (PageInfo.isPageEvent(request)) {
				pageinfo = new PageInfo(request, "userlist");
			} else {
				long dataNumber = rmi.queryObjectsNumberClerk(code, clerk, null);// �ܼ�¼��
				pageinfo = new PageInfo((int) dataNumber, _PRE_PAGE,
						request, "userlist");// ��ҳ��Ϣ
			}
			// ���ݷ�ҳ�Ĳ�����ѯ��ҳ���������
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
				// ���������Ķ�����д�������У�������ҳ����ȷ���Ƿ�Ϊ�����ʻ�
				// �����ʻ�������Ϊ 9$9$
				String pass = cl.getPassword();
				String key = cupmRmi.fetchConfig("EncrypKey");
				cl.setPassword(Encryption
						.encry(pass, key, false));// �޸�����1
				//�ж��û��Ƿ�����
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