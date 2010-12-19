package oe.cms.xhtml2;

/**
 * 分析入口<br>
 * 主要是在数据源的基础上，进一步的改造数据，改造的结果为最终的数据展现 提供依据
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface AnalysisInterface {
	/**
	 * 增加分析数据的维度
	 * 
	 * @param dimOri
	 *            分析维度
	 * @param targetOri
	 *            指标组
	 * @param dimNew
	 *            维度组
	 * @return
	 */
	String[][] addDim(String[][] dimOri, String[][] targetOri, String[] dimNew);

	/**
	 * 将参差不齐的维度和指标，智能的排列对齐
	 * 
	 * @param dim
	 *            维度组
	 * @param tar
	 *            指标组
	 * @return 第一个元素是 统一的维度，后面依次是按照参数顺序的指标
	 */
	String[][] adpetTargetDim(String[][] dim, String target[][]);

	/**
	 * 坐标转置
	 * 
	 * @param relation
	 * @return
	 */
	String[][] circleRelationCoordinate(String[][] relation);


	// topN,buttonN,Limit,orderAbs,orderDesc,forcase

}
