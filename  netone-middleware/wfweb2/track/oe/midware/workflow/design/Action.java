package oe.midware.workflow.design;

public class Action {
	String offX;

	String offY;

	boolean isStart;

	boolean isEnd;

	public Action() {
	}

	public Action(String offX, String offY, boolean isStart, boolean isEnd) {
		this.offX = offX;
		this.offY = offY;
		this.isStart = isStart;
		this.isEnd = isEnd;
	}

	public String getOffX() {
		return offX;
	}

	public void setOffX(String offX) {
		this.offX = offX;
	}

	public String getOffY() {
		return offY;
	}

	public void setOffY(String offY) {
		this.offY = offY;
	}

	/**
	 * @return ���� isEnd��
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * @param isEnd
	 *            Ҫ���õ� isEnd��
	 */
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	/**
	 * @return ���� isStart��
	 */
	public boolean isStart() {
		return isStart;
	}

	/**
	 * @param isStart
	 *            Ҫ���õ� isStart��
	 */
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
}