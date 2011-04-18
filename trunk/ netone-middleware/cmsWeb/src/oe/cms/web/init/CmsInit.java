package oe.cms.web.init;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;

import oe.cms.dao.infocell.JppMiddwareDIY;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.dao.infomodel.RichLevel;
import oe.cms.runtime.timeTrigger.InTimeTrigger;
import oe.env.client.EnvService;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;

public class CmsInit extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public CmsInit() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		//WebCache.removeallCache();
		//System.out.println("--清空已有的缓存--");
		// // 启动计时器
		// InTimeTrigger tig = (InTimeTrigger) CmsEntry
		// .fetchBean(CmsBean._INTIME_TRIGGER);
		// EnvService env = null;
		// try {
		// env = (EnvService) RmiEntry.iv("envinfo");
		// env.fetchAllJppScript();
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (NotBoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// try {
		// String isServer = env.fetchEnvValue("isServer");
		// if ("true".equals(isServer)) {
		// // 初始化portal池，参考配置文档中的参数决定是否初始化
		// // tig.initStart();
		// // // 启动定时缓存处理器，参考配置文档中的参数决定是否初始化
		// // System.out.println(" 初始化定时器.....");
		// // // 定时初始化
		// // Scheduler sched = tig.initdo();
		// // sched.start();
		// // 定时持久化
		// // Scheduler sched1 = tig.serildo();
		// // sched1.start();
		// // 定时打包备份程序
		// // Scheduler sched2 = tig.zipdo();
		// // sched2.start();
		//
		// // System.out.println(" done!");
		// // ModelDao modeldao = (ModelDao) CmsEntry.fetchDao("modelDao");
		// // modeldao.initview();
		// // modeldao.initNewCreateView();
		// // modeldao.initNewModifyView();
		// // RichLevel richLevel = (RichLevel) CmsEntry
		// // .fetchDao("richLevel");
		// // richLevel.doAutoTask();
		// env.addEnv("curl", env.fetchEnvValue("consoleServer"));
		//
		// } else {
		// String wait = env.fetchEnvValue("subServerWait");
		// T1.wait(Integer.parseInt(wait));
		// System.out.println(".....成功连接到服务器注册！");
		//
		// regIp(env.fetchEnvValue("contextName"), env
		// .fetchEnvValue("consoleServer"));
		// System.out.println();
		// System.out.println("启动Oesee客户端.........done!");
		// System.out.println();
		// System.out
		// .println("你可以使用IE浏览器,输入http://localhost:8000 访问Oesee客户端");
		// }
		 // 初始化用户自定义模板
//		JppMiddwareDIY jppMiddwareDIY = (JppMiddwareDIY) CmsEntry
//				.fetchDao("jppMiddwareDIY");
//		jppMiddwareDIY.initJppmidwarePool();
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void regIp(String clientname, String severip) {

		URL rul;
		InputStream input = null;

		EnvService env = null;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rul = new URL(severip + "/sysmain/syssite/regeditSys.do?nameinfo="
					+ clientname);
			URLConnection urlc = rul.openConnection();

			input = urlc.getInputStream();
			byte[] info = new byte[100];
			int read = input.read(info);
			String infoStr = new String(info, 0, read);
			if (infoStr != null) {
				infoStr = infoStr.substring(0, infoStr.length() - 4) + "8000";
				System.out.println(infoStr);
			}
			env.addEnv("curl", infoStr);
			// save(ipinfo);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void read() {
		InputStream input = null;
		try {
			input = new FileInputStream("IOTest.java");
			int read = 0;
			while ((read = input.read()) != -1) {
				System.out.print((char) read);
			}

			// input.reset();
			// while ((read = input.read()) != -1) {
			// System.out.print((char) read);
			// }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] arg) {
		CmsInit in = new CmsInit();
		try {
			in.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		// TODO Auto-generated method stub

	}
}
