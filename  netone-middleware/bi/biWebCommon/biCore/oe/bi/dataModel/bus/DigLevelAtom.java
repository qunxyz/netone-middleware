package oe.bi.dataModel.bus;

public interface DigLevelAtom {
	/**
	 * ���ݽڵ��øýڵ�����ˮƽ���ȵ�Ԫ�ؼ���
	 * 
	 * @return ÿ���ڵ��Ӧ����С����Ԫ�صļ��� ÿ��Ԫ���� ID,Name
	 */
	String[] fetchAtom(String datamodelid, String dimcolumnid, String node,
			String levelDimcolumnid);
}
