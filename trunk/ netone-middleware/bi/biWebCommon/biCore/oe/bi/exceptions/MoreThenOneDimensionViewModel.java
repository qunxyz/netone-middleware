package oe.bi.exceptions;

public class MoreThenOneDimensionViewModel extends Exception {

	public MoreThenOneDimensionViewModel(String info) {
		super("非法的切片运算！[" + info + "]");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
