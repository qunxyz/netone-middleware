package oe.cav.bean.logic.tools;

import java.sql.Connection;

import java.sql.SQLException;

import oe.frame.orm.OrmerEntry;

public class SQLTools {

	/**
	 * ��ȡ���ݿ�����
	 */
	public static Connection getConn(String systemid) {
		// ��̬���е�DSҲ����TCsForm�е� systemid ���ڶ�̬����˵��û�õ�
		// ��Ϊ��̬�����ǻ����Լ��ı�׼�ⴴ���ģ�DS������˵���ñ����������Ժη���ͨ������SQL�������л���ڸ��ֶε�ֵ��ͨ������Դģ���е�����Դ��naturalname

		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}
