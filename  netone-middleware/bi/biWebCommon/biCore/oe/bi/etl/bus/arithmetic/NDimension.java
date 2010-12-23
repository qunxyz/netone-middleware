package oe.bi.etl.bus.arithmetic;

public interface NDimension {
	/**
	 * 生成空间坐标条件（最高可20维）
	 * 
	 * @param dimCondition
	 * @return
	 */
	String[] nDimension(String[][] dimCondition,String symbol);
}
