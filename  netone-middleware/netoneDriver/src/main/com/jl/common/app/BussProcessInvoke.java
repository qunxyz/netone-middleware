package com.jl.common.app;

import java.util.Date;

import com.jl.common.dyform.DyFormData;

import oe.frame.web.WebCache;

/**
 * 生产执行类,该类支持多线程调度，被设备监听程序所调度执行
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class BussProcessInvoke implements Runnable {
	Thread t;

	DyFormData data;

	String appname;

	TestCaseAutoFlow bph;

	public void start(DyFormData data, String appname) {
		this.data = data;
		this.appname = appname;
		t = new Thread(this, "" + System.currentTimeMillis());
		t.start();
	}

	public void run() {
		try {
			bph.todo(data, appname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
