package com.jl.common.dyform;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.env.client.EnvService;
import oe.midware.dyform.service.DyFormService;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;

public final class DyformConsoleImpl implements DyFormConsoleIfc {

	static final String number_c = "/^[-]?([0-9]+)\\.?([0-9]*)$/";
	static final String mail_c = "/^[_a-z0-9]+@([_a-z0-9]+\\.)+[a-z0-9]{2,3}$/";
	static final String ip_c = "/^((\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5]))$/";

	public String addData(String formid, DyFormData bus) throws Exception {
		if (bus == null) {
			throw new Exception("�ձ�");
		}
		if (bus.getParticipant() == null || bus.getParticipant().equals("")) {
			throw new Exception("ȱ�ٲ�����");
		}
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus busx = new TCsBus();
		BeanUtils.copyProperties(busx, bus);
		busx.setLsh(UUID.randomUUID().toString().replaceAll("-", ""));
		// busx.setBelongx("");
		busx.setCreated((new Timestamp(System.currentTimeMillis()).toString()));
		busx.setStatusinfo("00");
		busx.setTimex((new Timestamp(System.currentTimeMillis()).toString()));
		busx.setFormcode(formid);
		String lsh = dy.addData(formid, busx);
		System.out.println("lsh:" + lsh);
		if (StringUtils.isNotEmpty(lsh)) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formid, lsh, "NewSave");
		}
		return lsh;
	}

	public boolean deleteData(String formid, String id) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		DyAnalysisXml dayx = new DyAnalysisXml();
		dayx.script(formid, id, "Delete");

		return  dy.deleteData(formid, id);

	}

	public List<DyFormColumn> fetchColumnList(String formid) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		List listcolumn = dy.fetchColumnList(formid);
		
		List newColumn = new ArrayList();
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		for (Iterator iterator = listcolumn.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();		
			String typex = object.getViewtype();
			if ("20".equals(typex) || "21".equals(typex) || "29".equals(typex)) {
				// continue;
			}
			if(object.getColumncode().startsWith("num_")){
				object.setViewtype("01");
			}
			if(object.getColumncode().startsWith("time_")){
				object.setViewtype("04");
			}
			DyFormColumn columnnew = new DyFormColumn();
			BeanUtils.copyProperties(columnnew, object);
			loadColumnx(columnnew);
			dealWithKvDict(columnnew, rs);
			newColumn.add(columnnew);
		}

		// DyFormColumn columnnew = new DyFormColumn();
		// columnnew.setColumnid("extendattribute");
		// columnnew.setColumname("��չ��Ϣ");
		// columnnew.setViewtype("13");
		// columnnew.setHidden(true);
		// columnnew.setMusk_(true);
		// newColumn.add(columnnew);
		
		return newColumn;
	}
	
	
	public List<DyFormColumn> fetchColumnListForDesign(String formid) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		List listcolumn = dy.fetchColumnList(formid);
		List newColumn = new ArrayList();
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		for (Iterator iterator = listcolumn.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			String typex = object.getViewtype();
			if ("20".equals(typex) || "21".equals(typex) || "29".equals(typex)) {
				// continue;
			}
			DyFormColumn columnnew = new DyFormColumn();
			BeanUtils.copyProperties(columnnew, object);
			loadColumnx(columnnew);
			//dealWithKvDict(columnnew, rs);
			newColumn.add(columnnew);
		}

		return newColumn;
	}
	
	private void dealWithKvDict(DyFormColumn columnnew, ResourceRmi rs) {
		// ��չ���� k-v �б�֧���ֵ�Ӧ��
		String htmltype = columnnew.getViewtype();
		System.out.println("-------------htmltype:"+htmltype);
		//KV�б���4��ģʽ 1���ֹ����õı�ѡֵ 2��������Դ��ĳ��Ŀ¼��ֵ 3������SOA�ű� 4������������̬�����ֶ�
		StringBuffer but = new StringBuffer();
		System.out.println(htmltype);
		if ("11".equals(htmltype)) {
			String valuelist = columnnew.getValuelist();
			//������Դ��ĳ��Ŀ¼��ֵ
			String rsinfo = StringUtils.substringBetween(valuelist, "[TREE:", "]");
			
			if (StringUtils.isNotEmpty(rsinfo)) {
				String valuetmp[]=StringUtils.split(rsinfo,",");
				String rsNaturaname=valuetmp[0];
				String rsKey=valuetmp.length==2?valuetmp[1]:"name";
				if (!StringUtils.contains(rsNaturaname, ".")) {
					rsNaturaname = rsNaturaname + "." + rsNaturaname;
				}
				try {
					UmsProtectedobject upo = rs.loadResourceByNatural(rsNaturaname);
					List sub = rs.subResource(upo.getId());

					for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						String key=object.getName();
						if(rsKey.equals("naturalname")){
							key=object.getNaturalname();
						}else if(rsKey.equals("id")){
							key=object.getId();
						}else if(rsKey.equals("nameid")){
							key=object.getName()+"["+object.getId()+"]";
						}else if(rsKey.equals("namenatual")){
							key=object.getName()+"["+object.getNaturalname()+"]";
						}
						but.append(key + "-"
								+ object.getName() + ",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//����SOA�ű�
			rsinfo = StringUtils.substringBetween(valuelist, "[SOA:", "]");
			if (StringUtils.isNotEmpty(rsinfo)) {
				UmsProtectedobject upo=null;
				try {
					upo = rs.loadResourceByNatural(rsinfo);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String param=upo.getDescription();
				EnvService env = null;
				try {

					env = (EnvService) RmiEntry.iv("envinfo");
					String value = env.fetchEnvValue("WEBSER_APPFRAME");
					value=value+"Soasvl?naturalname="+rsinfo+param;

							//System.out.println("sync user to php:"+url);
							// ͨѶЭ��
							URL rul = new URL(value);
							// ���������
							URLConnection urlc = rul.openConnection();
							InputStream input = urlc.getInputStream();
							// �������ݽ���

							int read = 0;
							while ((read = input.read()) != -1) {
								but.append((char) read);
							}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			//�����������ֶ�
			rsinfo = StringUtils.substringBetween(valuelist, "[DYFORM:", "]");

			if (StringUtils.isNotEmpty(rsinfo)) {
				String form[]=rsinfo.split(",");
				DyFormData dfd=new DyFormData();
				dfd.setFatherlsh("1");
				dfd.setFormcode(form[0]);
				String condition=form.length==4?form[3]:"";
				try {
					List data=DyEntry.iv().queryData(dfd, 0, 1000, condition);
					for (Iterator iterator = data.iterator(); iterator
							.hasNext();) {
						DyFormData object = (DyFormData) iterator.next();
						Object key=BeanUtils.getProperty(object, form[1]);
						Object value=BeanUtils.getProperty(object, form[2]);
						String keyinfo=key==null?"":key.toString();
						String valueinfo=value==null?"":value.toString();
						but.append(keyinfo + "-"
								+ valueinfo + ",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (but.length() > 0) {
			columnnew.setValuelist(but.toString());
		}
	}

	private void loadColumnx(DyFormColumn columnnew) throws Exception {

		String checktype = columnnew.getChecktype();
		String columnId = columnnew.getColumnid();
		if (columnId.equalsIgnoreCase("belongx")
				|| columnId.equalsIgnoreCase("timex")) {
			// �Ƿ�����
			columnnew.setHidden(true);
			return;
		}

		String ext = columnnew.getExtendattribute();
		if (ext != null && !ext.equals("")) {

			// ���û�ý����¼�
			String focusScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_FOCUSSCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setFocusScript(focusScript);
			// ����ʧȥ�����¼�
			String loseFocusScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_LOSEFOCUSSCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			if (loseFocusScript == null) {
				loseFocusScript = "";
			}

			String check_req = "";
			String reqExpressionular = "";
			if ("mail".equals(checktype)) {
				check_req = mail_c;
				reqExpressionular = mail_c;
			}
			if ("ip".equals(checktype)) {
				check_req = ip_c;
				reqExpressionular = ip_c;
			}
			if ("number".equals(checktype)) {
				check_req = number_c;
				reqExpressionular = number_c;
			}
			if (!check_req.equals("")) {
				loseFocusScript = "if($(this).val()!=''){ if(!$(this).val().match("
						+ check_req + ")){alert('��Ч��ʽ');}}" + loseFocusScript;
			}
			columnnew.setLoseFocusScript(loseFocusScript);

			// �Ƿ���һ���ֶ�
			String groupScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_GROUPSIZE,
					DymaticFormCheck._FINAL_CHECK);
			if (StringUtils.isNotEmpty(groupScript)) {
				columnnew.setGroupsize(Integer.parseInt(groupScript));
			}

			// ���ó�ʼ���¼�
			String initScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_INITSCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setInitScript(initScript);

			// ���øı��¼�
			String onchange = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_ONCHANGESCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setOnchangeScript(onchange);

			// ����У��ű�
			String reqExpressionularX = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_CHECKRULE,
					DymaticFormCheck._FINAL_CHECK);
			if (reqExpressionularX != null && !reqExpressionularX.equals("")) {
				String rule[] = StringUtils.split(reqExpressionularX, ";");
				if (rule.length > 1) {
					columnnew.setRegExpression(rule[0]);
				} else {
					columnnew.setRegExpression(reqExpressionular);
				}
			} else {
				columnnew.setRegExpression(reqExpressionular);
			}

			// �Ƿ�����
			columnnew.setHidden(false);
			// �Ƿ����
			columnnew.setMusk_("1".equals(columnnew.getMusk()));
			// �Ƿ�ֻ��
			columnnew.setReadonly("1".equals(columnnew.getOpemode()));
			// ���ÿؼ����
			String width = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_WIDTH,
					DymaticFormCheck._FINAL_CHECK);
			if (width != null && !width.equals("")) {
				columnnew.setWidth(Double.parseDouble(width));
			}
			// ���û�������
			String summarytype = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_SUMMARYTYPE,
					DymaticFormCheck._FINAL_CHECK);
			if (summarytype != null && !summarytype.equals("")) {
				columnnew.setSummarytype(summarytype);
			} else {
				columnnew.setSummarytype("");
			}
			// ����XY����
			String xyoffset = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_OFFSET,
					DymaticFormCheck._FINAL_CHECK);
			if (xyoffset != null && !xyoffset.equals("")) {
				String xy[] = StringUtils.split(xyoffset, "-");
				if (xy.length == 2) {
					columnnew.setYoffset(Double.parseDouble(xy[0]));
					columnnew.setXoffset(Double.parseDouble(xy[1]));
				}
			}
		} else {
			// �Ƿ�����
			columnnew.setHidden(false);
			// �Ƿ����
			columnnew.setMusk_("1".equals(columnnew.getMusk()));
			// �Ƿ�ֻ��
			columnnew.setReadonly("1".equals(columnnew.getOpemode()));
		}
	}

	public DyFormColumn loadColumn(String formid, String columnid)
			throws Exception {

		List list = fetchColumnList(formid);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DyFormColumn object = (DyFormColumn) iterator.next();
			object.getColumnid().equals(columnid);
			return object;
		}
		return null;
	}

	public DyFormData loadData(String formcode, String bussid) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bus = dy.loadData(bussid, formcode);
		DyFormData data = new DyFormData();
		BeanUtils.copyProperties(data, bus);
		if (data != null && !"".equals(data.getLsh())) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, bussid, "SelectRead");
		}
		return data;
	}

	public DyFormData loadDataS(String formcode, String bussid)
			throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bus = dy.loadData(bussid, formcode);
		DyFormData data = new DyFormData();
		BeanUtils.copyProperties(data, bus);
		if (data != null && !"".equals(data.getLsh())) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, bussid, "SelectUpdate");
		}
		return data;
	}

	public DyForm loadForm(String formid) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsForm form = dy.loadForm(formid);
		DyForm dyform = new DyForm();
		BeanUtils.copyProperties(dyform, form);
		// ���ñ��ֶ�
		List column = this.fetchColumnList(formid);
		dyform.setAllColumn_((DyFormColumn[]) column
				.toArray(new DyFormColumn[0]));

		// �����б�չʾ�ֶ�
		String listinfo = dyform.getListinfo();
		String[] infox = StringUtils.split(listinfo, ",");
		for (int i = 0; i < infox.length; i++) {
			if (infox[i].equals("1")) {
				infox[i] = "belongx";
			} else if (infox[i].equals("2")) {
				infox[i] = "timex";
			} else {
				infox[i] = "column" + infox[i];
			}
		}

		List dispColumnAndQueryColumn = new ArrayList();
		for (Iterator iterator = column.iterator(); iterator.hasNext();) {
			DyFormColumn object = (DyFormColumn) iterator.next();
			for (int i = 0; i < infox.length; i++) {
				if (object.getColumnid().equals(infox[i])) {
					dispColumnAndQueryColumn.add(object);
					break;
				}
			}

		}
		// if (dispColumnAndQueryColumn.size() < 2) {
		// dispColumnAndQueryColumn.addAll(column);
		// }
		DyFormColumn[] columnArr = (DyFormColumn[]) dispColumnAndQueryColumn
				.toArray(new DyFormColumn[0]);
		dyform.setQueryColumn_(columnArr);
		// ���ò�ѯչʾ�ֶ�
		dyform.setListColumn_(columnArr);

		String htmlinfo = form.getExtendattribute();
		dyform.setHtmltitleinfo_(htmlinfo);

		EnvService env = (EnvService) RmiEntry.iv("envinfo");
		String csshead = env.fetchEnvValue("WEBSER_CMSWEB");
		dyform.setStyleinfo_(dyform.getStyleinfo());
		String stylename = StringUtils.substringBetween(dyform.getStyleinfo(),
				"[", "]");
		dyform.setStyleinfourl_(csshead + "/DownloadSvl2?name=" + stylename);
		String subformlist = dyform.getSubform();
		if (subformlist != null && !subformlist.equals("")) {
			String subform[] = null;
			if (subformlist != null) {
				subform = subformlist.split(",");
			}
			List subformAll = new ArrayList();
			for (int i = 0; i < subform.length; i++) {
				try {
					String subformcode = StringUtils.substringBetween(
							subform[i], "[", "]");
					DyForm formsub = this.loadForm(subformcode);
					formsub.setSub(true);
					String submode = StringUtils
							.substringAfter(subform[i], "=");
					if (StringUtils.isNotEmpty(submode)
							&& submode.length() == 1) {
						formsub.setSubmode(submode);
					}
					subformAll.add(formsub);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dyform.setSubform_((DyForm[]) subformAll.toArray(new DyForm[0]));
		}

		return dyform;
	}

	public boolean modifyData(String formid, DyFormData bus) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		String lsh = bus.getLsh();
		bus.setFormcode(formid);
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		boolean rs = dy.modifyData(bux);
		if (rs) {
			if(bux.getStatusinfo()==null){
				DyAnalysisXml dayx = new DyAnalysisXml();
				dayx.script(formid, lsh, "UpdateSave"); //��������
			}
			if(bux.getStatusinfo()!=null&&bux.getStatusinfo().equals("00")){
				DyAnalysisXml dayx = new DyAnalysisXml();
				dayx.script(formid, lsh, "Onaffirm");//��ȷ��
			}
			if(bux.getStatusinfo()!=null&&bux.getStatusinfo().equals("01")){
				DyAnalysisXml dayx = new DyAnalysisXml();
				dayx.script(formid, lsh, "Yesaffirm");//ȷ��
			}
		}
		return rs;
	}

	public List queryData(DyFormData bus, int form, int to, String condition)
			throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		if (StringUtils.isEmpty(condition)) {
			condition = " order by timex";
		}
		if (StringUtils.isEmpty(bus.getFatherlsh())) {
			return new ArrayList();
		}
		List list = dy.queryData(bux, form, to, condition);

		if (list == null || list.size() == 0) {
			String formcode = bus.getFormcode();

			DyAnalysisXml dayx = new DyAnalysisXml();
			// ������ʼ��������Щʱ��������Ҫ�Զ����������ӱ���Ϣ
			// ��Ϣ��ʽΪ List(TCsBus)
			// �����ⲿ�ű��Զ������ӱ���ʼ������Ϣ
			Object rsinfo = dayx.script(formcode, null, "InitCreate");
			if (rsinfo != null
					&& rsinfo.getClass().getName()
							.equals("java.util.ArrayList")) {
				list = (ArrayList) rsinfo;
			}
		}
		List listnew = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			DyFormData data = new DyFormData();
			BeanUtils.copyProperties(data, object);
			listnew.add(data);
		}
		return listnew;
	}

	public int queryDataNum(DyFormData bus, String condition) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		int number = dy.queryDataNum(bux, condition);
		return number;

	}

	public int deleteAll(String formcode, String fatherlsh) throws Exception {
		DyFormData dydataSub = new DyFormData();
		dydataSub.setFormcode(formcode);// �ӱ���formcode
		dydataSub.setFatherlsh(fatherlsh);
		List listSub = DyEntry.iv().queryData(dydataSub, 0, 100, "");
		for (Iterator iterator = listSub.iterator(); iterator.hasNext();) {
			DyFormData object = (DyFormData) iterator.next();
			this.deleteData(formcode, object.getLsh());
		}
		return listSub.size();
	}

	public String[] addAll(List<DyFormData> data) throws Exception {
		List lsh = new ArrayList();
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			DyFormData dyFormData = (DyFormData) iterator.next();
			String formcode = dyFormData.getFormcode();
			if (formcode == null) {
				throw new Exception("ȱ�ٱ���ʶ��formcode");
			}
			String lshpre = this.addData(formcode, dyFormData);
			lsh.add(lshpre);
		}
		return (String[]) lsh.toArray(new String[0]);
	}

	public boolean deleteDataByLogic(String formid, String id) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	public void permission(DyForm form, String usercode) throws Exception {
		// TODO Auto-generated method stub
		columnPermission(form, usercode);
	}

	private void columnPermission(DyForm form, String usercode)
			throws Exception {
		CupmRmi cupm = null;
		ResourceRmi rmi = null;
		List formlist = null;
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
			rmi = (ResourceRmi) RmiEntry.iv("resource");

			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setExtendattribute(form.getFormcode());
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
				throw new RuntimeException("�������쳣����1�����ϵı���Դ:" + but);
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
		DyFormColumn[] columnList = form.getAllColumn_();
		String preNaturalname = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		String preId = ((UmsProtectedobject) formlist.get(0)).getId();
		Map mapPermissionkey=new HashMap();
		try {
			List subResource = rmi.subResource(preId);
			if (subResource == null || subResource.size() == 0) {
				// ���û��ע����ֶεı�����Դ����ô��Ϊ�ñ�����Ҫ��ȫ������ֱ�ӷ������е��ֶ����
				return;
			}
			for (Iterator iterator = subResource.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator.next();
				System.out.println("-------------:"+object.getNaturalname());
				mapPermissionkey.put(object.getNaturalname(), "");
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < columnList.length; i++) {
			DyFormColumn object = columnList[i];
			String naturalname = preNaturalname + "."
					+ object.getColumncode().toUpperCase();
			System.out.println("-------------x:"+naturalname);
			if(!mapPermissionkey.containsKey(naturalname)){
				continue;
			}
			boolean man = cupm.checkUserPermission("0000", usercode,
					naturalname, "7");
			if (!man) {
				object.setReadonly(true);
				boolean read = cupm.checkUserPermission("0000", usercode,
						naturalname, "3");
				if (!read) {
					object.setHidden(true);
				}
			}
		}
	}

	public void permission(DyForm form, String usercode, TWfActive act)
			throws Exception {
		String formcolumn = act.getEditcolumn();
		if (formcolumn == null || formcolumn.equals("")) {// �ܽ���
			// permission������ζ����Ҫ�༭��������û�û��������Ҫ�༭��Щ������ôĬ��ȫ�����ܱ༭
		}else{
		String[] columnxtmp = StringUtils.split(formcolumn, ",");
		Map permssionColumn = new HashMap();

		for (int i = 0; i < columnxtmp.length; i++) {
			String key = StringUtils.substringBetween(columnxtmp[i], "[", "]");
			permssionColumn.put(key, "");
		}
		if (permssionColumn.size() == 0) {
			return;
		}
		DyFormColumn[] columnx = form.getAllColumn_();
		for (int i = 0; i < columnx.length; i++) {
			if (!permssionColumn.containsKey(columnx[i].getColumnid())) {
				columnx[i].setReadonly(true);
				columnx[i].setMusk_(false);// �����ֻ����ô��Ҫ������������
			}
		}
		}
		
		//���ֶ���Ȩ����
		columnPermission(form,usercode);
		
		//���̱�����
		try {

		//�ֱ��ֶο���
		String subcolumn=act.getSubcolumn();
		if (subcolumn == null || subcolumn.equals("")) {// �ܽ���
			// permission������ζ����Ҫ�༭��������û�û��������Ҫ�༭��Щ������ôĬ��ȫ�����ܱ༭
			return;
		}
		subcolumn = subcolumn.replace("&quot;", "\"");
		subcolumn = subcolumn.replace("&amp;", "&");
		String [] subarr=subcolumn.split("&");
		DyForm [] subDyForms=form.getSubform_();
		for(int j=0;j<subDyForms.length;j++){
			for(int sub=0;sub<subarr.length;sub++){
				String substr=subarr[sub];
				JSONObject jsonobj= JSONObject.fromObject(substr);
				if(subDyForms[j].getFormcode().equals(jsonobj.get("subformcode").toString())){
					
					String[] read = StringUtils.split((jsonobj.get("read")).toString(), ",");
					Map readmap = new HashMap();
					//ֻ�����ֶ�
					for (int i = 0; i < read.length; i++) {
						String key = StringUtils.substringBetween(read[i], "[", "]");
						readmap.put(key, "");
					}
					String[] hide = StringUtils.split((jsonobj.get("hide")).toString(), ",");
					Map hidemap = new HashMap();
					//���ص��ֶ�
					for (int i = 0; i < hide.length; i++) {
						String key = StringUtils.substringBetween(hide[i], "[", "]");
						hidemap.put(key, "");
					}
					
					if (hidemap.size() == 0 && readmap.size()==0) {
						return;
					}
					DyFormColumn[] subcolumnx = subDyForms[j].getAllColumn_();
					 
					for (int i = 0; i < subcolumnx.length; i++) {
						if(readmap.size()!=0){
						if (readmap.containsKey(subcolumnx[i].getColumnid())) {
							subcolumnx[i].setReadonly(true);
							subcolumnx[i].setMusk_(false);// �����ֻ����ô��Ҫ������������
						}}
						if(hidemap.size()!=0){
						if (hidemap.containsKey(subcolumnx[i].getColumnid())) {
							subcolumnx[i].setHidden(true);// ���������
						}
						}
					}
				}
			}
			
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public List<TCsColumn> queryColumn(String formcode, String model)
			throws Exception {
		// TODO Auto-generated method stub
		DyColumnQuery dyfcQuery = new DyColumnQuery();
		dyfcQuery.QueryColumn(formcode, model);
		List list= dyfcQuery.QueryColumn(formcode, model);
		List newColumn=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			DyFormColumn columnnew = new DyFormColumn();
			BeanUtils.copyProperties(columnnew, object);
			loadColumnx(columnnew);
			dealWithKvDict(columnnew, rs);
			newColumn.add(columnnew);
		}
		return newColumn;
	}

}
