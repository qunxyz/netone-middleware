package oe.security3a.sso;

public interface LoginInfo {

	String[] _ERROR_1 = { "0", "验证码不正确!" };

	String[] _ERROR_2 = { "1", "用户名不能为空！" };

	String[] _ERROR_3 = { "2", "密码不能为空！" };

	String[] _ERROR_4 = { "3", "已经过期!" };
	String[] _ERROR_5 = { "4", "用户不存在！" };
	String[] _ERROR_6 = { "5", "用户密码错误！" };

	String[] _ERROR_7 = { "6", "登陆失败,请检查四位代码！" };
	
	String[] _ERROR_8 = { "7", "登陆失败,该账户已在线!" };
	
	String[] _ERROR_9 = { "8", "登陆失败,该账户禁用!" };

}
