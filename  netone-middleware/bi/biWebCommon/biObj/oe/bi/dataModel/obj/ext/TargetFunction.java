package oe.bi.dataModel.obj.ext;

/**
 * ָ��ͳ�ƺ���
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface TargetFunction {
	String[] _TF_SUM = { "sum", "���" };

	String[] _TF_COUNT = { "count", "������" };

	String[] _TF_AVG = { "avg", "ƽ��ֵ" };

	String[] _TF_MAX = { "max", "���ֵ" };

	String[] _TF_MIN = { "min", "��Сֵ" };

	/**
	 * Ĭ��ͳ�ƺ���avg
	 */
	String[][] _TF_ALL = { { "avg", "" }, { "sum", "���" }, { "count", "������" },
			{ "avg", "ƽ��ֵ" }, { "max", "���ֵ" }, { "min", "��Сֵ" } };

}
