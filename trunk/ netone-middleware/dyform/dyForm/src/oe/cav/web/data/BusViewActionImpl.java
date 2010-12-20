package oe.cav.web.data;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.BusExtendInfo;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.DynamicFormCreate;
import oe.cav.web.data.dyform.DynamicFormDisplay;
import oe.cav.web.data.dyform.DynamicFormExport;
import oe.cav.web.data.dyform.DynamicFormModify;
import oe.cav.web.data.dyform.DynamicFormQuery;
import oe.cav.web.data.dyform.utils.DealwithButton;
import oe.cav.web.data.dyform.utils.ListView;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.BaseAction;
import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Button;
import com.rongji.webframework.ui.html.Div;
import com.rongji.webframework.ui.html.Image;
import com.rongji.webframework.ui.html.Table;
import com.rongji.webframework.ui.html.TableColumn;
import com.rongji.webframework.ui.html.TableRow;

public class BusViewActionImpl extends BaseAction {

	Log log = LogFactory.getLog(BusViewActionImpl.class);

	DyFormDesignService dys = null;

	DyFormService dysc = null;

	public BusViewActionImpl() {
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

	private List columnPermission(Security ser, String formcode) {
		List columnList = null;
		ResourceRmi rmi = null;
		List formlist = null;
		try {
			columnList = dysc.fetchColumnList(formcode);
			rmi = (ResourceRmi) RmiEntry.iv("resource");

			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setExtendattribute(formcode);
			upo.setNaturalname("BUSSFORM.BUSSFORM.%");
			Map map = new HashMap();
			map.put("naturalname", "like");
			formlist = rmi.fetchResource(upo, map);

			if (formlist.size() != 1) {
				StringBuffer but = new StringBuffer("Error Resource:");
				for (Iterator iterator = formlist.iterator(); iterator
						.hasNext();) {
					UmsProtectedobject object = (UmsProtectedobject) iterator
							.next();
					but.append(object.getName() + "[" + object.getNaturalname()
							+ "]");
				}
				throw new RuntimeException("表单定义异常存在1个以上的表单资源:" + but);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String preNaturalname = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		String preId = ((UmsProtectedobject) formlist.get(0)).getId();
		try {
			List subResource = rmi.subResource(preId);
			if (subResource == null || subResource.size() == 0) {
				// 如果没有注册表单字段的保护资源，那么认为该表单不需要安全保护，直接返回所有的字段许可
				return columnList;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List listAllowColumn = new ArrayList();

		for (Iterator iterator = columnList.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			String naturalname = preNaturalname + "."
					+ object.getColumncode().toUpperCase();
			int action = ser.getUserAction(naturalname);
			System.out.println(naturalname + " action:" + action);

			if (action >= 7) {
				listAllowColumn.add(object);
			} else if (action == 3) {
				object.setOpemode("readonly");
				listAllowColumn.add(object);
			}
		}

		return listAllowColumn;
	}

	public String onCreateview(ActionEvent ae) throws Exception {

		Security ser = new Security(ae.getRequest());

		String formcode = (String) ae.getParameter("formcode");
		String fatherlsh = (String) ae.getParameter("fatherlsh");
		if (fatherlsh == null || fatherlsh.equals("")) {
			fatherlsh = "1";
		}
		// BusForm formPre = (BusForm) ae.getForm();

		// 在继续创建的时候， 复制上一个表单的数据，方便下一次填写
		TCsBus bus = new TCsBus();

		Map map = ae.getRequest().getParameterMap();
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			BeanUtils.setProperty(bus, element, map.get(element));
		}
		bus.setLsh(null);
		// BeanUtils.copyProperties(bus, formPre);

		// bus.setLsh(IdServer.uuid());
		bus.setFatherlsh(fatherlsh);
		bus.setStatusinfo("00");
		bus.setFormcode(formcode);
		bus.setCreated(new Timestamp(System.currentTimeMillis()).toString());
		bus.setParticipant(ser.getUserLoginName());
		bus.setTimex(bus.getCreated());
		
		// dysc.addData(bus.getFormcode(), bus);
		// ae.setAttribute("lsh", bus.getLsh());
		// 重新再装载一此bus中的数据
		// bus = dysc.loadData(bus.getLsh(), formcode);
		List columnList = columnPermission(ser, bus.getFormcode());
		DynaUIForm dyform = (DynaUIForm) ae.getForm();

		DynamicFormModify.generateModifyView(ae, bus, columnList, dyform, "",
				null, fatherlsh);

		TCsForm form = dysc.loadForm(formcode);
		String styleinfo = form.getStyleinfo();

		ae.setAttribute("styleinfo", StringUtils.substringBetween(styleinfo,
				"[", "]"));
		return "dataview";
	}

	public String onCreateviewScript(ActionEvent ae) throws Exception {

		String cellid = (String) ae.getParameter("cellid");

		DynaUIForm dyform = (DynaUIForm) ae.getForm();

		DynamicFormCreate.generateCreateScriptView("scriptid", cellid, dyform);

		return "dataview";
	}

	public String onModifyview(ActionEvent ae) throws Exception {
		Security ser = new Security(ae.getRequest());
		String fatherlsh = (String) ae.getParameter("fatherlsh");
		if (fatherlsh == null || fatherlsh.equals("")) {
			fatherlsh = "1";
		}
		String formcode = (String) ae.getParameter("formcode");
		TCsForm form = dysc.loadForm(formcode);
		String lsh = (String) ae.getParameter("lsh");
		String hidesome = (String) ae.getParameter("hidesome");
		TCsBus busForm = (TCsBus) dysc.loadData(lsh, formcode);
		// 访问控制
		List columnList = columnPermission(ser, formcode);

		DynaUIForm dyform = (DynaUIForm) ae.getForm();

		DynamicFormModify.generateModifyView(ae, busForm, columnList, dyform,
				hidesome, null, fatherlsh);

		String styleinfo = form.getStyleinfo();

		ae.setAttribute("styleinfo", StringUtils.substringBetween(styleinfo,
				"[", "]"));
		return "dataview";
	}

	public String onCreateOrModifyview(ActionEvent ae) throws Exception {
		Security ser = new Security(ae.getRequest());
		String formcode = (String) ae.getParameter("formcode");
		String lsh = (String) ae.getParameter("lsh");
		String fatherlsh = (String) ae.getParameter("fatherlsh");
		if (fatherlsh == null || fatherlsh.equals("")) {
			fatherlsh = "1";
		}
		try {
			TCsBus busForm = (TCsBus) dysc.loadData(lsh, formcode);
			String hidesome = (String) ae.getParameter("hidesome");
			List columnList = columnPermission(ser, formcode);
			DynaUIForm dyform = (DynaUIForm) ae.getForm();
			DynamicFormModify.generateModifyView(ae, busForm, columnList,
					dyform, hidesome, null, fatherlsh);
			return "dataview";
		} catch (Exception e) {
			return onCreateview(ae);
		}
	}

	public String onQueryview(ActionEvent ae) throws Exception {
		String formcode = (String) ae.getParameter("formcode");
		List columnList = dysc.fetchColumnList(formcode);
		// Map columnRef = formDao.fetchColumnRef("");
		DynaUIForm dyform = (DynaUIForm) ae.getForm();
		DynamicFormQuery.generateQueryView(ae, formcode, columnList, dyform,
				null);
		return "dataview";
	}

	public String onExportview(ActionEvent ae) throws Exception {
		String formcode = (String) ae.getParameter("formcode");
		List columnList = dysc.fetchColumnList(formcode);
		// Map columnRef = formDao.fetchColumnRef("");
		DynaUIForm dyform = (DynaUIForm) ae.getForm();
		DynamicFormExport.generateView(ae.getRequest().getContextPath(),
				formcode, columnList, dyform, null);
		return "dataview";
	}

	public String onDisplay(ActionEvent ae) throws Exception {

		ae.setAttribute("onlyview", "yes");
		return onModifyview(ae);
	}

	private List subForm(TCsForm tCsForm) {
		List listSubForm = new ArrayList();
		String subformlist = tCsForm.getSubform();
		String subform[] = null;
		if (subformlist != null) {
			subform = subformlist.split(",");
		}

		for (int i = 0; i < subform.length; i++) {
			try {
				String subformcode = StringUtils.substringBetween(subform[i],
						"[", "]");
				listSubForm.add(dysc.loadForm(subformcode));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listSubForm;
	}

	public String onListviews(ActionEvent ae) throws Exception {
		ae.setAttribute("onlyview", "yes");
		// 获得表单CODE
		String formcode = ae.getParameter("formcode");
		// 获得LSH
		String lsh = ae.getParameter("lsh");
		if (formcode == null || "".equals(formcode)) {
			return null;
		}
		TCsForm tCsForm = dysc.loadForm(formcode);

		DynaUIForm dyform = (DynaUIForm) ae.getForm();

		String[][] butInfo = formInfo(tCsForm);

		// 在动态表单中创建按钮信息
		addTopBut(dyform, formcode, lsh, butInfo[0]);

		ListView listView = new ListView();
		listView.setBut1(butInfo[1]);
		listView.setBut2(butInfo[2]);
		listView.setDyform(dyform);
		listView.setLsh(lsh);
		listView.setFormcode(formcode);
		listView.setAe(ae);

		TCsBus busForm = new TCsBus();
		busForm.setFormcode(formcode);
		busForm.setFatherlsh(lsh);
		busForm.setStatusinfo(BusExtendInfo._STATUS_NORMAL);

		listView.setSearchobj(busForm);
		listView.setContextpath(ae.getRequest().getContextPath());
		listView.setCondition(" order by extendattribute");
		listView.setSubForm(subForm(tCsForm));

		ae.setAttribute("en", listView);
		this.bindEvent(ae, "enList", BusTurnpageView.class);

		return "listviews";
	}

	public String onSublistview(ActionEvent ae) throws Exception {
		ae.setAttribute("onlyview", "yes");
		// 获得表单CODE
		String formcodex = ae.getParameter("subform");
		// 获得LSH
		String lsh = ae.getParameter("lsh");
		if (formcodex == null || "".equals(formcodex)) {
			return null;
		}
		TCsForm tCsForm = dysc.loadForm(formcodex);

		String[][] butInfo = formInfo(tCsForm);

		DynaUIForm dyform = (DynaUIForm) ae.getForm();

		// 在动态表单中创建按钮信息
		addTopBut(dyform, formcodex, lsh, butInfo[0]);

		ListView listView = new ListView();
		listView.setBut1(butInfo[1]);
		listView.setBut2(butInfo[2]);
		listView.setDyform(dyform);
		listView.setLsh(lsh);
		listView.setFormcode(formcodex);
		listView.setAe(ae);

		TCsBus busForm = new TCsBus();
		busForm.setFormcode(formcodex);
		busForm.setFatherlsh(lsh);
		busForm.setStatusinfo(BusExtendInfo._STATUS_NORMAL);

		listView.setSearchobj(busForm);
		listView.setContextpath(ae.getRequest().getContextPath());
		listView.setCondition(" order by extendattribute");
		listView.setSubForm(subForm(tCsForm));

		ae.setAttribute("en", listView);
		this.bindEvent(ae, "enList", BusTurnpageView.class);

		return "listviews";
	}

	private void listElementOne(ActionEvent ae, String formcode, String lsh,
			DynaUIForm dyform, String[] but1, String[] but2, List list) {

		TCsBus busFomrPre = null;
		try {
			busFomrPre = dysc.loadData(lsh, formcode);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List columnList = null;
		try {
			columnList = dysc.fetchColumnList(busFomrPre.getFormcode());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Table table = new Table();
		TCsForm tCsForm = null;
		try {
			tCsForm = dysc.loadForm(formcode);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DynamicFormDisplay.generateDispView(ae, busFomrPre, columnList, dyform,
				null, table, but1, but2, subForm(tCsForm));

		dyform.addControl(table);
	}

	private void addTopBut(DynaUIForm dyform, String subformcode, String lsh,
			String[] butInfo) {
		Table table = new Table();
		table.setWidth("100%");
		// 创建附件管理按钮
		Button butMan = new Button();
		butMan.setName("butx");
		butMan.setValue(butInfo[2] + "创建");
		butMan.setVisible("1".equals(butInfo[1]));
		butMan.setOnClick("openCreate('" + subformcode + "','" + lsh + "')");

		// 创建附件管理按钮
		Button butMan1 = new Button();
		butMan1.setName("butx");
		butMan1.setValue(butInfo[2] + "管理");
		butMan1.setVisible("1".equals(butInfo[1]));
		butMan1.setOnClick("openList('" + subformcode + "','" + lsh + "')");

		TableRow row = table.insertRow();
		row.setAlign("left");

		// 创建附件管理按钮
		Button butMan2 = new Button();
		butMan2.setName("buty");
		butMan2.setValue("刷新");
		butMan2.setOnClick("location.reload();");

		TableColumn col1 = row.insertCol();
		Div div = new Div();
		div.addUI(butMan);
		div.addUI(butMan1);
		div.addUI(butMan2);
		col1.insertUI(div);

		dyform.addControl(table);

		Image image = new Image();
		image.setHeight("6");
		image.setWidth("100%");
		image.setSrc("tp01.gif");
		dyform.addControl(image);

	}

	public String onBacktofather(ActionEvent ae) throws Exception {
		BusForm form = (BusForm) ae.getForm();
		String fatherlsh = form.getFatherlsh();
		if (true) {
			throw new RuntimeException("====参数:formcode");
		}
		TCsBus formBus = dysc.loadData(fatherlsh, "");
		String fatherFatherLsh = formBus.getFatherlsh();

		String formcode = formBus.getFormcode();

		form.setLsh(fatherFatherLsh);
		form.setFormcode(formcode);
		return onListviews(ae);
	}

	private String[][] formInfo(TCsForm tCsSubForm) {

		String[][] butInfoTmp = DealwithButton.dealwithBut(tCsSubForm
				.getViewbutinfo());
		String[][] butInfo = new String[3][3];
		if (butInfoTmp != null && butInfoTmp.length == 1) {
			butInfo[0][1] = butInfoTmp[0][1];
			butInfo[0][2] = butInfoTmp[0][2];
			butInfo[1][1] = "1";
			butInfo[1][2] = "进入明细";
			butInfo[2][1] = "1";
			butInfo[2][2] = "返回上一级";
		} else if (butInfoTmp != null && butInfoTmp.length == 2) {
			butInfo[0][1] = butInfoTmp[0][1];
			butInfo[0][2] = butInfoTmp[0][2];
			butInfo[1][1] = butInfoTmp[1][1];
			butInfo[1][2] = butInfoTmp[1][2];
			butInfo[2][1] = "1";
			butInfo[2][2] = "返回上一级";
		}
		if (butInfoTmp != null && butInfoTmp.length == 3) {
			butInfo[0][1] = butInfoTmp[0][1];
			butInfo[0][2] = butInfoTmp[0][2];
			butInfo[1][1] = butInfoTmp[1][1];
			butInfo[1][2] = butInfoTmp[1][2];
			butInfo[2][1] = butInfoTmp[2][1];
			butInfo[2][2] = butInfoTmp[2][2];
		}
		return butInfo;
	}
}
