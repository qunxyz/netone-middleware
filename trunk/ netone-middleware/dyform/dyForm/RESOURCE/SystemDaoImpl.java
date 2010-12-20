package RESOURCE;

import java.io.Serializable;
import java.util.List;



import oe.cav.bean.logic.form.FormExtendInfo;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.system.SystemDao;
import oe.cav.bean.logic.system.SystemExtendInfo;
import oe.cav.bean.logic.system.TCsSystem;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.Querist;

public class SystemDaoImpl implements SystemDao {

	public boolean create(TCsSystem arg0) {

		return OrmerEntry.fetchOrmer().fetchSerializer().create(arg0);

	}

	public boolean update(TCsSystem arg0) {

		return OrmerEntry.fetchOrmer().fetchSerializer().update(arg0);
	}

	public boolean drop(TCsSystem arg0) {
		arg0.setStatusinfo(SystemExtendInfo._STATUS_STOP);
		return OrmerEntry.fetchOrmer().fetchSerializer().update(arg0);

	}

	public Querist fetchQuerister() {
		return OrmerEntry.fetchOrmer().fetchQuerister();
	}

	public List fetchForm(TCsSystem system) {
		String systemid = system.getSystemid();
		TCsForm form = new TCsForm();
		form.setSystemid(systemid);
		form.setStatusinfo(FormExtendInfo._STATUS_NORMAL);
		return fetchQuerister().queryObjects(form);
	}

	public boolean checkSystemRegedit(String systemid) {
		TCsSystem system = new TCsSystem();
		String condition = " and systemid='" + systemid + "'";
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(system,
				condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public TCsSystem loadObject(Serializable key){
		return (TCsSystem)OrmerEntry.fetchOrmer().fetchQuerister().loadObject(TCsSystem.class,key);
	}

	public List queryObjects(TCsSystem obj) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj);
	}

	public List queryObjects(TCsSystem obj, String conditionPre) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				conditionPre);
	}

	public List queryObjects(TCsSystem obj, int from, int to) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj, from,
				to);
	}

	public List queryObjects(TCsSystem obj, int from, int to, String conditionPre) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj, from,
				to, conditionPre);
	}

	public long queryObjectsNumber(TCsSystem obj) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj);
	}

	public long queryObjectsNumber(TCsSystem obj, String conditionPre) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj,
				conditionPre);
	}

}
