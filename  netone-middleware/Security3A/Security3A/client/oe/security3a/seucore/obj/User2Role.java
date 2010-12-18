package oe.security3a.seucore.obj;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 用户与角色之间的关系	User2Role
 * 可以转化成实际存在的、与数据库产生映射的pojo
 * @author wsz
 * 
 */
public class User2Role implements Serializable {

	//id
	private Long id;

	//用户id
	private String userid;

	//角色id
	private String roleid;

	//用户的隶属于，这直接决定了这个类将向哪一个类转化
	private String code;

	public User2Role(String code, String userid, String roleid) {
		this.code = code;
		this.userid = userid;
		this.roleid = roleid;
	}

	public User2Role() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof User2Role))
			return false;
		User2Role castOther = (User2Role) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

}
