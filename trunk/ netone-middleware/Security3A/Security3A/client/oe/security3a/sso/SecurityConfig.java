package oe.security3a.sso;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.sso.util.PropertiesConf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SecurityConfig {

	private static Log log = LogFactory.getLog(SecurityConfig.class);

	private static SecurityConfig secconf = new SecurityConfig();

	private PropertiesConf confSer;

	private boolean isDebug = false;

	private SecurityConfig() {
		confSer = new PropertiesConf("ssoserver.properties");

	}

	public static synchronized SecurityConfig getInstance() {
		return secconf;
	}

	public boolean getIsDebug() {
		return isDebug;
	}

	public String getLibProperty(String key) {
		
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			return cupm.fetchConfig(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public String getSsoServerUrl() {
		try {
			String ip = getLibProperty("ssoserver.ip");
			String port = getLibProperty("ssoserver.port");
			String ssoctpath = getLibProperty("ssoserver.context");
			
			String ssoserverurl = "http://" + ip + ":" + port + ssoctpath;
			
			System.out.println("URL Filter:"+ssoserverurl);
			return ssoserverurl;
		} catch (Exception e) {
			log
					.error(
							"≈‰÷√œÓ£∫ssoserver.ip,ssoserver.port,server.contextpath.ssoserver∂¡»°≥ˆ¥Ì£°",
							e);
		}
		return null;
	}

	public String getLoginSubmitPath() {
		String ip = getLibProperty("ssoserver.ip");
		String port = getLibProperty("ssoserver.port");
		String ssoctpath = getLibProperty("ssoserver.context");
		String path = "http://" + ip + ":" + port + ssoctpath + "/login";

		return path;
	}

	public String getTimeOutPage() {
		return confSer.getProperty("timeoutLocation");
	}

	public String getLoginPage() {
		return confSer.getProperty("loginLocation");
	}

	public String getSSOServerProperty(String key) {
		return confSer.getProperty(key);
	}
}
