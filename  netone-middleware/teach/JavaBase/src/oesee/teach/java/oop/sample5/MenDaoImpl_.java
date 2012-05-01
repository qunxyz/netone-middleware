package oesee.teach.java.oop.sample5;

import java.util.List;

import oesee.teach.java.oop.xp.datatool1.DataPools;

public class MenDaoImpl_ implements MenDao {

	public String add(MenInfo men) {
		String id = DataPools.create(men);
		return id;
	}

	public void del(String id) {
		DataPools.delete(id);

	}

	public MenInfo[] query() {
		List list=DataPools.query();
		System.out.println(list.size());
		return (MenInfo[]) list.toArray(new MenInfo[0]);
	}

	public void update(MenInfo men) {
		DataPools.modify(men);

	}

}
