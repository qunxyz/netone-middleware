package oe.mid.netone.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import net.sf.json.JSONArray;
import com.jl.common.netone.RMI_SER;
import com.jl.common.netone.RMI_SER_obj;
import com.jl.common.workflow.DbTools;


public class MyframeSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyframeSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	String _DEFAULT_REV_KEY_CUSTOMER = "customer"; // 参与者
	String _DEFAULT_REV_KEY_BUSSID = "bussid"; // 业务ID
	String _DEFAULT_REV_KEY_BUSSURL = "bussurl"; // 业务地址
	String _DEFAULT_REV_KEY_BUSSTYPE = "busstype"; // 业务模式
	String _DEFAULT_REV_KEY_BUSSTIP = "busstip"; // 业务提示
	String _DEFAULT_REV_KEY_STATUS = "status";// 流程环节，该变量是工作流内部控制表示当前的业务环节值
	String _DEFAULT_REV_KEY_WORKCODE = "workcode";
	String _DEFAULT_REV_KEY_BUSSSTATUS = "bussstatus";// 业务状态，该字段表示流程的环节计数
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
		String username = request.getParameter("commiter");
		String model= request.getParameter("model");
		if(model==null){
		 model="0";
		}
		String sqlStr=null;
		String json=null;
		 List list=null;
		 List list1=null;
		 WorkflowView wfview = null;
			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//我的代办列表
		if(model.equals("0")){
			sqlStr="select w3.lsh lsh,w3.appname naturalname,w1.runtimeid runtimeid,w2.workcode " +
				"workcode,w3.d0 actname,w1.starttime starttime,w2.commitername " +
				"commitername,w2.commitercode  commiter from netone.t_wf_worklist " +
				"w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE " +
				"left join netone.t_wf_relevantvar_tmp w3 on w3.runtimeid=w1.runtimeid" +
				" where w1.EXECUTESTATUS='01' and w2.usercode='"+username+"' and w2.statusnow='01'" +
				"  and w2.types in('01','02') order by w1.starttime desc";
		}
		//当前代办工单数
		if(model.equals("1")){
			sqlStr= "select  count(*) as countnum from netone.t_wf_worklist w1 left join netone.t_wf_participant " +
					"w2 on w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"+username+"' and w2.statusnow='01' " +
					"and w2.types in ('01','02')";
		}
		//当前已办工单列表
		if(model.equals("2")){
 
			sqlStr="SELECT w3.lsh lsh,w3.appname naturalname,w1.runtimeid runtimeid,w2.workcode " +
					"workcode,w3.d0 actname,w1.starttime starttime,w2.commitername " +
					"commitername,w2.commitercode  commiter FROM netone.t_wf_worklist w1 LEFT JOIN netone.t_wf_participant w2 ON  " +
					"w1.workcode=w2.WORKCODE LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid=w1.runtimeid WHERE w2.usercode='"+username+"' AND w2.statusnow='02' AND w2.types IN ('01','02')";
		}
		//已办工单数
		if(model.equals("3")){
			sqlStr="select  count(*) as countnum3 from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on " +
					"w1.workcode=w2.WORKCODE where w2.usercode='"+username+"' and w2.statusnow='02' and w2.types in ('01','02')";
			
		}
		RMI_SER rmi=new RMI_SER();
		RMI_SER_obj rmiobj=rmi.RMI_SER();
		 if(sqlStr!=null){
		  list= DbTools.queryData(sqlStr);
		  String strurl=null;
		  System.out.println(list.size());
		  if(model.equals("0") || model.equals("2")){
			  list1=new ArrayList();
		    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();
					List listRev = wfview.fetchRelevantVar((String) map.get("runtimeid"));
                   
					for (Iterator iterator3 = listRev.iterator(); iterator3.hasNext();) {
						TWfRelevantvar name = (TWfRelevantvar) iterator3.next();
						String filedid = name.getDatafieldid();
						String valuenow = name.getValuenow();
						if (this._DEFAULT_REV_KEY_BUSSURL.equals(filedid)) {
							strurl=rmiobj.getWEBSER_APPFRAME()+valuenow+map.get("lsh")+"&workcode="+map.get("workcode")+"&operatemode=01&commiter="+username;
						} 
					}
					map.put("url", strurl); 
					map.remove("lsh");
					map.remove("runtimeid");
					map.remove("workcode");
					map.remove("naturalname");
					list1.add(map);
		    	}
		    	json= JSONArray.fromObject(list1).toString();
		    }else{
		    	json= JSONArray.fromObject(list).toString();
		    }
		   
		 }else{ 
		   json = JSONArray.fromObject(list).toString();
		 }
		
		  response.setContentType("text/html;charset=utf-8");
		  response.getWriter().print(json);
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
		doGet(request,response);
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
