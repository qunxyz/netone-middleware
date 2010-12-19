package oe.cms.datasource;

import java.util.List;

/**
 * ���ݳ��ں��Ĳ���
 * 
 * ��ǰ��Ҫʵ��һ������SQL������
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface DataAccessCore {
	/**
	 * �������
	 * 
	 * @param sql
	 *            sql�ı��ʽ
	 * @param column
	 *            ��Ҫ��ѯ���ֶ�
	 * @return ��¼ʹ��Map���洢��KeyΪcolumn[i]��valueΪresultset��ֵ
	 */
	List fetchData(String sql, String[] column);

	/**
	 * �������
	 * 
	 * @param sql
	 *            sql�ı��ʽ
	 * @return ��¼ʹ��Map���洢��KeyΪcolumn[i]��valueΪresultset��ֵ
	 * 
	 */
	List fetchData(String sql);

	/**
	 * ������ݣ�����ʾ
	 * 
	 * @param sql
	 *            sql�ı��ʽ
	 * @param column
	 *            ��Ҫ��ѯ���ֶ�
	 * @param ����ԴID
	 * @return ��¼ʹ��Map���洢��KeyΪcolumn[i]��valueΪresultset��ֵ
	 *         ���е�һ��Map��ֵ�Ǵ洢���Tip����Ϣ��KeyΪ tipId,ValueΪtip��value
	 */
	List fetchDataWithTip(String sql, String[] column, String sourceid);

	/**
	 * ������ݣ�����ʾ
	 * 
	 * @param sql
	 *            sql�ı��ʽ
	 * @param ����ԴID
	 * @return ��¼ʹ��Map���洢��KeyΪcolumn[i]��valueΪresultset��ֵ
	 *         ���е�һ��Map��ֵ�Ǵ洢���Tip����Ϣ��KeyΪ tipId,ValueΪtip��value
	 */
	List fetchDataWithTip(String sql, String sourceid);

}
