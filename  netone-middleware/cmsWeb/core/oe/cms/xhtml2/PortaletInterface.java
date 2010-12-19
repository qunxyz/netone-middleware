package oe.cms.xhtml2;

/**
 * 体现Poralet相关的应用
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface PortaletInterface {
	/**
	 * 获得历史portalet的信息
	 * 
	 * @param cellid 
	 * @param distanceToCurrent
	 * @return
	 */
	String selectHisPor(String cellid, String distanceToCurrent);

	/**
	 * 获得历史咨询元的信息
	 * 
	 * @param distanceToCurrent
	 * @return
	 */
	String selectHisPage(String distanceToCurrent);

	/**
	 * 创建一块portal元素
	 * 
	 * @param tableinfo
	 * @param titleinfo
	 * @param titlebgcolor
	 * @param bodyinfo
	 * @param bodybgcolor
	 * @return
	 */
	String makePorEle(String tableType, String titleinfo,
			String titlebgcolor, String bodyinfo, String bodybgcolor);

	/**
	 * 合并信息
	 * 
	 * 
	 * @param cellid
	 * @return
	 */
	String insertPorInfo(String cellid);
	
	

}
