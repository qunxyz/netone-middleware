package oe.security3a.seucore.obj;



public class Person extends User {
    private String sex;
    /**
     * 移动电话
     */
    private String phoneNO;
    /**
     * 部门
     */
    private String faxNO;

    private String address;
    /**
     * 邮件
     */
    private String email;

    private String nationArea;

    private String language;
    /**
     * 人员性质
     */
    private String province;
    
    private String city;

    private String zip;
    /**
     * 职务
     */
    private String company;

    private String education;
    /**
     * 备注
     */
    private String remark;

    private String officeNO;

    private String qq;

    private String msn;

    private String cardType;

    private String cardNO;

    public Person() {
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }

    public void setFaxNO(String faxNO) {
        this.faxNO = faxNO;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNationArea(String nationArea) {

        this.nationArea = nationArea;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setOfficeNO(String officeNO) {
        this.officeNO = officeNO;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }

    public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoneNO() {
        return phoneNO;
    }

    public String getFaxNO() {
        return faxNO;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getNationArea() {

        return nationArea;
    }

    public String getLanguage() {
        return language;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getCompany() {
        return company;
    }

    public String getEducation() {
        return education;
    }

    public String getRemark() {
        return remark;
    }

    public String getOfficeNO() {
        return officeNO;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardNO() {
        return cardNO;
    }

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
}
