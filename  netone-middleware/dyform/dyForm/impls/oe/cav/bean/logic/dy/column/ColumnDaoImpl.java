package oe.cav.bean.logic.dy.column;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.column.ColumnDao;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;

import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DbScriptTools;
import oe.cav.bean.logic.tools.DyObj;
import oe.cav.bean.logic.tools.DyObjFromXml;
import oe.cav.bean.logic.tools.DyObjToXML;

import oe.cav.bean.logic.tools.XmlPools;
import oe.cav.bean.logic.tools.reference.XmlWriter;
import oe.cav.bean.logic.tools.reference.DyReference;

;

public class ColumnDaoImpl implements ColumnDao {

	private DyObjToXML dyObjToXML;

	private DyObjFromXml dyObjFromXml;

	public String create(TCsColumn column) {
		if (column == null || column.equals("")) {
			return "无效对象";
		} else {
			String formcode = column.getFormcode();
			String columncode = getNextColumn(formcode);
			column.setColumncode(columncode);
			column.setUseable(true);

			DyObjFromXml dofx = (DyObjFromXml) FormEntry
					.fetchBean("dyObjFromXml");
			DyObj dfo = dofx.parser(XmlPools.fetchXML(formcode).toString());
			TCsForm tcf = dfo.getFrom();
			if (DyReference.DB.equals(tcf.getTypeinfo())) {
				return "来自DB的表单,不能创建字段";
			}
			List columnlist = dfo.getColumn();
			// 检查是否有重复的columncode
			for (Iterator iteror = columnlist.iterator(); iteror.hasNext();) {
				TCsColumn tcc = (TCsColumn) iteror.next();
				String columncodes = tcc.getColumnid();
				if (columncodes.equals(columncode)) {
					return "发现重复的字段ID";
				}
			}
			columnlist.add(column);
			dfo.setColumn(columnlist);
			XmlWriter.write(dyObjToXML.parser(dfo), XmlPools
					.writedPath(formcode));
			// Db Sys
			return DbScriptTools.addColumn(column, tcf.getSystemid());

		}
	}

