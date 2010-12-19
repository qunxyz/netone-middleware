package oe.midware.workflow.client;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

/**
 * 工作流会话，在每次个流程实例的执行过程中，用户可以使用session对象来存储业务对象， session的生命周期从流程启动开始到流程执行结束失效
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class Session {

	private String sessionid;

	private Timestamp created;

	private Map attribute = new HashMap();

	public Map description() {
		return attribute;
	}

	public Object getAttribute(String id) {
		return attribute.get(id);
	}

	public void setAttribute(String id, Object obj) {
		WorkflowConsole console = null;
		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		attribute.put(id, obj);
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

}
