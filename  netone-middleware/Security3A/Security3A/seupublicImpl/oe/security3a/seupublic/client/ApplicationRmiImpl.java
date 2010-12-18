package oe.security3a.seupublic.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oe.frame.orm.OrmerEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class ApplicationRmiImpl extends UnicastRemoteObject implements
		ApplicationRmi {

	public ApplicationRmiImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean checkExist(String naturalname) {
		UmsApplication obj = new UmsApplication();
		obj.setNaturalname(naturalname);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				null);
		if (list.size() == 1) {
			return true;
		}
		if (list.size() > 1) {
			throw new RuntimeException("存储异常出现多个重复命名:" + naturalname);
		}
		return false;

	}

	public String create(UmsApplication obj) throws RemoteException {

		obj.setNaturalname(obj.getNaturalname().toUpperCase());

		if (checkExist(obj.getNaturalname())) {
			return null;
		}
		OrmerEntry.fetchOrmer().fetchSerializer().create(obj);
		// 创建一个根节点。
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setActive("1");
		upo.setDescription(obj.getDescription());
		upo.setName(obj.getName());
		upo.setNaturalname(obj.getNaturalname());
		upo.setExtendattribute(obj.getExtendattribute());
		upo.setAppid(obj.getId());
		upo.setParentdir("0");

		OrmerEntry.fetchOrmer().fetchSerializer().create(upo);

		upo.setOu(upo.getAppid() + "." + upo.getId());
		upo.setNaturalname(obj.getNaturalname() + "." + obj.getNaturalname());
		OrmerEntry.fetchOrmer().fetchSerializer().update(upo);
		return upo.getAppid().toString();
	}

	public boolean drop(Long id) throws RemoteException {
		UmsApplication app=this.loadObject(id);
		app.setActive("0");//设置失效
		return OrmerEntry.fetchOrmer().fetchSerializer().update(app);
	}

	public boolean drops(Long[] ids) throws RemoteException {
		List list = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			UmsApplication app=this.loadObject(ids[i]);
			app.setActive("0");//设置失效
			list.add(app);
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().updates(list);
	}

	public UmsApplication loadObject(Serializable key) throws RemoteException {
		return (UmsApplication) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(UmsApplication.class, key);
	}

	public List queryObjects(UmsApplication obj, Map comparisonKey, int from,
			int to) throws RemoteException {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, from, to);
	}

	public List queryObjects(UmsApplication obj, Map comparisonKey, int from,
			int to, String conditionPre) throws RemoteException {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, from, to, conditionPre);
	}

	public List queryObjects(UmsApplication obj, Map comparisonKey)
			throws RemoteException {
		// TODO Auto-generated method stub
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey);
	}

	public List queryObjects(UmsApplication obj, Map comparisonKey,
			String conditionPre) throws RemoteException {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, conditionPre);
	}

	public long queryObjectsNumber(UmsApplication obj, Map comparisonKey)
			throws RemoteException {
		// TODO Auto-generated method stub
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj,
				comparisonKey);
	}

	public long queryObjectsNumber(UmsApplication obj, Map comparisonKey,
			String conditionPre) throws RemoteException {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj,
				comparisonKey, conditionPre);
	}

	public boolean update(UmsApplication obj) throws RemoteException {
		OrmerEntry.fetchOrmer().fetchSerializer().update(obj);
		// 顺便也需要一起更新根保护对象
		UmsProtectedobject poj = new UmsProtectedobject();
		poj.setAppid(obj.getId());
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(poj,
				null);
		UmsProtectedobject pojReal = (UmsProtectedobject) list.get(0);
		pojReal.setName(obj.getName());
		pojReal.setDescription(obj.getDescription());
		pojReal.setExtendattribute(obj.getExtendattribute());
		OrmerEntry.fetchOrmer().fetchSerializer().update(pojReal);
		return true;
	}

	public boolean updates(List objs) throws RemoteException {
		return OrmerEntry.fetchOrmer().fetchSerializer().updates(objs);
	}
}
