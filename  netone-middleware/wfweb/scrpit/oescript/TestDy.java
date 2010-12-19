package oescript;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import oe.cav.bean.logic.bus.TCsBus;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.DyScriptFunction;

public class TestDy {

	static DyScriptFunction dy = (DyScriptFunction) WfEntry.fetchBean("dy");

	public static void main(String[] args) throws RemoteException {
		// Ӧ�ö�̬��
		usedata();
		// Ӧ�ö�̬��������
		useDatax();
		// �޸ı�
		modify();
		// ��ѯ
		query();

	}

	/**
	 * Ӧ�ö�̬������Ӧ��
	 */
	public static void usedata() {
		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// ����һ����̬��ʵ��
		String lsh = dy.newInstance(dyformnaturalname);
		// ��ֵ(��ֵ�ֶβο��Ӷ�̬����WEB�������Ľ���б��еı�����XML�е�˵��)
		// ����uuid��paramName��attributeTypeָ�����ֶε�����
		dy.set(lsh, "column3", "mike");// �ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
		dy.set(lsh, "column4", 20);// �ο�Dy�������� column4Ϊ���������ֶΣ�����Ϊ����
		// ��ȡ�����ֶ�ֵ
		String name = dy.get(lsh, "column3");
		int age = dy.getn(lsh, "column4");
		System.out.println("i'm " + name + " my age is " + age);

	}

	/**
	 * ������Ķ�̬������Ӧ��
	 */
	public static void useDatax() {
		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// ����һ����̬��ʵ��
		String lsh = dy.newInstance(dyformnaturalname);
		Object objin = dy.getInobj(lsh);// ��Զ�̶���װ�ص�����
		// ��ֵ(��ֵ�ֶβο��Ӷ�̬����WEB�������Ľ���б��еı�����XML�е�˵��)
		// ����uuid��paramName��attributeTypeָ�����ֶε�����
		dy.setIn("column3", "mike", objin);// �ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
		dy.setIn("column4", 20, objin);// �ο�Dy�������� column4Ϊ���������ֶΣ�����Ϊ����
		dy.submit(objin);// ��Ҫ�ֹ��ύ������Զ�̶���

		// ��ȡ�����ֶ�ֵ
		Object objout = dy.getOutobj(lsh);// ��Զ�̶���װ�ص����أ�ע�⶯̬���������objout��֮ǰ��inobj����ͬ��Զ�̶���
		String name = dy.getOut("column3", objout);
		int age = dy.getOutn("column4", objout);
		System.out.println("i'm " + name + " my age is " + age);
	}

	/**
	 * ��ѯ�����������ļ�¼
	 */
	public static void query() {

		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// ������ѯ����Ҳ����Ϊ�գ�
		TCsBus bus = new TCsBus();
		// ��������,�ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
		bus.setColumn3("mike");
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// �ο�Dy�������� column4Ϊ���������ֶΣ�����Ϊ����
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			System.out.println("mike's age is" + dy.getOutn("column4", object));
		}
	}

	/**
	 * ����ѯ�����Ķ������������Ϊ12��
	 */
	public static void modify() {
		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// ������ѯ����Ҳ����Ϊ�գ�
		TCsBus bus = new TCsBus();
		// �������� ��������,�ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
		bus.setColumn3("mike");
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// �ο�Dy�������� column4Ϊ���������ֶΣ�����Ϊ����
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column4", 12, object);
			dy.submit(object);
		}

	}

}
