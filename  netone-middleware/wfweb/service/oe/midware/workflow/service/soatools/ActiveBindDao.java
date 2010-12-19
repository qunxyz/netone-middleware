package oe.midware.workflow.service.soatools;

import oe.midware.workflow.client.ActiveBind;
import oe.midware.workflow.client.ScriptObject;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.client.TaskObject;


public interface ActiveBindDao {
	/**
	 * ����id�ҵ���Ӧ�ı�������,�޸�extendattribute����
	 * 
	 * @param id
	 * @param ab
	 * @param task
	 * @param script
	 */
	public void create(String id, ActiveBind ab, TaskObject task,
			ScriptObject script);

	/**
	 * ȡ�����ɵ�SoaObj����
	 * 
	 * @param xml
	 * @return
	 */
	public SoaObj fromXml(String xml);

}
