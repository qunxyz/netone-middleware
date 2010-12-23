package oe.bi.dataModel.bus;

public interface DigLevelAtomSQL {
	String[] fetchAtom(String datamodelid, String dimcolumnid, String[] node,
			String fromLevel, String toLevel);
}
