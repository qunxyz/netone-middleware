package oe.midware.dyform.service;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import oe.cav.bean.logic.bus.BussDao;
import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DyFormServiceImpl extends UnicastRemoteObject implements
		DyFormService {
	static ResourceBundle rb=null;
	static String []headArr={""};
	static{
		try{
		rb=ResourceBundle.getBundle("dy");
		String head=rb.getString("cachehead");
		headArr=StringUtils.split(head,",");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public DyFormServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String addData(String formid, TCsBus bus) throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		bussDao.create(bus);
		String rs= bus.getLsh();
		for (int i = 0; i < headArr.length; i++) {
			WebCache.removeCache(headArr[i]+formid);
		}
		return rs;
	}

	public TCsForm loadForm(String formid) throws RemoteException {
		if (formid == null || formid.equals("")) {
			System.err.println("lose formid");
		}
		if (!WebCache.containCache("DYFORMX_" + formid)) {
			System.out.println("add cache form:" + formid);
			FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
			TCsForm form = formDao.loadObject(formid);
			// 零时适应，动态表单中的表名原先放在 description中，现在移
			form.setTablename(form.getDescription());
			// 增加扩展属性的展示 主要针对 html 头信息的追加
			ResourceRmi rsrmi = null;
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setExtendattribute(formid);
			List listq = rsrmi.queryObjectProtectedObj(upo, null, 0, 1, "");
			if (listq != null && listq.size() == 1) {
				form.setExtendattribute(((UmsProtectedobject) listq.get(0))
						.getDescription());
				form.setDescription(((UmsProtectedobject) listq.get(0))
						.getReference());
			}

			WebCache.setCache("DYFORMX_" + formid, form, null);
			return form;
		} else {
			return (TCsForm) WebCache.getCache("DYFORMX_" + formid);
		}
	}

	public List queryData(TCsBus bus, int from, int to, String condition)
			throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		return bussDao.queryObjects(bus, from, to, condition);
	}

	public boolean deleteData(String formid, String id) throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		TCsBus buss = bussDao.loadObject(formid, id);
		boolean rs= bussDao.drop(buss);
		for (int i = 0; i < headArr.length; i++) {
			WebCache.removeCache(headArr[i]+formid);
		}
		return rs;
	}

	public boolean modifyData(TCsBus bus) throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		boolean rs= bussDao.update(bus);
		for (int i = 0; i < headArr.length; i++) {
			WebCache.removeCache(headArr[i]+bus.getFormcode());
		}
		return rs;
	}

	public List fetchColumnList(String formid) throws RemoteException {

		if (!WebCache.containCache("DYFORMCOLUMNX_" + formid)) {
			FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
			List forms = formDao.fetchColumnList(formid);
			for (Iterator iterator = forms.iterator(); iterator.hasNext();) {
				TCsColumn object = (TCsColumn) iterator.next();
				String ext = object.getExtendattribute();
				ext = StringUtils.replace(ext, "%X@", "#");
				object.setExtendattribute(ext);
			}
			WebCache.setCache("DYFORMCOLUMNX_" + formid, forms, null);
			return forms;
		} else {
			return (List) WebCache.getCache("DYFORMCOLUMNX_" + formid);
		}
	}

	public TCsForm loadFormUrl(String url) throws RemoteException {
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		return formDao.loadObjectUrl(url);
	}

	public List fetchColumnListUrl(String url) throws RemoteException {
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		return formDao.fetchColumnListUrl(url);
	}

	public TCsBus loadData(String id, String formcode) throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		return bussDao.loadObject(formcode, id);
	}

	public int queryDataNum(TCsBus bus, String condition)
			throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		return (int) bussDao.queryObjectsNumber(bus, condition);
	}

	public Map fetchTitleInfos(String formcode) throws RemoteException {

		if (!WebCache.containCache("DYFORMTITLEX_" + formcode)) {
			FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
			Map forms = formDao.fetchTitleInfos(formcode);
			WebCache.setCache("DYFORMTITLEX_" + formcode, forms, null);
			return forms;
		} else {
			return (Map) WebCache.getCache("DYFORMTITLEX_" + formcode);
		}
	}
}
