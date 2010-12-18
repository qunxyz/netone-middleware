package oe.teach.mid.buss;

import java.util.Date;

public class MyAccount {

	private String lsh;

	private String name;

	private String types;

	private double oriprice;

	private double curprice;

	private String description;

	private String participant;

	private String created;
	
	private double estprice;
	
	private String auditing;

	public String getAuditing() {
		return auditing;
	}

	public void setAuditing(String auditing) {
		this.auditing = auditing;
	}

	public double getEstprice() {
		return estprice;
	}

	public void setEstprice(double estprice) {
		this.estprice = estprice;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public double getOriprice() {
		return oriprice;
	}

	public void setOriprice(double oriprice) {
		this.oriprice = oriprice;
	}

	public double getCurprice() {
		return curprice;
	}

	public void setCurprice(double curprice) {
		this.curprice = curprice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