	public String update(TCsColumn column) {
		if (column == null || column.equals("")) {
			return "无效对象";
		} else {
			String formcode = column.getFormcode();
			String columncode = column.getColumncode();

			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			TCsForm form = dfo.getFrom();
			List columnlist = dfo.getColumn();

			// 检查是否有重复的columncode
			for (Iterator iteror = columnlist.iterator(); iteror.hasNext();) {
				TCsColumn tcc = (TCsColumn) iteror.next();
				String columncodes = tcc.getColumncode();
				if (columncodes.equals(columncode)) {
					try {
						BeanUtils.copyProperties(tcc, column);
						tcc.setUseable(true);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			dfo.setColumn(columnlist);
			XmlWriter.write(dyObjToXML.parser(dfo), XmlPools
					.writedPath(formcode));
			// Db Sys
			return DbScriptTools.updateColumn(column, form.getSystemid());

		}
	}

	public String updateView(TCsColumn column) {
		if (column == null || column.equals("")) {
			return "无效对象";
		} else {
			String formcode = column.getFormcode();
			String columncode = column.getColumncode();

			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			TCsForm form = dfo.getFrom();
			List columnlist = dfo.getColumn();

			// 检查是否有重复的columncode
			for (Iterator iteror = columnlist.iterator(); iteror.hasNext();) {
				TCsColumn tcc = (TCsColumn) iteror.next();
				String columncodes = tcc.getColumncode();
				if (columncodes.equals(columncode)) {
					try {
						BeanUtils.copyProperties(tcc, column);
						tcc.setUseable(true);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			dfo.setColumn(columnlist);
			XmlWriter.write(dyObjToXML.parser(dfo), XmlPools
					.writedPath(formcode));

			return "";

		}
	}

	public String drop(TCsColumn column) {
		if (column == null || column.equals("")) {
			return "无效对象";
		} else {
			String formcode = column.getFormcode();
			String columncode = column.getColumnid();

			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			TCsForm tcf = dfo.getFrom();
			if (DyReference.DB.equals(tcf.getTypeinfo())) {
				return "来自DB的表单,不能删除字段";
			}
			List columnlist = dfo.getColumn();
			// 有重复的columncode
			for (Iterator iteror = columnlist.iterator(); iteror.hasNext();) {
				TCsColumn tcc = (TCsColumn) iteror.next();
				String columncodes = tcc.getColumnid();
				if (columncodes.equals(columncode)) {
					// columnlist.remove(tcc);
					tcc.setUseable(false);
					break;
				}
			}
			dfo.setColumn(columnlist);

			XmlWriter.write(dyObjToXML.parser(dfo), XmlPools
					.writedPath(formcode));

			// Db Sys
			// DbScriptTools.dropColumn(column);
			return "删除成功";
		}
	}

	public String getNextColumn(String formcode) {
		return COLUMNID_PRE + this.getNextIndexValue(formcode);
	}

	public TCsColumn loadObject(String formcode, Serializable key) {
		if (formcode == null || formcode.equals("") || key == null) {
			return null;
		}

		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		if (dfo == null) {
			return null;
		} else {
			List columnlist = dfo.getColumn();
			if (columnlist == null) {
				return null;
			} else {
				for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
					TCsColumn tcc = (TCsColumn) iter.next();
					String uuid = tcc.getColumncode();
					if (key.equals(uuid)) {
						return tcc;
					}
				}
				return null;
			}
		}
	}

	public boolean movedownColumn(String formcode, String columnid,
			String participant) {

		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		List columnlist = dfo.getColumn();
		// 要下移的对象
		TCsColumn nowColumn = null;
		// 被转移的对象
		TCsColumn nextColumn = null;
		for (Iterator iteror = columnlist.iterator(); iteror.hasNext();) {
			TCsColumn tcc = (TCsColumn) iteror.next();
			String columncode = tcc.getColumnid();
			if (columncode.equals(columnid)) {
				nowColumn = tcc;
				int i = columnlist.indexOf(nowColumn);
				if (i == columnlist.size() - 1) {
					return false;
				}
				nextColumn = (TCsColumn) columnlist.get(i + 1);
				columnlist.set(i, nextColumn);
				columnlist.set(i + 1, nowColumn);
				break;
			}
		}
		dfo.setColumn(columnlist);

		XmlWriter.write(dyObjToXML.parser(dfo), XmlPools.writedPath(formcode));
		return true;
	}

	public boolean moveupColumn(String formcode, String columnid,
			String participant) {

		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		List columnlist = dfo.getColumn();
		// 要上移的对象
		TCsColumn nowColumn = loadObject(formcode, columnid);

		if (nowColumn == null || nowColumn.getIndexvalue().longValue() == 1) {
			return true;
		}

		// 被转移的对象
		TCsColumn priviousColumn = null;
		for (Iterator iteror = columnlist.iterator(); iteror.hasNext();) {
			TCsColumn tcc = (TCsColumn) iteror.next();
			String columncode = tcc.getColumnid();
			if (columncode.equals(columnid)) {
				nowColumn = tcc;
				int i = columnlist.indexOf(nowColumn);
				if (i == 0) {
					return false;
				}
				priviousColumn = (TCsColumn) columnlist.get(i - 1);
				columnlist.set(i, priviousColumn);
				columnlist.set(i - 1, nowColumn);

				dfo.setColumn(columnlist);

				XmlWriter.write(dyObjToXML.parser(dfo), XmlPools
						.writedPath(formcode));

				break;
			}
		}
		return true;
	}

	public String[] parseViewType(String type) {
		if (ColumnExtendInfo._TYPE_NORMAL.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_TEXT };
		} else if (ColumnExtendInfo._TYPE_NUMBER.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NUMBER,
					ColumnExtendInfo._HTML_TYPE_TEXT };
		} else if (ColumnExtendInfo._TYPE_DATE.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_DATE };
		} else if (ColumnExtendInfo._TYPE_TIME.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_TIME };
		} else if (ColumnExtendInfo._TYPE_DATETIME.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_DATETIME };
		} else if (ColumnExtendInfo._TYPE_BOOLEAN.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_CHECKBOX };
		} else if (ColumnExtendInfo._TYPE_MAIL.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_MAIL,
					ColumnExtendInfo._HTML_TYPE_TEXT };
		} else if (ColumnExtendInfo._TYPE_BOOLEAN.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_TEXT };
		} else if (ColumnExtendInfo._TYPE_LISTINFO.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_SELECT };
		} else if (ColumnExtendInfo._TYPE_LISTINFO_KV.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_SELECT_KV };
		} else if (ColumnExtendInfo._TYPE_IP.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_IP,
					ColumnExtendInfo._HTML_TYPE_TEXT };
		} else if (ColumnExtendInfo._TYPE_TEXTAREA.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_TEXTAREA };
		} else if (ColumnExtendInfo._TYPE_FILE.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_FILE };
		} else if (ColumnExtendInfo._TYPE_IMAGE.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_IMAGE };
		} else if (ColumnExtendInfo._TYPE_BUT.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_BUT };
		} else if (ColumnExtendInfo._TYPE_TREE.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_TREE };
		} else if (ColumnExtendInfo._TYPE_TREE2.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_TREE2 };
		} else if (ColumnExtendInfo._TYPE_SCRIPT.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_SCRIPT };
		} else if (ColumnExtendInfo._TYPE_PORRAL_ITEM.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_PORTAL_ITEM };
		} else if (ColumnExtendInfo._TYPE_FCK_ITEM.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_FCK_ITEM };
		} else if (ColumnExtendInfo._TYPE_HUMAN_ITEM.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_HUMAN };
		} else if (ColumnExtendInfo._TYPE_HUMAN_ITEM2.equals(type)) {
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_HUMAN2 };
		} else
			return new String[] { ColumnExtendInfo._CHECK_TYPE_NO,
					ColumnExtendInfo._HTML_TYPE_TEXT };

	}

	public List queryObjects(TCsColumn obj) {
		List returnList = new ArrayList();
		if (obj == null || obj.equals("")) {
			return returnList;
		} else {
			String formcode = obj.getFormcode();

			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			if (dfo == null) {
				return null;
			} else {
				List column = dfo.getColumn();
				if (dfo.getFrom().getDescription().indexOf("DY") == 0) {
					// 需要过滤掉系统属性[LSH,FORMCODE,PARTICIPANT,PARENTID,EXTENDATTRIBUE,CREATED,HIT]
					if (column.size() > 7) {
						List list = column.subList(8, column.size());

						for (Iterator itr = list.iterator(); itr.hasNext();) {
							TCsColumn cs = (TCsColumn) itr.next();
							if (cs.isUseable()) {
								returnList.add(cs);
							}
						}
					} else {
						throw new RuntimeException("DY表单创建异常");
					}
				}
				// 从DB创建,没有系统属性
				return returnList;
			}
		}
	}

	public List queryObjects(TCsColumn obj, String conditionPre) {
		return queryObjects(obj);
	}

	public List queryObjects(TCsColumn obj, int from, int to) {
		return queryObjects(obj);
	}

	public List queryObjects(TCsColumn obj, int from, int to,
			String conditionPre) {
		return queryObjects(obj, from, to);
	}

	public long queryObjectsNumber(TCsColumn obj) {
		return queryObjects(obj).size();
	}

	public long queryObjectsNumber(TCsColumn obj, String conditionPre) {
		return queryObjectsNumber(obj);
	}

	public boolean resizeColumnIndexValue(String formcode, String participant) {

		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		List columnlist = dfo.getColumn();
		List list = new ArrayList();
		Long number[] = new Long[columnlist.size() - 7];
		int i = 0;
		for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
			TCsColumn column = (TCsColumn) iter.next();
			if (column.getColumnid().indexOf("column") >= 0) {
				String[] num = column.getColumnid().split("column");
				number[i] = new Long(num[1]);
				i++;
			} else {
				list.add(column);
			}
		}
		Arrays.sort(number);
		for (int j = 0; j < number.length; j++) {
			for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
				TCsColumn column = (TCsColumn) iter.next();
				if (column.getColumnid().indexOf("column") >= 0) {
					String[] num = column.getColumncode().split("column");
					if (number[j].toString().equals(num[1])) {
						list.add(column);
					}
				}
			}
		}
		dfo.setColumn(list);

		XmlWriter.write(dyObjToXML.parser(dfo), XmlPools.writedPath(formcode));
		return true;
	}

	public DyObjFromXml getDyObjFromXml() {
		return dyObjFromXml;
	}

	public void setDyObjFromXml(DyObjFromXml dyObjFromXml) {
		this.dyObjFromXml = dyObjFromXml;
	}

	public DyObjToXML getDyObjToXML() {
		return dyObjToXML;
	}

	public void setDyObjToXML(DyObjToXML dyObjToXML) {
		this.dyObjToXML = dyObjToXML;
	}

	public long getNextIndexValue(String formcode) {
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		List columnlist = dfo.getColumn();
		return (columnlist.size() - 7); // 8是前8位系统默认变量

	}

	public List queryObjectsUrl(String url) {
		System.out.println(url);
		List returnList = new ArrayList();
		if (url == null || url.equals("")) {
			return returnList;
		} else {
			DyObj dfo = dyObjFromXml.parser(url);
			if (dfo == null) {
				return null;
			} else {
				List column = dfo.getColumn();
				if (dfo.getFrom().getDescription().indexOf("DY") == 0) {
					// 需要过滤掉系统属性[LSH,FORMCODE,PARTICIPANT,PARENTID,EXTENDATTRIBUE,CREATED,HIT]
					if (column.size() > 7) {
						List list = column.subList(8, column.size());

						for (Iterator itr = list.iterator(); itr.hasNext();) {
							TCsColumn cs = (TCsColumn) itr.next();
							if (cs.isUseable()) {
								returnList.add(cs);
							}
						}
					} else {
						throw new RuntimeException("DY表单创建异常");
					}
				}
				// 从DB创建,没有系统属性
				return returnList;
			}
		}
	}
}
