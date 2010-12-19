package oe.cms.dao.infocell;

import java.util.Map;

import oe.cms.cfg.TCmsJppmidware;

/**
 * �û��Զ�JPPģ��
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface JppMiddwareDIY {

	TCmsJppmidware load(String id);

	TCmsJppmidware loadByNaturalname(String name);

	Map jppmidwareByUser(String particpant);

	Map jppmidwareAllPublic();

	void initJppmidwarePool();

	void initJppmidwarePoolByUser(String userid);

	void create(TCmsJppmidware jmw, String belongNaturalname);

	void modify(TCmsJppmidware jmw);

	void delete(String id);

}
