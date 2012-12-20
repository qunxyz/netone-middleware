package com.jl.common.workflow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

import org.apache.commons.lang.StringUtils;

import com.sun.xml.internal.bind.v2.model.core.ID;

public class LeaderViewOrderSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LeaderViewOrderSvl() {
		super();
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
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		
		String rootname=request.getParameter("rootname");
		String type=request.getParameter("type");
		
		List list=null;
		
		if("1".equals(type)){
			//获得人员工作量最大的前5个
			list=this.menTop5(rootname);
		}else if("2".equals(type)){
			//获得部门工作量最大的前5个
			list=this.deptTop5(rootname);
		}else if("3".equals(type)){
			//获得流程工作量最大的前5个
			list=this.flowTop5(rootname);
		}else{
			list=new ArrayList();
			Map find=new HashMap();
			find.put("value", "0");
			find.put("name", "错误参数调度");
			list=this.flowTop5(rootname);
		}
		
		response.setContentType("text/html;charset=gbk");
		String info=JSONArray.fromObject(list).toString();
		response.getWriter().print(info);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	private List menTop5(String deptid){
		String sql="select w1.username name,convert(count(*),char) value,count(*) value1 from netone.t_wf_participant w1,netone.t_wf_worklist w2 where w2.WORKCODE=w1.workcode and w1.STATUSNOW='01' and w2.EXECUTESTATUS='01' and w1.username != 'adminx' group by w1.usercode order by value1 desc limit 0,5";
		return DbTools.queryData(sql);
	}
	
	private List deptTop5(String deptid){
		String processid="all";//表示所有流程
		//从后台缓存中获得所有的部门工作量信息
		Object obj=WebCache.getCache(deptid+processid);
		List data=new ArrayList();
		if(obj!=null){
			data=(List)obj;
		}
		
		List depttask=new ArrayList();//临时数组用于存放所有部门的工作量，后面方便排序
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String doing=(String)object.get("this_doing");
			String num=StringUtils.substringAfter(doing, "#");
			int value=Integer.parseInt(num);
			depttask.add(value);
		}
		// 进行排序处理
		Integer []deptMa=(Integer[])depttask.toArray(new Integer[0]);
		Arrays.sort(deptMa);
		// 找出工作量最大的前5个部门，也就是top5
		List listMaxTaskDept=new ArrayList();
		
		Map map=new HashMap();
		for (int i = 1; i <=10 && deptMa.length >= i ; i++) {
			int valuepre=deptMa[deptMa.length-i];
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String doing=(String)object.get("this_doing");
				//doing数据格式中#后面是具体的工作量值
				String num=StringUtils.substringAfter(doing, "#");
				int value=Integer.parseInt(num);
				if(value == 0)
					continue;
				if(value==valuepre){
					String key=(String)object.get("deptname");
					if(map.containsKey(key)){
						continue;
					} else {
						map.put(key, "");
					}
					Map find=new HashMap();
					find.put("value", String.valueOf(value));
					find.put("name", key);
					listMaxTaskDept.add(find);
					break;
				}
			}
		}
		return listMaxTaskDept.subList(0, listMaxTaskDept.size()>=5?5:listMaxTaskDept.size());
	}
	
	private List flowTop5(String deptid){
		//通过sql查询出所有的流程
		String sql="select concat(NATURALNAME,'#',name) nax from netone.ums_protectedobject where PARENTDIR in(select id from netone.ums_protectedobject where NATURALNAME='BUSSWF.BUSSWF.NDYD') and "+
		"NATURALNAME!='BUSSWF.BUSSWF.NDYD.NRSP' and active='1'";
		List list=DbTools.queryData(sql);
		//从后台cache中获得所有流程的总代办工作量
		Map procecmax_tmp=new HashMap();
		List procemax=new ArrayList();
		String deptname=null;
		if(!deptid.startsWith("DEPT.DEPT")){
		try {
			ResourceRmi rsrmi=(ResourceRmi)RmiEntry.iv("resource");
			deptname=rsrmi.loadResourceById(deptid).getNaturalname();
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
		}else{
			deptname=deptid;
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String keyx=(String)object.get("nax");
			String key=StringUtils.substringBefore(keyx, "#");
//			Map data1=(Map)WebCache.getCache("lv_proc"+key);
//			if(data1==null)continue;
//			Long value=(Long)data1.get("doing");
			String keyfordata=deptname+key;
			List data1=(List)WebCache.getCache(keyfordata);
			long value=0;
			if(data1==null)continue;
			for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();) {
				Map object2 = (Map) iterator2.next();
				String rsx=(String)object2.get("this_doing");
				String size=StringUtils.substringAfter(rsx, "#");
				long sizevalue=0;
				try{
					sizevalue=Integer.parseInt(size);
				}catch(Exception e){
					e.printStackTrace();
				}
				value+=sizevalue;
			}
			
			procecmax_tmp.put(keyx, value);
			procemax.add(value);
		}
		
		Long []procMa=(Long[])procemax.toArray(new Long[0]);
		Arrays.sort(procMa);
		List procemaxFinal=new ArrayList();
		for (int i = 1; i <=5 && i<= procMa.length; i++) {
			long valuepre=procMa[procMa.length-i];
			for (Iterator iterator = procecmax_tmp.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				long doing=(Long)procecmax_tmp.get(key);
				if(doing == 0)
					continue;
				if(doing==valuepre){
					Map find=new HashMap();
					find.put("value", String.valueOf(doing));
					find.put("name",StringUtils.substringAfter(key, "#"));
					procemaxFinal.add(find);
					break;
				}
			}
		}
		return procemaxFinal;
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
	
	public static void main(String[] args) {
		
	}

}
