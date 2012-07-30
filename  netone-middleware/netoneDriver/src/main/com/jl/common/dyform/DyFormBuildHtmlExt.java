package com.jl.common.dyform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.AppObj;
import com.jl.entity.User;

public final class DyFormBuildHtmlExt {
	// 计算一行中列数为columnCount时，每列的宽度。
	public static int calcAvailColumnWidth(int columnCount) {
		columnCount = columnCount == 0 ? 1 : columnCount;
		return ((AvailWidth - AvailWidthCorrect) - (AvailWidth - AvailWidthCorrect)
				% columnCount)
				/ columnCount;
	}

	// 计算一行中列数为columnCount时，每列的宽度。
	public static int calcAvailColumnWidthN(int columnCount) {
		columnCount = columnCount == 0 ? 1 : columnCount;
		return ((AvailWidthN - AvailWidthCorrect) - (AvailWidthN - AvailWidthCorrect)
				% columnCount)
				/ columnCount;
	}

	private static ResourceBundle config = ResourceBundle.getBundle("config",
			Locale.CHINESE);
	public static final String projectname = config.getString("projectname");
	private static final String single_select = projectname
			+ "/frame.do?method=onSingleSelectResource";
	private static final String multi_select = projectname
			+ "/frame.do?method=onMultiSelectResource";
	private static final String edit_frame_ext = projectname
			+ "/frame.do?method=onMainViewExt";

	// 测试换行
	private static final String _N = "";

	/** */
	public static final String TABLE_FORM = "table_form";

	public static final String TABLE_TR_TITLE = "table_tr_title";
	public static final String TABLE_TD_TITLE = "table_td_title";

	public static final String TABLE_TR_HEADER = "table_tr_header";
	public static final String TABLE_TD_HEADER = "table_td_header";

	public static final String TABLE_TR_CONTENT = "table_tr_content";
	public static final String TABLE_TD_CONTENT = "table_td_content";

	public static final String FORM_TR = "form_tr";
	public static final String FORM_TD = "form_td";
	public static final String FORM_FIELD_CONTENT = "form_fieldcontent";
	public static final String FORM_FIELD_CAPTION = "form_fieldcaption";
	public static final String FORM_FIELD_CAPTION2 = "form_fieldcaption2";
	public static final String FORM_FIELD_INPUT = "form_fieldinput";
	public static final String FORM_FIELD_INPUT_READ = "form_fieldinput_read";

	public static final String _FORM_ID = "_FRAME_FORM_ID_";

	public static final int AvailWidth = 900;// 可用显示宽度
	public static final int AvailWidthN = 700;// 可用显示宽度
	public static final int AvailWidthN2 = 150;// 可用显示宽度
	public static final int AvailWidthCorrect = 8; // 可用显示宽度的修正值
	public static final int AvailNormalFieldTitleWidth = 80;// 普通字段标题宽度
	public static final int AvailNormalFieldCorrectWidth = 7;// 普通字段宽度修正值
	public static final int AvailExtBtnWidth = 30;// 扩展字段button宽度
	public static final String TableStyle = "";
	// public static final String TableStyle = " border-bottom:inset 3px
	// #C5E4FE;border-top:outset 3px #C5E4FE;border-left:3px outset
	// #C5E4FE;border-right:3px inset #C5E4FE; width:"
	// + AvailWidth + "px;table-layout:fixed; border-collapse:collapse;";
	public static final String TableExtProperties = " style=\"word-break:break-all;margin-top:2px;margin-bottom:2px;\" bgcolor=\"white\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"";
	public static final String TableTdStyle = "";
	public static final String TableTdStyle2 = "";

	/**
	 * 路由指定组件<BR>
	 * 以下组件开启事件<BR>
	 * 通用 数字/金额 邮件地址 列表信息 列表信息K-V 大文本
	 * 
	 * @param htmltype
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	protected static String routeAppointComp(String htmltype, String id,
			String value, String style, String classname, boolean readonly,
			String selectedvalue, String extvalue, String userinfo,
			String parameter, String defaultValue) {
		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
		}

		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (readonly)
			extvalue += " onfocus=\"this.blur()\" ";
		if (arr[0][0].equals(htmltype)) {// 00:通用
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[1][0].equals(htmltype)) {// 01:数字/金额
			return DyFormComp.getNumber(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[2][0].equals(htmltype)) {// 02:时间hh:mm:ss
			return DyFormComp.getTime(id, value, style, classname, readonly);
		} else if (arr[3][0].equals(htmltype)) {// 03:日期YYYY-MM-DD
			return DyFormComp.getDate(id, value, style, classname, readonly);
		} else if (arr[4][0].equals(htmltype)) {// 04:日期时间YYYY-MM-DD hh:mm:ss
			return DyFormComp
					.getDatetime(id, value, style, classname, readonly);
		} else if (arr[5][0].equals(htmltype)) {// 05:真假
			return DyFormComp.getBoolean(id, value, "", "", readonly);
		} else if (arr[6][0].equals(htmltype)) {// 06:邮件地址
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[7][0].equals(htmltype)) {// 10:列表信息
			return DyFormComp.getSelect(id, value, style, classname, readonly,
					selectedvalue, extvalue);
		} else if (arr[8][0].equals(htmltype)) {// 11:列表信息K-V
			return DyFormComp.getSelectKV(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[9][0].equals(htmltype)) {// 12:IP地址
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[10][0].equals(htmltype)) {// 13:大文本
			value = htmEncode(value);
			// return DyFormComp.getText(id, value, style, classname, readonly,
			// extvalue);
			return DyFormComp.getTextarea(id, value, style + "height:100px;",
					classname, readonly, extvalue);
		} else if (arr[11][0].equals(htmltype)) {// 14:文件
			return null;
		} else if (arr[12][0].equals(htmltype)) {// 15:图片
			if (StringUtils.isNotEmpty(value)) {
				return DyFormComp.getHref("管理", "管理", value, "", "_blank");
			}
			return "";
		} else if (arr[13][0].equals(htmltype)) {// 16:按钮
			return null;
		} else if (arr[14][0].equals(htmltype)) {// 17:单选资源
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[14][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[15][0].equals(htmltype)) {// 18:多选资源
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[15][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL项
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[16][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[17][0].equals(htmltype)) {// 21:多彩文档
			value = htmEncode2(value);
			return DyFormComp.getWysiwygUI(id, value, style, classname,
					readonly, "");
		} else if (arr[18][0].equals(htmltype)) {// 22:组织人员单选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[18][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[19][0].equals(htmltype)) {// 23:组织人员多选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[19][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[20][0].equals(htmltype)) {// 24:当前人员
			String userinfo_ = "";
			if (StringUtils.isNotEmpty(userinfo)) {
				userinfo_ = userinfo.split(",")[0];
			} else {
				userinfo_ = value;
			}
			return DyFormComp.getText(id, userinfo_, style, classname, true,
					extvalue);
		} else if (arr[21][0].equals(htmltype)) {// 25:当前部门
			String userinfo_ = "";
			if (StringUtils.isNotEmpty(userinfo)) {
				String[] x = userinfo.split(",");
				if (x.length == 2) {
					userinfo_ = userinfo.split(",")[1];
				}
			} else {
				userinfo_ = value;
			}
			return DyFormComp.getText(id, userinfo_, style, classname, true,
					extvalue);
		} else if (arr[22][0].equals(htmltype)) {// 26:多选选项
			return DyFormComp.getCheckboxs(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[23][0].equals(htmltype)) {// 27:组织机构单选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[23][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[24][0].equals(htmltype)) {// 28:组织机构多选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[24][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			return DyFormComp.getIframe(id, value, style, classname, readonly,
					parameter);
		} else if (arr[26][0].equals(htmltype)) {// 30:radio组
			return DyFormComp.getGroupRadio(id, value, style, classname,
					readonly, selectedvalue);
		} else if (arr[27][0].equals(htmltype)) {// 31:真假radio
			return DyFormComp.getBooleanRadio(id, value, "", "", readonly);
		} else if (arr[28][0].equals(htmltype)) {// 32:隐藏域
			return DyFormComp.getHiddenInput(id, value);
		} else {
			return "";
		}
	}

	/**
	 * 路由指定组件 不控制高度<BR>
	 * 以下组件开启事件<BR>
	 * 通用 数字/金额 邮件地址 列表信息 列表信息K-V 大文本
	 * 
	 * @param htmltype
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	protected static String routeAppointComp2(String htmltype, String id,
			String value, String style, String classname, boolean readonly,
			String selectedvalue, String extvalue, String userinfo,
			String parameter, String defaultValue) {
		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
		}

		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[0][0].equals(htmltype)) {// 00:通用
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[1][0].equals(htmltype)) {// 01:数字/金额
			return DyFormComp.getNumber(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[2][0].equals(htmltype)) {// 02:时间hh:mm:ss
			return DyFormComp.getTime(id, value, style, classname, readonly);
		} else if (arr[3][0].equals(htmltype)) {// 03:日期YYYY-MM-DD
			return DyFormComp.getDate(id, value, style, classname, readonly);
		} else if (arr[4][0].equals(htmltype)) {// 04:日期时间YYYY-MM-DD hh:mm:ss
			return DyFormComp
					.getDatetime(id, value, style, classname, readonly);
		} else if (arr[5][0].equals(htmltype)) {// 05:真假
			return DyFormComp.getBoolean(id, value, "", "", readonly);
		} else if (arr[6][0].equals(htmltype)) {// 06:邮件地址
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[7][0].equals(htmltype)) {// 10:列表信息
			return DyFormComp.getSelect(id, value, style, classname, readonly,
					selectedvalue, extvalue);
		} else if (arr[8][0].equals(htmltype)) {// 11:列表信息K-V
			return DyFormComp.getSelectKV(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[9][0].equals(htmltype)) {// 12:IP地址
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[10][0].equals(htmltype)) {// 13:大文本
			value = htmEncode(value);
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[11][0].equals(htmltype)) {// 14:文件
			return null;
		} else if (arr[12][0].equals(htmltype)) {// 15:图片
			if (StringUtils.isNotEmpty(value)) {
				return DyFormComp.getHref("管理", "管理", value, "", "_blank");
			}
			return "";
		} else if (arr[13][0].equals(htmltype)) {// 16:按钮
			return null;
		} else if (arr[14][0].equals(htmltype)) {// 17:单选资源
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[14][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[15][0].equals(htmltype)) {// 18:多选资源
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[15][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL项
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[16][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[17][0].equals(htmltype)) {// 21:多彩文档
			value = htmEncode2(value);
			return DyFormComp
					.getText(id, value, style, classname, readonly, "");
		} else if (arr[18][0].equals(htmltype)) {// 22:组织人员单选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[18][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[19][0].equals(htmltype)) {// 23:组织人员多选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[19][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[20][0].equals(htmltype)) {// 24:当前人员
			String userinfo_ = "";
			if (StringUtils.isNotEmpty(userinfo)) {
				userinfo_ = userinfo.split(",")[0];
			} else {
				userinfo_ = value;
			}
			return DyFormComp.getText(id, userinfo_, style, classname, true,
					extvalue);
		} else if (arr[21][0].equals(htmltype)) {// 25:当前部门
			String userinfo_ = "";
			if (StringUtils.isNotEmpty(userinfo)) {
				userinfo_ = userinfo.split(",")[1];
			} else {
				userinfo_ = value;
			}
			return DyFormComp.getText(id, userinfo_, style, classname, true,
					extvalue);
		} else if (arr[22][0].equals(htmltype)) {// 26:多选选项
			return DyFormComp.getCheckboxs(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[23][0].equals(htmltype)) {// 27:组织机构单选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[23][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[24][0].equals(htmltype)) {// 28:组织机构多选
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[24][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			return DyFormComp.getText(id, value, style, classname, readonly,
					parameter);
		} else if (arr[26][0].equals(htmltype)) {// 30:radio组
			return DyFormComp.getGroupRadio(id, value, style, classname,
					readonly, selectedvalue);
		} else if (arr[27][0].equals(htmltype)) {// 31:真假radio
			return DyFormComp.getBooleanRadio(id, value, "", "", readonly);
		} else if (arr[28][0].equals(htmltype)) {// 32:隐藏域
			return DyFormComp.getHiddenInput(id, value);
		} else {
			return "";
		}
	}

	/**
	 * 检查是否是输出文档多行文本字段内容
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkMultiDoc(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[10][0].equals(htmltype)) {// 13:大文本
			return true;
		} else if (arr[17][0].equals(htmltype)) {// 21:多彩文档
			return true;
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			return true;
		}
		return false;
	}

	/**
	 * 检查是否是输出文档多行文本字段内容
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkMultiDoc2(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[10][0].equals(htmltype)) {// 13:大文本
			return true;
		}
		return false;
	}

	/**
	 * 检查是否是真假
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkBoolean(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[5][0].equals(htmltype)) {// 05:真假
			return true;
		}
		return false;
	}

	/**
	 * 检查是否选择组件
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkSelect_(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[14][0].equals(htmltype)) {// 17:单选资源
			return true;
		} else if (arr[15][0].equals(htmltype)) {// 18:多选资源
			return true;
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL项
			return true;
		} else if (arr[18][0].equals(htmltype)) {// 22:组织人员单选
			return true;
		} else if (arr[19][0].equals(htmltype)) {// 23:组织人员多选
			return true;
		} else if (arr[23][0].equals(htmltype)) {// 27:组织机构单选
			return true;
		} else if (arr[24][0].equals(htmltype)) {// 28:组织机构多选
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 路由指定值
	 * 
	 * @param htmltype
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	protected static String routeAppointValue(String htmltype, String value,
			String selectedvalue) {
		return routeAppointValue(htmltype, value, selectedvalue, null);
	}

	/**
	 * 路由指定值
	 * 
	 * @param htmltype
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	protected static String routeAppointValue(String htmltype, String value,
			String selectedvalue, String type) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[0][0].equals(htmltype)) {// 00:通用
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[1][0].equals(htmltype)) {// 01:数字/金额
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[2][0].equals(htmltype)) {// 02:时间hh:mm:ss
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[3][0].equals(htmltype)) {// 03:日期YYYY-MM-DD
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[4][0].equals(htmltype)) {// 04:日期时间YYYY-MM-DD hh:mm:ss
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[5][0].equals(htmltype)) {// 05:真假
			if ("ext".equals(type)) {
				String columnid = value;
				return DyFormComp.getJsBooleanText(columnid, selectedvalue);
			}
			return DyFormComp.getBooleanText(value);
		} else if (arr[6][0].equals(htmltype)) {// 06:邮件地址
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[7][0].equals(htmltype)) {// 10:列表信息
			if ("ext".equals(type)) {
				return null;
			}
			return DyFormComp.getSelectText(value, selectedvalue);
		} else if (arr[8][0].equals(htmltype)) {// 11:列表信息K-V
			if ("ext".equals(type)) {
				return DyFormComp.getJsSelectTextKV(value, selectedvalue);
			}
			return DyFormComp.getSelectTextKV(value, selectedvalue);
			// if ("ext".equals(type)) {
			// return null;
			// }
			// return value;
		} else if (arr[9][0].equals(htmltype)) {// 12:IP地址
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[10][0].equals(htmltype)) {// 13:大文本
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode(value);
			return value;
		} else if (arr[11][0].equals(htmltype)) {// 14:文件
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode(value);
			return value;
		} else if (arr[12][0].equals(htmltype)) {// 15:图片
			if ("ext".equals(type)) {
				if (StringUtils.isNotEmpty(value)) {
					return DyFormComp.getJsFileHref(value, "", "管理");
				} else {
					return "";
				}
			}
			value = htmEncode(value);
			return DyFormComp.getHref("管理", "管理", value, "", "_blank");
		} else if (arr[13][0].equals(htmltype)) {// 16:按钮
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode(value);
			return value;
		} else if (arr[14][0].equals(htmltype)) {// 17:单选资源
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[15][0].equals(htmltype)) {// 18:多选资源
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL项
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[17][0].equals(htmltype)) {// 21:多彩文档
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode2(value);
			return value;
		} else if (arr[18][0].equals(htmltype)) {// 22:组织人员单选
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[19][0].equals(htmltype)) {// 23:组织人员多选
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[20][0].equals(htmltype)) {// 24:当前人员
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[21][0].equals(htmltype)) {// 25:当前部门
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[22][0].equals(htmltype)) {// 26:多选选项
			if ("ext".equals(type)) {
				return DyFormComp.getJsGroupRadioTextKV(value, selectedvalue);
			}
			return DyFormComp.getCheckboxsText(value, selectedvalue);
		} else if (arr[23][0].equals(htmltype)) {// 27:组织机构单选
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[24][0].equals(htmltype)) {// 28:组织机构多选
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			if ("ext".equals(type)) {
				return null;
			}
			return DyFormComp.getIframe("", value, "", "", false, "");
		} else if (arr[26][0].equals(htmltype)) {// 30:单选组
			if ("ext".equals(type)) {
				String columnid = value;
				return DyFormComp
						.getJsGroupRadioTextKV(columnid, selectedvalue);
			}
			return DyFormComp.getGroupRadioTextKV(value, selectedvalue);
		} else if (arr[27][0].equals(htmltype)) {// 31:真假radio
			if ("ext".equals(type)) {
				String columnid = value;
				return DyFormComp
						.getJsBooleanRadioText(columnid, selectedvalue);
			}
			return DyFormComp.getBooleanRadioText(value);
		} else if (arr[28][0].equals(htmltype)) {// 32:隐藏域
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else {
			return null;
		}
	}

	/**
	 * 获取每行最大列数
	 * 
	 * @param dyFormColumns
	 * @return
	 */
	protected static Map<Double, Double> getMaxYoffsetByX(
			DyFormColumn[] dyFormColumns) {
		Map<Double, Double> columnmap = new HashMap<Double, Double>();
		for (int i = 0; i < dyFormColumns.length; i++) {
			DyFormColumn _qc1 = dyFormColumns[i];
			Double yoffset = _qc1.getXoffset();
			Double xoffset = _qc1.getYoffset();
			boolean hidden = _qc1.isHidden();

			String[][] arr = DyFormConsoleIfc._HTML_LIST;

			if (hidden == false) {
				// 每行最大列数存储
				if (columnmap.containsKey(xoffset)) {
					Double yoffset_ = columnmap.get(xoffset);
					if (yoffset > yoffset_) {
						if (arr[28][0].equals(_qc1.getViewtype())) {
							columnmap.put(xoffset, yoffset_);
						} else {
							columnmap.put(xoffset, yoffset);
						}
					}
				} else {
					columnmap.put(xoffset, yoffset);
				}
			}
		}
		return columnmap;
	}

