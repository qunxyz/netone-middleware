package oe.bi.common.db;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

import oe.bi.datasource.obj.Datasource;


/**
 * ���ݿ��ѯ�������
 * 
 * �ڲ�����˵��:���ݿ��ѯ�������,�ṩ�������ݲ�ѯ�����ľ��. ���Ƕ�������ִ��ָ����SQL���,���ҷ���ResultSet(forward only
 * ,read only) �������. ����,ÿ�������������,����Ӧ��һ��Connection,���Connection��ϵͳ������ʼ,ֱ��ϵͳ�ر��ڼ���Զ
 * ֻ����һ��ʵ��,��������ر�(�����һ�����ݿ����,��ôҪ���´���һ��).������Stament,preparedStatement,DatabaseMetaData
 * ʹ���߱����ֹ��ر�,����ִ�н��Resultsetʹ�ý�����Ҳ�����ֹ��ر�
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface DBHandler {
	/**
	 * ���SQLִ��״̬���
	 * 
	 * @param datasourceObj
	 * @return
	 */
	Statement fetchHanderStatement(Datasource datasourceObj);

	/**
	 * ��ô�Ԥ�Ƶ�SQLִ��״̬���
	 * 
	 * @param datasourceObj
	 * @param sql
	 * @return
	 */
	PreparedStatement fetchHanderPreparedStatement(Datasource datasourceObj,
			String sql);

	/**
	 * �������Դ��DatabaseMetaData
	 * 
	 * @param datasourceObj
	 * @return
	 */
	DatabaseMetaData fetchDatabaseMetaData(Datasource datasourceObj);

}
