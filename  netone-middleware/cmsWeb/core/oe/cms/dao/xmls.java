package oe.cms.dao;

/**
 * 对象持久化操作
 * @author chen.jia.xun
 *
 */
public interface xmls  {
	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean create(Object obj);

	/**
	 * 删除业务对象
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean drop(Object obj);

	/**
	 * 更新业务对象
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean update(Object obj);


}
