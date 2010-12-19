package oe.midware.workflow.engine.rule2.func;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.env.client.EnvService;
import oe.frame.bus.workflow.rule.EnvScriptFunction;

import oe.rmi.client.RmiEntry;

public class EnvScriptFunctionImpl implements EnvScriptFunction {

	private static Log _log = LogFactory.getLog(EnvScriptFunctionImpl.class);
	static EnvService env = null;

	private static EnvService getEnv() {
		if (env == null) {
			try {
				env = (EnvService) RmiEntry.iv("envinfo");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				_log.error(e.getMessage());
			}
		}
		return env;
	}

	public String value(String key) {
		try {
			return getEnv().fetchEnvValue(key);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public String value(String url, String key) {
		EnvService envX = null;
		try {
			envX = (EnvService) RmiEntry.ivCore(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

		try {
			return envX.fetchEnvValue(key);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public String readEnvFile(String filename) {
		try {
			FileInputStream file = new FileInputStream(new File(filename));
			byte[] bytex = new byte[file.available()];
			file.read(bytex);
			return new String(bytex, 0, bytex.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public void writeEnvFile(String filename, String data) {
		try {
			FileOutputStream file = new FileOutputStream(new File(filename));
			file.write(data.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	public String exeOuterScript(String uri, String[] param) {
		// TODO Auto-generated method stub
		return null;
	}
}
