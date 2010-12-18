package oe.security3a.seupublic.authentication.obj.core;

import java.io.Serializable;

import oe.security3a.seupublic.authentication.obj.RequestCtx;


public class TargetCtx implements Serializable {

	private RequestCtx request;

	public RequestCtx newRequest() {
		if (request == null) {
			request = new RequestCtx();
			return request;
		}
		throw new RuntimeException("request中已经存在一个request");
	}

	public RequestCtx getRequest() {
		return request;
	}

	public void setRequest(RequestCtx request) {
		this.request = request;
	}

}
