package oe.cms.web.init;

/**
 * T1 ģ��һ����ѭ���ĳ���ͨ�����̣߳����޶���ʱ���ʱ �رճ�ʱִ�еĳ���
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
		// ������ѭ������
		T1 t1 = new T1();
		// ��һ������߳�
		Thread t = new Thread();

		try {
			// ������ȴ�3��
			t.sleep(time);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
