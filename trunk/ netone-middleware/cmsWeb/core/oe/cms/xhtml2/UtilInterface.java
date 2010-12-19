package oe.cms.xhtml2;

/**
 * ������
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface UtilInterface {
	/**
	 * ���������Ϣ(�Զ����������,�±��洦������)
	 * 
	 * @param arr
	 *            ����
	 * @param index
	 *            �±�
	 * @return ��������·��������е�ֵ,������Ϊ��ʱ����,���±��洦��ʱ�򷵻�-1
	 */
	String arrVar(String[] arr, int index);

	/**
	 * ���������Ϣ(�Զ����������,�±��洦������)
	 * 
	 * @param arr
	 *            ����
	 * @param x
	 *            �±�x
	 * @param y
	 *            �±�y
	 * @return ��������·��������е�ֵ,������Ϊ��ʱ����,���±��洦��ʱ�򷵻�-1
	 */
	String arrVar(String[][] arr, int x, int y);

	/**
	 * ����long����
	 * 
	 * @param value
	 * @return
	 */
	long parLong(String value);

	/**
	 * ����double����
	 * 
	 * @param value
	 * @return
	 */
	double parDouble(String value);

	/**
	 * ����float����
	 * 
	 * @param value
	 * @return
	 */
	float parFloat(String value);

	/**
	 * ����int����
	 * 
	 * @param value
	 * @return
	 */
	int parInt(String value);



}
