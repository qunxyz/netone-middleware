package oe.security4a.web.human;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.res.doc.ExcelHandler;
import oe.frame.orm.util.IdServer;
import oe.frame.web.WebCache;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.midware.doc.excel.ExcelHandlerImp;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.MultiDimData;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.Encryption;
import oe.security4a.web.system.XmlObjListAndMDD;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HumanIndexAction extends Action {

	private static int _PRE_PAGE = 10;// Ĭ�Ϸ�ҳ

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		Clerk clerk = new Clerk();
		clerk.setDeptment(reqmap.getParameter("id"));

		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();

		Map<String, String> comparisonKey = new LinkedHashMap<String, String>();
		String loginName = oluser.getLoginname();
		// �ǹ���Աֻ�ܲ쿴�Լ��������Ա
		if (!"adminx".equals(loginName)) {
			try {
				ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
				Clerk user = rmi.loadClerk(code, loginName);

				String extend = user.getExtendattribute();
				comparisonKey.put("extendattribute", "like");
				clerk.setExtendattribute(extend + "%");
				clerk.setOfficeNO(code);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ��ҳ��Ϣ
		PageInfo pageinfo = null;
		if ("output".equals(reqmap.getParameter("task"))) {
			try {
				ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
				if (StringUtils.isNotEmpty(reqmap.getParameter("sname"))) {
					comparisonKey.put("name", "like");
					clerk.setName("%" + reqmap.getParameter("sname").trim()
							+ "%");
				}
				if (StringUtils.isNotEmpty(reqmap.getParameter("sdepartment2"))) {
					clerk.setDeptment(reqmap.getParameter("sdepartment2"));
				}
				List clerklist = rmi.fetchClerk(code, clerk, comparisonKey,
						null);
				response.setContentType("text/html; charset=GBK");
				response.setContentType("application/x-msdownload");

				response.setHeader("Content-Disposition",
						"attachment; filename=" + IdServer.uuid() + ".xls");
				MultiDimData view = new MultiDimData();
				Map<String, String> map1 = new LinkedHashMap<String, String>();
				map1.put("name", "����");
				map1.put("naturalname", "����ƴ��");
				map1.put("description", "�ʺ�");
				map1.put("phoneNO", "�ƶ��绰");
				map1.put("email", "�ʼ�");
				map1.put("province", "��Ա����");
				map1.put("deptname", "����");
				map1.put("company", "ְ��");
				map1.put("remark", "��ע");
				view.setDataColumnName(map1);
				Map<String, String> map2 = new LinkedHashMap<String, String>();
				map2.put("name", "string");
				map2.put("naturalname", "string");
				map2.put("description", "string");
				map2.put("phoneNO", "string");
				map2.put("email", "string");
				map2.put("province", "string");
				map2.put("deptname", "string");
				map2.put("company", "string");
				map2.put("remark", "string");
				view.setDataColumnType(map2);
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (Iterator iter = clerklist.iterator(); iter.hasNext();) {
					Clerk user = (Clerk) iter.next();
					Map<String, String> mapelement = new LinkedHashMap<String, String>();
					for (Iterator iterx = map1.keySet().iterator(); iterx
							.hasNext();) {
						String elementName = (String) iterx.next();
						if ("deptname".equals(elementName)) {
							mapelement.put("deptname", user.getDeptment());
						} else {
							mapelement.put(elementName, (String) BeanUtils
									.getProperty(user, elementName));
						}
					}
					list.add(mapelement);
				}
				view.setDatavalue(list);
				List xmlobjlist = XmlObjListAndMDD.toXmlObjList(view);
				List value = view.getDatavalue();
				ExcelHandler excelHandler = new ExcelHandlerImp();
				excelHandler.writeExcel(value, xmlobjlist, null, "human",
						response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
				OperationLog.error(request, "�����û�", e.getMessage());
			}
			return null;
		} else {
			try {
				ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
				if (StringUtils.isNotEmpty(reqmap.getParameter("sname"))) {
					comparisonKey.put("name", "like");
					clerk.setName("%" + reqmap.getParameter("sname").trim()
							+ "%");
				}
				if (StringUtils.isNotEmpty(reqmap.getParameter("sdepartment2"))) {
					clerk.setDeptment(reqmap.getParameter("sdepartment2"));
				}
				if (PageInfo.isPageEvent(request)) {
					pageinfo = new PageInfo(request, "userlist");
				} else {
					long dataNumber = rmi.queryObjectsNumberClerk(code, clerk,
							comparisonKey);// �ܼ�¼��
					pageinfo = new PageInfo((int) dataNumber, _PRE_PAGE,
							request, "userlist");// ��ҳ��Ϣ
				}
				// ���ݷ�ҳ�Ĳ�����ѯ��ҳ���������
				List list = rmi.queryObjectsClerk(code, clerk, comparisonKey,
						pageinfo.getPageStartIndex(), pageinfo
								.getPageEndIndex() + 1);
				List<Clerk> newlist = new ArrayList<Clerk>();
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Clerk cl = (Clerk) iter.next();
					if (StringUtils.isNotEmpty(cl.getCompany())) {
						try {

							UmsProtectedobject upo = rmi
									.loadResourceByNatural(cl.getCompany());
							if (upo != null) {
								cl.setCompany(upo.getName());
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
					if (pass == null) {
						pass = "00";
					}
					cl.setPassword(Encryption.encry(pass, key, false));// �޸�����1
					//�ж��û��Ƿ�����
					Object online=WebCache.getCache(clerk.getNaturalname());
					if(online!=null){
						cl.setActive(true);
					}else{
						cl.setActive(false);
					}
					
					newlist.add(cl);
					//newlist.add(cl);
				}
				request.setAttribute("listinfo", newlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mapping.getInputForward();
	}
}
