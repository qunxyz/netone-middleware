package com.jl.common.report;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormColumn;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.dyform.DyFormConsoleIfc;
import com.jl.common.dyform.DyFormData;
import com.jl.common.dyform.DyformConsoleImpl;
import com.jl.common.report.obj.QueryColumn;
import com.jl.common.report.obj.Report;

/**
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-30 ����11:35:21
 * @history
 * @ע ���ֶβ��ܰ�����Щ�ַ�_START,_END,_OPE ���ܳ�������ַ�_REPORTID
 */
public class XReportFaceApi {

	// ����һ��������ΪcolumnCountʱ��ÿ�еĿ�ȡ�
	public static int calcAvailColumnWidth(int columnCount) {
		columnCount = columnCount == 0 ? 1 : columnCount;
		return ((getAvailWidth() - AvailWidthCorrect) - (getAvailWidth() - AvailWidthCorrect)
				% columnCount)
				/ columnCount;
	}

	public static int AvailWidth = 900;// ������ʾ���
	public static final int AvailWidthCorrect = 8; // ������ʾ��ȵ�����ֵ
	public static final int AvailNormalFieldTitleWidth = 80;// ��ͨ�ֶα�����
	public static final int AvailNormalFieldCorrectWidth = 7;// ��ͨ�ֶο������ֵ
	public static final int AvailExtBtnWidth = 30;// ��չ�ֶ�button���
	public static final String TableStyle = "";
	// public static final String TableStyle = " border-bottom:inset 3px
	// #C5E4FE;border-top:outset 3px #C5E4FE;border-left:3px outset
	// #C5E4FE;border-right:3px inset #C5E4FE; width:"
	// + AvailWidth + "px;table-layout:fixed; border-collapse:collapse;";
	public static final String TableTdStyle = "";
	public static final String TableTdStyle2 = "";
	public static final String $START = "_START";
	public static final String $END = "_END";
	public static final String $OPE = "_OPE";
	public static final String $REPORTID = "_REPORTID";

	// ���Ի���
	private static final String _N = "";

	private static final String $SPLIT = "$_$";
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

	public static final String _FORM_ID = "_xreport_form";

	private static ResourceBundle config = ResourceBundle.getBundle("config",
			Locale.CHINESE);
	public static final String projectname = config.getString("projectname");
	private static final String single_select = projectname
			+ "/xreport.do?method=onSingleSelectResource";
	private static final String multi_select = projectname
			+ "/xreport.do?method=onMultiSelectResource";
	private static final String edit_frame_ext = projectname
			+ "/frame.do?method=onMainViewExt";

