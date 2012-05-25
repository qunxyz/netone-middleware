package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import org.apache.commons.lang.StringUtils;
import com.jl.common.dyform.DyFormData;

public class ListingData extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListingData() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String lsh=request.getParameter("lsh");
		String formcode = request.getParameter("formcode");
		String parentId = request.getParameter("parentId");
		if (StringUtils.isEmpty(parentId)) {
			parentId = "1";
		}
		DyFormData dydata=new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setLsh(lsh);
		dydata.setParticipant(parentId);
		DyFormService dysc = null;
		try {
			dysc = (DyFormService) RmiEntry.iv("dyhandle");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 
		List list=dysc.queryData(dydata, 0, 1000, "");
		String jsonString=JSONArray.fromObject(list).toString();  
   		response.getWriter().print(jsonString);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
