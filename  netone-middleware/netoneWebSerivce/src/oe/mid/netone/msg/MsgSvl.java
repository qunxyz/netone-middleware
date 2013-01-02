package oe.mid.netone.msg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.jl.common.workflow.DbTools;

public class MsgSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MsgSvl() {
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
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String userid=request.getParameter("userid");
		String type=request.getParameter("type");
		String firsttime=request.getParameter("firsttime");
		String lasttime=request.getParameter("lasttime");
		String lsh=request.getParameter("lsh");
		
		List list=new ArrayList();

		if("01".equals(type)){// 返回公开和定向消息
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";
			sql+="where participant!='"+userid+"' and  column3='"+userid+"' or column3='' order by timex desc limit 0,30";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+="where participant!='"+userid+"' and column3='"+userid+"' or column3=''  timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+="where participant!='"+userid+"' and column3='"+userid+"' or column3=''  timex<'"+lasttime+"' order by timex desc  limit 0,30";
			}
			list=DbTools.queryData(sql);
		}else if("02".equals(type)){//返回公开的用户信息
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";

			sql+="where participant!='"+userid+"' and column3='' order by timex desc  limit 0,30";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+="where participant!='"+userid+"' and column3=''  timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+="where participant!='"+userid+"' and column3=''  timex<'"+lasttime+"' order by timex desc  limit 0,30";
			}
			list=DbTools.queryData(sql);
		}else if("03".equals(type)){//返回针对我的消息
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";

			sql+="where column3='"+userid+"' order by timex desc  limit 0,30";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3='"+userid+"' timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3='"+userid+"'  timex<'"+lasttime+"' order by timex desc limit 0,30";
			}
			list=DbTools.queryData(sql);
		}else if("04".equals(type)){//我的消息被关注的列表
			String sql="select column3 lshx,column4 rellsh,column5 types from dyform.DY_251356887574361 ";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3='"+userid+"' timex>'"+firsttime+"'";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3='"+userid+"'  timex<'"+lasttime+"'";
			}
			sql+=" order by timex desc limit 0,30";
			List listtmp=DbTools.queryData(sql);
			
			for (Iterator iterator = listtmp.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String typex=(String)object.get("types");
				String rellsh=(String)object.get("rellsh");
				String lshx=(String)object.get("lshx");
				if("01".equals(typex)){
					//获得主消息的内容
					String sqlF="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh='"+lshx+"'";
					List listTmp1=DbTools.queryData(sqlF);
					Map data=new HashMap();
					if(listTmp1!=null&&listTmp1.size()==1){
						data= (Map)listTmp1.get(0);
					}					
					
					String sql_rp="select concat('t',timex) timex, column3 context,column5 atturl,participant sendercode,column6 sendername from dyform.DY_391356510840526 where lsh='"+rellsh+"'";
					List listTmpx=DbTools.queryData(sql_rp);
					if(listTmpx!=null&&listTmpx.size()==1){
						data.put("rel", listTmpx.get(0));
					}
					list.add(data);
				}else if("02".equals(typex)){
					//获得主消息的内容(转发)
					String sqlF="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh='"+lshx+"'";
					List listTmp1=DbTools.queryData(sqlF);
					Map data=new HashMap();
					if(listTmp1!=null&&listTmp1.size()==1){
						data= (Map)listTmp1.get(0);
					}
					
					String sql_rt="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh='"+rellsh+"'";
					List listTmpx=DbTools.queryData(sql_rt);
					if(listTmpx!=null&&listTmpx.size()==1){
						data.put("rel", listTmpx.get(0));
					}
					list.add(data);
				}else if("03".equals(typex)){
					//获得主消息的内容（评论的评论模式）
					String sqlF="select concat('t',timex) timex, column3 context,column5 atturl,participant sendercode,column6 sendername from dyform.DY_391356510840526 where lsh='"+lshx+"'";
					List listTmp1=DbTools.queryData(sqlF);
					Map data=new HashMap();
					if(listTmp1!=null&&listTmp1.size()==1){
						data= (Map)listTmp1.get(0);
					}
					
					String sql_rp="select concat('t',timex) timex, column3 context,column5 atturl,participant sendercode,column6 sendername from dyform.DY_391356510840526 where lsh='"+rellsh+"'";
					List listTmpx=DbTools.queryData(sql_rp);
					if(listTmpx!=null&&listTmpx.size()==1){
						data.put("rel", listTmpx.get(0));
					}
					list.add(data);
				}
			}
		}else if("05".equals(type)){// 我的消息明细带评论
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";

			sql+= "where lsh='"+lsh+"'";
			String sqlapp="select concat('t',timex) timex, column3 context,column5 atturl,participant sendercode,column6 sendername from dyform.DY_391356510840526 where fatherlsh= '"+lsh+"' order by timex  limit 0,30";
			if(StringUtils.isNotEmpty(lasttime)){
				sqlapp="select concat('t',timex) timex, column3 context,column5 atturl,participant sendercode,column6 sendername from dyform.DY_391356510840526 where fatherlsh= '"+lsh+"' and timex>'"+lasttime+"' order by timex   limit 0,30";
			}		
			list=DbTools.queryData(sql);
			if(StringUtils.isNotEmpty(sqlapp)){
				list.addAll(DbTools.queryData(sqlapp));
			}
		}

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
		this.doGet(request, response);
	}

}
