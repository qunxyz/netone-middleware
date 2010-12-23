package oe.bi.datasource;

import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;


/**
 * ָ�꽻���߼����
 * 
 * @author chen.jia.xun
 * 
 */
public interface TargetAct {
	/**
	 * ���ָ����<br>
	 * ͨ����������node�����е�levelname����ͳһָ�������ļ���Ѱ������ ObjectTypeΪlevelname��Ԫ�أ� Ȼ�󷵻�
	 * ��ص����ֺ�ID <br>
	 * ע�⣺<br>
	 * ͳһָ�������ļ����洢��application�ĸ�Ŀ¼�£�����:tomcat�������о�����binĿ¼�£���bimodel\ Ŀ¼�� <br>
	 * ע��:<br>
	 * ����node����������ά��ѡ�����е�����һ��node����Ϊÿ��ѡ���ά�Ƚ�������е�node�ڵ��levelname����һ���ģ�
	 * ��Ȼ����������һ��ͳһԼ�������node����ȡ����ά��ѡ�����еĵ�һ��node
	 * 
	 * @param dimType
	 *            ά������ID
	 * @return ���е�ָ���飬����Ϊ[0][0]targetId,[0][1]targetName
	 */

	String[][] targetGroupList(NodeObj node);

	/**
	 * ����ָ����ID���,��ָ�����е��������е�ָ��Ԫ��
	 * 
	 * ע�⣺<br>
	 * ͳһָ�������ļ����洢��application�ĸ�Ŀ¼�£�����:tomcat�������о�����binĿ¼�£���bimodel\ Ŀ¼�� <br>
	 * ע��:<br>
	 * ָ��Ԫ���п��ܴ���ά��Ԫ�أ�����ݾ���ı�־λ˵��������֮
	 * 
	 * @param targetGroupid
	 *            ָ����ID
	 * @return ָ��Ԫ������,����ÿ��Ԫ��ΪTargetObj����
	 */
	List targetElementList(String targetGroupid);

}
