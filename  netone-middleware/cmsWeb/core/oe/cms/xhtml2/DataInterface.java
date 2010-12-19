package oe.cms.xhtml2;

/**
 * �������(�����ȡ���ԭʼ��������Ϣ)
 * 
 * Ŀǰ��Ҫ������Դ���������ط� <br>
 * 1��htmlҳ�棬�������Ϣͨ�� ������ʽ����ȡ<br>
 * 2: sql���ݿ⣬�������Ϣͨ��sql�������ȡ
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface DataInterface {

	/**
	 * ��ȡhtml��Ϣ
	 * 
	 * @param url:
	 *            url��ַ
	 * @param encode:
	 *            ���ݱ���
	 * @return url��Ӧ��html��Ϣ
	 */
	String insect(String url, String encode);

	/**
	 * ������ʽ
	 * 
	 * @param info
	 *            �ַ���Ϣ
	 * @param regularRule
	 *            ������ʽ
	 * @return
	 */
	String[] regular(String info, String regularRule);

	/**
	 * ������ʽ
	 * 
	 * @param info
	 *            �ַ���Ϣ
	 * @param regularRule
	 *            ������ʽ
	 * @return
	 */
	String[] regular(String info, String regularRule, String regularRule1);

	/**
	 * ִ��SQL�������
	 * 
	 * @param sql
	 *            sql���ʽ
	 * @return
	 */
	String[][] executeSql(String sql);

	/**
	 * ִ�д�������SQL�������
	 * 
	 * @param sql
	 *            ��������SQL,����ΪĳЩ�����ֶ�Ϊ?
	 *            SQL�еĲ���ֵ����Ҫע����ǣ��������ֵ���ַ��Ļ���Ҫ��''�����������ʱ�����Ҫto_date('yyyy-mm-nn
	 *            ','');
	 * @return
	 */
	String[][] executePrepareSql(String sql, String[] paramervalue);

	/**
	 * ʹ���ڲ����ݿ�
	 * 
	 * @param sql
	 *            �����SQL���,����Ƿǲ�ѯSQL��ô���ص�����һ��ֻ��1��ά�ȵ�1����¼�Ķ�ά����,���汣����SQL�Ķ����ݿ���µ�����
	 *            ����ǲ�ѯSQL,��ô���ؽ�����������ζ�Ӧ��SQL�еĲ�ѯ�ֶ�,�и������ζ�Ӧ�Ĳ�ѯ���
	 * @return
	 */
	String[][] useTempDb(String sql);

}
