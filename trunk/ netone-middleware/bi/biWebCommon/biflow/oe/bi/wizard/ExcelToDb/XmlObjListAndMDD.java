package oe.bi.wizard.ExcelToDb;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.common.XmlObj;

import oe.security3a.seucore.obj.MultiDimData;

/**
 * Excel数据封装处理
 * 
 */
public class XmlObjListAndMDD {
	/**
	 * 从Mdd中获得XmlObj list
	 * 
	 * @param mdd
	 * @return
	 */
	public static List toXmlObjList(MultiDimData mdd) {
		List listColumn = new ArrayList();
		Map dataColumnName = mdd.getDataColumnName();
		Map dataColumnType = mdd.getDataColumnType();
		for (Iterator itr = mdd.getDataColumnName().keySet().iterator(); itr
				.hasNext();) {
			XmlObj obj = new XmlObj();
			String columnId = (String) itr.next();
			String columnName = (String) dataColumnName.get(columnId);
			String columnType = (String) dataColumnType.get(columnId);
			if (columnName == null) {
				columnName = columnId;
			}
			if (columnType == null) {
				columnType = columnId;
			}

			obj.setId(columnId);
			obj.setName(columnName);
			obj.setHtmltype(columnType);
			listColumn.add(obj);
		}
		return listColumn;
	}

	/**
	 * 根据xmlObjList 和value 创建Mdd
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static MultiDimData toMDD(List column, List value) {
		MultiDimData mdd = new MultiDimData();
		Map mapKeyName = new LinkedHashMap();
		Map mayKeyType = new LinkedHashMap();
		for (Iterator itr = column.iterator(); itr.hasNext();) {
			XmlObj xmlobj = (XmlObj) itr.next();
			String id = xmlobj.getId();
			String name = xmlobj.getName();
			String type = xmlobj.getType();
			if (name == null) {
				name = id;
			}
			if (type == null) {
				type = "string";
			}
			mapKeyName.put(id, name);
			mayKeyType.put(id, type);
		}

		mdd.setDatavalue(value);
		mdd.setDataColumnName(mapKeyName);
		mdd.setDataColumnType(mayKeyType);
		return mdd;
	}

}
