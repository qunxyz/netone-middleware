package com.jl.common.msg;

import java.util.List;

public interface MsgIfc {
	
	/**
	 * 新建消息
	 * @param sender 发送者
	 * @param msgto 消息定向发送对象，如果有多个用户请循环调用该方法
	 * @param msgbody 消息正文
	 * @param comment 是否允许评论，true表示允许评论，false表示禁止评论
	 * @return 消息lsh
	 */
	String newMsg(String sender,String msgto,String msgbody,boolean comment);
	
	/**
	 * 转发消息
	 * @param sender 发送者
	 * @param msgto 消息定向发送
	 * @param msgbody 消息正文
	 * @param comment 是否允许评论，true表示允许评论，false表示禁止评论
	 * @param sourceMsgLsh 消息源lsh
	 * @return 消息lsh
	 */
	String forwardMsg(String sender,String msgto,String msgbody,boolean comment,String sourceMsgLsh);
	
	/**
	 * 评论
	 * @param sender 发送者
	 * @param sourceMsgLsh 被评论的消息
	 * @param msgbody 评论内容
	 * @return 消息lsh
	 */
	String commentMsg(String sender,String sourceMsgLsh,String msgbody);
	
	/**
	 * 我的群组和群组中的人员
	 * @param userid 用户id
	 * @return 群组信息和人员信息 List中嵌套map，其中元素有 groupid 群组id，groupname群组名,people 群组中的人员可以有多个 用逗号隔开
	 * 
	 * @参考群组设置 应用 http://42.120.40.204:8080/scm/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.MSG.MYTEAM
	 * 群组展示数据格式 http://42.120.40.204:82/netoneWebSerivce/MyGroupSvl?userid=msg1
	 */
	List myGroupAndMember(String userid);
	
	/**
	 * 消息列表
	 * @param userid 用户id
	 * @param type 消息类型Type=01 表示全部消息，02 公开的消息03 定向消息，04 提到我的05 评论 06 转发 00原文带评论列表
	 * @param firsttime 是第一条记录的时间 格式 2012-12-12 00:00:00用于拉动显示更多信息
	 * @param lasttime 是最后一条记录的时间 格式 2012-12-12 00:00:00用于拉动显示更多信息
	 * @param lsh 单条消息的lsh
	 * @return list 里面的Map表示一条记录，相关字段说明如下：lsh 消息id,timex 发表时间、sendercode 发送者code ,sendername 发送者名字，myimgurl 作
者头像(为空时用默认头像)recivercode 消息定向接收者code,recivername 消息定向接收者名字，context 消息内容，
rpnum 转发次数,rtnum 回复次数atturl 图片附件地址，atturlzip 带压缩的附件地址，rtsourcelsh 转发之原文id，rtusername 转
发原文作者名，canrp 是否禁止评论=1 表示禁止，isrt 是否转发=1 表示转发如果是04 提到我的消息，那么还包含嵌套消息rel 里面包含消息内容
	 */
	List msgList(String userid,String type,String firsttime,String lasttime,String lsh);


}
