<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String basePathRoot = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
	String parentid = request.getParameter("parentid");
	String pagepath = request.getParameter("pagepath");
	String id = request.getParameter("id");
%>

<HTML>
	<HEAD>
		<TITLE>消息页应用</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<!--修改/的方向 2009-2-8 dxl -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<!--修改/的方向 2009-2-8 dxl -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/checkrs.js"></SCRIPT>
		
<script type="text/javascript">
		function selecthis(){
			var funcvalue=document.getElementById("funcinfo").value;
			if(funcvalue=='daily'){
		  		$('url').value='/Message/bussform/bussMessage.do?short=yes&pagesytle=${param.modelname}';
			}else if(funcvalue=='send'){
				$('url').value='/Message/bussform/SendMessage.do?pagesytle=${param.modelname}';
			}else{
		  		$('url').value='/Message/bussform/MsgSend.do?pagesytle=${param.modelname}';
			}
		}
        function insermode(){
     
        	var selectvalue=document.getElementById('mode').value;
        	if(selectvalue=="disp"){
        		document.getElementById("disp").style.display='';
        	}else{
        		document.getElementById("disp").style.display='none';
        	}
        }

		//上一步脚本
		function forward(){
			history.go(-1);
		}

		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//结束脚本
		function done(){
	
		    var naturalname=document.getElementById("naturalname").value;
			var rs=checkNaturalname(naturalname);
			if(rs!=''){
				alert(rs);
				return;
			}
			var name=document.getElementById("name").value;
			if(name==''){
				alert('缺少名称');
				return;
			}
			//最终结束的时候相关的处理逻辑需要提交给Servlet来完成
			this.document.forms[0].action="<%=basePath%>servlet/AjaxPageItemSvl?objtype=url";
		//将相关的表单参数全部传递给上面的Servlet
		this.document.forms[0].submit();
	}
</script>

	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<!--增加id属性 2009-2-8 dxl -->
			<input type='hidden' name='parentid' id="parentid" value='<%=parentid%>'>
			<!--增加id属性 2009-2-8 dxl -->
			<input type='hidden' name='pagepath' id="pagepath" value='<%=pagepath%>'>

			<%@ include file="/include/page/PageHeadInfo.jsp"%>


			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD>
							<TABLE WIDTH="100%" BORDER="0" CELLPADDING="1" CELLSPACING="0">
								<TR CLASS="OecBgColorDark">
									<TD>
										&nbsp;
									</TD>
									<TD WIDTH="100%" valign="center">
										<FONT CLASS="OecGlobalPageTitle">添加消息应用</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG
												SRC="<%=basePath%>/include/image/helpsb.gif" ALT="帮助"
												BORDER="0"> </A>
									</TD>
									<TD>
										&nbsp;
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%">
								<TR>
									<TD ROWSPAN="2" VALIGN="TOP">
										<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
											<TR>
												<TD VALIGN="TOP" CLASS="OecBgColorDark">

												</TD>
											</TR>
										</TABLE>
									</TD>
									<TD ROWSPAN="2" VALIGN="TOP">
										&nbsp;&nbsp;&nbsp;
									</TD>
									<TD ALIGN="RIGHT" valign="top" width="100%">
										<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
											<TR>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
														onClick="javascript:forward();">
												</TD>
												<TD>
													<INPUT type='button' value='完成' onClick="javascript:done();">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="取消"
														onClick="javascript:cancelsubmit();">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 1 步, 共 1 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 单击“完成”。</FONT>
				<BR>
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">
						<!--增加id属性 2009-2-8 dxl -->
					<input type='hidden' name='id' id="id" value='<%=id%>' />
						<!--增加id属性 2009-2-8 dxl -->
					<INPUT type='hidden' name="url" id="url"
						value='<%=basePathRoot%>Message/bussform/bussMessage.do?short=yes'>
					<table CELLSPACING="4">
						<tr>
							<td>
								名&nbsp;&nbsp;&nbsp;&nbsp;称
							</td>
							<td>
								<!--增加id属性 2009-2-8 dxl -->
								<INPUT type='text' name="naturalname"  id="naturalname" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								显示名称
							</td>
							<td>
								<!--增加id属性 2009-2-8 dxl -->
								<INPUT type='text' name="name" id="name" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								应&nbsp;&nbsp;&nbsp;&nbsp;用
							</td>
							<td>
								<!--增加id属性 2009-2-8 dxl -->
								<select name='funcinfo' id="funcinfo" onChange='selecthis()'>
									<option value='daily'>
										每日消息
									</option>
									<option value='send'>
										发送消息
									</option>
									<option value='manage'>
										管理消息
									</option>
								</select>
							</td>
						</tr>

						<tr>
							<td>
								访问模式
							</td>
							<td>
								<!--增加id属性 2009-2-8 dxl -->
								<select name='mode' id="mode" onChange='insermode()'>
									<option value='link'>
										链接
									</option>
									<option value='disp'>
										嵌入显示
									</option>
								</select>
							</td>
						</tr>
						<tr id='disp' style='display: none'>
							<td colspan='2'>

								&nbsp;&nbsp;&nbsp;&nbsp;
								<!--增加id属性 2009-2-8 dxl -->
								<input type='checkbox' name='appbar' id="appbar" value='yes' />
								控制窗口 &nbsp;&nbsp;宽度:
								<!--增加id属性 2009-2-8 dxl -->
								<INPUT type='text' name="width" id="width" value="200" size='8'>

								&nbsp;&nbsp;高度:
								<!--增加id属性 2009-2-8 dxl -->
								<INPUT type='text' name="height" id="height" value="300" size='8'>

							</td>
						</tr>
						<tr>
							<td>
								描&nbsp;&nbsp;&nbsp;&nbsp;述
							</td>
							<td>
							<!--增加id属性 2009-2-8 dxl -->
								<textarea rows="5" cols="60" id="description" name='description'></textarea>
							</td>
						</tr>

					</table>
				</DIV>
			</DIV>
			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD ALIGN="RIGHT" valign="top" width="100%">
							<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
								<TR>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
											onClick="javascript:forward();">
									</TD>
									<TD>
										<INPUT type='button' value='完成' onClick="javascript:done();">
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="取消"
											onClick="javascript:cancelsubmit();">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
