package oe.cms.xhtml2;

/**
 * WEBչʾ����
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface ViewInterface {
	/**
	 * �򿪴�����ʾ��ϸ��Ϣ
	 * 
	 * @param cellid
	 * 
	 * @param newWindowInfo
	 * @param target
	 * @return
	 */
	String openDetailInfo(String cellid, String newWindowInfo, String target);

	/**
	 * �������JPP��Դ��URL��ַ
	 * 
	 * @param cellid
	 * @return
	 */
	String detailInfoCoreUrl(String cellid);

	/**
	 * ������ʾ��ϸ��Ϣ
	 * 
	 * @param cellid
	 * @param newWindowInfo
	 * @param target
	 * @return
	 */
	String linkDetailInfo(String cellid, String title, String target);

	/**
	 * 2������ѡ��
	 * 
	 * @param select1
	 *            ��һ����������
	 * @param select2
	 *            �ڶ�����������
	 * @param info
	 *            Ҫ��ʾ����Ϣ info�ĸ���Ϊmultiselect��ÿ������ĸ����ĳ˻�
	 * @return
	 */

	String selectInfo(String[] select1, String[] select2, String[] info);

	/**
	 * 1������ѡ��
	 * 
	 * @param select
	 *            չʾ����Ϣ�б�
	 * @param info
	 *            ��Ϣ�б�ı��⣨select�ĸ����������info�ĸ�����
	 * @return
	 */

	String selectInfo(String[] select, String[] info);

	/**
	 * չʾ���
	 * 
	 * @param info
	 *            �������
	 * @param title
	 *            ����
	 * @param tableHead
	 *            ���ͷ��ʽ,null�Ļ�����Ĭ����ʽ
	 * @return
	 */
	String dispTable(String[][] info, String[] title);

	/**
	 * չʾ����Ϣ����Ҫ�ֹ�����tabaleͷβ��Ϣ
	 * 
	 * @param info
	 *            �������
	 * @return
	 */
	String dispRowOnly(String[][] info);

	/**
	 * չʾͼ��(˫������չʾ,����Ϊ��ͼָ��,�����ӦΪ��ͼָ��)
	 * 
	 * @param dimvaluelist
	 *            ά����ֵ
	 * @param dimName
	 *            ά������
	 * @param targetvaluelistLeft
	 *            ָ��ֵ(������)
	 * @param targetvaluelistRight
	 *            ָ��ֵ(������)
	 * @param targetnameLeft
	 *            ָ����(������)
	 * @param targetnameLRight
	 *            ָ����(������)
	 * @param title
	 *            ����
	 * @param is3D
	 *            �Ƿ�3D
	 * @param graphwidth
	 *            ͼ��Ŀ��
	 * @param graphheight
	 *            ͼ��ĸ߶�
	 * @return
	 */
	String fetchGraph2Coordinate(String[] dimvaluelist, String dimName,
			String[][] targetvaluelistLeft, String[][] targetvaluelistRight,
			String[] targetnameLeft, String[] targetnameLRight, String title,
			String is3D, String graphwidth, String graphheight);

	/**
	 * չʾͼ��(����)
	 * 
	 * @param dimvaluelist
	 *            ά����ֵ
	 * @param dimName
	 *            ά������
	 * @param targetvaluelist
	 *            ָ��ֵ
	 * @param targetname
	 *            ָ����
	 * @param charttype
	 *            ͼ������(��\��\3D��\��\3D��\���߶Ա�\3D���߶Ա�)
	 * @param title
	 *            ����
	 * @param graphwidth
	 *            ͼ��Ŀ��
	 * @param graphheight
	 *            ͼ��ĸ߶�
	 * @return
	 */
	String fetchGraph(String[] dimvaluelist, String dimName,
			String[][] targetvaluelist, String[] targetname, String charttype,
			String title, String graphwidth, String graphheight);

	/**
	 * ���ͼƬ
	 * 
	 * @param imgid
	 * @param style
	 * @return
	 */
	String fetchImg(String imgid, String style);

	/**
	 * ����ļ�
	 * 
	 * @param fileid
	 * @param filename
	 * @return
	 */
	String fetchFile(String fileid, String filename);

	/**
	 * ���ֵ���ɹ��������ֵ��
	 * 
	 * @param value
	 * @param cellid
	 * @param defaultValue
	 * @return
	 */
	String fetchValue(String id, String cellid, String defaultValue);

	/**
	 * ���Html��ֵ��ʾ
	 * 
	 * @param value
	 * @param cellid
	 * @param defaultValue
	 * @return
	 */
	String fetchValueInfo(String id, String cellid, String defaultValue);

	/**
	 * ���Html��ֵ��ʾ
	 * 
	 * @param value
	 * @param cellid
	 * @param defaultValue
	 * @return
	 */
	String valueInfo(String id, String cellid, String defaultValue);

	/**
	 * �޶�htmlֵ����Ϣ
	 * 
	 * @param valueExpress
	 * @param color
	 * @return
	 */
	String modifyValueColor(String valueExpress, String color);

	/**
	 * ����ҳ��
	 * 
	 * @param itemname
	 *            ҳ�������
	 * @param request
	 *            http���������
	 * @return
	 */
	String outitem(String itemname, Object request);

}
