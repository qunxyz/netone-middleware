package oe.cms.xhtml2;

/**
 * Oesee ҵ������(��Ҫ��Ա������̵�����)
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface BusinessInterface {
	
	
	/**
	 * ����Oec������Ϣ
	 * 
	 * @param urlx
	 *            ��ַ Oesee��̳��ַ
	 *            /cavserweb/data/showdata/subListviewslink.do?lsh=1&formcode=
	 * @param limitrecordnum
	 *            ��ʾ�ļ�¼����
	 * @param wordnum
	 *            ��ʾ�Ĵʸ���
	 * @return
	 */
	public String oecForum(String urlBase, String limitrecordnum, String wordnum);

	/**
	 * ͳ�ƾ����е��ֵĸ��� ע�⵽����java������Ӣ�Ļ������Ķ���Ϊ��һ���֣�����������html����ʾ��
	 * ʱ������ȴ��Ӣ�ĵ�������Ϊ���ܸ��ÿ���web�ϵľ��ӵĳ��ȣ�������Ҫ������� ��ͳ�ƾ��ӵ����� ���У�����Ӣ�ĵ��ʼ�1,�������ʼ�2
	 * 
	 * @param senstance
	 * @param limit
	 * @return
	 * 
	 * ע�⣺�÷��������Դ�����ʽ����ʽʵ���ϲ���ռ����ʾλ�ã������ڷ�����ʱ��Ҫ��ʶ����� Ŀǰ��ʶ����˵���ʽֻ��һ��2�� font ģʽ ��
	 * strong��
	 * 
	 */
	String _fontStyle = "<font\\s+color='#?[A-Za-z0-9]+'>";

	String _fontStyleE = "</font>";

	String _strongStyle = "<strong>";

	String _strongStyleE = "</strong>";

	public String limitDispSenc(String senstance, int limit);

}
