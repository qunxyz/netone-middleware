package oe.cav.bean.logic.tools;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.dy.bus.reference.BussDaoReference;
import oe.cav.bean.logic.form.TCsForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConditionBuilder {

	static Log log = LogFactory.getLog(ConditionBuilder.class);

	public static String build(TCsBus obj, DyObj dfo, List columnlist) {

		// 表名
		String description = "";
		// 从TCsBus中读取数据库名和文件名作为公用属性

		String formcode = obj.getFormcode();

		StringBuffer conditionInfo = new StringBuffer();
		if (formcode == null || formcode.equals("")) {
			log.error("未找到文件名");
			return null;
		} else {

			if (dfo == null) {
				log.error("未找到DyObj对象");
				return null;
			}
			TCsForm tcf = dfo.getFrom();
			description = tcf.getDescription();
			if (description.equals("")) {
				log.error("未找到表名");
				return null;
			}
			String designer = tcf.getDesigner();
			if (designer == null || designer.equals("")) {
				log.error("未找到主键");
				return null;
			}

			for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
				TCsColumn element = (TCsColumn) iter.next();
				try {
					String value = (String) BeanUtils.getProperty(obj, element
							.getColumncode().toLowerCase());
					if (value != null && !value.equals("")) {
						String htmltype = element.getHtmltype();
						String checktype = element.getChecktype();
						// 判断字段类型
						if (ColumnExtendInfo._HTML_TYPE_BUT.equals(htmltype)
								|| ColumnExtendInfo._HTML_TYPE_IMAGE
										.equals(htmltype)
								|| ColumnExtendInfo._HTML_TYPE_FILE
										.equals(htmltype)
								|| element.getColumncode().equals("HIT")) {
							// 要忽律这个条件
							continue;
						} else {
							if (ColumnExtendInfo._CHECK_TYPE_NUMBER
									.equals(checktype)) {
								conditionInfo
										.append(" and " + element.getColumnid()
												+ " = " + value);
							} else {
								if (ColumnExtendInfo._HTML_TYPE_DATE
										.equals(htmltype)
										|| ColumnExtendInfo._HTML_TYPE_DATETIME
												.equals(htmltype)
										|| ColumnExtendInfo._HTML_TYPE_TIME
												.equals(htmltype)) {
									// 时间也当作字符串来处理
									value = "'" + value + "%'";
								} else {
									if(!element.getColumnid().equalsIgnoreCase("participant")){		
										value = "'" + value + "%'";		
									}
								}
								if(element.getColumnid().equalsIgnoreCase("participant")){
									conditionInfo.append(" and "
											+ element.getColumnid() + " in( "
											+ value+")");
								}else{
									conditionInfo.append(" and "
											+ element.getColumnid() + " like "
											+ value);
								}

							}
						}

					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return BussDaoReference._FROM + description + BussDaoReference._WHERE
				+ BussDaoReference._PRE_CONDITION + conditionInfo;

	}
}
