package oe.cav.web.data.dyform.utils;

import oe.security3a.sso.Security;


public class BusPermission {
	/**
	 * 针对表单字段的读写进行访问控制
	 * 
	 * @param formcode
	 * @return
	 */
	public static String getColumnPathDn(String formcode) {
		return "cupmsystem.MenuRs.dyform." + formcode + ".column";
	}

	/**
	 * 针对表单记录的操作(增,删,改,查)进行的访问控制
	 * 
	 * @param formcode
	 * @return
	 */
	public static String getDataPathDn(String formcode) {
		return "cupmsystem.MenuRs.dyform." + formcode + ".data";
	}

	public static String getFormPath() {
		return "cupmsystem.MenuRs.dyform";
	}

	public static String getFormPath(String formcode) {
		return "cupmsystem.MenuRs.dyform." + formcode;
	}

	public static String getOpePathDn(String formcode) {
		return "cupmsystem.MenuRs.dyform." + formcode + ".ope";
	}

	/**
	 * 检查是否需要访问控制,算法:如果保护资源中有访问控制该表单那么返回true,否则返回false
	 * 
	 * @param formcode
	 * @param ser
	 * @return
	 */
	public static boolean isExistPermission(String formcode, Security ser) {
		throw new RuntimeException("未实现");

	}

	/**
	 * 为表单做访问控制
	 * 
	 * @param formcode
	 * @param ser
	 */
	public static void addPermission(String formcode, Security ser) {
		throw new RuntimeException("未实现");
		// DyFormService dysc = null;
		// try {
		// dysc = (DyFormService) RmiEntry.iv("dyhandle");
		// } catch (MalformedURLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (RemoteException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (NotBoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// TCsForm formx = null;
		// try {
		// formx = (TCsForm) dysc.loadForm(formcode);
		// } catch (RemoteException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// RequestCtx req = new RequestCtx();
		// SubjectCtx subject = req.newSubject();
		// subject.newAttribute(Cupm._TODO_SUBJECT_ATTRIBUTE_OPE,
		// "addResource");
		//
		// ResourceCtx resource = req.newResource();
		//
		// // 导入表单
		// String formValue = "name=" + formx.getFormname() + ",naturalname="
		// + formcode;
		//
		// String formpath = BusPermission.getFormPath();
		// resource.newAttribute(formpath, formValue);
		//
		// req.newAction();
		// ser.todo(req.toString());
		//
		// String formcodepath = BusPermission.getFormPath(formcode);
		//
		// resource.setAttribute(null);
		// String dataValue = "name=数据,naturalname=data";
		// resource.newAttribute(formcodepath, dataValue);
		// String columnValue = "name=字段,naturalname=column";
		// resource.newAttribute(formcodepath, columnValue);
		// String opeValue = "name=操作,naturalname=ope";
		// resource.newAttribute(formcodepath, opeValue);
		// ser.todo(req.toString());
		//
		// resource.setAttribute(null);
		// // 导入字段
		// String coldnPathReal = BusPermission.getColumnPathDn(formcode) + ".";
		//
		// List listColumn = null;
		// try {
		// listColumn = dysc.fetchColumnList(formcode);
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// for (Iterator iter = listColumn.iterator(); iter.hasNext();) {
		// TCsColumn element = (TCsColumn) iter.next();
		// String colValue = "name=" + element.getColumname()
		// + ",naturalname=" + element.getColumncode();
		// resource.newAttribute(coldnPathReal, colValue);
		// }
		//
		// // 导入操作
		// String opednPathReal = BusPermission.getOpePathDn(formcode);
		// String[][] ope = { { "backIc", "回退" }, { "dispIc", "显示" },
		// { "queIc", "查询" }, { "expIc", "导出" }, { "createIc", "创建" },
		// { "modifyIc", "修改" }, { "deleteIc", "删除" },
		// { "sublistIc", "子表单列表" } };
		// for (int i = 0; i < ope.length; i++) {
		// String colValue = "name=" + ope[i][1] + ",naturalname=" + ope[i][0];
		// resource.newAttribute(opednPathReal, colValue);
		// }
		// ser.todo(req.toString());

	}

}
