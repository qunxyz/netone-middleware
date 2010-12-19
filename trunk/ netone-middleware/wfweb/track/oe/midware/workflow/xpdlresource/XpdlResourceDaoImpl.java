package oe.midware.workflow.xpdlresource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.mid.io.CommonIoTools;
import oe.midware.workflow.design.WebWorkflow;
import oe.midware.workflow.engine.XpdlModelHandlerImp;
import oe.midware.workflow.runtime.XpdlModelHandler;

import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 */
public class XpdlResourceDaoImpl implements XpdlResourceDao {

	private WebWorkflow webWorkflow;

	private XpdlModelHandler xpdlModelHandler;

	public void saveOpe(String context, String processid, String processname) {
		if (context == null) {
			context = "";
		}
		String webpath = "file:/" + System.getProperty("user.dir") + "/wf/";
		webpath=StringUtils.replace(webpath,"\\", "/");
		saveFile(context, webpath, processid);

	}

	/**
	 * 删除文件
	 * 
	 * @param filepath
	 *            文件路径
	 * @param filename
	 *            文件名
	 * @param proFile
	 * @return
	 */
	public boolean dropOpe(String processid) {
		String webpath = "file:/" + System.getProperty("user.dir") + "/wf/";
		webpath=StringUtils.replace(webpath,"\\", "/");
		URI uri = null;
		URI uri2 = null;
		try {
			uri = new URI(webpath + processid + ".xml");
			uri2 = new URI(webpath + processid + ".$xml");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = new File(uri);
		return file.renameTo(new File(uri2));

	}

	public boolean updateOpe(String context, String processid,
			String processname) {
		String webpath = "file:/" + System.getProperty("user.dir") + "/wf/";
		webpath=StringUtils.replace(webpath,"\\", "/");
		saveFile(context, webpath, processid);
		return false;
	}

	final String CONDITION_HEAD = "<Condition Type=\"CONDITION\">";

	final String CONDITION_END = "</Condition>";

	public static String _CONDTION_PRE = "<![CDATA[";

	public static String _CONDTION_END = "]]>";

	private String predealwithCondition(String xpdlinfo) {
		String new_condition_head = CONDITION_HEAD + _CONDTION_PRE;
		String new_condition_end = _CONDTION_END + CONDITION_END;
		xpdlinfo = StringUtils.replace(xpdlinfo, CONDITION_HEAD,
				new_condition_head);
		return StringUtils.replace(xpdlinfo, CONDITION_END, new_condition_end);

	}

	private void saveFile(String context, String webpath, String storename) {

		context = predealwithCondition(context);

		byte[] xpdlContendByte = context.getBytes();
		ByteArrayInputStream bAInput = new ByteArrayInputStream(xpdlContendByte);
		OutputStream out = null;
		URI uri = null;
		try {
			uri = new URI(webpath + storename + ".xml");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = new File(uri);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			out = new FileOutputStream(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CommonIoTools.inputDo(bAInput, out);
	}

	public String viewOpe(String id) {
		WorkflowProcess procView = xpdlModelHandler
				.fetchXpdlWorkflowByProcessid(id);

		return webWorkflow.fetchWorkflow(procView);
	}

	public WebWorkflow getWebWorkflow() {
		return webWorkflow;
	}

	public void setWebWorkflow(WebWorkflow webWorkflow) {
		this.webWorkflow = webWorkflow;
	}

	public XpdlModelHandler getXpdlModelHandler() {
		return xpdlModelHandler;
	}

	public void setXpdlModelHandler(XpdlModelHandler xpdlModelHandler) {
		this.xpdlModelHandler = xpdlModelHandler;
	}

	public String xpdldescription(String processid) {
		// TODO Auto-generated method stub
		return null;
	}

}
