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
		<TITLE>经营分析页应用</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\checkrs.js"></SCRIPT>
		<script type="text/javascript">
		//选择页面
		function selectpage(){
		  
		  	var funcvalue=document.getElementById("funcinfo").value;
			if(funcvalue=='table'||funcvalue=='table1'){ 
		  		window.open('/biWeb/PagelistpathRightSvl?pagename=etl&appname=ETL');
			}

			if(funcvalue=='ana'||funcvalue=='ana1'){ 
				window.open('/biWeb/PagelistpathRightSvl?pagename=chart&appname=CHART');
			}

		}
		
		function selecthis(id,name){
			var funcvalue=document.getElementById("funcinfo").value;

			if(funcvalue=='table1'){ 
		  		document.getElementById("linkvaluepage").value='/biWeb/flowpage.do?act=table&chkid='+id;
		  
			}
			if(funcvalue=='table'){                            
		  		document.getElementById("linkvaluepage").value='/biWeb/tableView.jsp?name='+name+'&pagestyle=${param.modelname}';
			}	
			if(funcvalue=='ana'){
		  		document.getElementById("linkvaluepage").value='/biWeb/graphView.jsp?chkid='+id;
			}	
			if(funcvalue=='ana1'){
		  		document.getElementById("linkvaluepage").value='/biWeb/flowpage.do?act=table&task=chart0&chkid='+id;
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

		   if(funcvalue=='table1'){ 
		    	var funcappx1=document.getElementById('funcappx1');
		  		var funcappx2=document.getElementById('funcappx2');
		  		var funcappx3=document.getElementById('funcappx3');
		  		var funcappx4=document.getElementById('funcappx4');
		  		var funcappx5=document.getElementById('funcappx5');
		  		

		  		var addparam='';
		  		if(funcappx1.checked){
		  		  addparam+='&'+funcappx1.value+'=yes';
		  		}
		  		if(funcappx2.checked){
		  		  addparam+='&'+funcappx2.value+'=yes';
		  		}
		  		if(funcappx3.checked){
		  		  addparam+='&'+funcappx3.value+'=yes';
		  		}
		  		if(funcappx4.checked){
		  		  addparam+='&'+funcappx4.value+'=yes';
		  		}
		  		if(funcappx5.checked){
		  		  addparam+='&'+funcappx5.value+'=yes';
		  		}
		  		$('linkvaluepage').value=$('linkvaluepage').value+addparam;
		     }
		
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
										<FONT CLASS="OecGlobalPageTitle">添加经营分析应用</FONT>
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
								function todo(){
									var selecfunc=document.getElementById('funcinfo').value;
									if(selecfunc=='table1'){
										act.style.display='';
									}else{
									    act.style.display='none';
									}
								}
								</script>
								<select name='funcinfo' onChange='todo()'>
									<option value='table'>
										普通表格分析
									</option>
									<option value='table1'>
										高级表格分析
									</option>
									<option value='ana'>
										普通图表分析
									</option>
									<option value='ana1'>
										高级图表分析
									</option>
								</select>
							</td>
						</tr>
						<TR id='act' style='display:none'>
							<TD>
								禁用的功能选项
							</TD>
							<TD>
								<input type='checkbox' name='funcappx1' value='forcast'/>预测分析
								<input type='checkbox' name='funcappx2' value='oneface'/>切片分析
								<input type='checkbox' name='funcappx3' value='normalact'/>普通交互
								<input type='checkbox' name='funcappx4' value='superact'/>高级交互
								<input type='checkbox' name='funcappx5' value='excek'/>Excel导出
							</TD>
						</TR>				
						<TR>
							<TD>
								应&nbsp;&nbsp;&nbsp;&nbsp;用
							</TD>
							<TD>
								<input type='text' name='linkvaluepage' value='' size='40'>
								<input type='button' name='choicepage' value='选择分析模式'
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


						<tr id='disp' style='display: none'>
							<td colspan='2'>

								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type='checkbox' name='appbar' value='yes'  />
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
						<TD><%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
