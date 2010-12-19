package oe.midware.workflow.client;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

/**
 * �������Ự����ÿ�θ�����ʵ����ִ�й����У��û�����ʹ��session�������洢ҵ����� session���������ڴ�����������ʼ������ִ�н���ʧЧ
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
