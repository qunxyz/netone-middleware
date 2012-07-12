package oe.rmi.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import oe.rmi.client.web.RmiInvocationHandler;
import oe.rmi.client.web.WebInvoke;
import oe.rmi.service.RmiStart;
import oe.rmi.web.RmiRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * RMI����
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class RmiEntry {

	static ResourceBundle messages = ResourceBundle.getBundle("rmiClient",
			Locale.CHINESE);

	static RmiStart rmiser = null;

	private static Log log = LogFactory.getLog(RmiEntry.class);

	private static Map proxyMap = new HashMap();

	public static Object iv(String name) throws MalformedURLException,
			RemoteException, NotBoundException {
		String urlRmi = (String) messages.getObject(name);
		if (urlRmi == null) {
			throw new RuntimeException("rmiClient.properties��δע��ķ�����:" + name);
		}
		return ivCore(urlRmi);
	}

	public static Object ivCore(String urlRmi) throws MalformedURLException,
			RemoteException, NotBoundException {
		if (urlRmi == null) {
			throw new RuntimeException("��Ч��ַ:" + urlRmi);
		}
		urlRmi = urlRmi.trim();
		if (urlRmi.indexOf("http://") >= 0) {
			// ����webRmiģʽ����
			return invokeWebRmi(urlRmi);
		}
		if (urlRmi.indexOf("home://") >= 0) {
			// ����Spring���õı���bean
			return invokeLocal(urlRmi);
		} else {
			// ������ͨRmiģʽ����
			return Naming.lookup(urlRmi);
		}
	}

	private static Object invokeLocal(String bean) {
		if (rmiser == null) {
			rmiser = new RmiStart();
		}

		return rmiser.invokeLocal(bean);
	}

	private static Object invokeWebRmi(String urlRmi) {
		return getProxy(urlRmi);
	}

	private static Object getProxy(String urlRmi) {
		if (proxyMap.get(urlRmi) == null) {
			Object proxy = null;
			try {
				Class[] classinfo = getInterface(urlRmi);

				RmiInvocationHandler rvi = new RmiInvocationHandler(urlRmi);
				proxy = Proxy.newProxyInstance(Thread.currentThread()
						.getContextClassLoader(), classinfo, rvi);
				proxyMap.put(urlRmi, proxy);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return proxyMap.get(urlRmi);
	}

	private static Class[] getInterface(String bindName) {
		RmiRequest req = new RmiRequest();
		req.setMode("ifc");
		req.setBindName(bindName);
		Object obj = WebInvoke.invoke(req);
		if (obj == null) {
			throw new RuntimeException("Զ�̵���ʧ��");
		} else {
			return (Class[]) obj;
		}
	}

}
