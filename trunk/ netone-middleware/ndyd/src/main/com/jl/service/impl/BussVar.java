/**
 * 
 */
package com.jl.service.impl;

/**
 * Ӧ��ϵͳ����
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-1-10 ����05:15:13
 * @history
 */
public class BussVar {

	/** ��֯�������� */
	/** �� */
	public final static String BUSSTYPE_0 = "";
	/** ʡ��˾ */
	public final static String BUSSTYPE_1 = "1";
	/** ��Ӫ���� */
	public final static String BUSSTYPE_2 = "2";
	/** Ӫ���� */
	public final static String BUSSTYPE_3 = "3";
	/** ������ */
	public final static String BUSSTYPE_4 = "4";
	/** ������ */
	public final static String BUSSTYPE_5 = "5";
	/** ҵ������ */
	public final static String BUSSTYPE_X = "x";
	/** �ֿ� */
	public final static String BUSSTYPE_S = "s";
	public final static String BUSSTYPE_S1 = "s1";// ʡ��˾�ֿ�����
	public final static String BUSSTYPE_S2 = "s2";// �����ֿ̲�����
	/** Ŀ¼ */
	public final static String BUSSTYPE_D = "-1";

	/** ����������ݱ��� */
	/** ʡ��˾ */
	public final static String GROUPBY_1 = "1";
	/** ��Ӫ���� */
	public final static String GROUPBY_2 = "2";
	/** Ӫ���� */
	public final static String GROUPBY_3 = "3";
	/** ������ */
	public final static String GROUPBY_4 = "4";
	/** ������ */
	public final static String GROUPBY_5 = "5";
	/** ҵ������ */
	public final static String GROUPBY_X = "x";
	/** ҵ������ */
	public final static String GROUPBY_T = "type";
	/** �� */
	public final static String GROUPBY_P_1 = "p1";
	/** ��Ʒ���� */
	public final static String GROUPBY_P_2 = "p2";
	/** ��Ʒ */
	public final static String GROUPBY_P_3 = "p3";

	/** �������� */
	/** �������� */
	public final static String INDENT_TYPE_1 = "1";
	/** ����þ� */
	public final static String INDENT_TYPE_2 = "2";
	/** �˻����� */
	public final static String INDENT_TYPE_3 = "3";
	/** ����þ��˻��� */
	public final static String INDENT_TYPE_4 = "4";

	/** �ƻ����� */
	/** �����ƻ� */
	public final static String PLAN_TYPE_1 = "1";
	/** ׷�Ӽƻ� */
	public final static String PLAN_TYPE_2 = "2";
	/** ȡ���ƻ� */
	public final static String PLAN_TYPE_3 = "3";
	/** �ƻ����� */
	public final static String PLAN_TYPE_4 = "4";

	public final static String SESSION_USER = "_SESSION_USER";

	/**
	 * ����ҵ������
	 * 
	 * @param type
	 * @return
	 */
	public static String returnBussType(String type) {
		String typeName = "";
		if (BUSSTYPE_1.equals(type)) {
			typeName = "ʡ��˾";
		} else if (BUSSTYPE_2.equals(type)) {
			typeName = "��Ӫ����";
		} else if (BUSSTYPE_3.equals(type)) {
			typeName = "Ӫ����";
		} else if (BUSSTYPE_4.equals(type)) {
			typeName = "������";
		} else if (BUSSTYPE_5.equals(type)) {
			typeName = "������";
		} else if (BUSSTYPE_S.equals(type)) {
			typeName = "�ֿ�";
		} else if (BUSSTYPE_D.equals(type)) {
			typeName = "����";
		} else {
			typeName = "δ֪";
		}
		return typeName;
	}

	/**
	 * ���ض�������
	 * 
	 * @param type
	 * @return
	 */
	public static String returnIndentType(String type) {
		String typeName = "";
		if (INDENT_TYPE_1.equals(type)) {
			typeName = "��������";
		} else if (INDENT_TYPE_2.equals(type)) {
			typeName = "����þ�";
		} else if (INDENT_TYPE_3.equals(type)) {
			typeName = "�˻�����";
		} else if (INDENT_TYPE_4.equals(type)) {
			typeName = "����þ��˻���";
		} else {
			typeName = "δ֪";
		}
		return typeName;
	}

	/**
	 * ���ؼƻ�����
	 * 
	 * @param type
	 * @return
	 */
	public static String returnPlanType(String type) {
		String typeName = "";
		if (PLAN_TYPE_1.equals(type)) {
			typeName = "�����ƻ�";
		} else if (PLAN_TYPE_2.equals(type)) {
			typeName = "׷�Ӽƻ�";
		} else if (PLAN_TYPE_3.equals(type)) {
			typeName = "ȡ���ƻ�";
		} else if (PLAN_TYPE_4.equals(type)) {
			typeName = "�ƻ�����";
		} else {
			typeName = "δ֪";
		}
		return typeName;
	}

	/**
	 * ȡ��������
	 * 
	 * @param x
	 * @return
	 */
	public static String getFinanceDirection(Double value) {
		if (value > 0) {
			return "��";
		} else if (value == 0) {
			return "ƽ";
		} else if (value < 0) {
			return "��";
		}
		return "";
	}

	/**
	 * ת������
	 * 
	 * @param value
	 * @return
	 */
	public static double convertPlus(Double value) {
		if (value < 0) {
			return -1 * value;
		}
		return value;
	}

}
