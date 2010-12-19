package oe.cms.web.init;

/**
 * T1 模拟一个死循环的程序，通过多线程，在限定的时间点时 关闭超时执行的程序
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class T1 implements Runnable {

	Thread t;

	T1() {
		t = new Thread(this, "s");
		t.start();
	}

	public void run() {
	}

	public static void wait(int time) {
		// 启动死循环程序
		T1 t1 = new T1();
		// 打开一个监测线程
		Thread t = new Thread();

		try {
			// 监测程序等待3秒
			t.sleep(time);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
