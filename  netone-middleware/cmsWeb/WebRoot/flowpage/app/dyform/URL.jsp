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
		<TITLE>表单页应用</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
			
			<!--修改/的方向 2009-2-8 dxl -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		
			<!--修改/的方向 2009-2-8 dxl -->
      <SCRIPT type="text/javascript"
			src="<%=path%>/include/js/checkrs.js"></SCRIPT>
			
		<script type="text/javascript">

		function selectFormColumn(){
			var formcode= document.getElementById("formcode").value;
			if(formcode==null||formcode==''){
				alert('请先选择一个表单');
				return;
			}
			window.open('/dyForm/servlet/FormViewsvl?formcode='+formcode,'_blank');
		}
		//接收父节点
		function selectfatherlsh(lsh,formcode){
		
			if(document.getElementById('funcinfo').value=='view'){
				selecthis(lsh,formcode);
				
			}else{
				document.getElementById('fatherlsh').value=lsh;
			}
		}
		
		function selecthis(lsh,formcode){
			document.getElementById('viewlsh').value=lsh;
			document.getElementById('formcode').value=formcode;
			

		  	document.getElementById("linkvaluepage").value='/dyForm/data/showdata/display.do?lsh='+lsh+'&formcode='+formcode+'&pagestyle=${param.modelname}';
			
		}
		
		//选择页面
		function selectpoint(){
		  window.open('/biWeb/PagelistpathRightSvl?pagename=dyformlist&appname=BUSSFORM');
		}
		
		function selectpage(){
		  window.open('/biWeb/SSelectSvl?pagename=dyformlist&appname=BUSSFORM');
		}
		
		function sselected(name,id){
		    document.getElementById("formcode").value=id;
			var funcvalue=document.getElementById("funcinfo").value;
			var fatherlsh=document.getElementById('fatherlsh').value;
			if(fatherlsh==null||fatherlsh==''){
			  fatherlsh='1';//默认为1
			}
			if(funcvalue=='create'){
		  		document.getElementById("linkvaluepage").value='/dyForm/data/showdata/createview.do?fatherlsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
			}
			if(funcvalue=='list')
			{
				var mode=document.getElementById("modelist").value;
				if(mode=='useview'||mode=='onlyview'){
		  		document.getElementById("linkvaluepage").value='/dyForm/data/data/listShare.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
				
				}else if(mode=='viewlist'){
				document.getElementById("linkvaluepage").value='/dyForm/data/showdata/listviews.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
				}
				
				else{
		  		document.getElementById("linkvaluepage").value='/dyForm/data/data/list.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
				
				}
			}
			if(funcvalue=='listview')
			{
		  		document.getElementById("linkvaluepage").value='/dyForm/data/showdata/listviews.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
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
		
		    if(document.getElementById('funcinfo').value=='view'){
		    	var viewlsh=document.getElementById('viewlsh').value;
				if(viewlsh==null||viewlsh==''){
			  		alert('未选择视图LSH');
			  		return;
				}
		    }
		
			if(document.getElementById('funcinfo').value=='list'){
			
			   var mode=document.getElementById("modelist").value;
			   $('url').value=$('linkvaluepage').value+"&mode="+mode;
			}else{
			   $('url').value=$('linkvaluepage').value;
			}
			var selectedcolumn=document.getElementById('columnselect').value;
		
			if(selectedcolumn!=''){
			   $('url').value+='&selectcolumn='+selectedcolumn;
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
			<input type='hidden' name='formcode' value=''>
			<!-- 加入id域  解决portal添加表单应用 出错问题  原因找不到id 对应后台cellid -->
			<input type='hidden' name='id' id="id" value='${param.id}'>
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
										<FONT CLASS="OecGlobalPageTitle">>添加表单应用程序</FONT>
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
					<!-- 增加id 2009-2-8 dxl -->
					<input type='hidden' name='id' id="id" nvalue='<%=id%>' />
					<!-- 增加id 2009-2-8 dxl -->
					<INPUT type='hidden' name="url" id="url" value="" width='300'>
					<table CELLSPACING="4">
						<tr>
							<td>
								名&nbsp;&nbsp;&nbsp;&nbsp;称
							</td>
							<td>
							<!-- 增加id 2009-2-8 dxl -->
								<INPUT type='text' name="naturalname" id="naturalname" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								显示名称
							</td>
							<td>
							<!-- 增加id 2009-2-8 dxl -->
								<INPUT type='text' name="name" id="name" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								功能选择
							</td>
							<td>
								<script type="text/javascript">
							function selectdo(){
								var funcinfo=document.getElementById('funcinfo').value;
								if(funcinfo=='view'){
									father.style.display='none'
									view.style.display='';
									formselect.style.display='none';
									
								}else{
									father.style.display=''
									view.style.display='none';	
									formselect.style.display='';					
								}
								
								if(funcinfo=='view'||funcinfo=='create'){
								   columnselectx.style.display='none';
								}else{
								   columnselectx.style.display='';
								}
							
								if(document.getElementById('funcinfo').value=='list'){
								    document.getElementById('modelist').style.display='';
								}else{
								    document.getElementById('modelist').style.display='none';
								}
							}
							</script>
							<!-- 增加id 2009-2-8 dxl -->
								<select name='funcinfo' id="funcinfo"  onchange='selectdo()'>
									<option value='listview'>
										视图列表
									</option>
									<option value='list'>
										记录列表
									</option>
									<option value='create'>
										创建
									</option>
									<option value='view'>
										视图
									</option>


								</select>
								<!-- 增加id 2009-2-8 dxl -->
								<select name='modelist' id="modelist" style='display: none'>
									<option value=''>
										管理1
									</option>
									<option value='manage'>
										管理2
									</option>
									<option value='useview'>
										应用
									</option>
									<option value='onlyview'>
										显示
									</option>
									<option value='viewlist'>
										显示列
									</option>
								</select>
							</td>
						</tr>
						<TR id='father'>
							<TD>
								父&nbsp;&nbsp;节&nbsp;点
							</TD>
							<TD>
							<!-- 增加id 2009-2-8 dxl -->
								<input type='text' name='fatherlsh' id="fatherlsh" value='1' size='20'>

								<a href='javascript:selectpoint()'>选择父节点</a>
								<em>默认父节点为1,[选择父节点]按钮可以选择父节点，在打开的表单选择页中，点[应用1]进入数据页面才可选择</em>
							</TD>
						</TR>
						<TR id='view' style='display: none'>
							<TD>
								视图节点
							</TD>
							<TD>
							<!-- 增加id 2009-2-8 dxl -->
								<input type='text' name='viewlsh' id="viewlsh" value='' size='20'>

								<a href='javascript:selectpoint()'>选择视图节点</a>
								<em>[选择视图节点]按钮可以选择节点，在打开的表单选择页中，点[应用1]进入数据页面才可选择</em>
							</TD>
						</TR>
						<TR id='formselect'>
							<TD>
								应&nbsp;&nbsp;&nbsp;&nbsp;用
							</TD>
							<TD>
							<!-- 增加id 2009-2-8 dxl -->
								<input type='text' name='linkvaluepage' id="linkvaluepage"  value='' size='40'>
								<!-- 增加id 2009-2-8 dxl -->
								<input type='button' name='choicepage' id="choicepage" value='选择表单'
									onClick='selectpage()'>
							</TD>
						</TR>
						<TR id='columnselectx' style='display:none'>
							<TD>
								字段选择
							</TD>
							<TD>
								<!-- 增加id 2009-2-8 dxl -->
								<input type='text' name='columnselect' id="columnselect" value='' size='40'>
								请需选择
								<a href='javascript:selectFormColumn()'><font color='blue'>[字段描述]</font></a>
								中的相关字段的uuid值,作为表单展现中需要的字段，多个字段可用逗号分割
							</TD>
						</TR>


						<tr>
							<td>
								访问模式
							</td>
							<td>
							<!-- 增加id 2009-2-8 dxl -->
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
								<!-- 增加id 2009-2-8 dxl -->
								<input type='checkbox' name='appbar' id="appbar" value='yes'  />
								控制窗口 &nbsp;&nbsp;宽度:
								<!-- 增加id 2009-2-8 dxl -->
								<INPUT type='text' name="width"  id="width" value="200" size='8'>

								&nbsp;&nbsp;高度:
								<!-- 增加id 2009-2-8 dxl -->
								<INPUT type='text' name="height" id="height" value="300" size='8'>

								<br>
							</td>
						</tr>
						<tr>
							<td>
								描&nbsp;&nbsp;&nbsp;&nbsp;述
							</td>
							<td>
							<!-- 增加id 2009-2-8 dxl -->
								<textarea rows="5" cols="60" name='description' id="description"></textarea>
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
							n<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
