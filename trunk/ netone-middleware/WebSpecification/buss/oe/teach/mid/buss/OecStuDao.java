package oe.teach.mid.buss;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 针对Oecstudent后台业务库表的业务应用逻辑设计<br>
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public interface OecStuDao {
	/**
	 * 列表数据条件
	 * 
	 * @param request
	 * @return
	 */
	String listCondition(HttpServletRequest request);

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
	public List query(String queryinfo, int form, int to);

	/**
	 * 创建OecStudent
	 * 
	 * @param tobj
	 */
	public void create(OecStudent tobj);

	/**
	 * 批量创建
	 * 
	 * @param tobj
	 */
	public void creates(List<OecStudent> tobj);

	/**
	 * 修改OecStudent
	 * 
	 * @param tobj
	 */
	public void update(OecStudent tobj);

	/**
	 * 批量修改
	 * 
	 * @param tobj
	 */
	public void update(List<OecStudent> tobj);

	/**
	 * 删除OecStudent
	 * 
	 * @param tobj
	 *            stuid
	 */
	public void delete(String tobj);

	/**
	 * 批量删除OecStudent
	 * 
	 * @param tobj
	 *            stuid数组
	 */
	public void delete(String[] tobj);

	/**
	 * 装载OecStudent
	 * 
	 * @param id
	 * @return
	 */
	public OecStudent load(String id);
}
