package oe.security3a.seucore.resourceser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.SearchControls;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;

import org.apache.commons.lang.StringUtils;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * ��������Daoʵ��
 * 
 * @author ni.he.qing
 * 
 */
public class ProtectedObjectDaoImpl2 implements ProtectedObjectDao {

	private ApplicationService appservice = (ApplicationService) SeuserEntry.iv("applicationService");

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
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj, null);
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
		currentObj.setNaturalname(currentObj.getNaturalname().toUpperCase().trim());
		currentObj.setCreated(new Date());
		// 2 ���������������naturalname
		String parentNaturalname = "";
		String parentOu = "";
		String parentDir = "";

		if ("0".equals(currentObj.getParentdir())) {// ���û�и��ڵ��ʱ��,ֱ��ʹ��app��naturalname
			parentOu = currentObj.getAppid().toString();
			UmsApplication app = (UmsApplication) appservice.fetchDao().loadObject(UmsApplication.class,
					currentObj.getAppid());
			parentNaturalname = app.getNaturalname();
			parentDir = "dc=" + app.getName();
		} else {// ���ڸ��ڵ��ʱ��
			UmsProtectedobject parentObj = (UmsProtectedobject) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(
					UmsProtectedobject.class, currentObj.getParentdir());
			parentOu = parentObj.getOu();
			parentNaturalname = parentObj.getNaturalname();
			parentDir = "ou=" + parentNaturalname + "," + parentObj.getParentdir();
		}
		// 3 �޶���ǰ�����ou��naturalname
		currentObj.setNaturalname(parentNaturalname + "." + currentObj.getNaturalname());
		if (checkExist(currentObj.getNaturalname())) {
			return false;
		}
		currentObj.setId(IdServer.uuid());
		currentObj.setOu(parentOu + "." + currentObj.getId());
		currentObj.setParentdir(parentDir);
		return OrmerEntry.fetchOrmer().fetchSerializer().create(currentObj);
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
		temp.setParentdir("ou=" + uproobj.getNaturalname() + "," + uproobj.getParentdir());
		Map comparisonKey = new LinkedHashMap();
		comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		if (OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(temp, comparisonKey) == 0) {
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
		return OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass, key);
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
		return queryObjects(obj, comparisonKey, null);
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
		}
		if ("0".equals(uproobj.getParentdir())) {
			UmsApplication app = (UmsApplication) appservice.fetchDao().loadObject(UmsApplication.class,
					uproobj.getAppid());
			String name = app.getName();
			UmsProtectedobject tmp = new UmsProtectedobject();
			uproobj.setParentdir("dc=" + name);
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		} else if (StringUtils.isNotEmpty(uproobj.getParentdir())) {
			UmsProtectedobject tmp = (UmsProtectedobject) loadObject(UmsProtectedobject.class, uproobj.getParentdir());
			uproobj.setParentdir("ou=" + tmp.getNaturalname() + "," + tmp.getParentdir());
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj, comparisonKey, conditionPre);
		if (list != null && !list.isEmpty()) {
			List newlist = new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsProtectedobject upo = (UmsProtectedobject) iterator.next();
				if (StringUtils.isNotEmpty(upo.getId())) {
					newlist.add(upo);
				}
			}
			list = newlist;
		}
		return list;
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
		return queryObjects(obj, comparisonKey, from, to, null);
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
	public List queryObjects(Object obj, Map comparisonKey, int from, int to, String conditionPre) {
		if (obj == null) {
			return null;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			uproobj.setParentdir(keyReal);
		}
		if ("0".equals(uproobj.getParentdir())) {
			UmsApplication app = (UmsApplication) appservice.fetchDao().loadObject(UmsApplication.class,
					uproobj.getAppid());
			String name = app.getName();
			UmsProtectedobject tmp = new UmsProtectedobject();
			uproobj.setParentdir("dc=" + name);
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		} else if (StringUtils.isNotEmpty(uproobj.getParentdir())) {
			UmsProtectedobject tmp = (UmsProtectedobject) loadObject(UmsProtectedobject.class, uproobj.getParentdir());
			uproobj.setParentdir("ou=" + tmp.getNaturalname() + "," + tmp.getParentdir());
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj, comparisonKey, from, to,
				conditionPre);
		if (list != null && !list.isEmpty()) {
			List newlist = new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsProtectedobject upo = (UmsProtectedobject) iterator.next();
				if (StringUtils.isNotEmpty(upo.getId())) {
					newlist.add(upo);
				}
			}
			list = newlist;
		}
		return list;
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
		List list = queryObjects(obj, comparisonKey);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
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
	public long queryObjectsNumber(Object obj, Map comparisonKey, String conditionPre) {
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

}