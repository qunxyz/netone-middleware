package oe.bi.datasource.obj;

import java.io.Serializable;

/**
 * 数据集对象
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class Datasource implements Serializable {

	/** 数据源ID（数字编码） */
	private String dsid;

	/** 数据源名 */
	private String dsname;

	/** 数据源类型（DBMS,XML,Excel……） */
	private String dstype;

	/** 数据源连接的URL */
	private String dsurl;

	/** 数据源的JAVA操作驱动类路径 */
	private String dsdriver;

	/** 对数据源中的数据集的过滤条件 */
	private String dsflit;

	/** 数据源的权限用户 */
	private String username;

	/** 数据源的权限用户的密码 */
	private String password;

	/** 扩展属性 */
	private String extendattribute;

	/** 描述 */
	private String description;

	/** full constructor */
	public Datasource(String dsname, String dstype, String dsurl,
			String dsdriver, String dsflit, String extendattribute,
			String description) {
		this.dsname = dsname;
		this.dstype = dstype;
		this.dsurl = dsurl;
		this.dsdriver = dsdriver;
		this.dsflit = dsflit;
		this.extendattribute = extendattribute;
		this.description = description;
	}

	/** default constructor */
	public Datasource() {
	}

	public String getDsid() {
		return this.dsid;
	}

	public void setDsid(String dsid) {
		this.dsid = dsid;
	}

	public String getDsname() {
		return this.dsname;
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

	public String getDstype() {
		return this.dstype;
	}

	public void setDstype(String dstype) {
		this.dstype = dstype;
	}

	public String getDsurl() {
		return this.dsurl;
	}

	public void setDsurl(String dsurl) {
		this.dsurl = dsurl;
	}

	public String getDsdriver() {
		return this.dsdriver;
	}

	public void setDsdriver(String dsdriver) {
		this.dsdriver = dsdriver;
	}

	public String getDsflit() {
		return this.dsflit;
	}

	public void setDsflit(String dsflit) {
		this.dsflit = dsflit;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
