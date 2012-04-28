/**
 * ��˵�� ���ݼ������������� ת��������jsonStore ���Խ��������ݸ�ʽ
 * @author ���� 
 * @version  1.0 
   @time ����ʱ�䣺2009-03-18
 * 
 */

package com.jl.common;

public class StoreUtil {
   
	private static final String RESULT = "total"; //��ʾ�����ܼ�¼��
	
	private static final String ROWS = "rows";//��ʾ����Դ
	
	public StoreUtil(){}
	
	/**
	 * 
	 * @param result �����м�¼����
	 * @param json  List����ת����json�����ַ���
	 * @return
	 */
	public static String StoreString(int result,String json){
		StringBuilder store = new StringBuilder();
		store.append("{");
		store.append(RESULT);
		store.append(" : ");
		store.append(result);
		store.append(",");
		store.append(ROWS);
		store.append(":");
		store.append(json);
		store.append("}");
		return store.toString();
	}
	
	
}
