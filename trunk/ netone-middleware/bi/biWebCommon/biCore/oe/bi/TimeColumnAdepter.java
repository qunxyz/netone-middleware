package oe.bi;

/**
 * 
 * @author chen.jia.xun
 *
 */
public interface TimeColumnAdepter {
	/**
	 * 处理 维度类型参数 对于维度可能存在的类型: SqlTypes中的除了Number类型外,其余的都将作为字符串来处理
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public String adaptStringNumberDimensionTypes(String dimensionvalue,
			String type);

	/**
	 * 处理 维度类型参数 对于维度可能存在的类型: SqlTypes中的除了Number类型外,其余的都将作为字符串来处理
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public String adaptDateDimensionTypes(String dimensionvalue, String dimId);
}
