package com.jl.common.dyform;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;

public final class DyFormComp {

	/**
	 * 获取组件
	 * 
	 * @param startTag
	 *            开始标签
	 * @param endTag
	 *            结束标签
	 * @param id
	 *            编号
	 * @param value
	 *            值
	 * @param style
	 *            样式
	 * @param classname
	 *            类名
	 * @param readonly
	 *            只读
	 * @param extvalue
	 *            扩展属性
	 * @return
	 */
	protected static String getComp(String startTag, String endTag, String id,
			String value, String style, String classname, boolean readonly,
			String extvalue) {
		id = id == null ? "" : id;
		value = value == null ? "" : value;
		style = style == null ? "" : style;
		classname = classname == null ? "" : classname;

		String idAnamestr = !"".equals(id) ? " id=\"" + id + "\" name=\"" + id
				+ "\" " : "";
		String valuestr = !"".equals(value) ? " value=\"" + value + "\" " : "";
		style += (readonly == true ? "background:#cccccc;color:#000;" : "");
		String stylestr = !"".equals(style) ? " style=\"" + style + "\" " : "";
		String classnamestr = !"".equals(classname) ? " class=\"" + classname
				+ "\" " : "";
		String readonlystr = readonly == true ? " readonly=\"readonly\" " : "";

		return startTag + idAnamestr + valuestr + stylestr + classnamestr
				+ readonlystr + " " + extvalue +" unselectable=\"off\" onFocus=\"this.select()\" "+ " " + endTag;
	}

	/**
	 * 获取组件
	 * 
	 * @param startTag
	 *            开始标签
	 * @param endTag
	 *            结束标签
	 * @param id
	 *            编号
	 * @param value
	 *            值
	 * @param style
	 *            样式
	 * @param classname
	 *            类名
	 * @param readonly
	 *            只读
	 * @param extvalue
	 *            扩展属性
	 * @return
	 */
	protected static String getComp(String startTag, String endTag, String id,
			String value, String style, String classname, int disable,
			String extvalue) {
		id = id == null ? "" : id;
		value = value == null ? "" : value;
		style = style == null ? "" : style;
		classname = classname == null ? "" : classname;

		String idAnamestr = !"".equals(id) ? " id=\"" + id + "\" name=\"" + id
				+ "\" " : "";
		String valuestr = !"".equals(value) ? " value=\"" + value + "\" " : "";
		String stylestr = !"".equals(style) ? " style=\"" + style + "\" " : "";
		String classnamestr = !"".equals(classname) ? " class=\"" + classname
				+ "\" " : "";
		String readonlystr = disable == 1 ? " disabled=\"disabled\" " : "";

		return startTag + idAnamestr + valuestr + stylestr + classnamestr
				+ readonlystr + " " + extvalue + " " + endTag;
	}

	/**
	 * 获取标签
	 * 
	 * @param startTag
	 * @param endTag
	 * @param value
	 */
	public static String getTag(String startTag, String endTag, String value) {
		return startTag + value + endTag;
	}

	/**
	 * 获取FORM表单
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public static String getForm(String id, String value) {
		return getTag("<form id=\"" + id + "\">", "</form>", value);
	}

	/**
	 * 获取FORM表单
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public static String getForm(String id, String value, String action) {
		return getTag("<form id=\"" + id + "\" action=\"" + action + "\">",
				"</form>", value);
	}

	/**
	 * 获取样式
	 * 
	 * @param value
	 * @return
	 */
	public static String getStyle(String value) {
		return getTag("<style type=\"text/css\" >", "</style>", value);
	}

	/**
	 * 获取样式
	 * 
	 * @param value
	 * @return
	 */
	public static String getLinkCss(String value) {
		return getTag("<link rel=\"stylesheet\" href=\"",
				"\" type=\"text/css\" >", value);
	}

	/**
	 * 获取链接
	 * 
	 * @param value
	 * @param extValue
	 * @return
	 */
	public static String getHref(String value, String title, String href,
			String extValue, String target) {
		String target_ = "";
		if (StringUtils.isNotEmpty(target)) {
			target_ = "target=\"" + target + "\"";
		}
		return getTag("<a title=\"" + title + "\" " + target_ + " href=\""
				+ href + "\" " + extValue + " >", "</a>", value);
	}

	/**
	 * 获取隐藏域
	 * 
	 * @param value
	 * @return
	 */
	public static String getHiddenInput(String id, String value) {
		if (StringUtils.isEmpty(id) && StringUtils.isBlank(id)) {
			return "";
		}
		return getComp("<input type=\"hidden\" ", " />", id, value, "", "",
				false, "");
	}

	/**
	 * 获取文本框
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getText(String id, String value, String style,
			String classname, boolean readonly, String extvalue) {
		return getComp("<input type=\"text\" ", " />", id, value, style,
				classname, readonly, extvalue);
	}

	/**
	 * 获取按钮
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param extvalue
	 * @return
	 */
	public static String getButton(String id, String value, String style,
			String classname, boolean readonly, String extvalue) {
		return getComp("<input type=\"button\" ", " />", id, value, style,
				classname, readonly, extvalue);
	}

	/**
	 * 获取文本域
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getTextarea(String id, String value, String style,
			String classname, boolean readonly, String extvalue) {
		if (readonly)
			id = "";// 只读不show column ID
		String hiddenInput = getHiddenInput(id, value);
		String $id = "textarea" + id;
		String changescript = " onchange=javascript:" + "$(this).siblings(\"#"
				+ id + "\").val($(this).val());" + " ";
		return hiddenInput
				+ getComp("<textarea ", " >" + value + "</textarea>", $id, "",
						style, "", readonly, " rows=\"2\" cols=\"20\" "
								+ extvalue + changescript);
	}

	/**
	 * 获取数值文本框
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getNumber(String id, String value, String style,
			String classname, boolean readonly, String extvalue) {
		return getComp("<input type=\"text\" ", " />", id, value, style,
				classname, readonly, extvalue);
	}

	public static String getDateComp(String id, String value, String style,
			String classname, boolean readonly, int dateformattype) {
		// String valuestr = "<fmt:formatDate value=\"" + value
		// + "\" pattern=\"yyyy-MM-dd\" />";
		String valuestr = value;
		String extValue = "";
		if (readonly == false) {
			extValue = " onFocus=\"" + WdatePickerFunc + "(" + dateformattype
					+ ");this.blur();\" ";
		}
		return getComp("<input type=\"text\" ", " />", id, valuestr, style,
				"Wdate", readonly, extValue);
	}

	private static final String WdatePickerFunc = "$WdatePicker";

	/**
	 * 初始化所需要的函数(时间控件)
	 * 
	 * @return
	 */
	public static String getInitFuncScript() {
		StringBuffer script = new StringBuffer();
		script.append("function " + WdatePickerFunc + "(t){");
		script.append("if (t==1){");
		script.append("		WdatePicker({dateFmt:\"yyyy-MM-dd HH:mm:ss\"});");
		script.append("	} else if(t==2){");
		script.append("		WdatePicker({dateFmt:\"yyyy-MM-dd\"});");
		script.append("	} else if(t=3){");
		script.append("		WdatePicker({dateFmt:\"HH:mm:ss\"});");
		script.append("	}");
		script.append("}");

		return getTag("<script>", "</script>", script.toString());
	}

