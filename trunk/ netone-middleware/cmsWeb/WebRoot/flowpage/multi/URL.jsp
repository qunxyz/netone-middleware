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
		<TITLE>添加多彩文本的URL</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>

		<script type="text/javascript">
		//选择页面
		function selectpage(){
		  window.open('/fck/PagelistpathRightSvl?pagename=fcklist&appname=FCK');
		}
		
		function selecthis(id,name,inclusion,parentdir,path){
		//seltype是下拉框中设置当前页面的显示方式，包括直接显示、可修改的显示。
			var seltype=document.getElementById('pagetype').value;
			
			if(seltype=='0'){
			   if(inclusion=='1'){
			   	  alert('普通页不能选择目录');
			   	  return;
			   }
			   document.getElementById("linkvaluepage").value='/fck/PagelistViewSvl?pagename=simplefcklist&chkid='+id;
			}
			else if(seltype=='1'){
			   if(inclusion=='1'){
			   	  alert('普通页不能选择目录');
			   	  return;
			   }
			  document.getElementById("linkvaluepage").value='/fck/PagelistModifySvl?task=edit&pagename=simplefcklist&chkid='+id;
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
			$('url').value=$('linkvaluepage').value;
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
										<FONT CLASS="OecGlobalPageTitle">添加多彩文本应用</FONT>
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
						<tr style="display:none">
							<td>
								名&nbsp;&nbsp;&nbsp;&nbsp;称
							</td>
							<td>
								<INPUT type='text' name="naturalname" value="${param.naturalname}" width='200'>
							</td>
						</tr>
						<tr style="display:none">
							<td>
								显示名称
							</td>
							<td>
								<INPUT type='text' name="name" value="${param.cname}" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								页类型
							</td>
							<td>
								<select name='pagetype'>
									<option value='0'>普通显示</option>
									<option value='1'>可修改的显示</option>
								</select> 
								<font size='2' color='#999999'>资源相关的页的参数appname为默认DEPT，需要结合实际的来修订。向导页中参数css可根据需要选择不同的样式</font>
							</td>
						</tr>
						<TR>
							<TD>
								应&nbsp;&nbsp;&nbsp;&nbsp;用
							</TD>
							<TD>
								<input type='text' name='linkvaluepage' value='' size='40'>
								<input type='button' name='choicepage' value='选择页'
									onClick='selectpage()'>
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
							<td colspan='2'>

								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type='checkbox' name='appbar' value='yes' checked />
								控制窗口 &nbsp;&nbsp;宽度:
								<INPUT type='text' name="width" value="200" size='8'>

								&nbsp;&nbsp;高度:
								<INPUT type='text' name="height" value="300" size='8'>

							</td>
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
						<TD>
							<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
