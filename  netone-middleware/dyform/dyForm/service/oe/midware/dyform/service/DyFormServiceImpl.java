package oe.midware.dyform.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.BussDao;
import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;
import oe.frame.web.WebCache;

public class DyFormServiceImpl extends UnicastRemoteObject implements
		DyFormService {

	public DyFormServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String addData(String formid, TCsBus bus) throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		bussDao.create(bus);
		return bus.getLsh();
	}

	public TCsForm loadForm(String formid) throws RemoteException {
		if (!WebCache.containCache("DYFORM$_" + formid)) {
			FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
			TCsForm form = formDao.loadObject(formid);
			WebCache.setCache("DYFORM$_" + formid, form, null);
			return form;
		} else {
			return (TCsForm) WebCache.getCache("DYFORM$_" + formid);
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
		return bussDao.drop(buss);
	}

	public boolean modifyData(TCsBus bus) throws RemoteException {
		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		return bussDao.update(bus);
	}

	public List fetchColumnList(String formid) throws RemoteException {

		if (!WebCache.containCache("DYFORMCOLUMN$_" + formid)) {
			FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
			List forms = formDao.fetchColumnList(formid);
			WebCache.setCache("DYFORMCOLUMN$_" + formid, forms, null);
			return forms;
		} else {
			return (List) WebCache.getCache("DYFORMCOLUMN$_" + formid);
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
		
		if (!WebCache.containCache("DYFORMTITLE$_" + formcode)) {
			FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
			Map forms = formDao.fetchTitleInfos(formcode);
			WebCache.setCache("DYFORMTITLE$_" + formcode, forms, null);
			return forms;
		} else {
			return (Map) WebCache.getCache("DYFORMTITLE$_" + formcode);
		}
	}
}
