package oe.rmi.message.mail.user;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DBOperator {


	private static Connection conn;

	public static Connection getConn() {

		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = rmi
					.loadResourceByNatural("DATASOURCE.DATASOURCE.EMAIL");
			String dbinfo = upo.getExtendattribute();
			String []driverinfo=StringUtils.split(dbinfo,"#");
			if(driverinfo.length==4){
				String driverName = driverinfo[0];
				String dbURL = driverinfo[1];
				String userName = driverinfo[2];
				String userPwd = driverinfo[3];
				try {
					Class.forName(driverName);
					conn = DriverManager.getConnection(dbURL, userName, userPwd);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return conn;
			}


		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;

	}

	public static void freeDbConnectoin(Connection conn,
			PreparedStatement psmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (psmt != null) {
				psmt.close();
				psmt = null;
			}
			if (!conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
