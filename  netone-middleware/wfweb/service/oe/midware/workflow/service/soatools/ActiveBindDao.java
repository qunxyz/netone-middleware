package oe.midware.workflow.service.soatools;

import oe.midware.workflow.client.ActiveBind;
import oe.midware.workflow.client.ScriptObject;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.client.TaskObject;


public interface ActiveBindDao {
	/**
	 * 根据id找到对应的保护对象,修改extendattribute属性
	 * 
	 * @param id
	 * @param ab
	 * @param task
	 * @param script
	 */
	public void create(String id, ActiveBind ab, TaskObject task,
			ScriptObject script);

	/**
	 * 取得生成的SoaObj对象
	 * 
	 * @param xml
	 * @return
	 */
	public SoaObj fromXml(String xml);

}
