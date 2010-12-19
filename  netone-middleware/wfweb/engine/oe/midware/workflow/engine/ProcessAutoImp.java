package oe.midware.workflow.engine;

import java.util.List;

import oe.frame.bus.workflow.ProcessAuto;



/**
 * 异步多线程自动化活动操作
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessAutoImp implements ProcessAuto, Runnable {
	Thread t;

	public void autoexecute() {

		t = new Thread(this, "autoexecute");
		t.start();

	}

	private List checkHaveAuto() {
		return null;
	}

	public void run() {
		List list = checkHaveAuto();

		while (list != null && list.size() > 0) {

		}
	}

}
