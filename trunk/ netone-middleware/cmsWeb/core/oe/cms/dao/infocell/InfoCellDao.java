package oe.cms.dao.infocell;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.TCmsInfocell;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public interface InfoCellDao {
	/**
	 * 预处理,主要是融合资源的内容来生成页的展现数据
	 * 
	 * @param cellid
	 * @return
	 */
	TCmsInfocell viewPreOperation(String cellid, HttpServletRequest req);
	/**
	 * 给ext用的
	 * 
	 * @param cellid
	 * @return
	 */
	TCmsInfocell view4ext(String cellid, HttpServletRequest req);


	/**
	 * 根据查询的资讯元SQL取得 资讯元列表
	 * 
	 * @return
	 */
	List searchCellList(String sql, String participant, int from, int to);

	/**
	 * 根据查询的资讯元SQL查询 取得 资讯元列表的总数
	 * 
	 * @param name
	 * @return
	 */
	int searchCellNum(String sql, String participant);

	/**
	 * 获取资讯元列表
	 * 
	 * @return
	 */
	List getCellToolList(String belongto, String participant);

	/**
	 * 获取图形列表
	 * 
	 * @return
	 */
	public List getChartList(String[][] chartStr);

	public boolean create(TCmsInfocell cell, String type, String bodyinfo);

	public boolean update(TCmsInfocell cell, String bodyinfo);

	public TCmsInfocell view(String cellid);

	public UmsProtectedobject viewRS(String cellid);

	/**
	 * 根据cellid 查找资源2<br>
	 * 解决基于模版创建,对应复制创建的页找不到的问题,主要是资源查找的问题,改用loadResourceByNatural API进行查找
	 * 
	 * @author don (cai.you.dun) create by 2009-5-15
	 * @param cellid
	 * @return
	 */
	public UmsProtectedobject viewRS2(String cellid);

	public boolean delete(String id, String participant);

	public List queryByType(String userid, String cellTypes);

}
