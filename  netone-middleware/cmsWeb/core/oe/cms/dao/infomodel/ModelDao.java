package oe.cms.dao.infomodel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.TCmsInfomodel;

public interface ModelDao {

	public TCmsInfomodel userinfoModel(String userid, String modelx,
			HttpServletRequest request);

	boolean create(TCmsInfomodel model,String belongto);

	boolean update(TCmsInfomodel model);

	boolean delete(String id, String participant);

	boolean delete(String participant);

	boolean checkExist(String id);

	TCmsInfomodel view(String id);

	TCmsInfomodel viewByUser(String userid);

	// 初始化所有的排名信息（Impl中的所有Map）
	void initview();

	// 初始化所有的排名信息
	void initview(String level);

	// 初始化绿带的所新创建的空间
	void initNewCreateView();

	// 初始化绿带的所有最新被修改过的空间
	void initNewModifyView();

	// 冠军的空间ID
	TCmsInfomodel fetchWinner();

	// 更具模型ID获得名次
	int fetchOrder(String level, String modelid);

	/**
	 * 畅想空间展现界面
	 * 
	 * @param level
	 *            畅想级别（目前有:A，B，C三个级别）
	 * @param from
	 *            从第几个开始
	 * @param to
	 *            到第几个结束
	 * @return
	 */
	List richview(String level, int from, int to);

	/**
	 * 普通的按照创建时间顺序显示
	 * 
	 * @return
	 */
	List richViewByCreateTime();

	/**
	 * 普通的按照最新修改顺序显示
	 * 
	 * @return
	 */
	List richViewByModifyTime();

	/**
	 * 畅想空间总数
	 * 
	 * @param level
	 * @return
	 */
	int totalRichview(String level);

	/**
	 * 支持畅想，投票支持
	 * 
	 * @param index
	 *            这个是指支持对象在 所有的对象List中的位置
	 */
	void supportThis(String level, int index);

	/**
	 * 持久化信息
	 * 
	 * @param level
	 */
	void serialRich(String level);

	List fetchList(String level);

	String fetchHitNum(String level, String modelid);

	//String fetchOrderIndex(String level, String modelid);

	/**
	 * A水平空间，排名更新时间
	 */
	String fetchblogLevelARichSeriTime();

	/**
	 * B水平空间，排名更新时间
	 */
	String fetchblogLevelBRichSeriTime();

	/**
	 * C水平空间，排名更新时间
	 */
	String fetchblogLevelCRichSeriTime();

	/**
	 * 进级A水平空间时间
	 */
	String fetchcomeAlevelTime();

	/**
	 * 进级B水平空间时间
	 */
	String fetchcomeBlevelTime();

	/**
	 * 退到B水平空间时间
	 */
	String fetchbackBlevelTime();

	/**
	 * 退到C水平空间时间
	 */
	String fetchbackClevelTime();

	/**
	 * 冠军评审时间
	 */
	String fetchWinnerTime();

	/**
	 * 进级A水平空间的个数
	 */
	String fetchcomeAlevelNum();

	/**
	 * 在进级A水平空间时间里,需要淘汰到B级空间的倒数空间个数
	 */
	String fetchbackBlevelNum();

	/**
	 * 进级B水平空间的个数
	 */
	String fetchcomeBlevelNum();

	/**
	 * 在进级B水平空间时间里,需要淘汰到C级空间的倒数空间个数
	 */
	String fetchbackClevelNum();

	List listAllModel();

}
