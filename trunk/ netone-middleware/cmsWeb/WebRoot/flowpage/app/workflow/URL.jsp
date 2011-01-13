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
		<TITLE>流程页应用</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\checkrs.js"></SCRIPT>
		<script type="text/javascript">
		
		//选择页面
		function selectpage(){
		  window.open('<%=basePathRoot%>wfweb/PagelistpathRightSvl?pagename=pagelist&appname=BUSSWF');
		}
		
		function selecthis(id){
			var funcvalue=document.getElementById("funcinfo").value;
			if(funcvalue=='list'){
		  		document.getElementById("linkvaluepage").value='/wfweb/listUseprocess.do?processid='+id;
			}else if(funcvalue=='disp'){
		  		document.getElementById("linkvaluepage").value='/wfweb/viewreadonlyprocess.do?processid='+id;
			}else if(funcvalue=='inst'){
		  		document.getElementById("linkvaluepage1").value='/wfweb/listRuntimeprocess.do?runtimeid='+id;
			}
		}
        
        function insermode(){
        	var selectvalue=document.getElementById('mode').value;
        	if(selectvalue=='disp'){
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
			var funcvalue=document.getElementById("funcinfo").value;
			if(funcvalue=='inst'){
			  $('url').value=$('linkvaluepage1').value;
			}else{
			  $('url').value=$('linkvaluepage').value;
			}
			
			//最终结束的时候相关的处理逻辑需要提交给Servlet来完成
			this.document.forms[0].action='<%=basePath%>servlet/AjaxPageItemSvl?objtype=url';
			//将相关的表单参数全部传递给上面的Servlet
			this.document.forms[0].submit();
		}
		</SCRIPT>

	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			

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
										<FONT CLASS="OecGlobalPageTitle">添加流程应用</FONT>
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
														onClick="javascript:forward()">
												</TD>
												<TD>
													<INPUT type='button' value='完成' onClick='javascript:done()'>
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="取消"
														onClick="javascript:cancelsubmit()">
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
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 单击“完成”。</FONT>
				<BR>
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<input type='hidden' name='id' value='<%=id%>' />
					<INPUT type='hidden' name="url" value="" width='300'>
					<table CELLSPACING="4">
						<tr>
							<td>
								名&nbsp;&nbsp;&nbsp;&nbsp;称
							</td>
							<td>
								<INPUT type='text' name="naturalname" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								显示名称
							</td>
							<td>
								<INPUT type='text' name="name" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								功能选择
							</td>
							<td>
								<script type="text/javascript">
								function todox(){
								    document.getElementById('linkvaluepage').value='';
									var selet=document.getElementById('funcinfo').value;
									if(selet=='inst'){
										use2.style.display=''
										use1.style.display='none'
									}else{
										use2.style.display='none'
										use1.style.display=''									
									}
									if(selet=='worklist'){
									   document.getElementById('linkvaluepage').value='/wfweb-ext/worklist?reloadtime=10';
									}
								}
								</script>
								<select name='funcinfo' onChange='todox()'>
									<option value='disp'>
										某流程实例视图
									</option>
									<option value='inst'>
										某流程实例交互
									</option>
									<option value='list'>
										某流程创建管理
									</option>
									<option value='worklist'>
										代办任务
									</option>
								</select>
							</td>
						</tr>
						<TR id='use1'>
							<TD>
								应&nbsp;&nbsp;&nbsp;&nbsp;用
							</TD>
							<TD>
								<input type='text' name='linkvaluepage' value='' size='40'>
								<input type='button' name='choicepage' value='选择流程'
									onClick='selectpage()'>
							</TD>
						</TR>
						<TR id='use2' style='display:none'>
						<TD>
								应&nbsp;&nbsp;&nbsp;&nbsp;用
							</TD>
							<TD>
								<input type='text' name='linkvaluepage1' value='' size='40'>
								<input type='button' name='choicepage' value='选择流程实例'
									onClick='selectpage()'> <em>先进入流程列表的点[流程实例],接着再选择一个流程实例</em>
							</TD>
						</TR>
						<tr>
							<td>
								访问模式
							</td>
							<td>
								<select name='mode' onChange='insermode()'>
									<option value='link'>
										链接
									</option>
									<option value='disp'>
										嵌入显示
									</option>
								</select>
							</td>
						</tr>


						<tr id='disp' style='display:none'>
							<td colspan='2'>non &nbsp;&nbsp;&nbsp; 
								<input type='checkbox' name='appbar' value='yes'  />
								控制窗口 &nbsp;&nbsp;宽度:
								<INPUT type='text' name="width" value="200" size='8'>

								&nbsp;&nbsp;高度:
								<INPUT type='text' name="height" value="300" size='8'>

							<br><br><br></td>
						</tr>
						<tr>
							<td>
								描&nbsp;&nbsp;&nbsp;&nbsp;述
							</td>
							<td>
								<textarea rows="5" cols="60" name='description'></textarea>
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
											onClick="javascript:forward()">
									</TD>
									<TD>
										<INPUT type='button' value='完成' onClick='javascript:done()'>
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="取消"
											onClick="javascript:cancelsubmit()">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD><%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
