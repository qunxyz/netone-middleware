package oe.bi.dataModel.bus;

public interface DigAtom {
	/**
	 * ���ݽڵ��øýڵ����С���ȵ�Ԫ�ؼ���
	 * 
	 * @return ÿ���ڵ��Ӧ����С����Ԫ�صļ��� ÿ��Ԫ���� ID,Name
	 */
	String[] fetchAtom(String datamodelid, String dimcolumnid, String node);
}
