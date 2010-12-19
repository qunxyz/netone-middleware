package oe.cav.web.workflow.resource.soa2;

import org.apache.struts.action.ActionForm;

public class ShowForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String chkid;

	public String checks;

	public String txt;

	public String syn;

	public String formcode;

	public String processid;

	public String outdytext;

	public String outwftext;

	public String outbetext;

	public String innewtext;
	
	public String cdata;

	public String getCdata() {
		return cdata;
	}

	public void setCdata(String cdata) {
		this.cdata = cdata;
	}

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getSyn() {
		return syn;
	}

	public void setSyn(String syn) {
		this.syn = syn;
	}

	public String getChkid() {
		return chkid;
	}

	public void setChkid(String chkid) {
		this.chkid = chkid;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getChecks() {
		return checks;
	}

	public void setChecks(String checks) {
		this.checks = checks;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getOutbetext() {
		return outbetext;
	}

	public void setOutbetext(String outbetext) {
		this.outbetext = outbetext;
	}

	public String getOutdytext() {
		return outdytext;
	}

	public void setOutdytext(String outdytext) {
		this.outdytext = outdytext;
	}

	public String getOutwftext() {
		return outwftext;
	}

	public void setOutwftext(String outwftext) {
		this.outwftext = outwftext;
	}

	public String getInnewtext() {
		return innewtext;
	}

	public void setInnewtext(String innewtext) {
		this.innewtext = innewtext;
	}

}
