package oe.cav.web.data;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.cav.bean.logic.bus.BusExtendInfo;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.BusPermission;
import oe.cav.web.data.dyform.utils.DealwithButton;
import oe.cav.web.data.dyform.utils.ListView;
import oe.cav.web.util.BuildFormStaticPage;
import oe.frame.orm.util.IdServer;
import oe.frame.web.util.WebTip;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.sso.Security;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.BaseAction;
import com.rongji.webframework.ui.Control;

public class BusActionImpl extends BaseAction {

	static String QUERY_TIP = "quetip";

	static String QUERY_SUPER_CONDITION = "superCondition";

	DyFormDesignService dys = null;

	DyFormService dysc = null;

	static String _ROLE_ADMIN_SYS = "BUSSENV.BUSSENV.SECURITY.SYSROLE.ADMINX";

	public BusActionImpl() {
		if (dys == null) {
			try {
				dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (dysc == null) {
			try {
				dysc = (DyFormService) RmiEntry.iv("dyhandle");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected boolean validateLogon() {
		return false;
	}

	public String onSublist(ActionEvent ae) throws Exception {

		Security ser = new Security(ae.getRequest());

		BusForm form = (BusForm) ae.getForm();
		String fatherlsh = form.getLsh();
		if (fatherlsh == null) {
			ae.getMessageDialog().info("�Ƿ������ӱ�");
			return onList(ae);
		}
		TCsForm tCsForm = dysc.loadForm(form.getFormcode());
		String subFormcode = form.getSubform()[0];
		if (subFormcode == null || "".equals(subFormcode)) {
			ae.getMessageDialog().info("û����һ����");
			TCsBus bus = dysc.loadData(fatherlsh, subFormcode);
			form.setLsh(bus.getFatherlsh());
			return onList(ae);
		}

		// ���ɶ�̬���б�ͷ��Ϣ
		String selectcolumn = ae.getParameter("selectcolumn");
		boolean havekey = false;
		if (selectcolumn != null && selectcolumn.length() > 0) {
			havekey = dylistTitleManual(subFormcode, ae, selectcolumn
					.split(","));
		} else {
			havekey = dylistTitle(subFormcode, ae);
		}
		if (!havekey) {
			return null;
		}

		// ����ť��ʽ
		dealwithButton(subFormcode, ae);

		String orderinfo = tCsForm.getOrderinfo();
		ListView searchObj = setSubListInfo(ae, orderinfo, fatherlsh,
				subFormcode, false);
		ae.setAttribute("en", searchObj);
		form.setFormcode(subFormcode);
		form.setFatherlsh(fatherlsh);

		List list = createSubInfo(subFormcode, ae);

		String mode = ae.getParameter("mode");

		searchObj.setSubForm(list);

		if ("manage".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormManage.class);
			return "listsmall";
		} else if ("useview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listsmall";
		} else if ("onlyview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listverysmall";
		} else {
			this.bindEvent(ae, "enList", BusTurnpage.class);
			return "list";
		}
	}

	public String onSublistShare(ActionEvent ae) throws Exception {

		BusForm form = (BusForm) ae.getForm();
		String fatherlsh = form.getLsh();
		if (fatherlsh == null) {
			ae.getMessageDialog().info("�Ƿ������ӱ�");
			return onList(ae);
		}
		TCsForm tCsForm = dysc.loadForm(form.getFormcode());
		String subFormcode = form.getSubform()[0];
		if (subFormcode == null || "".equals(subFormcode)) {
			ae.getMessageDialog().info("û����һ����");
			TCsBus bus = dysc.loadData(fatherlsh, subFormcode);
			form.setLsh(bus.getFatherlsh());
			return onList(ae);
		}

		// ���ɶ�̬���б�ͷ��Ϣ
		String selectcolumn = ae.getParameter("selectcolumn");
		boolean havekey = false;
		if (selectcolumn != null && selectcolumn.length() > 0) {
			havekey = dylistTitleManual(subFormcode, ae, selectcolumn
					.split(","));
		} else {
			havekey = dylistTitle(subFormcode, ae);
		}
		if (!havekey) {
			return null;
		}
		// ����ť��ʽ
		dealwithButton(subFormcode, ae);

		String orderinfo = tCsForm.getOrderinfo();
		ListView searchObj = setSubListInfo(ae, orderinfo, fatherlsh,
				subFormcode, true);
		ae.setAttribute("en", searchObj);
		form.setFormcode(subFormcode);
		form.setFatherlsh(fatherlsh);

		List list = createSubInfo(subFormcode, ae);

		String mode = ae.getParameter("mode");

		searchObj.setSubForm(list);

		if ("manage".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormManage.class);
			return "listsmall";
		} else if ("useview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listsmall";
		} else if ("onlyview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listverysmall";
		} else {
			this.bindEvent(ae, "enList", BusTurnpage.class);
			return "list";
		}
	}

	private ListView setSubListInfo(ActionEvent ae, String orderinfo,
			String fatherlsh, String subformcode, boolean ignoremen) {
		TCsBus busForm = new TCsBus();
		// ���ò�ѯĬ��ֵ
		busForm.setFormcode(subformcode);
		busForm.setFatherlsh(fatherlsh);
		busForm.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);

		Security ser = new Security(ae.getRequest());

		String usercode = ser.getUserLoginName();
		if (!usercode.equals("adminx")) {// �ǳ����û�
			boolean sysadmin = ser.checkUserPermission(_ROLE_ADMIN_SYS, "1");
			if (!sysadmin) {// û�й���Ա��ɫȨ��(����ԱȨ�޵Ķ�DEPT.DEPT���Ÿ���Ȩ�޵�)
				busForm.setParticipant(busForm.getParticipant());
			}
		}

		// ��ѯ��������
		ListView search = new ListView();
		search.setSearchobj(busForm);
		if ("1".equals(orderinfo)) {
			search.setCondition(" order by created desc");
			search.setContextpath(ae.getRequest().getContextPath());
			search.setFormcode(subformcode);
		}

		return search;
	}

	/**
	 * ע��: ignoremen���� ��request�еĻ�ñ����Ƿ��û�ֻ�ܲ鿴�޸��Լ�����Ϣ ignoremen=yes �������еļ�¼���⹲��
	 */
	public String onList(ActionEvent ae) throws Exception {

		Security ser = new Security(ae.getRequest());

		String querytip = ae.getParameter(QUERY_TIP);
		BusForm form = (BusForm) ae.getForm();
		String lsh = form.getLsh();
		if (lsh != null) {// ��һ�δ�List��ʱ�� fatherlsh ֵ��
			// lsh��,֮���ҳʱ,fatherlshֵ��fatherlsh��,����ǳ������ʷ��������,���ͳһ
			form.setFatherlsh(form.getLsh());
		}

		String formcode = form.getFormcode();
		if (formcode == null || formcode.equals("")) {
			return null;
		}

		// ���ɶ�̬���б�ͷ��Ϣ
		String selectcolumn = ae.getParameter("selectcolumn");
		boolean havekey = false;
		if (selectcolumn != null && selectcolumn.length() > 0) {
			havekey = dylistTitleManual(formcode, ae, selectcolumn.split(","));
		} else {
			havekey = dylistTitle(formcode, ae);
		}

		if (!havekey) {
			return null;
		}
		// ����ť
		dealwithButton(formcode, ae);

		// ������Ա���ˣ�ֻ�ܲ����Լ�������
		String man = ser.getUserLoginName();
		if (!man.equals("adminx")) {// ����Ա����
			boolean sysadmin = ser.checkUserPermission(_ROLE_ADMIN_SYS, "1");
			if (!sysadmin) {// û�й���Ա��ɫȨ��(����ԱȨ�޵Ķ�DEPT.DEPT���Ÿ���Ȩ�޵�)
				form.setParticipant(man);
			}

		}

		// ҵ���ѯ����
		ListView search = null;
		if ("true".equals(querytip)) {
			search = setQueryInfo(form);
		} else {
			search = setListInfo(form, ae.getRequest());
		}
		search.setFormcode(formcode);
		search.setContextpath(ae.getRequest().getContextPath());
		// search.setCondition(" order by extendattribute");
		ae.setAttribute("en", search);

		List list = createSubInfo(formcode, ae);
		String mode = ae.getParameter("mode");

		search.setSubForm(list);
		if ("manage".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormManage.class);
			return "listsmall";
		} else if ("useview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listsmall";
		} else if ("onlyview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listverysmall";
		} else {
			this.bindEvent(ae, "enList", BusTurnpage.class);
			return "list";
		}
	}

	/**
	 * ע��: ignoremen���� ��request�еĻ�ñ����Ƿ��û�ֻ�ܲ鿴�޸��Լ�����Ϣ ignoremen=yes �������еļ�¼���⹲��
	 */
	public String onListShare(ActionEvent ae) throws Exception {

		String querytip = ae.getParameter(QUERY_TIP);
		BusForm form = (BusForm) ae.getForm();
		String lsh = form.getLsh();
		if (lsh != null) {// ��һ�δ�List��ʱ�� fatherlsh ֵ��
			// lsh��,֮���ҳʱ,fatherlshֵ��fatherlsh��,����ǳ������ʷ��������,���ͳһ
			form.setFatherlsh(form.getLsh());
		}

		String formcode = form.getFormcode();
		if (formcode == null || formcode.equals("")) {
			return null;
		}

		// ���ɶ�̬���б�ͷ��Ϣ
		String selectcolumn = ae.getParameter("selectcolumn");
		boolean havekey = false;
		if (selectcolumn != null && selectcolumn.length() > 0) {
			havekey = dylistTitleManual(formcode, ae, selectcolumn.split(","));
		} else {
			havekey = dylistTitle(formcode, ae);
		}

		if (!havekey) {
			return null;
		}
		// ����ť
		dealwithButton(formcode, ae);

		// ҵ���ѯ����
		ListView search = null;
		if ("true".equals(querytip)) {
			search = setQueryInfo(form);
		} else {
			search = setListInfo(form, ae.getRequest());
		}
		search.setFormcode(formcode);
		search.setContextpath(ae.getRequest().getContextPath());
		// search.setCondition(" order by extendattribute");
		ae.setAttribute("en", search);

		List list = createSubInfo(formcode, ae);
		String mode = ae.getParameter("mode");

		search.setSubForm(list);
		if ("manage".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormManage.class);
			return "listsmall";
		} else if ("useview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listsmall";
		} else if ("onlyview".equals(mode)) {
			this.bindEvent(ae, "enList", TurnpageFormUseView.class);
			return "listverysmall";
		} else {
			this.bindEvent(ae, "enList", BusTurnpage.class);
			return "listverysmall";
		}
	}

