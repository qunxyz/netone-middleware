package oe.bi.etl.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ����������ѡ�����Ϣ
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class ChoiceInfo implements Serializable {

	private String lsh;

	// ��������
	private String name;

	// ����
	private String naturalname;

	/** ҵ������ģ�� */
	private String dataModelid;

	/** ѡ���ά����ϢDimensionElement */
	private List dimensionElement;

	/** ѡ���ָ����ϢTargetElement */
	private List targetElement;

	private String otherinfo;

	private String created;

	/** ά�ȶ�ֵ̬ */
	private boolean dynamicDim;

	/**
	 * �������� ���ѡ���,���縣���µ�����BTS��Ӧ��, �����levelҪѡ��bts��,����ҵ������ȷ���ڸ������������
	 */
	private String xlevel;

	/** ָ���� */
	private String tggroup;

	/** �Ƿ���ʾͼ�������� */
	private String showactive;

	/** �Ƿ�ʹ��ͼ�������� */
	private boolean active;

	/** ͼ������1,��5��ֵ,verticalbar3D=������ͼ,verticalbarLinec=����ͼ�Ա�,verticalbarLine=����ͼ,line=��ͼ,pie=��ͼ */
	private String selcharttype;

	/** ͼ������2,��3��ֵ,default=Ĭ��ͳ��ͼ,ne-time=��Ԫ-ʱ��ͳ��ͼ,time-ne=ʱ��-��Ԫͳ��ͼ */
	private String seldatatype;

	/** ά��1 */
	private String name_en;

	/** ά��2 */
	private String start_time;

	/** ָ�� */
	private String[] seltg;

	/** ��ͼ��ʾ */
	private String multichart;

	private String actcondition;
	
	/**��������*/
	private String acturl;

	/** ����ͼ��1:y����Сֵ */
	private String maxvalue;

	/** ����ͼ��2:ͼƬ��� */
	private String pngwidth;

	/** ����ͼ��3:ͼƬ��ʾ��ֵ */
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
