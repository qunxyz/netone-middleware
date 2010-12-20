package oe.cav.bean.logic.bus;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TCsBus implements Serializable {
	// 真正的维度字段,其值只能来自资源树的节点
	private String belongx;

	private String timex;

	private String lsh;

	private String fatherlsh;

	private String start_time;

	private String sys_int_id;

	/** persistent field */
	private String formcode;

	/** persistent field */
	private String participant;

	/** persistent field */
	private String created;

	private Long hit;

	/** nullable persistent field */
	private String column1;

	/** nullable persistent field */
	private String column2;

	/** nullable persistent field */
	private String column3;

	/** nullable persistent field */
	private String column4;

	/** nullable persistent field */
	private String column5;

	/** nullable persistent field */
	private String column6;

	/** nullable persistent field */
	private String column7;

	/** nullable persistent field */
	private String column8;

	/** nullable persistent field */
	private String column9;

	/** nullable persistent field */
	private String column10;

	/** nullable persistent field */
	private String column11;

	/** nullable persistent field */
	private String column12;

	/** nullable persistent field */
	private String column13;

	/** nullable persistent field */
	private String column14;

	/** nullable persistent field */
	private String column15;

	/** nullable persistent field */
	private String column16;

	/** nullable persistent field */
	private String column17;

	/** nullable persistent field */
	private String column18;

	/** nullable persistent field */
	private String column19;

	/** nullable persistent field */
	private String column20;

	/** nullable persistent field */
	private String column21;

	/** nullable persistent field */
	private String column22;

	/** nullable persistent field */
	private String column23;

	/** nullable persistent field */
	private String column24;

	/** nullable persistent field */
	private String column25;

	/** nullable persistent field */
	private String column26;

	/** nullable persistent field */
	private String column27;

	/** nullable persistent field */
	private String column28;

	/** nullable persistent field */
	private String column29;

	/** nullable persistent field */
	private String column30;

	/** nullable persistent field */
	// [queryinfo]clumnid,opesymbol#clumnid,opesymbol
	private String extendattribute;

	/** persistent field */
	private String statusinfo;

	/** full constructor */
	public TCsBus(String formcode, String participant, String created,
			String taskcode, String column1, String column2, String column3,
			String column4, String column5, String column6, String column7,
			String column8, String column9, String column10, String column11,
			String column12, String column13, String column14, String column15,
			String column16, String column17, String column18, String column19,
			String column20, String column21, String column22, String column23,
			String column24, String column25, String column26, String column27,
			String column28, String column29, String column30,
			String extendattribute, String statusinfo) {
		this.formcode = formcode;
		this.participant = participant;
		this.created = created;
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.column4 = column4;
		this.column5 = column5;
		this.column6 = column6;
		this.column7 = column7;
		this.column8 = column8;
		this.column9 = column9;
		this.column10 = column10;
		this.column11 = column11;
		this.column12 = column12;
		this.column13 = column13;
		this.column14 = column14;
		this.column15 = column15;
		this.column16 = column16;
		this.column17 = column17;
		this.column18 = column18;
		this.column19 = column19;
		this.column20 = column20;
		this.column21 = column21;
		this.column22 = column22;
		this.column23 = column23;
		this.column24 = column24;
		this.column25 = column25;
		this.column26 = column26;
		this.column27 = column27;
		this.column28 = column28;
		this.column29 = column29;
		this.column30 = column30;
		this.extendattribute = extendattribute;
		this.statusinfo = statusinfo;
	}

	/** default constructor */
	public TCsBus() {
	}

	/** minimal constructor */
	public TCsBus(String formcode, String participant, String created,
			String statusinfo) {
		this.formcode = formcode;
		this.participant = participant;
		this.created = created;
		this.statusinfo = statusinfo;
	}

	public String getLsh() {
		return this.lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getFormcode() {
		return this.formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getParticipant() {
		return this.participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getColumn1() {
		return this.column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return this.column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return this.column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	public String getColumn4() {
		return this.column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	public String getColumn5() {
		return this.column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	public String getColumn6() {
		return this.column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}

	public String getColumn7() {
		return this.column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	public String getColumn8() {
		return this.column8;
	}

	public void setColumn8(String column8) {
		this.column8 = column8;
	}

	public String getColumn9() {
		return this.column9;
	}

	public void setColumn9(String column9) {
		this.column9 = column9;
	}

	public String getColumn10() {
		return this.column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return this.column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getColumn12() {
		return this.column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	public String getColumn13() {
		return this.column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	public String getColumn14() {
		return this.column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getColumn15() {
		return this.column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
	}

	public String getColumn16() {
		return this.column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
	}

	public String getColumn17() {
		return this.column17;
	}

	public void setColumn17(String column17) {
		this.column17 = column17;
	}

	public String getColumn18() {
		return this.column18;
	}

	public void setColumn18(String column18) {
		this.column18 = column18;
	}

	public String getColumn19() {
		return this.column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}

	public String getColumn20() {
		return this.column20;
	}

	public void setColumn20(String column20) {
		this.column20 = column20;
	}

	public String getColumn21() {
		return this.column21;
	}

	public void setColumn21(String column21) {
		this.column21 = column21;
	}

	public String getColumn22() {
		return this.column22;
	}

	public void setColumn22(String column22) {
		this.column22 = column22;
	}

	public String getColumn23() {
		return this.column23;
	}

	public void setColumn23(String column23) {
		this.column23 = column23;
	}

	public String getColumn24() {
		return this.column24;
	}

	public void setColumn24(String column24) {
		this.column24 = column24;
	}

	public String getColumn25() {
		return this.column25;
	}

	public void setColumn25(String column25) {
		this.column25 = column25;
	}

	public String getColumn26() {
		return this.column26;
	}

	public void setColumn26(String column26) {
		this.column26 = column26;
	}

	public String getColumn27() {
		return this.column27;
	}

	public void setColumn27(String column27) {
		this.column27 = column27;
	}

	public String getColumn28() {
		return this.column28;
	}

	public void setColumn28(String column28) {
		this.column28 = column28;
	}

	public String getColumn29() {
		return this.column29;
	}

	public void setColumn29(String column29) {
		this.column29 = column29;
	}

	public String getColumn30() {
		return this.column30;
	}

	public void setColumn30(String column30) {
		this.column30 = column30;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getStatusinfo() {
		return this.statusinfo;
	}

	public void setStatusinfo(String statusinfo) {
		this.statusinfo = statusinfo;
	}

	public String toString() {
		return new ToStringBuilder(this).append("lsh", getLsh()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TCsBus))
			return false;
		TCsBus castOther = (TCsBus) other;
		return new EqualsBuilder().append(this.getLsh(), castOther.getLsh())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getLsh()).toHashCode();
	}

	public String getFatherlsh() {
		return fatherlsh;
	}

	public void setFatherlsh(String fatherlsh) {
		this.fatherlsh = fatherlsh;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getBelongx() {
		return belongx;
	}

	public void setBelongx(String belongx) {
		this.belongx = belongx;
	}

	public String getTimex() {
		return timex;
	}

	public void setTimex(String timex) {
		this.timex = timex;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getSys_int_id() {
		return sys_int_id;
	}

	public void setSys_int_id(String sys_int_id) {
		this.sys_int_id = sys_int_id;
	}

}
