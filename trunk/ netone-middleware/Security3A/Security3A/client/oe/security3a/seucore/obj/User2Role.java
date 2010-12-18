package oe.security3a.seucore.obj;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * �û����ɫ֮��Ĺ�ϵ	User2Role
 * ����ת����ʵ�ʴ��ڵġ������ݿ����ӳ���pojo
 * @author wsz
 * 
 */
public class User2Role implements Serializable {

	//id
	private Long id;

	//�û�id
	private String userid;

	//��ɫid
	private String roleid;

	//�û��������ڣ���ֱ�Ӿ���������ཫ����һ����ת��
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
