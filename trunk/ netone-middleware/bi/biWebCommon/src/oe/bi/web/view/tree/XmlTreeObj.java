package oe.bi.web.view.tree;

public class XmlTreeObj {

	private String text;

	private String id;

	private String children;

	private String toolTip;

	private String icon;

	private String openicon;

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenicon() {
		return openicon;
	}

	public void setOpenicon(String openicon) {
		this.openicon = openicon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

}
