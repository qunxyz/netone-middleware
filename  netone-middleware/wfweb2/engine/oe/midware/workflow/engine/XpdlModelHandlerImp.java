package oe.midware.workflow.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import oe.frame.bus.workflow.RuntimeInfo;

import oe.midware.workflow.runtime.XpdlModelHandler;

import oe.midware.workflow.xpdl.model.pkg.XPDLPackage;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.midware.workflow.xpdl.parser.XPDLParser;
import oe.midware.workflow.xpdl.parser.XPDLParserException;
import oe.midware.workflow.xpdl.parser.dom4j.Dom4JXPDLParser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * XPDL资源处理
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class XpdlModelHandlerImp implements XpdlModelHandler {


	private Log _log = LogFactory.getLog(XpdlModelHandlerImp.class);

	/**
	 * 通过系统名和xpdl文件名获得流程包
	 */
	public String fetchXpdlpackageDoc(String xpdlfilename) {
		
		String webpath = "file:/"+System.getProperty("user.dir")+"/wf/";
		webpath=StringUtils.replace(webpath,"\\", "/");
		InputStream input = null;
		try {
			URL url = new URL(webpath + xpdlfilename + ".xml");
			input = url.openStream();
			byte[] length = new byte[input.available()];
			input.read(length);
			return new String(length);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error("lose workflow defination file");
			e.printStackTrace();

			return "";
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 通过系统名和xpdl文件名获得流程包
	 */
	public XPDLPackage fetchXpdlpackage(String xpdlfilename) {
		XPDLPackage pkgs = new XPDLPackage();
		XPDLParser parser = new Dom4JXPDLParser();
		String webpath = "file:/"+System.getProperty("user.dir")+"/wf/";
		webpath=StringUtils.replace(webpath,"\\", "/");
		InputStream input = null;
		try {
			URL url = new URL(webpath + xpdlfilename + ".xml");
			input = url.openStream();
			pkgs = parser.parse(input);

		} catch (XPDLParserException e) {
			e.printStackTrace();
			_log.error(RuntimeInfo.OE_WF_DEF_ERR_009);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			_log.error("lose workflow defination file");
			e.printStackTrace();

			return null;
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return pkgs;

	}

	public WorkflowProcess fetchXpdlWorkflowByProcessid(String processid) {
		XPDLPackage pkgs = fetchXpdlpackage(processid);
		return pkgs.getWorkflowProcess(0);
	}

	public WorkflowProcess fetchXpdlWorkflowByProcessid(InputStream in) {
		XPDLPackage pkgs = new XPDLPackage();
		XPDLParser parser = new Dom4JXPDLParser();
		try {
			pkgs = parser.parse(in);

		} catch (XPDLParserException e) {
			_log.error(RuntimeInfo.OE_WF_DEF_ERR_009);
			return null;
		} catch (IOException e) {
			_log.error("lose workflow defination file");
			e.printStackTrace();
			return null;
		}
		return pkgs.getWorkflowProcess(0);
	}

}
