package oe.rmi.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiStart {
	ResourceBundle messages = null;

	ResourceBundle messages1 = null;

	// 本地服务Bean
	Map homemap = new HashMap();

	private Log log = LogFactory.getLog(RmiStart.class);

	private void start(int port, String host) {

		try {
			// String port = messages.getString("spiport");
			System.setProperty("java.rmi.server.hostname", host);
			Registry registry = LocateRegistry.createRegistry(port);
			
			try {
				RMISocketFactory.setSocketFactory(new SMRMISocket());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			Enumeration enr = messages.getKeys();
			// Enumeration enr = messages.getKeys();
			while (enr.hasMoreElements()) {

				String classname = (String) enr.nextElement();
				String classInfo = messages.getString(classname);

				log.info("bind naming [" + classname + "]");

				try {
					Remote obj = (Remote) invokeLocal(classInfo);
					registry.bind(classname, obj);
				} catch (AlreadyBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info("service [" + classInfo + "].......done");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object invokeLocal(String bean) {
		int beannameindex = bean.lastIndexOf('/');
		String beanname = bean.substring(beannameindex + 1);
		// 7是 'home://'的长度
		String beanContext = bean.substring(7, beannameindex);
		if (!homemap.containsKey(beanContext)) {
			AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
					beanContext + ".xml");
			homemap.put(beanContext, appContext);
		}
		AbstractApplicationContext appContext = (AbstractApplicationContext) homemap
				.get(beanContext);
		return appContext.getBean(beanname);
	}

	public void start() {

		messages = ResourceBundle.getBundle("rmiService", Locale.CHINESE);

		messages1 = ResourceBundle.getBundle("Service", Locale.CHINESE);

		String ip = messages1.getString("ipconfig");
		String portinfo = messages1.getString("rmiport");
		if (portinfo == null || portinfo.equals("")) {
			portinfo = "2888";
		}
		int port = Integer.parseInt(portinfo);
		if (ip == null || ip.equals("")) {
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ip = address.getHostAddress();
		}
		System.out.println();
		log.info("Start port:" + port + " host:" + ip);
		System.out.println();
		start(port, ip);
	}

	public static void main(String[] args) {
		RmiStart rs = new RmiStart();
		rs.start();
	}
}
