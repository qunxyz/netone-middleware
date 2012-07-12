package oe.rmi.web;

import java.io.BufferedOutputStream;
import java.io.IOException;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RmiServiceSvl extends HttpServlet {

	private Log log = LogFactory.getLog(RmiServiceSvl.class);

	/**
	 * Constructor of the object.
	 */
	public RmiServiceSvl() {
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
		response.setContentType("application/octet-stream");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");

		String contenttype = request.getContentType();
		Object obj = null;
		if ("application/octet-stream".equals(contenttype)) {
			try {
				String name = request.getParameter("name");
				String mode = request.getParameter("mode");
				log.debug("be invoked name:" + name);
				Object retObj = RmiEntry.iv(name);

				if ("ifc".equals(mode)) {
					System.out.println("--------ifc");
					obj = retObj.getClass().getInterfaces();

				} else if ("method".equals(mode)) {
					System.out.println("--------method");
					InputStream is = request.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(
							new BufferedInputStream(is));
					Object objIn = ois.readObject();
					System.out.println("--------method1");
					if (objIn instanceof RmiRequest) {
						System.out.println("--------method2");
						RmiRequest req = (RmiRequest) objIn;

						Method m = retObj.getClass().getMethod(
								req.getMethodName(), req.getParam());
						obj = m.invoke(retObj, req.getArgs());
					}

				} else {
					obj = retObj;
				}

			} catch (Exception e) {
				log.error("Rmi-Http读取请求对象出错,或着Rmi调用出错！", e);
			}

			OutputStream out;
			ObjectOutputStream objStream = null;
			out = response.getOutputStream();
			objStream = new ObjectOutputStream(new BufferedOutputStream(out));
			objStream.writeObject(obj);
			objStream.flush();
			objStream.close();
			log.debug("be invoked done!");
		} else {
			PrintWriter out = response.getWriter();
			out.println("该servlet只处理Rmi的调用！");
		}
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

		doGet(request, response);
	}
}
