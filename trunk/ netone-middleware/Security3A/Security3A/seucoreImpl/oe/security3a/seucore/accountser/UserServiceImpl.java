package oe.security3a.seucore.accountser;

import oe.frame.orm.util.IdServer;
import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.accountser.UserDao;
import oe.security3a.seucore.accountser.UserService;


/**
 * 用户服务接口实现
 * 
 * @author ni.he.qing
 * 
 */
public class UserServiceImpl implements UserService {

	public static final String Login_Key = "login";

	private String sessionTimeOut;

	private String tokenTimeout;

	public String getTokenTimeout() {
		return tokenTimeout;
	}

	public void setTokenTimeout(String tokenTimeout) {
		this.tokenTimeout = tokenTimeout;
	}

	public String getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(String sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public String getLoginSeq() {
		return IdServer.uuid();
	}

	public UserDao fetchDao() {
		return (UserDao) SeudaoEntry.iv("userDao");
	}

	public UserDao fetchDao(String pointto) {
		return (UserDao) SeudaoEntry.iv(pointto);
	}

}