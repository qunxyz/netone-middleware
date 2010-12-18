package oe.security3a.seucore.obj;

public interface ProtectedObjectReference {
	// /////////////////////////////
	/**
	 * 页组
	 */
	String _OBJ_TYPE_PAGEGROUP = "pagegroup";

	// /////////////////////////////
	/**
	 * 页
	 */
	String _OBJ_TYPE_PAGE = "page";

	// /////////////////////////////
	/**
	 * 页项-页链接
	 */
	String _OBJ_TYPE_PAGELINK = "pagelink";

	String _OBJ_TYPE_ITEM = "item";

	/**
	 * 页项-图片
	 */
	String _OBJ_TYPE_PIC = "pic";

	/**
	 * 页项-文件
	 */
	String _OBJ_TYPE_FILE = "file";

	/**
	 * 页项-URL
	 */
	String _OBJ_TYPE_URL = "url";

	/**
	 * 页项-ZIP
	 */
	String _OBJ_TYPE_ZIP = "zip";

	/**
	 * 页项-文章
	 */
	String _OBJ_TYPE_ARTICLE = "article";

	/**
	 * 页项-WEB捕捉程序
	 */
	String _OBJ_TYPE_CUT = "cut";

	// /////////////////////////////
	/**
	 * 页项-portal程序()
	 */
	String _OBJ_TYPE_APP_DYFORM = "dyform";

	/**
	 * 页项-portal程序()
	 */
	String _OBJ_TYPE_APP_JSP = "jsp";

	/**
	 * 页项-portal程序()
	 */
	String _OBJ_TYPE_APP_JPP = "jpp";

	// /////////////////////////////
	/**
	 * 资源对象的包含属性
	 */
	String _OBJ_INCLUSTION_YES = "1";

	String _OBJ_INCLUSTION_NO = "0";

	/**
	 * 资源对象的有效
	 */
	String _OBJ_ACTIVE_YES = "1";

	String _OBJ_ACTIVE_NO = "0";

	/**
	 * 资源服务 URL
	 */
	String _RS_URI_SER = "%{resourceser}";

	/**
	 * 页面服务 URL
	 */
	String _PAGE_URI_USE = "%{pageser}";
}
