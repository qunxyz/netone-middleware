package oe.cav.web.data.dyform.utils;

import oe.security3a.sso.Security;


public class BusPermission {
	/**
	 * ��Ա��ֶεĶ�д���з��ʿ���
	 * 
	 * @param formcode
	 * @return
	 */
	public static String getColumnPathDn(String formcode) {
		return "cupmsystem.MenuRs.dyform." + formcode + ".column";
	}

	/**
	 * ��Ա���¼�Ĳ���(��,ɾ,��,��)���еķ��ʿ���
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
	 * ����Ƿ���Ҫ���ʿ���,�㷨:���������Դ���з��ʿ��Ƹñ���ô����true,���򷵻�false
	 * 
	 * @param formcode
	 * @param ser
	 * @return
	 */
	public static boolean isExistPermission(String formcode, Security ser) {
		throw new RuntimeException("δʵ��");

	}

	/**
	 * Ϊ�������ʿ���
	 * 
	 * @param formcode
	 * @param ser
	 */
	public static void addPermission(String formcode, Security ser) {
		throw new RuntimeException("δʵ��");
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
		// // �����
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
		// String dataValue = "name=����,naturalname=data";
		// resource.newAttribute(formcodepath, dataValue);
		// String columnValue = "name=�ֶ�,naturalname=column";
		// resource.newAttribute(formcodepath, columnValue);
		// String opeValue = "name=����,naturalname=ope";
		// resource.newAttribute(formcodepath, opeValue);
		// ser.todo(req.toString());
		//
		// resource.setAttribute(null);
		// // �����ֶ�
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
		// // �������
		// String opednPathReal = BusPermission.getOpePathDn(formcode);
		// String[][] ope = { { "backIc", "����" }, { "dispIc", "��ʾ" },
		// { "queIc", "��ѯ" }, { "expIc", "����" }, { "createIc", "����" },
		// { "modifyIc", "�޸�" }, { "deleteIc", "ɾ��" },
		// { "sublistIc", "�ӱ��б�" } };
		// for (int i = 0; i < ope.length; i++) {
		// String colValue = "name=" + ope[i][1] + ",naturalname=" + ope[i][0];
		// resource.newAttribute(opednPathReal, colValue);
		// }
		// ser.todo(req.toString());

	}

}