	/**
	 * 根据ID获取DyFormColumn对象
	 * 
	 * @param dyFormColumns
	 * @return
	 */
	protected static Map<String, DyFormColumn> getDyFormColumnById(
			DyFormColumn[] dyFormColumns) {
		Map<String, DyFormColumn> columnmapx = new HashMap<String, DyFormColumn>();
		for (int i = 0; i < dyFormColumns.length; i++) {
			DyFormColumn _qc1 = dyFormColumns[i];
			String columnid = _qc1.getColumnid();
			columnmapx.put(columnid, _qc1);
		}
		return columnmapx;
	}

	/**
	 * 功能: 把文本编码为Html代码,由于函数返回
	 * 
	 * @param s
	 * @return
	 */
	protected static String htmEncode(String s) {
		if (StringUtils.isNotEmpty(s)) {
			// System.out.println("大文本转换前:" + s);
			s = s.replaceAll("<br>", "\n");
			s = s.replaceAll("<BR>", "\n");
			// System.out.println("大文本转换后:" + s);
		}
		return s;
	}

	protected static String htmEncode2(String s) {
		if (StringUtils.isNotEmpty(s)) {
			// System.out.println("多彩文档转换前:" + s);
			s = StringEscapeUtils.unescapeHtml(s);
			// System.out.println("多彩文档转换后:" + s);
		}
		return s;
	}

	protected static Comparator getFormComparator() {
		return new Comparator() {
			public int compare(Object o1, Object o2) {
				DyFormColumn col0 = (DyFormColumn) o1;
				DyFormColumn col1 = (DyFormColumn) o2;

				Double x0 = col0.getXoffset();
				Double x1 = col1.getXoffset();

				Double y0 = col0.getYoffset();
				Double y1 = col1.getYoffset();

				// 首先比较行，如果行相同，则比较列
				int flag = y0.compareTo(y1);
				if (flag == 0) {
					return x0.compareTo(x1);
				} else {
					return flag;
				}
			}
		};
	}

	/**
	 * 获取文档段落
	 * 
	 * @param readonly
	 *            只读或可写
	 * @param data
	 * @param _formx
	 * @param columnmap
	 * @param columnmapx
	 * @return
	 * @throws Exception
	 */
	protected static String buildTr(String trid, String formcode,
			boolean readonly, DyFormData data, DyFormColumn[] _formx,
			Map<String, DyFormColumn> columnmap,
			Map<String, DyFormColumn> columnmapx, boolean isedit,
			String userinfo, String parameter) throws Exception {
		data = data == null ? new DyFormData() : data;
		StringBuffer td_ = new StringBuffer();

		if (isedit) {
			String hiddeninput = DyFormComp
					.getHiddenInput("formcode", formcode);
			td_.append(DyFormComp.getTd("", hiddeninput
					+ DyFormComp.getCheckbox(DyFormComp._CHECK_NAME, "", "",
							"", false), "width:1%;" + TableTdStyle2, "", ""));//
		}

		for (int i = 0; i < _formx.length; i++) {
			String _col = _formx[i].getColumnid();

			if (_formx[i].isHidden() == false) {
				String value = BeanUtils.getProperty(data, _col);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(_col);
				Double wpercent = column.getWpercent();

				if (isedit == false) {
					td_.append(DyFormComp.getTd("", routeAppointValue(column
							.getViewtype(), "" + value + "", column
							.getValuelist()), TableTdStyle, TABLE_TD_CONTENT,
							""));
				} else {
					boolean isSelect_ = checkSelect_(column.getViewtype());
					String style = "width:98%;";
					if (isSelect_) {
						style = "width:80%;";
					}

					String comp = routeAppointComp(column.getViewtype(), column
							.getColumnid(), "" + value, style, "", column
							.isReadonly(), column.getValuelist(), "", userinfo,
							parameter, column.getDefaultValue());

					td_.append(DyFormComp.getTd("", comp, TableTdStyle
							+ ((wpercent == null || wpercent == 0) ? ""
									: "width:" + column.getWpercent() + "%;"),
							TABLE_TD_CONTENT, ""));
				}
			}
		}

		return DyFormComp.getTr(trid, td_.toString(), "", TABLE_TR_CONTENT, "");
	}

	/**
	 * 获取文档段落 固定
	 * 
	 * @param readonly
	 *            只读或可写
	 * @param data
	 * @param _formx
	 * @param columnmap
	 * @param columnmapx
	 * @return
	 * @throws Exception
	 */
	protected static String buildTrBywidth(String trid, String formcode,
			boolean readonly, DyFormData data, DyFormColumn[] _formx,
			Map<String, DyFormColumn> columnmap,
			Map<String, DyFormColumn> columnmapx, boolean isedit,
			String userinfo, String parameter) throws Exception {
		data = data == null ? new DyFormData() : data;
		StringBuffer td_ = new StringBuffer();

		if (isedit) {
			String hiddeninput = DyFormComp
					.getHiddenInput("formcode", formcode);
			td_.append(DyFormComp.getTd("", hiddeninput
					+ DyFormComp.getCheckbox(DyFormComp._CHECK_NAME, "", "",
							"", false), "width:1%;" + TableTdStyle2, "", ""));//
		}

		for (int i = 0; i < _formx.length; i++) {
			String _col = _formx[i].getColumnid();

			if (_formx[i].isHidden() == false) {
				String value = BeanUtils.getProperty(data, _col);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(_col);
				Double wpercent = column.getWpercent();

				if (isedit == false) {
					td_.append(DyFormComp.getTd("", routeAppointValue(column
							.getViewtype(), "" + value + "", column
							.getValuelist()), TableTdStyle, TABLE_TD_CONTENT,
							""));
				} else {
					boolean isSelect_ = checkSelect_(column.getViewtype());
					// String style = "width:95%;";
					// if (isSelect_) {
					// style = "width:80%;";
					// }
					String style = "width:98%;";
					// if (column.getWidth() <= 0) {
					// style = "width:100px;";
					// }

					String comp = routeAppointComp(column.getViewtype(), column
							.getColumnid(), "" + value, style, "", column
							.isReadonly(), column.getValuelist(), "", userinfo,
							parameter, column.getDefaultValue());

					td_.append(DyFormComp.getTd("", comp, TableTdStyle
							+ ((wpercent == null || wpercent == 0) ? ""
									: "width:" + column.getWpercent() + "%;"),
							TABLE_TD_CONTENT, ""));
				}
			}
		}

		return DyFormComp.getTr(trid, td_.toString(), "", TABLE_TR_CONTENT, "");
	}

