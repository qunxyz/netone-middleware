package oe.midware.workflow.xpdlresource;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * XPDL资源处理
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface XpdlResourceDao {
	public String _FILENAME_SEP = "_";

	void saveOpe(String context, String processid, String processname);

	boolean dropOpe(String processid);

	boolean updateOpe(String context, String processid, String processname);

	String viewOpe(String processid);
	
	String xpdldescription(String processid);


}
