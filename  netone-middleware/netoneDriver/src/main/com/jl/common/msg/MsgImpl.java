package com.jl.common.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.dyform.DyEntry;

import com.jl.common.dyform.DyFormData;
import com.jl.common.workflow.DbTools;

public class MsgImpl implements MsgIfc {

	@Override
	public String commentMsg(String sender, String sourceMsgLsh, String msgbody) {
		String appname ="APPFRAME.APPFRAME.MSG.PL";
		
		DyFormData dydata = new DyFormData();
		dydata.setFatherlsh(sourceMsgLsh);
		dydata.setParticipant(sender);

		dydata.setColumn3(msgbody);
		//还有评论的评论逻辑还没加入
		String lsh=null;
		try {
			String formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
			lsh = DyEntry.iv().addData(formcode, dydata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lsh;
	}

	@Override
	public String forwardMsg(String sender, String msgto, String msgbody,
			boolean comment, String sourceMsgLsh) {
		
		String appname ="APPFRAME.APPFRAME.MSG.RZXT";

		DyFormData dydata = new DyFormData();
		dydata.setFatherlsh("1");
		dydata.setParticipant(sender);
		dydata.setColumn3(msgto);
		dydata.setColumn4(msgbody);
		dydata.setColumn6("1");
		dydata.setBelongx(sourceMsgLsh);
		if(comment)
		dydata.setColumn12("0");
		else
		dydata.setColumn12("1");
		String lsh=null;
		try {
			String formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
			lsh = DyEntry.iv().addData(formcode, dydata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lsh;
	}



	@Override
	public List msgList(String userid, String type, String firsttime,
			String lasttime,String lsh) {

		List list=new ArrayList();

		if("01".equals(type)){// 返回公开和定向消息
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum ,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+="where  (column3='"+userid+"' or column3='') and  timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+="where   (column3='"+userid+"' or column3='') and  timex<'"+lasttime+"' order by timex desc  limit 0,30";
			}else{
				sql+="where (column3='"+userid+"' or column3='') order by timex desc limit 0,30";
			}
			list=DbTools.queryData(sql);
		}else if("02".equals(type)){//返回公开的用户信息
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+="where column3='' and  timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+="where column3='' and  timex<'"+lasttime+"' order by timex desc  limit 0,30";
			}else{
				sql+="where  column3='' order by timex desc  limit 0,30";

			}
			list=DbTools.queryData(sql);
		}else if("03".equals(type)){//返回针对我的消息
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum ,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";

			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3='"+userid+"' and timex>'"+firsttime+"' order by timex desc  limit 0,30";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3='"+userid+"' and  timex<'"+lasttime+"' order by timex desc limit 0,30";
			}else{
				sql+="where column3='"+userid+"' order by timex desc  limit 0,30";

			}
			list=DbTools.queryData(sql);
		}else if("04".equals(type)){//我的消息被关注的列表
			String sql="select column3 lshx,column4 rellsh,column5 types from dyform.DY_251356887574361 ";
			if(StringUtils.isNotEmpty(firsttime)){
				sql+=" where column3='"+userid+"' and timex>'"+firsttime+"'";
			}else if(StringUtils.isNotEmpty(lasttime)){
				sql+=" where column3='"+userid+"' and timex<'"+lasttime+"'";
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
					String sqlF="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh='"+lshx+"'";
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
					String sqlF="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh='"+lshx+"'";
					List listTmp1=DbTools.queryData(sqlF);
					Map data=new HashMap();
					if(listTmp1!=null&&listTmp1.size()==1){
						data= (Map)listTmp1.get(0);
					}
					
					String sql_rt="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum ,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh='"+rellsh+"'";
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
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";

			sql+= "where lsh='"+lsh+"'";
			String sqlapp="select concat('t',timex) timex, column3 context,column5 atturl,participant sendercode,column6 sendername from dyform.DY_391356510840526 where fatherlsh= '"+lsh+"' order by timex  limit 0,30";
			if(StringUtils.isNotEmpty(lasttime)){
				sqlapp="select concat('t',timex) timex, column3 context,column5 atturl,participant sendercode,column6 sendername from dyform.DY_391356510840526 where fatherlsh= '"+lsh+"' and timex<'"+lasttime+"' order by timex   limit 0,30";
			}		
			list=DbTools.queryData(sql);
			if(StringUtils.isNotEmpty(sqlapp)){
				list.addAll(DbTools.queryData(sqlapp));
			}
		}else if("06".equals(type)){// 我的消息明细带转发列表
			String sql="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 ";

			sql+= "where lsh='"+lsh+"'";
			String sqlapp="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh in(select column4 from dyform.DY_251356887574361 where column3='"+lsh+"' and column5='02') order by timex  limit 0,30";
			if(StringUtils.isNotEmpty(lasttime)){
				sqlapp="select concat('t',timex) timex,participant sendercode,column11 sendername , '' myimgurl,lsh,column3 recivercode,column10 recivername,column4 context,column7 rtnum,column8 rpnum,column5 atturl,belongx rtsourcelsh,column13 rtusername,column12 canrp,column6 isrt from dyform.DY_391356510840525 where lsh in(select column4 from dyform.DY_251356887574361 where column3='"+lsh+"' and column5='02') and timex<'"+lasttime+"' order by timex   limit 0,30";
			}		
			list=DbTools.queryData(sql);
			if(StringUtils.isNotEmpty(sqlapp)){
				list.addAll(DbTools.queryData(sqlapp));
			}
		}

		
		
		
		EnvService env;
		String headUrl ="";
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			headUrl=env.fetchEnvValue("WEBSER_APPFRAMEX");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			boolean rs=object.containsKey("atturl");
			if(rs){
				String sql="select unid zzud from iss.t_file where d_unid='"+ lsh + "'";
				List listx=DbTools.queryData(sql);
				if(listx.size()>0){
					object.put("atturl", headUrl+"/file.do?method=onDownLoadFile&isOnLine=0&unid="+((Map) listx.get(0)).get("zzud"));
					object.put("atturlzip", headUrl+"/file.do?method=onDownLoadFile&isOnLine=0&unid="+((Map) listx.get(0)).get("zzud"));

				}
			}
		}
		
		return list;
		
	}

	@Override
	public List myGroupAndMember(String userid) {
		
		String sql="select column3,column4,lsh from dyform.DY_251356887574360 where participant='"+userid+"'";
		List list=DbTools.queryData(sql);
		
		List listall=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String groupid=(String)object.get("lsh");
			String groupname=(String)object.get("column3");
			String users=(String)object.get("column4");
			
			
			Map data=new HashMap();
			data.put("groupid", groupid);
			data.put("groupname", groupname);
			
			String []strx=StringUtils.split(users,",");
			if(strx!=null){
				List userpre=new ArrayList();
				for (int i = 0; i < strx.length; i++) {
					Map map=new HashMap();
					map.put("id", StringUtils.substringBefore(strx[i], "["));
					map.put("name", StringUtils.substringBetween(strx[i], "[","]"));
					userpre.add(map);
				}
				data.put("people", userpre);
			}
			listall.add(data);
		}
		return listall;
	}

