package oe.rmi.client.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import oe.rmi.web.RmiRequest;

public class WebInvoke {

	static String _RMI_SERVLET_NAME = "/httprmi?name=";

	public static Object invoke(RmiRequest req) {
		String urlRmi = req.getBindName();
		int indexServername = urlRmi.lastIndexOf('/');
		String servername = urlRmi.substring(indexServername + 1);
		if (servername == null || servername.equals("")) {
			throw new RuntimeException(
					"rmiClient.properties���ô���,��Ч��RmiWebЭ����ʽ");
		}
		InputStream iis = null;
		ObjectInputStream ois = null;
		try {
			// ����httpЭ�鷢��Rmi�����������
			String urlroot = urlRmi.substring(0, indexServername);
			
			urlRmi = urlroot + _RMI_SERVLET_NAME + servername ;
			if ("ifc".equals(req.getMode())) {
				urlRmi=urlRmi + "&mode=ifc";
			} else if("method".equals(req.getMode())){
				urlRmi=urlRmi+ "&mode=method";
			}

			URL url = new URL(urlRmi);
			HttpURLConnection urlconn = (HttpURLConnection) url
					.openConnection();

			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setUseCaches(false);
			urlconn.setRequestProperty("Content-Type",
					"application/octet-stream");

			OutputStream os = urlconn.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(req);
			oos.flush();
			oos.close();

			// ��û���httpЭ�鷵�ص�Rmi����
			iis = urlconn.getInputStream();
			ois = new ObjectInputStream(new BufferedInputStream(iis));
			Object obj = ois.readObject();

			return obj;

		} catch (Exception e) {
			throw new RuntimeException("WebRMI�����쳣,url��" + urlRmi, e);
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
