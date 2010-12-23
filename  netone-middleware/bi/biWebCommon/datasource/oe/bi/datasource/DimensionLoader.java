package oe.bi.datasource;

import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;
/**
 * ά��չ�������߼����
 * 
 * @author chen.jia.xun
 * 
 */

public interface DimensionLoader {
	/**
	 * װ����ͼչ������Ҫ��ҵ������<br>
	 * ע�⣺<br>
	 * ��ҵ������ֻ��һ������������ݣ��ǲ���ԣ�Ҳ����˵ֻ����ͼ�е�һ�������Ϣ��<br>
	 * ע��:<br>
	 * ���ݵ�װ��ģʽ�����ж��ֱ���:SQL����Դ��RMI����...... ��Щװ��ģʽ�ľ���̶����� ���ã�λ��ά��ģ�������ļ����е�
	 * datasource.properites�ļ�������
	 * 
	 * @param param
	 *            modeltreeid ģ������ID��Ҳ�����ļ�����
	 * @param param
	 *            nodeid ��ǰ�����
	 * 
	 * 
	 * @return List ������һ�����е�N��NodeObj�������Ϣ
	 */
	List loadDimension(String modeltreeid, NodeObj node);

	/**
	 * �����е��ӽڵ�
	 * 
	 * @param view
	 * @param node
	 * @param allLevelname
	 * @return
	 */
	List getAllDimension(String view, NodeObj node, String allLevelname);

}
