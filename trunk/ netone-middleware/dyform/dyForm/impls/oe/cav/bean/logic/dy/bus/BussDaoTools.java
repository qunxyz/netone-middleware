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
	 * ����ֶ����ͣ�����ȷ�ϸ��ֶ��Ƿ���Ҫʹ�õ�
	 * 
	 * @param dfo
	 * @param tcf
	 * @param bus
	 * @param columnidvalue
	 * @return ����Ϊ���ʾ�ñ���Ҫ����Portalҳ
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
				// �ǿ�ֵ���ֶ�Ҫ����丳ֵ
				listavailableColumn.add(tcc);

				// ����BussObj�ֶ������ֶ�ֵ
				String checktype = tcc.getChecktype();
				String htmltype = tcc.getHtmltype();
				String attributeType = "";
				// �ж��ֶ�����
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
				// ����BussObj�ֶ������ֶ�����

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
		// �ж��еĸ����ı�־����Ϊ0����Ϊfalse,��Ϊ1����������Ϊtrue
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
		// �ж��еĸ����ı�־����Ϊ0����Ϊfalse,��Ϊ1����������Ϊtrue
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
