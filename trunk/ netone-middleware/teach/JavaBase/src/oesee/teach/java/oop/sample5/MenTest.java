package oesee.teach.java.oop.sample5;

public class MenTest {

	public static void main(String[] args) {
		// ����������,֧��ҵ�����
		MenDao mendao = new MenDaoImpl_();

		// ��Աmike����Ϣ
		MenInfo men1 = new MenInfo();
		men1.setName("mike");
		men1.setAge(12);
		men1.setDescription("i am mike");

		String id1 = mendao.add(men1);
		// ��Աjim����Ϣ
		MenInfo men2 = new MenInfo();
		men2.setName("jim");
		men2.setAge(18);
		men2.setDescription("i am jim");
		String id2 = mendao.add(men2);
		// ��Աlucy����Ϣ
		MenInfo men3 = new MenInfo();
		men3.setName("lucy");
		men3.setAge(20);
		men3.setDescription("i am lucy");

		String id3 = mendao.add(men3);
		// ɾ�� mike ��Ϣ
		mendao.del(id1);

		// ��ѯ������Ա
		MenInfo[] meninfolist = mendao.query();
		for (int i = 0; i < meninfolist.length; i++) {
			System.out.println(meninfolist[i].getName());
		}

	}

	public MenInfo[] todo() {
		// ����������,֧��ҵ�����
		MenDao mendao = new MenDaoImpl_();

		// ��Աmike����Ϣ
		MenInfo men1 = new MenInfo();
		men1.setName("mike");
		men1.setAge(12);
		men1.setDescription("i am mike");

		String id1 = mendao.add(men1);
		// ��Աjim����Ϣ
		MenInfo men2 = new MenInfo();
		men2.setName("jim");
		men2.setAge(18);
		men2.setDescription("i am jim");
		String id2 = mendao.add(men2);

		// ɾ�� mike ��Ϣ
		mendao.del(id1);

		// ����������Ա
		return mendao.query();
	}

}
