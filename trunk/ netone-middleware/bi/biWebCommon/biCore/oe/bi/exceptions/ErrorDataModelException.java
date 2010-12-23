package oe.bi.exceptions;

public class ErrorDataModelException extends Exception {

	public ErrorDataModelException(String error) {
		super("模型定义错误！["+error+"]");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
