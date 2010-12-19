package oe.cms.xhtml2;

/**
 * ����Poralet��ص�Ӧ��
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface PortaletInterface {
	/**
	 * �����ʷportalet����Ϣ
	 * 
	 * @param cellid 
	 * @param distanceToCurrent
	 * @return
	 */
	String selectHisPor(String cellid, String distanceToCurrent);

	/**
	 * �����ʷ��ѯԪ����Ϣ
	 * 
	 * @param distanceToCurrent
	 * @return
	 */
	String selectHisPage(String distanceToCurrent);

	/**
	 * ����һ��portalԪ��
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
	 * �ϲ���Ϣ
	 * 
	 * 
	 * @param cellid
	 * @return
	 */
	String insertPorInfo(String cellid);
	
	

}
