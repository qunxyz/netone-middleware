package oe.bi.dataModel.bus;

public interface DigAtomSQL {
	String[] fetchAtom(String datamodelid, String dimcolumnid, String[] node);
}
