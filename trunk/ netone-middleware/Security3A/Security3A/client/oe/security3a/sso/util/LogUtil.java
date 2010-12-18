package oe.security3a.sso.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

/**
 * 关于日志登记的相关字典类<br>
 * 
 * Mar 9, 2009 11:13:05 AM<br>
 * 
 * @author wu.shang.zhan<br>
 */
public class LogUtil {

	// 操作类型 查看 增加 修改 删除
	//其中增加失败和成功区分，便于确认资源所有者的查询
	public static final String _READ = "read";
	
	public static final String _ADD = "add";

	public static final String _EDIT = "edit";

	public static final String _DEL = "del";

	// 操作结果
	public static final String _ADD_SUCCESS = "创建成功";
	public static final String _ADD_FAIL = "创建失败";

	public static final String _EDIT_SUCCESS = "编辑成功";
	public static final String _EDIT_FAIL = "编辑失败";

	public static final String _DEL_SUCCESS = "删除成功";
	public static final String _DEL_FAIL = "删除失败";

}
