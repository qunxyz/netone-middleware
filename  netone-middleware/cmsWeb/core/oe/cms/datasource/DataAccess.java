package oe.cms.datasource;

import java.util.List;

/**
 * ���ݳ���
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface DataAccess {
	/**
	 * ͨ������ԴID����ø�����Դ����ʵ����
	 * 
	 * @param sourceid
	 * @return ���ؽ��List��Ԫ����Map�� ListԪ�صĸ�����������������һ����Ԫ���Ƕ�����List�е�Ԫ��ֵ�ı�ע��Ϣ,����SQLID.ColumnID.ֵ��ΪKEY
	 * 
	 * Ŀǰ��Ҫʵ��һ������SQL��ʵ���� ����sourceid�ĸ�ʽ��SQLID.ColumnID,��Ҫ����������IDȡ��ȡSQL�����ֶ�
	 * Ȼ�����DataAccess�ķ��������û���SQL������������ݷ���
	 */
	List fetchData(String sourceid);

}
