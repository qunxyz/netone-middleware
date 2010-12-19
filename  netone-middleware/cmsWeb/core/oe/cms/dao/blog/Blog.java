package oe.cms.dao.blog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Blog {
	// 页组的头部分
	String _PAGEGROUP = "PORTALPG.PORTALPG";

	// 标准Portalet中的属性
	String _PORTALET_KEY_NAME = "$name";

	String _PORTALET_KEY_LINK = "$link";

	String _PORTALET_KEY_COLOR = "$color";

	String _PORTALET_KEY_HEIGHT = "$height";

	String _PORTALET_KEY_WIDTH = "$width";

	String _PORTALET_KEY_TIME = "$time";

	String _PORTALET_KEY_PARTICIPANT = "$participant";

	String _PORTALET_KEY_ELEMENTTYPE = "$elementtype";

	// --------------------------

	// 标准portalet的结构
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

	// Portal 应用
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
	 * 注册个人伯克,默认是公开个人伯克资源
	 * 
	 * @param userid
	 * @param username
	 * @return
	 */
	Long regeidtBlog(String userid);

	/**
	 * 添加图片
	 * 
	 * @param rep
	 *            http输出流
	 * @param body
	 *            图片ID
	 * @param participant
	 *            作者
	 * @param needflash
	 *            是否需要刷新版面(true代表要刷新版面,false代表在设计界面中无需刷新)
	 */
	void addPic(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	/**
	 * 添加文件
	 * 
	 * @param rep
	 *            http输出流
	 * @param body
	 *            文件ID
	 * @param participant
	 *            作者
	 * @param needflash
	 *            是否需要刷新版面(true代表要刷新版面,false代表在设计界面中无需刷新)
	 */
	void addFile(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	void addZip(HttpServletRequest req, HttpServletResponse rep,
			String participant);



	/**
	 * 添加捉页
	 * 
	 * @param rep
	 *            http输出流
	 * @param body
	 *            文件ID
	 * @param participant
	 *            作者
	 * @param needflash
	 *            是否需要刷新版面(true代表要刷新版面,false代表在设计界面中无需刷新)
	 */
	void addCutFish(HttpServletRequest req, HttpServletResponse rep,
			String participant);



	/**
	 * 添加文章
	 * 
	 * @param rep
	 *            http输出流
	 * @param body
	 *            文件ID
	 * @param participant
	 *            作者
	 * @param needflash
	 *            是否需要刷新版面(true代表要刷新版面,false代表在设计界面中无需刷新)
	 */
	void addArticle(HttpServletRequest req, HttpServletResponse rep,
			String participant);


	/**
	 * 添加空白布局块
	 * 
	 * @param rep
	 *            http输出流
	 * @param height
	 *            高度
	 * @param weidth
	 *            宽度
	 * @param participant
	 *            作者
	 * @param needflash
	 *            是否需要刷新版面(true代表要刷新版面,false代表在设计界面中无需刷新)
	 */
	void addUrl(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	/**
	 * 添加页
	 * 
	 * @param rep
	 *            http输出流
	 * @param height
	 *            高度
	 * @param weidth
	 *            宽度
	 * @param participant
	 *            作者
	 * @param needflash
	 *            是否需要刷新版面(true代表要刷新版面,false代表在设计界面中无需刷新)
	 */
	void addPage(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	void updatePage(HttpServletRequest request, HttpServletResponse rep,
			String participant);

	/**
	 * 添加 OecScript
	 * 
	 * @param rep
	 *            http输出流
	 * @param body
	 *            图片ID
	 * @param participant
	 *            作者
	 * @param needflash
	 *            是否需要刷新版面(true代表要刷新版面,false代表在设计界面中无需刷新)
	 */
	void addOecScript(HttpServletRequest req, HttpServletResponse rep,
			String participant);

	/**
	 * 增加外部项
	 * 
	 * @param req
	 * @param rep
	 * @param participant
	 */
	void addOutItem(HttpServletRequest req, HttpServletResponse rep,
			String participant);

}
