package oesee.teach.java.oop.sample5;

/**
 * ��Ա����DAO���
 * 
 * @author chen.jia.xun <br>
 *         mail: 56414429@qq.com<br>
 *         tel:15860836998
 * 
 */
public interface MenDao {
	/**
	 * ������Ա
	 * 
	 * @param men
	 *            ��Ա����
	 * @return
	 */
	String add(MenInfo men);

	/**
	 * ɾ����Ա
	 * 
	 * @param id
	 *            ��Աid
	 */
	void del(String id);

	/**
	 * �޸���Ա
	 * 
	 * @param men
	 *            ��Ա����
	 */
	void update(MenInfo men);

	/**
	 * ��ѯ��Ա
	 * 
	 * @return
	 */
	MenInfo[] query();

}
