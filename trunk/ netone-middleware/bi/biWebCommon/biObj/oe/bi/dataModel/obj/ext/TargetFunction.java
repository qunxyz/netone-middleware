package oe.bi.dataModel.obj.ext;

/**
 * 指标统计函数
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface TargetFunction {
	String[] _TF_SUM = { "sum", "求和" };

	String[] _TF_COUNT = { "count", "求总数" };

	String[] _TF_AVG = { "avg", "平均值" };

	String[] _TF_MAX = { "max", "最大值" };

	String[] _TF_MIN = { "min", "最小值" };

	/**
	 * 默认统计函数avg
	 */
	String[][] _TF_ALL = { { "avg", "" }, { "sum", "求和" }, { "count", "求总数" },
			{ "avg", "平均值" }, { "max", "最大值" }, { "min", "最小值" } };

}
