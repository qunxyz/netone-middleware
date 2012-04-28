package com.jl.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import oe.serialize.dao.PageInfo;

/**
 * �������ݷ��ʶ���ӿ�<BR>
 * Ŀǰ��ʱ��֧�ֶ�����Դ����(������),����DAO�����������������ʵ��
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Apr 6, 2010 create by Don
 * @history
 */
public abstract interface CommonDAO {

	/**
	 * ����
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract Object insert(String statement, Object object) throws Exception;

	/**
	 * ��������
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract int insertBatch(String statement, List entitySet) throws Exception;

	/**
	 * ����
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract Object update(String statement, Object object) throws Exception;

	/**
	 * ɾ��
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract Object delete(String statement, Object object) throws Exception;

	/**
	 * ����ɾ��
	 * 
	 * @param statement
	 * @param entitySet
	 * @throws Exception
	 */
	public abstract int deleteBatch(String statement, List entitySet) throws Exception;

	/**
	 * ��������
	 * 
	 * @param statement
	 * @param entitySet
	 * @throws Exception
	 */
	public abstract int updateBatch(String statement, List entitySet) throws Exception;

	/**
	 * ���ض��� ������int ������string Ҳ������һ������
	 * 
	 * @param statement
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	public abstract Object findForObject(String statement, Object Object) throws Exception;

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param totalStatement
	 * @param resultStatement
	 * @param condition
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract PageInfo selectForPage(String totalStatement, String resultStatement,
			Map condition, PageInfo page) throws Exception;

	/**
	 * ��ͨ��ѯ
	 * 
	 * @param statement
	 * @param object
	 *            implements Collection
	 * @return
	 * @throws Exception
	 */
	public abstract Collection select(String statement, Object object) throws Exception;
}
