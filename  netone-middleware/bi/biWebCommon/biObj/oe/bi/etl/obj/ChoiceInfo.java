package oe.bi.etl.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 交互分析中选择的信息
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class ChoiceInfo implements Serializable {

	private String lsh;

	// 中文名称
	private String name;

	// 名称
	private String naturalname;

	/** 业务数据模型 */
	private String dataModelid;

	/** 选择的维度信息DimensionElement */
	private List dimensionElement;

	/** 选择的指标信息TargetElement */
	private List targetElement;

	private String otherinfo;

	private String created;

	/** 维度动态值 */
	private boolean dynamicDim;

	/**
	 * 这个是针对 层次选择的,比如福州下的所有BTS的应用, 这里的level要选择bts的,但是业务数据确是在福州这个级别里
	 */
	private String xlevel;

	/** 指标组 */
	private String tggroup;

	/** 是否显示图表定制条件 */
	private String showactive;

	/** 是否使用图表定制条件 */
	private boolean active;

	/** 图表类型1,有5个值,verticalbar3D=立体柱图,verticalbarLinec=柱线图对比,verticalbarLine=柱线图,line=线图,pie=饼图 */
	private String selcharttype;

	/** 图表类型2,有3个值,default=默认统计图,ne-time=网元-时间统计图,time-ne=时间-网元统计图 */
	private String seldatatype;

	/** 维度1 */
	private String name_en;

	/** 维度2 */
	private String start_time;

	/** 指标 */
	private String[] seltg;

	/** 多图显示 */
	private String multichart;

	private String actcondition;
	
	/**生成链接*/
	private String acturl;

	/** 定制图表1:y轴最小值 */
	private String maxvalue;

	/** 定制图表2:图片宽度 */
	private String pngwidth;

	/** 定制图表3:图片显示数值 */
	private String showvalue;

	private String pictitle;

	private String piccolor;

	private String xqingxie;

	public String getXqingxie() {
		return xqingxie;
	}

	public void setXqingxie(String xqingxie) {
		this.xqingxie = xqingxie;
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

	public ChoiceInfo() {
		dimensionElement = new ArrayList();
		targetElement = new ArrayList();
	}

	public String getDataModelid() {
		return dataModelid;
	}

	public void setDataModelid(String dataModelid) {
		this.dataModelid = dataModelid;
	}

	public List getDimensionElement() {
		return dimensionElement;
	}

	public void setDimensionElement(List dimensionElement) {
		this.dimensionElement = dimensionElement;
	}

	public List getTargetElement() {
		return targetElement;
	}

	public void setTargetElement(List targetElement) {
		this.targetElement = targetElement;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
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

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getShowvalue() {
		return showvalue;
	}

	public void setShowvalue(String showvalue) {
		this.showvalue = showvalue;
	}

	public void setSeltg(String[] seltg) {
		this.seltg = seltg;
	}

	public String[] getSeltg() {
		return seltg;
	}

	public String getShowactive() {
		return showactive;
	}

	public void setShowactive(String showactive) {
		this.showactive = showactive;
	}

	public String getTggroup() {
		return tggroup;
	}

	public void setTggroup(String tggroup) {
		this.tggroup = tggroup;
	}

	public String getXlevel() {

		return xlevel;
	}

	public void setXlevel(String xlevel) {
		this.xlevel = xlevel;
	}

	public String getActcondition() {
		return actcondition;
	}

	public void setActcondition(String actcondition) {
		this.actcondition = actcondition;
	}

	public boolean isDynamicDim() {
		return dynamicDim;
	}

	public void setDynamicDim(boolean dynamicDim) {
		this.dynamicDim = dynamicDim;
	}

	public String getActurl() {
		return acturl;
	}

	public void setActurl(String acturl) {
		this.acturl = acturl;
	}

}
