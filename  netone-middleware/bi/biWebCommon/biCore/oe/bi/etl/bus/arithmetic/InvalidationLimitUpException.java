package oe.bi.etl.bus.arithmetic;

public class InvalidationLimitUpException extends Exception {

	public InvalidationLimitUpException() {
		super("无效的上限值");
	}

}
