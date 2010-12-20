package oe.cav.bean.logic.tools;

import java.sql.Connection;

import java.sql.SQLException;

import oe.frame.orm.OrmerEntry;

public class SQLTools {

	/**
	 * 获取数据库连接
	 */
	public static Connection getConn(String systemid) {
		// 动态表单中的DS也就是TCsForm中的 systemid 对于动态表单来说是没用的
		// 因为动态表单都是基于自己的标准库创建的，DS仅仅是说明该表单的数据来自何方，通常是在SQL表单创建中会存在该字段的值，通常是资源模型中的数据源的naturalname

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
