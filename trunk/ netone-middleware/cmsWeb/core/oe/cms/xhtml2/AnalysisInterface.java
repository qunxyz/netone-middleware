package oe.cms.xhtml2;

/**
 * �������<br>
 * ��Ҫ��������Դ�Ļ����ϣ���һ���ĸ������ݣ�����Ľ��Ϊ���յ�����չ�� �ṩ����
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface AnalysisInterface {
	/**
	 * ���ӷ������ݵ�ά��
	 * 
	 * @param dimOri
	 *            ����ά��
	 * @param targetOri
	 *            ָ����
	 * @param dimNew
	 *            ά����
	 * @return
	 */
	String[][] addDim(String[][] dimOri, String[][] targetOri, String[] dimNew);

	/**
	 * ���β���ά�Ⱥ�ָ�꣬���ܵ����ж���
	 * 
	 * @param dim
	 *            ά����
	 * @param tar
	 *            ָ����
	 * @return ��һ��Ԫ���� ͳһ��ά�ȣ����������ǰ��ղ���˳���ָ��
	 */
	String[][] adpetTargetDim(String[][] dim, String target[][]);

	/**
	 * ����ת��
	 * 
	 * @param relation
	 * @return
	 */
	String[][] circleRelationCoordinate(String[][] relation);


	// topN,buttonN,Limit,orderAbs,orderDesc,forcase

}