	static String _VAR_TYPE[][] = { { "01", "time" }, { "02", "date" },
			{ "03", "datetime" }, { "04", "string" }, { "05", "number" },
			{ "06", "timerange" }, { "07", "daterange" },
			{ "08", "datetimerange" }, { "09", "numberrange" },
			{ "10", "timecompare" }, { "11", "numbercompare" },
			{ "12", "singleuser" }, { "13", "muliuser" },
			{ "14", "singledir" }, { "15", "mulidir" },
			{ "16", "singleresource" }, { "17", "muliresource" },
			{ "18", "list" }, { "19", "datecompare" },
			{ "20", "datetimecompare" }, { "21", "stringscope" },
			{ "22", "string" } };

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
	protected static String routeAppointComp(String htmltype, String id,
			String value, String style, String selectedvalue, String opesel) {
		String[][] arr = XReportIfc._TYPE;
		String[][] arrx = DyFormConsoleIfc._HTML_LIST;
		double owidth = Double.parseDouble(style);
		double width = Double.parseDouble(style) / 2 - 10;
		double width2 = Double.parseDouble(style) - 30;
		String $ope = DyFormComp
				.getOpe(id + $OPE, value, "", "", false, opesel);
		String hidden$ope = DyFormComp.getHiddenInput(id + $OPE, "=");
		String hidden$id = DyFormComp.getHiddenInput(id, "");

		style = "width:" + owidth + "px";
		String two_width = "width:" + width + "px";
		String two_width_2 = "width:" + width2 + "px";

		if (arr[0][0].equals(htmltype)) {// 01:ʱ��
			return hidden$ope + DyFormComp.getTime(id, value, style, "", false);
		} else if (arr[1][0].equals(htmltype)) {// 02:����
			return hidden$ope + DyFormComp.getDate(id, value, style, "", false);
		} else if (arr[2][0].equals(htmltype)) {// 03:����ʱ��
			return hidden$ope
					+ DyFormComp.getDatetime(id, value, style, "", false);
		} else if (arr[3][0].equals(htmltype)) {// 04:�ַ�
			return hidden$ope
					+ DyFormComp.getText(id, value, style, "", false, "");
		} else if (arr[4][0].equals(htmltype)) {// 05:����
			return hidden$ope
					+ DyFormComp.getNumber(id, value, style, "", false, "");
		} else if (arr[5][0].equals(htmltype)) {// 06:ʱ�䷶Χ
			return hidden$id
					+ hidden$ope
					+ buildChangeScript(id + $START, id + $END, id)
					+ DyFormComp.getTime(id + $START, value, two_width, "",
							false)
					+ "��"
					+ DyFormComp
							.getTime(id + $END, value, two_width, "", false);
		} else if (arr[6][0].equals(htmltype)) {// 07:���ڷ�Χ
			return hidden$id
					+ hidden$ope
					+ buildChangeScript(id + $START, id + $END, id)
					+ DyFormComp.getDate(id + $START, value, two_width, "",
							false)
					+ "��"
					+ DyFormComp
							.getDate(id + $END, value, two_width, "", false);
		} else if (arr[7][0].equals(htmltype)) {// 08:����ʱ�䷶Χ
			return hidden$id
					+ hidden$ope
					+ buildChangeScript(id + $START, id + $END, id)
					+ DyFormComp.getDatetime(id + $START, value, two_width, "",
							false)
					+ "��"
					+ DyFormComp.getDatetime(id + $END, value, two_width, "",
							false);
		} else if (arr[8][0].equals(htmltype)) {// 09:���ַ�Χ
			return hidden$id
					+ hidden$ope
					+ buildChangeScript(id + $START, id + $END, id)
					+ DyFormComp.getNumber(id + $START, value, two_width, "",
							false, "")
					+ "��"
					+ DyFormComp.getNumber(id + $END, value, two_width, "",
							false, "");
		} else if (arr[9][0].equals(htmltype)) {// 10:ʱ��Ƚ�
			return $ope + DyFormComp.getTime(id, value, two_width_2, "", false);
		} else if (arr[10][0].equals(htmltype)) {// 11:��ֵ�Ƚ�
			return $ope
					+ DyFormComp.getNumber(id, value, two_width_2, "", false,
							"");
		} else if (arr[11][0].equals(htmltype)) {// 12:��ѡ��Ա
			return hidden$ope
					+ DyFormComp.getSelect_(id, value, style, "", false,
							single_select + "&type=" + arrx[15][0]
									+ "&naturalname=" + selectedvalue, "");
		} else if (arr[12][0].equals(htmltype)) {// 13:��ѡ��Ա
			return hidden$ope
					+ DyFormComp.getSelect_(id, value, style, "", false,
							multi_select + "&type=" + arrx[16][0]
									+ "&naturalname=" + selectedvalue, "");
		} else if (arr[13][0].equals(htmltype)) {// 14:��ѡĿ¼
			return hidden$ope
					+ DyFormComp.getSelect_(id, value, style, "", false,
							single_select + "&type=" + arrx[15][0]
									+ "&naturalname=" + selectedvalue, "");
		} else if (arr[14][0].equals(htmltype)) {// 15:��ѡĿ¼
			return hidden$ope
					+ DyFormComp.getSelect_(id, value, style, "", false,
							multi_select + "&type=" + arrx[16][0]
									+ "&naturalname=" + selectedvalue, "");
		} else if (arr[15][0].equals(htmltype)) {// 16:��ѡ��Դ
			return hidden$ope
					+ DyFormComp.getSelect_(id, value, style, "", false,
							single_select + "&type=" + arrx[11][0]
									+ "&naturalname=" + selectedvalue, "");
		} else if (arr[16][0].equals(htmltype)) {// 17:��ѡ��Դ
			return hidden$ope
					+ DyFormComp.getSelect_(id, value, style, "", false,
							multi_select + "&type=" + arrx[12][0]
									+ "&naturalname=" + selectedvalue, "");
		} else if (arr[17][0].equals(htmltype)) {// 18:����
			return hidden$ope
					+ DyFormComp.getSelectKV(id, value, style, "", false,
							selectedvalue, "");
		} else if (arr[18][0].equals(htmltype)) {// 19:���ڱȽ�
			return $ope + DyFormComp.getDate(id, value, two_width_2, "", false);
		} else if (arr[19][0].equals(htmltype)) {// 20:����ʱ��Ƚ�
			return $ope
					+ DyFormComp.getDatetime(id, value, two_width_2, "", false);
		} else if (arr[20][0].equals(htmltype)) {// 21:�ַ�����Χ
			return hidden$id
			+ hidden$ope
			+ buildChangeScript(id + $START, id + $END, id)
			+ DyFormComp.getNumber(id + $START, value, two_width, "",
					false, "")
			+ "��"
			+ DyFormComp.getNumber(id + $END, value, two_width, "",
					false, "");
		} else if (arr[21][0].equals(htmltype)) {// 22:ģ����ѯ
			return hidden$ope
			+ DyFormComp.getText(id, value, style, "", false, "");
		} else {
			return "";
		}
	}

