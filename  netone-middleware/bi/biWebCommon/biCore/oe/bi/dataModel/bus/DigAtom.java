package oe.bi.dataModel.bus;

public interface DigAtom {
	/**
	 * 根据节点获得该节点的最小粒度的元素集合
	 * 
	 * @return 每个节点对应的最小粒度元素的集合 每个元素是 ID,Name
	 */
	String[] fetchAtom(String datamodelid, String dimcolumnid, String node);
}
