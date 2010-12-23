package oe.bi.etl.wizard.summarydata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.datasource.SumUtilIfc;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * ִ��ͬ��
 * 
 * @author ni.he.qing
 * 
 */
public class SumSynSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SumSynSvl() {
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
		response.setContentType("text/html;charset=GBK");
		String task = request.getParameter("task");
		String sql = request.getParameter("sql");
		String cname = request.getParameter("cname");
		String syntable = request.getParameter("syntable");
		String formcode = request.getParameter("formcode");
		String isall = request.getParameter("isall");// �ж��Ƿ�����ӣ���Ϊyes,��Ϊno
		String stepmode = request.getParameter("stepmode");// �ж�����������ͣ�����ʱ��Ϊlimittime,�Զ���N��Ϊlimitcount,�ֹ�ѡ��Ϊlimitnumber
		SumUtilIfc sumutil = null;
		try {
			sumutil = (SumUtilIfc) RmiEntry.iv("biData");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sys = "";
		if ("check".equals(task)) {
			ResourceRmi rsrmi = null;
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = rsrmi.loadResourceById(request
						.getParameter("chkid"));
				DyFormDesignService dfd = (DyFormDesignService) RmiEntry
						.iv("dydesign");
				DyObj objx = dfd.fromDy(upo.getExtendattribute());
				TCsForm tcform = objx.getFrom();
				List resultlist = sumutil.getResultBySQL(tcform.getSqlinfo(),
						tcform.getFormcode());
				request.setAttribute("resultlist", resultlist);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher(
					"/bi/etl/wizard/SummaryData/Syncheck.jsp").forward(request,
					response);
		} else {
			// �������
			if ("yes".equals(isall)) {
				sumutil.deleteTable(syntable);
				sys = sumutil.searchToAdd(sql, cname, syntable, formcode);
				// �������
			} else if ("no".equals(isall)) {
				// ����ʱ�䣨ͨ��ʶ��ʱ�� ���Զ���������ݣ�
				if ("append".equals(stepmode)) {
					sys = sumutil.searchToAdd(sql, cname, syntable, formcode);
					// �Զ����N��
				} else if ("limittime".equals(stepmode)) {
					String limittime = request.getParameter("limittime");
					sys = sumutil.searchToAddByTime(sql, cname, syntable,
							formcode, limittime);
					// �Զ����N��
				} else if ("limitcount".equals(stepmode)) {
					String limitcount = request.getParameter("limitcount");
					sys = sumutil.searchToAddByCount(sql, cname, syntable,
							formcode, limitcount);
					// �ֹ�ѡ��
				} else if ("limitnumber".equals(stepmode)) {
					String limitnumber = request.getParameter("limitnumber");
					sys = sumutil.searchToAddByNumber(sql, cname, syntable,
							formcode, limitnumber);
				}
			}
			if ("true".equals(sys)) {
				response.getWriter().print(
						"<script>alert('ͬ������!');window.close();</script>");
			} else {
				response.getWriter()
						.print(
								"<script>alert('" + sys
										+ "');window.close();</script>");
			}
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

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