	private static String buildChangeScript(String id, String toid) {
		String $id = "$(\"#" + id + "\")";
		String $toid = "$(\"#" + toid + "\")";
		String change_event = "if (" + $toid + ") " + $toid + ".val(" + $id
				+ ".val())";

		String _script = "if (" + $id + ") " + $id + ".blur(function(){"
				+ change_event + "});";

		return DyFormComp.getJqueryFunctionScript(_script);
	}

	private static String buildChangeScript(String id1, String id2, String toid) {
		String $id1 = "$(\"#" + id1 + "\")";
		String $id2 = "$(\"#" + id2 + "\")";
		String $toid = "$(\"#" + toid + "\")";

		String $total = $id1 + ".val()" + "+\"" + $SPLIT + "\"+" + $id2
				+ ".val()";

		String change_event = "if (" + $toid + ") " + $toid + ".val(" + $total
				+ ")";

		String _script1 = "if (" + $id1 + ") " + $id1 + ".blur(function(){"
				+ change_event + "});";
		String _script2 = "if (" + $id2 + ") " + $id2 + ".blur(function(){"
				+ change_event + "});";
		return DyFormComp.getJqueryFunctionScript(_script1 + _script2);
	}

	/**
	 * ��ȡÿ���������
	 * 
	 * @param dyFormColumns
	 * @return
	 */
	protected static Map<Integer, Integer> getMaxYoffsetByX(
			QueryColumn[] queryColumns) {
		Map<Integer, Integer> columnmap = new HashMap<Integer, Integer>();
		for (int i = 0; i < queryColumns.length; i++) {
			QueryColumn _qc1 = queryColumns[i];
			int yoffset = _qc1.getXoffset();
			int xoffset = _qc1.getYoffset();
			// ÿ����������洢
			if (columnmap.containsKey(xoffset)) {
				Integer yoffset_ = columnmap.get(xoffset);
				if (yoffset > yoffset_) {
					columnmap.put(xoffset, yoffset);
				}
			} else {
				columnmap.put(xoffset, yoffset);
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
	protected static Map<String, QueryColumn> getQueryFormColumnById(
			QueryColumn[] queryColumns) {
		Map<String, QueryColumn> columnmapx = new HashMap<String, QueryColumn>();
		for (int i = 0; i < queryColumns.length; i++) {
			QueryColumn _qc1 = queryColumns[i];
			String columnid = _qc1.getColumnid();
			columnmapx.put(columnid, _qc1);
		}
		return columnmapx;
	}

	public static List buildTab(Map<String, Report> reportsMap) {
		StringBuffer selectedvalue = new StringBuffer();
		String split = "";
		List list = new ArrayList();
		int i = 0;
		for (Iterator iterator = reportsMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			Report r = reportsMap.get(key);

			String clickjs = " onchange=javascript:$('#" + $REPORTID
					+ "').val('" + r.getRpId() + "');";
			String checked = "";
			if (i == 0)
				checked = "1";

			list.add(DyFormComp.getSingleRadio($REPORTID + "KEY", checked, "", "",
					false, clickjs)
					+ "&nbsp;" + r.getRpName());
			split = ",";
			i++;
		}
		return list;
	}

	public static String buildQueryForm(QueryColumn queryColumn[],
			Map<String, Report> reportsMap, String naturalname)
			throws Exception {
		String formcode = "xreport";

		StringBuffer html = new StringBuffer();

		// ����
		Arrays.sort(queryColumn, getFormComparator());
		Map<Integer, Integer> columnmap = getMaxYoffsetByX(queryColumn);// ���ÿ���������
		Map<String, QueryColumn> columnmapx = getQueryFormColumnById(queryColumn);// ӳ��

		// ����ĵ���ͨ�ֶ����ݿ�ʼ
		Method[] ms = DyFormData.class.getMethods();
		Map<Double, String> htmlresult = new TreeMap<Double, String>();// ���ÿ��HTML����

		StringBuffer eventListenScripts = new StringBuffer();// �¼������ű�
		for (int i = 0; i < queryColumn.length; i++) {
			QueryColumn column = queryColumn[i];
			//�ֶΰ�ֵ�Ĵ���
			dealWithKvDict(column);
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = column.getColumnid();
			// �ֶ��������ģ�
			String columnname = column.getColumnname();

			Integer xoffset = column.getYoffset();

			Integer yoffset_ = columnmap.get(xoffset);// �������
			int availColumnWidth = calcAvailColumnWidth(yoffset_.intValue());
			htmlresult = buildTdHtml(htmlresult, availColumnWidth, column,
					columnname, "", xoffset);
			
		}// end for

		for (Iterator iterator = htmlresult.keySet().iterator(); iterator
				.hasNext();) {
			Double x_ = (Double) iterator.next();

			String tdstr = DyFormComp.getTd("", htmlresult.get(x_),
					TableTdStyle, FORM_TD, "");
			html.append(DyFormComp.getTr("", tdstr, "", FORM_TR, "") + _N);
		}

		String TableExtProperties = "width=\""
				+ (getAvailWidth() - 8)
				+ "\" bgcolor=\"white\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"";

		String html_ = DyFormComp.getTable(formcode, html.toString(),
				TableStyle, TABLE_FORM, 0, TableExtProperties);
		// ����ĵ���ͨ�ֶ����ݽ���

		String hiddenid = DyFormComp.getHiddenInput("naturalname", naturalname);

		String id = "";
		for (Iterator iterator = reportsMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			id = key;
			break;
		}

		String reports = "";
		// String reports = DyFormComp.getTag("<div align='center'>", "</div>",
		// DyFormComp.getSelectKV("reports", "", "", "", false,
		// selectedvalue.toString(), "", false));

		String submitbutton = DyFormComp.getHiddenInput($REPORTID, id);

		String html_2 = eventListenScripts.toString()
				+ DyFormComp.getForm(_FORM_ID, hiddenid + html_ + reports
						+ submitbutton) + _N;
		return html_2;
	}

	protected static Map<Double, String> buildTdHtml(
			Map<Double, String> htmlresult, int availColumnWidth,
			QueryColumn column, String columnname, String value, double xoffset) {

		int columnsize = (columnname.length() * 12) + 12;
		String html_ = "<div class=\"" + FORM_FIELD_CONTENT
				+ "\" style=\"width:" + availColumnWidth + "px\">" + _N;
		String _width = (availColumnWidth - columnsize) + "px;";
		String _width_input = (availColumnWidth - columnsize
				- AvailNormalFieldCorrectWidth - (true ? AvailExtBtnWidth : 0))
				+ "";
		html_ += "<div class=\"" + FORM_FIELD_CAPTION
				+ "\" align=\"left\" title=\"" + columnname
				+ "\" style=\"width:" + columnsize + "px;" + "\">" + columnname
				+ ":</div>" + _N;

		String _startDiv = "";
		String _endDiv = "";

		String _value2 = "";

		String _value = _value2
				+ routeAppointComp(column.getColumntype(),
						column.getColumnid(), "" + value, "" + _width_input,
						column.getDefaultvalue(), column.getOpesel()) + _N;
		_startDiv = "<div class=\"" + FORM_FIELD_INPUT + "\" style=\"width:"
				+ _width + "\" align=\"left\">" + _N;
		_endDiv = "</div>";

		html_ += DyFormComp.getTag(_startDiv, _endDiv, _value);

		html_ += "</div>" + _N;
		if (htmlresult.containsKey(xoffset)) {
			htmlresult.put(xoffset, htmlresult.get(xoffset) + html_);
		} else {
			htmlresult.put(xoffset, html_);
		}
		return htmlresult;
	}

	protected static Comparator getFormComparator() {
		return new Comparator() {
			public int compare(Object o1, Object o2) {
				QueryColumn col0 = (QueryColumn) o1;
				QueryColumn col1 = (QueryColumn) o2;

				Integer x0 = col0.getXoffset();
				Integer x1 = col1.getXoffset();

				Integer y0 = col0.getYoffset();
				Integer y1 = col1.getYoffset();

				// ���ȱȽ��У��������ͬ����Ƚ���
				int flag = x0.compareTo(x1);
				if (flag == 0) {
					return y0.compareTo(y1);
				} else {
					return flag;
				}
			}
		};
	}

	public static int getAvailWidth() {
		return AvailWidth;
	}

	public static void setAvailWidth(int availWidth) {
		AvailWidth = availWidth;
	}
	//�ֶ����ݰ�
	private static void dealWithKvDict(QueryColumn columnnew) {
		ResourceRmi rs=null;
		try {
			rs = (ResourceRmi) RmiEntry.iv("resource");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// ��չ���� k-v �б�֧���ֵ�Ӧ��
		String htmltype = columnnew.getColumntype();
		System.out.println("-------------htmltype:"+htmltype);
		//KV�б���4��ģʽ 1���ֹ����õı�ѡֵ 2��������Դ��ĳ��Ŀ¼��ֵ 3������SOA�ű� 4������������̬�����ֶ�
		StringBuffer but = new StringBuffer();
		System.out.println(htmltype);
		if ("18".equals(htmltype)) {
			String valuelist = columnnew.getDefaultvalue();
			//������Դ��ĳ��Ŀ¼��ֵ
			String rsinfo = StringUtils.substringBetween(valuelist, "[TREE:", "]");
			
			if (StringUtils.isNotEmpty(rsinfo)) {
				String valuetmp[]=StringUtils.split(rsinfo,",");
				String rsNaturaname=valuetmp[0];
				String rsKey=valuetmp.length==2?valuetmp[1]:"name";
				if (!StringUtils.contains(rsNaturaname, ".")) {
					rsNaturaname = rsNaturaname + "." + rsNaturaname;
				}
				try {
					UmsProtectedobject upo = rs.loadResourceByNatural(rsNaturaname);
					List sub = rs.subResource(upo.getId());

					for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						String key=object.getName();
						if(rsKey.equals("naturalname")){
							key=object.getNaturalname();
						}else if(rsKey.equals("id")){
							key=object.getId();
						}else if(rsKey.equals("nameid")){
							key=object.getName()+"["+object.getId()+"]";
						}else if(rsKey.equals("namenatual")){
							key=object.getName()+"["+object.getNaturalname()+"]";
						}
						but.append(key + "-"
								+ object.getName() + ",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//����SOA�ű�
			rsinfo = StringUtils.substringBetween(valuelist, "[SOA:", "]");
			if (StringUtils.isNotEmpty(rsinfo)) {
				UmsProtectedobject upo=null;
				try {
					upo = rs.loadResourceByNatural(rsinfo);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String param=upo.getDescription();
				EnvService env = null;
				try {

					env = (EnvService) RmiEntry.iv("envinfo");
					String value = env.fetchEnvValue("WEBSER_APPFRAME");
					value=value+"Soasvl?naturalname="+rsinfo+param;

							//System.out.println("sync user to php:"+url);
							// ͨѶЭ��
							URL rul = new URL(value);
							// ���������
							URLConnection urlc = rul.openConnection();
							InputStream input = urlc.getInputStream();
							// �������ݽ���

							int read = 0;
							while ((read = input.read()) != -1) {
								but.append((char) read);
							}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			//�����������ֶ�
			rsinfo = StringUtils.substringBetween(valuelist, "[DYFORM:", "]");

			if (StringUtils.isNotEmpty(rsinfo)) {
				String form[]=rsinfo.split(",");
				DyFormData dfd=new DyFormData();
				dfd.setFatherlsh("1");
				dfd.setFormcode(form[0]);
				String condition=form.length==4?form[3]:"";
				try {
					List data=DyEntry.iv().queryData(dfd, 0, 1000, condition);
					for (Iterator iterator = data.iterator(); iterator
							.hasNext();) {
						DyFormData object = (DyFormData) iterator.next();
						Object key=BeanUtils.getProperty(object, form[1]);
						Object value=BeanUtils.getProperty(object, form[2]);
						String keyinfo=key==null?"":key.toString();
						String valueinfo=value==null?"":value.toString();
						but.append(keyinfo + "-"
								+ valueinfo + ",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (but.length() > 0) {
			columnnew.setDefaultvalue(but.toString());
		}
	}
}
