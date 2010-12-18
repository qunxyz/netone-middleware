package oe.teach.mid.buss;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 针对后台业务库表的业务应用逻辑设计<br>
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public interface MyAccountDao {
	/**
	 * 列表数据条件
	 * 
	 * @param request
	 * @return
	 */
	String makeCondition(HttpServletRequest request);

	/**
	 * 统计满足条件的记录总数
	 * 
	 * @param queryinfo
	 *            SQL中Select的where子条件
	 * @return
	 */
	public int totalNum(String queryinfo);

	/**
	 * 查询符合条件的记录
	 * 
	 * @param queryinfo
	 *            SQL中Select的where子条件
	 * @return BussObj对象
	 */
	public List query(String queryinfo, int from, int to);

	/**
	 * 创建MyAccount
	 * 
	 * @param tobj
	 */
	public void create(MyAccount tobj);

	/**
	 * 修改MyAccount
	 * 
	 * @param tobj
	 */
	public void update(MyAccount tobj);

	/**
	 * 删除MyAccount
	 * 
	 * @param tobj
	 *            stuid
	 */
	public void delete(String tobj);

	/**
	 * 批量删除MyAccount
	 * 
	 * @param tobj
	 *            stuid数组
	 */
	public void delete(String[] tobj);

	/**
	 * 装载MyAccount
	 * 
	 * @param id
	 * @return
	 */
	public MyAccount load(String id);
}
