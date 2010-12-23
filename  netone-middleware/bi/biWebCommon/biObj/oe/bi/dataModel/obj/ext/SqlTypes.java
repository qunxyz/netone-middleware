package oe.bi.dataModel.obj.ext;

/**
 * 字段的类型
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface SqlTypes {
	String[] _DIM_TYPE_DATE = { "date", "日期YYYY-MM-DD" };

	String[] _DIM_TYPE_STRING = { "string", "字符" };

	String[] _DIM_TYPE_NUMBER = { "number", "数值" };

	String[][] _DIM_TYPE_ALL = { { "date", "日期YYYY-MM-DD" },
			{ "string", "字符" }, { "number", "数值" } };

}
