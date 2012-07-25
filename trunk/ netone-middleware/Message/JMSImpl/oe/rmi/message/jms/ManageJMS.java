package oe.rmi.message.jms;


public class ManageJMS {
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ManageJMS() {
		super();
	}

	public ManageJMS(String url) {
		super();
		this.url = url;
	}
}