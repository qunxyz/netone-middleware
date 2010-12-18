package oe.security3a.sso;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;


public class ClerkUtil {
	
	public static String getNameFromLoginname(String code,String loginname){
		try{
			UserService userservice = (UserService) SeuserEntry.iv("userService");
			Clerk clerk = (Clerk) userservice.fetchDao().loadObject(code, loginname);
			return clerk.getName();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

