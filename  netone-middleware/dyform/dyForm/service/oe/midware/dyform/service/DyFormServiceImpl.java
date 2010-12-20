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
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		return formDao.loadObject(formid);
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
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		return formDao.fetchColumnList(formid);
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
		// TODO Auto-generated method stub
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		return formDao.fetchTitleInfos(formcode);
	}

}
