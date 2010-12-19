package oe.cms.dao.infocell;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsJppmidware;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class JppMiddwareDIYImpl implements JppMiddwareDIY {
	static Map jpppool = new Hashtable();

	static Map jpppoolUser = new Hashtable();

	public void initJppmidwarePool() {
		jpppool = null;
		jpppool = new Hashtable();

		TCmsJppmidware jmw = new TCmsJppmidware();
		String sql = " and accesstype='" + CellInfo._TYPE_PUBLIC
				+ "' order by participant";

		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(jmw,
				null, sql);
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsJppmidware jmwPre = (TCmsJppmidware) itr.next();
			jpppool.put(jmwPre.getJppmid(), jmwPre);
		}
	}

	public Map jppmidwareByUser(String particiapnt) {
		Map map = (Map) jpppoolUser.get(particiapnt);
		if (map == null) {
			initJppmidwarePoolByUser(particiapnt);
		}
		return (Map) jpppoolUser.get(particiapnt);

	}

	public TCmsJppmidware load(String id) {
		return (TCmsJppmidware) jpppool.get(id);
	}

	public TCmsJppmidware loadByNaturalname(String name) {
		TCmsJppmidware jmw = new TCmsJppmidware();
		jmw.setNaturalname(name);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(jmw,
				null);
		if (list != null && list.size() == 1) {
			return (TCmsJppmidware) list.get(0);
		}
		return null;
	}

	public void initJppmidwarePoolByUser(String userid) {
		jpppoolUser.remove(userid);
		TCmsJppmidware jmw = new TCmsJppmidware();
		String sql = " and participant= '" + userid + "' order by participant";

		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(jmw,
				null, sql);
		Map map = new Hashtable();
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsJppmidware jmwPre = (TCmsJppmidware) itr.next();
			map.put(jmwPre.getJppmid(), jmwPre);
		}
		jpppoolUser.put(userid, map);
	}

	public Map jppmidwareAllPublic() {
		// TODO Auto-generated method stub
		return jpppool;
	}

	public void create(TCmsJppmidware jmw, String naturalname) {
		String id = IdServer.uuid();
		String lastNaturalname = jmw.getNaturalname();
		String fullNaturalname = naturalname + "."
				+ lastNaturalname.toUpperCase();
		jmw.setJppmid(id);
		jmw.setNaturalname(fullNaturalname);

		OrmerEntry.fetchOrmer().fetchSerializer().create(jmw);

		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setName(jmw.getJppmidname());
			upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_NO);
			upo.setNaturalname(lastNaturalname);
			upo.setExtendattribute(jmw.getJppmid());
			upo.setObjecttype("BUSSENV.BUSSENV.PORTAL.ITEMTEMPLATE");
			resourceRmi.addResource(upo, naturalname);
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

		Map userpool = (Map) jpppoolUser.get(jmw.getParticipant());
		if (userpool == null) {
			userpool = new Hashtable();
		}
		userpool.put(jmw.getJppmid(), jmw);
		jpppool.put(jmw.getJppmid(), jmw);

	}

	public void delete(String id) {
		TCmsJppmidware xx = (TCmsJppmidware) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsJppmidware.class, id);
		OrmerEntry.fetchOrmer().fetchSerializer().drop(xx);
		Map userpool = (Map) jpppoolUser.get(xx.getParticipant());
		if (userpool != null) {
			userpool.remove(id);
		}
		jpppool.remove(id);
	}

	public void modify(TCmsJppmidware jmw) {
		OrmerEntry.fetchOrmer().fetchSerializer().update(jmw);
		Map userpool = (Map) jpppoolUser.get(jmw.getParticipant());
		if (userpool != null) {
			userpool.remove(jmw.getJppmid());
			userpool.put(jmw.getJppmid(), jmw);
		}
		jpppool.remove(jmw.getJppmid());
		jpppool.put(jmw.getJppmid(), jmw);

	}

}
