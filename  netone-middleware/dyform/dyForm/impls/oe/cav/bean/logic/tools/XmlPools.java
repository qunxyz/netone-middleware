package oe.cav.bean.logic.tools;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

public class XmlPools {

	public static URL fetchXML(String filename) {
		String webpath =  System.getProperty("user.dir") + "/dy/";
		 File file = new File(webpath + filename + ".xml");
		 try {
		 return file.toURL();
		 } catch (MalformedURLException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 throw new RuntimeException("对象中的参数无效");
	}

	/**
	 * 获得写XML的路径
	 * 
	 * @param systemid
	 * @return
	 */
	public static String writedPath(String formcode) {

		return System.getProperty("user.dir") + "/dy/"+ formcode + ".xml";
	}

}
