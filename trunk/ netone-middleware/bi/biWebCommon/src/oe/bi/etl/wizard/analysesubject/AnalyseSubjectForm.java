//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.analysesubject;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AnalyseSubjectForm extends ActionForm {

	// --------------------------------------------------------- Instance
	// Variables
	// All.jsp
	public String parentid;

	public String pagepath;

	// Zero.jsp
	public String name;

	public String naturalname;

	// BeforeFirst.jsp
	public String timetype;

	public String selpoint1;// 时间粒度

	public String timelevelinfo;

	public String selpoint1Text;

	// First.jsp
	public String preps;

	public String selectedDimValue;

	public String levelname;

	public String dimResultValue;

	public String dynamicDim;// 动态维度

	public String type;// 选择模式

	public String dimTreeList;// 类型选择

	public String choice;// 层次节点

	public String dimTreeDiv;// 维度树DIV

	public String dimResult;// 维度选择的结果

	// Step.jsp
	public String tgids;

	public String tgnames;

	public String seltgvalue;

	public String tgGroup;// 指标组

	public String tglistdiv;// 指标树DIV

	public String targetResult;// 指标选择的结果

	// Step1.jsp
	public String filtertype;// 过滤类型

	// Step2.jsp
	public String tgfiltSqlvalue;

	public String allchkid;

	public String alltop;

	public String alltopvalue;

	public String allalarm;

	public String alltxtalarm;

	public String fil_tgid;// 选择过滤条件

	public String filtcbxtop;// 最值过滤

	public String top;//

	public String topvalue;//

	public String r00;//

	public String filtcbxalarm;// 门限值

	public String alarm;//

	public String txtalarm;//

	public String filtcbxorder;// 排序

	public String order;//

	// Step3.jsp
	public String exterValue;

	public String timelevel;

	public String seldimvalue;

	public String selpoint2;// 间隔

	public String hour;// 时段

	public String grownum;// 步长

	public String selecttime1;// 起始时间

	public String selecttime2;// 结束时间

	public String timeResult;// 时间选择的结果

	public String unit;// 单位

	public String timeResults;//

	// Step4.jsp
	private String active;// 是否使用图表定制条件

	// Step5.jsp
	private String showactive;// 是否选择图表定制条件

	private String selcharttype;// 图表类型1

	private String seldatatype;// 图表类型2

	private String seldim;// 维度

	private String str;//

	private String seltg;// 指标

	private String tgstr;//

	private String multichart;// 多图显示

	private String maxvalue;// y轴最小值

	private String pngwidth;// 图片宽度

	private String showvalue;// 图片显示数值

	private String pictitle;// 图表标题

	private String piccolor;// 背景色

	private String xqingxie;// x轴倾斜

	// Done.jsp
	public String act;//

	public String timespecial;//

	public String sqlview;//

	public String allchkid2;

	public String allorder;

	public String tgfiltvalue;

	// Done2.jsp
	public String actcondition;//

	// Done3.jsp
	public String acturl;//

	// Finish.jsp
	public String lsh;

	public String tgothername;

	// --------------------------------------------------------- Methods

	public String getActurl() {
		return acturl;
	}

	public void setActurl(String acturl) {
		this.acturl = acturl;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getDimResult() {
		return dimResult;
	}

	public void setDimResult(String dimResult) {
		this.dimResult = dimResult;
	}

	public String getDimTreeDiv() {
		return dimTreeDiv;
	}

	public void setDimTreeDiv(String dimTreeDiv) {
		this.dimTreeDiv = dimTreeDiv;
	}

	public String getDimTreeList() {
		return dimTreeList;
	}

	public void setDimTreeList(String dimTreeList) {
		this.dimTreeList = dimTreeList;
	}

	public String getExterValue() {
		return exterValue;
	}

	public void setExterValue(String exterValue) {
		this.exterValue = exterValue;
	}

	public String getGrownum() {
		return grownum;
	}

	public void setGrownum(String grownum) {
		this.grownum = grownum;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getPreps() {
		return preps;
	}

	public void setPreps(String preps) {
		this.preps = preps;
	}

	public String getSeldimvalue() {
		return seldimvalue;
	}

	public void setSeldimvalue(String seldimvalue) {
		this.seldimvalue = seldimvalue;
	}

	public String getSelectedDimValue() {
		return selectedDimValue;
	}

	public void setSelectedDimValue(String selectedDimValue) {
		this.selectedDimValue = selectedDimValue;
	}

	public String getSelecttime1() {
		return selecttime1;
	}

	public void setSelecttime1(String selecttime1) {
		this.selecttime1 = selecttime1;
	}

	public String getSelecttime2() {
		return selecttime2;
	}

	public void setSelecttime2(String selecttime2) {
		this.selecttime2 = selecttime2;
	}

	public String getSelpoint1() {
		return selpoint1;
	}

	public void setSelpoint1(String selpoint1) {
		this.selpoint1 = selpoint1;
	}

	public String getSelpoint1Text() {
		return selpoint1Text;
	}

	public void setSelpoint1Text(String selpoint1Text) {
		this.selpoint1Text = selpoint1Text;
	}

	public String getSelpoint2() {
		return selpoint2;
	}

	public void setSelpoint2(String selpoint2) {
		this.selpoint2 = selpoint2;
	}

	public String getSeltgvalue() {
		return seltgvalue;
	}

	public void setSeltgvalue(String seltgvalue) {
		this.seltgvalue = seltgvalue;
	}

	public String getTargetResult() {
		return targetResult;
	}

	public void setTargetResult(String targetResult) {
		this.targetResult = targetResult;
	}

	public String getTgfiltSqlvalue() {
		return tgfiltSqlvalue;
	}

	public void setTgfiltSqlvalue(String tgfiltSqlvalue) {
		this.tgfiltSqlvalue = tgfiltSqlvalue;
	}

	public String getTgfiltvalue() {
		return tgfiltvalue;
	}

	public void setTgfiltvalue(String tgfiltvalue) {
		this.tgfiltvalue = tgfiltvalue;
	}

	public String getTgGroup() {
		return tgGroup;
	}

	public void setTgGroup(String tgGroup) {
		this.tgGroup = tgGroup;
	}

	public String getTgids() {
		return tgids;
	}

	public void setTgids(String tgids) {
		this.tgids = tgids;
	}

	public String getTglistdiv() {
		return tglistdiv;
	}

	public void setTglistdiv(String tglistdiv) {
		this.tglistdiv = tglistdiv;
	}

	public String getTgnames() {
		return tgnames;
	}

	public void setTgnames(String tgnames) {
		this.tgnames = tgnames;
	}

	public String getTimelevel() {
		return timelevel;
	}

	public void setTimelevel(String timelevel) {
		this.timelevel = timelevel;
	}

	public String getTimelevelinfo() {
		return timelevelinfo;
	}

	public void setTimelevelinfo(String timelevelinfo) {
		this.timelevelinfo = timelevelinfo;
	}

	public String getTimeResult() {
		return timeResult;
	}

	public void setTimeResult(String timeResult) {
		this.timeResult = timeResult;
	}

	public String getTimespecial() {
		return timespecial;
	}

	public void setTimespecial(String timespecial) {
		this.timespecial = timespecial;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDimResultValue() {
		return dimResultValue;
	}

	public void setDimResultValue(String dimResultValue) {
		this.dimResultValue = dimResultValue;
	}

	public String getTgothername() {
		return tgothername;
	}

	public void setTgothername(String tgothername) {
		this.tgothername = tgothername;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	public String getTimeResults() {
		return timeResults;
	}

	public void setTimeResults(String timeResults) {
		this.timeResults = timeResults;
	}

	public String getSqlview() {
		return sqlview;
	}

	public void setSqlview(String sqlview) {
		this.sqlview = sqlview;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMaxvalue() {
		return maxvalue;
	}

	public void setMaxvalue(String maxvalue) {
		this.maxvalue = maxvalue;
	}

	public String getMultichart() {
		return multichart;
	}

	public void setMultichart(String multichart) {
		this.multichart = multichart;
	}

	public String getPngwidth() {
		return pngwidth;
	}

	public void setPngwidth(String pngwidth) {
		this.pngwidth = pngwidth;
	}

	public String getSelcharttype() {
		return selcharttype;
	}

	public void setSelcharttype(String selcharttype) {
		this.selcharttype = selcharttype;
	}

	public String getSeldatatype() {
		return seldatatype;
	}

	public void setSeldatatype(String seldatatype) {
		this.seldatatype = seldatatype;
	}

	public String getSeldim() {
		return seldim;
	}

	public void setSeldim(String seldim) {
		this.seldim = seldim;
	}

	public String getSeltg() {
		return seltg;
	}

	public void setSeltg(String seltg) {
		this.seltg = seltg;
	}

	public String getShowvalue() {
		return showvalue;
	}

	public void setShowvalue(String showvalue) {
		this.showvalue = showvalue;
	}

	public String getTgstr() {
		return tgstr;
	}

	public void setTgstr(String tgstr) {
		this.tgstr = tgstr;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getShowactive() {
		return showactive;
	}

	public void setShowactive(String showactive) {
		this.showactive = showactive;
	}

	public String getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(String filtertype) {
		this.filtertype = filtertype;
	}

	public String getAllchkid() {
		return allchkid;
	}

	public void setAllchkid(String allchkid) {
		this.allchkid = allchkid;
	}

	public String getAlltop() {
		return alltop;
	}

	public void setAlltop(String alltop) {
		this.alltop = alltop;
	}

	public String getAlltopvalue() {
		return alltopvalue;
	}

	public void setAlltopvalue(String alltopvalue) {
		this.alltopvalue = alltopvalue;
	}

	public String getAllalarm() {
		return allalarm;
	}

	public void setAllalarm(String allalarm) {
		this.allalarm = allalarm;
	}

	public String getAllorder() {
		return allorder;
	}

	public void setAllorder(String allorder) {
		this.allorder = allorder;
	}

	public String getAlltxtalarm() {
		return alltxtalarm;
	}

	public void setAlltxtalarm(String alltxtalarm) {
		this.alltxtalarm = alltxtalarm;
	}

	public String getAllchkid2() {
		return allchkid2;
	}

	public void setAllchkid2(String allchkid2) {
		this.allchkid2 = allchkid2;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getFil_tgid() {
		return fil_tgid;
	}

	public void setFil_tgid(String fil_tgid) {
		this.fil_tgid = fil_tgid;
	}

	public String getFiltcbxalarm() {
		return filtcbxalarm;
	}

	public void setFiltcbxalarm(String filtcbxalarm) {
		this.filtcbxalarm = filtcbxalarm;
	}

	public String getFiltcbxorder() {
		return filtcbxorder;
	}

	public void setFiltcbxorder(String filtcbxorder) {
		this.filtcbxorder = filtcbxorder;
	}

	public String getFiltcbxtop() {
		return filtcbxtop;
	}

	public void setFiltcbxtop(String filtcbxtop) {
		this.filtcbxtop = filtcbxtop;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getR00() {
		return r00;
	}

	public void setR00(String r00) {
		this.r00 = r00;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getTopvalue() {
		return topvalue;
	}

	public void setTopvalue(String topvalue) {
		this.topvalue = topvalue;
	}

	public String getTxtalarm() {
		return txtalarm;
	}

	public void setTxtalarm(String txtalarm) {
		this.txtalarm = txtalarm;
	}

	public String getPictitle() {
		return pictitle;
	}

	public void setPictitle(String pictitle) {
		this.pictitle = pictitle;
	}

	public String getPiccolor() {
		return piccolor;
	}

	public void setPiccolor(String piccolor) {
		this.piccolor = piccolor;
	}

	public String getXqingxie() {
		return xqingxie;
	}

	public void setXqingxie(String xqingxie) {
		this.xqingxie = xqingxie;
	}

	public String getActcondition() {
		return actcondition;
	}

	public void setActcondition(String actcondition) {
		this.actcondition = actcondition;
	}

	public String getDynamicDim() {
		return dynamicDim;
	}

	public void setDynamicDim(String dynamicDim) {
		this.dynamicDim = dynamicDim;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
