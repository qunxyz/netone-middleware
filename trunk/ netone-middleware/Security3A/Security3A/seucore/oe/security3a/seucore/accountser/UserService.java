package oe.security3a.seucore.accountser;


/**
 * �����û��Ľӿ�
 * 
 * @author hls,chen.jia.xun
 * 
 */
public interface UserService {

	String Login_Key = "login";

	public UserDao fetchDao();

	public UserDao fetchDao(String pointto);




}