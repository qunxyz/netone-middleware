package oe.bi.dataModel.obj.ext;

/**
 * �ֶε�����
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface SqlTypes {
	String[] _DIM_TYPE_DATE = { "date", "����YYYY-MM-DD" };

	String[] _DIM_TYPE_STRING = { "string", "�ַ�" };

	String[] _DIM_TYPE_NUMBER = { "number", "��ֵ" };

	String[][] _DIM_TYPE_ALL = { { "date", "����YYYY-MM-DD" },
			{ "string", "�ַ�" }, { "number", "��ֵ" } };

}
