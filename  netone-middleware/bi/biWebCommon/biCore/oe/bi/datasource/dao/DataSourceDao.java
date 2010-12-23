package oe.bi.datasource.dao;

import java.util.List;

import oe.bi.datasource.obj.Datasource;


/**
 * ����ԴDAO�ӿ� �ýӿ��ж���������Դ�Ĵ������޸ġ�ɾ������ѯ��װ�صĲ�����
 * 
 * ʵ��˵��: ��������Դ��DAO�ĳ־û�Ŀ��,������ʵ������. 1)���ݿ�ʵ�� PRI (LOW) 2)XMLʵ�� PRI (High)
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface DataSourceDao {
	/**
	 * ��������Դ
	 * 
	 * @param tBiDatasource
	 *            ����Դ����
	 */
	void create(Datasource tBiDatasource);

	/**
	 * ��������Դ
	 * 
	 * @param tBiDatasource
	 */
	void update(Datasource tBiDatasource);

	/**
	 * ɾ������Դ
	 * 
	 * @param tBiDatasource
	 */
	void drop(Datasource tBiDatasource);

	/**
	 * ��ѯ
	 * 
	 * @return
	 */
	List query(String condition);
	/**
	 * װ������
	 * @param id
	 * @return
	 */
	Datasource load(String id);

}
