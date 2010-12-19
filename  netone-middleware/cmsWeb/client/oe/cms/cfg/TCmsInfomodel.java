package oe.cms.cfg;

import java.io.Serializable;

import java.util.List;

import oe.cms.CmsEntry;
import oe.cms.datasource.XMLParser;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 咨讯模型对象
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class TCmsInfomodel implements Serializable {

	/** identifier field */
	private Long modelid;

	private String wintime;

	private String naturalname;

	/** nullable persistent field */
	private String modelname;

	/** nullable persistent field */
	private String description;

	/** nullable persistent field */
	private String extendattribute;

	/** nullable persistent field */
	private String userid;

	/** persistent field */
	private String accessmode;

	/** nullable persistent field */
	private String infoxml;

	private String participant;

	private Long hit;

	private String levels;

	private String userinfo;

	private String createdtime;

	private String modifytime;

	private String styleinfo;

	public String getStyleinfo() {
		return styleinfo;
	}

	public void setStyleinfo(String styleinfo) {
		this.styleinfo = styleinfo;
	}

	/** full constructor */
	public TCmsInfomodel(Long modelid, String modelname, String description,
			String extendattribute, String userid, String accessmode,
			String infoxml) {
		this.modelid = modelid;
		this.modelname = modelname;
		this.description = description;
		this.extendattribute = extendattribute;
		this.userid = userid;
		this.accessmode = accessmode;
		this.infoxml = infoxml;
	}

	/** default constructor */
	public TCmsInfomodel() {
	}

	/** minimal constructor */
	public TCmsInfomodel(Long modelid, String accessmode) {
		this.modelid = modelid;
		this.accessmode = accessmode;
	}

	public Long getModelid() {
		return this.modelid;
	}

	public void setModelid(Long modelid) {
		this.modelid = modelid;
	}

	public String getModelname() {
		return this.modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccessmode() {
		return this.accessmode;
	}

	public void setAccessmode(String accessmode) {
		this.accessmode = accessmode;
	}

	public String getInfoxml() {
		return this.infoxml;
	}

	public void setInfoxml(String infoxml) {
		this.infoxml = infoxml;
	}

	public String toString() {
		return new ToStringBuilder(this).append("modelid", getModelid())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TCmsInfomodel))
			return false;
		TCmsInfomodel castOther = (TCmsInfomodel) other;
		return new EqualsBuilder().append(this.getModelid(),
				castOther.getModelid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getModelid()).toHashCode();
	}

	/**
	 * 获得资讯模型中的全部咨讯组信息
	 * 
	 * 该方法是通过解析infoXml属性来获得的
	 * 
	 * @return List中的元素是CellInfo
	 */
	public List fetchInfocell() {
		XMLParser xmlParser = (XMLParser) CmsEntry.fetchBean("xmlParser");
		// 获取CellInfo列表
		return xmlParser.toOBj(this.getInfoxml());

	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}

	public String getWintime() {
		return wintime;
	}

	public void setWintime(String wintime) {
		this.wintime = wintime;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

}
