package oe.cav.bean.logic.dy.bus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.dy.bus.reference.BussDaoReference;
import oe.cav.bean.logic.form.TCsForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BussDaoTools {

	static Log log = LogFactory.getLog(BussDaoTools.class);

	/**
	 * 检查字段类型，并且确认该字段是否需要使用到
	 * 
	 * @param dfo
	 * @param tcf
	 * @param bus
	 * @param columnidvalue
	 * @return 返回为真表示该表单需要集成Portal页
	 */
	public static List columnTypeAndValueAvail(List columnlist, TCsForm tcf,
			TCsBus bus, Map columnidvalue, boolean ignoreNullColumn) {
		List listavailableColumn = new ArrayList();
		for (Iterator iterator = columnlist.iterator(); iterator.hasNext();) {
			TCsColumn tcc = (TCsColumn) iterator.next();
			try {

				String columnid = tcc.getColumnid();
				String columncode = tcc.getColumncode();
				Object value = BeanUtils.getProperty(bus, columncode
						.toLowerCase());
				if (ignoreNullColumn && value == null) {
					continue;
				}
				// 非空值的字段要求给其赋值
				listavailableColumn.add(tcc);

				// 生成BussObj字段名和字段值
				String checktype = tcc.getChecktype();
				String htmltype = tcc.getHtmltype();
				String attributeType = "";
				// 判断字段类型
				if (ColumnExtendInfo._HTML_TYPE_BUT.equals(htmltype)
						|| ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltype)
						|| ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltype)) {

				} else {
					if (ColumnExtendInfo._CHECK_TYPE_NUMBER.equals(checktype)) {
						attributeType = "number";
					} else {
						if (ColumnExtendInfo._HTML_TYPE_DATE.equals(htmltype)
								|| ColumnExtendInfo._HTML_TYPE_DATETIME
										.equals(htmltype)
								|| ColumnExtendInfo._HTML_TYPE_TIME
										.equals(htmltype)) {
							attributeType = "date";
						} else {
							attributeType = "string";
						}
					}
				}
				Object valueReal = null;
				if ("number".equals(attributeType)
						&& (value == null || value.equals(""))) {
					valueReal = new Double(0);
				} else {
					valueReal = value;
				}
				columnidvalue.put(columnid.toLowerCase(), valueReal);
				// 生成BussObj字段名和字段类型

				log.debug(columnid + ":" + value + ":" + attributeType);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}

		}
		return listavailableColumn;

	}

	public static String createSQL(String formcode, String description,
			List column) {

		StringBuffer sb = new StringBuffer();
		sb.append(BussDaoReference._INSERT + description + "(");
		// 判断列的个数的标志，若为0个则为false,若为1个及以上则为true
		boolean flag = false;
		for (Iterator iter = column.iterator(); iter.hasNext();) {
			TCsColumn columnEle = (TCsColumn) iter.next();
			if (flag == false) {
				sb.append(columnEle.getColumnid());
				flag = true;
			} else {
				sb.append("," + columnEle.getColumnid());
			}
		}
		sb.append(") values (");
		// 判断列的个数的标志，若为0个则为false,若为1个及以上则为true
		boolean sign = false;
		for (int i = 0; i < column.size(); i++) {
			if (sign == false) {
				sb.append(BussDaoReference._FlAG);
				sign = true;
			} else {
				sb.append("," + BussDaoReference._FlAG);
			}
		}
		sb.append(")");
		log.debug(sb.toString());
		return sb.toString();

	}

	public static String updateSql(String formcode, String description,
			List column, String pk) {
		StringBuffer sb = new StringBuffer();
		sb.append(BussDaoReference._UPDATE + description
				+ BussDaoReference._SET);

		StringBuffer butColumn = new StringBuffer();
		for (Iterator iter = column.iterator(); iter.hasNext();) {
			TCsColumn columnEle = (TCsColumn) iter.next();
			if (!pk.equals(columnEle.getColumnid())) {
				butColumn.append("," + columnEle.getColumnid() + " = "
						+ BussDaoReference._FlAG);
			}
		}
		sb.append(butColumn.substring(1));
		sb.append(BussDaoReference._WHERE + BussDaoReference._PRE_CONDITION);

		sb.append(BussDaoReference._AND + pk + " = " + BussDaoReference._FlAG);

		log.debug(sb.toString());
		return sb.toString();
	}

	public static String dropSql(String description, String formcode, String pk) {
		StringBuffer sb = new StringBuffer();
		sb.append(BussDaoReference._DELETE + BussDaoReference._FROM
				+ description);
		sb.append(BussDaoReference._WHERE + BussDaoReference._PRE_CONDITION);
		sb.append(BussDaoReference._AND + pk + "=" + BussDaoReference._FlAG);

		log.debug(sb.toString());
		return sb.toString();
	}

}
