package oe.midware.workflow.engine.rule2.func;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.rule.WfScriptFunction;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 基于工作流的脚本服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class WfScriptFunctionImpl implements WfScriptFunction {

	private Log _log = LogFactory.getLog(WfScriptFunctionImpl.class);

	private String runtimeid;

	private String workcode;

	public void init(String runtimeid, String workcode) {
		this.workcode = workcode;
		this.runtimeid = runtimeid;
	}

	public String get(String id, String paramname) {
		TWfRelevantvar var = new TWfRelevantvar();
		var.setRuntimeid(id);
		var.setDatafieldid(paramname);
		WorkflowView view = null;
		try {
			view = (WorkflowView) RmiEntry.iv("wfview");
			TWfRelevantvar varpre = view.fetchRelevantVar(id, paramname);
			return varpre.getValuenow();
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return null;

	}

	public String getext(String id, String paramname, String extname) {
		TWfRelevantvar var = new TWfRelevantvar();
		var.setRuntimeid(id);
		var.setDatafieldid(paramname);
		WorkflowView view = null;
		try {
			view = (WorkflowView) RmiEntry.iv("wfview");
			TWfRelevantvar varpre = view.fetchRelevantVar(id, paramname);
			String ext = varpre.getExtendattribute();
			return StringUtils.substringBetween(ext, extname + ":", ";");
		} catch (Exception e) {
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

	public long getl(String id, String paramname) {
		// TODO Auto-generated method stub
		String obj = get(id, paramname);

		try {
			Long value = Long.parseLong(obj);
			return value.longValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1l;
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

	public void set(String id, String paramName, Object value) {
		// 流程视图操作句柄
		WorkflowView view = null;
		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			view = (WorkflowView) RmiEntry.iv("wfview");
			TWfRelevantvar var = view.fetchRelevantVar(id, paramName);

			if (value != null) {
				var.setValuenow(String.valueOf(value));
			} else {
				var.setValuenow("");
			}
			console.updateRelevantvar(var);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public String newInstance(String beanname) {

		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi
					.loadResourceByNatural(beanname);
			String processid = upo.getExtendattribute();
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			return console.newProcess(processid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public String run(String id) {
		return runCore(id, false);
	}

	public String runCore(String id, boolean sync) {
		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			if (runtimeid != null) {
				console.worklistAppBind(workcode, "wf", id);
				console.runSubFlow(id, runtimeid, workcode, sync);
			} else {
				console.runProcess(id);
			}
			return "";// 表示正常启动
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public void setIn(String fieldid, Object value, Object obj) {
		if (obj == null) {
			return;
		}
		List list = (List) obj;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TWfRelevantvar element = (TWfRelevantvar) iter.next();
			if (element.getDatafieldid().equalsIgnoreCase(fieldid)) {
				if (value == null) {
					value = "";
				}
				element.setValuenow(String.valueOf(value));
			}
		}

	}

	public void submit(Object obj) {
		if (obj == null) {
			return;
		}
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.updateRelevantvars((List) obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public Object getInobj(String id) {
		WorkflowView view;
		try {
			view = (WorkflowView) RmiEntry.iv("wfview");
			return view.fetchRelevantVar(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;

	}

	public double getOutd(String fieldId, Object obj) {
		String xv = getOut(fieldId, obj);
		try {
			Double value = Double.parseDouble(xv);
			return value.doubleValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1.0;
		}
	}

	public float getOutf(String fieldId, Object obj) {
		String xv = getOut(fieldId, obj);
		try {
			Float value = Float.parseFloat(xv);
			return value.floatValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1.0f;
		}
	}

	public int getOutn(String fieldId, Object obj) {
		String xv = getOut(fieldId, obj);
		try {
			Integer value = Integer.parseInt(xv);
			return value.intValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1;
		}
	}

	public String getOut(String fieldId, Object obj) {
		if (obj == null) {
			return null;
		}
		List list = (List) obj;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TWfRelevantvar element = (TWfRelevantvar) iter.next();
			if (element.getDatafieldid().equalsIgnoreCase(fieldId)) {

				return element.getValuenow();

			}
		}
		return null;
	}

	public long getOutl(String fieldId, Object obj) {
		String xv = getOut(fieldId, obj);
		try {
			Long value = Long.parseLong(xv);
			return value.longValue();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return -1;
		}
	}

	public Object getOutobj(String id) {
		return getInobj(id);
	}

	public void commitAct(String workcode) throws RemoteException {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.commitActivity(workcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public void commitActAndPoint(String workcode, String actid)
			throws RemoteException {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.commitActivityByManual(workcode, actid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public void commitActAndPoints(String workcode, String[] actid)
			throws RemoteException {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.commitActivityByManual(workcode, actid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public void dropProc(String runtimeid) throws RemoteException {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.dropProcess(runtimeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
	}

	public void initProc(String runtimeid) throws RemoteException {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.initProcess(runtimeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public void updateWorklistStatus(String workcode, String status) {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.updateWorklistStatus(workcode, status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public void worklistAppBind(String workcode, String apptype, String appvalue)
			throws RemoteException {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.worklistAppBind(workcode, apptype, appvalue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public Object getSession(String name) {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			return console.getSessionAttribute(runtimeid, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public void setSession(String name, Object value) {
		WorkflowConsole console;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.setSessionAttribute(runtimeid, name, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	@Override
	public String[] getUser(String workcode) {
		WorkflowView view = null;
		try {
			view = (WorkflowView) RmiEntry.iv("wfview");
			String sqlparticipant="select concat(username,'[',usercode,']') userinfo from netone.t_wf_participant where workcode='"+workcode+"' order by donetime desc";

			List list=view.coreSqlview(sqlparticipant);
			List newlist=new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				
				Map object = (Map) iterator.next();

				String usercode=(String)object.get("userinfo");

				newlist.add(usercode);
			}
			return (String[])newlist.toArray(new String[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public String getCommiter(String workcode) {
		// TODO Auto-generated method stub
		String []user= getUser(workcode);
		if(user!=null&&user.length>0){
			return user[0];
		}
		return null;
	}

}
