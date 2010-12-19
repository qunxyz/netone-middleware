package oe.cms.dao.blog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Blog {
	// ҳ���ͷ����
	String _PAGEGROUP = "PORTALPG.PORTALPG";

	// ��׼Portalet�е�����
	String _PORTALET_KEY_NAME = "$name";

	String _PORTALET_KEY_LINK = "$link";

	String _PORTALET_KEY_COLOR = "$color";

	String _PORTALET_KEY_HEIGHT = "$height";

	String _PORTALET_KEY_WIDTH = "$width";

	String _PORTALET_KEY_TIME = "$time";

	String _PORTALET_KEY_PARTICIPANT = "$participant";

	String _PORTALET_KEY_ELEMENTTYPE = "$elementtype";

	// --------------------------

	// ��׼portalet�Ľṹ
	String _PORTALET_HEAD_MARK1 = "<table  border='1' class='HeaderTable'><tr class='HeaderColor'><td><font class='HeaderText'>$name</font>&nbsp;&nbsp;<font class='HeaderLink'>$link</font></td></tr><tr>";
	String _PORTALET_HEAD_MARK2 = "<table  border='0' class='HeaderTable'><tr class='HeaderColor'><td><font class='HeaderText'>$name</font>&nbsp;&nbsp;<font class='HeaderLink'>$link</font></td></tr><tr>";
	String _PORTALET_HEAD_MARK3 = "<table  border='1' class='HeaderTable'><tr>";
	String _PORTALET_HEAD_MARK4 = "<table  border='0' class='HeaderTable'><tr>";

	String _PORTALET_START_MARK = "<tr> <td height='$height' width='$width' class='BodyColor' style='word-break:break-all'>";

	String _PORTALET_END_MARK = "</td></tr></table>";

	String _PORTALET_BODY_START_MARK_PRE = "<div style='width:$widtht;height:$height;overflow:auto;'>";

	String _PORTALET_BODY_END_MARK_PRE = "</div>";

	String _PORTALET_BODY_START_MARK = "\n<table id='$$xxxqqffcc123'>";

	String _PORTALET_BODY_END_MARK = "\n<tr style='display:none'><td>end items</td></tr></table>";

	String _PORTALET_ELEMENT_START_MARK = "\n<tr><td>";

	String _PORTALET_ELEMENT_END_MARK = "\n</td></tr>";

	// Portal Ӧ��
	String _PORTAL_APP_TITLE = "BarTitle";

	String _PORTAL_APP_HEIGHT = "$height";

	String _PORTAL_APP_WIDTH = "$width";

	String _PORTAL_APP_URL = "$url";

	String _PORTAL_APP_BODY = "$bodyx";

	String _PORTAL_APP_CONTEXTPATH = "$CONTEXTPATH";

	String _PORTAL_APP_WITH_BAR = "<table  border='1' class='SubHeaderTable'><tr class='SubHeaderColor'>"
			+ "<td width='$width'><table border='0' width='100%'><tr><td><font class='SubHeaderText'>BarTitle</font></td><td align='right'><a href=\"javascript:\" onClick=\"$bodyx.style.display='none'\"><img src='$CONTEXTPATH/image/portal/icon_ar_down.gif' BORDER='0'/></a> <a href='javascript:' onClick=\"$bodyx.style.display=''\"><img src='$CONTEXTPATH/image/portal/icon_ar_up.gif' BORDER='0'/></a></td></tr></table></td></tr>"
			+ "<tr id='$bodyx'><td height='$height' width='$width' class='BodyColor'><iframe src='$url' height='$height' width='$width' frameborder='0'></iframe></td></tr></table>";

	String _PORTAL_APP_ = "<iframe src='$url' height='$height' width='$width' frameborder='0'></iframe>";

	/**
	 * ע����˲���,Ĭ���ǹ������˲�����Դ
	 * 
	 * @param userid
	 * @param username
	 * @return
	 */
	Long regeidtBlog(String userid);

	/**
	 * ���ͼƬ
	 * 
	 * @param rep
	 *            http�����
	 * @param body
	 *            ͼƬID
	 * @param participant
	 *            ����
	 * @param needflash
	 *            �Ƿ���Ҫˢ�°���(true����Ҫˢ�°���,false��������ƽ���������ˢ��)
	 */
	void addPic(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	/**
	 * ����ļ�
	 * 
	 * @param rep
	 *            http�����
	 * @param body
	 *            �ļ�ID
	 * @param participant
	 *            ����
	 * @param needflash
	 *            �Ƿ���Ҫˢ�°���(true����Ҫˢ�°���,false��������ƽ���������ˢ��)
	 */
	void addFile(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	void addZip(HttpServletRequest req, HttpServletResponse rep,
			String participant);



	/**
	 * ���׽ҳ
	 * 
	 * @param rep
	 *            http�����
	 * @param body
	 *            �ļ�ID
	 * @param participant
	 *            ����
	 * @param needflash
	 *            �Ƿ���Ҫˢ�°���(true����Ҫˢ�°���,false��������ƽ���������ˢ��)
	 */
	void addCutFish(HttpServletRequest req, HttpServletResponse rep,
			String participant);



	/**
	 * �������
	 * 
	 * @param rep
	 *            http�����
	 * @param body
	 *            �ļ�ID
	 * @param participant
	 *            ����
	 * @param needflash
	 *            �Ƿ���Ҫˢ�°���(true����Ҫˢ�°���,false��������ƽ���������ˢ��)
	 */
	void addArticle(HttpServletRequest req, HttpServletResponse rep,
			String participant);


	/**
	 * ��ӿհײ��ֿ�
	 * 
	 * @param rep
	 *            http�����
	 * @param height
	 *            �߶�
	 * @param weidth
	 *            ���
	 * @param participant
	 *            ����
	 * @param needflash
	 *            �Ƿ���Ҫˢ�°���(true����Ҫˢ�°���,false��������ƽ���������ˢ��)
	 */
	void addUrl(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	/**
	 * ���ҳ
	 * 
	 * @param rep
	 *            http�����
	 * @param height
	 *            �߶�
	 * @param weidth
	 *            ���
	 * @param participant
	 *            ����
	 * @param needflash
	 *            �Ƿ���Ҫˢ�°���(true����Ҫˢ�°���,false��������ƽ���������ˢ��)
	 */
	void addPage(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	void updatePage(HttpServletRequest request, HttpServletResponse rep,
			String participant);

	/**
	 * ��� OecScript
	 * 
	 * @param rep
	 *            http�����
	 * @param body
	 *            ͼƬID
	 * @param participant
	 *            ����
	 * @param needflash
	 *            �Ƿ���Ҫˢ�°���(true����Ҫˢ�°���,false��������ƽ���������ˢ��)
	 */
	void addOecScript(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	/**
	 * �����ⲿ��
	 * 
	 * @param req
	 * @param rep
	 * @param participant
	 */
	void addOutItem(HttpServletRequest req, HttpServletResponse rep,
			String participant);

}