	protected static String buildExtDatas(DyFormData data,
			DyFormColumn[] _formx, Map<String, DyFormColumn> columnmap,
			Map<String, DyFormColumn> columnmapx, boolean isedit,
			String userinfo, String parameter) throws Exception {
		data = data == null ? new DyFormData() : data;
		StringBuffer datas = new StringBuffer();
		datas.append("[");
		String split = ",";
		datas.append("'',''");
		for (int i = 0; i < _formx.length; i++) {
			String _col = _formx[i].getColumnid();
			if (_formx[i].isHidden() == false) {

				String value = BeanUtils.getProperty(data, _col);
				value = value == null ? "" : value;
				value = "null".equals(value) ? "" : value;
				DyFormColumn column = columnmapx.get(_col);
				Double wpercent = column.getWpercent();

				if (isedit == false) {
					datas.append(split
							+ "'"
							+ routeAppointValue(column.getViewtype(), ""
									+ value + "'", column.getValuelist()));
					split = ",";
				} else {

					String comp = routeAppointComp(column.getViewtype(), column
							.getColumnid(), "" + value, "width:98%;", "",
							column.isReadonly(), column.getValuelist(), "",
							userinfo, parameter, column.getDefaultValue());
					datas.append(split + "'" + comp + "'");
					split = ",";

				}
			}
		}
		datas.append("]");

		return datas.toString();
	}

	protected static String buildNullDatas(DyFormData data,
			DyFormColumn[] _formx, Map<String, DyFormColumn> columnmap,
			Map<String, DyFormColumn> columnmapx, boolean isedit,
			String userinfo, String parameter) throws Exception {
		data = data == null ? new DyFormData() : data;
		StringBuffer datas = new StringBuffer();
		datas.append("{");
		String split = "";
		for (int i = 0; i < _formx.length; i++) {
			String _col = _formx[i].getColumnid();
			if (_formx[i].isHidden() == false) {

				String value = BeanUtils.getProperty(data, _col);
				value = value == null ? "" : value;
				value = "null".equals(value) ? "" : value;
				DyFormColumn column = columnmapx.get(_col);
				Double wpercent = column.getWpercent();

				if (isedit == false) {
					datas.append(split
							+ "'"
							+ routeAppointValue(column.getViewtype(), ""
									+ value + "'", column.getValuelist()));
					split = ",";
				} else {

					String comp = routeAppointComp(column.getViewtype(), column
							.getColumnid(), "" + value, "width:98%;", "",
							column.isReadonly(), column.getValuelist(), "",
							userinfo, parameter, column.getDefaultValue());
					datas.append(split + "'&nbsp;" + column.getColumname()
							+ "':'" + comp + "'");
					split = ",";

				}
			}
		}
		datas.append("}");

		return datas.toString();
	}

	/**
	 * 新增汇总行
	 * 
	 * @return
	 * @throws Exception
	 */
	protected static String buildExtSummaryDatas(DyFormData data,
			DyFormColumn[] _formx, Map<String, DyFormColumn> columnmap,
			Map<String, DyFormColumn> columnmapx, boolean isedit,
			String userinfo, String parameter) throws Exception {
		data = data == null ? new DyFormData() : data;
		StringBuffer datas = new StringBuffer();
		datas.append("[");
		String split = ",";
		datas.append("'summary','summary'");
		for (int i = 0; i < _formx.length; i++) {
			String _col = _formx[i].getColumnid();
			if (_formx[i].isHidden() == false) {

				String value = BeanUtils.getProperty(data, _col);
				value = value == null ? "" : value;
				value = "null".equals(value) ? "" : value;

				datas.append(split + "'<span id=\"" + _col + "_summary\">"
						+ "</span>'");
				split = ",";

			}
		}
		datas.append("]");

		return datas.toString();
	}

	protected static String buildExtNullField(DyFormData data,
			DyFormColumn[] _formx, Map<String, DyFormColumn> columnmap,
			Map<String, DyFormColumn> columnmapx, boolean isedit,
			String userinfo, String parameter) throws Exception {
		data = data == null ? new DyFormData() : data;
		StringBuffer datas = new StringBuffer();
		String split = "";
		for (int i = 0; i < _formx.length; i++) {
			String _col = _formx[i].getColumnid();

			DyFormColumn _qc1 = _formx[i];

			if (_formx[i].isHidden() == false) {

				String comp = routeAppointComp(_qc1.getViewtype(), _qc1
						.getColumnid(), "", "width:98%;", "",
						_qc1.isReadonly(), _qc1.getValuelist(), "", userinfo,
						parameter, _qc1.getDefaultValue());
				datas.append(split + "'" + _col + "':" + "'" + comp + "'");
				split = ",";
			}

		}

		return datas.toString();
	}

