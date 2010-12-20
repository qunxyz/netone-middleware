package oe.cav.bean.logic.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Session;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.reference.DyReference;
import oe.cav.bean.logic.tools.reference.GetDateTime;
import oe.cav.bean.logic.tools.reference.GetPK;
import oe.cav.bean.logic.tools.reference.XMLReference;
import oe.frame.orm.util.IdServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 从数据库读取表和字段来构建DyObj对象， 有3中构造方式， 第1种是通过数据库名把该数据库中所有的表及其字段构造成DyObj对象，
 * 第2种是是通过数据库名和表名把所选中的表及其字段构造成DyObj对象， 第3种是是通过Map把其中的表及其字段构造成DyObj对象
 * 
 * @author admin
 * 
 */
public class DyObjFromDatabaseImpl implements DyObjFromDatabase {

	static Log log = LogFactory.getLog(DyObjFromDatabaseImpl.class);

	public DyObj[] parser(String ds) {
		if (ds == null) {
			log.info("使用默认DS");

		}
		DyObj[] dfo = null;
		String extendAttribute = "";
		Session session = null;
		Connection conn = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			conn = SQLTools.getConn(ds);
			rs1 = conn.getMetaData().getTables(null, "%", "%",
					new String[] { "TABLE" });
			if (rs1.last()) {
				dfo = new DyObj[rs1.getRow()];
			}
			rs1 = conn.getMetaData().getTables(null, "%", "%",
					new String[] { "TABLE" });
			while (rs1.next()) {
				String table = rs1.getString("TABLE_NAME");
				// 封装TCsForm对象
				TCsForm tcf = new TCsForm();
				tcf.setButinfo(DyReference.BUTINFO_VALUE);
				tcf.setCreated(GetDateTime.getDateTime());
				tcf.setDescription(table);
				// tcf.setDesigner(GetPK.getPKColumn(conn, table));
				tcf.setExtendattribute("");
				String formcode = IdServer.uuid() + "_" + ds;
				tcf.setFormcode(formcode);
				tcf.setFormname(table);
				tcf.setListinfo(DyReference.LISTINFO_VALUE);
				tcf.setOrderinfo(DyReference.ORDERINFO_VALUE);
				tcf.setParticipant("");
				tcf.setStatusinfo("");
				tcf.setSubform(DyReference.SUBFORM_VALUE);
				tcf.setSystemid(ds);
				tcf.setTypeinfo(DyReference.DB);
				tcf.setViewbutinfo(DyReference.VIEWBUTINFO_VALUE);
				try {
					rs2 = conn.getMetaData().getColumns(null, "%", table, "%");
					List formcolumn = new ArrayList();
					long indexvalue = 1;
					while (rs2.next()) {
						String column = rs2.getString("COLUMN_NAME");
						String type = rs2.getString("TYPE_NAME");
						String uuid = "column" + indexvalue;
						// 封装TCsColumn对象
						TCsColumn tcc = new TCsColumn();
						if (type.equals("int") || type.equals("smallint")
								|| type.equals("LONG") || type.equals("bigint")
								|| type.equals("tinyint")
								|| type.equals("decimal")
								|| type.equals("numeric")
								|| type.equals("money")
								|| type.equals("smallmoney")
								|| type.equals("float") || type.equals("real")
								|| type.equals("double")
								|| type.equals("mediumint")
								|| type.equals("integer")
								|| type.equals("NUMBER")) {
							tcc.setChecktype("number");
							tcc.setHtmltype("");
						} else if (type.equals("datetime")
								|| type.equals("smalldatetime")
								|| type.equals("time") || type.equals("date")
								|| type.equals("year") || type.equals("DATE")) {
							tcc.setChecktype("");
							tcc.setHtmltype("date");
						} else {
							tcc.setChecktype("");
							tcc.setHtmltype(type);
						}
						tcc.setColumname(column);
						tcc.setColumncode(column);
						tcc.setColumnid(uuid);
						tcc.setExtendattribute(DyReference.EXTEND_VALUE);
						tcc.setFormcode("");
						tcc.setIndexvalue(new Long(indexvalue));
						tcc.setMusk(DyReference.MUSK_VALUE);
						tcc.setOpemode(DyReference.OPEMODE_VALUE);
						tcc.setParticipant("");
						tcc.setStatusinfo("");
						tcc.setUseable(XMLReference.TRUE);
						tcc.setValuelist(DyReference.VALUELIST_VALUE);
						tcc.setViewtype("");
						formcolumn.add(tcc);
						indexvalue++;
					}
					// 封装DyObj对象
					dfo[rs1.getRow() - 1] = new DyObj();
					dfo[rs1.getRow() - 1].setFrom(tcf);
					dfo[rs1.getRow() - 1].setColumn(formcolumn);
					dfo[rs1.getRow() - 1].setSystemid(ds);
					dfo[rs1.getRow() - 1].setExtendAttribute(extendAttribute);
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return dfo;

	}

	public DyObj parser(String ds, String table) {
		if (ds == null) {
			log.info("使用默认DS");
		}
		DyObj dfo = new DyObj();
		String extendAttribute = "";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = SQLTools.getConn(ds);

			// 封装TCsForm对象
			TCsForm tcf = new TCsForm();
			tcf.setButinfo(DyReference.BUTINFO_VALUE);
			tcf.setCreated(GetDateTime.getDateTime());
			tcf.setDescription(table);
			tcf.setDesigner(GetPK.getPKColumnName(conn, table));
			tcf.setExtendattribute("");
			String formcode = IdServer.uuid() + "_" + ds;
			tcf.setFormcode(formcode);
			tcf.setFormname(table);
			tcf.setListinfo(DyReference.LISTINFO_VALUE);
			tcf.setOrderinfo(DyReference.ORDERINFO_VALUE);
			tcf.setParticipant("");
			tcf.setStatusinfo("");
			tcf.setSubform(DyReference.SUBFORM_VALUE);
			tcf.setSystemid(ds);
			tcf.setTypeinfo(DyReference.DB);
			tcf.setViewbutinfo(DyReference.VIEWBUTINFO_VALUE);
			rs = conn.getMetaData().getColumns(null, "%", table, "%");
			List formcolumn = new ArrayList();
			long indexvalue = 1;
			while (rs.next()) {
				String column = rs.getString("COLUMN_NAME");
				String type = rs.getString("TYPE_NAME");
				String uuid = "column" + indexvalue;
				// 封装TCsColumn对象
				TCsColumn tcc = new TCsColumn();
				if (type.equals("int") || type.equals("smallint")
						|| type.equals("LONG") || type.equals("bigint")
						|| type.equals("tinyint") || type.equals("decimal")
						|| type.equals("numeric") || type.equals("money")
						|| type.equals("smallmoney") || type.equals("float")
						|| type.equals("real") || type.equals("double")
						|| type.equals("mediumint") || type.equals("integer")
						|| type.equals("NUMBER")) {
					tcc.setChecktype("number");
					tcc.setHtmltype("");
				} else if (type.equals("datetime")
						|| type.equals("smalldatetime") || type.equals("time")
						|| type.equals("date") || type.equals("year")
						|| type.equals("DATE")) {
					tcc.setChecktype("");
					tcc.setHtmltype("date");
				} else {
					tcc.setChecktype("");
					tcc.setHtmltype(type);
				}
				tcc.setColumname(column);
				tcc.setColumncode(column);
				tcc.setColumnid(uuid);
				tcc.setExtendattribute(DyReference.EXTEND_VALUE);
				tcc.setFormcode("");
				tcc.setIndexvalue(new Long(indexvalue));
				tcc.setMusk(DyReference.MUSK_VALUE);
				tcc.setOpemode(DyReference.OPEMODE_VALUE);
				tcc.setParticipant("");
				tcc.setStatusinfo("");
				tcc.setUseable(XMLReference.TRUE);
				tcc.setValuelist(DyReference.VALUELIST_VALUE);
				tcc.setViewtype("");
				formcolumn.add(tcc);
				indexvalue++;
			}
			// 封装DyObj对象
			dfo = new DyObj();
			dfo.setFrom(tcf);
			dfo.setColumn(formcolumn);
			dfo.setSystemid(ds);
			dfo.setExtendAttribute(extendAttribute);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return dfo;

	}

	public DyObj[] parser(String ds, String[] table) {
		if (ds == null) {
			log.error("使用默认DS");
		}
		if (table == null || table.length <= 0) {
			log.error("表名不能为空");
			return null;
		} else {
			DyObj[] dfo = new DyObj[table.length];
			String extendAttribute = "";
			Session session = null;
			Connection conn = null;
			ResultSet rs = null;
			try {
				conn = SQLTools.getConn(ds);
				for (int i = 0; i < table.length; i++) {
					// 封装TCsForm对象
					TCsForm tcf = new TCsForm();
					tcf.setButinfo(DyReference.BUTINFO_VALUE);
					tcf.setCreated(GetDateTime.getDateTime());
					tcf.setDescription(table[i]);
					tcf.setDesigner(GetPK.getPKColumnName(conn, table[i]));
					tcf.setExtendattribute("");
					String formcode = IdServer.uuid() + "_" + ds;
					tcf.setFormcode(formcode);
					tcf.setFormname(table[i]);
					tcf.setListinfo(DyReference.LISTINFO_VALUE);
					tcf.setOrderinfo(DyReference.ORDERINFO_VALUE);
					tcf.setParticipant("");
					tcf.setStatusinfo("");
					tcf.setSubform(DyReference.SUBFORM_VALUE);
					tcf.setSystemid(ds);
					tcf.setTypeinfo(DyReference.DB);
					tcf.setViewbutinfo(DyReference.VIEWBUTINFO_VALUE);
					rs = conn.getMetaData()
							.getColumns(null, "%", table[i], "%");
					List formcolumn = new ArrayList();
					long indexvalue = 1;
					while (rs.next()) {
						String column = rs.getString("COLUMN_NAME");
						String type = rs.getString("TYPE_NAME");
						String uuid = "column" + indexvalue;
						// 封装TCsColumn对象
						TCsColumn tcc = new TCsColumn();
						if (type.equals("int") || type.equals("smallint")
								|| type.equals("LONG") || type.equals("bigint")
								|| type.equals("tinyint")
								|| type.equals("decimal")
								|| type.equals("numeric")
								|| type.equals("money")
								|| type.equals("smallmoney")
								|| type.equals("float") || type.equals("real")
								|| type.equals("double")
								|| type.equals("mediumint")
								|| type.equals("integer")
								|| type.equals("NUMBER")) {
							tcc.setChecktype("number");
							tcc.setHtmltype("");
						} else if (type.equals("datetime")
								|| type.equals("smalldatetime")
								|| type.equals("time") || type.equals("date")
								|| type.equals("year") || type.equals("DATE")) {
							tcc.setChecktype("");
							tcc.setHtmltype("date");
						} else {
							tcc.setChecktype("");
							tcc.setHtmltype(type);
						}
						tcc.setColumname(column);
						tcc.setColumncode(column);
						tcc.setColumnid(uuid);
						tcc.setExtendattribute(DyReference.EXTEND_VALUE);
						tcc.setFormcode("");
						tcc.setIndexvalue(new Long(indexvalue));
						tcc.setMusk(DyReference.MUSK_VALUE);
						tcc.setOpemode(DyReference.OPEMODE_VALUE);
						tcc.setParticipant("");
						tcc.setStatusinfo("");
						tcc.setUseable(XMLReference.TRUE);
						tcc.setValuelist(DyReference.VALUELIST_VALUE);
						tcc.setViewtype("");
						formcolumn.add(tcc);
						indexvalue++;
					}
					// 封装DyObj对象
					dfo[i] = new DyObj();
					dfo[i].setFrom(tcf);
					dfo[i].setColumn(formcolumn);
					dfo[i].setSystemid(ds);
					dfo[i].setExtendAttribute(extendAttribute);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			return dfo;
		}
	}

	public DyObj[] parser(String ds, Map tableColumn) {
		if (ds == null) {
			log.error("使用默认DS");
		}
		if (tableColumn.size() <= 0) {
			log.error("表和字段不能为空");
			return null;
		} else {
			DyObj[] dfo = new DyObj[tableColumn.size()];
			String extendAttribute = "";
			Session session = null;
			Connection conn = null;
			try {
				conn = SQLTools.getConn(ds);
				int i = 0;
				for (Iterator iter = tableColumn.keySet().iterator(); iter
						.hasNext();) {
					String table = (String) iter.next();
					// 封装TCsForm对象
					TCsForm tcf = new TCsForm();
					tcf.setButinfo(DyReference.BUTINFO_VALUE);
					tcf.setCreated(GetDateTime.getDateTime());
					tcf.setDescription(table);
					tcf.setDesigner(GetPK.getPKColumnName(conn, table));
					tcf.setExtendattribute("");
					String formcode = IdServer.uuid() + "_" + ds;
					tcf.setFormcode(formcode);
					tcf.setFormname(table);
					tcf.setListinfo(DyReference.LISTINFO_VALUE);
					tcf.setOrderinfo(DyReference.ORDERINFO_VALUE);
					tcf.setParticipant("");
					tcf.setStatusinfo("");
					tcf.setSubform(DyReference.SUBFORM_VALUE);
					tcf.setSystemid(ds);
					tcf.setTypeinfo(DyReference.DB);
					tcf.setViewbutinfo(DyReference.VIEWBUTINFO_VALUE);
					String[] columns = (String[]) tableColumn.get(table);
					List formcolumn = new ArrayList();
					long indexvalue = 1;
					for (int j = 0; j < columns.length; j = j + 2) {
						String uuid = "column" + indexvalue;
						String type = columns[j + 1];
						// 封装TCsColumn对象
						TCsColumn tcc = new TCsColumn();
						if (type.equals("int") || type.equals("smallint")
								|| type.equals("LONG") || type.equals("bigint")
								|| type.equals("tinyint")
								|| type.equals("decimal")
								|| type.equals("numeric")
								|| type.equals("money")
								|| type.equals("smallmoney")
								|| type.equals("float") || type.equals("real")
								|| type.equals("double")
								|| type.equals("mediumint")
								|| type.equals("integer")
								|| type.equals("NUMBER")) {
							tcc.setChecktype("number");
							tcc.setHtmltype("");
						} else if (type.equals("datetime")
								|| type.equals("smalldatetime")
								|| type.equals("time") || type.equals("date")
								|| type.equals("year") || type.equals("DATE")) {
							tcc.setChecktype("");
							tcc.setHtmltype("date");
						} else {
							tcc.setChecktype("");
							tcc.setHtmltype(type);
						}
						tcc.setColumname(columns[j]);
						tcc.setColumncode(columns[j]);
						tcc.setColumnid(uuid);
						tcc.setExtendattribute(DyReference.EXTEND_VALUE);
						tcc.setFormcode("");
						tcc.setIndexvalue(new Long(indexvalue));
						tcc.setMusk(DyReference.MUSK_VALUE);
						tcc.setOpemode(DyReference.OPEMODE_VALUE);
						tcc.setParticipant("");
						tcc.setStatusinfo("");
						tcc.setUseable(XMLReference.TRUE);
						tcc.setValuelist(DyReference.VALUELIST_VALUE);
						tcc.setViewtype("");
						formcolumn.add(tcc);
						indexvalue++;
					}
					// 封装DyObj对象
					dfo[i] = new DyObj();
					dfo[i].setFrom(tcf);
					dfo[i].setColumn(formcolumn);
					dfo[i].setSystemid(ds);
					dfo[i].setExtendAttribute(extendAttribute);
					i++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			return dfo;
		}
	}
}
