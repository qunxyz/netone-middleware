package oe.teach.mid.bussmap;

import java.util.List;
import java.util.ResourceBundle;

import oe.frame.web.db.DbTools;

/**
 * 公共类实现配置方式的SQL应用
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class CommSelecTools {

	public  List fetchdata(String name, Class classinfo) {
		ResourceBundle rs = ResourceBundle.getBundle("sql");
		try {
			String sql = rs.getString(name);
			return DbTools.queryData(sql, classinfo);
		} catch (Exception e) {
			throw new RuntimeException(name
					+ " is not exist in sql.proerties");
		}

	}

	public  List fetchdata(String name) {
		ResourceBundle rs = ResourceBundle.getBundle("sql");
		try {
			String sql = rs.getString(name);
			return DbTools.queryData(sql);
		} catch (Exception e) {
			throw new RuntimeException(name
					+ " is not exist in config.proerties");
		}

	}

}
