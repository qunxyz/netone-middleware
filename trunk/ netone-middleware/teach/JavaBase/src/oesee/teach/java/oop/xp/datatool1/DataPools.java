package oesee.teach.java.oop.xp.datatool1;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.apache.commons.beanutils.BeanUtils;

/**
 * ���ݴ洢��
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class DataPools {

	static Random rom = null;
	static Map map = null;

	static {
		map = SerilizerPools.fetchObj();
		rom = new Random(System.currentTimeMillis());

	}

	/**
	 * �����־û�����
	 * 
	 * @param obj
	 *            ����ĳ־û����� ͨ����POJO����
	 * @return
	 */
	public static String create(Object obj) {

		String key = "" + rom.nextLong();
		try {
			BeanUtils.setProperty(obj, "id", key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("���������ȱ�����Թؼ���id");
			return null;
		}
		map.put(key, obj);
		SerilizerPools.toSerilizer(map);
		return key;
	}

	/**
	 * ɾ���־û�����
	 * 
	 * @param key
	 *            ����Ĺؼ���
	 */
	public static void delete(String key) {
		map.remove(key);
		SerilizerPools.toSerilizer(map);
	}

	/**
	 * �޸ĳ־û�����
	 * 
	 * @param obj
	 *            ����ĳ־û����� ͨ����POJO����
	 */
	public static void modify(Object obj) {
		String key = null;
		try {
			key = (String) BeanUtils.getProperty(obj, "id");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("���������ȱ�����Թؼ���id");
			return;
		}
		map.put(key, obj);
		SerilizerPools.toSerilizer(map);
	}

	/**
	 * �������еĳ־û�����
	 * 
	 * @return
	 */
	public static List query() {
		List list = new ArrayList();
		for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
			list.add(map.get(itr.next()));
		}
		return list;
	}
}
