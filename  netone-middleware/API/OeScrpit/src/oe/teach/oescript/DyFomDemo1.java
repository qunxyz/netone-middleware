package oe.teach.oescript;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

/**
 * Ӧ�ýű��ڲ����� DY  <br> ��ģʽͨ�������������ı���Ӧ�ã�ͨ������������ر����Խӱ�����
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class DyFomDemo1 extends OeScript {

	public static void main(String[] args) throws RemoteException {

		String formNaturalname = "BUSSFORM.BUSSFORM.PMS.DY_571239285556990";
		// Ӧ�ö�̬��
		usedata(formNaturalname);
		// Ӧ�ö�̬��������
		// useDatax(formNaturalname);
		// �޸ı�
		// modify(formNaturalname);
		// ��ѯ
		// query(formNaturalname);
		// ɾ������
		// delete(formNaturalname);
		
		
		System.out.println("flag!-------------------------------------");
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		String a1=dateformat.format(new java.util.Date());
		dy.set("$(lsh)"+":"+"$(formcode)","column18" , a1);
		dy.set("$(lsh)"+":"+"$(formcode)","column21" , "$(participant)");
	}

	/**
	 * Ӧ�ö�̬������Ӧ��
	 */
	public static void usedata(String formcode) {
		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = formcode;
		// ����һ����̬��ʵ��
		String lsh = dy.newInstance(dyformnaturalname);
		// ��ֵ(��ֵ�ֶβο��Ӷ�̬����WEB�������Ľ���б��еı�����XML�е�˵��)
		// ����uuid��paramName��attributeTypeָ�����ֶε�����
		dy.set(lsh, "column3", "mike");// �ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
		dy.set(lsh, "column4", 20);// �ο�Dy�������� column4Ϊ���������ֶΣ�����Ϊ����
		dy.set(lsh, "belongx", "���[dept.dept]");
		

		// ��ȡ�����ֶ�ֵ
		String name = dy.get(lsh, "column3");
		int age = dy.getn(lsh, "column4");
		// �������ӡ������̨
		System.out.println("i'm " + name + " my age is " + age);

	}

	/**
	 * ������Ķ�̬������Ӧ��
	 */
	public static void useDatax(String formcode) {
		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = formcode;
		// ����һ����̬��ʵ��
		String lsh = dy.newInstance(dyformnaturalname);
		Object objin = dy.getInobj(lsh);// ��Զ�̶���װ�ص�����
		// ��ֵ(��ֵ�ֶβο��Ӷ�̬����WEB�������Ľ���б��еı�����XML�е�˵��)
		// ����uuid��paramName��attributeTypeָ�����ֶε�����
		dy.setIn("column3", "jim", objin);// �ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
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
	public static void query(String formcode) {

		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = formcode;
		// ������ѯ����Ҳ����Ϊ�գ�
		TCsBus bus = new TCsBus();
		// ��������,�ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
		bus.setColumn3("mike");
		// ����ѯ�Ľ������list������
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// �ο�Dy�������� column4Ϊ���������ֶΣ�����Ϊ����
		// ͨ��ѭ�������������ӡ�ڿ���̨
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			System.out
					.println("mike's age is " + dy.getOutn("column4", object));
		}
	}

	/**
	 * ����ѯ�����Ķ������������Ϊ12��
	 */
	public static void modify(String formcode) {
		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = formcode;
		// ������ѯ����Ҳ����Ϊ�գ�
		TCsBus bus = new TCsBus();
		// �������� ��������,�ο�Dy�������� column3Ϊ�����ֶΣ�����Ϊ�ַ���
		bus.setColumn3("mike");
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// �ο�Dy�������� column4Ϊ���������ֶΣ�����Ϊ����
		// ͨ��ѭ�������������������ݼ�¼�����޸�
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column4", 50, object);
			dy.submit(object);
		}
	}

	/**
	 * ɾ�����¼
	 * 
	 * @param formcode
	 *            ����naturalname
	 */
	public static void delete(String formcode) {

		// ����Դ��ע�������(�Ӷ�̬����WEB�������Ľ���б��л�ø�����)
		String dyformnaturalname = formcode;
		String lsh = null;
		// ������ѯ����Ҳ����Ϊ�գ�
		int operateCountSuccess = 0;// �����������ɾ�����ݸò����ĳɹ���
		int operatecountFail = 0;// �����������ɾ�����ݸò�����ʧ����
		TCsBus bus = new TCsBus();
		// ͳ��ָ�����ļ�¼����
		int datacout = dy.queryDataNum(formcode, bus, " and column4>40");
		// ����������������ݼ�¼������list����
		List list = dy.queryData(dyformnaturalname, bus, 0, datacout,
				" and column4>40");
		// ���������
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			String Deletecondition = object.getLsh() + ":"
					+ object.getFormcode();// ��ü�¼��lsh��forcode
			Boolean success = dy.deleteData(Deletecondition);// ɾ����¼
			if (success) {
				operateCountSuccess++;
			} else {
				operatecountFail++;
			}
		}
		// �ڿ���̨��ӡ�����Ľ��
		System.out.println("�ɹ�ɾ�����ݹ�" + operateCountSuccess + "��  ; ʧ��"
				+ operatecountFail + "��");

	}
}
