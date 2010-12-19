package oe.frame.bus.workflow.rule;

import java.rmi.RemoteException;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;

public interface DyScriptFunction extends ScriptFunction {

	/**
	 * 删除数据
	 * 
	 * @param recordid
	 *            是 lsh:formcode
	 * @return
	 */
	boolean deleteData(String recordid);

	/**
	 * 查询数据
	 * 
	 * @param name
	 *            动态表单的naturalname
	 * @param bus
	 * @param form
	 * @param to
	 * @param condition
	 * @return
	 */
	List queryData(String name, TCsBus bus, int form, int to, String condition);

	/**
	 * 查询符合条件的记录
	 * 
	 * @param bus
	 * @param condition
	 * @return
	 * @throws RemoteException
	 */
	int queryDataNum(String name, TCsBus bus, String condition);

	/**
	 * 创建一个应用实例,使用formcode编码非naturalname
	 * 
	 * @param formcode
	 * @return lsh
	 */
	String newInstanceByCode(String formcode);

}
