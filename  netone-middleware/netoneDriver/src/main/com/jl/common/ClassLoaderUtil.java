package com.jl.common;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��ȡ���class·���µ���Դ����URL������.properties,file
 * <ul>
 * <li>web application �����war������Щ���������棬���ȡ���������ļ���ͨ�����class·��������Ӧ��ͬ�����¶�ȡ�����ļ�
 * eg:1) web-inf/class/defualt.properties, path���������ļ�����--defualt.properties
 * 
 * 2) web-inf/class/��web-inf/config/default.protperties��
 * path����Ϊ../config/defualt.properties
 * 
 * 3) web-inf/class/��example/default.protperties��
 * path����Ϊ../../example/defualt.properties
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

	/** class path����·�� */
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
	 * װ����
	 * 
	 * @param name
	 *            ��ȫ��
	 * @throws ClassNotFoundException
	 */
	public Class loadClass(String className) throws ClassNotFoundException {
		return this.clasLoader.loadClass(className);
	}

	/**
	 * ��ȡ���class����Դ��URL
	 * 
	 * @param path
	 *            ���classpath·��
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

		// ����������·����ľ���·��
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
	 * ��ȡ��Դ������
	 * 
	 * @param path
	 *            ���classpath·��
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
	 * ��ȡ������Դ
	 * 
	 * @param path
	 *            ���classpath·��
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
