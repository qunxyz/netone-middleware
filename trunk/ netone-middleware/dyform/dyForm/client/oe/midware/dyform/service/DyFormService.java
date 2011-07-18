package oe.midware.dyform.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.form.TCsForm;

/**
 * 表单应用服务,该服务中提供了表单的获取,表单对应的业务数据的管理
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface DyFormService extends Remote {
	/**
	 * 装载表单
	 * 
	 * @param formid
	 *            表单唯一标识码
	 * @return
	 */
	TCsForm loadForm(String formid) throws RemoteException;

	/**
	 * 装载表单
	 * 
	 * @param formid
	 * @return
	 */
	TCsForm loadFormUrl(String url) throws RemoteException;

	/**
	 * 装载表单字段
	 * 
	 * @param formid
	 * @return
	 */
	List fetchColumnList(String formid) throws RemoteException;

	/**
	 * 装载表单字段
	 * 
	 * @param url
	 * @return
	 */
	List fetchColumnListUrl(String url) throws RemoteException;

	/**
	 * 往表单中添加数据
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	String addData(String formid, TCsBus bus) throws RemoteException;

	/**
	 * 修改表单数据
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	boolean modifyData(TCsBus bus) throws RemoteException;

	/**
	 * 删除数据
	 * 
	 * @param formid
	 * @param id
	 * @return
	 */
	boolean deleteData(String formid, String id) throws RemoteException;

	/**
	 * 查询数据
	 * 
	 * @param bus
	 * @param form
	 * @param to
	 * @param condition
	 * @return
	 */
	List queryData(TCsBus bus, int form, int to, String condition)
			throws RemoteException;

	/**
	 * 装载数据
	 * 
	 * @param id
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	TCsBus loadData(String id, String formcode) throws RemoteException;

	/**
	 * 查询符合条件的记录
	 * 
	 * @param bus
	 * @param condition
	 * @return
	 * @throws RemoteException
	 */
	int queryDataNum(TCsBus bus, String condition) throws RemoteException;

	/**
	 * 获得动态数据的列表标题头信息
	 * 
	 * 
	 * 算法说明: 从Form的xml的other属性中获得[listinfo]对应的数据,如果为空那么值为1,2,3,4,5,6,7,8"
	 * 然后根据listinfo的值(对应的索引位置)去获得FileMapping对象(比如:1对应着第一个,3对应着第三个...获得过程注意要做错误处理,如果索引所对应的FileMapping对象不存在)
	 * 将能获得到的Filemapping对象中放入Map 其中key是 索引值,value为{uuid,name}
	 * 
	 * 
	 * 
	 * @param formcode
	 * @return Map key:tcsColumn.indexvalue,
	 *         value:[]{tcsColumn.columnid,tcsColumn.columnname}
	 */
	public Map fetchTitleInfos(String formcode) throws RemoteException;
}
