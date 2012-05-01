package oe.mid.datatools;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.web.db.DbTools;
import oe.mid.datatools.obj.DColumn;
import oe.mid.datatools.obj.DColumnref;
import oe.mid.datatools.obj.DIn;
import oe.mid.datatools.obj.DOut;
import oe.mid.datatools.obj.DSQL;
import oe.mid.datatools.obj.DSource;
import oe.mid.datatools.obj.DataTrans;
import oe.mid.datatools.utils.DB;
import oe.mid.datatools.utils.RuleEngine;

import org.apache.commons.collections.SequencedHashMap;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DataToolImplamle implements DataToolIfc {
	Map checkSqlRepeat = new HashMap();

	public DataTrans parser(String datameta) {

		DataTrans dataTrans = new DataTrans();
		SAXReader reader = new SAXReader();
		Map sources = new HashMap();
		Map sqls = new HashMap();
		Map outs = new HashMap();
		List<DIn> ins = new ArrayList<DIn>();

		Document document;
		try {
			if (StringUtils.indexOf(datameta, "<?xml") == 0) {
				java.io.InputStream inpx = null;
				try {
					inpx = new ByteArrayInputStream(datameta.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				document = reader.read(inpx);
			} else {
				document = reader.read(datameta);
			}

			Element root = document.getRootElement();

			// 遍历第一层的节点信息

			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();

				if ("source".equals(element.getName())) {
					DSource source = new DSource();
					source.setId(element.attributeValue("id"));
					source.setDriver(element.attributeValue("driver"));
					source.setName(element.attributeValue("username"));
					source.setPasssword(element.attributeValue("password"));
					source.setUrl(element.attributeValue("url"));
					sources.put(element.attributeValue("id"), source);
				}

				if ("sql".equals(element.getName())) {
					DSQL sql = new DSQL();
					sql.setId(element.attributeValue("id"));
					sql.setSql(element.getStringValue());
					sqls.put(element.attributeValue("id"), sql);
				}

				if ("out".equals(element.getName())) {
					DOut out = new DOut();
					out.setId(element.attributeValue("id"));
					out
							.setSql((DSQL) sqls.get(element
									.attributeValue("sqlid")));
					out.setSource((DSource) sources.get(element
							.attributeValue("sourceid")));

					outs.put(element.attributeValue("id"), out);
				}

				if ("in".equals(element.getName())) {
					List<DColumn> columns = new ArrayList<DColumn>();
					List<DColumnref> columnrefs = new ArrayList<DColumnref>();
					DIn in = new DIn();
					in.setId(element.attributeValue("id"));
					in.setSource((DSource) sources.get(element
							.attributeValue("tosourceid")));
					in.setOut((DOut) outs.get(element.attributeValue("outid")));
					in.setCommit(Integer.parseInt(element
							.attributeValue("commit")));

					for (Iterator iterator = element.elementIterator("column"); iterator
							.hasNext();) {
						Element ele = (Element) iterator.next();
						DColumn column = new DColumn();
						column.setName(ele.attributeValue("name"));
						column.setOutname(ele.attributeValue("outname"));
						column.setType(ele.attributeValue("type"));
						column.setScript(ele.getStringValue());
						columns.add(column);

					}

					for (Iterator iterator = element
							.elementIterator("columnref"); iterator.hasNext();) {
						Element ele = (Element) iterator.next();
						DColumnref columnref = new DColumnref();
						columnref.setName(ele.attributeValue("name"));
						columnref.setRefertoname(ele
								.attributeValue("refertoname"));
						columnref.setScript(ele.getStringValue());
						columnref.setType(ele.attributeValue("type"));
						columnrefs.add(columnref);
					}
					in.setColumnrefs(columnrefs);
					in.setColumns(columns);
					ins.add(in);
				}

			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dataTrans.setSource(sources);
		dataTrans.setSql(sqls);
		dataTrans.setOut(outs);
		dataTrans.setIn(ins);

		return dataTrans;
	}

	public String todo(DataTrans dt, boolean printsql) {
		List in = dt.getIn();
		StringBuffer retinfo = new StringBuffer();
		int count = 0;
		for (Iterator iterator = in.iterator(); iterator.hasNext();) {
			DIn inObj = (DIn) iterator.next();
			System.out.print("todo " + inObj.getId() + "....");
			DOut outObj = inObj.getOut();
			List data = fetchData(outObj.getSql().getSql(), outObj.getSource());
			int commit = inObj.getCommit();

			List sqlInBuffer = new ArrayList();
			DSource inDs = inObj.getSource();
			List columns = inObj.getColumns();
			List columnRefs = inObj.getColumnrefs();

			List alldata = new ArrayList();
			for (Iterator iterator2 = data.iterator(); iterator2.hasNext();) {

				Map object = (Map) iterator2.next();
				Map preNewcolmninfo = new SequencedHashMap();

				for (Iterator iterator3 = columns.iterator(); iterator3
						.hasNext();) {
					DColumn dco = (DColumn) iterator3.next();
					String outname = dco.getOutname();
					String type = dco.getType();
					Object valueTmp = object.get(outname);
					String value = "";
					if (valueTmp != null) {
						value = valueTmp.toString();
					}

					String script = dco.getScript();
					String incolumn = dco.getName();

					dealWithScript(script, type, value, preNewcolmninfo,
							incolumn, object);

				}

				for (Iterator iterator3 = columnRefs.iterator(); iterator3
						.hasNext();) {
					DColumnref dcor = (DColumnref) iterator3.next();
					String incolumn = dcor.getName();
					String refertoname = dcor.getRefertoname();
					String value = (String) preNewcolmninfo.get(refertoname);
					String type = dcor.getType();
					if (type == null || type.equals("")) {
						type = "varchar";
						if (StringUtils.contains(value, "'")) {
							type = "int";
						}
					}

					String script = dcor.getScript();
					dealWithScript(script, type, value, preNewcolmninfo,
							incolumn, object);

				}
				boolean isok = false;
				try {
					isok = this.insert(preNewcolmninfo, printsql, inDs);
				} catch (Exception e) {
					retinfo.append(e.getMessage());
				}
				if (isok) {
					count++;
				}

			}
			
			System.out.println(".....done!" + count + ",lose:"
					+ (data.size() - count));
			return retinfo.toString()+".....done!" + count + ",lose:"
					+ (data.size() - count);
		}
	}

	private void dealWithScript(String script, String type, String value,
			Map preNewcolmninfo, String incolumn, Map recordInfo) {
		if ("".equals(script.trim()) || script == null) {

			if (type.equals("varchar") || type.equals("date")
					|| type.equals("datetime")) {
				value = "'" + value + "'";
			}
			preNewcolmninfo.put(incolumn, value);
		} else {
			String rs = RuleEngine.todo(script, value, recordInfo);

			if (type.equals("varchar") || type.equals("date")
					|| type.equals("datetime")) {
				rs = "'" + rs + "'";
			}
			preNewcolmninfo.put(incolumn, rs);
		}
	}

	private boolean insert(Map alldata, boolean printsql, DSource inDs) {

		StringBuffer insertKey1 = new StringBuffer();
		StringBuffer insertKey2 = new StringBuffer();
		StringBuffer insertValue1 = new StringBuffer();
		StringBuffer insertValue2 = new StringBuffer();
		String temp1 = "";
		String temp2 = "";

		boolean isIgnore = false;
		for (Iterator iterator2 = alldata.entrySet().iterator(); iterator2
				.hasNext();) {

			Map.Entry m = (Map.Entry) iterator2.next();

			// 获取表名，字段名，和对应的数据
			String key = m.getKey().toString();
			String value = m.getValue().toString();

			if ("'$99$'".equals(value)) {// 说明出现异常数据，需要跳过该数据
				isIgnore = true;
				break;
			}

			String keys[] = key.split("\\.");
			String tableName = keys[0];

			String columnName = keys[1];

			if (false == iterator2.hasNext() || !iterator2.hasNext()) {
				// 最后一次插入
				if ("''".equals(value) || "".equals(value) || value == null) {
					insertKey1.append(") ");
					insertValue1.append(")");

					insertKey2.append(") ");
					insertValue2.append(")");
				} else if (insertKey1.toString().contains(tableName + "(")) {
					insertKey1.append("," + columnName + ") ");
					insertValue1.append("," + value + ")");

					insertKey2.append(") ");
					insertValue2.append(")");
				} else {
					insertKey2.append("," + columnName + ") ");
					insertValue2.append("," + value + ")");

					insertKey1.append(") ");
					insertValue1.append(")");
				}
			} else if ("''".equals(value) || "".equals(value) || value == null) {
				continue;
			} else if ("".equals(insertKey1.toString())
					|| insertKey1.toString() == null) {
				if (insertKey2.toString().contains(tableName + "(")) {
					insertKey2.append("," + columnName + ")");
					insertValue2.append("," + value + ")");
				} else {
					insertKey1.append("insert into " + tableName + "("
							+ columnName);
					insertValue1.append("values(" + value);
				}
			} else if ("".equals(insertKey2.toString())
					|| insertKey2.toString() == null) {
				if (insertKey1.toString().contains(tableName + "(")) {
					insertKey1.append("," + columnName);
					insertValue1.append("," + value);
				} else {
					insertKey2.append("insert into " + tableName + "("
							+ columnName);
					insertValue2.append("values(" + value);
				}
			} else {
				// 不是第一次插入，则先区分表名
				if (insertKey1.toString().contains(tableName + "(")) {
					insertKey1.append("," + columnName);
					insertValue1.append("," + value);
				} else {
					insertKey2.append("," + columnName);
					insertValue2.append("," + value);
				}
			}
		}
		if (isIgnore) {
			return false;
		}
		temp1 = insertKey1.toString() + insertValue1.toString();
		temp2 = insertKey2.toString() + insertValue2.toString();

		boolean insert = false;
		if (!"".equals(temp1) && temp1 != null && temp1.length() >= 5) {
			if (!checkSqlRepeat.containsKey(temp1)) {
				checkSqlRepeat.put(temp1, "");
				addData(inDs, temp1);
				insert = true;
				if (printsql)
					System.out.println(temp1);

			}
		}

		if (!"".equals(temp2) && temp2 != null && temp2.length() >= 5) {
			if (!checkSqlRepeat.containsKey(temp2)) {
				checkSqlRepeat.put(temp2, "");
				addData(inDs, temp2);
				insert = true;
				if (printsql)
					System.out.println(temp2);
			}
		}
		if (!insert) {
			System.err.println("ignore: sql1:" + temp1 + ",sql2:" + temp2);
		}
		return insert;

	}

	private void addData(DSource inDs, String sql) {
		Connection conIn = null;
		try {
			conIn = DB.conByDs(inDs);
			DbTools.executeTransaction(sql, conIn);

		} catch (Exception e) {
			System.err.println(sql);
			e.printStackTrace();
			throw new RuntimeException(sql);
		} finally {
			DbTools.freeDbConnectoin(conIn, null, null);
		}
	}

	private List fetchData(String sql, DSource ds) {
		Connection con = DB.conByDs(ds);
		return DbTools.queryData(sql, con);

	}

}
