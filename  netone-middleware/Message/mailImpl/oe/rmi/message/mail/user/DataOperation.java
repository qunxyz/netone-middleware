package oe.rmi.message.mail.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.util.Encryption;

import org.apache.james.security.DigestUtil;



public class DataOperation {
	private static ResourceBundle messages = ResourceBundle.getBundle("mailuser", Locale.CHINESE);

	public boolean addUserInfo(Clerk clerk,String key) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBOperator.getConn();
			ps = conn.prepareStatement(messages.getString("addUserInfo"));
			ps.setString(1, clerk.getDescription());
			String password=Encryption.encry(clerk.getPassword(), key, false);
			ps.setString(2, DigestUtil.digestString(password, "SHA"));
			ps.setString(3, "SHA");
			ps.setString(4, "0");
			ps.setString(5, "0");
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DBOperator.freeDbConnectoin(conn, ps, null);
		}
		return true;
	}

	public boolean updateUserInfo(Clerk clerk) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBOperator.getConn();
			ps = conn.prepareStatement(messages.getString("updateUserInfo"));
			ps.setString(1, DigestUtil.digestString(clerk.getPassword(), "SHA"));
			ps.setString(2, "SHA");
			ps.setString(3, "0");
			ps.setString(4, "0");
			ps.setString(5, clerk.getDescription());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DBOperator.freeDbConnectoin(conn, ps, null);
		}
		return true;
	}

	public boolean deleteUserInfo(String loginname) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBOperator.getConn();
			ps = conn.prepareStatement(messages.getString("deleteUserInfo"));
			ps.setString(1, loginname);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DBOperator.freeDbConnectoin(conn, ps, null);
		}
		return true;
	}

	public String searchUserInfo(Clerk clerk) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userid = "";
		try {
			conn = DBOperator.getConn();
			ps = conn.prepareStatement(messages.getString("searchUserInfo"));
			ps.setString(1, clerk.getDescription());
			rs = ps.executeQuery();
			if (rs.next()) {
				userid = rs.getString("username");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBOperator.freeDbConnectoin(conn, ps, rs);
		}
		return userid;
	}
}
