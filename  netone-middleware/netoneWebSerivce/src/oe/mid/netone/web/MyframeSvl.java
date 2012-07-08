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

import net.sf.json.JSONArray;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.RMI_SER;
import com.jl.common.netone.RMI_SER_obj;
import com.jl.common.workflow.DbTools;
import com.jl.common.workflow.WfEntry;
/**
 * 待办任务
 * @author robanco
 *
 */
public class MyframeSvl extends HttpServlet {

	/**
	 * xuwei(2012-5-4) 手机上我的代办 展示的服务；
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
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("userid");
		String model = request.getParameter("model");
		String naturalname=request.getParameter("naturalname");
		
		String extcondString="";
		if(StringUtils.isNotEmpty(naturalname)){
			extcondString=" and w3.appname='"+naturalname+"' ";
		}
		
		if (model == null) {
			model = "0";
		}
		String sqlStr = null;
		String json = null;
		List list = null;
		List list1 = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 我的代办列表
		if (model.equals("0")) {
			sqlStr = "select w3.lsh lsh,w3.appname naturalname,w1.runtimeid runtimeid,w2.workcode "
					+ "workcode,left(w3.d0,10) actname,concat(substring(w1.starttime,6,2),'-',substring(w1.starttime,9,2),' ',substring(w1.starttime,12,2),':',substring(w1.starttime,15,2)) starttime,w2.commitername "
					+ "commitername,w2.commitercode  commiter,w3.appname appname from netone.t_wf_worklist "
					+ "w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE "
					+ "left join netone.t_wf_relevantvar_tmp w3 on w3.runtimeid=w1.runtimeid"
					+ " where w1.EXECUTESTATUS='01' and w2.usercode='"
					+ username
					+ "' and w2.statusnow='01'"
					+ "  and w2.types in('01','02') "+extcondString+" order by w1.starttime desc";
		}
		// 当前代办工单数
		if (model.equals("1")) {

			sqlStr = "select   count(*) as countnum  from netone.t_wf_worklist "
				+ "w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE "
				+ "left join netone.t_wf_relevantvar_tmp w3 on w3.runtimeid=w1.runtimeid"
				+ " where w1.EXECUTESTATUS='01' and w2.usercode='"
				+ username
				+ "' and w2.statusnow='01'"
				+ "  and w2.types in('01','02') "+extcondString+" order by w1.starttime desc";
		}
		// 当前已办工单列表
		if (model.equals("2")) {

			sqlStr = "SELECT w3.lsh lsh,w3.appname naturalname,w1.runtimeid runtimeid,w2.workcode "
					+ "workcode,left(w3.d0,10) actname,concat(substring(w1.starttime,6,2),'-',substring(w1.starttime,9,2),' ',substring(w1.starttime,12,2),':',substring(w1.starttime,15,2)) starttime,w2.commitername "
					+ "commitername,w2.commitercode  commiter,w3.appname appname FROM netone.t_wf_worklist w1 LEFT JOIN netone.t_wf_participant w2 ON  "
					+ "w1.workcode=w2.WORKCODE LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid=w1.runtimeid left join netone.t_wf_runtime wx on wx.runtimeid=w1.runtimeid  WHERE w2.usercode='"
					+ username
					+ "' AND w2.statusnow='02'  and wx.statusnow='01' AND w2.types IN ('01','02')"+extcondString+"" ;
		}
		// 已办工单数
		if (model.equals("3")) {
			sqlStr = "SELECT count(*) as countnum  FROM netone.t_wf_worklist w1 LEFT JOIN netone.t_wf_participant w2 ON  "
				+ "w1.workcode=w2.WORKCODE LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid=w1.runtimeid left join netone.t_wf_runtime wx on wx.runtimeid=w1.runtimeid  WHERE w2.usercode='"
				+ username
				+ "' AND w2.statusnow='02'  and wx.statusnow='01' AND w2.types IN ('01','02')"+extcondString+"" ;

		}
		// 已办且归档
		if (model.equals("4")) {

			sqlStr = "SELECT w3.lsh lsh,w3.appname naturalname,w1.runtimeid runtimeid,w2.workcode "
					+ "workcode,left(w3.d0,10) actname,concat(substring(w1.starttime,6,2),'-',substring(w1.starttime,9,2),' ',substring(w1.starttime,12,2),':',substring(w1.starttime,15,2)) starttime,w2.commitername "
					+ "commitername,w2.commitercode  commiter,w3.appname appname FROM netone.t_wf_worklist w1 LEFT JOIN netone.t_wf_participant w2 ON  "
					+ "w1.workcode=w2.WORKCODE LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid=w1.runtimeid left join netone.t_wf_runtime wx on wx.runtimeid=w1.runtimeid WHERE w2.usercode='"
					+ username
					+ "' AND w2.statusnow='02' and wx.statusnow='02' AND w2.types IN ('01','02')"+extcondString+"";
		}
		// 已办且归档总数
		if (model.equals("5")) {
			
			sqlStr = "SELECT  count(*) as countnum FROM netone.t_wf_worklist w1 LEFT JOIN netone.t_wf_participant w2 ON  "
				+ "w1.workcode=w2.WORKCODE LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid=w1.runtimeid left join netone.t_wf_runtime wx on wx.runtimeid=w1.runtimeid WHERE w2.usercode='"
				+ username
				+ "' AND w2.statusnow='02' and wx.statusnow='02' AND w2.types IN ('01','02')"+extcondString+"";

		}
		// 所有工单
		if (model.equals("6")) {

			sqlStr = "SELECT w3.lsh lsh,w3.appname naturalname,w1.runtimeid runtimeid,w2.workcode "
					+ "workcode,left(w3.d0,10) actname,concat(substring(w1.starttime,6,2),'-',substring(w1.starttime,9,2),' ',substring(w1.starttime,12,2),':',substring(w1.starttime,15,2)) starttime,w2.commitername "
					+ "commitername,w2.commitercode  commiter,w3.appname appname FROM netone.t_wf_worklist w1 LEFT JOIN netone.t_wf_participant w2 ON  "
					+ "w1.workcode=w2.WORKCODE LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid=w1.runtimeid WHERE w2.usercode='"
					+ username
					+ "' AND w2.types IN ('01','02')"+extcondString+"";
		}
		// 所有工单数
		if (model.equals("7")) {
		
			sqlStr = "SELECT count(*) as countnum FROM netone.t_wf_worklist w1 LEFT JOIN netone.t_wf_participant w2 ON  "
				+ "w1.workcode=w2.WORKCODE LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid=w1.runtimeid WHERE w2.usercode='"
				+ username
				+ "' AND w2.types IN ('01','02')"+extcondString+"";

		}
		RMI_SER rmi = new RMI_SER();
		RMI_SER_obj rmiobj = rmi.RMI_SER();
		if (sqlStr != null) {
			list = DbTools.queryData(sqlStr);

			if (model.equals("0")||model.equals("2")||model.equals("4")||model.equals("6")) {
				list1 = new ArrayList();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();
					
					String appname=(String) map.get("appname");
					String lsh=(String) map.get("lsh");
					String webUrl=rmiobj.getWEBSER_APPFRAME()+"frame.do?method=onEditViewMain&naturalname="+appname+"&lsh="+lsh + "&workcode="
					+ map.get("workcode")
					+ "&operatemode=01&commiter=" + username;
					
					String processid=null;
					String actid=null;
					String workcode=(String)map.get("workcode");
					if(StringUtils.isNotEmpty(workcode)){
					TWfWorklist twf=null;
					try {
						twf = WfEntry.iv().loadWorklist(workcode);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 actid=twf.getActivityid();
					 processid=twf.getProcessid();
					}
					map.put("url", webUrl);
					String string="function${";
					string=string+"\"27\":"+"{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/QuerySvl?appname="+appname+"&lsh="+map.get("lsh")+"\",\"parameter\":\"\",\"displayfield\":\"\",\"imgae\":\"\"},"+
					"\"29\":{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/Queryform?appname="+appname+"\",\"parameter\":\"\",\"displayfield\":\"\",\"imgae\":\"\"},"+
					"\"30\":{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/listnextrouteactive?processid="+processid+"&activeid="+actid+"&runtimeid="+map.get("runtimeid")+"&commiter="+map.get("commiter")+"\",\"parameter\":\"\",\"displayfield\":\"\",\"imgae\":\"\"},"+
					"\"31\":{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/listnextbackactive?runtimeid="+map.get("runtimeid")+"\",\"parameter\":\"\",\"displayfield\":\"\",\"imgae\":\"\"},"+
					"\"32\":{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/loadcfgactive?naturalname="+map.get("naturalname")+"&commiter="+map.get("commiter")+"&runtimeid="+map.get("runtimeid")+"\",\"parameter\":\"[actid]\",\"displayfield\":\"\",\"imgae\":\"\"},"+
					"\"33\":{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/saveauditnote?workcode="+map.get("workcode")+"\",\"parameter\":\"[participant,note]\",\"displayfield\":\"\",\"imgae\":\"\"},"+
					"\"34\":{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/nextbymanual?workcode="+map.get("workcode")+"\",\"parameter\":\"[actid,clientId]\",\"displayfield\":\"\",\"imgae\":\"\"}," +
					"\"26\":{\"url\":\""+rmiobj.getWEBSER_WebSerivce()+"/UpdataSvl?appname="+map.get("naturalname")+"\",\"parameter\":\"[lsh,userid]\",\"displayfield\":\"\",\"imgae\":\"\"}}$;";
					map.put("serve", string);
					map.put("appname",appname);
					map.remove("lsh");
					map.remove("runtimeid");
					map.remove("workcode");
					map.remove("naturalname");
					list1.add(map);
				}
				json = JSONArray.fromObject(list1).toString();
			} else {
				json = JSONArray.fromObject(list).toString();
			}

		} else {
			json = JSONArray.fromObject(list).toString();
		}

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(json);
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
