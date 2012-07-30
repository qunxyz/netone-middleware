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
	// ����һ��������ΪcolumnCountʱ��ÿ�еĿ�ȡ�
	public static int calcAvailColumnWidth(int columnCount) {
		columnCount = columnCount == 0 ? 1 : columnCount;
		return ((AvailWidth - AvailWidthCorrect) - (AvailWidth - AvailWidthCorrect)
				% columnCount)
				/ columnCount;
	}

	// ����һ��������ΪcolumnCountʱ��ÿ�еĿ�ȡ�
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

	// ���Ի���
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

	public static final int AvailWidth = 900;// ������ʾ���
	public static final int AvailWidthN = 700;// ������ʾ���
	public static final int AvailWidthN2 = 150;// ������ʾ���
	public static final int AvailWidthCorrect = 8; // ������ʾ��ȵ�����ֵ
	public static final int AvailNormalFieldTitleWidth = 80;// ��ͨ�ֶα�����
	public static final int AvailNormalFieldCorrectWidth = 7;// ��ͨ�ֶο������ֵ
	public static final int AvailExtBtnWidth = 30;// ��չ�ֶ�button���
	public static final String TableStyle = "";
	// public static final String TableStyle = " border-bottom:inset 3px
	// #C5E4FE;border-top:outset 3px #C5E4FE;border-left:3px outset
	// #C5E4FE;border-right:3px inset #C5E4FE; width:"
	// + AvailWidth + "px;table-layout:fixed; border-collapse:collapse;";
	public static final String TableExtProperties = " style=\"word-break:break-all;margin-top:2px;margin-bottom:2px;\" bgcolor=\"white\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"";
	public static final String TableTdStyle = "";
	public static final String TableTdStyle2 = "";

	/**
	 * ·��ָ�����<BR>
	 * ������������¼�<BR>
	 * ͨ�� ����/��� �ʼ���ַ �б���Ϣ �б���ϢK-V ���ı�
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
		if (arr[0][0].equals(htmltype)) {// 00:ͨ��
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[1][0].equals(htmltype)) {// 01:����/���
			return DyFormComp.getNumber(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[2][0].equals(htmltype)) {// 02:ʱ��hh:mm:ss
			return DyFormComp.getTime(id, value, style, classname, readonly);
		} else if (arr[3][0].equals(htmltype)) {// 03:����YYYY-MM-DD
			return DyFormComp.getDate(id, value, style, classname, readonly);
		} else if (arr[4][0].equals(htmltype)) {// 04:����ʱ��YYYY-MM-DD hh:mm:ss
			return DyFormComp
					.getDatetime(id, value, style, classname, readonly);
		} else if (arr[5][0].equals(htmltype)) {// 05:���
			return DyFormComp.getBoolean(id, value, "", "", readonly);
		} else if (arr[6][0].equals(htmltype)) {// 06:�ʼ���ַ
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[7][0].equals(htmltype)) {// 10:�б���Ϣ
			return DyFormComp.getSelect(id, value, style, classname, readonly,
					selectedvalue, extvalue);
		} else if (arr[8][0].equals(htmltype)) {// 11:�б���ϢK-V
			return DyFormComp.getSelectKV(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[9][0].equals(htmltype)) {// 12:IP��ַ
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[10][0].equals(htmltype)) {// 13:���ı�
			value = htmEncode(value);
			// return DyFormComp.getText(id, value, style, classname, readonly,
			// extvalue);
			return DyFormComp.getTextarea(id, value, style + "height:100px;",
					classname, readonly, extvalue);
		} else if (arr[11][0].equals(htmltype)) {// 14:�ļ�
			return null;
		} else if (arr[12][0].equals(htmltype)) {// 15:ͼƬ
			if (StringUtils.isNotEmpty(value)) {
				return DyFormComp.getHref("����", "����", value, "", "_blank");
			}
			return "";
		} else if (arr[13][0].equals(htmltype)) {// 16:��ť
			return null;
		} else if (arr[14][0].equals(htmltype)) {// 17:��ѡ��Դ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[14][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[15][0].equals(htmltype)) {// 18:��ѡ��Դ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[15][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL��
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[16][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[17][0].equals(htmltype)) {// 21:����ĵ�
			value = htmEncode2(value);
			return DyFormComp.getWysiwygUI(id, value, style, classname,
					readonly, "");
		} else if (arr[18][0].equals(htmltype)) {// 22:��֯��Ա��ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[18][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[19][0].equals(htmltype)) {// 23:��֯��Ա��ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[19][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[20][0].equals(htmltype)) {// 24:��ǰ��Ա
			String userinfo_ = "";
			if (StringUtils.isNotEmpty(userinfo)) {
				userinfo_ = userinfo.split(",")[0];
			} else {
				userinfo_ = value;
			}
			return DyFormComp.getText(id, userinfo_, style, classname, true,
					extvalue);
		} else if (arr[21][0].equals(htmltype)) {// 25:��ǰ����
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
		} else if (arr[22][0].equals(htmltype)) {// 26:��ѡѡ��
			return DyFormComp.getCheckboxs(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[23][0].equals(htmltype)) {// 27:��֯������ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[23][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[24][0].equals(htmltype)) {// 28:��֯������ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[24][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			return DyFormComp.getIframe(id, value, style, classname, readonly,
					parameter);
		} else if (arr[26][0].equals(htmltype)) {// 30:radio��
			return DyFormComp.getGroupRadio(id, value, style, classname,
					readonly, selectedvalue);
		} else if (arr[27][0].equals(htmltype)) {// 31:���radio
			return DyFormComp.getBooleanRadio(id, value, "", "", readonly);
		} else if (arr[28][0].equals(htmltype)) {// 32:������
			return DyFormComp.getHiddenInput(id, value);
		} else {
			return "";
		}
	}

	/**
	 * ·��ָ����� �����Ƹ߶�<BR>
	 * ������������¼�<BR>
	 * ͨ�� ����/��� �ʼ���ַ �б���Ϣ �б���ϢK-V ���ı�
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
		if (arr[0][0].equals(htmltype)) {// 00:ͨ��
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[1][0].equals(htmltype)) {// 01:����/���
			return DyFormComp.getNumber(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[2][0].equals(htmltype)) {// 02:ʱ��hh:mm:ss
			return DyFormComp.getTime(id, value, style, classname, readonly);
		} else if (arr[3][0].equals(htmltype)) {// 03:����YYYY-MM-DD
			return DyFormComp.getDate(id, value, style, classname, readonly);
		} else if (arr[4][0].equals(htmltype)) {// 04:����ʱ��YYYY-MM-DD hh:mm:ss
			return DyFormComp
					.getDatetime(id, value, style, classname, readonly);
		} else if (arr[5][0].equals(htmltype)) {// 05:���
			return DyFormComp.getBoolean(id, value, "", "", readonly);
		} else if (arr[6][0].equals(htmltype)) {// 06:�ʼ���ַ
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[7][0].equals(htmltype)) {// 10:�б���Ϣ
			return DyFormComp.getSelect(id, value, style, classname, readonly,
					selectedvalue, extvalue);
		} else if (arr[8][0].equals(htmltype)) {// 11:�б���ϢK-V
			return DyFormComp.getSelectKV(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[9][0].equals(htmltype)) {// 12:IP��ַ
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[10][0].equals(htmltype)) {// 13:���ı�
			value = htmEncode(value);
			return DyFormComp.getText(id, value, style, classname, readonly,
					extvalue);
		} else if (arr[11][0].equals(htmltype)) {// 14:�ļ�
			return null;
		} else if (arr[12][0].equals(htmltype)) {// 15:ͼƬ
			if (StringUtils.isNotEmpty(value)) {
				return DyFormComp.getHref("����", "����", value, "", "_blank");
			}
			return "";
		} else if (arr[13][0].equals(htmltype)) {// 16:��ť
			return null;
		} else if (arr[14][0].equals(htmltype)) {// 17:��ѡ��Դ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[14][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[15][0].equals(htmltype)) {// 18:��ѡ��Դ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[15][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL��
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[16][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[17][0].equals(htmltype)) {// 21:����ĵ�
			value = htmEncode2(value);
			return DyFormComp
					.getText(id, value, style, classname, readonly, "");
		} else if (arr[18][0].equals(htmltype)) {// 22:��֯��Ա��ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[18][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[19][0].equals(htmltype)) {// 23:��֯��Ա��ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[19][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[20][0].equals(htmltype)) {// 24:��ǰ��Ա
			String userinfo_ = "";
			if (StringUtils.isNotEmpty(userinfo)) {
				userinfo_ = userinfo.split(",")[0];
			} else {
				userinfo_ = value;
			}
			return DyFormComp.getText(id, userinfo_, style, classname, true,
					extvalue);
		} else if (arr[21][0].equals(htmltype)) {// 25:��ǰ����
			String userinfo_ = "";
			if (StringUtils.isNotEmpty(userinfo)) {
				userinfo_ = userinfo.split(",")[1];
			} else {
				userinfo_ = value;
			}
			return DyFormComp.getText(id, userinfo_, style, classname, true,
					extvalue);
		} else if (arr[22][0].equals(htmltype)) {// 26:��ѡѡ��
			return DyFormComp.getCheckboxs(id, value, style, classname,
					readonly, selectedvalue, extvalue);
		} else if (arr[23][0].equals(htmltype)) {// 27:��֯������ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					single_select + "&type=" + arr[23][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[24][0].equals(htmltype)) {// 28:��֯������ѡ
			return DyFormComp.getSelect_(id, value, style, classname, readonly,
					multi_select + "&type=" + arr[24][0] + "&naturalname="
							+ selectedvalue, extvalue);
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			return DyFormComp.getText(id, value, style, classname, readonly,
					parameter);
		} else if (arr[26][0].equals(htmltype)) {// 30:radio��
			return DyFormComp.getGroupRadio(id, value, style, classname,
					readonly, selectedvalue);
		} else if (arr[27][0].equals(htmltype)) {// 31:���radio
			return DyFormComp.getBooleanRadio(id, value, "", "", readonly);
		} else if (arr[28][0].equals(htmltype)) {// 32:������
			return DyFormComp.getHiddenInput(id, value);
		} else {
			return "";
		}
	}

	/**
	 * ����Ƿ�������ĵ������ı��ֶ�����
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkMultiDoc(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[10][0].equals(htmltype)) {// 13:���ı�
			return true;
		} else if (arr[17][0].equals(htmltype)) {// 21:����ĵ�
			return true;
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			return true;
		}
		return false;
	}

	/**
	 * ����Ƿ�������ĵ������ı��ֶ�����
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkMultiDoc2(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[10][0].equals(htmltype)) {// 13:���ı�
			return true;
		}
		return false;
	}

	/**
	 * ����Ƿ������
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkBoolean(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[5][0].equals(htmltype)) {// 05:���
			return true;
		}
		return false;
	}

	/**
	 * ����Ƿ�ѡ�����
	 * 
	 * @param htmltype
	 * @return
	 */
	protected static boolean checkSelect_(String htmltype) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[14][0].equals(htmltype)) {// 17:��ѡ��Դ
			return true;
		} else if (arr[15][0].equals(htmltype)) {// 18:��ѡ��Դ
			return true;
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL��
			return true;
		} else if (arr[18][0].equals(htmltype)) {// 22:��֯��Ա��ѡ
			return true;
		} else if (arr[19][0].equals(htmltype)) {// 23:��֯��Ա��ѡ
			return true;
		} else if (arr[23][0].equals(htmltype)) {// 27:��֯������ѡ
			return true;
		} else if (arr[24][0].equals(htmltype)) {// 28:��֯������ѡ
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ·��ָ��ֵ
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
	 * ·��ָ��ֵ
	 * 
	 * @param htmltype
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	protected static String routeAppointValue(String htmltype, String value,
			String selectedvalue, String type) {
		String[][] arr = DyFormConsoleIfc._HTML_LIST;
		if (arr[0][0].equals(htmltype)) {// 00:ͨ��
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[1][0].equals(htmltype)) {// 01:����/���
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[2][0].equals(htmltype)) {// 02:ʱ��hh:mm:ss
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[3][0].equals(htmltype)) {// 03:����YYYY-MM-DD
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[4][0].equals(htmltype)) {// 04:����ʱ��YYYY-MM-DD hh:mm:ss
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[5][0].equals(htmltype)) {// 05:���
			if ("ext".equals(type)) {
				String columnid = value;
				return DyFormComp.getJsBooleanText(columnid, selectedvalue);
			}
			return DyFormComp.getBooleanText(value);
		} else if (arr[6][0].equals(htmltype)) {// 06:�ʼ���ַ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[7][0].equals(htmltype)) {// 10:�б���Ϣ
			if ("ext".equals(type)) {
				return null;
			}
			return DyFormComp.getSelectText(value, selectedvalue);
		} else if (arr[8][0].equals(htmltype)) {// 11:�б���ϢK-V
			if ("ext".equals(type)) {
				return DyFormComp.getJsSelectTextKV(value, selectedvalue);
			}
			return DyFormComp.getSelectTextKV(value, selectedvalue);
			// if ("ext".equals(type)) {
			// return null;
			// }
			// return value;
		} else if (arr[9][0].equals(htmltype)) {// 12:IP��ַ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[10][0].equals(htmltype)) {// 13:���ı�
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode(value);
			return value;
		} else if (arr[11][0].equals(htmltype)) {// 14:�ļ�
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode(value);
			return value;
		} else if (arr[12][0].equals(htmltype)) {// 15:ͼƬ
			if ("ext".equals(type)) {
				if (StringUtils.isNotEmpty(value)) {
					return DyFormComp.getJsFileHref(value, "", "����");
				} else {
					return "";
				}
			}
			value = htmEncode(value);
			return DyFormComp.getHref("����", "����", value, "", "_blank");
		} else if (arr[13][0].equals(htmltype)) {// 16:��ť
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode(value);
			return value;
		} else if (arr[14][0].equals(htmltype)) {// 17:��ѡ��Դ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[15][0].equals(htmltype)) {// 18:��ѡ��Դ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[16][0].equals(htmltype)) {// 20:PORTAL��
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[17][0].equals(htmltype)) {// 21:����ĵ�
			if ("ext".equals(type)) {
				return null;
			}
			value = htmEncode2(value);
			return value;
		} else if (arr[18][0].equals(htmltype)) {// 22:��֯��Ա��ѡ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[19][0].equals(htmltype)) {// 23:��֯��Ա��ѡ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[20][0].equals(htmltype)) {// 24:��ǰ��Ա
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[21][0].equals(htmltype)) {// 25:��ǰ����
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[22][0].equals(htmltype)) {// 26:��ѡѡ��
			if ("ext".equals(type)) {
				return DyFormComp.getJsGroupRadioTextKV(value, selectedvalue);
			}
			return DyFormComp.getCheckboxsText(value, selectedvalue);
		} else if (arr[23][0].equals(htmltype)) {// 27:��֯������ѡ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[24][0].equals(htmltype)) {// 28:��֯������ѡ
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else if (arr[25][0].equals(htmltype)) {// 29:URL
			if ("ext".equals(type)) {
				return null;
			}
			return DyFormComp.getIframe("", value, "", "", false, "");
		} else if (arr[26][0].equals(htmltype)) {// 30:��ѡ��
			if ("ext".equals(type)) {
				String columnid = value;
				return DyFormComp
						.getJsGroupRadioTextKV(columnid, selectedvalue);
			}
			return DyFormComp.getGroupRadioTextKV(value, selectedvalue);
		} else if (arr[27][0].equals(htmltype)) {// 31:���radio
			if ("ext".equals(type)) {
				String columnid = value;
				return DyFormComp
						.getJsBooleanRadioText(columnid, selectedvalue);
			}
			return DyFormComp.getBooleanRadioText(value);
		} else if (arr[28][0].equals(htmltype)) {// 32:������
			if ("ext".equals(type)) {
				return null;
			}
			return value;
		} else {
			return null;
		}
	}

	/**
	 * ��ȡÿ���������
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
				// ÿ����������洢
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
	 * ����ID��ȡDyFormColumn����
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
	 * ����: ���ı�����ΪHtml����,���ں�������
	 * 
	 * @param s
	 * @return
	 */
	protected static String htmEncode(String s) {
		if (StringUtils.isNotEmpty(s)) {
			// System.out.println("���ı�ת��ǰ:" + s);
			s = s.replaceAll("<br>", "\n");
			s = s.replaceAll("<BR>", "\n");
			// System.out.println("���ı�ת����:" + s);
		}
		return s;
	}

	protected static String htmEncode2(String s) {
		if (StringUtils.isNotEmpty(s)) {
			// System.out.println("����ĵ�ת��ǰ:" + s);
			s = StringEscapeUtils.unescapeHtml(s);
			// System.out.println("����ĵ�ת����:" + s);
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

				// ���ȱȽ��У��������ͬ����Ƚ���
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
	 * ��ȡ�ĵ�����
	 * 
	 * @param readonly
	 *            ֻ�����д
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
	 * ��ȡ�ĵ����� �̶�
	 * 
	 * @param readonly
	 *            ֻ�����д
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
	 * ����������
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
				if (!arr[28][0].equals(column.getViewtype())) {// ������
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
					"align=\"left\""));// ����
		}

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// �����ѱ���,����Ҫ������Ա��Ϣ
			userinfo = "";
		}
		// ����
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// ���ÿ���������
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		// ����ĵ���ͨ�ֶ����ݿ�ʼ
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
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// ���ÿ��HTML����

		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), _qc1.getFocusScript(), _qc1
						.getLoseFocusScript(), _qc1.getOnchangeScript()));// ����¼�����

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// �������
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
		// ����ĵ���ͨ�ֶ����ݽ���

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
					"align=\"left\""));// ����
		}

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// �����ѱ���,����Ҫ������Ա��Ϣ
			userinfo = "";
		}
		// ����
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// ���ÿ���������
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		// ����ĵ���ͨ�ֶ����ݿ�ʼ
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
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// ���ÿ��HTML����

		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), _qc1.getFocusScript(), _qc1
						.getLoseFocusScript(), _qc1.getOnchangeScript()));// ����¼�����

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// �������
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
		// ����ĵ���ͨ�ֶ����ݽ���

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
					"align=\"left\""));// ����
		}

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// �����ѱ���,����Ҫ������Ա��Ϣ
			userinfo = "";
		}

		// ����
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// ���ÿ���������
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		// ����ĵ���ͨ�ֶ����ݿ�ʼ
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
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// ���ÿ��HTML����

		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();

			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), _qc1.getFocusScript(), _qc1
						.getLoseFocusScript(), _qc1.getOnchangeScript()));// ����¼�����

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// �������
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
		// ����ĵ���ͨ�ֶ����ݽ���

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
		if (arr[28][0].equals(column.getViewtype())) {// ����
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
	 * �������ӱ�
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
				+ "'+'&fatherlsh='+lsh;if(lsh=='') alert('��δ����,���ܴ����ӱ�!'); else window.open(url);\" ";
		String btntitle = "<B>�༭</B>";
		if (!isedit) {
			btntitle = "<B>�鿴</B>";
			clickscript = " onclick=\"javascript:var lsh=$('#lsh').val();var url='"
					+ hrefurl
					+ "'+'&query=look&fatherlsh='+lsh;if(lsh=='') alert('��δ����,���ܴ����ӱ�!'); else window.open(url);\" ";
		}

		String link = "&nbsp;"
				+ title
				+ "&nbsp;&nbsp;&nbsp;"
				+ DyFormComp.getHref(btntitle, title, "javascript:void(0);",
						clickscript + " style=\"color:green;\" ", "");
		html.append(DyFormComp.getTr("", DyFormComp.getTd("", link,
				TableTdStyle2 + "width:100%;", TABLE_TD_TITLE,
				"align=\"left\" "), "", TABLE_TR_TITLE, "align=\"left\""));// ����

		String html_ = DyFormComp.getTable(formcode, html.toString(), dyform
				.getStyleinfo_(), "", 0, TableExtProperties);

		return html_.toString();
	}

	/**
	 * ��ȡ��֤�ű�
	 * 
	 * @param dyForm
	 * @return
	 */

	public static String buildValidateScript(DyForm dyForm) {
		DyFormColumn[] allcolumns = dyForm.getAllColumn_();
		DyForm[] subdyforms = dyForm.getSubform_();
		StringBuffer script = new StringBuffer();

		script.append("var str = '����ʧ��!������ʾ����:<br />';" + _N);
		script.append("var i=1;" + _N);
		script.append("var blank = '&nbsp;&nbsp;';" + _N);
		for (int i = 0; i < allcolumns.length; i++) {
			DyFormColumn _c1 = allcolumns[i];

			String columnid = _c1.getColumnid();
			String columnname = _c1.getColumname();
			boolean hidden = _c1.isHidden();// �Ƿ�����
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
					script.append(" str+= blank+ i+ '��" + columnname
							+ "Ϊ�ա�<br />';" + _N);
					script.append(" i++;" + _N);
					script.append("}" + _N);

					if (StringUtils.isNotEmpty(regExp)
							&& StringUtils.isNotBlank(regExp)) {
						script.append(" var reg" + uid + " = " + regExp + ";"
								+ _N);

						script.append("  if(!reg" + uid + ".test(" + uid
								+ ".val())){" + _N);

						script.append(" str+= blank+ i+ '��" + columnname
								+ "�����ϸ�ʽ��<br />';" + _N);
						script.append(" i++;" + _N);
						script.append("}" + _N);
					}
				}

				script.append("}" + _N);
			}
		}
		// script.append("if ($('#belongx_').val()==''){" + _N);
		// script.append(" str+= blank+ i+ '��" + "�������" + "Ϊ�ա�<br />';" + _N);
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
					boolean hidden = _c1.isHidden();// �Ƿ�����
					boolean ismusk = _c1.isMusk_();
					boolean isreadonly = _c1.isReadonly();
					String regExp = _c1.getRegExpression();
					if (hidden == false && isreadonly == false) {

						script.append("$('table#" + subformcode + "').find('#"
								+ columnid + "').each(function(){");

						// start ���
						String uid = "$" + uuid();
						if (ismusk) {
							script.append("if ($(this).val()==''){" + _N);
							script.append(" str+= blank+ i+ '��" + columnname
									+ "Ϊ�ա�<br />';" + _N);
							script.append(" i++;" + _N);
							script.append("}" + _N);

							if (StringUtils.isNotEmpty(regExp)
									& StringUtils.isNotBlank(regExp)) {
								script.append("var reg" + uid + " = " + regExp
										+ ";" + _N);
								script.append("  if(!reg" + uid
										+ ".test($(this).val())){" + _N);

								script.append(" str+= blank+ i+ '��"
										+ columnname + "�����ϸ�ʽ��<br />';" + _N);
								script.append(" i++;" + _N);
								script.append("}" + _N);
							}
						}
						// end ���

						script.append("});");
					}
				}

			}
		}
		script.append("if(i > 1){ ");
		script.append(" Ext.MessageBox.alert('������ʾ',str);");
		script.append("	return false;");
		script.append("}");

		script.append("return true;");
		return script.toString();
	}

	/**
	 * �����ֶ��б�
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

		// չʾ���ֶ�-��Ա��е�����ֶ�
		// DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// �����ѱ���,����Ҫ������Ա��Ϣ
			userinfo = "";
		}
		// ����
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// ���ÿ���������
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		// ����ĵ���ͨ�ֶ����ݿ�ʼ
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
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// ���ÿ��HTML����

		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), "", "", ""));// ����¼�����

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// �������
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
		// ����ĵ���ͨ�ֶ����ݽ���

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
	 * �����ֶ��б�
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

		// չʾ���ֶ�-��Ա��е�����ֶ�
		// DyFormColumn _formx[] = dyform.getAllColumn_();
		if (StringUtils.isNotEmpty(lsh)) {// �����ѱ���,����Ҫ������Ա��Ϣ
			userinfo = "";
		}
		// ����
		Arrays.sort(_formx, getFormComparator());
		Map<Double, Double> columnmap = getMaxYoffsetByX(_formx);// ���ÿ���������
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		// ����ĵ���ͨ�ֶ����ݿ�ʼ
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
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// ���ÿ��HTML����

		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			Double xoffset = _qc1.getYoffset();

			boolean hidden = _qc1.isHidden();
			if (hidden == false) {

				eventListenScripts.append(DyFormComp.getEventScript("$('table#"
						+ formcode + "').find('#" + columnid + "')", _qc1
						.getInitScript(), "", "", ""));// ����¼�����

				// System.out.println(columnname + ":" + xoffset + ":" + yoffset
				// + ":" + hidden);
				String value = BeanUtils.getProperty(dydata, columnid);
				value = value == null ? "" : value;
				DyFormColumn column = columnmapx.get(columnid);
				Double yoffset_ = columnmap.get(xoffset);// �������
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
		// ����ĵ���ͨ�ֶ����ݽ���

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
	 * ��ȡ��ѯ������
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildQueryColumn(DyFormColumn queryColumn[]) {
		StringBuffer html = new StringBuffer();
		// չʾ���ֶ�-��Թ����б��еĲ�ѯ�ֶ�
		Arrays.sort(queryColumn, getFormComparator());// ����
		for (int i = 0; i < queryColumn.length; i++) {
			DyFormColumn _qc1 = queryColumn[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();
			// �ֶε�html����
			String htmltype = _qc1.getViewtype();
			// �ֶ�Ĭ��ֵ
			String valuelist = _qc1.getValuelist();

			if (columnid.contains("column")) {
				String input = DyFormComp.getTag("<span>&nbsp;" + columnname
						+ ":", "</span>", routeAppointComp2(htmltype, columnid,
						"", "", "", false, valuelist, "", "", "", _qc1
								.getDefaultValue()));
				html.append(input);
			}
		}
		html.append(DyFormComp.getButton("", " �� ѯ ", "", "", false,
				" onclick='refresh();' "));
		// html.append(DyFormComp.getButton("", "��ϸ��ѯ", "", "", false,
		// " onclick='alert(\"��ϸ��ѯ\")' "));
		return html.toString();
	}

	/**
	 * ��ȡ��ѯ����
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildQueryCondition(DyFormColumn queryColumn[]) {
		StringBuffer html = new StringBuffer();
		// չʾ���ֶ�-��Թ����б��еĲ�ѯ�ֶ�
		Arrays.sort(queryColumn, getFormComparator());// ����
		html.append("condition: '" + "" + "'");
		for (int i = 0; i < queryColumn.length; i++) {
			DyFormColumn _qc1 = queryColumn[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
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
		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		StringBuffer html = new StringBuffer();
		StringBuffer theadhtml = new StringBuffer();
		DyForm dyform = subdyform;
		String formcode = dyform.getFormcode();

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn _formx[] = dyform.getAllColumn_();
		Arrays.sort(_formx, getFormComparator());// ����
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		StringBuffer td = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();
		int colspan = 1;

		if (isedit == true) {
			td.append(DyFormComp.getTh(formcode + DyFormComp._CHECK_NAME,
					DyFormComp
							.getCheckbox(formcode + "_btn", "", "", "", false),
					"width:1%;" + TableTdStyle2, "", ""));// ȫѡ/�����ť
		}

		htmlall.append(DyFormComp.getClickEventScript("$(\"#" + formcode
				+ "_btn\")", DyFormComp.getSelectOrUnselectAll(formcode)));

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			// �Ƿ�����
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				columnmap.put(columnid, _qc1);
				String musktip = _qc1.isMusk_() == true ? "<span style=\"color:red\">*</span>"
						: "";

				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// ������
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
										.getOnchangeScript()));// ����¼�����

			}
		}

		// html.append(DyFormComp.getTr("", DyFormComp.getTd("", "&nbsp;"
		// + dyform.getFormname(), TableTdStyle2, TABLE_TD_TITLE,
		// "align=\"left\" colspan=\"" + colspan + "\""), "",
		// TABLE_TR_TITLE, "align=\"left\""));// ����
		theadhtml.append(DyFormComp.getTr("", td.toString(), "",
				TABLE_TR_HEADER, ""));// ��ͷ

		// ����
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh(fatherlsh);
		List list = new ArrayList();
		// if (StringUtils.isNotEmpty(fatherlsh)) {
		list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// }

		if (list.size() > 0) {// �м�¼
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();
				html.append(buildTr(uuid(), formcode, false, data, _formx,
						columnmap, columnmapx, isedit, userinfo, parameter));
			}
		} else {// �޼�¼
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
		btnstr.append(DyFormComp.getButton("", "������", "", "btn", false,
				addrowscript)
				+ _N);
		btnstr.append(DyFormComp.getButton("", "ɾ����", "", "btn", false,
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
					TABLE_TR_TITLE, "align=\"left\"");// ��ť�˵�

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
		// tablejs.append(" 'sProcessing': '������...', \n");
		// tablejs.append(" /*'sLengthMenu': ' ��ʾ_MENU_����¼',*/ \n");
		// tablejs.append(" 'sZeroRecords': 'û���ҵ���¼!', \n");
		// tablejs.append(" 'sInfo': '��_START_��_END_����¼ ��_TOTAL_����¼', \n");
		// tablejs.append(" 'sInfoEmpty': '��0����0����¼ ��0����¼', \n");
		// tablejs.append(" 'sInfoFiltered': '(��_MAX_����¼����)', \n");
		// tablejs.append(" 'sInfoPostFix': '', \n");
		// tablejs.append(" 'sSearch': '����:', \n");
		// tablejs.append(" 'sUrl': '', \n");
		// tablejs.append(" 'oPaginate': { \n");
		// tablejs.append(" 'sFirst': '��ҳ', \n");
		// tablejs.append(" 'sPrevious': '��һҳ', \n");
		// tablejs.append(" 'sNext': '��һҳ', \n");
		// tablejs.append(" 'sLast': 'ĩҳ' \n");
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

		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		StringBuffer html = new StringBuffer();
		DyForm dyform = subdyform;
		String formcode = dyform.getFormcode();

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn _formx[] = dyform.getAllColumn_();
		Arrays.sort(_formx, getFormComparator());// ����
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		StringBuffer td = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();
		int colspan = 1;

		if (isedit == true) {
			td.append(DyFormComp.getTd(formcode + DyFormComp._CHECK_NAME,
					DyFormComp
							.getCheckbox(formcode + "_btn", "", "", "", false),
					"width:1%;" + TableTdStyle2, "", ""));// ȫѡ/�����ť
		}

		htmlall.append(DyFormComp.getClickEventScript("$(\"#" + formcode
				+ "_btn\")", DyFormComp.getSelectOrUnselectAll(formcode)));

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			// �Ƿ�����
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				columnmap.put(columnid, _qc1);
				String musktip = _qc1.isMusk_() == true ? "<span style=\"color:red\">*</span>"
						: "";

				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// ������
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
									.getOnchangeScript()));// ����¼�����

			}
		}

		html.append(DyFormComp.getTr("", DyFormComp.getTd("", "&nbsp;"
				+ dyform.getFormname(), TableTdStyle2, TABLE_TD_TITLE,
				"align=\"left\" colspan=\"" + colspan + "\""), "",
				TABLE_TR_TITLE, "align=\"left\""));// ����
		html.append(DyFormComp
				.getTr("", td.toString(), "", TABLE_TR_HEADER, ""));// ��ͷ

		// ����
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh(fatherlsh);
		List list = new ArrayList();
		// if (StringUtils.isNotEmpty(fatherlsh)) {
		list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// }

		if (list.size() > 0) {// �м�¼
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();
				html.append(buildTr(uuid(), formcode, false, data, _formx,
						columnmap, columnmapx, isedit, userinfo, parameter));
			}
		} else {// �޼�¼
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
		btnstr.append(DyFormComp.getButton("", "������", "", "btn", false,
				addrowscript)
				+ _N);
		btnstr.append(DyFormComp.getButton("", "ɾ����", "", "btn", false,
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
					TABLE_TR_TITLE, "align=\"left\"");// ��ť�˵�

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

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn _formx[] = dyform.getAllColumn_();
		Arrays.sort(_formx, getFormComparator());// ����
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��

		StringBuffer th = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();

		StringBuffer datas = new StringBuffer();
		String fields = buildExtFields2(dyform);
		String columns = buildExtGridColumns(dyform, user.getUserCode());

		StringBuffer eventListenScripts = new StringBuffer();
		boolean issummary = false;
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			// debug
			// if ("column3".equals(columnid)) {
			// _qc1.setSummarytype("sum");
			// }

			// �Ƿ�����
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				columnmap.put(columnid, _qc1);

				String summarychangescript = DyFormComp.getSummaryScript(_qc1
						.getSummarytype(), formcode, columnid);// ���ܽű�

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
										+ summarychangescript));// ����¼�����

				String uuid = DyFormBuildHtml.uuid();
				eventListenScripts.append("<script>function $" + uuid + "(){");
				eventListenScripts.append(summarychangescript);
				eventListenScripts.append("}</script>");
				eventListenScripts.append(DyFormComp
						.getJqueryFunctionScript("setTimeout('$" + uuid
								+ "()',1000);"));

			}
			// ���ڻ���
			if (StringUtils.isNotEmpty(_qc1.getSummarytype())) {
				issummary = true;
			}
		}
		htmlall.append(eventListenScripts.toString());

		// ����
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh(fatherlsh);
		List list = new ArrayList();
		// if (StringUtils.isNotEmpty(fatherlsh)) {
		list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
		// }
		if (list.size() > 0) {// �м�¼
			String split = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();

				datas.append(split
						+ buildExtDatas(data, _formx, columnmap, columnmapx,
								isedit, userinfo, parameter));
				split = ",";
			}
		} else {// �޼�¼
			String split = "";
			// for (int i = 0; i < 7; i++) {
			datas.append(split
					+ buildExtDatas(null, _formx, columnmap, columnmapx,
							isedit, userinfo, parameter));
			split = ",";
			// }
		}
		if (issummary) {// ������
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
				.append(",bbar:new Ext.Toolbar([{text : '������ ',iconCls:'addIcon',id:'ext_b_addRow',handler:function(){");

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
				.append(",{text : 'ɾ���� ',iconCls:'deleteIcon',id:'ext_b_deleteRow',handler:function(){ ");

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
	 * ����Ext����Ҫ���ֶ�<BR>
	 * 
	 * {name: 'groupTerminalId'}, {name: 'subjectId'}, {name: 'subjectName'}
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildExtFields(DyForm dyform) {
		StringBuffer html = new StringBuffer();
		// չʾ���ֶ�-��Ա��е�����ֶ�
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
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			html.append("," + "{name: '" + columnid + "'}");
		}
		return html.toString();
	}

	public static String buildExtFieldsX(DyFormColumn[] formx) {
		StringBuffer html = new StringBuffer();
		// չʾ���ֶ�-��Ա��е�����ֶ�

		html.append("{name: '" + "status" + "'}");
		html.append("," + "{name: '" + "run" + "'}");
		html.append("," + "{name: '" + "runtimeid" + "'}");
		html.append("," + "{name: '" + "lsh" + "'}");
		html.append("," + "{name: '" + "lshs" + "'}");
		if (formx != null) {
			for (int i = 0; i < formx.length; i++) {
				DyFormColumn _qc1 = formx[i];
				// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
				String columnid = _qc1.getColumnid();
				// �ֶ��������ģ�
				String columnname = _qc1.getColumname();

				html.append("," + "{name: '" + columnid + "'}");
			}
		}
		return html.toString();
	}

	public static String buildExtFields2(DyForm dyform) {
		StringBuffer html = new StringBuffer();
		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn[] _formx = dyform.getAllColumn_();

		// html.append("'" + "status" + "'");
		// html.append("," + "'" + "run" + "'");
		// html.append("," + "'" + "runtimeid" + "'");
		html.append("" + "'" + "lsh" + "'");
		html.append("," + "'" + "sid" + "'");
		String split = ",";
		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			if (_qc1.isHidden() == false) {
				html.append(split + "'" + columnid + "'");
				split = ",";
			}

		}
		return html.toString();
	}

	/**
	 * ����Ext����Ҫ����<BR>
	 * {header: "��������", width: 120, dataIndex: 'subjectName', sortable: true},
	 * {header: "�ͻ�����", width: 100, dataIndex: 'customerName', sortable: true}
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildExtColumns(DyForm dyform, String type,
			boolean isedit, String usercode) {
		StringBuffer html = new StringBuffer();
		// չʾ���ֶ�-��Ա��е�����ֶ�
		// DyFormColumn[] _formx = dyform.getListColumn_();
		String split = "";
		DyFormColumn[] _formx = new DyFormColumn[0];

		DyFormData data = new DyFormData();
		data.setColumn3(usercode);
		data.setColumn5(dyform.getFormcode());
		String json = getColDisableConfig(data);

		if (json == null) {// ʹ�ú�̨Ĭ�����ã�����ʹ���û��Զ�������
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
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			// �Ƿ�����
			boolean hidden = _qc1.isHidden();

			if (hidden == false) {
				String ext = routeAppointValue(_qc1.getViewtype(), columnid,
						_qc1.getValuelist(), "ext");// ��չ�ű�����
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
				if (!arr[28][0].equals(_qc1.getViewtype())) {// ������
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
					.append(",{header: \"����\",dataIndex: \"\", sortable: false, renderer:");
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
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$query('\"+lsh+\"','\"+runtimeid+\"','true'); >�鿴</a>&nbsp;\";");
			// html.append("}else{");
			html
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$edit('\"+lsh+\"','\"+runtimeid+\"'); >�༭</a>&nbsp;\";");
			// html.append(" }");

			html
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$delete('\"+lsh+\"'); >ɾ��</a>&nbsp;\";");

			html.append("return  value;");
			html.append("}");

			html.append("}");
		} else if ("2".equals(type)) {
			html
					.append(",{header: \"����\",dataIndex: \"\", sortable: false, renderer:");
			html
					.append("     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {");
			html
					.append("     	   var lsh = store.getAt(rowIndex).get('lsh') ;");
			html.append("     	   var value = \"\";");
			html
					.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$query('\"+lsh+\"'); >�鿴</a>&nbsp;\";");
			if (isedit) {
				html
						.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$edit('\"+lsh+\"'); >�༭</a>&nbsp;\";");
				html
						.append("value += \"&nbsp;<a href='javascript:void(0)' onclick=$delete('\"+lsh+\"'); >ɾ��</a>&nbsp;\";");
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
		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn[] _formx = dyform;
		String split = "";
		if (_formx != null) {
			for (int i = 0; i < _formx.length; i++) {
				DyFormColumn _qc1 = _formx[i];
				// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
				String columnid = _qc1.getColumnid();
				// �ֶ��������ģ�
				String columnname = _qc1.getColumname();

				String ext = routeAppointValue(_qc1.getViewtype(), columnid,
						_qc1.getValuelist(), "ext");// ��չ�ű�����
				// ext = null;

				String musktip = "";
				String[][] arr = DyFormConsoleIfc._HTML_LIST;
				if (!arr[28][0].equals(_qc1.getViewtype())) {// ������
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
	 * ����Ext����Ҫ����<BR>
	 * {header: "��������", width: 120, dataIndex: 'subjectName', sortable: true},
	 * {header: "�ͻ�����", width: 100, dataIndex: 'customerName', sortable: true}
	 * 
	 * @param dyform
	 * @return
	 */
	public static String buildExtGridColumns(DyForm dyform, String usercode) {
		StringBuffer html = new StringBuffer();
		// չʾ���ֶ�-��Ա��е�����ֶ�
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
				.append(",{header : '���',sortable: true,dataIndex:'sid',width : 22, renderer:function(value, cellmeta, record, rowIndex){var lsh=record.get('lsh');if (lsh=='summary') return ''; else return rowIndex + 1; }, align : 'left' }");

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			// �Ƿ�����
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
				if (!arr[28][0].equals(_qc1.getViewtype())) {// ������
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
	 * ��ȡ��ѯ���
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

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn[] _formx = formx;
		Arrays.sort(_formx, getFormComparator());// ����
		Map<String, DyFormColumn> columnmapx = getDyFormColumnById(_formx);// ӳ��
		StringBuffer td = new StringBuffer();
		Map<String, DyFormColumn> columnmap = new HashMap<String, DyFormColumn>();

		td.append(DyFormComp.getTd(formcode + DyFormComp._CHECK_NAME,
				DyFormComp.getCheckbox(formcode + "_btn", "", "", "", false),
				"width:1%;" + TableTdStyle2, "", ""));// ȫѡ/�����ť

		for (int i = 0; i < _formx.length; i++) {
			DyFormColumn _qc1 = _formx[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();

			double yoffset = _qc1.getYoffset();

			// �Ƿ�����
			boolean hidden = _qc1.isHidden();

			columnmap.put(columnid, _qc1);
			if (hidden == false) {
				td.append(DyFormComp.getTd(columnid, "&nbsp;" + columnname,
						TableTdStyle2, TABLE_TD_HEADER, ""));
			}
		}
		html.append(DyFormComp.getTr(formcode + "_header", td.toString(), "",
				TABLE_TR_HEADER, ""));

		// ����
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
	 * ��ҳ
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
	 * ��JSON����
	 * 
	 * @param dydata
	 *            column3 �û����� column5 FORMCODE
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getColDisableConfig(DyFormData dydata) {
		dydata.setFormcode("47c36268b7c611e1ba92af790c025a41_");// �û��Զ���������
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
		// dydata.setFormcode(formcode);// �û��Զ���������
		dydata.setFormcode("47c36268b7c611e1ba92af790c025a41_");// �û��Զ���������
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
	 * ��ȡ�߼���ѯSQL
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
	 * �Ƿ���� 6����չʾ ģʽ
	 * 
	 * @param subdyforms
	 * @return
	 */
	private static boolean isSubmode6(DyForm[] subdyforms) {
		if (subdyforms != null && subdyforms.length > 0) {
			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				String submode = subdyform.getSubmode();
				if ("6".equals(submode)) {// 6:����չʾ-�����ұ߱�
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

		// // ��ѯ����
		// System.out.println(buildQueryColumn(dyform.getQueryColumn_()));
		//
		// // ��ѯ���
		// System.out.println(buildResult(dyform, dyform.getListColumn_(), 0,
		// 10,
		// "", true));
		//
		// // ��ҳ��
		// // System.out.println(buildPaginator("", 0, 5));
		//
		// // ���
		// System.out.println(calcAvailColumnWidth(2));

		// ��ӡ��
		String lsh = "44cc006390304e538d3a326c98f6f0b1";
		System.out.println(buildForm(dyform, false, "adminx", naturalname, lsh,
				true, true, "", null));

		// ��ӡ�ӱ�
		System.out.println(buildSubForm(dyform.getSubform_()[0], lsh, true,
				"adminx", "", null));
	}

}
