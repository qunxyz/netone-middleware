package oe.rmi.web;

import java.io.Serializable;

public class RmiRequest implements Serializable {

	private String bindName;

	private String methodName;

	private Class[] param;

	private Object[] args;

	private String mode;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParam() {
		return param;
	}

	public void setParam(Class[] param) {
		this.param = param;
	}

}
