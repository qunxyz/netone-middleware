package oe.mid.datatools.utils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import oe.frame.web.db.DbTools;
import oe.mid.datatools.DataToolIfc;
import oe.mid.datatools.DataToolImplamle;
import oe.mid.datatools.obj.DSource;
import oe.mid.datatools.obj.DataTrans;

import org.apache.commons.dbcp.BasicDataSource;

public class DB extends DbTools {

	static Map dscache = new HashMap();

	static {

	}

	static String source;

	public static String getSource() {
		return source;
	}

	public static void setSource(String source) {
		DB.source = source;
	}

	public static Connection conByDs(String dsid) {
		DataToolIfc dataToolIfc = new DataToolImplamle();

		DataTrans dataTrans = dataToolIfc.parser(source);

		DSource dsthis = dataTrans.getSource().get(dsid);

		return conByDs(dsthis);
	}
	
	public static Connection conByDs(DSource dsthis) {

		try {
			String key = dsthis.getPasssword() + dsthis.getUrl();
			if (!dscache.containsKey(key)) {
				BasicDataSource ds = new BasicDataSource();

				int maxActivev = 100;
				int maxWaitv = 1000;
				int maxIdlev = 20;
				int initialSizev = 30;

				ds.setPassword(dsthis.getPasssword());
				ds.setUrl(dsthis.getUrl());
				ds.setDriverClassName(dsthis.getDriver());
				ds.setUsername(dsthis.getName());
				ds.setMaxActive(maxActivev);
				ds.setMaxIdle(maxIdlev);
				ds.setMaxWait(maxWaitv);
				ds.setInitialSize(initialSizev);
				dscache.put(key, ds);
			}

			BasicDataSource ds = (BasicDataSource) dscache.get(key);

			return ds.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