	private List createSubInfo(String formcode, ActionEvent ae) {
		// �����ӱ��б�
		TCsForm subFormInfo = null;
		try {
			subFormInfo = dysc.loadForm(formcode);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String subformInfo = subFormInfo.getSubform();
		if (subformInfo != null && !subformInfo.equals("")) {
			// //DB�е��ӱ���ѯģʽ
			// subformInfo = "'" + subformInfo.replaceAll(",", "','") + "'";
			// TCsForm subFormInfoQue = new TCsForm();
			// List listSublist = formDao.queryObjects(subFormInfoQue,
			// " and formcode in(" + subformInfo + ")");
			// ae.setAttribute("subform", listSublist);

			// �µ�XML�е��ӱ���ģʽ
			String[] subinfoall = subformInfo.split(",");
			ResourceRmi rmi = null;
			try {
				rmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List listSublist = new ArrayList();
			for (int i = 0; i < subinfoall.length; i++) {
				String formcodeName = StringUtils.substringBetween(
						subinfoall[i], "[", "]");
				String formcodeReal = formcodeName;

				TCsForm formPre = null;
				try {
					formPre = dysc.loadForm(formcodeReal);

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String mode = StringUtils.substringAfterLast(subinfoall[i],
						"]mode=");
				if (mode != null & mode.trim().length() == 1) {
					// mode ��ֵ��ʾ�ӱ���չ��ģʽ Ĭ����ֻ���б�
					// 1����ͨ�б�,2���б�3ֻ���б����ӱ���4ֻ������5����
					formPre.setExtendattribute(mode);
				}

				listSublist.add(formPre);
			}
			ae.setAttribute("subform", listSublist);
			return listSublist;
		} else {
			ae.setAttribute("subform", new ArrayList());
			return new ArrayList();
		}

	}

	public String onListsum(ActionEvent ae) throws Exception {

		String querytip = ae.getParameter(QUERY_TIP);
		BusForm form = (BusForm) ae.getForm();
		form.setFatherlsh(form.getLsh());
		String formcode = form.getFormcode();
		if (formcode == null || formcode.equals("")) {
			return null;
		}
		// ���ɶ�̬���б�ͷ��Ϣ
		boolean havekey = dylistTitle(formcode, ae);
		if (!havekey) {
			return null;
		}
		// ����ť
		Security ser = new Security(ae.getRequest());
		dealwithButton(formcode, ae);

		// ҵ���ѯ����
		ListView search = null;
		if ("true".equals(querytip)) {
			search = setQueryInfo(form);
		} else {
			search = setListInfo(form, ae.getRequest());
		}
		ae.setAttribute("en", search);

		this.bindEvent(ae, "enList", BusTurnpage.class);
		return "listsum";
	}

	public String onListview(ActionEvent ae) throws Exception {
		Security ser = new Security(ae.getRequest());
		String querytip = ae.getParameter(QUERY_TIP);
		BusForm form = (BusForm) ae.getForm();
		form.setFatherlsh(form.getLsh());
		String formcode = form.getFormcode();
		if (formcode == null || formcode.equals("")) {
			return null;
		}
		// ���ɶ�̬���б�ͷ��Ϣ
		boolean havekey = dylistTitle(formcode, ae);
		if (!havekey) {
			return null;
		}

		// ҵ���ѯ����
		ListView search = null;
		if ("true".equals(querytip)) {
			search = setQueryInfo(form);
		} else {
			search = setListInfo(form, ae.getRequest());
		}
		ae.setAttribute("en", search);

		this.bindEvent(ae, "enList", BusTurnpage.class);
		return "listview";
	}

	public String onExportope(ActionEvent ae) throws Exception {
		BusForm form = (BusForm) ae.getForm();
		Security ser = new Security(ae.getRequest());
		ListView search = null;
		search = setQueryInfo(form);
		TCsBus busForm = (TCsBus) search.getSearchobj();
		String sql = search.getCondition();
		String type = form.getTypeselect();
		throw new RuntimeException("δʵ��");
		// return onList(ae);
	}

	private ListView setQueryInfo(BusForm form) {
		String formcode = form.getFormcode();
		if (formcode == null || formcode.equals("")) {
			return null;
		}
		TCsBus busForm = new TCsBus();
		try {

			BeanUtils.copyProperties(busForm, form);
			busForm.setFatherlsh(form.getFatherlsh());
			// ���ò�ѯĬ��ֵ
			busForm.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);
			// String dn = BusPermission.getColumnPathDn(formcode);
			// int action = ser.getUserAction(dn);
			// if (action == 7) {// ����߱�����7Ȩ����ô�����Բ������еı�
			// busForm.setParticipant(null);
			// }
			// ��ѯ��������
			ListView search = new ListView();
			search.setSearchobj(busForm);
			search.setCondition(form.getSuperCondition());
			return search;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ListView setListInfo(BusForm form, HttpServletRequest req) {

		String formcode = form.getFormcode();
		String fatherlsh = form.getFatherlsh();
		if (formcode == null || formcode.equals("")) {
			return null;
		}

		TCsBus busForm = new TCsBus();
		// ���ò�ѯĬ��ֵ
		busForm.setFormcode(formcode);
		busForm.setFatherlsh(fatherlsh);
		busForm.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);

		String mode = req.getParameter("mode");

		if ("useview".equals(mode) || "onlyview".equals(mode)) {
			// �����б�ģʽ������,�������
		} else {
			// ��ͨ����ģʽ����
			Security ser = new Security(req);
			String usercode = ser.getUserName();
			if (!usercode.equals("adminx")) {// �ǳ����û�
				boolean sysadmin = ser
						.checkUserPermission(_ROLE_ADMIN_SYS, "1");
				if (!sysadmin) {// û�й���Ա��ɫȨ��(����ԱȨ�޵Ķ�DEPT.DEPT���Ÿ���Ȩ�޵�)
					busForm.setParticipant(form.getParticipant());
				}
			}
		}

		String dn = BusPermission.getColumnPathDn(formcode);
		// int action = ser.getUserAction(dn);
		// if (action == 7) {// ����߱�����7Ȩ����ô�����Բ������еı�
		// busForm.setParticipant(null);
		// }
		// ��ѯ��������
		ListView search = new ListView();
		search.setSearchobj(busForm);
		search.setCondition(" order by created desc");

		return search;
	}

	private void dealwithButton(String formcode, ActionEvent ae) {

		try {

			TCsForm form = dysc.loadForm(formcode);
			String buttoninfo = form.getButinfo();
			String[] butall = buttoninfo.split(",");
			for (int i = 0; i < butall.length; i++) {
				String[] prebut = butall[i].split(":");
				if (prebut.length == 3) {
					Control con = ae.getControl(prebut[0]);
					boolean display = "1".equals(prebut[1]);

					// con.setAttribute("disabled", disabled?"false":"true");
					con.setAttribute("value", prebut[2]);
					con.setVisible(display);
				}
			}
			System.out.println("");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if (isExist) {

		// RequestCtx req = new RequestCtx();
		// SubjectCtx ctx = req.newSubject();
		// ctx.newAttribute(Cupm._TODO_SUBJECT_ATTRIBUTE_OPE,
		// Cupm._OPE_GET_RESOURCE);
		// String natrualname = BusPermission.getOpePathDn(formcode) + ".-";
		// ctx.newAttribute(Cupm._TODO_SUBJECT_ATTRIBUTE_CONDITION,
		// natrualname);
		// req.newResource();
		// req.newAction();
		//			
		// List set = null;
		// try {
		// ResponseCtx rep = ser.todo(req);
		// set = ResourceResponseToProtectedobject
		// .makeProtectedobject(rep);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// for (Iterator itr = set.iterator(); itr.hasNext();) {
		// ProtectedObject ctxAttri = (ProtectedObject) itr.next();
		// String opePath = BusPermission.getOpePathDn(formcode);
		// int action = ser.getUserAction(opePath + "."
		// + ctxAttri.getNaturalname());
		//			
		// Control con = ae.getControl(ctxAttri.getNaturalname());
		// boolean visible = false;
		// String disabled = "true";
		// if (action == 7) {
		// visible = true;
		// disabled = "false";
		// } else if (action == 3) {
		// visible = true;
		// }
		// con.setAttribute("disabled", disabled);
		// con.setAttribute("value", ctxAttri.getName());
		// con.setVisible(visible);
		// }

		// } else {
		// TCsForm formBus = null;
		// try {
		// formBus = dysc.loadForm(formcode);
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// String[][] butinfo = DealwithButton.dealwithBut(formBus
		// .getButinfo());
		// for (int i = 0; i < butinfo.length; i++) {
		// boolean visible = butinfo[i][1].equals("1");
		// Control con = ae.getControl(butinfo[i][0]);
		//
		// con.setVisible(visible);
		// con.setAttribute("value", butinfo[i][2]);
		// }
		// }
		// TCsForm formBus = formDao.loadObject(formcode);
		// String[][] butinfo =
		// DealwithButton.dealwithBut(formBus.getButinfo());

	}

	/**
	 * ��̬�����б�ͷ��Ϣ
	 * 
	 * @param formcode
	 * @param ae
	 */
	private boolean dylistTitleManual(String formcode, ActionEvent ae,
			String[] key) {
		Map map = null;
		try {
			map = dysc.fetchTitleInfos(formcode);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] titleKeyListTmp = ((String) map.get("0")).split(",");
		if (titleKeyListTmp != null && titleKeyListTmp.length > 0) {
			String checkBox = titleKeyListTmp[titleKeyListTmp.length - 1];
			if ("n".equals(checkBox)) {
				Control control = ae.getControl("col");
				control.setVisible(false);
			}
		}
		String[] titleKeyList = new String[titleKeyListTmp.length - 1];
		System.arraycopy(titleKeyListTmp, 0, titleKeyList, 0,
				titleKeyListTmp.length - 1);
		int i = 0;
		boolean havekey = false;
		for (; i < titleKeyList.length; i++) {
			if (titleKeyList[i] == null || titleKeyList[i].equals("")) {
				break;
			}
			String[] columnInfo = (String[]) map.get(titleKeyList[i]);

			if (columnInfo == null) {
				continue;
			}
			System.out.println(columnInfo[0] + "," + columnInfo[1]);
			for (int k = 0; k < key.length; k++) {
				if (columnInfo[0].equalsIgnoreCase(key[k])) {
					havekey = true;
					String controlKey = "col" + i;
					Control control = ae.getControl(controlKey);
					control.setAttribute("field", columnInfo[0].toLowerCase());
					control.setAttribute("headText", columnInfo[1]
							.toLowerCase());
					control.setVisible(true);
				}
			}
		}
		if (i < 7) {
			for (; i <= 7; i++) {
				String controlKey = "col" + i;
				ae.getControl(controlKey).setVisible(false);
			}
		}
		return havekey;
	}

	/**
	 * ��̬�����б�ͷ��Ϣ
	 * 
	 * @param formcode
	 * @param ae
	 */
	private boolean dylistTitle(String formcode, ActionEvent ae) {
		Map map = null;
		try {
			map = dysc.fetchTitleInfos(formcode);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] titleKeyListTmp = ((String) map.get("0")).split(",");
		if (titleKeyListTmp != null && titleKeyListTmp.length > 0) {
			String checkBox = titleKeyListTmp[titleKeyListTmp.length - 1];
			if ("n".equals(checkBox)) {
				Control control = ae.getControl("col");
				control.setVisible(false);
			}
		}
		String[] titleKeyList = new String[titleKeyListTmp.length - 1];
		System.arraycopy(titleKeyListTmp, 0, titleKeyList, 0,
				titleKeyListTmp.length - 1);
		int i = 0;
		boolean havekey = false;
		for (; i < titleKeyList.length; i++) {
			if (titleKeyList[i] == null || titleKeyList[i].equals("")) {
				break;
			}
			String[] columnInfo = (String[]) map.get(titleKeyList[i]);

			if (columnInfo == null) {
				continue;
			}
			System.out.println(columnInfo[0] + "," + columnInfo[1]);
			havekey = true;
			String controlKey = "col" + i;
			Control control = ae.getControl(controlKey);
			control.setAttribute("field", columnInfo[0].toLowerCase());
			if (columnInfo[1] != null) {
				columnInfo[1]=columnInfo[1].toLowerCase();
			}
			control.setAttribute("headText", columnInfo[1]);
			control.setVisible(true);
		}
		if (i < 7) {
			for (; i <= 7; i++) {
				String controlKey = "col" + i;
				ae.getControl(controlKey).setVisible(false);
			}
		}
		return havekey;
	}

	public String onCreateope(ActionEvent ae) throws Exception {
		BusForm form = (BusForm) ae.getForm();
		Security ser = new Security(ae.getRequest());

		TCsBus busForm = new TCsBus();
		BeanUtils.copyProperties(busForm, form);
		busForm.setLsh(IdServer.uuid());
		busForm.setStatusinfo(BusExtendInfo._STATUS_NORMAL);
		busForm.setCreated((new Timestamp(System.currentTimeMillis()))
				.toString().substring(0, 19));
		busForm.setFatherlsh(form.getFatherlsh());
		busForm.setParticipant(ser.getUserLoginName());
		busForm.setExtendattribute("");
		dysc.addData(busForm.getFormcode(), busForm);

		if ("true".equals(ae.getParameter("static"))) {
			// ������̬ҳ��
			String realpath = ae.getRequest().getRealPath("") + "/fm/";

			BuildFormStaticPage.buildForumAuto(realpath, form.getFatherlsh(),
					form.getLsh(), form.getFormcode(), ser.getUserLoginName());
			// /////////////////////////////////////
			if ("1".equals(form.getFatherlsh())) {
				// ������̬ҳ��

				BuildFormStaticPage.buildForumLevel2(realpath, form.getLsh(),
						form.getFormcode(), ser.getUserLoginName());
				// /////////////////////////////////////
			}
		}

		WebTip
				.htmlInfoOri("<script>history.go(-1);</script>", ae
						.getResponse());
		return null;
	}

	public String onModifyope(ActionEvent ae) throws Exception {
		BusForm form = (BusForm) ae.getForm();
		TCsBus busForm = new TCsBus();
		BeanUtils.copyProperties(busForm, form);
		Security ser = new Security(ae.getRequest());
		busForm.setParticipant(ser.getUserLoginName());
		if (busForm.getLsh() == null || busForm.getLsh().equals("")) {
			busForm.setLsh(IdServer.uuid());
			busForm.setExtendattribute("");
			dysc.addData(form.getFormcode(), busForm);
		} else {
			// TCsBus oribus = dysc.loadData(form.getLsh(), form.getFormcode());
			// busForm.setCreated(oribus.getCreated());
			dysc.modifyData(busForm);
		}

		form.setLsh(form.getFatherlsh());
		if ("commit".equals(ae.getParameter("wf"))) {
			// �����Ҫ�����ڹ������ύ
			// SoaCenterService soc = (SoaCenterService) RmiEntry.iv("soaser");
			String wfinfo = StringUtils.substringBetween(busForm
					.getExtendattribute(), "wf[", "]");
			// if (wfinfo != null && wfinfo.length() > 0) {
			// soc.inDo(wfinfo);
			// }
			throw new RuntimeException("����Ҫ�����ڹ������ύ");
		}

		if ("true".equals(ae.getParameter("static"))) {
			// ������̬ҳ��
			String realpath = ae.getRequest().getRealPath("") + "/fm/";

			BuildFormStaticPage.buildForumAuto(realpath, form.getFatherlsh(),
					form.getLsh(), form.getFormcode(), form.getParticipant());
			// /////////////////////////////////////
		}
		String url = "/dyForm/data/showdata/modifyview.do?fatherlsh="
				+ busForm.getFatherlsh() + "&formcode=" + busForm.getFormcode()
				+ "&lsh=" + busForm.getLsh();
		WebTip.htmlInfoOri("<script>opener.location.href='" + url
				+ "';window.close();</script>", ae.getResponse());
		return null;
	}

	public String onDropope(ActionEvent ae) throws Exception {
		BusForm form = (BusForm) ae.getForm();
		String busCode = form.getLsh();
		String formcode = form.getFormcode();

		String[] busCodeArr = busCode.split(",");
		for (int i = 0; i < busCodeArr.length; i++) {

			dysc.deleteData(formcode, busCodeArr[i]);
		}

		if ("true".equals(ae.getParameter("static"))) {
			// ������̬ҳ��
			String realpath = ae.getRequest().getRealPath("") + "/fm/";
			BuildFormStaticPage.buildForumAuto(realpath, form.getFatherlsh(),
					form.getLsh(), form.getFormcode(), form.getParticipant());
			// /////////////////////////////////////
		}
		form.setLsh(form.getFatherlsh());
		WebTip
				.htmlInfoOri(
						"<script>window.close();window.opener.location.reload();</script>",
						ae.getResponse());
		return null;
	}

	public String onBacktofather(ActionEvent ae) throws Exception {
		BusForm form = (BusForm) ae.getForm();
		String formcode = form.getFormcode();
		String fatherlsh = form.getFatherlsh();
		if (fatherlsh == null || "".equals(fatherlsh) || "1".equals(fatherlsh)) {
			form.setLsh(fatherlsh);
			return onList(ae);
		}
		TCsBus formBus = dysc.loadData(fatherlsh, formcode);
		String fatherFatherLsh = formBus.getFatherlsh();

		form.setLsh(fatherFatherLsh);
		form.setFormcode(formcode);
		return onList(ae);
	}

}