	/**
	 * 获取时间控件
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getDate(String id, String value, String style,
			String classname, boolean readonly) {
		return getDateComp(id, value, style, classname, readonly, 2);
	}

	/**
	 * 获取时间控件
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getTime(String id, String value, String style,
			String classname, boolean readonly) {
		return getDateComp(id, value, style, classname, readonly, 3);
	}

	/**
	 * 获取时间控件
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getDatetime(String id, String value, String style,
			String classname, boolean readonly) {
		return getDateComp(id, value, style, classname, readonly, 1);
	}

	/**
	 * 获取下拉列表
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getSelect(String id, String value, String style,
			String classname, boolean readonly, String selectedvalue,
			String extvalue) {
		String[] values = selectedvalue.split(",");
		StringBuffer valuestr = new StringBuffer();
		String split = "";
		for (int i = 0; i < values.length; i++) {
			valuestr.append(split + values[i] + "-" + values[i]);
			split = ",";
		}

		return getSelectKV(id, value, style, classname, readonly, valuestr
				.toString(), extvalue);
	}

	/**
	 * 获取下拉列表KV
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getSelectKV(String id, String value, String style,
			String classname, boolean readonly, String selectedvalue,
			String extvalue) {
		return DyFormComp.getSelectKV(id, value, style, classname, readonly,
				selectedvalue, extvalue, true);
	}

	/**
	 * 获取下拉列表KV
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getSelectKV(String id, String value, String style,
			String classname, boolean readonly, String selectedvalue,
			String extvalue, boolean isselect) {
		String valuelist = selectedvalue;
		String[] v = valuelist.split(",");
		StringBuffer valuestr = new StringBuffer();
		if (isselect) {
			valuestr.append("<option value=\"\" " + "selected=\"selected\""
					+ " >" + "请选择" + "</option>");
		}
		for (int i = 0; i < v.length; i++) {
			String[] x = v[i].split("-");
			if (x != null && x.length == 2) {
				String sele = "";
				if (value.equals(x[0]) || value == x[0]) {
					sele = " selected=\"selected\" ";
				}
				valuestr.append("<option value=\"" + x[0] + "\" " + sele + " >"
						+ x[1] + "</option>");
			}
		}
		return getHiddenInput(id + "_hidden", value)
				+ getComp("<select ", ">" + valuestr + "</select>", id, "",
						style, classname, (readonly == true) ? 1 : 0, extvalue);
	}

	/**
	 * 获取下拉列表选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getSelectText(String value, String selectedvalue) {
		String[] values = selectedvalue.split(",");
		StringBuffer valuestr = new StringBuffer();
		String split = "";
		for (int i = 0; i < values.length; i++) {
			valuestr.append(split + values[i] + "-" + values[i]);
			split = ",";
		}

		return getSelectTextKV(value, valuestr.toString());
	}

	/**
	 * 获取下拉列表KV选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getSelectTextKV(String value, String selectedvalue) {
		String valuelist = selectedvalue;
		String[] v = valuelist.split(",");
		StringBuffer valuestr = new StringBuffer();
		for (int i = 0; i < v.length; i++) {
			String[] x = v[i].split("-");
			if (value.equals(x[0]) || value == x[0]) {
				if (x.length == 2) {
					return x[1];
				} else {
					return x[0];
				}
			}
		}
		return value;
	}

	/**
	 * 获取radio
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getRadio(String id, String value, String style,
			String classname, boolean readonly, String extvalue) {
		String checkstr = "";
		if ("1".equals(value)) {
			checkstr = " checked=\"checked\" ";
		} else {
			checkstr = " ";
		}
		return getComp("<input type=\"radio\" ", checkstr + " />", id
				+ DyFormBuildHtml.uuid(), "", style, classname, readonly,
				extvalue);
	}

	/**
	 * 获取radio组
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getGroupRadio(String id, String value, String style,
			String classname, boolean readonly, String selectedvalue) {
		StringBuffer comp = new StringBuffer();
		String checkstr = "";

		String valuelist = selectedvalue;
		String[] v = valuelist.split(",");

		comp.append(getHiddenInput(id, value));

		String radioid = DyFormBuildHtml.uuid();
		for (int i = 0; i < v.length; i++) {
			String key_ = v[i].split("-")[0];
			String value_ = v[i].split("-")[1];

			if (key_.equals(value)) {
				checkstr = " checked=\"checked\" ";
			} else {
				checkstr = " ";
			}

			String clickscript = " onclick=\"javascript:";
			clickscript += " var o=$(this).siblings(&quot;#" + id + "&quot;);";
			clickscript += " var v=o.val();";
			clickscript += "  if ($(this).attr(&quot;checked&quot;) == true) {";
			clickscript += "   o.val(&quot;" + key_ + "&quot;);";
			clickscript += "  }\" ";
			comp.append(value_
					+ getComp("<input type=\"radio\" ", clickscript + checkstr
							+ " />", id + "_radio" + radioid, "", "", "",
							readonly, ""));
			comp.append(" ");
		}

		return comp.toString();
	}

	/**
	 * 获取radio组Text
	 * 
	 * @param columnid
	 * @param valuelist
	 * @return
	 */
	public static String getJsGroupRadioTextKV(String columnid, String valuelist) {

		StringBuffer jshtml = new StringBuffer();
		jshtml.append("var pre = store.getAt(rowIndex).get('" + columnid
				+ "') ;");
		jshtml.append("var v = \"" + valuelist + "\".split(',');");

		jshtml.append("var val = '';");
		jshtml.append("for (var i = 0; i < v.length; i++) {");
		jshtml.append("	var x = v[i].split('-');");
		jshtml.append("	if (v == x[0]) {");
		jshtml.append("		val=x[1];break;");
		jshtml.append("	}");
		jshtml.append("}");
		jshtml.append("return val;");

		return getTag(
				"renderer:function todo(value, cellmeta, record, rowIndex, columnIndex, store){",
				"}", jshtml.toString());
	}

	/**
	 * 获取radio组KV选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getGroupRadioTextKV(String value, String selectedvalue) {
		String valuelist = selectedvalue;
		String[] v = valuelist.split(",");
		StringBuffer valuestr = new StringBuffer();
		for (int i = 0; i < v.length; i++) {
			String[] x = v[i].split("-");
			if (value.equals(x[0]) || value == x[0]) {
				if (x.length == 2) {
					return x[1];
				} else {
					return x[0];
				}
			}
		}
		return value;
	}

	/**
	 * 获取radio
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getBooleanRadio(String id, String value, String style,
			String classname, boolean readonly) {
		StringBuffer comp = new StringBuffer();
		String checkstr = "";

		comp.append(getHiddenInput(id, value));
		String radioid = DyFormBuildHtml.uuid();
		if ("1".equals(value)) {
			checkstr = " checked=\"checked\" ";
		} else {
			checkstr = " ";
		}
		String clickscript = " onclick=\"javascript:";
		clickscript += " var o=$(this).siblings(&quot;#" + id + "&quot;);";
		clickscript += " var v=o.val();";
		clickscript += "  if ($(this).attr(&quot;checked&quot;) == true) {";
		clickscript += "   o.val(1);";
		clickscript += "  }\" ";

		comp.append(getComp("<input type=\"radio\" value=1 ", clickscript
				+ checkstr + " />", id + "_radio" + radioid, "", style,
				classname, readonly, "")
				+ "真");
		comp.append(" ");

		if ("0".equals(value)) {
			checkstr = " checked=\"checked\" ";
		} else {
			checkstr = " ";
		}
		clickscript = " onclick=\"javascript:";
		clickscript += " var o=$(this).siblings(&quot;#" + id + "&quot;);";
		clickscript += " var v=o.val();";
		clickscript += "  if ($(this).attr(&quot;checked&quot;) == true) {";
		clickscript += "   o.val(0);";
		clickscript += "  }\" ";
		comp.append(getComp("<input type=\"radio\" value=0 ", clickscript
				+ checkstr + " />", id + "_radio" + radioid, "", style,
				classname, readonly, "")
				+ "假");
		return comp.toString();
	}

	/**
	 * 获取下拉列表KV选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getBooleanRadioText(String value) {
		if ("1".equals(value)) {
			return "是";
		} else {
			return "否";
		}
	}

	/**
	 * 获取JS真假选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getJsBooleanRadioText(String columnid, String valuelist) {
		StringBuffer jshtml = new StringBuffer();
		jshtml.append("var pre = record.get('" + columnid + "') ;");
		jshtml.append("if(pre==1){");
		jshtml.append("	return \"是\";");
		jshtml.append("}else{");
		jshtml.append("	return \"否\";");
		jshtml.append("}");

		return getTag(
				"renderer:function todo(value, cellmeta, record, rowIndex, columnIndex, store){",
				"}", jshtml.toString());
	}

	/**
	 * 获取真假checkbox
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getBoolean(String id, String value, String style,
			String classname, boolean readonly) {
		String checkstr = "";
		if ("1".equals(value)) {
			checkstr = " value=1 checked=\"checked\" ";
		} else if ("0".equals(value)) {
			checkstr = " value=0 ";
			value = "0";
		} else {
			value = null;
		}
		String uuid = DyFormBuildHtml.uuid();

		String clickscript = " onclick=\"javascript:";
		clickscript += " var o=$(this).siblings(&quot;#" + id + "&quot;);";
		clickscript += " var v=o.val();";
		clickscript += "  if ($(this).attr(&quot;checked&quot;) == true) {";
		clickscript += "   $(this).val(1);o.val(1);";
		clickscript += "  } else {";
		clickscript += "   $(this).val(0);o.val(0);";
		clickscript += "  }\" ";

		String hidden = getHiddenInput(id, value);
		return hidden
				+ getComp("<input type=\"checkbox\" ", checkstr + clickscript
						+ " />", uuid, "", style, classname, readonly, "");
	}

	/**
	 * 获取下拉列表KV选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getBooleanText(String value) {
		if ("1".equals(value)) {
			return "是";
		} else {
			return "否";
		}
	}

	/**
	 * 获取JS真假选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getJsBooleanText(String columnid, String valuelist) {
		StringBuffer jshtml = new StringBuffer();
		jshtml.append("var pre = record.get('" + columnid + "') ;");
		jshtml.append("if(pre==1){");
		jshtml.append("	return \"是\";");
		jshtml.append("}else{");
		jshtml.append("	return \"否\";");
		jshtml.append("}");

		return getTag(
				"renderer:function todo(value, cellmeta, record, rowIndex, columnIndex, store){",
				"}", jshtml.toString());
	}

	/**
	 * 获取JS下拉列表KV选定的值
	 * 
	 * @param value
	 * @param selectedvalue
	 * @return
	 */
	public static String getJsSelectTextKV(String columnid, String valuelist) {

		StringBuffer jshtml = new StringBuffer();
		jshtml.append("var pre = store.getAt(rowIndex).get('" + columnid
				+ "') ;");
		jshtml.append("var v = \"" + valuelist + "\".split(',');");

		jshtml.append("var val = '';");
		jshtml.append("for (var i = 0; i < v.length; i++) {");
		jshtml.append("	var x = v[i].split('-');");
		jshtml.append("	if (pre == x[0]) {");
		jshtml.append("		val=x[1];break;");
		jshtml.append("	}");
		jshtml.append("}");
		jshtml.append("return val;");

		return getTag(
				"renderer:function todo(value, cellmeta, record, rowIndex, columnIndex, store){",
				"}", jshtml.toString());
	}

