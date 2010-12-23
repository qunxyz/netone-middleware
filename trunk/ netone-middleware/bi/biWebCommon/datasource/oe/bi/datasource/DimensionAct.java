package oe.bi.datasource;

import oe.bi.dao.ui.obj.NodeObj;

/**
 * ά�Ƚ����߼����
 * 
 * @author chen.jia.xun
 * 
 */
public interface DimensionAct {

	/**
	 * ��ʾ�����е�ά��ģ���� <br>
	 * ע�⣺<br>
	 * ά��ģ�������ļ���Ϣ���洢��application�ĸ�Ŀ¼�£�����:tomcat�������о�����binĿ¼�£���bimodel\dimtree
	 * Ŀ¼��
	 * 
	 * @return ���е�ά��ģ����������Ϊ[0][0]modelTreeId��ά��ģ�������ļ�����,[0][1]modelTreeName
	 */
	String[][] listTree();

	/**
	 * ��ͼչ�������еĽ�����Ŀ������չ����ǰ�ڵ����������Ϣ
	 * 
	 * ע�⣺<br>
	 * ά��ģ�������ļ���Ϣ���洢��application�ĸ�Ŀ¼�£�����:tomcat�������о�����binĿ¼�£���bimodel\dimtree
	 * Ŀ¼��,����treeModelId��ά��ģ�������ļ��������Զ�λ�������ģ���� <br>
	 * ע�⣺<br>
	 * ��ͼ�ľ���ҵ�����ݣ��ӽӿ�DimensionLoader�е�loadDimension()�����л��
	 * 
	 * @param treeModelId
	 *            ��ͼ��Ӧ��ģ��ID
	 * @param curNode
	 *            ��ǰ�Ľڵ�
	 * @return ��ͼ��ǰ��չʾxml�ĵ�(XML��ʽ����ǰ����ͬ����ο�BiTree�ӿ��������)
	 */
	String subTree(String treeModelId, NodeObj curNode);

	/**
	 * ʱ����
	 * 
	 * @param curNode
	 *            ��ǰ�Ľڵ�
	 * @return ��ͼ��ǰ��չʾxml�ĵ�(XML��ʽ����ǰ����ͬ����ο�BiTree�ӿ��������)
	 */
	String timeTree(NodeObj curNode);

}
