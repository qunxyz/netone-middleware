/**
 * 
 */
package com.jl.dao;

import java.util.Map;

import oe.serialize.dao.DAOException;
import oe.serialize.dao.PageInfo;

/**
 * ������־����DAO�ӿ�
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 25, 2010 create by Don
 * @history
 */
public interface LogDAO {

	// �洢����
	/**
	 * ������־ (���� ��̬������־�� ����Ϊ��λ)(���ô洢����)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	Object insertLog(Object object) throws Exception;

	/**
	 * ��־��ѯ ���ô洢����(����ѯ����ҳ)
	 * 
	 * @param condition
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageInfo findLog(Map condition) throws Exception;

}