	protected static String buildExtColumns(String formcode, boolean readonly,
			DyFormData data, DyFormColumn[] _formx,
			Map<String, DyFormColumn> columnmap,
			Map<String, DyFormColumn> columnmapx, boolean isedit,
			String userinfo, String parameter) throws Exception {
		data = data == null ? new DyFormData() : data;

		StringBuffer columns = new StringBuffer();
		String split = "";

		for (int i = 0; i < _formx.length; i++) {
			String _col = _formx[i].getColumnid();
			if (_formx[i].isHidden() == false) {

				String value = BeanUtils.getProperty(data, _col);

				value = value == null ? "" : value;

				DyFormColumn column = columnmapx.get(_col);
				String ext = null;
				if (isedit == false) {

					ext = "renderer:( value , cellmeta , record , rowIndex , columnIndex , store ){ return '"
							+ routeAppointValue(column.getViewtype(), ""
									+ value + "", column.getValuelist())
							+ "';}";
				} else {
					String style = "width:98%;";

					String comp = routeAppointComp(column.getViewtype(), column
							.getColumnid(), "" + value, style, "", column
							.isReadonly(), column.getValuelist(), "", userinfo,
							parameter, column.getDefaultValue());
					ext = "renderer:( value , cellmeta , record , rowIndex , columnIndex , store ){ return '"
							+ comp + "';}";

				}

				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(column.getViewtype())) {// 非隐藏
					columns.append(split + "{header: '" + column.getColumname()
							+ "', dataIndex: '" + column.getColumnid()
							+ "', sortable: true"
							+ (ext == null ? "" : ("," + ext)) + "}");
					split = ",";
				}

			}
		}
		return columns.toString();
	}

	public static String buildMainForm(DyForm dyform, boolean isedit,
			String userinfo, String naturalname, String lsh, boolean issub,
			boolean isShowTitle, String parameter, User user) throws Exception {
		String formcode = dyform.getFormcode();
		StringBuffer html = new StringBuffer();

		if (isShowTitle) {
			html.append(DyFormComp.getTr("", DyFormComp.getTd("", "&nbsp;"
					+ dyform.getFormname(), TableTdStyle2 + "width:100%;",
					TABLE_TD_TITLE, "align=\"left\" "), "", TABLE_TR_TITLE,
					"align=\"left\""));// 标题
		}

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// 公单已保存,不需要加载人员信息
			userinfo = "";
		}
		// 排序
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// 存放每行最大列数
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		// 输出文档普通字段内容开始
		DyFormData dydata = new DyFormData();
		// if (StringUtils.isNotEmpty(lsh)) {
		if (issub) {
			dydata.setFormcode(formcode);
			dydata.setFatherlsh(lsh);
			List list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
			if (list.size() > 0) {
				dydata = (DyFormData) list.get(0);
			} else {
				dydata = new DyFormData();
			}
		} else {
			dydata = DyEntry.iv().loadData(formcode, lsh);
		}
		// }
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// 存放每行HTML代码

		StringBuffer eventListenScripts = new StringBuffer();// 事件监听脚本
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), _qc1.getFocusScript(), _qc1
						.getLoseFocusScript(), _qc1.getOnchangeScript()));// 添加事件监听

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// 最大列数
				// int availColumnWidth =
				// calcAvailColumnWidth(yoffset_
				// .intValue());
				int availColumnWidth = 0;
				availColumnWidth = calcAvailColumnWidth(yoffset_.intValue());

				htmlresult = buildTdHtml(htmlresult, availColumnWidth, column,
						columnname, formcode, isedit, "" + value, userinfo,
						xoffset, parameter);
			}// end if
		}// end for

		for (Iterator iterator = htmlresult.keySet().iterator(); iterator
				.hasNext();) {
			Double x_ = (Double) iterator.next();

			String tdstr = DyFormComp.getTd("", htmlresult.get(x_),
					TableTdStyle, FORM_TD, "");
			html.append(DyFormComp.getTr("", tdstr, "", FORM_TR, "") + _N);
		}

		String TableExtPropertiesExt = " border=\"1\" width=\"900px\" style=\"word-break:break-all;margin-top:2px;margin-bottom:2px;\" bgcolor=\"#FFFFDB\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"";

		String html_ = DyFormComp.getTable(formcode, html.toString(),
				TableStyle, TABLE_FORM, 0, TableExtPropertiesExt);
		// 输出文档普通字段内容结束

		String hiddenid = DyFormComp.getHiddenInput("naturalname", naturalname);
		String hiddenunid = DyFormComp.getHiddenInput("unid", lsh);
		String hiddenlsh = DyFormComp.getHiddenInput("lsh", lsh);
		if (issub) {
			hiddenid = "";
			hiddenunid = "";
			hiddenlsh = "";
		}
		String html_2 = eventListenScripts.toString()
				+ DyFormComp.getForm(_FORM_ID, hiddenid + hiddenunid
						+ hiddenlsh + html_) + _N;
		return html_2;
	}

	public static String buildForm(DyForm dyform, boolean isedit,
			String userinfo, String naturalname, String lsh, boolean issub,
			boolean isShowTitle, String parameter, User user) throws Exception {
		String formcode = dyform.getFormcode();
		StringBuffer html = new StringBuffer();

		if (isShowTitle) {
			html.append(DyFormComp.getTr("", DyFormComp.getTd("", "&nbsp;"
					+ dyform.getFormname(), TableTdStyle2 + "width:100%;",
					TABLE_TD_TITLE, "align=\"left\" "), "", TABLE_TR_TITLE,
					"align=\"left\""));// 标题
		}

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// 公单已保存,不需要加载人员信息
			userinfo = "";
		}
		// 排序
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// 存放每行最大列数
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		// 输出文档普通字段内容开始
		DyFormData dydata = new DyFormData();
		// if (StringUtils.isNotEmpty(lsh)) {
		if (issub) {
			dydata.setFormcode(formcode);
			dydata.setFatherlsh(lsh);
			List list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
			if (list.size() > 0) {
				dydata = (DyFormData) list.get(0);
			} else {
				dydata = new DyFormData();
			}
		} else {
			dydata = DyEntry.iv().loadData(formcode, lsh);
		}
		// }
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// 存放每行HTML代码

		StringBuffer eventListenScripts = new StringBuffer();// 事件监听脚本
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), _qc1.getFocusScript(), _qc1
						.getLoseFocusScript(), _qc1.getOnchangeScript()));// 添加事件监听

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// 最大列数
				// int availColumnWidth =
				// calcAvailColumnWidth(yoffset_
				// .intValue());
				int availColumnWidth = 0;
				availColumnWidth = calcAvailColumnWidth(yoffset_.intValue());

				htmlresult = buildTdHtml(htmlresult, availColumnWidth, column,
						columnname, formcode, isedit, "" + value, userinfo,
						xoffset, parameter);
			}// end if
		}// end for

		for (Iterator iterator = htmlresult.keySet().iterator(); iterator
				.hasNext();) {
			Double x_ = (Double) iterator.next();

			String tdstr = DyFormComp.getTd("", htmlresult.get(x_),
					TableTdStyle, FORM_TD, "");
			html.append(DyFormComp.getTr("", tdstr, "", FORM_TR, "") + _N);
		}

		String TableExtPropertiesExt = " border=\"1\" width=\"900px\" style=\"word-break:break-all;margin-top:2px;margin-bottom:2px;\" bgcolor=\"#D2CF98\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"";
		String html_ = DyFormComp.getTable(formcode, html.toString(),
				TableStyle, TABLE_FORM, 0, TableExtPropertiesExt);
		// 输出文档普通字段内容结束

		String html_2 = eventListenScripts.toString()
				+ DyFormComp.getForm(_FORM_ID, html_) + _N;
		return html_2;
	}

	public static String buildFormMode6(DyForm dyform, boolean isedit,
			String userinfo, String naturalname, String lsh, boolean issub,
			boolean isShowTitle, String parameter, User user) throws Exception {
		String formcode = dyform.getFormcode();
		StringBuffer html = new StringBuffer();

		if (isShowTitle) {
			html.append(DyFormComp.getTr("", DyFormComp.getTd("", "&nbsp;"
					+ dyform.getFormname(), TableTdStyle2 + "width:100%;",
					TABLE_TD_TITLE, "align=\"left\" "), "", TABLE_TR_TITLE,
					"align=\"left\""));// 标题
		}

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// 公单已保存,不需要加载人员信息
			userinfo = "";
		}

		// 排序
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// 存放每行最大列数
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		// 输出文档普通字段内容开始
		DyFormData dydata = new DyFormData();
		// if (StringUtils.isNotEmpty(lsh)) {
		if (issub) {
			dydata.setFormcode(formcode);
			dydata.setFatherlsh(lsh);
			List list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
			if (list.size() > 0) {
				dydata = (DyFormData) list.get(0);
			} else {
				dydata = new DyFormData();
			}
		} else {
			dydata = DyEntry.iv().loadData(formcode, lsh);
		}
		// }
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// 存放每行HTML代码

		StringBuffer eventListenScripts = new StringBuffer();// 事件监听脚本
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();

			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), _qc1.getFocusScript(), _qc1
						.getLoseFocusScript(), _qc1.getOnchangeScript()));// 添加事件监听

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// 最大列数
				// int availColumnWidth =
				// calcAvailColumnWidth(yoffset_
				// .intValue());
				int availColumnWidth = 300;

				htmlresult = buildTdHtml(htmlresult, availColumnWidth, column,
						columnname, formcode, isedit, "" + value, userinfo,
						xoffset, parameter);
			}// end if
		}// end for

		for (Iterator iterator = htmlresult.keySet().iterator(); iterator
				.hasNext();) {
			Double x_ = (Double) iterator.next();

			String tdstr = DyFormComp.getTd("", htmlresult.get(x_),
					TableTdStyle, FORM_TD, "");
			html.append(DyFormComp.getTr("", tdstr, "", FORM_TR, "") + _N);
		}

		String html_ = DyFormComp.getTable(formcode, html.toString(),
				TableStyle, TABLE_FORM, 0, TableExtProperties);
		// 输出文档普通字段内容结束

		String html_2 = eventListenScripts.toString()
				+ DyFormComp.getForm(_FORM_ID, html_) + _N;
		return html_2;
	}

	protected static Map<Double, String> buildTdHtml(
			Map<Double, String> htmlresult, int availColumnWidth,
			DyFormColumn column, String columnname, String formcode,
			boolean isedit, String value, String userinfo, double xoffset,
			String parameter) {
		return buildTdHtml(htmlresult, availColumnWidth, column, columnname,
				formcode, isedit, value, userinfo, xoffset, parameter, true);
	}

	protected static Map<Double, String> buildTdHtml(
			Map<Double, String> htmlresult, int availColumnWidth,
			DyFormColumn column, String columnname, String formcode,
			boolean isedit, String value, String userinfo, double xoffset,
			String parameter, boolean isMusk_) {
		int columnsize = (columnname.length() * 12) + 12;
		String html_ = "<div class=\"" + FORM_FIELD_CONTENT
				+ "\" style=\"width:" + availColumnWidth + "px\">" + _N;
		String _width = (availColumnWidth - columnsize) + "px;";
		String _width_input = (availColumnWidth - columnsize
				- AvailNormalFieldCorrectWidth - (checkSelect_(column
				.getViewtype()) ? AvailExtBtnWidth : 0))
				+ "px;";
		boolean isMultiDoc = checkMultiDoc(column.getViewtype());
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		String hiddenstyle = "";
		if (arr[28][0].equals(column.getViewtype())) {// 隐藏
			hiddenstyle = "display:none;";
		}
		if (isMultiDoc) {
			html_ += "<div class=\""
					+ FORM_FIELD_CAPTION2
					+ "\" align=\"left\" style=\"width:"
					+ availColumnWidth
					+ "px;"
					+ hiddenstyle
					+ "\"  title=\""
					+ columnname
					+ "\">"
					+ "&nbsp;"
					+ columnname
					+ ((column.isMusk_() && isMusk_) == true ? "<span style=\"color:red\">*</span>"
							: "") + "</div>" + _N;
			_width = "98%;";
			_width_input = "98%;";
		} else {
			html_ += "<div class=\""
					+ FORM_FIELD_CAPTION
					+ "\" align=\"left\" title=\""
					+ columnname
					+ "\" style=\"width:"
					+ columnsize
					+ "px;"
					+ hiddenstyle
					+ ""
					+ "\">"
					+ columnname
					+ ((column.isMusk_() && isMusk_) == true ? "<span style=\"color:red\">*</span>"
							: "") + ":</div>" + _N;
		}
		if (!isedit) {
			String _startDiv = "";
			String _endDiv = "";
			String _value = "";
			boolean isMultiDoc2 = checkMultiDoc2(column.getViewtype());

			if (isMultiDoc2) {
				String _value2 = DyFormComp
						.getJqueryFunctionScript("$(\"table#" + formcode
								+ "\").find(\"#textarea" + column.getColumnid()
								+ "\").autogrow();");
				_value = _value2
						+ routeAppointComp(column.getViewtype(), column
								.getColumnid(), "" + value,
								"overflow:hidden;width:" + _width_input, "",
								true, column.getValuelist(), "", userinfo,
								parameter, column.getDefaultValue()) + _N;
			} else {
				_startDiv = "<div class=\"" + FORM_FIELD_INPUT_READ
						+ "\" style=\"width:" + _width + "\" align=\"left\">"
						+ _N;
				_endDiv = "</div>";
				_value = routeAppointValue(column.getViewtype(), "" + value
						+ "", column.getValuelist())
						+ _N;
			}

			html_ += DyFormComp.getTag(_startDiv, _endDiv, _value);
		} else {
			String _startDiv = "";
			String _endDiv = "";

			String _value2 = "";
			if (isMultiDoc) {
				_value2 = DyFormComp.getJqueryFunctionScript("$(\"table#"
						+ formcode + "\").find(\"#textarea"
						+ column.getColumnid() + "\").autogrow();");
			}

			String _value = _value2
					+ routeAppointComp(column.getViewtype(), column
							.getColumnid(), "" + value,
							"width:" + _width_input, "", column.isReadonly(),
							column.getValuelist(), "", userinfo, parameter,
							column.getDefaultValue()) + _N;
			if (!isMultiDoc) {
				_startDiv = "<div class=\"" + FORM_FIELD_INPUT
						+ "\" style=\"width:" + _width + "\" align=\"left\">"
						+ _N;
				_endDiv = "</div>";
			}

			html_ += DyFormComp.getTag(_startDiv, _endDiv, _value);
		}

		html_ += "</div>" + _N;
		if (htmlresult.containsKey(xoffset)) {
			htmlresult.put(xoffset, htmlresult.get(xoffset) + html_);
		} else {
			htmlresult.put(xoffset, html_);
		}
		return htmlresult;
	}

	/**
	 * 建立链接表单
	 * 
	 * @param subdyform
	 * @param fatherlsh
	 * @param isedit
	 * @param userinfo
	 * @param submode
	 * @return
	 * @throws Exception
	 */
	public static String buildLinkForm(DyForm subdyform, String fatherlsh,
			boolean isedit, String userinfo, String submode, String workcode,
			String naturalname, String parameter, User user) throws Exception {
		StringBuffer html = new StringBuffer();
		DyForm dyform = subdyform;
		String formcode = dyform.getFormcode();

		if (StringUtils.isEmpty(workcode))
			workcode = "";

		String title = dyform.getFormname();
		String hrefurl = edit_frame_ext + "&formcode=" + formcode + "&submode="
				+ submode + "&workcode=" + workcode + "&naturalname="
				+ naturalname + "&url=" + parameter;
		String clickscript = " onclick=\"javascript:var lsh=$('#lsh').val();var url='"
				+ hrefurl
				+ "'+'&fatherlsh='+lsh;if(lsh=='') alert('表单未保存,不能打开链接表单!'); else window.open(url);\" ";
		String btntitle = "<B>编辑</B>";
		if (!isedit) {
			btntitle = "<B>查看</B>";
			clickscript = " onclick=\"javascript:var lsh=$('#lsh').val();var url='"
					+ hrefurl
					+ "'+'&query=look&fatherlsh='+lsh;if(lsh=='') alert('表单未保存,不能打开链接表单!'); else window.open(url);\" ";
		}

		String link = "&nbsp;"
				+ title
				+ "&nbsp;&nbsp;&nbsp;"
				+ DyFormComp.getHref(btntitle, title, "javascript:void(0);",
						clickscript + " style=\"color:green;\" ", "");
		html.append(DyFormComp.getTr("", DyFormComp.getTd("", link,
				TableTdStyle2 + "width:100%;", TABLE_TD_TITLE,
				"align=\"left\" "), "", TABLE_TR_TITLE, "align=\"left\""));// 标题

		String html_ = DyFormComp.getTable(formcode, html.toString(), dyform
				.getStyleinfo_(), "", 0, TableExtProperties);

		return html_.toString();
	}

	/**
	 * 获取验证脚本
	 * 
	 * @param dyForm
	 * @return
	 */

	public static String buildValidateScript(DyForm dyForm) {
		DyFormColumn[] allcolumns = dyForm.getAllColumn_();
		DyForm[] subdyforms = dyForm.getSubform_();
		StringBuffer script = new StringBuffer();

		script.append("var str = '保存失败!出错提示如下:<br />';" + _N);
		script.append("var i=1;" + _N);
		script.append("var blank = '&nbsp;&nbsp;';" + _N);
		for (int i = 0; i < allcolumns.length; i++) {
			DyFormColumn _c1 = allcolumns[i];

			String columnid = _c1.getColumnid();
			String columnname = _c1.getColumname();
			boolean hidden = _c1.isHidden();// 是否隐蔽
			boolean ismusk = _c1.isMusk_();
			boolean isreadonly = _c1.isReadonly();
			String regExp = _c1.getRegExpression();
			if (hidden == false && isreadonly == false) {
				String uid = "$" + uuid();

				script.append("var " + uid + " = $('table#"
						+ dyForm.getFormcode() + " #" + columnid + "');" + _N);
				script.append("if (" + uid + " && " + uid + ".length ){" + _N);

				if (ismusk) {
					script.append("if (" + uid + ".val()==''){" + _N);
					script.append(" str+= blank+ i+ '、" + columnname
							+ "为空。<br />';" + _N);
					script.append(" i++;" + _N);
					script.append("}" + _N);

					if (StringUtils.isNotEmpty(regExp)
							&& StringUtils.isNotBlank(regExp)) {
						script.append(" var reg" + uid + " = " + regExp + ";"
								+ _N);

						script.append("  if(!reg" + uid + ".test(" + uid
								+ ".val())){" + _N);

						script.append(" str+= blank+ i+ '、" + columnname
								+ "不符合格式。<br />';" + _N);
						script.append(" i++;" + _N);
						script.append("}" + _N);
					}
				}

				script.append("}" + _N);
			}
		}
		// script.append("if ($('#belongx_').val()==''){" + _N);
		// script.append(" str+= blank+ i+ '、" + "申请简述" + "为空。<br />';" + _N);
		// script.append(" i++;" + _N);
		// script.append("}" + _N);

		if (subdyforms != null) {
			for (int z = 0; z < subdyforms.length; z++) {
				DyForm subdyform = subdyforms[z];
				DyFormColumn[] suballcolumns = subdyform.getAllColumn_();

				String subformcode = subdyform.getFormcode();
				for (int i = 0; i < suballcolumns.length; i++) {
					DyFormColumn _c1 = suballcolumns[i];

					String columnid = _c1.getColumnid();
					String columnname = _c1.getColumname();
					boolean hidden = _c1.isHidden();// 是否隐蔽
					boolean ismusk = _c1.isMusk_();
					boolean isreadonly = _c1.isReadonly();
					String regExp = _c1.getRegExpression();
					if (hidden == false && isreadonly == false) {

						script.append("$('table#" + subformcode + "').find('#"
								+ columnid + "').each(function(){");

						// start 检查
						String uid = "$" + uuid();
						if (ismusk) {
							script.append("if ($(this).val()==''){" + _N);
							script.append(" str+= blank+ i+ '、" + columnname
									+ "为空。<br />';" + _N);
							script.append(" i++;" + _N);
							script.append("}" + _N);

							if (StringUtils.isNotEmpty(regExp)
									& StringUtils.isNotBlank(regExp)) {
								script.append("var reg" + uid + " = " + regExp
										+ ";" + _N);
								script.append("  if(!reg" + uid
										+ ".test($(this).val())){" + _N);

								script.append(" str+= blank+ i+ '、"
										+ columnname + "不符合格式。<br />';" + _N);
								script.append(" i++;" + _N);
								script.append("}" + _N);
							}
						}
						// end 检查

						script.append("});");
					}
				}

			}
		}
		script.append("if(i > 1){ ");
		script.append(" Ext.MessageBox.alert('错误提示',str);");
		script.append("	return false;");
		script.append("}");

		script.append("return true;");
		return script.toString();
	}

	/**
	 * 生成字段列表
	 * 
	 * @param dyform
	 * @param userinfo
	 * @param naturalname
	 * @param lsh
	 * @param issub
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static String buildQueryForm1(DyForm dyform, String userinfo,
			String naturalname, String lsh, boolean issub, String parameter)
			throws Exception {

		String formcode = dyform.getFormcode();
		StringBuffer html = new StringBuffer();

		DyFormColumn[] _formx = new DyFormColumn[0];

		try {
			List<DyFormColumn> list = DyEntry.iv().queryColumnX(
					dyform.getFormcode(), "2");
			_formx = (DyFormColumn[]) list
					.toArray(new DyFormColumn[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 展示表单字段-针对表单中的相关字段
		// DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// 公单已保存,不需要加载人员信息
			userinfo = "";
		}
		// 排序
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// 存放每行最大列数
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		// 输出文档普通字段内容开始
		DyFormData dydata = new DyFormData();
		// if (StringUtils.isNotEmpty(lsh)) {
		// if (issub) {
		// dydata.setFormcode(formcode);
		// dydata.setFatherlsh(lsh);
		// List list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// if (list.size() > 0) {
		// dydata = (DyFormData) list.get(0);
		// } else {
		// dydata = new DyFormData();
		// }
		// } else {
		// dydata = DyEntry.iv().loadData(formcode, lsh);
		// }
		// }
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// 存放每行HTML代码

		StringBuffer eventListenScripts = new StringBuffer();// 事件监听脚本
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), "", "", ""));// 添加事件监听

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// 最大列数
				// int availColumnWidth =
				// calcAvailColumnWidth(yoffset_
				// .intValue());
				int availColumnWidth = 0;
				availColumnWidth = calcAvailColumnWidth(yoffset_.intValue());

				htmlresult = buildTdHtml(htmlresult, availColumnWidth, column,
						columnname, formcode, true, "" + value, "", xoffset,
						"", false);
			}// end if
		}// end for

		for (Iterator iterator = htmlresult.keySet().iterator(); iterator
				.hasNext();) {
			Double x_ = (Double) iterator.next();

			String tdstr = DyFormComp.getTd("", htmlresult.get(x_),
					TableTdStyle, FORM_TD, "");
			html.append(DyFormComp.getTr("", tdstr, "", FORM_TR, "") + _N);
		}

		String TableExtPropertiesExt = " border=\"1\" width=\"900px\" style=\"word-break:break-all;margin-top:2px;margin-bottom:2px;\" bgcolor=\"white\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"";
		String html_ = DyFormComp.getTable(formcode, html.toString(),
				TableStyle, TABLE_FORM, 0, TableExtPropertiesExt);
		// 输出文档普通字段内容结束

		String hiddenid = DyFormComp.getHiddenInput("naturalname", naturalname);
		String hiddenunid = DyFormComp.getHiddenInput("unid", lsh);
		String hiddenlsh = DyFormComp.getHiddenInput("lsh", lsh);
		if (issub) {
			hiddenid = "";
			hiddenunid = "";
			hiddenlsh = "";
		}
		String html_2 = eventListenScripts.toString()
				+ DyFormComp.getForm(_FORM_ID, hiddenid + hiddenunid
						+ hiddenlsh + html_) + _N;
		return html_2;
	}

	/**
	 * 生成字段列表
	 * 
	 * @param dyform
	 * @param userinfo
	 * @param naturalname
	 * @param lsh
	 * @param issub
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static String buildQueryForm0(DyForm dyform, String userinfo,
			String naturalname, String lsh, boolean issub, String parameter)
			throws Exception {

		String formcode = dyform.getFormcode();
		StringBuffer html = new StringBuffer();

		DyFormColumn[] _formx = new DyFormColumn[0];

		try {
			List<DyFormColumn> list = DyEntry.iv().queryColumnQ(
					dyform.getFormcode());
			_formx = (DyFormColumn[]) list
					.toArray(new DyFormColumn[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 展示表单字段-针对表单中的相关字段
		// DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// 公单已保存,不需要加载人员信息
			userinfo = "";
		}
		// 排序
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// 存放每行最大列数
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		// 输出文档普通字段内容开始
		DyFormData dydata = new DyFormData();
		// if (StringUtils.isNotEmpty(lsh)) {
		// if (issub) {
		// dydata.setFormcode(formcode);
		// dydata.setFatherlsh(lsh);
		// List list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// if (list.size() > 0) {
		// dydata = (DyFormData) list.get(0);
		// } else {
		// dydata = new DyFormData();
		// }
		// } else {
		// dydata = DyEntry.iv().loadData(formcode, lsh);
		// }
		// }
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// 存放每行HTML代码

		StringBuffer eventListenScripts = new StringBuffer();// 事件监听脚本
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), "", "", ""));// 添加事件监听

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// 最大列数
				// int availColumnWidth =
				// calcAvailColumnWidth(yoffset_
				// .intValue());
				int availColumnWidth = 0;
				availColumnWidth = calcAvailColumnWidthN(yoffset_.intValue());

				htmlresult = buildTdHtml(htmlresult, availColumnWidth, column,
						columnname, formcode, true, "" + value, "", xoffset,
						"", false);
			}// end if
		}// end for

		for (Iterator iterator = htmlresult.keySet().iterator(); iterator
				.hasNext();) {
			Double x_ = (Double) iterator.next();

			String tdstr = DyFormComp.getTd("", htmlresult.get(x_),
					TableTdStyle, FORM_TD, "");
			html.append(DyFormComp.getTr("", tdstr, "", FORM_TR, "") + _N);
		}

		String TableExtPropertiesExt = " border=\"1\" width=\"700px\" style=\"word-break:break-all;margin-top:2px;margin-bottom:2px;\" bgcolor=\"#FFFFDB\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"";
		String html_ = DyFormComp.getTable(formcode, html.toString(),
				TableStyle, TABLE_FORM, 0, TableExtPropertiesExt);
		// 输出文档普通字段内容结束

		String hiddenid = DyFormComp.getHiddenInput("naturalname", naturalname);
		String hiddenunid = DyFormComp.getHiddenInput("unid", lsh);
		String hiddenlsh = DyFormComp.getHiddenInput("lsh", lsh);
		if (issub) {
			hiddenid = "";
			hiddenunid = "";
			hiddenlsh = "";
		}
		String html_2 = eventListenScripts.toString()
				+ DyFormComp.getForm(_FORM_ID, hiddenid + hiddenunid
						+ hiddenlsh + html_) + _N;
		return html_2;
	}

	/**
	 * 获取查询条件栏
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildQueryColumn(DyFormColumn queryColumn[]) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对管理列表中的查询字段
		Arrays.sort(queryColumn, getFormComparator());// 排序
		for (int i = 0; i < queryColumn.length; i++) {
			DyFormColumn _qc1 = queryColumn[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();
			// 字段的html类型
			String htmltype = _qc1.getViewtype();
			// 字段默认值
			String valuelist = _qc1.getValuelist();

			if (columnid.contains("column")) {
				String input = DyFormComp.getTag("<span>&nbsp;" + columnname
						+ ":", "</span>", routeAppointComp2(htmltype, columnid,
						"", "", "", false, valuelist, "", "", "", _qc1
								.getDefaultValue()));
				html.append(input);
			}
		}
		html.append(DyFormComp.getButton("", " 查 询 ", "", "", false,
				" onclick='refresh();' "));
		// html.append(DyFormComp.getButton("", "详细查询", "", "", false,
		// " onclick='alert(\"详细查询\")' "));
		return html.toString();
	}

	/**
	 * 获取查询条件
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildQueryCondition(DyFormColumn queryColumn[]) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对管理列表中的查询字段
		Arrays.sort(queryColumn, getFormComparator());// 排序
		html.append("condition: '" + "" + "'");
		for (int i = 0; i < queryColumn.length; i++) {
			DyFormColumn _qc1 = queryColumn[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();

			if (columnid.contains("column")) {
				if (_qc1.isHidden() == false)
					html.append("," + columnid + ":" + "(Ext.get('" + columnid
							+ "')==null)?'':Ext.get('" + columnid
							+ "').getValue()");
			}
		}

		return html.toString();
	}

	public static Map buildSubForm_(DyForm subdyform, String fatherlsh,
			boolean isedit, String userinfo, String parameter, User user)
			throws Exception {
		StringBuffer htmlall = new StringBuffer();
		StringBuffer eventListenScripts = new StringBuffer();// 事件监听脚本
		StringBuffer html = new StringBuffer();
		StringBuffer theadhtml = new StringBuffer();
		DyForm dyform = subdyform;
		String formcode = dyform.getFormcode();

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn _formx[] = dyform.getAllColumn_();
		Arrays.sort(_formx, getFormComparator());// 排序
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		StringBuffer td = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();
		int colspan = 1;

		if (isedit == true) {
			td.append(DyFormComp.getTh(formcode + DyFormComp._CHECK_NAME,
					DyFormComp
							.getCheckbox(formcode + "_btn", "", "", "", false),
					"width:1%;" + TableTdStyle2, "", ""));// 全选/清除按钮
		}

		htmlall.append(DyFormComp.getClickEventScript("$(\"#" + formcode
				+ "_btn\")", DyFormComp.getSelectOrUnselectAll(formcode)));

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			// 是否隐蔽
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				columnmap.put(columnid, _qc1);
				String musktip = _qc1.isMusk_() == true ? "<span style=\"color:red\">*</span>"
						: "";

				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// 非隐藏
					Double width = _qc1.getWidth();
					if (width == null || width == 0) {
						td.append(DyFormComp.getTh("", "&nbsp;" + columnname
								+ musktip, TableTdStyle2, TABLE_TD_HEADER, ""));
					} else {
						td.append(DyFormComp.getTh("", "&nbsp;" + columnname
								+ musktip, TableTdStyle2 + "width:" + width
								+ "px;", TABLE_TD_HEADER, ""));
					}
					++colspan;
				}
				eventListenScripts
						.append(DyFormComp.getLiveEventScript("$('table#"
								+ formcode + "').find('#" + columnid + "')",
								_qc1.getInitScript(), _qc1.getFocusScript(),
								_qc1.getLoseFocusScript(), _qc1
										.getOnchangeScript()));// 添加事件监听

			}
		}

		// html.append(DyFormComp.getTr("", DyFormComp.getTd("", "&nbsp;"
		// + dyform.getFormname(), TableTdStyle2, TABLE_TD_TITLE,
		// "align=\"left\" colspan=\"" + colspan + "\""), "",
		// TABLE_TR_TITLE, "align=\"left\""));// 标题
		theadhtml.append(DyFormComp.getTr("", td.toString(), "",
				TABLE_TR_HEADER, ""));// 表头

		// 数据
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh(fatherlsh);
		List list = new ArrayList();
		// if (StringUtils.isNotEmpty(fatherlsh)) {
		list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// }

		if (list.size() > 0) {// 有记录
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();
				html.append(buildTr(uuid(), formcode, false, data, _formx,
						columnmap, columnmapx, isedit, userinfo, parameter));
			}
		} else {// 无记录
			// for (int i = 0; i < 7; i++) {
			html.append(buildTr(uuid(), formcode, false, null, _formx,
					columnmap, columnmapx, isedit, userinfo, parameter));
			// }

		}

		String nulltr = buildTr(uuid() + "_TR_UUID_", formcode, false, null,
				_formx, columnmap, columnmapx, isedit, userinfo, parameter);
		String onclickAddFunctionname = "$ADD_" + formcode;
		String onclickRemoveFunctionname = "$REMOVE_" + formcode;

		String datas_config = buildNullDatas(new DyFormData(), _formx,
				columnmap, columnmapx, isedit, userinfo, parameter);

		String addrowscript = "onclick='" + onclickAddFunctionname + "();'";
		String removerowscript = "onclick='" + onclickRemoveFunctionname
				+ "();'";

		StringBuffer btnstr = new StringBuffer();
		btnstr.append(DyFormComp.getButton("", "新增行", "", "btn", false,
				addrowscript)
				+ _N);
		btnstr.append(DyFormComp.getButton("", "删除行", "", "btn", false,
				removerowscript)
				+ _N);
		nulltr = nulltr.replaceAll("\"", "\'");
		String htmlcacheid = uuid();
		String xhtml = "$('#htmlcache"
				+ htmlcacheid
				+ "').html(\""
				+ nulltr
				+ "\");var nulltr=$('#htmlcache"
				+ htmlcacheid
				+ "').html();nulltr=nulltr.replace('_TR_UUID_',makeUUID());nulltr=nulltr.replace('_BTN_UUID',makeUUID());";
		btnstr.append("<script> $('body').append('<div id=\"htmlcache"
				+ htmlcacheid + "\" style=\"display:none;\"></div>');function "
				+ onclickAddFunctionname + "(){var datas_config = "
				+ datas_config + ";$('#" + formcode
				+ "').jqGrid('addRowData',makeUUID(), datas_config);}");
		btnstr.append("function " + onclickRemoveFunctionname + "(){");
		btnstr.append(DyFormComp.deleteRow_(formcode, onclickAddFunctionname));
		btnstr.append("}");
		btnstr.append("</script>");

		String TableExtProperties_ = " style=\"width:900px;word-break:break-all;margin-top:2px;margin-bottom:2px;\" bgcolor=\"white\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"";

		String html_ = "<div style='width:900px;'>"
				+ DyFormComp.getTable(formcode, "<thead>"
						+ theadhtml.toString() + "</thead>" + "<tbody>"
						+ html.toString() + "</tbody>", dyform.getStyleinfo_(),
						"", 0, TableExtProperties_) + "</div>";
		String html_btn = "";
		if (isedit) {
			String btnstr_ = DyFormComp.getTr("", DyFormComp.getTd("", btnstr
					.toString(), TableTdStyle2, TABLE_TD_TITLE,
					"align=\"left\" colspan=\"" + colspan + "\""), "",
					TABLE_TR_TITLE, "align=\"left\"");// 按钮菜单

			String btnstr_null = DyFormComp.getTr("", DyFormComp.getTd("",
					"&nbsp;", "", "", "align=\"left\" colspan=\"" + colspan
							+ "\""), "", "", "align=\"left\"");// null

			html_btn = DyFormComp.getTable(formcode + "btn", btnstr_
					+ btnstr_null, dyform.getStyleinfo_(), "", 0,
					TableExtProperties);
		}

		htmlall.append(eventListenScripts.toString()
		// + "<div style=\"overflow-x:scroll; overflow-y:hidden;
				// width:900px;\">"
				+ html_ + html_btn
		// + "</div>"
				);

		StringBuffer tablejs = new StringBuffer();

		// tablejs.append("$('table#"+formcode+"').ingrid({ \n");
		// tablejs.append(" height: 350, \n");
		// tablejs.append(" resizableCols: true, \n");
		// tablejs.append(" paging: false, \n");
		// tablejs.append(" sorting: false \n");
		// tablejs.append("}); \n");

		// tablejs.append("$('table#"+formcode+"').chromatable({ \n");
		// tablejs.append(" width: '100%', \n");
		// tablejs.append(" scrolling: 'yes' \n");
		// tablejs.append("}); \n");

		// tablejs.append("$('table#"+formcode+"').dataTable({ \n");
		// tablejs.append(" 'sScrollY': '500px', \n");
		// tablejs.append(" 'sScrollX': '100%', \n");
		// tablejs.append(" 'sScrollXInner': '150%', \n");
		// tablejs.append(" 'bScrollCollapse': true, \n");
		// tablejs.append(" 'bPaginate': false, \n");
		// tablejs.append(" 'bSort': false, \n");
		//		
		//		
		// tablejs.append(" 'bLengthChange': false, \n");
		// tablejs.append(" 'bFilter': false, \n");
		// tablejs.append(" 'bInfo': false, \n");
		// tablejs.append(" 'bAutoWidth': false, \n");

		// tablejs.append(" 'bJQueryUI': true, \n");

		// tablejs.append(" 'oLanguage': { \n");
		// tablejs.append(" 'sProcessing': '加载中...', \n");
		// tablejs.append(" /*'sLengthMenu': ' 显示_MENU_条记录',*/ \n");
		// tablejs.append(" 'sZeroRecords': '没有找到记录!', \n");
		// tablejs.append(" 'sInfo': '从_START_至_END_条记录 共_TOTAL_条记录', \n");
		// tablejs.append(" 'sInfoEmpty': '从0条至0条记录 共0条记录', \n");
		// tablejs.append(" 'sInfoFiltered': '(从_MAX_条记录过滤)', \n");
		// tablejs.append(" 'sInfoPostFix': '', \n");
		// tablejs.append(" 'sSearch': '查找:', \n");
		// tablejs.append(" 'sUrl': '', \n");
		// tablejs.append(" 'oPaginate': { \n");
		// tablejs.append(" 'sFirst': '首页', \n");
		// tablejs.append(" 'sPrevious': '上一页', \n");
		// tablejs.append(" 'sNext': '下一页', \n");
		// tablejs.append(" 'sLast': '末页' \n");
		// tablejs.append(" } \n");
		// tablejs.append(" }, \n");
		// tablejs.append(" /*'sPaginationType': 'full_numbers',*/ \n");
		// tablejs.append(" 'sDom':\"<'dataTables_length'>frtip\" \n");
		// tablejs.append(" }); \n");

		tablejs.append("tableToGrid('table#" + formcode + "');$('table#"
				+ formcode + "').setGridWidth('99%'); \n");

		tablejs
				.append("jQuery('table#"
						+ formcode
						+ "').jqGrid('gridResize',{minWidth:350,maxWidth:2000,minHeight:80, maxHeight:1000});");

		Map mapx = new HashMap();
		mapx.put("html", DyFormComp
				.getJqueryFunctionScript_(tablejs.toString())
				+ htmlall.toString());
		mapx.put("count", list.size());
		mapx.put("js", "");

		return mapx;
	}

	public static String buildSubForm(DyForm subdyform, String fatherlsh,
			boolean isedit, String userinfo, String parameter, User user)
			throws Exception {

		StringBuffer htmlall = new StringBuffer();

		StringBuffer eventListenScripts = new StringBuffer();// 事件监听脚本
		StringBuffer html = new StringBuffer();
		DyForm dyform = subdyform;
		String formcode = dyform.getFormcode();

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn _formx[] = dyform.getAllColumn_();
		Arrays.sort(_formx, getFormComparator());// 排序
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		StringBuffer td = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();
		int colspan = 1;

		if (isedit == true) {
			td.append(DyFormComp.getTd(formcode + DyFormComp._CHECK_NAME,
					DyFormComp
							.getCheckbox(formcode + "_btn", "", "", "", false),
					"width:1%;" + TableTdStyle2, "", ""));// 全选/清除按钮
		}

		htmlall.append(DyFormComp.getClickEventScript("$(\"#" + formcode
				+ "_btn\")", DyFormComp.getSelectOrUnselectAll(formcode)));

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			// 是否隐蔽
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				columnmap.put(columnid, _qc1);
				String musktip = _qc1.isMusk_() == true ? "<span style=\"color:red\">*</span>"
						: "";

				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// 非隐藏
					td.append(DyFormComp.getTd("", "&nbsp;" + columnname
							+ musktip, TableTdStyle2, TABLE_TD_HEADER, ""));
					++colspan;
				}

				if (isedit)
					eventListenScripts.append(DyFormComp.getLiveEventScript(
							"$('table#" + formcode + "').find('#" + columnid
									+ "')", _qc1.getInitScript(), _qc1
									.getFocusScript(), _qc1
									.getLoseFocusScript(), _qc1
									.getOnchangeScript()));// 添加事件监听

			}
		}

		html.append(DyFormComp.getTr("", DyFormComp.getTd("", "&nbsp;"
				+ dyform.getFormname(), TableTdStyle2, TABLE_TD_TITLE,
				"align=\"left\" colspan=\"" + colspan + "\""), "",
				TABLE_TR_TITLE, "align=\"left\""));// 标题
		html.append(DyFormComp
				.getTr("", td.toString(), "", TABLE_TR_HEADER, ""));// 表头

		// 数据
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh(fatherlsh);
		List list = new ArrayList();
		// if (StringUtils.isNotEmpty(fatherlsh)) {
		list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// }

		if (list.size() > 0) {// 有记录
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();
				html.append(buildTr(uuid(), formcode, false, data, _formx,
						columnmap, columnmapx, isedit, userinfo, parameter));
			}
		} else {// 无记录
			html.append(buildTr(uuid(), formcode, false, null, _formx,
					columnmap, columnmapx, isedit, userinfo, parameter));
		}

		String nulltr = buildTr(uuid() + "$TR_UUID$", formcode, false, null,
				_formx, columnmap, columnmapx, isedit, userinfo, parameter);
		String onclickAddFunctionname = "$ADD_" + uuid();
		String onclickRemoveFunctionname = "$REMOVE_" + uuid();

		String addrowscript = "onclick='" + onclickAddFunctionname + "();'";
		String removerowscript = "onclick='" + onclickRemoveFunctionname
				+ "();'";

		StringBuffer btnstr = new StringBuffer();
		btnstr.append(DyFormComp.getButton("", "新增行", "", "btn", false,
				addrowscript)
				+ _N);
		btnstr.append(DyFormComp.getButton("", "删除行", "", "btn", false,
				removerowscript)
				+ _N);
		String xhtml = "var nulltr='"
				+ nulltr
				+ "';nulltr=nulltr.replace('$TR_UUID$',makeUUID());nulltr=nulltr.replace('_BTN_UUID',makeUUID());";
		btnstr.append("<script> function " + onclickAddFunctionname + "(){"
				+ xhtml + " $('#" + formcode + "').append(nulltr);} ");
		btnstr.append("function " + onclickRemoveFunctionname + "(){");
		btnstr.append(DyFormComp.deleteRow(formcode, onclickAddFunctionname));
		btnstr.append("}");
		btnstr.append("</script>");

		String html_ = DyFormComp.getTable(formcode, html.toString(), dyform
				.getStyleinfo_(), "", 0, TableExtProperties);
		String html_btn = "";
		if (isedit) {
			String btnstr_ = DyFormComp.getTr("", DyFormComp.getTd("", btnstr
					.toString(), TableTdStyle2, TABLE_TD_TITLE,
					"align=\"left\" colspan=\"" + colspan + "\""), "",
					TABLE_TR_TITLE, "align=\"left\"");// 按钮菜单

			html_btn = DyFormComp.getTable(formcode + "btn", btnstr_, dyform
					.getStyleinfo_(), "", 0, TableExtProperties);
		}

		htmlall.append(eventListenScripts.toString() + html_ + html_btn);
		return htmlall.toString();
	}

	public static Map buildExtSubForm(String id, DyForm subdyform,
			String fatherlsh, boolean isedit, String userinfo,
			String parameter, User user) throws Exception {

		StringBuffer htmlall = new StringBuffer();

		DyForm dyform = subdyform;
		String formcode = dyform.getFormcode();

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn _formx[] = dyform.getAllColumn_();
		Arrays.sort(_formx, getFormComparator());// 排序
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射

		StringBuffer th = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();

		StringBuffer datas = new StringBuffer();
		String fields = buildExtFields2(dyform);
		String columns = buildExtGridColumns(dyform, user.getUserCode());

		StringBuffer eventListenScripts = new StringBuffer();
		boolean issummary = false;
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			// debug
			// if ("column3".equals(columnid)) {
			// _qc1.setSummarytype("sum");
			// }

			// 是否隐蔽
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				columnmap.put(columnid, _qc1);

				String summarychangescript = DyFormComp.getSummaryScript(_qc1
						.getSummarytype(), formcode, columnid);// 汇总脚本

				eventListenScripts.append(DyFormComp
						.getLiveEventScript("$('table#" + formcode
								+ "').find('#" + columnid + "')", (_qc1
								.getInitScript() == null ? "" : _qc1
								.getInitScript()),
								(_qc1.getFocusScript() == null ? "" : _qc1
										.getFocusScript())
										+ summarychangescript, (_qc1
										.getLoseFocusScript() == null ? ""
										: _qc1.getLoseFocusScript())
										+ summarychangescript, (_qc1
										.getOnchangeScript() == null ? ""
										: _qc1.getOnchangeScript())
										+ summarychangescript));// 添加事件监听

				String uuid = DyFormBuildHtml.uuid();
				eventListenScripts.append("<script>function $" + uuid + "(){");
				eventListenScripts.append(summarychangescript);
				eventListenScripts.append("}</script>");
				eventListenScripts.append(DyFormComp
						.getJqueryFunctionScript("setTimeout('$" + uuid
								+ "()',1000);"));

			}
			// 存在汇总
			if (StringUtils.isNotEmpty(_qc1.getSummarytype())) {
				issummary = true;
			}
		}
		htmlall.append(eventListenScripts.toString());

		// 数据
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh(fatherlsh);
		List list = new ArrayList();
		// if (StringUtils.isNotEmpty(fatherlsh)) {
		list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// }
		if (list.size() > 0) {// 有记录
			String split = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();

				datas.append(split
						+ buildExtDatas(data, _formx, columnmap, columnmapx,
								isedit, userinfo, parameter));
				split = ",";
			}
		} else {// 无记录
			String split = "";
			// for (int i = 0; i < 7; i++) {
			datas.append(split
					+ buildExtDatas(null, _formx, columnmap, columnmapx,
							isedit, userinfo, parameter));
			split = ",";
			// }
		}
		if (issummary) {// 汇总行
			datas.append(","
					+ buildExtSummaryDatas(null, _formx, columnmap, columnmapx,
							isedit, userinfo, parameter));
		}
		// datas = new StringBuffer();
		// datas
		// .append("['1','name1','descn1'], ['2','name2','descn2'],
		// ['3','name3','descn3'], ['4','name4','descn4'],
		// ['5','name3','descn5'] ");
		htmlall.append(DyFormComp.getExtGridPanel2(id + "Grid", datas
				.toString(), fields, columns));

		Map map = new HashMap();

		StringBuffer bbarjs = new StringBuffer();
		bbarjs
				.append(",bbar:new Ext.Toolbar([{text : '新增行 ',iconCls:'addIcon',id:'ext_b_addRow',handler:function(){");

		bbarjs.append("    var grid = Ext.getCmp('" + id + "Grid" + "');");
		bbarjs.append("    var currRowIndex = grid.store.getCount();");
		bbarjs.append("    var newRs = new Ext.data.Record({");

		bbarjs.append(buildExtNullField(null, _formx, columnmap, columnmapx,
				isedit, userinfo, parameter));

		bbarjs.append("    });");

		if (issummary) {
			bbarjs.append("    grid.store.insert(currRowIndex-1,newRs);");
		} else {
			bbarjs.append("    grid.store.add(newRs);");
		}

		bbarjs.append("}}");

		bbarjs
				.append(",{text : '删除行 ',iconCls:'deleteIcon',id:'ext_b_deleteRow',handler:function(){ ");

		bbarjs.append("var grid = Ext.getCmp('" + id + "Grid" + "'); ");
		bbarjs.append("var recs = grid.getSelectionModel().getSelections(); ");
		bbarjs.append("var list = []; ");
		bbarjs.append("if (recs.length == 0) { ");
		bbarjs.append("} else { ");
		bbarjs.append("    for (var i = 0; i < recs.length; i++) { ");
		bbarjs.append("        var rec = recs[i];  ");
		bbarjs
				.append("        if (rec.get('lsh')!='summary') grid.store.remove(rec); ");
		bbarjs.append("    } ");
		bbarjs.append("} ");

		bbarjs.append(" }}])");

		map.put("html", htmlall.toString());
		map.put("count", list.size());
		if (isedit) {
			map.put("js", bbarjs.toString());
		} else {
			map.put("js", "");
		}
		return map;
	}

	/**
	 * 建立Ext所需要的字段<BR>
	 * 
	 * {name: 'groupTerminalId'}, {name: 'subjectId'}, {name: 'subjectName'}
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildExtFields(DyForm dyform) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对表单中的相关字段
		// DyFormColumn[] _formx = dyform.getAllColumn_();

		DyFormColumn[] _formx = new DyFormColumn[0];

		try {
			List<DyFormColumn> list = DyEntry.iv().queryColumnX(
					dyform.getFormcode(), "2");
			_formx = (DyFormColumn[]) list
					.toArray(new DyFormColumn[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		html.append("{name: '" + "status" + "'}");
		html.append("," + "{name: '" + "run" + "'}");
		html.append("," + "{name: '" + "runtimeid" + "'}");
		html.append("," + "{name: '" + "lsh" + "'}");
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			html.append("," + "{name: '" + columnid + "'}");
		}
		return html.toString();
	}

	public static String buildExtFieldsX(DyFormColumn[] formx) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对表单中的相关字段

		html.append("{name: '" + "status" + "'}");
		html.append("," + "{name: '" + "run" + "'}");
		html.append("," + "{name: '" + "runtimeid" + "'}");
		html.append("," + "{name: '" + "lsh" + "'}");
		html.append("," + "{name: '" + "lshs" + "'}");
		if (formx != null) {
			for (int i = 0; i < formx.length; i++) {
				DyFormColumn _qc1 = formx[i];
				// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
				String columnid = _qc1.getColumnid();
				// 字段名（中文）
				String columnname = _qc1.getColumname();

				html.append("," + "{name: '" + columnid + "'}");
			}
		}
		return html.toString();
	}

	public static String buildExtFields2(DyForm dyform) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对表单中的相关字段
		DyFormColumn[] _formx = dyform.getAllColumn_();

		// html.append("'" + "status" + "'");
		// html.append("," + "'" + "run" + "'");
		// html.append("," + "'" + "runtimeid" + "'");
		html.append("" + "'" + "lsh" + "'");
		html.append("," + "'" + "sid" + "'");
		String split = ",";
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			if (_qc1.isHidden() == false) {
				html.append(split + "'" + columnid + "'");
				split = ",";
			}

		}
		return html.toString();
	}

	/**
	 * 建立Ext所需要的列<BR>
	 * {header: "需求名称", width: 120, dataIndex: 'subjectName', sortable: true},
	 * {header: "客户姓名", width: 100, dataIndex: 'customerName', sortable: true}
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildExtColumns(DyForm dyform, String type,
			boolean isedit, String usercode) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对表单中的相关字段
		// DyFormColumn[] _formx = dyform.getListColumn_();
		String split = "";
		DyFormColumn[] _formx = new DyFormColumn[0];

		DyFormData data = new DyFormData();
		data.setColumn3(usercode);
		data.setColumn5(dyform.getFormcode());
		String json = getColDisableConfig(data);

		if (json == null) {// 使用后台默认配置，否则使用用户自定义配置
			try {
				List<DyFormColumn> list = DyEntry.iv().queryColumnX(
						dyform.getFormcode(), "2");
				_formx = (DyFormColumn[]) list.toArray(new DyFormColumn[list
						.size()]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			_formx = dyform.getAllColumn_();
		}

		JSONObject jsonobj = JSONObject.fromObject(json);

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			// 是否隐蔽
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				String ext = routeAppointValue(_qc1.getViewtype(), columnid,
						_qc1.getValuelist(), "ext");// 扩展脚本控制
				// ext = null;

				// String musktip = _qc1.isMusk_() == true ? "<span
				// style=\"color:red\">*</span>"
				// : "";
				String musktip = "";

				boolean disable = false;
				if (jsonobj != null) {
					if (jsonobj.containsKey(columnid)) {
						String disables = (String) jsonobj.get(columnid);
						if ("1".equals(disables)) {
							disable = false;
						} else {
							disable = true;
						}
					}
				}

				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// 非隐藏
					html.append(split + "{header: '" + columnname + musktip
							+ "', dataIndex: '" + columnid
							+ "', sortable: true" + ",hidden:" + disable
							+ (ext == null ? "" : ("," + ext)) + "}");
					split = ",";
				}
			}
		}

		if ("1".equals(type)) {
			html
					.append(",{header: \"操作\",dataIndex: \"\", sortable: false, renderer:");
			html
					.append("     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {");
			html
					.append("     	   var lsh = store.getAt(rowIndex).get('lsh') ;");
			html
					.append("     	   var runtimeid = store.getAt(rowIndex).get('runtimeid') ;");
			html
					.append("     	   var RUN = store.getAt(rowIndex).get('run') ;");
			html.append("     	   var value = \"\";");
			// html.append(" if(RUN==true){");
			html
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$query('\"+lsh+\"','\"+runtimeid+\"','true'); >查看</a>&nbsp;\";");
			// html.append("}else{");
			html
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$edit('\"+lsh+\"','\"+runtimeid+\"'); >编辑</a>&nbsp;\";");
			// html.append(" }");

			html
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$delete('\"+lsh+\"'); >删除</a>&nbsp;\";");

			html.append("return  value;");
			html.append("}");

			html.append("}");
		} else if ("2".equals(type)) {
			html
					.append(",{header: \"操作\",dataIndex: \"\", sortable: false, renderer:");
			html
					.append("     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {");
			html
					.append("     	   var lsh = store.getAt(rowIndex).get('lsh') ;");
			html.append("     	   var value = \"\";");
			html
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$query('\"+lsh+\"'); >查看</a>&nbsp;\";");
			if (isedit) {
				html
						.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$edit('\"+lsh+\"'); >编辑</a>&nbsp;\";");
				html
						.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$delete('\"+lsh+\"'); >删除</a>&nbsp;\";");
			}

			html.append("return  value;");
			html.append("}");

			html.append("}");
		}

		return html.toString();
	}

	public static String buildExtColumnsX(DyFormColumn[] dyform, String type,
			boolean isedit) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对表单中的相关字段
		DyFormColumn[] _formx = dyform;
		String split = "";
		if (_formx != null) {
			for (int i = 0; i < _formx.length; i++) {
				DyFormColumn _qc1 = _formx[i];
				// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
				String columnid = _qc1.getColumnid();
				// 字段名（中文）
				String columnname = _qc1.getColumname();

				String ext = routeAppointValue(_qc1.getViewtype(), columnid,
						_qc1.getValuelist(), "ext");// 扩展脚本控制
				// ext = null;

				String musktip = "";
				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// 非隐藏
					html.append(split + "{header: '" + columnname + musktip
							+ "', dataIndex: '" + columnid
							+ "', sortable: true"
							+ (ext == null ? "" : ("," + ext)) + "}");
					split = ",";
				}
			}
		}

		return html.toString();
	}

	/**
	 * 建立Ext所需要的列<BR>
	 * {header: "需求名称", width: 120, dataIndex: 'subjectName', sortable: true},
	 * {header: "客户姓名", width: 100, dataIndex: 'customerName', sortable: true}
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildExtGridColumns(DyForm dyform, String usercode) {
		StringBuffer html = new StringBuffer();
		// 展示表单字段-针对表单中的相关字段
		DyFormColumn[] _formx = dyform.getAllColumn_();
		String split = ",";

		DyFormData data = new DyFormData();
		data.setColumn3(usercode);
		data.setColumn5(dyform.getFormcode());
		String json = getColDisableConfig(data);
		JSONObject jsonobj = JSONObject.fromObject(json);

		html
				.append("new Ext.grid.CheckboxSelectionModel({singleSelect: false})");
		html
				.append(",{header : '序号',sortable: true,dataIndex:'sid',width : 22, renderer:function(value, cellmeta, record, rowIndex){var lsh=record.get('lsh');if (lsh=='summary') return ''; else return rowIndex + 1; }, align : 'left' }");

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			// 是否隐蔽
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				String ext = "renderer:function operateRend( value , cellmeta , record , rowIndex , columnIndex , store ){ return '"
						+ routeAppointComp(_qc1.getViewtype(), _qc1
								.getColumnid(), "" + _qc1.getValuelist(),
								"width:98%", "", _qc1.isReadonly(), _qc1
										.getValuelist(), "", "", "", _qc1
										.getDefaultValue()) + "';}";
				ext = null;

				String musktip = _qc1.isMusk_() == true ? "<span style=\"color:red\">*</span>"
						: "";

				boolean disable = false;
				if (jsonobj != null) {
					if (jsonobj.containsKey(columnid)) {
						String disables = (String) jsonobj.get(columnid);
						if ("1".equals(disables)) {
							disable = false;
						} else {
							disable = true;
						}
					}
				}

				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// 非隐藏
					html.append(split + "{header: '" + columnname + musktip
							+ "',dataIndex: '" + columnid + "', sortable: true"
							+ ",width:" + _qc1.getWidth() + ",hidden:"
							+ disable + (ext == null ? "" : ("," + ext)) + "}");
					split = ",";
				}
			}
		}
		return html.toString();
	}

	/**
	 * 获取查询结果
	 * 
	 * @param dyform
	 * @param from
	 * @param to
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static String buildResult(DyForm dyform, DyFormColumn[] formx,
			int from, int to, String condition, boolean isedit)
			throws Exception {
		StringBuffer html = new StringBuffer();
		String formcode = dyform.getFormcode();
		int pagesize = dyform.getEachPageSize_();

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn[] _formx = formx;
		Arrays.sort(_formx, getFormComparator());// 排序
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// 映射
		StringBuffer td = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();

		td.append(DyFormComp.getTd(formcode + DyFormComp._CHECK_NAME,
				DyFormComp.getCheckbox(formcode + "_btn", "", "", "", false),
				"width:1%;" + TableTdStyle2, "", ""));// 全选/清除按钮

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();

			double yoffset = _qc1.getYoffset();

			// 是否隐蔽
			boolean hidden = _qc1.isHidden();

			columnmap.put(columnid, _qc1);
			if (hidden == false) {
				td.append(DyFormComp.getTd(columnid, "&nbsp;" + columnname,
						TableTdStyle2, TABLE_TD_HEADER, ""));
			}
		}
		html.append(DyFormComp.getTr(formcode + "_header", td.toString(), "",
				TABLE_TR_HEADER, ""));

		// 数据
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		List list = DyEntry.iv().queryData(dydata, from, to, condition);

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DyFormData data = (DyFormData) iterator.next();

			html.append(buildTr(uuid(), formcode, true, data, _formx,
					columnmap, columnmapx, isedit, "", ""));
		}

		String html_ = DyFormComp.getTable(formcode, html.toString(), dyform
				.getStyleinfo_(), "", 0, TableExtProperties);

		return html_;
	}

	/**
	 * 分页
	 * 
	 * @param posturl
	 * @param pagesize
	 * @return
	 */
	public static String buildPaginator(String posturl, int from, int pagesize) {
		return DyFormComp.getPaginatorScript(posturl, from, pagesize);
	}

	public static String buildCustomColConfigHtml(DyForm dyform, String usercode) {
		List<DyFormColumn[]> list = new ArrayList<DyFormColumn[]>();
		List<String> titles = new ArrayList<String>();
		List<String> formcodes = new ArrayList<String>();

		list.add(dyform.getAllColumn_());
		titles.add(dyform.getFormname());
		formcodes.add(dyform.getFormcode());

		DyForm[] subforms = dyform.getSubform_();
		if (subforms != null && subforms.length > 0) {
			for (DyForm dyForm2 : subforms) {
				list.add(dyForm2.getAllColumn_());
				titles.add(dyForm2.getFormname());
				formcodes.add(dyForm2.getFormcode());
			}
		}
		StringBuffer html = new StringBuffer();

		for (int j = 0; j < list.size(); j++) {
			DyFormColumn[] col = list.get(j);
			String formcode = formcodes.get(j);

			DyFormData data = new DyFormData();
			data.setColumn3(usercode);
			data.setColumn5(formcode);
			String json = getColDisableConfig(data);

			html.append("<table id=\"" + formcode + "\">");
			for (int i = 0; i < col.length; i++) {
				DyFormColumn column = col[i];
				String columnid = column.getColumnid();
				if (column.isHidden() == false) {
					html.append("<tr>");

					html.append("<td>");
					html.append("&nbsp;");
					html.append(column.getColumname());
					html.append("&nbsp;");
					html.append("</td>");

					JSONObject jsonobj = JSONObject.fromObject(json);

					boolean disable = false;
					if (jsonobj != null) {
						if (jsonobj.containsKey(columnid)) {
							String disables = (String) jsonobj.get(columnid);
							if ("1".equals(disables)) {
								disable = false;
							} else {
								disable = true;
							}
						}
					}
					html.append("<td>");
					html.append(DyFormComp.getBoolean(columnid,
							disable == false ? "1" : "0", "", "", false));
					html.append("</td>");

					html.append("</tr>");
				}
			}
			html.append("</table>");
		}

		return html.toString();
	}

	public static String buildCustomColConfigJs(DyForm dyform) {
		List<String> titles = new ArrayList<String>();
		List<String> formcodes = new ArrayList<String>();

		titles.add(dyform.getFormname());
		formcodes.add(dyform.getFormcode());

		DyForm[] subforms = dyform.getSubform_();
		if (subforms != null && subforms.length > 0) {
			for (DyForm dyForm2 : subforms) {
				titles.add(dyForm2.getFormname());
				formcodes.add(dyForm2.getFormcode());
			}
		}
		StringBuffer html = new StringBuffer();

		for (int j = 0; j < formcodes.size(); j++) {
			String formcode = formcodes.get(j);
			String title = titles.get(j);

			html.append("addTab('" + formcode + "', '" + title + "', '"
					+ formcode + "')");
			if (j == 0)
				html.append(".show()");
			html.append(";");
		}

		return html.toString();
	}

	/**
	 * 以JSON构造
	 * 
	 * @param dydata
	 *            column3 用户编码 column5 FORMCODE
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getColDisableConfig(DyFormData dydata) {
		dydata.setFormcode("47c36268b7c611e1ba92af790c025a41_");// 用户自定义列配置
		dydata.setFatherlsh("1");
		List<DyFormData> list = new ArrayList();
		try {
			list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		DyFormData data = new DyFormData();
		if (list.size() > 0)
			data = list.get(0);
		return data.getColumn4();
	}

	public static String checkColConfigExist(DyFormData dydata) {
		// dydata.setFormcode(formcode);// 用户自定义列配置
		dydata.setFormcode("47c36268b7c611e1ba92af790c025a41_");// 用户自定义列配置
		List<DyFormData> list = new ArrayList();
		try {
			dydata.setFatherlsh("1");
			list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		DyFormData data = new DyFormData();
		if (list.size() > 0)
			data = list.get(0);
		return data.getLsh();
	}

	/**
	 * 获取高级查询SQL
	 * 
	 * @param extcondition
	 * @return
	 */
	public static String getQueryCondition(String extcondition) {
		if ("".equals(extcondition))
			return "";

		JSONArray jsonarr = JSONArray.fromObject(extcondition);
		if (jsonarr.isEmpty())
			return "";
		if (!jsonarr.isArray())
			return "";

		StringBuffer conditions = new StringBuffer();
		StringBuffer orders = new StringBuffer("");

		for (int i = 0; i < jsonarr.size(); i++) {
			JSONObject json = (JSONObject) jsonarr.get(i);

			if (json.containsKey("condition")) {
				JSONArray arrcondition = json.getJSONArray("condition");
				for (int j = 0; j < arrcondition.size(); j++) {
					JSONObject o = arrcondition.getJSONObject(j);

					String c1 = o.getString("c1");
					String c2 = o.getString("c2");
					String c3 = o.getString("c3");
					String c4 = o.getString("c4");
					String c5 = o.getString("c5");
					String c6 = o.getString("c6");

					if ("like".equals(c3) || "=".equals(c3)) {
						conditions.append(" " + c1 + " " + c2 + " " + c3 + " '"
								+ c4 + "' " + c5 + " " + c6 + " ");
					} else {
						conditions.append(" " + c1 + " " + c2 + " " + c3 + " "
								+ c4 + " " + c5 + " " + c6 + " ");
					}

				}
			}
			if (json.containsKey("order")) {
				JSONArray arrorder = json.getJSONArray("order");
				String split = "";
				for (int j = 0; j < arrorder.size(); j++) {
					JSONObject o = arrorder.getJSONObject(j);
					orders.append(split + o.getString("ordercolumnid"));
					split = ",";
				}
			}
		}
		// System.out.println(conditions.toString() + orders.toString());
		if (!"".equals(orders.toString()))
			orders.insert(0, " order by ");

		if (!"".equals(conditions.toString()))
			conditions.insert(0, " and ");
		return conditions.toString() + orders.toString();
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 是否包含 6集成展示 模式
	 * 
	 * @param subdyforms
	 * @return
	 */
	private static boolean isSubmode6(DyForm[] subdyforms) {
		if (subdyforms != null && subdyforms.length > 0) {
			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				String submode = subdyform.getSubmode();
				if ("6".equals(submode)) {// 6:集成展示-主表单右边表单
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		String naturalname = "APPFRAME.APPFRAME.NDYD";
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();

		DyForm dyform = DyEntry.iv().loadForm(formcode);

		// // 查询条件
		// System.out.println(buildQueryColumn(dyform.getQueryColumn_()));
		//
		// // 查询结果
		// System.out.println(buildResult(dyform, dyform.getListColumn_(), 0,
		// 10,
		// "", true));
		//
		// // 分页栏
		// // System.out.println(buildPaginator("", 0, 5));
		//
		// // 宽度
		// System.out.println(calcAvailColumnWidth(2));

		// 打印表单
		String lsh = "44cc006390304e538d3a326c98f6f0b1";
		System.out.println(buildForm(dyform, false, "adminx", naturalname, lsh,
				true, true, "", null));

		// 打印子表单
		System.out.println(buildSubForm(dyform.getSubform_()[0], lsh, true,
				"adminx", "", null));
	}

}
