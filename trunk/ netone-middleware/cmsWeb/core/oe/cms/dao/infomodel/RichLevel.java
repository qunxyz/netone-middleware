package oe.cms.dao.infomodel;

import java.util.List;

public interface RichLevel {

	String _LEVEL_A = "3";

	String _LEVEL_B = "2";

	String _LEVEL_C = "1";

	String _LEVEL_WINNER = "4";

	String[] _LEVEL_TOTAL = { _LEVEL_A, _LEVEL_B, _LEVEL_C };

	String _LEVEL_WINNER_NAME = "<strong>�ڴ��ھ�</strong>";

	String _LEVEL_A_NAME = "<strong>�ڴ�</strong>";

	String _LEVEL_B_NAME = "<strong><font color='blue'>����</font></strong>";

	String _LEVEL_C_NAME = "<strong><font color='green'>��ͨ</font></strong>";

	int _ANY_QUERY_LIMIT = 10;// ���в�ѯ������

	/**
	 * ����ÿ���Զ�ͳ��,�����Ĺ���
	 * 
	 */
	void doAutoTask();

	/**
	 * ģ����ѯ-������
	 * 
	 * @param name
	 * @param from
	 * @param to
	 * @return
	 */
	List queryByName(String name);


	List queryLimit(List list, int form, int to);

	/**
	 * ģ����ѯ-�����´�����
	 * 
	 * 
	 * @return
	 */
	List queryByNewCreate();

	/**
	 * ģ����ѯ-��������µ�
	 * 
	 * 
	 * @return
	 */
	List queryByNewModify();

}
