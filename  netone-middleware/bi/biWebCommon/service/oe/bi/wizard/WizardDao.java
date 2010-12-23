package oe.bi.wizard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.etl.obj.ChoiceInfo;

/**
 * ChoiceInfo��dao��Ҫ�ǽ�ChoiceInfo����ת��Ϊxml����
 * 
 * @author chen.jia.xun
 * 
 */
public interface WizardDao extends Remote {
	/**
	 * ����,�־û��Ľ��ΪXML�ļ�
	 * 
	 * @param cho
	 */
	void create(ChoiceInfo cho, String natrualname) throws RemoteException;

	boolean checkExist(String natrualname) throws RemoteException;

	void modify(ChoiceInfo cho) throws RemoteException;

	void delete(String lsh) throws RemoteException;

	List list() throws RemoteException;

	String toXml(ChoiceInfo cho) throws RemoteException;

	ChoiceInfo fromXml(String lsh) throws RemoteException;

	/**
	 * ��ʾ�����е�ά��ģ���� <br>
	 * ע�⣺<br>
	 * ά��ģ�������ļ���Ϣ���洢��application�ĸ�Ŀ¼�£�����:tomcat�������о�����binĿ¼�£���bimodel\dimtree
	 * Ŀ¼��
	 * 
	 * @return ���е�ά��ģ����������Ϊ[0][0]modelTreeId��ά��ģ�������ļ�����,[0][1]modelTreeName
	 */
	String[][] listTree() throws RemoteException;

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
	String subTree(String treeModelId, NodeObj curNode) throws RemoteException;

	/**
	 * ʱ����
	 * 
	 * @param curNode
	 *            ��ǰ�Ľڵ�
	 * @return ��ͼ��ǰ��չʾxml�ĵ�(XML��ʽ����ǰ����ͬ����ο�BiTree�ӿ��������)
	 */
	String timeTree(NodeObj curNode) throws RemoteException;

}
