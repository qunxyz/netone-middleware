package oe.cms.dao;

/**
 * ����־û�����
 * @author chen.jia.xun
 *
 */
public interface xmls  {
	/**
	 * �������
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean create(Object obj);

	/**
	 * ɾ��ҵ�����
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean drop(Object obj);

	/**
	 * ����ҵ�����
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean update(Object obj);


}
