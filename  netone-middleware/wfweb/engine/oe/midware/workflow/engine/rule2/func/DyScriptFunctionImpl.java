package oe.midware.workflow.engine.rule2.func;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oe.frame.bus.workflow.rule.DyScriptFunction;
import oe.frame.orm.util.IdServer;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 动态表单脚本实现<br>
 * 
 * 需要注意动态表单的脚本实现中需要注意 getInobj 和 getOutObj 是一样的.
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class DyScriptFunctionImpl implements DyScriptFunction {

	private Log _log = LogFactory.getLog(DyScriptFunctionImpl.class);

	private String runtimeid;

	private String workcode;

	public void init(String runtimeid, String workcode) {
		this.workcode = workcode;
		this.runtimeid = runtimeid;
	}

	/**
	 * 注意这里的参数ID是由lsh:formcode组成的
	 */
	public String get(String id, String paramname) {

		if (id == null || id.length() == 0) {
			return null;
		}
		String[] idinfo = id.split(":");
		if (idinfo.length != 2) {
			return null;
		}
		String lsh = idinfo[0];
		String formcode = idinfo[1];
		DyFormService dy;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			TCsBus bus = dy.loadData(lsh, formcode);
			return (String) BeanUtils.getProperty(bus, paramname);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

		return null;
	}

	public double getd(String id, String paramname) {
		// TODO Auto-generated method stub
		String obj = get(id, paramname);

		try {
			Double value = Double.parseDouble(obj);
			return value.doubleValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1.0;
		}

	}

	public float getf(String id, String paramname) {
		String obj = get(id, paramname);
		try {
			Float value = Float.parseFloat(obj);
			return value.floatValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1f;
		}
	}

	public int getn(String id, String paramname) {
		String obj = get(id, paramname);
		try {
			Integer value = Integer.parseInt(obj);
			return value.intValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1;
		}
	}

	public long getl(String id, String paramname) {
		String obj = get(id, paramname);
		try {
			Long value = Long.parseLong(obj);
			return value.longValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1;
		}
	}

	public String newInstance(String beanname) {
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi
					.loadResourceByNatural(beanname);
			String formcode = upo.getExtendattribute();
			return newInstanceByCode(formcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public String newInstanceByCode(String formcode) {
		try {

			DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
			TCsBus bus = new TCsBus();
			bus.setLsh(IdServer.uuid());

			bus.setStatusinfo("00");
			bus.setFormcode(formcode);
			bus.setFatherlsh("1");
			bus.setParticipant("scriptmen");
			bus
					.setCreated(new Timestamp(System.currentTimeMillis())
							.toString());
			// bus.setParticipant(workcode);
			bus.setExtendattribute("wf[" + workcode + "]");

			// 创建一个表单
			String lsh = dy.addData(null, bus);
			// 表单的对象的id中需要formcode
			return lsh+":"+formcode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public void set(String id, String paramName, Object value) {
		if (id == null || id.length() == 0) {
			return;
		}
		String[] idinfo = id.split(":");
		if (idinfo.length != 2) {
			return;
		}
		String lsh = idinfo[0];
		String formcode = idinfo[1];
		DyFormService dy;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			TCsBus bus = dy.loadData(lsh, formcode);
			BeanUtils.setProperty(bus, paramName, value);
			dy.modifyData(bus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public String run(String id) {
		// 表单不需要执行
		return "";
	}

	public void setIn(String id, Object value, Object obj) {
		if (obj == null) {
			return;
		}
		try {
			BeanUtils.setProperty(obj, id, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
	}

	public void submit(Object obj) {

		TCsBus bus = (TCsBus) obj;

		DyFormService dy;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			dy.modifyData(bus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public Object getInobj(String id) {
		if (id == null || id.length() == 0) {
			return null;
		}
		String[] idinfo = id.split(":");
		if (idinfo.length != 2) {
			return null;
		}
		String lsh = idinfo[0];
		String formcode = idinfo[1];
		DyFormService dy;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			return dy.loadData(lsh, formcode);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

		return null;
	}

	public double getOutd(String fieldId, Object obj) {
		// TODO Auto-generated method stub
		try {
			Double doub = new Double(BeanUtils.getProperty(obj, fieldId));
			return doub.doubleValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1d;
	}

	public long getOutl(String fieldId, Object obj) {
		// TODO Auto-generated method stub
		try {
			Long doub = new Long(BeanUtils.getProperty(obj, fieldId));
			return doub.longValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1l;
	}

	public float getOutf(String fieldId, Object obj) {
		// TODO Auto-generated method stub
		try {
			Float doub = new Float(BeanUtils.getProperty(obj, fieldId));
			return doub.floatValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1f;
	}

	public int getOutn(String fieldId, Object obj) {
		// TODO Auto-generated method stub
		try {
			Integer doub = new Integer(BeanUtils.getProperty(obj, fieldId));
			return doub.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1;
	}

	public String getOut(String fieldId, Object obj) {
		// TODO Auto-generated method stub
		try {
			return BeanUtils.getProperty(obj, fieldId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public Object getOutobj(String id) {
		return getInobj(id);
	}

	public boolean deleteData(String id) {
		if (id == null || id.length() == 0) {
			return false;
		}
		String[] idinfo = id.split(":");
		if (idinfo.length != 2) {
			return false;
		}
		String lsh = idinfo[0];
		String formcode = idinfo[1];
		DyFormService dy;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			return dy.deleteData(formcode, lsh);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return false;
	}

	public int queryDataNum(String name, TCsBus bus, String condition) {
		DyFormService dy;
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceByNatural(name);
			String formcode = upo.getExtendattribute();
			if (bus == null) {
				bus = new TCsBus();
			}
			bus.setFormcode(formcode);
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			return dy.queryDataNum(bus, condition);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1;
	}

	public List queryData(String name, TCsBus bus, int form, int to,
			String condition) {
		DyFormService dy;
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceByNatural(name);
			String formcode = upo.getExtendattribute();
			if (bus == null) {
				bus = new TCsBus();
			}
			bus.setFormcode(formcode);
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			return dy.queryData(bus, form, to, condition);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

}
