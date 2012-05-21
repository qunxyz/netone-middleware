package oe.netone.dy;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.io.*;
import java.util.*;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class ConnDB {
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet rs = null;

	private static String dbDriver;
	private static String dbUrl;
	private static String dbUser;
	private static String dbPwd;
   
	public static Connection getConnection() {

//		XmlAnalysic xmlanalysic=new XmlAnalysic();
//		//List<SQLData> lsitxml=xmlanalysic.readXML();
//		System.out.println(lsitxml.get(0).getQudong()+"   "+lsitxml.get(0).getLujing()+"  "+lsitxml.get(0).getName()+"  "+lsitxml.get(0).getPaw());
//		dbDriver=lsitxml.get(0).getQudong();
//		dbUrl=lsitxml.get(0).getLujing();
//		dbUser=lsitxml.get(0).getName();
//		dbPwd=lsitxml.get(0).getPaw();
		Connection conn = null;
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbUser,dbPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conn == null) {
			System.err.println("警告:数据库连接失败!");
		}
		return conn;
	}
	

	public ResultSet doQuery(String sql) {
		try {
			conn = ConnDB.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
public int userlogin(String sql) {
		int a = 0;
		try {
			conn = ConnDB.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				a =rs.getInt("STATUSINFO");
			} 
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}


	public int doUpdate(String sql) {
		int result = 0;
		try {
			conn = ConnDB.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql);

		} catch (SQLException e) {
			result = 0;
		}
		return result;
	}

	
	public void closeConnection() {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getURLContent(String url) {
		  StringBuffer content = new StringBuffer();
		  try {
		   // 新建URL对象
		   URL u = new URL(url);
		   InputStream in = new BufferedInputStream(u.openStream());
		   InputStreamReader theHTML = new InputStreamReader(in, "UTF-8");
		   int c;
		   while ((c = theHTML.read()) != -1) {
		    content.append((char) c);
		   }
		  }
		  // 处理异常
		  catch (MalformedURLException e) {
		   System.err.println(e);
		  } catch (IOException e) {
		   System.err.println(e);
		  }
		  return content.toString();
		 }
/*	public static void main(String[] args) {
		System.out.println(getURLContent("http://127.0.0.1:81/Security3A/mod/LoginMvl?name=adminx&password=xan625"));
	}*/

}
