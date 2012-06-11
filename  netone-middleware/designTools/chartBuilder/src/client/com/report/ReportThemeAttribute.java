package com.report;

public class ReportThemeAttribute {
	private boolean haveBorder;
	private boolean haveBold;
	private boolean haveItalic;
	private String backgroundColor;
	private short fontWidth;

	public boolean isHaveBorder() {
		return haveBorder;
	}

	public void setHaveBorder(boolean haveBorder) {
		this.haveBorder = haveBorder;
	}

	public boolean isHaveBold() {
		return haveBold;
	}

	public void setHaveBold(boolean haveBold) {
		this.haveBold = haveBold;
	}

	public boolean isHaveItalic() {
		return haveItalic;
	}

	public void setHaveItalic(boolean haveItalic) {
		this.haveItalic = haveItalic;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public short getFontWidth() {
		return fontWidth;
	}

	public void setFontWidth(short fontWidth) {
		this.fontWidth = fontWidth;
	}

}
