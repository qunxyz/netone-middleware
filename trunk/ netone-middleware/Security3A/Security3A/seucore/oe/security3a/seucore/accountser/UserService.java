package oe.security3a.seucore.accountser;


/**
 * 操作用户的接口
 * 
 * @author hls,chen.jia.xun
 * 
 */
public interface UserService {

	String Login_Key = "login";

	public UserDao fetchDao();

	public UserDao fetchDao(String pointto);




}