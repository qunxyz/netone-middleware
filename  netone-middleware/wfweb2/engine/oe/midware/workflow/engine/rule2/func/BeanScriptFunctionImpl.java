package oe.midware.workflow.engine.rule2.func;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import oe.frame.bus.workflow.rule.BeanScriptFunction;
import oe.mid.soa.bean.BeanMonitor;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.SoaBean;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanScriptFunctionImpl implements BeanScriptFunction {

	private Log _log = LogFactory.getLog(BeanScriptFunctionImpl.class);
	private String runtimeid;

	private String workcode;

	public void init(String runtimeid, String workcode) {
		this.workcode = workcode;
		this.runtimeid = runtimeid;

	}

	public void set(String id, String paramName, Object value) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			SoaBean bean = beansv.loadInParam(id);
			bean.getValues().put(paramName, value);
			beansv.saveInParam(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

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
			return -1;
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
		String[] extarr = parser(beanname);
		String beannameuse = extarr[1];
		String beanurl = extarr[0];

		String testtip = "";

		BeanService beansv = null;
		try {
			beansv = (BeanService) RmiEntry.ivCore(beanurl);
			SoaBean param = beansv.inParamDescription(beannameuse);
			return param.getLsh();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			testtip = e1.getMessage() + "未绑定 ";
			_log.error(testtip);

		} catch (ConnectException e2) {
			testtip = e2.getMessage() + "无法连接 ";
			_log.error(testtip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(testtip);
		}
		return null;
	}

	private String[] parser(String beanname) {

		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject pnj = (UmsProtectedobject) resourceRmi
					.loadResourceByNatural(beanname);
			String extet = pnj.getExtendattribute();
			if (extet == null) {
				_log.error("无效注册对象:" + beanname);

			}
			String[] extarr = extet.split("#");
			if (extarr.length != 2) {
				_log.error("无效注册对象:" + beanname);
			}
			return extarr;
		} catch (Exception e3) {
			e3.printStackTrace();
			_log.error(e3.getMessage());
		}
		return null;
	}

	public String run(String id) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			beansv.todo(id);
			return id;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_log.error(e.getMessage());
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			_log.error(e.getMessage());
		}
		return null;
	}

	public void setIn(String id, Object value, Object obj) {
		if (obj == null) {
			return;
		}
		SoaBean bean = (SoaBean) obj;
		bean.getValues().put(id, value);
	}

	public void submit(Object obj) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			beansv.saveInParam((SoaBean) obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
	}

	public String get(String id, String paramname) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			SoaBean bean = beansv.loadOutParam(id);
			Object obj = bean.getValues().get(paramname);
			return String.valueOf(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;

	}

	public Object getInobj(String id) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			return beansv.loadInParam(id);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public double getOutd(String fieldId, Object obj) {
		if (obj == null) {
			return 0;
		}
		SoaBean bean = (SoaBean) obj;
		String value = (String) bean.getValues().get(fieldId);
		if (value == null) {
			return -1;
		}
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1;
	}

	public float getOutf(String fieldId, Object obj) {
		if (obj == null) {
			return 0;
		}
		SoaBean bean = (SoaBean) obj;
		String value = (String) bean.getValues().get(fieldId);
		if (value == null) {
			return -1;
		}
		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1;
	}

	public int getOutn(String fieldId, Object obj) {
		if (obj == null) {
			return -1;
		}
		SoaBean bean = (SoaBean) obj;
		String value = (String) bean.getValues().get(fieldId);
		if (value == null) {
			return -1;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1;

	}

	public long getOutl(String fieldId, Object obj) {
		if (obj == null) {
			return -1;
		}
		SoaBean bean = (SoaBean) obj;
		String value = (String) bean.getValues().get(fieldId);
		if (value == null) {
			return -1;
		}
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1;

	}

	public String getOut(String fieldId, Object obj) {
		if (obj == null) {
			return null;
		}
		SoaBean bean = (SoaBean) obj;
		String value = (String) bean.getValues().get(fieldId);
		if (value == null) {
			return null;
		}
		try {
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;

	}

	public Object getOutobj(String id) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			return beansv.loadOutParam(id);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public void release(String beanname, String lsh) {
		String[] extarr = parser(beanname);
		String beannameuse = extarr[1];
		String beanurl = StringUtils.substringBeforeLast(extarr[0], "/")
				+ "/beanmonitor";

		String testtip = "";

		try {
			BeanMonitor bmoir = (BeanMonitor) RmiEntry.ivCore(beanurl);
			if (lsh != null && !lsh.equals("")) {
				bmoir.initSession(new String[] { lsh });
			}
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			testtip = e1.getMessage() + "未绑定 ";
			throw new RuntimeException(testtip);

		} catch (ConnectException e2) {
			testtip = e2.getMessage() + "无法连接 ";
			throw new RuntimeException(testtip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	public SoaBean runSyncByObj(Object obj) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			return beansv.todo((SoaBean) obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public SoaBean runSyncById(String id) {
		BeanService beansv;
		try {
			beansv = (BeanService) RmiEntry.iv("beanhandle");
			beansv.todo(id);
			return beansv.loadOutParam(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}


}
