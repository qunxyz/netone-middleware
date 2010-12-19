package oe.cms.dao.infomodel;

import java.util.List;

public interface RichLevel {

	String _LEVEL_A = "3";

	String _LEVEL_B = "2";

	String _LEVEL_C = "1";

	String _LEVEL_WINNER = "4";

	String[] _LEVEL_TOTAL = { _LEVEL_A, _LEVEL_B, _LEVEL_C };

	String _LEVEL_WINNER_NAME = "<strong>黑带冠军</strong>";

	String _LEVEL_A_NAME = "<strong>黑带</strong>";

	String _LEVEL_B_NAME = "<strong><font color='blue'>蓝带</font></strong>";

	String _LEVEL_C_NAME = "<strong><font color='green'>普通</font></strong>";

	int _ANY_QUERY_LIMIT = 10;// 所有查询的上限

	/**
	 * 自行每日自动统计,进级的工作
	 * 
	 */
	void doAutoTask();

	/**
	 * 模糊查询-按名字
	 * 
	 * @param name
	 * @param from
	 * @param to
	 * @return
	 */
	List queryByName(String name);


	List queryLimit(List list, int form, int to);

	/**
	 * 模糊查询-找最新创建的
	 * 
	 * 
	 * @return
	 */
	List queryByNewCreate();

	/**
	 * 模糊查询-找最近更新的
	 * 
	 * 
	 * @return
	 */
	List queryByNewModify();

}
