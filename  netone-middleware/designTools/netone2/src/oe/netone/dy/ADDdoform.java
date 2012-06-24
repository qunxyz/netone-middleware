package oe.netone.dy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.jsp.JspException;

import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.Dycreate;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.DbTools;
import com.jl.common.workflow.DbTools2;

public class ADDdoform {
	public ADDdoform() {
		super();
	}
	public static List selectMenInDIr(String deptid) throws Exception {
		String sqlString1 = "select ID from netone.ums_protectedobject where NATURALNAME='"
				+ deptid + "'";
		List aList = DbTools.queryData(sqlString1);
		String fidString = null;
		for (Iterator iterator1 = aList.iterator(); iterator1.hasNext();) {
			Map object = (Map) iterator1.next();
			fidString = (String) object.get("ID");
		}
		String sqlString = "select * from t_cs_user where SYSTEMID='"
				+ fidString + "'";

		List list = DbTools.queryData(sqlString);
		List sarr = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			Resource rsInfo = new Resource();
			rsInfo.setResourcename((String) object.get("NAME"));

			rsInfo.setResourcecode((String) object.get("USERCODE"));
			sarr.add(rsInfo);
		}
		return sarr;
	}

	public List selectcssRs() throws Exception {
		ArrayList<ResourceInfo> aList = new ArrayList();
		ResourceInfo rInfox = new ResourceInfo();
		rInfox.setRname("样式信息");
		rInfox.setRcode("CSSFILE.CSSFILE");
		rInfox.setParentid("");
		aList.add(rInfox);
		List listx = SecurityEntry.iv().listDirRs("CSSFILE.CSSFILE");
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.setRname(name.getResourcename());
			rInfo.setRcode(name.getResourcecode());
			rInfo.setParentid(name.getParentid());
			aList.add(rInfo);
		}
		return aList;
	}

	public List selectbusstypeRs() throws Exception {
		ArrayList<ResourceInfo> aList = new ArrayList();
		ResourceInfo rInfox = new ResourceInfo();
		rInfox.setRname("业务维度类型信息");
		rInfox.setRcode("BUSSENV.BUSSENV.DYSER.BUSSLEVEL");
		rInfox.setParentid("");
		aList.add(rInfox);
		List listx = SecurityEntry.iv().listDirRs(
				"BUSSENV.BUSSENV.DYSER.BUSSLEVEL");
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.setRname(name.getResourcename());
			rInfo.setRcode(name.getResourcecode());
			rInfo.setParentid(name.getParentid());
			aList.add(rInfo);
		}
		return aList;
	}

	public List selectcssInDir(String code) throws Exception {
		List selectList = new ArrayList();
		selectList = selectcss(code);
		return selectList;
	}

	public List selectcss(String rcode) {
		ArrayList<ResourceInfo> aList = new ArrayList();
		String sqlString = "select Name,NATURALNAME from netone.ums_protectedobject where NATURALNAME like  '%CSSFILE.CSSFILE%'";

		List list = DbTools.queryData(sqlString);

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.extendattribute = (String) object.get("NATURALNAME");
			rInfo.rname = (String) object.get("Name");
			aList.add(rInfo);
		}
		return aList;
	}

	public List selectforms() {
		List selectList = new ArrayList();
		String sqlString = "select 	* from netone.ums_protectedobject where NATURALNAME like  '%BUSSFORM.BUSSFORM%' and objectType='DYFROM';";
		List list = DbTools.queryData(sqlString);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			ResourceInfo rsInfo = new ResourceInfo();
			rsInfo.rname = (String) object.get("NAME");
			rsInfo.objecttype = (String) object.get("objectType");
			rsInfo.rcode = (String) object.get("NATURALNAME");
			rsInfo.dateTime = ((Timestamp) object.get("created")).toString();
			if (((String) object.get("ACTIVE")).toString().equals("1")) {
				rsInfo.setACTIVE("有效");
			} else {
				rsInfo.setACTIVE("无效");
			}
			rsInfo.FID = (String) object.get("ID");
			rsInfo.extendattribute = (String) object.get("extendattribute");
			selectList.add(rsInfo);
		}

		return selectList;
	}

	public static List expendTreeInfo(String rcode, int st) throws Exception {
		String sqlStr = null;
		List fList = new ArrayList();
		boolean isrole = false;
		if (rcode.startsWith("SYSROLE.SYSROLE")) {
			sqlStr = "select NAME,ID from  netone.ums_role  where BELONGINGNESS like '"
					+ rcode + "%'";
			isrole = true;
		} else if (rcode.startsWith("DEPT.DEPT") && st == 1) {
			sqlStr = "select NAME,DESCRIPTION from t_cs_user where extendinfo like '%"
					+ rcode + "%'";
		}

		System.out.println(sqlStr);
		if (StringUtils.isEmpty(sqlStr)) {
			return new ArrayList();
		}

		List list = DbTools.queryData(sqlStr);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			if (object == null) {
				continue;
			}
			Resource rsInfo = new Resource();
			rsInfo.setResourcename((String) object.get("NAME"));
			if (isrole) {
				rsInfo.setResourcecode(((Integer) object.get("ID")).toString());
			} else {
				rsInfo.setResourcecode((String) object.get("DESCRIPTION"));
			}

			fList.add(rsInfo);
		}

		return fList;
	}

	public void deleteform(String formcode) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		TCsForm form = dysc.loadForm(formcode);
		boolean flag = dys.dropForm(form);
	}

	// 创建表单
	public String createForms(String bussdata, String busstype,
			String busstiem, String formname, String styinfo, String subform,
			String Description, String parentdir, String time) throws Exception {

		TCsForm busForm = new TCsForm();
		busForm.setFormname(formname);
		busForm.setStyleinfo(styinfo);
		busForm.setSubform(subform);
		busForm.setDimdata(bussdata);
		busForm.setDimlevel(busstype);
		busForm.setTimelevel(time);

		busForm.setDescription(Description);
		Dycreate dycreate = new Dycreate();
		String exart = dycreate.createForms(busForm, parentdir);
		return exart;
	}

	// 创建sql表单
	public String createsql(String formname, String sql, String parentdir,String tablename)
			throws Exception {
		String exart=null;
		try {
			TCsForm busForm = new TCsForm();
			busForm.setFormname(formname);
			if(StringUtils.isNotEmpty(tablename)){
			 busForm.setTablename(StringUtils.substringAfterLast(tablename, "."));
			}
			busForm.setSqlinfo(sql);
			Dycreate dycreate = new Dycreate();
			exart= dycreate.createForms(busForm, parentdir);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
		return exart;
	}
	public String ceshisql(String sql){
		List list=new ArrayList();
		StringBuffer butBuffer=new StringBuffer();
		if(StringUtils.isNotEmpty(sql))
		{	
			try {
			 list=DbTools2.queryData(sql);
			 for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				for (Iterator iterator2 = object.keySet().iterator(); iterator2.hasNext();) {
					String key = (String) iterator2.next();
					Object value=object.get(key);
					String valueReal=value==null?"":value.toString();
					butBuffer.append(key+"-"+valueReal+";");
				}
				butBuffer.append("\n\r");
			 }
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				return e.getMessage();
			}
		}
		return butBuffer.toString();
	}
    //根据formcode删除表单
	public Boolean Delform(String formcode,String naturalname){	
		ResourceRmi resourceRmi = null;
		List list=new ArrayList();
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upbj=up.loadUmsProtecteNaturalname(naturalname);
		String id=upbj.getId();
		try {
			list=resourceRmi.subResource(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			Boolean fal=up.delUmsProtecte(object);
		}
		Boolean fal=up.delUmsProtecte(upbj);
		TCsForm busForm = new TCsForm();
		busForm.setFormcode(formcode);
		Dycreate dycreate = new Dycreate();
		return dycreate.DeleteForm(busForm);
	}
//	 //根据formcode删除视图表单
//	public Boolean DelformView(String formcode,String naturalname){	
//		DyFormService dysc=null;
//		TCsForm tCsForm=null;
//		try {
//			dysc = (DyFormService) RmiEntry.iv("dyhandle");
//			tCsForm=dysc.loadForm(formcode);
//			String sql="Drop view dyform."+tCsForm.getTablename();
//			DbTools.countData(sql);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		} 
//		return true;
//	}
	public void saveAppliction(String YSstate, String Nname, String Cname,
			String typeString, String ependString, String note)
			throws Exception {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		ApplicationRmi appx = (ApplicationRmi) RmiEntry.iv("application");
		UmsApplication umsapp = new UmsApplication();
		umsapp.setName(Cname);
		umsapp.setNaturalname(Nname);
		umsapp.setDescription(note);
		umsapp.setPassword("1");
		umsapp.setApptype(YSstate);
		umsapp.setCreated(new Date(System.currentTimeMillis()));
		umsapp.setExtendattribute(ependString);
		umsapp.setDescription(note);
		umsapp.setActive(YSstate);
		String appid = appx.create(umsapp);
	}

	public void updateAppliction(String id, String YSstate, String Nname,
			String Cname, String typeString, String ependString, String note)
			throws Exception {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		ApplicationRmi appx = (ApplicationRmi) RmiEntry.iv("application");
		UmsApplication uApplication = appx.loadObject(Long.parseLong(id));
		uApplication.setName(Cname);
		uApplication.setDescription(note);
		uApplication.setPassword("1");
		uApplication.setApptype(typeString);
		uApplication.setCreated(new Date(System.currentTimeMillis()));
		uApplication.setExtendattribute(ependString);
		uApplication.setActive(YSstate);
		appx.update(uApplication);
	}

	public List todo() throws Exception {
		List arrList = new ArrayList();
		ApplicationRmi appx = (ApplicationRmi) RmiEntry.iv("application");
		UmsApplication app = new UmsApplication();
		List list = appx.queryObjects(app, null);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Resource rs = new Resource();
			UmsApplication name = (UmsApplication) iterator.next();
			String appid = name.getNaturalname() + "." + name.getNaturalname();
			String appname = name.getName();
			String objecttype = name.getApptype();
			String timeString = name.getCreated().toString();
			String youxiaoString = name.getActive();
			String fidString = name.getId().toString();
			rs.setResourcecode(appid);
			rs.setResourcename(appname);
			rs.setTypes(objecttype);
			rs.setText(timeString);
			if (youxiaoString.equals("1")) {
				rs.setResourceid("有效");
			} else {
				rs.setResourceid("无效");
			}
			rs.setId(fidString);
			arrList.add(rs);
		}
		return arrList;
	}

	public static List selectPersonRs() throws Exception {
		ArrayList<ResourceInfo> aList = new ArrayList();
		ResourceInfo rInfox = new ResourceInfo();
		rInfox.setRname("参与者管理");
		rInfox.setRcode("DEPT.DEPT");
		rInfox.setParentid("");
		aList.add(rInfox);
		List listx = SecurityEntry.iv().listDirRs("DEPT.DEPT");
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.setRname(name.getResourcename());
			rInfo.setRcode(name.getResourcecode());
			rInfo.setParentid(name.getParentid());
			aList.add(rInfo);
		}
		for (int j = 0; j < aList.size(); j++) {
			System.out.println(((ResourceInfo) aList.get(j)).rname);
		}

		return aList;
	}

	public static List selectMoHu(String name, String Naturalname,
			String createtime, String endtime, String type) throws Exception {
		List list = new ArrayList();
		List MHList = new ArrayList();
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upoQ = new UmsProtectedobject();
		Map key = new HashMap();
		if (StringUtils.isNotEmpty(name)) {
			upoQ.setName("%" + name + "%");
			key.put("name", "like");
		}
		upoQ.setNaturalname(Naturalname + "%");
		key.put("naturalname", "like");

		String timeinfoString = "";
		timeinfoString = ("and objecttype='" + type + "'");
		if (StringUtils.isNotEmpty(createtime)) {
			timeinfoString = " and created>'" + createtime + "' ";
		}
		if (StringUtils.isNotEmpty(endtime)) {
			timeinfoString = timeinfoString + " and created<'" + endtime + "'";
		}
		MHList = rsrmi.queryObjectProtectedObj(upoQ, key, 0, 100,
				timeinfoString);
		for (int i = 0; i < MHList.size(); i++) {
			ResourceInfo rsInfo = new ResourceInfo();
			rsInfo.setRname(((UmsProtectedobject) MHList.get(i)).getName());
			rsInfo.setRcode(((UmsProtectedobject) MHList.get(i))
					.getNaturalname());
			rsInfo.setObjecttype(((UmsProtectedobject) MHList.get(i))
					.getObjecttype());
			rsInfo.setDateTime(((UmsProtectedobject) MHList.get(i))
					.getCreated().toString());
			if (((UmsProtectedobject) MHList.get(i)).getActive().toString()
					.equals("1")) {
				rsInfo.setACTIVE("有效");
			} else {
				rsInfo.setACTIVE("无效");
			}
			rsInfo.setFID(((UmsProtectedobject) MHList.get(i)).getId()
					.toString());
			if (((UmsProtectedobject) MHList.get(i)).getExtendattribute()
					.toString() != null
					|| !((UmsProtectedobject) MHList.get(i))
							.getExtendattribute().toString().equals("")) {
				rsInfo.setExtendattribute(((UmsProtectedobject) MHList.get(i))
						.getExtendattribute().toString());
			}
			list.add(rsInfo);
		}
		for (int i = 0; i < list.size(); i++) {

			System.out.println(((ResourceInfo) list.get(i)).getObjecttype()
					.toString());
		}

		return list;
	}

	public List selectTreeSource(String rname, String rcode) throws Exception {

		List streeList = new ArrayList();
		ResourceInfo rInfox = new ResourceInfo();
		rInfox.setRname(rname);
		rInfox.setRcode(rcode);
		rInfox.setParentid("");
		streeList.add(rInfox);
		List listx = SecurityEntry.iv().listDirRs(rcode);

		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.setRname(name.getResourcename());
			rInfo.setRcode(name.getResourcecode());
			rInfo.setParentid(name.getParentid());
			streeList.add(rInfo);
		}
		for (Iterator itst = listx.iterator(); itst.hasNext();) {
			String code = ((Resource) itst.next()).getResourcecode().toString();
			List listy = SecurityEntry.iv().listRsInDir(code, "", 0, 100, "");
			if (listy != null) {
				for (Iterator ity = listy.iterator(); ity.hasNext();) {
					Resource rs = (Resource) ity.next();
					ResourceInfo rInfo1 = new ResourceInfo();
					rInfo1.setRname(rs.getResourcename());
					rInfo1.setRcode(rs.getResourcecode());
					rInfo1.setParentid(code);
					if (!code.equals(rs.getResourcecode())) {
						streeList.add(rInfo1);
					}

				}
			}
		}

		return streeList;

	}

	public List getFormparentid(String exten) {
		List streeList = new ArrayList();
		String sqlString = "select PARENTDIR from netone.ums_protectedobject  where extendattribute='"
				+ exten + "' and inclusion is null and  objectType='DYFROM'";
		List list = DbTools.queryData(sqlString);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.parentid = (String) object.get("PARENTDIR");
			streeList.add(rInfo);
		}

		return streeList;
	}

	public List selectTreeInDir(String code) throws Exception {

		List listy = SecurityEntry.iv().listRsInDir(code, "", 0, 100, "");
		return listy;
	}
}