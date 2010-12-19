package oe.cms.dao.blog;

import oe.cms.cfg.TCmsInfomodel;

public interface LayoutX {
	/**
	 * 改变布局
	 * 
	 * @param userid
	 * @return
	 */
	boolean copyLayOut(String userid, TCmsInfomodel cim);

	boolean initTemplate();

	/**
	 * 拷贝创建模板
	 * 
	 * @param cim
	 * @return
	 */
	boolean copyToTemplate(TCmsInfomodel cim);

}
