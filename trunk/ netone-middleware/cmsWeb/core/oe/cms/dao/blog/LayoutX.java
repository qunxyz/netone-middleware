package oe.cms.dao.blog;

import oe.cms.cfg.TCmsInfomodel;

public interface LayoutX {
	/**
	 * �ı䲼��
	 * 
	 * @param userid
	 * @return
	 */
	boolean copyLayOut(String userid, TCmsInfomodel cim);

	boolean initTemplate();

	/**
	 * ��������ģ��
	 * 
	 * @param cim
	 * @return
	 */
	boolean copyToTemplate(TCmsInfomodel cim);

}
