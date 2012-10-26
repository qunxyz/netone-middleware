package com.jl.common.workflow;

import java.io.IOException;
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

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import oe.frame.web.WebCache;

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
			//�����Ա����������ǰ5��
			list=this.menTop5(rootname);
		}else if("2".equals(type)){
			//��ò��Ź���������ǰ5��
			list=this.deptTop5(rootname);
		}else if("3".equals(type)){
			//������̹���������ǰ5��
			list=this.flowTop5(rootname);
		}else{
			list=new ArrayList();
			Map find=new HashMap();
			find.put("value", "0");
			find.put("name", "�����������");
			list=this.flowTop5(rootname);
		}
		
		response.setContentType("text/html;charset=gbk");
		String info=JSONArray.fromObject(list).toString();
		response.getWriter().print(info);

	}
	
	private List menTop5(String deptid){
		String sql="select username name,count(*) value from t_wf_participant where STATUSNOW='01' group by usercode order by value desc limit 0,5";
		return DbTools.queryData(sql);
	}
	
	private List deptTop5(String deptid){
		String processid="all";//��ʾ��������
		//�Ӻ�̨�����л�����еĲ��Ź�������Ϣ
		Object obj=WebCache.getCache(deptid+processid);
		List data=new ArrayList();
		if(obj!=null){
			data=(List)obj;
		}
		
		List depttask=new ArrayList();//��ʱ�������ڴ�����в��ŵĹ����������淽������
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String doing=(String)object.get("this_doing");
			String num=StringUtils.substringAfter(doing, "#");
			int value=Integer.parseInt(num);
			depttask.add(value);
		}
		// ����������
		Integer []deptMa=(Integer[])depttask.toArray(new Integer[0]);
		Arrays.sort(deptMa);
		// �ҳ�����������ǰ5�����ţ�Ҳ����top5
		List listMaxTaskDept=new ArrayList();
		for (int i = 1; i <=5; i++) {
			int valuepre=deptMa[deptMa.length-i];
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String doing=(String)object.get("this_doing");
				//doing���ݸ�ʽ��#�����Ǿ���Ĺ�����ֵ
				String num=StringUtils.substringAfter(doing, "#");
				int value=Integer.parseInt(num);
				if(value==valuepre){
					Map find=new HashMap();
					find.put("value", value);
					find.put("name", object.get("deptname"));
					listMaxTaskDept.add(find);
					break;
				}
			}
		}
		return listMaxTaskDept;
	}
	
	private List flowTop5(String deptid){
		//ͨ��sql��ѯ�����е�����
		String sql="select NATURALNAME from ums_protectedobject where PARENTDIR in(select id from ums_protectedobject where NATURALNAME='BUSSWF.BUSSWF.NDYD') and"+
		"NATURALNAME!='BUSSWF.BUSSWF.NDYD.NRSP' and active='1'";
		List list=DbTools.queryData(sql);
		
		//�Ӻ�̨cache�л���������̵��ܴ��칤����
		Map procecmax_tmp=new HashMap();
		List procemax=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String key1=(String)object.get("NATURALNAME");
			Map data1=(Map)WebCache.getCache("lv_proc"+key1);
			Long value=(Long)data1.get("doing");
			procecmax_tmp.put(key1, value);
			procemax.add(procecmax_tmp);
		}
		
		Long []procMa=(Long[])procemax.toArray(new Long[0]);
		Arrays.sort(procMa);
		List procemaxFinal=new ArrayList();
		for (int i = 1; i <=5; i++) {
			long valuepre=procMa[procMa.length-i];
			for (Iterator iterator = procecmax_tmp.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				long doing=(Long)procecmax_tmp.get(key);

				if(doing==valuepre){
					Map find=new HashMap();
					find.put("value", doing);
					find.put("name",key);
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
