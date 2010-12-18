package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;

import org.apache.commons.lang.StringUtils;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.resourceser.ProtectedObjectService;

/**
 * ��ɫDaoʵ��(����UmsRolepermission)
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImplpermission2 implements RoleDao {

	private ProtectedObjectService pos = (ProtectedObjectService) SeuserEntry
			.iv("protectedObjectService");

	/**
	 * ��ȫ��죬�ڶ����м����û���Ϣ
	 * 
	 * @param obj
	 * @return
	 */
	public boolean checkSecrity(Object obj) {
		throw new RuntimeException("�÷���û��ʵ��");
	}

	/**
	 * �������
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		String dninfo = urper.getDninfo();
		dninfo = StringUtils.replace(dninfo, ".*", "");
		dninfo = StringUtils.replace(dninfo, ".-", "");
		UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
				.loadObject(UmsProtectedobject.class,
						StringUtils.substringAfterLast(dninfo, "."));
		dninfo = "ou=" + upo.getNaturalname() + "," + upo.getParentdir();
		urper.setDninfo(dninfo);
		urper.setLsh(Long.valueOf(IdServer.xnumID()));
		return OrmerEntry.fetchOrmer().fetchSerializer().create(urper);
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
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			if (!this.create(urper)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ɾ������
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().drop(urper);
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
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			if (!this.drop(urper)) {
				return false;
			}
		}
		return true;
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
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().update(urper);
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
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			if (!this.update(urper)) {
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
		UmsRolepermission umsper = (UmsRolepermission) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(objClass, key);
		UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
				.loadObject(UmsProtectedobject.class, umsper.getDninfo());
		String comment = umsper.getComments();
		if (StringUtils.contains(comment, "*")) {
			umsper.setDninfo(upo.getOu() + ".*");
		} else if (StringUtils.contains(comment, "-")) {
			umsper.setDninfo(upo.getOu() + ".-");
		} else {
			umsper.setDninfo(upo.getOu());
		}
		return umsper;
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            UmsRolepermission
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
	 *            UmsRolepermission
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
		UmsRolepermission urole = (UmsRolepermission) obj;
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				urole, comparisonKey, conditionPre);
		if (list != null && !list.isEmpty()) {
			List<UmsRolepermission> tmplist = new ArrayList<UmsRolepermission>();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRolepermission umsper = (UmsRolepermission) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
						.loadObject(UmsProtectedobject.class,
								umsper.getDninfo());
				String comment = umsper.getComments();
				if (StringUtils.contains(comment, "*")) {
					umsper.setDninfo(upo.getOu() + ".*");
				} else if (StringUtils.contains(comment, "-")) {
					umsper.setDninfo(upo.getOu() + ".-");
				} else {
					umsper.setDninfo(upo.getOu());
				}
				tmplist.add(umsper);
			}
			list = tmplist;
		}
		return list;
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            UmsRolepermission
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
	 *            UmsRolepermission
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
		UmsRolepermission urole = (UmsRolepermission) obj;
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				urole, comparisonKey, from, to, conditionPre);
		if (list != null && !list.isEmpty()) {
			List<UmsRolepermission> tmplist = new ArrayList<UmsRolepermission>();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRolepermission umsper = (UmsRolepermission) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
						.loadObject(UmsProtectedobject.class,
								umsper.getDninfo());
				String comment = umsper.getComments();
				if (StringUtils.contains(comment, "*")) {
					umsper.setDninfo(upo.getOu() + ".*");
				} else if (StringUtils.contains(comment, "-")) {
					umsper.setDninfo(upo.getOu() + ".-");
				} else {
					umsper.setDninfo(upo.getOu());
				}
				tmplist.add(umsper);
			}
			list = tmplist;
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
	 *            UmsRolepermission
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
	 *            UmsRolepermission
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
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		return false;
	}

}
