<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String parentid = request.getParameter("parentid");
	String pagepath = request.getParameter("pagepath");
	String id = request.getParameter("id");
%>

<HTML>
	<HEAD>
		<TITLE>编辑文章</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
		   function SendColor(color) {
		    document.getElementById('p_color').value = color; 
		  }
		  function SendColor1(color) {
		    document.getElementById('p_color1').value = color; 
		  }
		  function hideTitle(){
		 
		  	if(document.getElementById('diystyle').checked){
		  	   diyTitleAll.style.display='';
		  	}else{
		
		  	   diyTitleAll.style.display='none';
		  	}
		  }
		  
		    function hideText(){
		 
		  	if(document.getElementById('diystyleText').checked){
		  	   diyTextAll.style.display='';
		  	}else{
		
		  	   diyTextAll.style.display='none';
		  	}
		  }
		</script>

		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			history.go(-1);
		}
		//下一步脚本
		function nextsubmit(){
			window.location.href='Done.jsp';
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//结束脚本
		function done(){
			//最终结束的时候相关的处理逻辑需要提交给Servlet来完成
			this.document.forms[0].action='<%=basePath%>servlet/AjaxPageItemSvl?objtype=article';
			//将相关的表单参数全部传递给上面的Servlet
			this.document.forms[0].submit();
		}
		</SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>


	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<input type='hidden' name='cname' value='${param.cname }'>
			<input type='hidden' name='naturalname' value='${param.naturalname}'>
			
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
										<FONT CLASS="OecGlobalPageTitle">文章编辑</FONT>
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
						编辑文章
					</NOBR> </FONT>
				<hr>

				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<input type='hidden' name='id' value='<%=id%>' />
					<strong><font color='red'>标题:</font> </strong>
					<INPUT name="name" value="无标题文章" size='30'>

					<select name='headstyle'>
						<option value='Heading1'>
							系统样式1
						</option>
						<option value='Heading2'>
							系统样式2
						</option>
						<option value='Heading3'>
							系统样式3
						</option>
						<option value='Heading4'>
							系统样式4
						</option>
					</select>
					&nbsp;&nbsp;&nbsp; 使用自定义样式:
					<input type='checkbox' name='diystyle' value='1'
						onClick="hideTitle()">
					<br>
					<br>
					<div id='diyTitleAll' style='display:none'>
						<table>
							<tr>
								<td>
									字体效果:
									<SELECT NAME="p_valuea">
										<OPTION SELECTED value="None">
											<FONT CLASS="OraFieldText">&lt;无&gt;</FONT>
										<OPTION value="Plain">
											<FONT CLASS="OraFieldText">普通</FONT>
										<OPTION value="Underline">
											<FONT CLASS="OraFieldText">下划线</FONT>
										<OPTION value="Line-Through">
											<FONT CLASS="OraFieldText">删除线</FONT>
										<OPTION value="Underline Line-Through">
											<FONT CLASS="OraFieldText">下划线删除线</FONT>
									</SELECT>
								</td>
								<td>
									字体:
									<SELECT NAME="p_valueb">
										<OPTION>
											&lt;Default&gt;
										<OPTION>
											Arial
										<OPTION>
											Arial Black
										<OPTION>
											Arial Narrow
										<OPTION>
											Arial Rounded MT Bold
										<OPTION SELECTED>
											Arial, Helvetica
										<OPTION>
											Book Antiqua
										<OPTION>
											Century Gothic
										<OPTION>
											Century Schoolbook
										<OPTION>
											Comic Sans MS
										<OPTION>
											Courier
										<OPTION>
											Courier New,Courier
										<OPTION>
											Haettenschweiler
										<OPTION>
											Helvetica
										<OPTION>
											Impact
										<OPTION>
											Marlett
										<OPTION>
											Monotype Sorts
										<OPTION>
											Tahoma
										<OPTION>
											Times New Roman
										<OPTION>
											Verdana
									</SELECT>
								</td>
								<td>
									字体大小:
									<SELECT NAME="p_valuec">
										<OPTION>
											6pt
										<OPTION>
											7pt
										<OPTION>
											8pt
										<OPTION SELECTED>
											9pt
										<OPTION>
											10pt
										<OPTION>
											11pt
										<OPTION>
											12pt
										<OPTION>
											13pt
										<OPTION>
											14pt
										<OPTION>
											15pt
										<OPTION>
											16pt
										<OPTION>
											17pt
										<OPTION>
											18pt
									</SELECT>
								</td>
								<td>
									字体样式:
									<SELECT NAME="p_valued">
										<OPTION SELECTED value="Plain">
											<FONT CLASS="OraFieldText">普通</FONT>
										<OPTION value="Bold">
											<FONT CLASS="OraFieldText">粗体</FONT>
										<OPTION value="Italic">
											<FONT CLASS="OraFieldText">斜体</FONT>
										<OPTION value="Bold-Italic">
											<FONT CLASS="OraFieldText">粗斜体</FONT>
									</SELECT>
								</td>
							</tr>
						</table>
						<TABLE BORDER="0">
							<TR>
								<TD COLSPAN="2">
									<BR>
									<MAP NAME="map_colorpal">
										<!-- ---- Grayscale ---- -->
										<AREA COORDS="243,75,258,91"
											HREF="javascript:SendColor('#000000')">
										<AREA COORDS="243,60,258,76"
											HREF="javascript:SendColor('#333333')">
										<AREA COORDS="243,45,258,61"
											HREF="javascript:SendColor('#666666')">
										<AREA COORDS="243,30,258,46"
											HREF="javascript:SendColor('#999999')">
										<AREA COORDS="243,15,258,31"
											HREF="javascript:SendColor('#CCCCCC')">
										<AREA COORDS="243,0,258,16"
											HREF="javascript:SendColor('#FFFFFF')">
										<!-- ---- Red-Magenta ---- -->
										<AREA COORDS="207,82,244,91"
											HREF="javascript:SendColor('#660033')">
										<AREA COORDS="225,72,244,83"
											HREF="javascript:SendColor('#990033')">
										<AREA COORDS="207,72,226,83"
											HREF="javascript:SendColor('#993366')">
										<AREA COORDS="231,60,244,73"
											HREF="javascript:SendColor('#CC0066')">
										<AREA COORDS="219,60,232,73"
											HREF="javascript:SendColor('#CC3399')">
										<AREA COORDS="207,60,220,73"
											HREF="javascript:SendColor('#CC6699')">
										<AREA COORDS="234,45,244,61"
											HREF="javascript:SendColor('#FF0099')">
										<AREA COORDS="225,45,235,61"
											HREF="javascript:SendColor('#FF3399')">
										<AREA COORDS="216,45,226,61"
											HREF="javascript:SendColor('#FF6699')">
										<AREA COORDS="207,45,217,61"
											HREF="javascript:SendColor('#FF99CC')">
										<!-- ---- Red ---- -->
										<AREA COORDS="184,82,208,91"
											HREF="javascript:SendColor('#660000')">
										<AREA COORDS="162,82,185,91"
											HREF="javascript:SendColor('#663333')">
										<AREA COORDS="192,72,208,83"
											HREF="javascript:SendColor('#990000')">
										<AREA COORDS="177,72,193,83"
											HREF="javascript:SendColor('#993333')">
										<AREA COORDS="162,72,178,83"
											HREF="javascript:SendColor('#996666')">
										<AREA COORDS="196,60,208,73"
											HREF="javascript:SendColor('#CC0000')">
										<AREA COORDS="184,60,197,73"
											HREF="javascript:SendColor('#CC3333')">
										<AREA COORDS="173,60,185,73"
											HREF="javascript:SendColor('#CC6666')">
										<AREA COORDS="162,60,174,73"
											HREF="javascript:SendColor('#CC9999')">
										<AREA COORDS="198,45,208,61"
											HREF="javascript:SendColor('#FF0000')">
										<AREA COORDS="189,45,199,61"
											HREF="javascript:SendColor('#FF3333')">
										<AREA COORDS="180,45,190,61"
											HREF="javascript:SendColor('#FF6666')">
										<AREA COORDS="171,45,181,61"
											HREF="javascript:SendColor('#FF9999')">
										<AREA COORDS="162,45,172,61"
											HREF="javascript:SendColor('#FFCCCC')">
										<!-- ---- Red-Yellow ---- -->
										<AREA COORDS="126,82,163,91"
											HREF="javascript:SendColor('#663300')">
										<AREA COORDS="144,72,163,83"
											HREF="javascript:SendColor('#993300')">
										<AREA COORDS="126,72,145,83"
											HREF="javascript:SendColor('#996633')">
										<AREA COORDS="150,60,163,73"
											HREF="javascript:SendColor('#CC6600')">
										<AREA COORDS="138,60,151,73"
											HREF="javascript:SendColor('#CC6633')">
										<AREA COORDS="126,60,139,73"
											HREF="javascript:SendColor('#CC9966')">
										<AREA COORDS="153,45,163,61"
											HREF="javascript:SendColor('#FF6600')">
										<AREA COORDS="144,45,154,61"
											HREF="javascript:SendColor('#FF9933')">
										<AREA COORDS="135,45,145,61"
											HREF="javascript:SendColor('#FF9966')">
										<AREA COORDS="126,45,136,61"
											HREF="javascript:SendColor('#FFCC00')">
										<!-- ---- Yellow ---- -->
										<AREA COORDS="103,82,127,91"
											HREF="javascript:SendColor('#666600')">
										<AREA COORDS="81,82,104,91"
											HREF="javascript:SendColor('#666633')">
										<AREA COORDS="111,72,127,83"
											HREF="javascript:SendColor('#999900')">
										<AREA COORDS="96,72,112,83"
											HREF="javascript:SendColor('#999933')">
										<AREA COORDS="81,72,97,83"
											HREF="javascript:SendColor('#999966')">
										<AREA COORDS="115,60,127,73"
											HREF="javascript:SendColor('#CCCC00')">
										<AREA COORDS="103,60,116,73"
											HREF="javascript:SendColor('#CCCC33')">
										<AREA COORDS="92,60,104,73"
											HREF="javascript:SendColor('#CCCC66')">
										<AREA COORDS="81,60,93,73"
											HREF="javascript:SendColor('#CCCC99')">
										<AREA COORDS="117,45,127,61"
											HREF="javascript:SendColor('#FFFF00')">
										<AREA COORDS="108,45,118,61"
											HREF="javascript:SendColor('#FFFF33')">
										<AREA COORDS="99,45,109,61"
											HREF="javascript:SendColor('#FFFF66')">
										<AREA COORDS="90,45,100,61"
											HREF="javascript:SendColor('#FFFF99')">
										<AREA COORDS="81,45,91,61"
											HREF="javascript:SendColor('#FFFFCC')">
										<!-- ---- Green-Yellow ---- -->
										<AREA COORDS="45,82,82,91"
											HREF="javascript:SendColor('#336600')">
										<AREA COORDS="63,72,82,83"
											HREF="javascript:SendColor('#339900')">
										<AREA COORDS="45,72,64,83"
											HREF="javascript:SendColor('#669933')">
										<AREA COORDS="69,60,82,73"
											HREF="javascript:SendColor('#66CC00')">
										<AREA COORDS="57,60,70,73"
											HREF="javascript:SendColor('#99CC33')">
										<AREA COORDS="45,60,58,73"
											HREF="javascript:SendColor('#99CC66')">
										<AREA COORDS="72,45,82,61"
											HREF="javascript:SendColor('#99FF00')">
										<AREA COORDS="63,45,73,61"
											HREF="javascript:SendColor('#99FF33')">
										<AREA COORDS="54,45,64,61"
											HREF="javascript:SendColor('#99FF66')">
										<AREA COORDS="45,45,55,61"
											HREF="javascript:SendColor('#CCFF99')">
										<!-- ---- Green ---- -->
										<AREA COORDS="22,82,46,91"
											HREF="javascript:SendColor('#006600')">
										<AREA COORDS="0,82,23,91"
											HREF="javascript:SendColor('#336633')">
										<AREA COORDS="30,72,46,83"
											HREF="javascript:SendColor('#009900')">
										<AREA COORDS="15,72,31,83"
											HREF="javascript:SendColor('#339933')">
										<AREA COORDS="0,72,16,83"
											HREF="javascript:SendColor('#669966')">
										<AREA COORDS="34,60,46,73"
											HREF="javascript:SendColor('#00CC00')">
										<AREA COORDS="22,60,35,73"
											HREF="javascript:SendColor('#33CC33')">
										<AREA COORDS="11,60,23,73"
											HREF="javascript:SendColor('#66CC66')">
										<AREA COORDS="0,60,12,73"
											HREF="javascript:SendColor('#99CC99')">
										<AREA COORDS="36,45,46,61"
											HREF="javascript:SendColor('#00FF00')">
										<AREA COORDS="27,45,37,61"
											HREF="javascript:SendColor('#33FF33')">
										<AREA COORDS="18,45,28,61"
											HREF="javascript:SendColor('#66FF66')">
										<AREA COORDS="9,45,19,61"
											HREF="javascript:SendColor('#99FF99')">
										<AREA COORDS="0,45,10,61"
											HREF="javascript:SendColor('#CCFFCC')">
										<!-- ---- Green-Cyan ---- -->
										<AREA COORDS="207,37,244,46"
											HREF="javascript:SendColor('#006633')">
										<AREA COORDS="225,27,244,38"
											HREF="javascript:SendColor('#009933')">
										<AREA COORDS="207,27,226,38"
											HREF="javascript:SendColor('#339966')">
										<AREA COORDS="231,15,244,28"
											HREF="javascript:SendColor('#00CC66')">
										<AREA COORDS="219,15,232,28"
											HREF="javascript:SendColor('#33CC66')">
										<AREA COORDS="207,15,220,28"
											HREF="javascript:SendColor('#66CC99')">
										<AREA COORDS="234,0,244,16"
											HREF="javascript:SendColor('#00FF66')">
										<AREA COORDS="225,0,235,16"
											HREF="javascript:SendColor('#33FF99')">
										<AREA COORDS="216,0,226,16"
											HREF="javascript:SendColor('#66FF99')">
										<AREA COORDS="207,0,217,16"
											HREF="javascript:SendColor('#99FFCC')">
										<!-- ---- Cyan ---- -->
										<AREA COORDS="184,37,208,46"
											HREF="javascript:SendColor('#006666')">
										<AREA COORDS="162,37,185,46"
											HREF="javascript:SendColor('#336666')">
										<AREA COORDS="192,27,208,38"
											HREF="javascript:SendColor('#009999')">
										<AREA COORDS="177,27,193,38"
											HREF="javascript:SendColor('#339999')">
										<AREA COORDS="162,27,178,38"
											HREF="javascript:SendColor('#669999')">
										<AREA COORDS="196,15,208,28"
											HREF="javascript:SendColor('#00CCCC')">
										<AREA COORDS="184,15,197,28"
											HREF="javascript:SendColor('#33CCCC')">
										<AREA COORDS="173,15,185,28"
											HREF="javascript:SendColor('#66CCCC')">
										<AREA COORDS="162,15,174,28"
											HREF="javascript:SendColor('#99CCCC')">
										<AREA COORDS="198,0,208,16"
											HREF="javascript:SendColor('#00FFFF')">
										<AREA COORDS="189,0,199,16"
											HREF="javascript:SendColor('#33FFFF')">
										<AREA COORDS="180,0,190,16"
											HREF="javascript:SendColor('#66FFFF')">
										<AREA COORDS="171,0,181,16"
											HREF="javascript:SendColor('#99FFFF')">
										<AREA COORDS="162,0,172,16"
											HREF="javascript:SendColor('#CCFFFF')">
										<!-- ---- Blue-Cyan ---- -->
										<AREA COORDS="126,37,163,46"
											HREF="javascript:SendColor('#003366')">
										<AREA COORDS="144,27,163,38"
											HREF="javascript:SendColor('#003399')">
										<AREA COORDS="126,27,145,38"
											HREF="javascript:SendColor('#336699')">
										<AREA COORDS="150,15,163,28"
											HREF="javascript:SendColor('#0066CC')">
										<AREA COORDS="138,15,151,28"
											HREF="javascript:SendColor('#3399CC')">
										<AREA COORDS="126,15,139,28"
											HREF="javascript:SendColor('#6699CC')">
										<AREA COORDS="153,0,163,16"
											HREF="javascript:SendColor('#0099FF')">
										<AREA COORDS="144,0,154,16"
											HREF="javascript:SendColor('#3399FF')">
										<AREA COORDS="135,0,145,16"
											HREF="javascript:SendColor('#6699FF')">
										<AREA COORDS="126,0,136,16"
											HREF="javascript:SendColor('#99CCFF')">
										<!-- ---- Blue-Cyan ---- -->
										<AREA COORDS="126,37,163,46"
											HREF="javascript:SendColor('#003366')">
										<AREA COORDS="144,27,163,38"
											HREF="javascript:SendColor('#003399')">
										<AREA COORDS="126,27,145,38"
											HREF="javascript:SendColor('#336699')">
										<AREA COORDS="150,15,163,28"
											HREF="javascript:SendColor('#0066CC')">
										<AREA COORDS="138,15,151,28"
											HREF="javascript:SendColor('#3399CC')">
										<AREA COORDS="126,15,139,28"
											HREF="javascript:SendColor('#6699CC')">
										<AREA COORDS="153,0,163,16"
											HREF="javascript:SendColor('#0099FF')">
										<AREA COORDS="144,0,154,16"
											HREF="javascript:SendColor('#3399FF')">
										<AREA COORDS="135,0,145,16"
											HREF="javascript:SendColor('#6699FF')">
										<AREA COORDS="126,0,136,16"
											HREF="javascript:SendColor('#99CCFF')">
										<!-- ---- Blue ---- -->
										<AREA COORDS="103,37,127,46"
											HREF="javascript:SendColor('#000066')">
										<AREA COORDS="81,37,104,46"
											HREF="javascript:SendColor('#333366')">
										<AREA COORDS="111,27,127,38"
											HREF="javascript:SendColor('#000099')">
										<AREA COORDS="96,27,112,38"
											HREF="javascript:SendColor('#333399')">
										<AREA COORDS="81,27,97,38"
											HREF="javascript:SendColor('#666699')">
										<AREA COORDS="115,15,127,28"
											HREF="javascript:SendColor('#0000CC')">
										<AREA COORDS="103,15,116,28"
											HREF="javascript:SendColor('#3333CC')">
										<AREA COORDS="92,15,104,28"
											HREF="javascript:SendColor('#6666CC')">
										<AREA COORDS="81,15,93,28"
											HREF="javascript:SendColor('#9999CC')">
										<AREA COORDS="117,0,127,16"
											HREF="javascript:SendColor('#0000FF')">
										<AREA COORDS="108,0,118,16"
											HREF="javascript:SendColor('#3333FF')">
										<AREA COORDS="99,0,109,16"
											HREF="javascript:SendColor('#6666FF')">
										<AREA COORDS="90,0,100,16"
											HREF="javascript:SendColor('#9999FF')">
										<AREA COORDS="81,0,91,16"
											HREF="javascript:SendColor('#CCCCFF')">
										<!-- ---- Magenta-Blue ---- -->
										<AREA COORDS="45,37,82,46"
											HREF="javascript:SendColor('#330066')">
										<AREA COORDS="63,27,82,38"
											HREF="javascript:SendColor('#330099')">
										<AREA COORDS="45,27,64,38"
											HREF="javascript:SendColor('#663399')">
										<AREA COORDS="69,15,82,28"
											HREF="javascript:SendColor('#6600CC')">
										<AREA COORDS="57,15,70,28"
											HREF="javascript:SendColor('#6633CC')">
										<AREA COORDS="45,15,58,28"
											HREF="javascript:SendColor('#9966CC')">
										<AREA COORDS="72,0,82,16"
											HREF="javascript:SendColor('#6600FF')">
										<AREA COORDS="63,0,73,16"
											HREF="javascript:SendColor('#9933FF')">
										<AREA COORDS="54,0,64,16"
											HREF="javascript:SendColor('#9966FF')">
										<AREA COORDS="45,0,55,16"
											HREF="javascript:SendColor('#CC99FF')">
										<!-- ---- Magenta ---- -->
										<AREA COORDS="22,37,46,46"
											HREF="javascript:SendColor('#660066')">
										<AREA COORDS="0,37,23,46"
											HREF="javascript:SendColor('#663366')">
										<AREA COORDS="30,27,46,38"
											HREF="javascript:SendColor('#990099')">
										<AREA COORDS="15,27,31,38"
											HREF="javascript:SendColor('#993399')">
										<AREA COORDS="0,27,16,38"
											HREF="javascript:SendColor('#996699')">
										<AREA COORDS="34,15,46,28"
											HREF="javascript:SendColor('#CC00CC')">
										<AREA COORDS="22,15,35,28"
											HREF="javascript:SendColor('#CC33CC')">
										<AREA COORDS="11,15,23,28"
											HREF="javascript:SendColor('#CC66CC')">
										<AREA COORDS="0,15,12,28"
											HREF="javascript:SendColor('#CC99CC')">
										<AREA COORDS="36,0,46,16"
											HREF="javascript:SendColor('#FF00FF')">
										<AREA COORDS="27,0,37,16"
											HREF="javascript:SendColor('#FF33FF')">
										<AREA COORDS="18,0,28,16"
											HREF="javascript:SendColor('#FF66FF')">
										<AREA COORDS="9,0,18,16"
											HREF="javascript:SendColor('#FF99FF')">
										<AREA COORDS="0,0,9,16" HREF="javascript:SendColor('#FFCCFF')">
									</MAP>
									<IMG SRC="<%=path%>/cms/include/css/colorpicker.gif" ISMAP
										USEMAP="#map_colorpal" width="257" height="91" border="0">
									<BR>
								</TD>
								<TD>
									<FONT CLASS="OraFieldText">背景色</FONT>
								</TD>
								<TD>
									<INPUT TYPE="text" NAME="p_color" SIZE="7" MAXLENGTH="7"
										VALUE="#336699">
								</TD>
							</TR>

						</TABLE>

					</div>

					<hr>
					<strong><font color='red'>正文:</font> </strong>
					<select name='bodystyle'>
						<option value='Text1'>
							样式1
						</option>
						<option value='Text2'>
							样式2
						</option>
						<option value='Text3'>
							样式3
						</option>
						<option value='Text4'>
							样式4
						</option>
					</select>
					使用自定义样式:
					<input type='checkbox' name='diystyleText' value='1'
						onClick="hideText()">
					<br>

					<div id='diyTextAll' style='display:none'>
						<table>
							<tr>
								<td>
									字体效果:
									<SELECT NAME="p_value1">
										<OPTION SELECTED value="None">
											<FONT CLASS="OraFieldText">&lt;无&gt;</FONT>
										<OPTION value="Plain">
											<FONT CLASS="OraFieldText">普通</FONT>
										<OPTION value="Underline">
											<FONT CLASS="OraFieldText">下划线</FONT>
										<OPTION value="Line-Through">
											<FONT CLASS="OraFieldText">删除线</FONT>
										<OPTION value="Underline Line-Through">
											<FONT CLASS="OraFieldText">下划线删除线</FONT>
									</SELECT>
								</td>
								<td>
									字体:
									<SELECT NAME="p_value2">
										<OPTION>
											&lt;Default&gt;
										<OPTION>
											Arial
										<OPTION>
											Arial Black
										<OPTION>
											Arial Narrow
										<OPTION>
											Arial Rounded MT Bold
										<OPTION SELECTED>
											Arial, Helvetica
										<OPTION>
											Book Antiqua
										<OPTION>
											Century Gothic
										<OPTION>
											Century Schoolbook
										<OPTION>
											Comic Sans MS
										<OPTION>
											Courier
										<OPTION>
											Courier New,Courier
										<OPTION>
											Haettenschweiler
										<OPTION>
											Helvetica
										<OPTION>
											Impact
										<OPTION>
											Marlett
										<OPTION>
											Monotype Sorts
										<OPTION>
											Tahoma
										<OPTION>
											Times New Roman
										<OPTION>
											Verdana
									</SELECT>
								</td>
								<td>
									字体大小:
									<SELECT NAME="p_value3">
										<OPTION>
											6pt
										<OPTION>
											7pt
										<OPTION>
											8pt
										<OPTION SELECTED>
											9pt
										<OPTION>
											10pt
										<OPTION>
											11pt
										<OPTION>
											12pt
										<OPTION>
											13pt
										<OPTION>
											14pt
										<OPTION>
											15pt
										<OPTION>
											16pt
										<OPTION>
											17pt
										<OPTION>
											18pt
									</SELECT>
								</td>
								<td>
									字体样式:
									<SELECT NAME="p_value4">
										<OPTION SELECTED value="Plain">
											<FONT CLASS="OraFieldText">普通</FONT>
										<OPTION value="Bold">
											<FONT CLASS="OraFieldText">粗体</FONT>
										<OPTION value="Italic">
											<FONT CLASS="OraFieldText">斜体</FONT>
										<OPTION value="Bold-Italic">
											<FONT CLASS="OraFieldText">粗斜体</FONT>
									</SELECT>
								</td>
							</tr>
						</table>
						<TABLE BORDER="0">
							<TR>
								<TD COLSPAN="2">
									<BR>
									<MAP NAME="map_colorpalx">
										<!-- ---- Grayscale ---- -->
										<AREA COORDS="243,75,258,91"
											HREF="javascript:SendColor1('#000000')">
										<AREA COORDS="243,60,258,76"
											HREF="javascript:SendColor1('#333333')">
										<AREA COORDS="243,45,258,61"
											HREF="javascript:SendColor1('#666666')">
										<AREA COORDS="243,30,258,46"
											HREF="javascript:SendColor1('#999999')">
										<AREA COORDS="243,15,258,31"
											HREF="javascript:SendColor1('#CCCCCC')">
										<AREA COORDS="243,0,258,16"
											HREF="javascript:SendColor1('#FFFFFF')">
										<!-- ---- Red-Magenta ---- -->
										<AREA COORDS="207,82,244,91"
											HREF="javascript:SendColor1('#660033')">
										<AREA COORDS="225,72,244,83"
											HREF="javascript:SendColor1('#990033')">
										<AREA COORDS="207,72,226,83"
											HREF="javascript:SendColor1('#993366')">
										<AREA COORDS="231,60,244,73"
											HREF="javascript:SendColor1('#CC0066')">
										<AREA COORDS="219,60,232,73"
											HREF="javascript:SendColor1('#CC3399')">
										<AREA COORDS="207,60,220,73"
											HREF="javascript:SendColor1('#CC6699')">
										<AREA COORDS="234,45,244,61"
											HREF="javascript:SendColor1('#FF0099')">
										<AREA COORDS="225,45,235,61"
											HREF="javascript:SendColor1('#FF3399')">
										<AREA COORDS="216,45,226,61"
											HREF="javascript:SendColor1('#FF6699')">
										<AREA COORDS="207,45,217,61"
											HREF="javascript:SendColor1('#FF99CC')">
										<!-- ---- Red ---- -->
										<AREA COORDS="184,82,208,91"
											HREF="javascript:SendColor1('#660000')">
										<AREA COORDS="162,82,185,91"
											HREF="javascript:SendColor1('#663333')">
										<AREA COORDS="192,72,208,83"
											HREF="javascript:SendColor1('#990000')">
										<AREA COORDS="177,72,193,83"
											HREF="javascript:SendColor1('#993333')">
										<AREA COORDS="162,72,178,83"
											HREF="javascript:SendColor1('#996666')">
										<AREA COORDS="196,60,208,73"
											HREF="javascript:SendColor1('#CC0000')">
										<AREA COORDS="184,60,197,73"
											HREF="javascript:SendColor1('#CC3333')">
										<AREA COORDS="173,60,185,73"
											HREF="javascript:SendColor1('#CC6666')">
										<AREA COORDS="162,60,174,73"
											HREF="javascript:SendColor1('#CC9999')">
										<AREA COORDS="198,45,208,61"
											HREF="javascript:SendColor1('#FF0000')">
										<AREA COORDS="189,45,199,61"
											HREF="javascript:SendColor1('#FF3333')">
										<AREA COORDS="180,45,190,61"
											HREF="javascript:SendColor1('#FF6666')">
										<AREA COORDS="171,45,181,61"
											HREF="javascript:SendColor1('#FF9999')">
										<AREA COORDS="162,45,172,61"
											HREF="javascript:SendColor1('#FFCCCC')">
										<!-- ---- Red-Yellow ---- -->
										<AREA COORDS="126,82,163,91"
											HREF="javascript:SendColor1('#663300')">
										<AREA COORDS="144,72,163,83"
											HREF="javascript:SendColor1('#993300')">
										<AREA COORDS="126,72,145,83"
											HREF="javascript:SendColor1('#996633')">
										<AREA COORDS="150,60,163,73"
											HREF="javascript:SendColor1('#CC6600')">
										<AREA COORDS="138,60,151,73"
											HREF="javascript:SendColor1('#CC6633')">
										<AREA COORDS="126,60,139,73"
											HREF="javascript:SendColor1('#CC9966')">
										<AREA COORDS="153,45,163,61"
											HREF="javascript:SendColor1('#FF6600')">
										<AREA COORDS="144,45,154,61"
											HREF="javascript:SendColor1('#FF9933')">
										<AREA COORDS="135,45,145,61"
											HREF="javascript:SendColor1('#FF9966')">
										<AREA COORDS="126,45,136,61"
											HREF="javascript:SendColor1('#FFCC00')">
										<!-- ---- Yellow ---- -->
										<AREA COORDS="103,82,127,91"
											HREF="javascript:SendColor1('#666600')">
										<AREA COORDS="81,82,104,91"
											HREF="javascript:SendColor1('#666633')">
										<AREA COORDS="111,72,127,83"
											HREF="javascript:SendColor1('#999900')">
										<AREA COORDS="96,72,112,83"
											HREF="javascript:SendColor1('#999933')">
										<AREA COORDS="81,72,97,83"
											HREF="javascript:SendColor1('#999966')">
										<AREA COORDS="115,60,127,73"
											HREF="javascript:SendColor1('#CCCC00')">
										<AREA COORDS="103,60,116,73"
											HREF="javascript:SendColor1('#CCCC33')">
										<AREA COORDS="92,60,104,73"
											HREF="javascript:SendColor1('#CCCC66')">
										<AREA COORDS="81,60,93,73"
											HREF="javascript:SendColor1('#CCCC99')">
										<AREA COORDS="117,45,127,61"
											HREF="javascript:SendColor1('#FFFF00')">
										<AREA COORDS="108,45,118,61"
											HREF="javascript:SendColor1('#FFFF33')">
										<AREA COORDS="99,45,109,61"
											HREF="javascript:SendColor1('#FFFF66')">
										<AREA COORDS="90,45,100,61"
											HREF="javascript:SendColor1('#FFFF99')">
										<AREA COORDS="81,45,91,61"
											HREF="javascript:SendColor1('#FFFFCC')">
										<!-- ---- Green-Yellow ---- -->
										<AREA COORDS="45,82,82,91"
											HREF="javascript:SendColor1('#336600')">
										<AREA COORDS="63,72,82,83"
											HREF="javascript:SendColor1('#339900')">
										<AREA COORDS="45,72,64,83"
											HREF="javascript:SendColor1('#669933')">
										<AREA COORDS="69,60,82,73"
											HREF="javascript:SendColor1('#66CC00')">
										<AREA COORDS="57,60,70,73"
											HREF="javascript:SendColor1('#99CC33')">
										<AREA COORDS="45,60,58,73"
											HREF="javascript:SendColor1('#99CC66')">
										<AREA COORDS="72,45,82,61"
											HREF="javascript:SendColor1('#99FF00')">
										<AREA COORDS="63,45,73,61"
											HREF="javascript:SendColor1('#99FF33')">
										<AREA COORDS="54,45,64,61"
											HREF="javascript:SendColor1('#99FF66')">
										<AREA COORDS="45,45,55,61"
											HREF="javascript:SendColor1('#CCFF99')">
										<!-- ---- Green ---- -->
										<AREA COORDS="22,82,46,91"
											HREF="javascript:SendColor1('#006600')">
										<AREA COORDS="0,82,23,91"
											HREF="javascript:SendColor1('#336633')">
										<AREA COORDS="30,72,46,83"
											HREF="javascript:SendColor1('#009900')">
										<AREA COORDS="15,72,31,83"
											HREF="javascript:SendColor1('#339933')">
										<AREA COORDS="0,72,16,83"
											HREF="javascript:SendColor1('#669966')">
										<AREA COORDS="34,60,46,73"
											HREF="javascript:SendColor1('#00CC00')">
										<AREA COORDS="22,60,35,73"
											HREF="javascript:SendColor1('#33CC33')">
										<AREA COORDS="11,60,23,73"
											HREF="javascript:SendColor1('#66CC66')">
										<AREA COORDS="0,60,12,73"
											HREF="javascript:SendColor1('#99CC99')">
										<AREA COORDS="36,45,46,61"
											HREF="javascript:SendColor1('#00FF00')">
										<AREA COORDS="27,45,37,61"
											HREF="javascript:SendColor1('#33FF33')">
										<AREA COORDS="18,45,28,61"
											HREF="javascript:SendColor1('#66FF66')">
										<AREA COORDS="9,45,19,61"
											HREF="javascript:SendColor1('#99FF99')">
										<AREA COORDS="0,45,10,61"
											HREF="javascript:SendColor1('#CCFFCC')">
										<!-- ---- Green-Cyan ---- -->
										<AREA COORDS="207,37,244,46"
											HREF="javascript:SendColor1('#006633')">
										<AREA COORDS="225,27,244,38"
											HREF="javascript:SendColor1('#009933')">
										<AREA COORDS="207,27,226,38"
											HREF="javascript:SendColor1('#339966')">
										<AREA COORDS="231,15,244,28"
											HREF="javascript:SendColor1('#00CC66')">
										<AREA COORDS="219,15,232,28"
											HREF="javascript:SendColor1('#33CC66')">
										<AREA COORDS="207,15,220,28"
											HREF="javascript:SendColor1('#66CC99')">
										<AREA COORDS="234,0,244,16"
											HREF="javascript:SendColor1('#00FF66')">
										<AREA COORDS="225,0,235,16"
											HREF="javascript:SendColor1('#33FF99')">
										<AREA COORDS="216,0,226,16"
											HREF="javascript:SendColor1('#66FF99')">
										<AREA COORDS="207,0,217,16"
											HREF="javascript:SendColor1('#99FFCC')">
										<!-- ---- Cyan ---- -->
										<AREA COORDS="184,37,208,46"
											HREF="javascript:SendColor1('#006666')">
										<AREA COORDS="162,37,185,46"
											HREF="javascript:SendColor1('#336666')">
										<AREA COORDS="192,27,208,38"
											HREF="javascript:SendColor1('#009999')">
										<AREA COORDS="177,27,193,38"
											HREF="javascript:SendColor1('#339999')">
										<AREA COORDS="162,27,178,38"
											HREF="javascript:SendColor1('#669999')">
										<AREA COORDS="196,15,208,28"
											HREF="javascript:SendColor1('#00CCCC')">
										<AREA COORDS="184,15,197,28"
											HREF="javascript:SendColor1('#33CCCC')">
										<AREA COORDS="173,15,185,28"
											HREF="javascript:SendColor1('#66CCCC')">
										<AREA COORDS="162,15,174,28"
											HREF="javascript:SendColor1('#99CCCC')">
										<AREA COORDS="198,0,208,16"
											HREF="javascript:SendColor1('#00FFFF')">
										<AREA COORDS="189,0,199,16"
											HREF="javascript:SendColor1('#33FFFF')">
										<AREA COORDS="180,0,190,16"
											HREF="javascript:SendColor1('#66FFFF')">
										<AREA COORDS="171,0,181,16"
											HREF="javascript:SendColor1('#99FFFF')">
										<AREA COORDS="162,0,172,16"
											HREF="javascript:SendColor1('#CCFFFF')">
										<!-- ---- Blue-Cyan ---- -->
										<AREA COORDS="126,37,163,46"
											HREF="javascript:SendColor1('#003366')">
										<AREA COORDS="144,27,163,38"
											HREF="javascript:SendColor1('#003399')">
										<AREA COORDS="126,27,145,38"
											HREF="javascript:SendColor1('#336699')">
										<AREA COORDS="150,15,163,28"
											HREF="javascript:SendColor1('#0066CC')">
										<AREA COORDS="138,15,151,28"
											HREF="javascript:SendColor1('#3399CC')">
										<AREA COORDS="126,15,139,28"
											HREF="javascript:SendColor1('#6699CC')">
										<AREA COORDS="153,0,163,16"
											HREF="javascript:SendColor1('#0099FF')">
										<AREA COORDS="144,0,154,16"
											HREF="javascript:SendColor1('#3399FF')">
										<AREA COORDS="135,0,145,16"
											HREF="javascript:SendColor1('#6699FF')">
										<AREA COORDS="126,0,136,16"
											HREF="javascript:SendColor1('#99CCFF')">
										<!-- ---- Blue-Cyan ---- -->
										<AREA COORDS="126,37,163,46"
											HREF="javascript:SendColor1('#003366')">
										<AREA COORDS="144,27,163,38"
											HREF="javascript:SendColor1('#003399')">
										<AREA COORDS="126,27,145,38"
											HREF="javascript:SendColor1('#336699')">
										<AREA COORDS="150,15,163,28"
											HREF="javascript:SendColor1('#0066CC')">
										<AREA COORDS="138,15,151,28"
											HREF="javascript:SendColor1('#3399CC')">
										<AREA COORDS="126,15,139,28"
											HREF="javascript:SendColor1('#6699CC')">
										<AREA COORDS="153,0,163,16"
											HREF="javascript:SendColor1('#0099FF')">
										<AREA COORDS="144,0,154,16"
											HREF="javascript:SendColor1('#3399FF')">
										<AREA COORDS="135,0,145,16"
											HREF="javascript:SendColor1('#6699FF')">
										<AREA COORDS="126,0,136,16"
											HREF="javascript:SendColor1('#99CCFF')">
										<!-- ---- Blue ---- -->
										<AREA COORDS="103,37,127,46"
											HREF="javascript:SendColor1('#000066')">
										<AREA COORDS="81,37,104,46"
											HREF="javascript:SendColor1('#333366')">
										<AREA COORDS="111,27,127,38"
											HREF="javascript:SendColor1('#000099')">
										<AREA COORDS="96,27,112,38"
											HREF="javascript:SendColor1('#333399')">
										<AREA COORDS="81,27,97,38"
											HREF="javascript:SendColor1('#666699')">
										<AREA COORDS="115,15,127,28"
											HREF="javascript:SendColor1('#0000CC')">
										<AREA COORDS="103,15,116,28"
											HREF="javascript:SendColor1('#3333CC')">
										<AREA COORDS="92,15,104,28"
											HREF="javascript:SendColor1('#6666CC')">
										<AREA COORDS="81,15,93,28"
											HREF="javascript:SendColor1('#9999CC')">
										<AREA COORDS="117,0,127,16"
											HREF="javascript:SendColor1('#0000FF')">
										<AREA COORDS="108,0,118,16"
											HREF="javascript:SendColor1('#3333FF')">
										<AREA COORDS="99,0,109,16"
											HREF="javascript:SendColor1('#6666FF')">
										<AREA COORDS="90,0,100,16"
											HREF="javascript:SendColor1('#9999FF')">
										<AREA COORDS="81,0,91,16"
											HREF="javascript:SendColor1('#CCCCFF')">
										<!-- ---- Magenta-Blue ---- -->
										<AREA COORDS="45,37,82,46"
											HREF="javascript:SendColor1('#330066')">
										<AREA COORDS="63,27,82,38"
											HREF="javascript:SendColor1('#330099')">
										<AREA COORDS="45,27,64,38"
											HREF="javascript:SendColor1('#663399')">
										<AREA COORDS="69,15,82,28"
											HREF="javascript:SendColor1('#6600CC')">
										<AREA COORDS="57,15,70,28"
											HREF="javascript:SendColor1('#6633CC')">
										<AREA COORDS="45,15,58,28"
											HREF="javascript:SendColor1('#9966CC')">
										<AREA COORDS="72,0,82,16"
											HREF="javascript:SendColor1('#6600FF')">
										<AREA COORDS="63,0,73,16"
											HREF="javascript:SendColor1('#9933FF')">
										<AREA COORDS="54,0,64,16"
											HREF="javascript:SendColor1('#9966FF')">
										<AREA COORDS="45,0,55,16"
											HREF="javascript:SendColor1('#CC99FF')">
										<!-- ---- Magenta ---- -->
										<AREA COORDS="22,37,46,46"
											HREF="javascript:SendColor1('#660066')">
										<AREA COORDS="0,37,23,46"
											HREF="javascript:SendColor1('#663366')">
										<AREA COORDS="30,27,46,38"
											HREF="javascript:SendColor1('#990099')">
										<AREA COORDS="15,27,31,38"
											HREF="javascript:SendColor1('#993399')">
										<AREA COORDS="0,27,16,38"
											HREF="javascript:SendColor1('#996699')">
										<AREA COORDS="34,15,46,28"
											HREF="javascript:SendColor1('#CC00CC')">
										<AREA COORDS="22,15,35,28"
											HREF="javascript:SendColor1('#CC33CC')">
										<AREA COORDS="11,15,23,28"
											HREF="javascript:SendColor1('#CC66CC')">
										<AREA COORDS="0,15,12,28"
											HREF="javascript:SendColor1('#CC99CC')">
										<AREA COORDS="36,0,46,16"
											HREF="javascript:SendColor1('#FF00FF')">
										<AREA COORDS="27,0,37,16"
											HREF="javascript:SendColor1('#FF33FF')">
										<AREA COORDS="18,0,28,16"
											HREF="javascript:SendColor1('#FF66FF')">
										<AREA COORDS="9,0,18,16"
											HREF="javascript:SendColor1('#FF99FF')">
										<AREA COORDS="0,0,9,16"
											HREF="javascript:SendColor1('#FFCCFF')">
									</MAP>
									<IMG SRC="<%=path%>/cms/include/css/colorpicker.gif" ISMAP
										USEMAP="#map_colorpalx" width="257" height="91" border="0">
									<BR>
								</TD>
								<TD>
									<FONT CLASS="OraFieldText">背景色</FONT>
								</TD>
								<TD>
									<INPUT TYPE="text" NAME="p_color1" SIZE="7" MAXLENGTH="7"
										VALUE="#336699">

								</TD>
							</TR>

						</TABLE>

					</div>
					<br>
					<TEXTAREA name='article' cols='90' rows='10'></TEXTAREA>
					<br>
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
