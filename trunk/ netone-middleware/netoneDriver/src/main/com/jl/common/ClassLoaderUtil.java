package com.jl.common;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 获取相对class路径下的资源流或URL，比如.properties,file
 * <ul>
 * <li>web application 打包成war后部署到有些服务器上面，会读取不到配置文件。通过相对class路径，可适应不同环境下读取配置文件
 * eg:1) web-inf/class/defualt.properties, path参数就是文件名称--defualt.properties
 * 
 * 2) web-inf/class/和web-inf/config/default.protperties，
 * path参数为../config/defualt.properties
 * 
 * 3) web-inf/class/和example/default.protperties，
 * path参数为../../example/defualt.properties
 * 
 * </li>
 * </ul>
 * 
 * @author zhang.chao.yi
 * @autthor
 * @version 1.0 Date:Oct 12, 2007
 */
public class ClassLoaderUtil {

	// -------------------------------------------------------Class Methods

	private static ClassLoaderUtil classLoaderUtil;

	private static ClassLoader clasLoader;

	/** class path绝对路径 */
	private String classAbsolutePath;

	private ClassLoaderUtil(ClassLoader classLoader) {
		if (classLoader != null) {
			this.clasLoader = classLoader;
		} else {
			this.clasLoader = Thread.currentThread().getContextClassLoader();
		}
		this.classAbsolutePath = this.clasLoader.getResource("").toString();
	}

	public static ClassLoaderUtil newInstance() {
		return newInstance(null);
	}

	public static ClassLoaderUtil newInstance(ClassLoader classLoader) {
		if (classLoaderUtil == null) {
			classLoaderUtil = new ClassLoaderUtil(classLoader);
		}
		return classLoaderUtil;
	}

	// -------------------------------------------------------Private Variables

	Log log = LogFactory.getLog(ClassLoaderUtil.class);

	// --------------------------------------------------------Public Methods

	/**
	 * 装载类
	 * 
	 * @param name
	 *            类全名
	 * @throws ClassNotFoundException
	 */
	public Class loadClass(String className) throws ClassNotFoundException {
		return this.clasLoader.loadClass(className);
	}

	/**
	 * 获取相对class下资源的URL
	 * 
	 * @param path
	 *            相对classpath路径
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getResourceAbsoluteURL(String path) throws MalformedURLException {
		if (path.indexOf("../") < 0) {
			return this.clasLoader.getResource(path);
		}

		String relativeStr = "", resourcePath = "";
		int relativeNum = 0;
		relativeStr = path.substring(0, path.lastIndexOf("../") + 3);
		resourcePath = path.substring(path.lastIndexOf("../") + 3);

		while (relativeStr.indexOf("../") >= 0) {
			relativeNum++;
			relativeStr = relativeStr.substring(3);
		}

		// 处理相对类根路径后的绝对路径
		String relativeClassPath = this.classAbsolutePath;
		while (relativeNum > 0) {
			relativeClassPath = relativeClassPath.substring(0,
					relativeClassPath.lastIndexOf("/", relativeClassPath
							.length() - 2) + 1);
			relativeNum--;
		}

		path = relativeClassPath + resourcePath;
		URL url = new URL(path);

		return url;
	}

	/**
	 * 获取资源输入流
	 * 
	 * @param path
	 *            相对classpath路径
	 * @return
	 * @throws Exception
	 */
	public InputStream getResourceStream(String path) throws Exception {
		URL url = this.getResourceAbsoluteURL(path);
		if (url != null) {
			return url.openStream();
		}

		return null;
	}

	/**
	 * 获取属性资源
	 * 
	 * @param path
	 *            相对classpath路径
	 * @return
	 * @throws Exception
	 */
	public Properties getProperties(String path) throws Exception {
		Properties properties = new Properties();
		InputStream in = this.getResourceStream(path);

		properties.load(in);

		return properties;
	}

	// ---------------------------------------------------------Private Methods

	public static void main(String[] args) {
		try {
			URL url = ClassLoaderUtil.newInstance().getResourceAbsoluteURL(
					"jdbc.properties");
			System.out.println(url.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
