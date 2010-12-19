package oe.cms.xhtml2;

/**
 * WEB展示功能
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface ViewInterface {
	/**
	 * 打开窗口显示详细信息
	 * 
	 * @param cellid
	 * 
	 * @param newWindowInfo
	 * @param target
	 * @return
	 */
	String openDetailInfo(String cellid, String newWindowInfo, String target);

	/**
	 * 获得链接JPP资源的URL地址
	 * 
	 * @param cellid
	 * @return
	 */
	String detailInfoCoreUrl(String cellid);

	/**
	 * 链接显示详细信息
	 * 
	 * @param cellid
	 * @param newWindowInfo
	 * @param target
	 * @return
	 */
	String linkDetailInfo(String cellid, String title, String target);

	/**
	 * 2级下拉选择
	 * 
	 * @param select1
	 *            第一级下拉参数
	 * @param select2
	 *            第二级下拉参数
	 * @param info
	 *            要显示的信息 info的个数为multiselect中每个数组的个数的乘积
	 * @return
	 */

	String selectInfo(String[] select1, String[] select2, String[] info);

	/**
	 * 1级下拉选择
	 * 
	 * @param select
	 *            展示的信息列表
	 * @param info
	 *            信息列表的标题（select的个数必须等于info的个数）
	 * @return
	 */

	String selectInfo(String[] select, String[] info);

	/**
	 * 展示表格
	 * 
	 * @param info
	 *            表格数据
	 * @param title
	 *            标题
	 * @param tableHead
	 *            表格头样式,null的话是用默认样式
	 * @return
	 */
	String dispTable(String[][] info, String[] title);

	/**
	 * 展示列信息，需要手工加入tabale头尾信息
	 * 
	 * @param info
	 *            表格数据
	 * @return
	 */
	String dispRowOnly(String[][] info);

	/**
	 * 展示图表(双坐标轴展示,左轴为柱图指标,右轴对应为线图指标)
	 * 
	 * @param dimvaluelist
	 *            维度数值
	 * @param dimName
	 *            维度名称
	 * @param targetvaluelistLeft
	 *            指标值(左坐标)
	 * @param targetvaluelistRight
	 *            指标值(右坐标)
	 * @param targetnameLeft
	 *            指标名(左坐标)
	 * @param targetnameLRight
	 *            指标名(右坐标)
	 * @param title
	 *            标题
	 * @param is3D
	 *            是否3D
	 * @param graphwidth
	 *            图表的宽度
	 * @param graphheight
	 *            图表的高度
	 * @return
	 */
	String fetchGraph2Coordinate(String[] dimvaluelist, String dimName,
			String[][] targetvaluelistLeft, String[][] targetvaluelistRight,
			String[] targetnameLeft, String[] targetnameLRight, String title,
			String is3D, String graphwidth, String graphheight);

	/**
	 * 展示图表(单轴)
	 * 
	 * @param dimvaluelist
	 *            维度数值
	 * @param dimName
	 *            维度名称
	 * @param targetvaluelist
	 *            指标值
	 * @param targetname
	 *            指标名
	 * @param charttype
	 *            图表类型(线\柱\3D柱\饼\3D饼\柱线对比\3D柱线对比)
	 * @param title
	 *            标题
	 * @param graphwidth
	 *            图表的宽度
	 * @param graphheight
	 *            图表的高度
	 * @return
	 */
	String fetchGraph(String[] dimvaluelist, String dimName,
			String[][] targetvaluelist, String[] targetname, String charttype,
			String title, String graphwidth, String graphheight);

	/**
	 * 获得图片
	 * 
	 * @param imgid
	 * @param style
	 * @return
	 */
	String fetchImg(String imgid, String style);

	/**
	 * 获得文件
	 * 
	 * @param fileid
	 * @param filename
	 * @return
	 */
	String fetchFile(String fileid, String filename);

	/**
	 * 获得值（可管理的数据值）
	 * 
	 * @param value
	 * @param cellid
	 * @param defaultValue
	 * @return
	 */
	String fetchValue(String id, String cellid, String defaultValue);

	/**
	 * 获得Html的值显示
	 * 
	 * @param value
	 * @param cellid
	 * @param defaultValue
	 * @return
	 */
	String fetchValueInfo(String id, String cellid, String defaultValue);

	/**
	 * 获得Html的值显示
	 * 
	 * @param value
	 * @param cellid
	 * @param defaultValue
	 * @return
	 */
	String valueInfo(String id, String cellid, String defaultValue);

	/**
	 * 修订html值的信息
	 * 
	 * @param valueExpress
	 * @param color
	 * @return
	 */
	String modifyValueColor(String valueExpress, String color);

	/**
	 * 插入页项
	 * 
	 * @param itemname
	 *            页项的命名
	 * @param request
	 *            http的请求对象
	 * @return
	 */
	String outitem(String itemname, Object request);

}