	@Override
	public String newMsg(String sender, String msgto, String msgbody,
			boolean comment) {
	
		String appname ="APPFRAME.APPFRAME.MSG.RZXT";

		DyFormData dydata = new DyFormData();
		dydata.setFatherlsh("1");
		dydata.setParticipant(sender);
		dydata.setColumn3(msgto);
		dydata.setColumn4(msgbody);
		if(comment)
		dydata.setColumn12("0");
		else
		dydata.setColumn12("1");
		String lsh=null;
		try {
			String formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
			lsh = DyEntry.iv().addData(formcode, dydata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lsh;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		MsgIfc msg=new MsgImpl();
		// adminx 用户给 msg1 用户发送了一条消息,允许评论
		String lsh1=msg.newMsg("adminx", "msg1", "我的消息", true);
		// msg2 评论了上一条消息
		msg.commentMsg("msg2", lsh1, "不好");
		// msg2 转发了上一条消息
		msg.forwardMsg("msg2", "", "小学校", false, lsh1);
		
		// adminx展示其自己创建的群组和群组中的人员
		List list=msg.myGroupAndMember("adminx");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String groupid=(String)object.get("groupid");
			String groupname=(String)object.get("groupname");
			List people=(List)object.get("people");
			System.out.println(groupid+","+groupname+","+people.toString());
		}
		// msg1用户查看公共的消息
		List list2=msg.msgList("msg1", "01", null, null, null);
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			System.out.println(object.toString());
		}
		// msg1用户查看某消息的评论列表
		List list3=msg.msgList("msg1", "05", null, null, lsh1);
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			System.out.println(object.toString());
		}
	}

	@Override
	public boolean delComment(String lsh) {
		String appname ="APPFRAME.APPFRAME.MSG.PL";

		try {
			String formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
			DyEntry.iv().deleteData(formcode, lsh);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delMsg(String lsh) {
		String appname ="APPFRAME.APPFRAME.MSG.RZXT";

		try {
			String formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
			DyEntry.iv().deleteData(formcode, lsh);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}




}
