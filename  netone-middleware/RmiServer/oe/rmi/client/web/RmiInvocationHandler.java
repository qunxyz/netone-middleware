package oe.rmi.client.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import oe.rmi.web.RmiRequest;

public class RmiInvocationHandler implements InvocationHandler {
	private String httpurl;

	public RmiInvocationHandler(String httpurl) {
		this.httpurl = httpurl;

	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		RmiRequest req = new RmiRequest();
		req.setArgs(args);
		req.setBindName(httpurl);
		req.setMethodName(method.getName());
		req.setMode("method");
		req.setParam(method.getParameterTypes());

		return WebInvoke.invoke(req);
	}

}
