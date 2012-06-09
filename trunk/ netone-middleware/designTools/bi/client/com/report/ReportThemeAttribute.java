package com.report;

public class ReportThemeAttribute {
	private String fontName;
	private short fontHeightInPoints;
	private short boldWeight;
	private String alignment;
	private String verticalAlignment;

	private String fillForegroundColor;
	private String fillBackgroundColor;

	/** ±ﬂøÚ Ù–‘ */
	private short borderBottom;
	private short borderLeft;
	private short borderRight;
	private short borderTop;
	private String topBorderColor;
	private String leftBorderColor;
	private String bottomBorderColor;
	private String rightBorderColor;

	private boolean hidden;

	// excel
	private String fillPattern;

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public short getFontHeightInPoints() {
		return fontHeightInPoints;
	}

	public void setFontHeightInPoints(short fontHeightInPoints) {
		this.fontHeightInPoints = fontHeightInPoints;
	}

	public short getBoldWeight() {
		return boldWeight;
	}

	public void setBoldWeight(short boldWeight) {
		this.boldWeight = boldWeight;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public String getFillForegroundColor() {
		return fillForegroundColor;
	}

	public void setFillForegroundColor(String fillForegroundColor) {
		this.fillForegroundColor = fillForegroundColor;
	}

	public String getFillBackgroundColor() {
		return fillBackgroundColor;
	}

	public void setFillBackgroundColor(String fillBackgroundColor) {
		this.fillBackgroundColor = fillBackgroundColor;
	}

	public short getBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(short borderBottom) {
		this.borderBottom = borderBottom;
	}

	public short getBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(short borderLeft) {
		this.borderLeft = borderLeft;
	}

	public short getBorderRight() {
		return borderRight;
	}

	public void setBorderRight(short borderRight) {
		this.borderRight = borderRight;
	}

	public short getBorderTop() {
		return borderTop;
	}

	public void setBorderTop(short borderTop) {
		this.borderTop = borderTop;
	}

	public String getTopBorderColor() {
		return topBorderColor;
	}

	public void setTopBorderColor(String topBorderColor) {
		this.topBorderColor = topBorderColor;
	}

	public String getLeftBorderColor() {
		return leftBorderColor;
	}

	public void setLeftBorderColor(String leftBorderColor) {
		this.leftBorderColor = leftBorderColor;
	}

	public String getBottomBorderColor() {
		return bottomBorderColor;
	}

	public void setBottomBorderColor(String bottomBorderColor) {
		this.bottomBorderColor = bottomBorderColor;
	}

	public String getRightBorderColor() {
		return rightBorderColor;
	}

	public void setRightBorderColor(String rightBorderColor) {
		this.rightBorderColor = rightBorderColor;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getFillPattern() {
		return fillPattern;
	}

	public void setFillPattern(String fillPattern) {
		this.fillPattern = fillPattern;
	}

}
