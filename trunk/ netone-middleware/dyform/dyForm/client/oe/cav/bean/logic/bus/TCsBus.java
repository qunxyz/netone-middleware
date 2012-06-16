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
	private String column31;

	/** nullable persistent field */
	private String column32;

	private String column33;

	private String column34;

	private String column35;

	private String column36;

	private String column37;

	private String column38;

	private String column39;

	private String column40;

	private String column41;

	private String column42;

	private String column43;

	private String column44;

	private String column45;

	private String column46;

	private String column47;

	private String column48;

	private String column49;

	private String column50;
	
	private String column51;
	
	private String column52;
	
	private String column53;
	private String column54;
	private String column55;
	private String column56;
	private String column57;
	private String column58;
	private String column59;
	private String column60;
	private String column61;
	private String column62;
	private String column63;
	private String column64;
	private String column65;
	private String column66;
	private String column67;
	private String column68;
	private String column69;
	private String column70;
	
	private String column71;
	private String column72;
	private String column73;
	private String column74;
	private String column75;
	private String column76;
	private String column77;
	private String column78;
	private String column79;
	private String column80;
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

	public String getColumn31() {
		return column31;
	}

	public void setColumn31(String column31) {
		this.column31 = column31;
	}

	public String getColumn32() {
		return column32;
	}

	public void setColumn32(String column32) {
		this.column32 = column32;
	}

	public String getColumn33() {
		return column33;
	}

	public void setColumn33(String column33) {
		this.column33 = column33;
	}

	public String getColumn34() {
		return column34;
	}

	public void setColumn34(String column34) {
		this.column34 = column34;
	}

	public String getColumn35() {
		return column35;
	}

	public void setColumn35(String column35) {
		this.column35 = column35;
	}

	public String getColumn36() {
		return column36;
	}

	public void setColumn36(String column36) {
		this.column36 = column36;
	}

	public String getColumn37() {
		return column37;
	}

	public void setColumn37(String column37) {
		this.column37 = column37;
	}

	public String getColumn38() {
		return column38;
	}

	public void setColumn38(String column38) {
		this.column38 = column38;
	}

	public String getColumn39() {
		return column39;
	}

	public void setColumn39(String column39) {
		this.column39 = column39;
	}

	public String getColumn40() {
		return column40;
	}

	public void setColumn40(String column40) {
		this.column40 = column40;
	}

	public String getColumn41() {
		return column41;
	}

	public void setColumn41(String column41) {
		this.column41 = column41;
	}

	public String getColumn42() {
		return column42;
	}

	public void setColumn42(String column42) {
		this.column42 = column42;
	}

	public String getColumn43() {
		return column43;
	}

	public void setColumn43(String column43) {
		this.column43 = column43;
	}

	public String getColumn44() {
		return column44;
	}

	public void setColumn44(String column44) {
		this.column44 = column44;
	}

	public String getColumn45() {
		return column45;
	}

	public void setColumn45(String column45) {
		this.column45 = column45;
	}

	public String getColumn46() {
		return column46;
	}

	public void setColumn46(String column46) {
		this.column46 = column46;
	}

	public String getColumn47() {
		return column47;
	}

	public void setColumn47(String column47) {
		this.column47 = column47;
	}

	public String getColumn48() {
		return column48;
	}

	public void setColumn48(String column48) {
		this.column48 = column48;
	}

	public String getColumn49() {
		return column49;
	}

	public void setColumn49(String column49) {
		this.column49 = column49;
	}

	public String getColumn50() {
		return column50;
	}

	public void setColumn50(String column50) {
		this.column50 = column50;
	}

	public String getColumn51() {
		return column51;
	}

	public void setColumn51(String column51) {
		this.column51 = column51;
	}

	public String getColumn52() {
		return column52;
	}

	public void setColumn52(String column52) {
		this.column52 = column52;
	}

	public String getColumn53() {
		return column53;
	}

	public void setColumn53(String column53) {
		this.column53 = column53;
	}

	public String getColumn54() {
		return column54;
	}

	public void setColumn54(String column54) {
		this.column54 = column54;
	}

	public String getColumn55() {
		return column55;
	}

	public void setColumn55(String column55) {
		this.column55 = column55;
	}

	public String getColumn56() {
		return column56;
	}

	public void setColumn56(String column56) {
		this.column56 = column56;
	}

	public String getColumn57() {
		return column57;
	}

	public void setColumn57(String column57) {
		this.column57 = column57;
	}

	public String getColumn58() {
		return column58;
	}

	public void setColumn58(String column58) {
		this.column58 = column58;
	}

	public String getColumn59() {
		return column59;
	}

	public void setColumn59(String column59) {
		this.column59 = column59;
	}

	public String getColumn60() {
		return column60;
	}

	public void setColumn60(String column60) {
		this.column60 = column60;
	}

	public String getColumn61() {
		return column61;
	}

	public void setColumn61(String column61) {
		this.column61 = column61;
	}

	public String getColumn62() {
		return column62;
	}

	public void setColumn62(String column62) {
		this.column62 = column62;
	}

	public String getColumn63() {
		return column63;
	}

	public void setColumn63(String column63) {
		this.column63 = column63;
	}

	public String getColumn64() {
		return column64;
	}

	public void setColumn64(String column64) {
		this.column64 = column64;
	}

	public String getColumn65() {
		return column65;
	}

	public void setColumn65(String column65) {
		this.column65 = column65;
	}

	public String getColumn66() {
		return column66;
	}

	public void setColumn66(String column66) {
		this.column66 = column66;
	}

	public String getColumn67() {
		return column67;
	}

	public void setColumn67(String column67) {
		this.column67 = column67;
	}

	public String getColumn68() {
		return column68;
	}

	public void setColumn68(String column68) {
		this.column68 = column68;
	}

	public String getColumn69() {
		return column69;
	}

	public void setColumn69(String column69) {
		this.column69 = column69;
	}

	public String getColumn70() {
		return column70;
	}

	public void setColumn70(String column70) {
		this.column70 = column70;
	}

	public String getColumn71() {
		return column71;
	}

	public void setColumn71(String column71) {
		this.column71 = column71;
	}

	public String getColumn72() {
		return column72;
	}

	public void setColumn72(String column72) {
		this.column72 = column72;
	}

	public String getColumn73() {
		return column73;
	}

	public void setColumn73(String column73) {
		this.column73 = column73;
	}

	public String getColumn74() {
		return column74;
	}

	public void setColumn74(String column74) {
		this.column74 = column74;
	}

	public String getColumn75() {
		return column75;
	}

	public void setColumn75(String column75) {
		this.column75 = column75;
	}

	public String getColumn76() {
		return column76;
	}

	public void setColumn76(String column76) {
		this.column76 = column76;
	}

	public String getColumn77() {
		return column77;
	}

	public void setColumn77(String column77) {
		this.column77 = column77;
	}

	public String getColumn78() {
		return column78;
	}

	public void setColumn78(String column78) {
		this.column78 = column78;
	}

	public String getColumn79() {
		return column79;
	}

	public void setColumn79(String column79) {
		this.column79 = column79;
	}

	public String getColumn80() {
		return column80;
	}

	public void setColumn80(String column80) {
		this.column80 = column80;
	}

}
