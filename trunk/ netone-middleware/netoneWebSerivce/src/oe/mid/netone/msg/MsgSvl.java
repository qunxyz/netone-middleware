package oe.mid.netone.msg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		String userid=request.getParameter("userid");
		String type=request.getParameter("type");
		String firsttime=request.getParameter("firsttime");
		String lasttime=request.getParameter("lasttime");
		String lsh=request.getParameter("lsh");
		
		List list=new ArrayList();
		String sql="select timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rpnum,column8 rtnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525";
		String sqlapp="";
		if("01".equals(type)){// 返回所有用户消息
			sql+="where column3='"+userid+"' or column3='' order by timex desc limit 0,30";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3='"+userid+"' or column3=''  timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3='"+userid+"' or column3=''  timex<'"+lasttime+"' order by timex desc  limit 0,30";
			}
			
		}else if("02".equals(type)){//返回公开的用户信息
			sql+="where column3='' order by timex desc  limit 0,30";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3=''  timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3=''  timex<'"+lasttime+"' order by timex desc  limit 0,30";
			}
		}else if("03".equals(type)){//返回针对我的消息
			sql+="where column3='"+userid+"' order by timex desc  limit 0,30";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3='"+userid+"' timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3='"+userid+"'  timex<'"+lasttime+"' order by timex desc limit 0,30";
			}
		}else if("04".equals(type)){//我的消息被关注的列表
			sql+="where participant='"+userid+"' order by column9 desc limit 0,30";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3='"+userid+"' timex>'"+firsttime+"' order by timex desc limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3='"+userid+"'  timex<'"+lasttime+"' order by timex desc limit 0,30";
			}
		}else if("05".equals(type)){// 我的消息明细带评论
			sql+= "where lsh='"+lsh+"'";
			sqlapp="select column3 context,column4 atturl,timex,participant sendercode,column5 sendername from DY_391356510840526 where parentid= '"+lsh+"' order by timex  limit 0,30";
			if(StringUtils.isNotEmpty(lasttime)){
				sqlapp="select column3 context,column4 atturl,timex,participant sendercode,column5 sendername from DY_391356510840526 where parentid= '"+lsh+"' and timex>'"+lasttime+"' order by timex   limit 0,30";
			}			
		}
		list=DbTools.queryData(sql);
		if(StringUtils.isNotEmpty(sqlapp)){
			list.addAll(DbTools.queryData(sqlapp));
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