	/**
	 * 获取checkbox
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getCheckbox(String id, String value, String style,
			String classname, boolean readonly) {
		String checkstr = "";
		if ("1".equals(value)) {
			checkstr = " checked=\"checked\" ";
		} else {
			checkstr = " ";
		}

		return getComp("<input type=\"checkbox\" ", checkstr + " />", id, "",
				style, classname, readonly, "");
	}

	/**
	 * 获取下拉列表
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getCheckboxs(String id, String value, String style,
			String classname, boolean readonly, String selectedvalue,
			String extvalue) {
		String[] values = selectedvalue.split(",");
		StringBuffer valuestr = new StringBuffer();
		String split = "";
		for (int i = 0; i < values.length; i++) {
			valuestr.append(split + values[i] + "-" + values[i]);
			split = ",";
		}

		return getCheckboxsKV(id, value, style, classname, readonly, valuestr
				.toString());
	}

	/**
	 * 获取多个真假checkbox
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @return
	 */
	public static String getCheckboxsKV(String id, String value, String style,
			String classname, boolean readonly, String selectedvalue) {
		String valuelist = selectedvalue;
		String[] v = valuelist.split(",");
		StringBuffer valuestr = new StringBuffer();
		String[] values = null;
		if (StringUtils.isNotEmpty(value) && StringUtils.isNotBlank(value)) {
			value = value.trim();
			values = value.split(",");
		} else {
			value = "";
		}

		String spanstyle = "color:#000;padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;";

		// 选择事件脚本
		String clickscript = " onclick=\"javascript:var t=$(&quot;#&quot;+this.id).siblings(&quot;span&quot;).text();";
		clickscript += " var o=$(&quot;#&quot;+this.id).parent().parent().siblings(&quot;#"
				+ id + "&quot;);";
		clickscript += " var v=o.val();";
		clickscript += "  if ($(&quot;#&quot;+this.id).attr(&quot;checked&quot;) == true) {";
		clickscript += "   if (v!=&quot;&quot;) o.val(v+&quot;,&quot;+t); else o.val(t);";
		clickscript += "  } else {";
		clickscript += "   var strs = (v+&quot;,&quot;).replace((t+&quot;,&quot;),&quot;&quot;);";
		clickscript += "   o.val(strs.substring(0,strs.length - 1));";
		clickscript += "  }\" ";

		for (int i = 0; i < v.length; i++) {
			String[] x = v[i].split("-");
			String sele = "";

			if (values != null) {
				for (int j = 0; j < values.length; j++) {
					if (values[j].equals(x[0]) || values[j] == x[0]) {
						sele = " checked=\"checked\" ";
					}
				}
			}

			valuestr.append("<span>");
			if ("其它".equals(x[1]) || "其他".equals(x[1])) {
				valuestr.append("<input id=\"" + DyFormBuildHtml.uuid()
						+ "\" type=\"checkbox\" value=\"" + x[0] + "\" " + sele
						+ " />" + "<span style=\"" + spanstyle + "\">" + x[1]
						+ "</span>");

				String changescript = " onchange=\"javascript:var t=$(&quot;#&quot;+this.id).siblings(&quot;span&quot;).text();";
				changescript += " var o=$(&quot;#&quot;+this.id).parent().parent().siblings(&quot;#"
						+ id + "&quot;);";
				changescript += " var v=o.val();";
				changescript += "  if ($(&quot;#&quot;+this.id).attr(&quot;checked&quot;) == true && (t==&quot;其它&quot; || t==&quot;其他&quot;) ) {";
				changescript += "   if (v!=&quot;&quot;) o.val(v+&quot;,&quot;+this.value); else o.val(this.value);";
				changescript += "  } else {";
				changescript += "   var strs = (v+&quot;,&quot;).replace((this.value+&quot;,&quot;),&quot;&quot;);";
				changescript += "   o.val(strs.substring(0,strs.length - 1));";
				changescript += "  }\" ";
				valuestr.append("<input id=\"" + DyFormBuildHtml.uuid()
						+ "\" type=\"text\" " + changescript + " />");
			} else {
				valuestr.append("<input id=\"" + DyFormBuildHtml.uuid()
						+ "\" type=\"checkbox\" value=\"" + x[0] + "\" " + sele
						+ clickscript + " />" + "<span style=\"" + spanstyle
						+ "\">" + x[1] + "</span>");
			}
			valuestr.append("</span>");
		}

		String hiddens = getHiddenInput(id, value);
		return hiddens
				+ getComp("<div ", " align=\"left\">" + valuestr + "</div>",
						"", "", style, classname, (readonly == true) ? 1 : 0,
						"");
	}

	/**
	 * 获取多个真假checkbox值
	 * 
	 * @return
	 */
	public static String getCheckboxsText(String value, String selectedvalue) {
		String[] values = selectedvalue.split(",");
		StringBuffer valuestr = new StringBuffer();
		String split = "";
		for (int i = 0; i < values.length; i++) {
			valuestr.append(split + values[i] + "-" + values[i]);
			split = ",";
		}

		return getCheckboxTextsKV(value, valuestr.toString());
	}

	/**
	 * 获取多个真假checkbox值
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @return
	 */
	public static String getCheckboxTextsKV(String value, String selectedvalue) {
		String valuelist = selectedvalue;
		String[] v = valuelist.split(",");
		StringBuffer valuestr = new StringBuffer();
		String[] values = null;
		if (StringUtils.isNotEmpty(value) && StringUtils.isNotBlank(value)) {
			value = value.trim();
			values = value.split(",");
		} else {
			value = "";
		}

		String split = "";
		for (int i = 0; i < v.length; i++) {
			String[] x = v[i].split("-");
			String sele = "";

			if (values != null) {
				for (int j = 0; j < values.length; j++) {
					if (values[j].equals(x[0]) || values[j] == x[0]) {
						sele = split + x[1];
						split = ",";
					}
				}
			}
			valuestr.append(sele);
		}

		return valuestr.toString();
	}

