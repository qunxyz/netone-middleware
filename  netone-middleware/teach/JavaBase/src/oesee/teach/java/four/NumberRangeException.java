package oesee.teach.java.four;

/**
 * 
 * @author chen.jia.xun
 * 
 */
public class NumberRangeException extends Exception {
	private String msg;

	public NumberRangeException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String toString() {
		return this.msg;
	}
}
