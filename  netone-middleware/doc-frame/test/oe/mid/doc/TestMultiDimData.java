package oe.mid.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.common.obj.MultiDimData;

/**
 * ģ���������
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */

public class TestMultiDimData {

	public static MultiDimData data1() {
		// ģ��1
		MultiDimData view = new MultiDimData();
		view.setLevel_column("processid");
		view.setStart_time_column("created");
		Map map1 = new HashMap();
		map1.put("kind", "����");
		map1.put("runtimeid", "����id");
		map1.put("starttime", "��ʼʱ��");
		map1.put("endtime", "����ʱ��");
		map1.put("processid", "��ID");
		view.setDataColumnName(map1);

		Map map2 = new HashMap();
		map2.put("kind", "string");
		map2.put("runtimeid", "number");
		map2.put("starttime", "string");
		map2.put("endtime", "string");
		map2.put("processid", "string");
		view.setDataColumnType(map2);

		Map a = new HashMap();
		a.put("kind", "k1");
		a.put("runtimeid", "1");
		a.put("starttime", "10:20:10");
		a.put("endtime", "20:20:10");
		a.put("processid", "a2011");

		Map b = new HashMap();
		b.put("kind", "k2");
		b.put("runtimeid", "2");
		b.put("starttime", "30:20:10");
		b.put("endtime", "40:20:10");
		b.put("processid", "a3011");

		List list = new ArrayList();
		list.add(a);
		list.add(b);
		// list.add(c);
		view.setDatavalue(list);
		return view;
	}
}
