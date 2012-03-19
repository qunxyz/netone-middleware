package oe.cav.bean.logic.tools;

import java.sql.Connection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;

import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.frame.orm.util.DbTools;
import oe.frame.orm.util.IdServer;

public class DbScriptTools {

	static Log log = LogFactory.getLog(DbScriptTools.class);

	/**
	 * 创建表
	 * 
	 * @return
	 */
	public static String[] create(String ds) {
		String tableName = "DY_" + IdServer.xnumID();
		// String createScript = "create table "
		// + tableName
		// +
		// "(LSH VARCHAR(32) NOT NULL, FORMCODE VARCHAR(50) NOT NULL,PARTICIPANT
		// VARCHAR(100) NOT NULL,CREATED VARCHAR(30) NOT NULL,FATHERLSH
		// VARCHAR(32),STATUSINFO CHAR(2),EXTENDATTRIBUTE VARCHAR(1000),HIT
		// numeric(10),BELONGX VARCHAR(255),TIMEX TIMESTAMP, PRIMARY KEY
		// (LSH))";

		/**
		 * Linux 发布 SQL 创建/修改表编码问题 如果字段需要输入中文 就会报错 出错信息: Java.sql.SQLException:
		 * Incorrect string value: '\xE9\xBB\x98\xE8\xAE\xA4...' for column
		 * 'BELONG X' at row 1
		 */
		// character set gbk 添加字段编码以GBK保存
		String createScript = "create table "
				+ tableName
				+ "(LSH  VARCHAR(32) NOT NULL, FORMCODE VARCHAR(50) NOT NULL,PARTICIPANT VARCHAR(100) NOT NULL,CREATED  VARCHAR(30)  NOT NULL,FATHERLSH  VARCHAR(32),STATUSINFO  CHAR(2),EXTENDATTRIBUTE  VARCHAR(1000),HIT numeric(10),BELONGX  VARCHAR(2000) character set gbk ,TIMEX TIMESTAMP, PRIMARY KEY (LSH))";

		Connection con = SQLTools.getConn(ds);
		log.debug("create form:" + createScript);
		DbTools.execute(con, createScript);

		return new String[] { tableName, "LSH" };
	}

	/**
	 * 增加字段
	 * 
	 * @param column
	 */
	public static String addColumn(TCsColumn column, String ds) {
		return columnOpe(column, "add", ds);
	}

	public static String dropColumn(TCsColumn column, String ds) {
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		TCsForm formObj = formDao.loadObject(column.getFormcode());

		Connection con = null;
		con = SQLTools.getConn(ds);
		String sql = "alter table " + formObj.getDescription() + " drop "
				+ column.getColumnid();
		log.debug("drop form column:" + sql);
		try {
			DbTools.execute(con, sql);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "";
	}

	/**
	 * 修改字段
	 * 
	 * @param column
	 */
	public static String updateColumn(TCsColumn column, String ds) {
		return columnOpe(column, "modify", ds);
	}

	private static String columnOpe(TCsColumn column, String opeType, String ds) {
		if (column == null || column.getColumncode() == null
				|| column.getFormcode() == null) {
			throw new RuntimeException("无效对象TCsColumn");
		}

		String ext = column.getExtendattribute();
		String sizeinfo = "";
		if (StringUtils.isNotEmpty(ext)) {
			sizeinfo = StringUtils
					.substringBetween(ext, DymaticFormCheck._CHECK_SIZE,
							DymaticFormCheck._FINAL_CHECK);
		}

		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		TCsForm formObj = formDao.loadObject(column.getFormcode());

		log.debug("preprared add form column:" + column.getChecktype() + ","
				+ column.getHtmltype());
		String type = "";

		if ("DECIMAL".equals(column.getHtmltype())
				|| "number".equals(column.getChecktype())) {
			if (StringUtils.isNotEmpty(sizeinfo)
					&& sizeinfo.matches("\\d+,\\d*")) {
				type = "DECIMAL(" + sizeinfo + ")";
			} else {
				type = "DECIMAL(8,4)";
			}
						
		} else if (ColumnExtendInfo._HTML_TYPE_TEXTAREA.equals(column
				.getHtmltype())||ColumnExtendInfo._HTML_TYPE_FCK_ITEM.equals(column
				.getHtmltype())) {
			type = "text " + " character set gbk";
		}else {
        log.debug("sizeinfo:" + sizeinfo);
        if ((StringUtils.isNotEmpty(sizeinfo)) && (sizeinfo.matches("\\d+")))
          type = "varchar(" + sizeinfo + ")" + " character set gbk";
        else {
          type = "varchar(100) character set gbk";
        }

		}
		String addScript = "alter table " + formObj.getDescription() + " "
				+ opeType + " " + column.getColumnid() + " " + type;

		log.debug(addScript);
		Connection con = null;
		con = SQLTools.getConn(ds);
		try {
			System.out.println(addScript);
			DbTools.execute(con, addScript);
		} catch (Exception e) {
			System.err.println(addScript);
			e.printStackTrace();
			return e.getMessage();
		}
		return "完成";
	}
}