	/**
	 * 获取选择项 (单选资源,多选资源, PORTAL项, 组织人员单选, 组织人员多选)
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @param url
	 * @return
	 */
	public static String getSelect_(String id, String value, String style,
			String classname, boolean readonly, String url, String selectedvalue) {
		url += "&columnid=" + id;
		String btnid = DyFormBuildHtml.uuid() + "_BTN_UUID";
		String selectbtn = "";
		if (readonly == false) {
			selectbtn = "<input id=\""
					+ btnid
					+ "\" type=\"button\" value=\"…\" onclick=javascript:$select(this,\""
					+ url + "\") />";
		}
		return getComp("<input type=\"text\" ", " onfocus=\"this.blur()\" />"
				+ selectbtn, id, value, style, classname, readonly, "");
	}

	/**
	 * 获取多彩文档
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param url
	 * @return
	 */
	public static String getRichtext(String id, String value, String style,
			String classname, boolean readonly, String url) {
		return getComp("<a href=\"" + url + "\" ", " >" + value + "</a>", id,
				"", style, classname, readonly, "");
	}

	/**
	 * 获取iframe
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param url
	 * @return
	 */
	public static String getIframe(String id, String value, String style,
			String classname, boolean readonly, String url) {
		if (StringUtils.isNotEmpty(url)) {
			value = url;
		}
		try {
			if (StringUtils.isNotEmpty(value)) {
				if (value.indexOf("&") < 0)
					value = new String(new BASE64Decoder().decodeBuffer(value));

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hiddenInput = getHiddenInput(id, value);
		return hiddenInput
				+ getComp(
						"<iframe width=\"100%\" height=\"450px\" frameborder=\"0\" scrolling=\"auto\" src=\""
								+ value + "\" ", " ></iframe>", "", "", style,
						classname, readonly, "");
	}

	public static String getWysiwygUI(String id, String value, String style,
			String classname, boolean readonly, String extvalue) {
		StringBuffer tinymcescript = new StringBuffer("");
		// 所见即所得编辑器
		String textareaClassId = DyFormBuildHtml.uuid();
		tinymcescript.append("$(\"textarea." + textareaClassId
				+ "\").tinymce({");
		tinymcescript.append("  script_url: \"" + DyFormBuildHtml.projectname
				+ "/tiny_mce/tiny_mce.js\",");
		tinymcescript.append("  language: \"zh-cn\",");
		tinymcescript.append("  theme: \"advanced\",");
		tinymcescript
				.append("  plugins: \"table,advimage,inlinepopups,preview,media,contextmenu,paste,fullscreen\",");
		tinymcescript
				.append("  theme_advanced_buttons1: \"code,fullscreen,preview,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,bullist,numlist,styleselect,formatselect,fontselect,fontsizeselect\",");
		tinymcescript
				.append("  theme_advanced_buttons2: \"undo,redo,|,link,unlink,anchor,|,sub,sup,|,forecolor,backcolor,image,media,tablecontrols\",");
		tinymcescript.append("  theme_advanced_buttons3: \"\",");
		tinymcescript.append("  theme_advanced_toolbar_location: \"top\",");
		tinymcescript.append("  theme_advanced_toolbar_align: \"left\",");
		tinymcescript.append("  content_css: \"css/content.css\",");
		tinymcescript
				.append("  template_external_list_url: \"lists/template_list.js\",");
		tinymcescript
				.append("  external_link_list_url: \"lists/link_list.js\",");
		tinymcescript
				.append("  external_image_list_url: \"lists/image_list.js\",");
		tinymcescript
				.append("  media_external_list_url: \"lists/media_list.js\",");
		tinymcescript.append("  relative_urls: false,");
		tinymcescript.append("  remove_script_host: true");
		tinymcescript
				.append(" ,onchange_callback:function(inst){$(\"#\"+this.id).siblings(\"#"
						+ id + "\").text(inst.getBody().innerHTML);}");

		tinymcescript.append(",setup:function(ed) {");
		tinymcescript
				.append("      ed.onKeyDown.add(function(ed, e) {$(\"#\"+this.id).siblings(\"#"
						+ id + "\").text(ed.getBody().innerHTML);");
		tinymcescript.append("})}");

		tinymcescript.append("});");

		String s = getTag("<script type=\"text/javascript\">$(function() {",
				"});</script>", tinymcescript.toString());
		String mess = "<div style=\"width: 100%;height: 10px;clear: both;overflow: hidden;\"></div><span id=\"contentMessagePosition"
				+ textareaClassId + "\"></span>";
		String hiddenInput = getComp("<textarea style=\"display:none\" ", " >"
				+ value + "</textarea>", id, "", "", "", false, "");
		return hiddenInput
				+ getComp(
						"<textarea ",
						" >" + value + "</textarea>",
						DyFormBuildHtml.uuid(),
						"",
						style,
						textareaClassId
								+ " {messagePosition: \"#contentMessagePosition"
								+ textareaClassId + "\"}", readonly,
						" rows=\"2\" cols=\"20\" " + extvalue) + mess + s;
	}

	/**
	 * 取得表格
	 * 
	 * @param id
	 * @param style
	 * @param classname
	 * @param border
	 * @return
	 */
	public static String getTable(String id, String value, String style,
			String classname, int border, String extvalue) {
		return getComp("<table ", " >" + value + "</table>", id, "", style,
				classname, false, extvalue + " border=\"" + border + "\" ");
	}

	/**
	 * 获取TR
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @return
	 */
	public static String getTr(String id, String value, String style,
			String classname, String extvalue) {
		return getComp("<tr ", " >" + value + "</tr>", id, "", style,
				classname, false, extvalue);
	}

	/**
	 * 获取TD
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @return
	 */
	public static String getTd(String id, String value, String style,
			String classname, String extvalue) {
		return getComp("<td ", " nowrap=\"nowrap\">" + value + "</td>", id, "",
				style, classname, false, extvalue);
	}

	/**
	 * 获取TH
	 * 
	 * @param id
	 * @param value
	 * @param style
	 * @param classname
	 * @return
	 */
	public static String getTh(String id, String value, String style,
			String classname, String extvalue) {
		return getComp("<th ", " nowrap=\"nowrap\">" + value + "</th>", id, "",
				style, classname, false, extvalue);
	}

	/**
	 * 获取比较符号
	 * 
	 * @param id
	 * @param style
	 * @param classname
	 * @param readonly
	 * @param selectedvalue
	 * @param extvalue
	 * @return
	 */
	public static String getOpe(String id, String value, String style,
			String classname, boolean readonly, String extvalue) {
		String selectedvalue = extvalue;
		return getSelectKV(id, value, style, classname, readonly,
				selectedvalue, "", false);
	}

	/**
	 * Ext border 布局
	 * 
	 * @param center
	 *            中间
	 * @param east
	 *            东部
	 * @param west
	 *            西部
	 * @param south
	 *            南部
	 * @param north
	 *            北部
	 * @return
	 */
	public static String getExtBorderViewport(String center, String east,
			String west, String south, String north) {
		String framename = "Frame" + DyFormBuildHtml.uuid();

		StringBuffer js = new StringBuffer();
		js.append("Ext.ns('" + framename + ".Layout');");
		js.append(framename + ".Layout.Viewport =  Ext.extend(Ext.Viewport, {");

		js.append("initComponent: function(){");
		js.append("var clientHeight = 0;");
		js
				.append("if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {");
		js.append("clientHeight = document.documentElement.clientHeight;");
		js
				.append("} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {");
		js.append("clientHeight = document.body.clientHeight;");
		js.append("}\n");

		js.append(" var config = {");
		js.append("collapsible:true,");
		js.append("layout:'border', ");
		js.append("autoWidth:true,");
		js.append("border:false,");
		js.append("cls:'',");
		js.append("items:[{" + center + "},{" + east + "},{" + west + "},{"
				+ north + "},{" + south + "}]");
		js.append("}\n");

		js.append("Ext.apply(this, Ext.apply(this.initialConfig, config));\n");
		js
				.append(framename
						+ ".Layout.Viewport.superclass.initComponent.apply(this, arguments);\n");
		js.append("}");
		js.append("});\n");
		js.append("\nExt.onReady(function (){\n");
		js.append("	var viewport = new " + framename + ".Layout.Viewport();\n");
		js.append("})\n");

		return getTag("<script type=\"text/javascript\">", "</script>\n", js
				.toString());
	}

	/**
	 * 获取ext panel 属性
	 * 
	 * @param id
	 * @param layout
	 * @param xtype
	 * @param items
	 * @param height
	 * @return
	 */
	public static String getExtPanelAttr(String id, String layout,
			String xtype, String items, String height, String ext) {
		if (items == null || "".equals(items))
			return "";

		StringBuffer sb = new StringBuffer();
		sb.append("id:'" + id + "_id',");
		if (xtype != null && !"".equals(xtype)) {
			sb.append("xtype:'" + xtype + "',");
		}
		if (layout != null && !"".equals(layout)) {
			sb.append("region:'" + layout + "',");
		}
		sb.append("border:true,");
		sb.append("hideBorders:true,");
		if (items != null && !"".equals(items)) {
			sb.append("items:[" + items + "],");
		}
		if (height != null && !"".equals(height)) {
			sb.append("height : " + height + ",");
		}
		if (ext != null && !"".equals(ext)) {
			sb.append(ext + ",");
		}
		sb.append("autoScroll:true");
		return sb.toString();
	}

	/**
	 * 获取ext panel 属性
	 * 
	 * @param id
	 * @param layout
	 * @param xtype
	 * @param contentEl
	 * @param height
	 * @return
	 */
	public static String getExtPanelAttr2(String id, String layout,
			String xtype, String contentEl, String height, String ext) {
		if (contentEl == null || "".equals(contentEl))
			return "";

		StringBuffer sb = new StringBuffer();
		sb.append("id:'" + id + "_id',");
		if (xtype != null && !"".equals(xtype)) {
			sb.append("xtype:'" + xtype + "',");
		}
		if (layout != null && !"".equals(layout)) {
			sb.append("region:'" + layout + "',");
		}
		sb.append("border:false,");
		sb.append("hideBorders:true,");
		if (contentEl != null && !"".equals(contentEl)) {
			sb.append("contentEl:'" + contentEl + "',");
		}
		if (height != null && !"".equals(height)) {
			sb.append("height : " + height + ",");
		}
		if (ext != null && !"".equals(ext)) {
			sb.append(ext + ",");
		}
		sb.append("autoScroll:true");
		return sb.toString();
	}

	/**
	 * 获取Ext panel
	 * 
	 * @param id
	 * @param title
	 * @param items
	 * @param contentEl
	 * @return
	 */
	public static String getExtPanel(String id, String title, String items,
			String contentEl, String xtype, String ext) {
		if (id == null || "".equals(id))
			return "";

		StringBuffer js = new StringBuffer();
		js.append("var $" + id + " = new Ext.Panel({");
		js.append("id:'" + id + "_$id'");
		if (xtype != null && !"".equals(xtype)) {
			js.append(",xtype:'" + xtype + "'");
		}
		if (title != null && !"".equals(title)) {
			js.append(",title:'" + title + "'");
			js.append(",collapsible:true");
		}
		if (items != null && !"".equals(items)) {
			js.append(",items:[" + items + "]");
		}
		if (contentEl != null && !"".equals(contentEl)) {
			js.append(",contentEl:'" + contentEl + "_form'");
		}
		if (ext != null || !"".equals(ext)) {
			js.append(ext);
		}
		js.append("});");

		return getTag("<script type=\"text/javascript\">", "</script>\n", js
				.toString());
	}

	/**
	 * 获取ext gridpanel
	 * <P>
	 * data:<BR>
	 * ['1','name1','descn1'], ['2','name2','descn2'], ['3','name3','descn3'],
	 * ['4','name4','descn4'], ['5','name3','descn5'] <BR>
	 * fields:<BR>
	 * {name: 'id'}, {name: 'name'}, {name: 'descn'} <BR>
	 * columns:<BR>
	 * {header:'编号',dataIndex:'id',sortable:true},
	 * {header:'名称',dataIndex:'name',renderer:$render},
	 * {header:'描述',dataIndex:'descn'}
	 * </p>
	 * 
	 * @param id
	 * @param data
	 * @param fields
	 * @param columns
	 * @return
	 */
	public static String getExtGridPanel(String id, String data, String fields,
			String columns) {
		StringBuffer js = new StringBuffer();

		String framename = id;
		js.append("Ext.ns('" + framename + ".Data');\n");
		js.append(framename
				+ ".Data.FrameGrid  =  Ext.extend(Ext.grid.EditorGridPanel,{\n");

		js.append("		 initComponent: function() {\n");

		js.append("		    var index= new Ext.grid.RowNumberer();\n");
		js.append("		    var cb = new Ext.grid.CheckboxSelectionModel({\n");
		js.append("				singleSelect: false\n");
		js.append("		   	});\n");

		js.append("			var config = {\n");
		js.append("			    sm : cb,\n");

		js.append("			    border:false,\n");
		// js.append("plugins: new Ext.grid.GridSummary(),\n");
		// js.append(" enableHdMenu:false,\n");

		js.append("store: new Ext.data.Store({\n");
		js.append("	proxy: new Ext.data.MemoryProxy([" + data + "]),\n");
		js.append("	reader:new Ext.data.ArrayReader({},[\n");
		js.append(fields);
		js.append(" ])\n");
		js.append("}),\n");

		js.append("				columns:[\n");
		js.append("				        index,\n");
		js.append("						cb,\n");
		js.append("						" + columns + "\n");
		js.append("				],\n");
		js
				.append("				viewConfig:{forceFit:false,autoScroll:true,width:800},\n");
		js.append("				loadMask:true\n");
		js.append("		   }\n");

		js
				.append("		  Ext.apply(this, Ext.apply(this.initialConfig, config));\n");
		js
				.append("		  "
						+ framename
						+ ".Data.FrameGrid.superclass.initComponent.apply(this, arguments);\n");
		js.append("		  this.on({\n");
		js
				.append("			 beforeshow : {scope:this, single:true, fn:function() {\n");
		js.append("				   this.store.load();\n");
		js.append("			 }}\n");
		js.append("		  });\n");
		js.append("	  }\n");
		js.append("  });\n");
		// js.append(" Ext.reg('" + framename + "Grid', " + framename
		// + ".Data.FrameGrid);\n");
		return getTag("<script type=\"text/javascript\">", "</script>\n", js
				.toString());
	}

	/**
	 * 获取ext gridpanel
	 * <P>
	 * data:<BR>
	 * ['1','name1','descn1'], ['2','name2','descn2'], ['3','name3','descn3'],
	 * ['4','name4','descn4'], ['5','name3','descn5'] <BR>
	 * fields:<BR>
	 * {name: 'id'}, {name: 'name'}, {name: 'descn'} <BR>
	 * columns:<BR>
	 * {header:'编号',dataIndex:'id',sortable:true},
	 * {header:'名称',dataIndex:'name',renderer:$render},
	 * {header:'描述',dataIndex:'descn'}
	 * </p>
	 * 
	 * @param id
	 * @param data
	 * @param fields
	 * @param columns
	 * @return
	 */
	public static String getExtGridPanel2(String id, String data,
			String fields, String columns) {
		StringBuffer js = new StringBuffer();
		js.append("var $" + id + " = new Ext.grid.EditorGridPanel({ \n");
		js.append("viewConfig:{formcode:'" + id.replace("Grid", "")
				+ "',forceFit:false,autoHeight:true,autoScroll:true,width:800,onLayout : function(vw, vh){\n");
		js.append("	    var g = this.grid;\n");
		js.append("	    \n");
		js.append("	        /**原本Ext设置的是visible，不会出现滚动条*/\n");
		js.append("	        this.scroller.dom.style.overflow = 'auto';\n");
		js.append("	        /**另外还要更新表头宽度，以便滚动位置同步*/\n");
		js.append("	        if(this.innerHd){\n");
		js.append("	            this.innerHd.style.width = (vw)+'px';\n");
		js.append("	        }\n");
		js.append("	    \n");
		js.append("	}\n");
		js.append("},\n");
		js
				.append("autoHeight:true,enableColumnMove:false,enableColumnHide:true,enableHdMenu:true,\n");
		js
				.append("sm:new Ext.grid.CheckboxSelectionModel({singleSelect: false}),\n");

		// js.append("plugins: new Ext.grid.GridSummary(),\n");
		// js.append("stripeRows: true,\n");

		js.append("columns:[" + columns + "],\n");
		js.append("id:'" + id + "',\n");
		js.append("store:\n");
		js.append("new Ext.data.SimpleStore({data:[" + data + "],fields:["
				+ fields + "]})");
		js.append("});\n");
		return getTag("<script type=\"text/javascript\">", "</script>\n", js
				.toString());
	}

	/**
	 * 获取选项卡
	 * 
	 * @param formname
	 * @param formlist
	 * @return
	 */
	public static String getTabs(List<String> formname, List<String> formlist) {
		StringBuffer tabshtml = new StringBuffer();
		if (formlist.size() > 0) {
			String uid = DyFormBuildHtml.uuid();
			tabshtml.append("<div id='tabs" + uid + "' >");

			tabshtml.append("<ul>");
			for (int i = 0; i < formname.size(); i++) {
				tabshtml.append("<li>");
				tabshtml.append("<a href='#tabs" + uid + "-" + i + "' >"
						+ formname.get(i) + "</a>");
				tabshtml.append("</li>");
			}
			tabshtml.append("</ul>");

			for (int i = 0; i < formlist.size(); i++) {
				tabshtml.append("<div id='tabs" + uid + "-" + i + "'>");
				tabshtml.append(formlist.get(i));
				tabshtml.append("</div>");
			}

			tabshtml.append("</div>");

			String functionscript = "$('#tabs" + uid + "').tabs();$('#tabs"
					+ uid + "').tabs('select', 'tabs" + uid + "-0');";
			tabshtml.append(getTag(
					"<script type=\"text/javascript\">$(function() {",
					"});</script>\n", functionscript));
		}

		return tabshtml.toString();
	}

	/**
	 * 获取Ext treepanel
	 * 
	 * @param id
	 * @param items
	 * @return
	 */
	public static String getExtTreepanel(String id, String items) {
		StringBuffer js = new StringBuffer();
		js.append("var $" + id + " = new Ext.TabPanel({");
		js
				.append("id:\'"
						+ id
						+ "\',activeTab:0,autoTabs:true,split:true,animScroll:true,enableTabScroll:true,deferredRender:false,autoHeight:true,autoShow  :true");
		js.append("    ,items: [" + items + "]");
		js.append("});");
		return js.toString();
	}

	/**
	 * 获取Ext选项卡
	 * 
	 * @param formname
	 * @param formlist
	 * @return
	 */
	public static String getExtTabs(String uid, List<String> formname,
			List<Map> formlist) {
		StringBuffer tabshtml = new StringBuffer();
		StringBuffer html = new StringBuffer();
		if (formlist.size() > 0) {

			String split = "";
			StringBuffer items = new StringBuffer();
			for (int i = 0; i < formlist.size(); i++) {
				Map map = (Map) formlist.get(i);
				String form = (String) map.get("form");
				String id = (String) map.get("id");
				String extjs = (String) map.get("extjs");
				String mode = (String) map.get("mode");
				html.append(form + extjs);

				// if ("mode7".equals(mode)) {
				items
						.append(split
								+ "{listeners:{show:function(tab){Ext.getCmp('"
								+ id
								+ "Grid').store.sort('sid');} },autoHeight:true,items:["
								+

								"$" + id + ""

								+ "], title:'" + formname.get(i) + "'}");

				// items.append(split + "{autoHeight:true,html:'"+i+"', title:'"
				// + formname.get(i) + "'}");

				split = ",";
				// } else if ("mode8".equals(mode)) {
				// items.append(split + "{autoHeight:true,contentEl:'" + "$"
				// + id + "', title:'" + formname.get(i) + "'}");
				// split = ",";
				// }
			}
			if (items.length() > 0) {
				tabshtml.append(getExtTreepanel(uid, items.toString()));
			}
		}
		return html.toString()
				+ "\n"
				+ getTag("<script type=\"text/javascript\">", "</script>\n",
						tabshtml.toString());
	}

	/**
	 * 获取手风琴+树<BR>
	 * <P>
	 * JSON数据格式: [{ "id":1, "text":"Folder1", "iconCls":"icon-ok", "children":[{
	 * "id":2, "text":"File1", "checked":true },{ "id":3, "text":"Folder2",
	 * "state":"open", "children":[{ "id":4, "text":"File3", "attributes":{
	 * "p1":"value1", "p2":"value2" }, "checked":true, "iconCls":"icon-reload"
	 * },{ "id": 8, "text":"Async Folder", "state":"closed" }] }] },{
	 * "text":"Languages", "state":"closed", "children":[{ "id":"j1",
	 * "text":"Java" },{ "id":"j2", "text":"C#" }] }]
	 * 
	 * </P>
	 * 
	 * @param jsonList
	 * @return
	 */
	public static String getEasyuiAccordionTree(
			List<Map<String, String>> jsonList) {
		StringBuffer html = new StringBuffer();
		StringBuffer js = new StringBuffer();

		if (jsonList.size() > 0) {

			html
					.append("<div class='easyui-accordion' fit='true' border='false'>");
			js.append("<script type=\"text/javascript\">$(function() {");
			int i = 0;
			for (Map<String, String> map : jsonList) {
				html.append("<div id='accord" + i + "' title='"
						+ map.get("title")
						+ "' selected='false' class='LBody'>");
				html.append("<ul id='tt" + i + "'></ul>");
				html.append("</div>");

				js.append("$('#tt" + i + "').tree({");
				js.append("onClick:function(node){");
				// js.append("alert(node.text+':'+node.attributes.linkurl);");
				js
						.append("	if(node.attributes.linkurl!=null) linkUrl(node.text,node.attributes.linkurl);");
				js.append("},");
				js.append("animate: true,");
				// js.append("checkbox: true,");
				js.append("data: ");
				js.append(map.get("json"));

				js.append("});");
				i++;
			}

			html.append("</div>");
			js.append("});</script>\n");

		}
		return html.toString() + js.toString();
	}

	/**
	 * 获取click事件脚本
	 * 
	 * @param obj
	 * @param init_event
	 * @param focus_event
	 * @param losefocus_event
	 * @return
	 */
	public static String getClickEventScript(String obj, String click_event) {
		StringBuffer script = new StringBuffer();
		if (StringUtils.isNotEmpty(click_event)) {
			String click_script = obj + ".click(function(){" + click_event
					+ "});";
			script.append(click_script);
		}
		return getJqueryFunctionScript(script.toString());
	}

	/**
	 * 获取load事件脚本
	 * 
	 * @param obj
	 * @param load_event
	 * @param focus_event
	 * @param losefocus_event
	 * @return
	 */
	public static String getLoadEventScript(String obj, String load_event) {
		StringBuffer script = new StringBuffer();
		if (StringUtils.isNotEmpty(load_event)) {
			String init_script = obj + ".load(function(){" + load_event + "});";
			script.append(init_script);
		}
		return getJqueryFunctionScript(script.toString());
	}

	/**
	 * 获取focus事件脚本
	 * 
	 * @param obj
	 * @param init_event
	 * @param focus_event
	 * @param losefocus_event
	 * @return
	 */
	public static String getFocusEventScript(String obj, String focus_event) {
		StringBuffer script = new StringBuffer();
		if (StringUtils.isNotEmpty(focus_event)) {
			String init_script = obj + ".focus(function(){" + focus_event
					+ "});";
			script.append(init_script);
		}
		return getJqueryFunctionScript(script.toString());
	}

	/**
	 * 获取blur事件脚本
	 * 
	 * @param obj
	 * @param init_event
	 * @param focus_event
	 * @param losefocus_event
	 * @return
	 */
	public static String getLosefocusEventScript(String obj,
			String losefocus_event) {
		StringBuffer script = new StringBuffer();
		if (StringUtils.isNotEmpty(losefocus_event)) {
			String init_script = obj + ".blur(function(){" + losefocus_event
					+ "});";
			script.append(init_script);
		}
		return getJqueryFunctionScript(script.toString());
	}

	/**
	 * 获取事件脚本
	 * 
	 * @param obj
	 * @param init_event
	 * @param focus_event
	 * @param losefocus_event
	 * @return
	 */
	public static String getEventScript(String obj, String init_event,
			String focus_event, String losefocus_event, String change_event) {
		StringBuffer script = new StringBuffer();
		if (StringUtils.isNotEmpty(focus_event)) {
			String _script = obj + ".focus(function(){" + focus_event + "});";
			script.append(_script);
		}
		if (StringUtils.isNotEmpty(losefocus_event)) {
			String _script = obj + ".blur(function(){" + losefocus_event
					+ "});";
			script.append(_script);
		}
		if (StringUtils.isNotEmpty(change_event)) {
			String _script = obj + ".change(function(){" + change_event + "});";
			script.append(_script);
		}
		if (StringUtils.isNotEmpty(init_event)) {
			String _script = init_event;
			_script = _script.replace("$(object)", obj);
			script.append(_script);
		}

		return getJqueryFunctionScript(script.toString());
	}

	/**
	 * 获取事件脚本<BR>
	 * 解决子表单动态生成 无法绑定事件问题
	 * 很多时候我们对一个对象绑定一个事件的时候老是发现绑定不成功，这时你可以查查看这个对像是不是你通过Jquery动态生成的。<BR>
	 * jQuery为我们提供了一个函数来解决这个问题，它就是.live(),它可以给所有元素绑定事件，不论是已有的，还是将来生成的<BR>
	 * 官方文档:说明live不支持 focus blur事件 以focusin,focusout替代<BR>
	 * As of jQuery 1.4.1 even focus and blur work with live (mapping to the
	 * more appropriate, bubbling, events focusin and focusout).
	 * 
	 * 
	 * @param obj
	 * @param init_event
	 * @param focus_event
	 * @param losefocus_event
	 * @return
	 */
	public static String getLiveEventScript(String obj, String init_event,
			String focus_event, String losefocus_event, String change_event) {
		StringBuffer script = new StringBuffer();
		if (StringUtils.isNotEmpty(focus_event)) {
			String _script = obj + ".live(\"focusout\",function(){"
					+ focus_event + "});";
			script.append(_script);
		}
		if (StringUtils.isNotEmpty(losefocus_event)) {
			String _script = obj + ".live(\"focusout\",function(){"
					+ losefocus_event + "});";
			script.append(_script);
		}
		if (StringUtils.isNotEmpty(change_event)) {
			String _script = obj + ".live(\"change\",function(){"
					+ change_event + "});";
			script.append(_script);
		}
		if (StringUtils.isNotEmpty(init_event)) {
			String _script = init_event;
			_script = _script.replace("$(object)", obj);
			script.append(_script);
		}

		return getJqueryFunctionScript(script.toString());
	}

	/**
	 * 
	 * @param script
	 * @return
	 */
	public static String getJqueryFunctionScript(String script) {
		if (StringUtils.isEmpty(script)) {
			return "";
		} else {
			script = StringUtils.replace(script, "\"", "'");

			// 语法解析
			script = parseScript(script);

			// 转义
			script = StringUtils.replace(script, "$$", "#");
			script = StringUtils.replace(script, "$(form)",
					"$(this).parents('table')");
			script = StringUtils.replace(script, "$(currform)",
					"$(this).parent().parent()");

		}

		return getTag("<script type=\"text/javascript\">$(function() {",
				"});</script>\n", script);
	}

	public static String getSummaryScript(String summarytype, String formcode,
			String columnid) {
		if (StringUtils.isEmpty(summarytype))
			return "";

		StringBuffer js = new StringBuffer();

		String uuid = DyFormBuildHtml.uuid();
		/** 访问子表单每行指定列字段的值 */
		js.append("var $value" + uuid + "=0;var $sum" + uuid + "=0;var $total"
				+ uuid + "=0;var $tmp" + uuid + "=null;");
		js.append("$('table#" + formcode + "').find('#" + columnid
				+ "').each(function(){");
		String floatstr = StringUtils.substringBetween(summarytype, "$float(",
				")");
		if (floatstr == null && StringUtils.isBlank(floatstr)) {
			floatstr = "2";
		} else {
			floatstr = floatstr.trim();
		}

		if (summarytype.contains("sum")) {// 总和
			js.append("var c=parseFloat($(this).val());");
			js.append("if(isNaN(c)){");
			js.append("c=0;");
			js.append("}");
			js.append("$sum" + uuid + "+=c;");
			js.append("$value" + uuid + "=$sum" + uuid + ";");
			js.append("$value" + uuid + "=$value" + uuid + ".toFixed("
					+ floatstr + ");");
		} else if (summarytype.contains("average")) {// 平均
			js.append("var c=parseFloat($(this).val());");
			js.append("if(isNaN(c)){");
			js.append("c=0;");
			js.append("}else{");
			js.append("$total" + uuid + "++;");
			js.append("}");
			js.append("$sum" + uuid + "+=c;");
			js
					.append("$value" + uuid + "=$sum" + uuid + "/$total" + uuid
							+ ";");
			js.append("$value" + uuid + "=$value" + uuid + ".toFixed("
					+ floatstr + ");");
		} else if (summarytype.contains("max")) {// 最大值
			js.append("var c=parseFloat($(this).val());");
			js.append("if(isNaN(c)){");
			js.append("c=null;");
			js.append("}");

			js.append("if (c==null || $tmp" + uuid + "==null){");
			js.append("$tmp" + uuid + "=c;");
			js.append("}else{");
			js.append("	if (c>$tmp" + uuid + ") $tmp" + uuid + "=c;");
			js.append("}");

			js.append("$value" + uuid + "=$tmp" + uuid + ";");
		} else if (summarytype.contains("min")) {// 最小值
			js.append("var c=parseFloat($(this).val());");
			js.append("if(isNaN(c)){");
			js.append("c=null;");
			js.append("}");

			js.append("if (c==null || $tmp" + uuid + "==null){");
			js.append("$tmp" + uuid + "=c;");
			js.append("}else{");
			js.append("	if (c<$tmp" + uuid + ") $tmp" + uuid + "=c;");
			js.append("}");

			js.append("$value" + uuid + "=$tmp" + uuid + ";");
		} else if (summarytype.contains("count")) {// 计数
			js.append("var c=$(this).val();");
			js.append("if(c==null || c=='' || c=='undefiend'){");
			js.append("}else{");
			js.append("$total" + uuid + "++;");
			js.append("}");
			js.append("$value" + uuid + "=$total" + uuid + ";");
		}

		js.append("});");
		js.append("if ($('table#" + formcode + "').find('#" + columnid
				+ "_summary')) $('table#" + formcode + "').find('#" + columnid
				+ "_summary').html($value" + uuid + ");");

		return js.toString();
	}

	/**
	 * 解析语法
	 * 
	 * @param script
	 * @return
	 */
	public static String parseScript(String script) {
		script = todoscript(script, "sum"); // 求和
		script = todoscript(script, "avg"); // 平均值
		script = todoscript(script, "calculate"); // 自定义表达式
		return script;
	}

	protected static String todoscript(String script, String function) {
		if ("sum".equals(function)) {// 求和

		} else if ("avg".equals(function)) {// 平均值

		} else if ("calculate".equals(function)) {// 自定义表达式

		} else {
			return script;
		}

		if (script.contains("$" + function + "")
				&& script.contains(".$end" + function + ";")) {
			StringBuffer scriptbuf = new StringBuffer();
			int laststartindex = script.lastIndexOf("$" + function + "");
			int lastendindex = script.lastIndexOf(".$end" + function + ";");
			String v = script.substring(laststartindex, lastendindex) + ".$end"
					+ function + ";";

			String sumformstr = StringUtils.substringBetween(v, "$" + function
					+ "{", "}");
			String tostr = StringUtils.substringBetween(v, "$to(", ")");
			String jumptostr = StringUtils.substringBetween(v, "$jumpto{", "}");
			String columnstr = StringUtils.substringBetween(v, "$column(", ")");
			String floatstr = StringUtils.substringBetween(v, "$float(", ")");
			String expressionstr = StringUtils.substringBetween(v,
					"$expression{", "}");

			if (floatstr == null && StringUtils.isBlank(floatstr)) {
				floatstr = "2";
			} else {
				floatstr = floatstr.trim();
			}
			if (StringUtils.isNotEmpty(sumformstr)) {
				sumformstr = sumformstr.trim();
			}
			if (StringUtils.isNotEmpty(tostr)) {
				tostr = tostr.trim();
			}
			if (StringUtils.isNotEmpty(columnstr)) {
				columnstr = columnstr.trim();
			}
			if (StringUtils.isNotEmpty(jumptostr)) {
				jumptostr = jumptostr.trim();
			}
			if (StringUtils.isNotEmpty(expressionstr)) {
				expressionstr = expressionstr.trim();
			}

			String uid = "$" + DyFormBuildHtml.uuid();
			scriptbuf.append("var " + uid + " = 0;");
			scriptbuf.append(sumformstr + ".each(function(){");

			String[] columns = null;
			int columnlength = 1;
			if (StringUtils.isNotEmpty(columnstr)) {
				columns = columnstr.split(",");
				columnlength = columns.length;
			}
			if (columns != null) {
				for (int i = 0; i < columns.length; i++) {
					String x_ = columns[i];
					x_ = x_.trim();
					scriptbuf.append("var " + x_ + " = $(this).find('#" + x_
							+ "').val();");
					scriptbuf.append("" + x_ + "=parseFloat(" + x_ + ");");
					scriptbuf.append("if(isNaN(" + x_ + ")){");
					scriptbuf.append("" + x_ + "=0;");
					scriptbuf.append("}");
				}
				if (StringUtils.isNotEmpty(expressionstr)
						&& StringUtils.isNotBlank(expressionstr)) {// 使用自定义表达式
					scriptbuf.append(uid + "=" + expressionstr + ";");
				} else {// 根据function生成表达式
					if ("sum".equals(function)) {
						String split = "";
						StringBuffer buf = new StringBuffer();
						for (int i = 0; i < columns.length; i++) {
							String c = columns[i];
							c = c.trim();
							buf.append(split + c);
							split = "+";
						}
						scriptbuf.append(uid + "=parseFloat(" + buf.toString()
								+ ");");
					} else if ("avg".equals(function)) {
						String split = "";
						StringBuffer buf = new StringBuffer();
						for (int i = 0; i < columns.length; i++) {
							String c = columns[i];
							c = c.trim();
							buf.append(split + c);
							split = "+";
						}
						scriptbuf.append(uid + "=parseFloat(" + buf.toString()
								+ ")/" + columns.length + ";");
					}
				}
			}
			scriptbuf.append("});");
			scriptbuf.append(uid + "=parseFloat(" + uid + ")" + ".toFixed("
					+ floatstr + ");");
			scriptbuf.append("if (" + uid + "==parseInt(" + uid + ")){" + uid
					+ "=parseInt(" + uid + ");}");

			// 传值给当前表单下的一个或多个对象
			if (StringUtils.isNotEmpty(tostr) && StringUtils.isNotBlank(tostr)) {
				String[] tostr_ = tostr.split(",");
				for (int i = 0; i < tostr_.length; i++) {
					scriptbuf.append(sumformstr + ".find('#" + tostr_[i]
							+ "').each(function(){");
					scriptbuf.append("$(this).val(" + uid + ")");
					scriptbuf.append("});");
				}
			}
			// 跳转传值给需要的一个或多个对象
			if (StringUtils.isNotEmpty(jumptostr)
					&& StringUtils.isNotBlank(jumptostr)) {
				String[] jumptostr_ = jumptostr.split(",");
				for (int i = 0; i < jumptostr_.length; i++) {
					scriptbuf.append(jumptostr_[i] + ".val(" + uid + ");");
				}
			}

			String newValue = StringUtils.replace(script, v, scriptbuf
					.toString(), 1);
			return todoscript(newValue, function);
		}
		return script;
	}

	/**
	 * 获取分页脚本
	 * 
	 * @param posturl
	 * @param pagesize
	 * @return
	 */
	@Deprecated
	public static String getPaginatorScript(String posturl, int from,
			int pagesize) {
		StringBuffer html = new StringBuffer();
		html
				.append("<div id=\"paginator\" class=\"pagination\" style=\"margin-top:20px;margin-bottom:80px\"></div>");
		html
				.append("<div style=\"clear:both;\"> <div id=\"paginator_content\"></div> </div>");

		StringBuffer script = new StringBuffer();
		script.append("var options = {postUrl:\"" + posturl + "\"");
		script.append(",itemsPerPage: " + pagesize);
		script.append(",elementModel: []");
		script.append(",showPrev: true");
		script.append(",showNext: true");
		script.append(",showPageXofY : true");
		script.append(",num_display_entries:10");
		script.append(",current_page:" + from);
		script.append(",num_edge_entries:0");
		script.append(",link_to:\"#\"");
		script.append(",prev_text:\"上一页\"");
		script.append(",next_text:\"下一页\"");
		script.append(",ellipse_text:\"...\"");
		script.append(",prev_show_always:true");
		script.append(",next_show_always:true");
		script.append(",page_x_of_y:true");
		script.append(",xofy_left:true");
		script.append(",xofy_right:false");
		script.append(",callback:function(){return false;}");
		script.append("}; ");
		script.append("$(\"#paginator\").paginator(options);");
		return html.toString()
				+ getTag("<script type=\"text/javascript\">$(function() {",
						"});</script>", script.toString());
	}

	public static final String _CHECK_NAME = "_checkbox_name_";

	public static String getSelectOrUnselectAll(String id) {
		StringBuffer script = new StringBuffer();
		script.append("if ($(this).attr(\"checked\") == true) {");
		script.append("$(\"#" + id + " input[type=checkbox][name="
				+ _CHECK_NAME + "]\").each(function() {");
		script.append("$(this).attr(\"checked\", true); ");
		script.append("}); ");
		script.append("} else {");
		script.append("$(\"#" + id + " input[type=checkbox][name="
				+ _CHECK_NAME + "]\").each(function() {");
		script.append("$(this).attr(\"checked\", false); ");
		script.append("}); ");
		script.append("} ");
		return script.toString();
	}

	public static String deleteRow(String tableid, String onclickAddFunctionname) {
		StringBuffer script = new StringBuffer();
		script.append("$('#" + tableid + "').find('tr').each(function(){");
		script.append("	if ($(this).attr('id')!=''){");
		script.append("	var trid = $(this).attr('id');");
		script.append("		$(this).find('td').each(function(){");
		script.append("			$(this).find('input').each(function(){");
		script.append("				if ($(this).attr('id')=='" + _CHECK_NAME + "'){");
		script.append("			if ($(this).attr('checked') == true){");
		// script.append(" alert(trid);");
		script.append("					$('#'+trid).remove();");
		script.append("			}");
		script.append("		}");
		script.append("	});");
		script.append("	});");
		script.append("	}");
		script.append("});");

		// 若无记录 添加新记录
		script.append("if ($('#" + tableid + " tr').size()==2){");
		script.append(onclickAddFunctionname + "();");
		script.append("}");

		return script.toString();
	}

	public static void main(String[] args) {

		String[][] x = DyFormConsoleIfc._HTML_LIST;
		for (int i = 0; i < x.length; i++) {
			String[] y = x[i];
			for (int j = 0; j < y.length; j++) {
				System.out.println("[" + i + ":" + j + "]");
				System.out.println(x[i][j]);
			}
		}

		System.out.println(getText("test1", "value1", "width:100px;",
				"teststype", true, ""));

		System.out.println(getTextarea("test1", "value1", "width:100px;",
				"teststype", true, ""));

		System.out.println(getNumber("test1", "value1", "width:100px;",
				"teststype", true, ""));

		System.out.println(getDate("test1", "value1", "width:100px;",
				"teststype", true));

		System.out.println(getTime("test1", "value1", "width:100px;",
				"teststype", true));

		System.out.println(getDatetime("test1", "value1", "width:100px;",
				"teststype", true));

		System.out.println(getSelect("test1", "男", "width:100px;", "teststype",
				true, "男,女", ""));

		System.out.println(getSelectKV("test1", "1", "width:100px;",
				"teststype", true, "1-男,2-女", ""));

		System.out.println(getCheckbox("test1", "1", "width:100px;",
				"teststype", true));

		System.out.println(getSelect_("test1", "value1", "width:100px;",
				"teststype", true, "http://www.baidu.com/", ""));

		System.out.println(getRichtext("test1", "value1", "width:100px;",
				"teststype", true, "http://www.baidu.com/"));

		System.out.println(getSelectOrUnselectAll("xxx"));

		String script = "$sum{$(currform)}.$column(column1,column2,column3).$to(column4).$float(2).$endsum;";
		script += " ggg fdfaf  dfsaf " + script;
		System.out.println(script);

		System.out.println("脚本:" + getJqueryFunctionScript(script));
		System.out.println("转换脚本:" + parseScript(script));

	}

}
