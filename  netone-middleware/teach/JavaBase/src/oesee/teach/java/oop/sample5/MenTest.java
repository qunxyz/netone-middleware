package oesee.teach.java.oop.sample5;

public class MenTest {

	public static void main(String[] args) {
		// 创建功能类,支持业务操作
		MenDao mendao = new MenDaoImpl_();

		// 人员mike的信息
		MenInfo men1 = new MenInfo();
		men1.setName("mike");
		men1.setAge(12);
		men1.setDescription("i am mike");

		String id1 = mendao.add(men1);
		// 人员jim的信息
		MenInfo men2 = new MenInfo();
		men2.setName("jim");
		men2.setAge(18);
		men2.setDescription("i am jim");
		String id2 = mendao.add(men2);
		// 人员lucy的信息
		MenInfo men3 = new MenInfo();
		men3.setName("lucy");
		men3.setAge(20);
		men3.setDescription("i am lucy");

		String id3 = mendao.add(men3);
		// 删除 mike 信息
		mendao.del(id1);

		// 查询所有人员
		MenInfo[] meninfolist = mendao.query();
		for (int i = 0; i < meninfolist.length; i++) {
			System.out.println(meninfolist[i].getName());
		}

	}

	public MenInfo[] todo() {
		// 创建功能类,支持业务操作
		MenDao mendao = new MenDaoImpl_();

		// 人员mike的信息
		MenInfo men1 = new MenInfo();
		men1.setName("mike");
		men1.setAge(12);
		men1.setDescription("i am mike");

		String id1 = mendao.add(men1);
		// 人员jim的信息
		MenInfo men2 = new MenInfo();
		men2.setName("jim");
		men2.setAge(18);
		men2.setDescription("i am jim");
		String id2 = mendao.add(men2);

		// 删除 mike 信息
		mendao.del(id1);

		// 返回所有人员
		return mendao.query();
	}

}
