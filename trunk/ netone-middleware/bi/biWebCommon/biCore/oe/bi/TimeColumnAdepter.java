package oe.bi;

/**
 * 
 * @author chen.jia.xun
 *
 */
public interface TimeColumnAdepter {
	/**
	 * ���� ά�����Ͳ��� ����ά�ȿ��ܴ��ڵ�����: SqlTypes�еĳ���Number������,����Ķ�����Ϊ�ַ���������
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public String adaptStringNumberDimensionTypes(String dimensionvalue,
			String type);

	/**
	 * ���� ά�����Ͳ��� ����ά�ȿ��ܴ��ڵ�����: SqlTypes�еĳ���Number������,����Ķ�����Ϊ�ַ���������
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public String adaptDateDimensionTypes(String dimensionvalue, String dimId);
}
