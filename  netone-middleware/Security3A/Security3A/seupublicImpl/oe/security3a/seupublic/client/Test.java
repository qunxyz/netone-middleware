package oe.security3a.seupublic.client;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class Test {
	
	
	public String invokeUrl(String url) {
		URL rul = null;
		InputStream input = null;
		try {
			rul = new URL(url);
			URLConnection urlc = rul.openConnection();
			input = urlc.getInputStream();
			byte[] bytex = new byte[input.available()];
			int num = input.read(bytex);
			return new String(bytex, 0, num);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
//		UserService userservice = (UserService) SeuserEntry.iv("userService");
//		Clerk clerk = new Clerk();
//		clerk.setOfficeNO("0001");
//		List list = userservice.fetchDao().queryObjects(clerk, null, null);
//		System.out.println(list.size());
		
//		Test t = new Test();
//		String ss = t.invokeUrl("http://192.168.2.100:8080/Security3A/ImageCodeSvl");
//		System.out.println(ss);
	}
		
}
