package oe.security3a.seucore.resourceser;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.orm.OrmerEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.resourceser.ApplicationService;
import oe.security3a.seucore.resourceser.ProtectedObjectDao;


import org.apache.commons.lang.StringUtils;


/**
 * ��������Daoʵ��
 * 
 * @author ni.he.qing
 * 
 */
public class ProtectedObjectDaoImpl implements ProtectedObjectDao {

	private ApplicationService appservice = (ApplicationService) SeuserEntry
			.iv("applicationService");

	/**
	 * ��ȫ��죬�ڶ����м����û���Ϣ
	 * 
	 * @param obj
	 * @return
	 */
	public boolean checkSecrity(Object obj) {
		throw new RuntimeException("�÷���û��ʵ��");
	}

	private boolean checkExist(String naturalname) {
		UmsProtectedobject obj = new UmsProtectedobject();
		obj.setNaturalname(naturalname);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				null);
		if (list.size() == 1) {
			return true;
		}
		if (list.size() > 1) {
			throw new RuntimeException("�洢�쳣���ֶ���ظ�����:" + naturalname);
		}
		return false;

	}

	/**
	 * �������
	 * 
	 * @param obj
	 *            UmsProtectedobject
	 * @return boolean �㷨��<br>
	 *         1����appidװ�أ�����Ƿ���ڸ�application 2�����������uproobjִ�д�������
	 *         3�����԰��������uproobj��parentdir��װ��UmsProtectedobject
	 *         4�����ǳɹ������ж���������ou�Ƿ�Ϊ�գ���Ϊ�����ø��ڵ��ou�ӱ���id������ 5���������ɵ�ou�޸���װ�س����Ķ�����
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		// 1 Ԥ�����±���������������
		UmsProtectedobject currentObj = (UmsProtectedobject) obj;
		currentObj.setNaturalname(currentObj.getNaturalname().toUpperCase()
				.trim());
		currentObj.setCreated(new Date());
		// 2 ���������������naturalname
		String parentNaturalname = "";
		String parentOu = "";

		if ("0".equals(currentObj.getParentdir())) {// ���û�и��ڵ��ʱ��,ֱ��ʹ��app��naturalname
			parentOu = currentObj.getAppid().toString();
			UmsApplication app = (UmsApplication) appservice.fetchDao()
					.loadObject(UmsApplication.class, currentObj.getAppid());
			parentNaturalname = app.getNaturalname();
		} else {// ���ڸ��ڵ��ʱ��
			UmsProtectedobject parentObj = (UmsProtectedobject) OrmerEntry
					.fetchOrmer().fetchQuerister()
					.loadObject(UmsProtectedobject.class,
							currentObj.getParentdir());
			parentOu = parentObj.getOu();
			parentNaturalname = parentObj.getNaturalname();
		}

		// 3 �޶���ǰ�����ou��naturalname

		currentObj.setNaturalname(parentNaturalname + "."
				+ currentObj.getNaturalname());

		if (checkExist(currentObj.getNaturalname())) {
			return false;
		}

		OrmerEntry.fetchOrmer().fetchSerializer().create(currentObj);

		currentObj.setOu(parentOu + "." + currentObj.getId());
		return OrmerEntry.fetchOrmer().fetchSerializer().update(currentObj);

	}

	/**
	 * ������������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean creates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsProtectedobject uproobj = (UmsProtectedobject) iter.next();
			if (!create(uproobj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ɾ������
	 * 
	 * @param obj
	 *            UmsProtectedobject
	 * @return boolean
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		UmsProtectedobject temp = new UmsProtectedobject();
		temp.setParentdir(uproobj.getId());
		if (OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(temp,
				null) == 0) {
			return OrmerEntry.fetchOrmer().fetchSerializer().drop(uproobj);
		} else {
			return false;
		}
	}

	/**
	 * ����ɾ������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(List objs) {
		if (objs == null) {
			return false;
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().drops(objs);
	}

	/**
	 * ˢ�¶���
	 * 
	 * @param objClass
	 *            Class ������
	 * @param key
	 *            Serializable ����ID
	 */
	public void refreshObject(Object objClass, Serializable key) {
		throw new RuntimeException("�÷���û��ʵ��");
	}

	public boolean serial(Object obj) {
		throw new RuntimeException("�÷���û��ʵ��");
	}

	/**
	 * ���¶���
	 * 
	 * @param obj
	 *            UmsProtectedobject
	 * @return boolean
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().update(uproobj);
	}

	/**
	 * �������¶���
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsProtectedobject uproobj = (UmsProtectedobject) iter.next();
			if (!this.update(uproobj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * װ�ض���
	 * 
	 * @param objClass
	 *            Class ������
	 * 
	 * 
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public Object loadObject(Class objClass, Serializable key) {
		return OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass,
				key);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            UmsProtectedobject
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @return List ��������
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		if (obj == null) {
			return null;
		}
		
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			UmsProtectedobject up = new UmsProtectedobject();
			up.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(up,
					comparisonKey);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���֧��SQL����
	 * 
	 * @param Object
	 *            UmsProtectedobject
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(Object obj, Map comparisonKey, String conditionPre) {
		if (obj == null) {
			return null;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			uproobj.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					uproobj, comparisonKey, conditionPre);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            UmsProtectedobject
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(Object obj, Map comparisonKey, int from, int to) {
		if (obj == null) {
			return null;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			UmsProtectedobject up = new UmsProtectedobject();
			up.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(up,
					comparisonKey, from, to);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey, from, to);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ��SQL����
	 * 
	 * @param obj
	 *            UmsProtectedobject
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * @param conditionPre
	 *            String ��������,����SQL92��׼����Where�Ĳ�ѯ����,��ѯ�ֶ����ɲο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(Object obj, Map comparisonKey, int from, int to,
			String conditionPre) {
		if (obj == null) {
			return null;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			UmsProtectedobject up = new UmsProtectedobject();
			up.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(up,
					comparisonKey, from, to, conditionPre);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey, from, to, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param Object
	 *            UmsProtectedobject
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		if (obj == null) {
			return -1;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(
				uproobj, comparisonKey);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            UmsProtectedobject
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		if (obj == null) {
			return -1;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(
				uproobj, comparisonKey, conditionPre);
	}
	
	
	

}