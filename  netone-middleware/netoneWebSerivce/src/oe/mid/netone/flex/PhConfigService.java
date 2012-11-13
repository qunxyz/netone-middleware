package oe.mid.netone.flex;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.RMI_SER_obj;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.DbTools;

public class PhConfigService extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PhConfigService() {
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
		List listx = new ArrayList();
		request.setCharacterEncoding("utf-8");
		String naturalname = request.getParameter("naturalname");
		ResourceRmi rs;
		EnvService env;
		try {
			rs = (ResourceRmi)RmiEntry.iv("resource");
			env = (EnvService) RmiEntry.iv("envinfo");
			String webservice=env.fetchEnvValue("WEBSER_WebSerivce");
		String id=rs.loadResourceByNatural(naturalname).getId();
//
//		String sqlStr = "SELECT naturalname,name,objectType,id,extendattribute,inclusion,ACTIVE FROM netone.ums_protectedobject WHERE PARENTDIR='"
//				+ upobj.getId() + "'ORDER BY aggregation ";
//		List list = DbTools.queryData(sqlStr);
		List applist=rs.subResource(id);
		for (Iterator iterator = applist.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();

			phConfigobj phobj = new phConfigobj();
			phobj.setDistinguish(object.getInclusion());
			phobj.setNaturalname(object.getNaturalname());
			
			phobj.setResourcename(object.getName());
			phobj.setImagename(object.getId()+"."+object.getDescription());
			if("1".equals(object.getInclusion())){
				phobj.setImagename("dir.png");
				phobj.setTypes(object.getObjecttype());
				phobj.setExtendattribute(object.getExtendattribute());
			}else	if ("0".equals(object.getInclusion())) {
				//如果是应用程序时的处理
				String mobileAppName=object.getObjecttype();
				if (StringUtils
						.isNotEmpty(mobileAppName)) {
					mobileAppName=StringUtils.substringBetween(mobileAppName, "[","]");
					//具体功能配置位于
					UmsProtectedobject upob = rs.loadResourceByNatural(mobileAppName);
					String appconfig=upob.getExtendattribute();
					//生产实际的服务地址
					appconfig=StringUtils.replace(appconfig, "$(ms)", webservice);
					//处理参数(参数是在 phone.phone资源中配置的)
					String param=object.getActionurl();
					String paramX[]=StringUtils.split(param,";");
					for (int i = 0; i < paramX.length; i++) {
						String []paramKV=paramX[i].split(":");
						if(paramKV.length!=2){
							continue;
						}
						String paramKey="$("+paramKV[0]+")";
						appconfig=StringUtils.replace(appconfig, paramKey, paramKV[1]);
					}
					phobj.setExtendattribute(appconfig);
					phobj.setTypes(upob.getObjecttype());
				}
			}
			if ("1".equals(object.getActive())) {
				if (!object.getNaturalname().equals(naturalname)) {
					listx.add(phobj);
				}
			}
		}
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(listx).toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(json);
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
