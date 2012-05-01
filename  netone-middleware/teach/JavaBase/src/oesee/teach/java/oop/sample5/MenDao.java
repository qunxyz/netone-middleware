package oesee.teach.java.oop.sample5;

/**
 * 人员操作DAO设计
 * 
 * @author chen.jia.xun <br>
 *         mail: 56414429@qq.com<br>
 *         tel:15860836998
 * 
 */
public interface MenDao {
	/**
	 * 增加人员
	 * 
	 * @param men
	 *            人员对象
	 * @return
	 */
	String add(MenInfo men);

	/**
	 * 删除人员
	 * 
	 * @param id
	 *            人员id
	 */
	void del(String id);

	/**
	 * 修改人员
	 * 
	 * @param men
	 *            人员对象
	 */
	void update(MenInfo men);

	/**
	 * 查询人员
	 * 
	 * @return
	 */
	MenInfo[] query();

}
