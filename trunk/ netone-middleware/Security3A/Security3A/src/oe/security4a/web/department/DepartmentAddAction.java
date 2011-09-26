package oe.security4a.web.department;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import oe.frame.web.util.WebTip;

public class DepartmentAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		String task = reqmap.getParameter("task");
		if ("addsave".equals(task)) {
			
			
			UmsProtectedobject upo = new UmsProtectedobject();
			upo
					.setNaturalname(reqmap.getParameter("naturalname")
							.toUpperCase());
			upo.setName(reqmap.getParameter("name"));
			String actionurl = reqmap.getParameter("actionurl");
			upo.setActionurl(actionurl);
			String objecttype = reqmap.getParameter("objecttype");
			upo.setObjecttype(objecttype);
			upo.setExtendattribute(reqmap.getParameter("extendattribute"));
			if (StringUtils.isEmpty(reqmap.getParameter("id"))) {
				upo.setParentdir("0");
			} else {
				upo.setParentdir(reqmap.getParameter("id"));
			}
			upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);
			if (StringUtils.isNotEmpty(reqmap.getParameter("appid"))) {
				upo.setAppid(new Long(reqmap.getParameter("appid")));
			}

			try {
				ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
				// �������Ƿ����
			
				UmsProtectedobject upoq = new UmsProtectedobject();
				upoq.setActionurl(upo.getActionurl());
				List list = new ArrayList();
				if (upo.getActionurl() != null
						&& !"".equals(upo.getActionurl())) {
					list = rmi.fetchResource(upoq, null, null);
				}
			
				// ���Ŀ¼�Ƿ�Ϸ�
				// Ѱ��Ӫ������
				UmsProtectedobject f = rmi.loadResourceById(upo.getParentdir());
// if ("jxs".equals(objecttype)) {
// boolean pass = findHeadCom(f.getNaturalname(), rmi,
// response);
// if(!pass) return null;
// }
// if("fxs".equals(objecttype)){
// boolean pass = findHeadSell(f.getNaturalname(), rmi, response);
// if(!pass) return null;
// }
				

				// if (list.size() <= 0) {

					Serializable ouid = rmi.addResource(upo);
					if (ouid != null) {
						RequestUtil.loadObjectParam(reqmap, f);
						request.setAttribute("CreateSuccess", "y");
						OperationLog.info(request, "�½�Ŀ¼", upo.getNaturalname()+upo.getName()+"�½�Ŀ¼�ɹ���",true);
						UmsProtectedobject upox = rmi.loadResourceById(ouid
								.toString());
						request.setAttribute("upo", upox);
						return mapping.findForward("editnode");
					} else {
						reqmap.setAlertMsg("�������Ѵ��ڣ�");
						request.setAttribute("CreateSuccess", "n");
						OperationLog.info(request, "�½�Ŀ¼", upo.getNaturalname()+upo.getName()+"�������Ѵ��ڣ��½�Ŀ¼ʧ�ܣ�",false);
					}

				// } else {
				// reqmap.setAlertMsg("�ñ����Ѵ��ڣ�");
				// request.setAttribute("CreateSuccess", "n");
				// OperationLog.error(request, "�½�Ŀ¼", "�������Ѵ��ڣ��½�Ŀ¼ʧ�ܣ�");
				// }
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("CreateSuccess", "n");
				OperationLog.info(request, "�½�Ŀ¼", e.getMessage(),false);
			}
		}
		return mapping.findForward("newnode");
	}

	// ���ϼ�Ӫ������
	private boolean findHeadCom(String name, ResourceRmi rmi,
			HttpServletResponse response) throws RemoteException {

		UmsProtectedobject headComObj = null;
		String headComName = null;
		String headCom = name;
		String names[] = headCom.split(".");
		for (int i = 0; i < names.length - 3; i++) { // ѭ������
			headComObj = rmi.loadResourceByNatural(headCom);
			// �ҵ�Ӫ���������͵Ĳ���
			if ("yxbm".equals(headComObj.getObjecttype())) {
				headComName = headComObj.getName();
				break; // �Ӻ���ǰ�ҵ������һ���������
			}
			// �����Ӻ���ǰ�ҿ���Ӫ������
			headCom = StringUtils.substringBeforeLast(headCom, ".");
		}
		if (headComName == null) {
			WebTip.htmlInfo("�޷������þ����̹�˾,ȱ���ϼ�Ӫ������", true, response);
			return false;
		}
		return true;

	}

	// ���������ϼ�������
	private boolean findHeadSell(String name, ResourceRmi rmi,
			HttpServletResponse response) throws RemoteException {

		UmsProtectedobject headComObj = null;
		String headComName = null;
		String headCom = name;
		String names[] = headCom.split(".");
		for (int i = 0; i < names.length - 3; i++) { // ѭ������
			headComObj = rmi.loadResourceByNatural(headCom);
			// �ҵ�Ӫ���������͵Ĳ���
			if ("jxs".equals(headComObj.getObjecttype())) {
				headComName = headComObj.getName();
				break; // �Ӻ���ǰ�ҵ������һ���������
			}
			// �����Ӻ���ǰ�ҿ���Ӫ������
			headCom = StringUtils.substringBeforeLast(headCom, ".");
		}
		if (headComName == null) {
			WebTip.htmlInfo("�޷������÷����̹�˾,ȱ���ϼ�������", true, response);
			return false;
		}
		return true;

	}
}