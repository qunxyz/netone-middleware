<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" href="<%=basePath%>js/jquery/ui/themes/redmond/jquery.ui.all.css">
		<script src="<%=basePath%>js/jquery/jquery-1.5.1.min.js"></script>
		<script src="<%=basePath%>js/jquery/external/jquery.bgiframe-2.1.2.js"></script>
		<script src="<%=basePath%>js/jquery/ui/jquery.ui.core.js"></script>
		<script src="<%=basePath%>js/jquery/ui/jquery.ui.widget.js"></script>
		<script src="<%=basePath%>js/jquery/ui/jquery.ui.mouse.js"></script>
		<script src="<%=basePath%>js/jquery/ui/jquery.ui.draggable.js"></script>
		<script src="<%=basePath%>js/jquery/ui/jquery.ui.position.js"></script>
		<script src="<%=basePath%>js/jquery/ui/jquery.ui.resizable.js"></script>
		<script src="<%=basePath%>js/jquery/ui/jquery.ui.dialog.js"></script>
		

		<script type="text/javascript">
		function hidenDo(){
			//pageleft.style.display='none';
			//show.style.display='';
			//hid.style.display='none';
			//pagelefttd.style.width='0';
			
			$('#pageleft').css('display','none');
			$('#pagelefttd').css('display','none');
			$('#show').css('display','');
			$('#hid').css('display','none');
			$('#rightContainer').css('margin-left','120px');
		}
		function showDo(){
			//pageleft.style.display='';
			//show.style.display='none';
			//hid.style.display='';
			//pagelefttd.style.width='170';
			
			$('#pageleft').css('display','');
			$('#pagelefttd').css('display','');
			$('#show').css('display','none');
			$('#hid').css('display','');
			$('#rightContainer').css('margin-left','0');
		}		
		$(function() {
			
		});
		function opendialog()
		{
			if(this.parent!=null)
			{
				this.parent.opendialog()
			}
			else
			{
				$( "#dialogx" ).dialog({title:'新建工单',height:220,width:530});
			}
		}
		</script>
		
		<style>
  *{
  	font-size:12px;
  	padding:0;
	margin:0;
  }
  a:link,a:visited {
	color: #444;
	text-decoration: none;
	color:#2F5196;
}
	</style>
	
	</head>
	<body  BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0>
		<table width="100%" height="100%" border="0" align="center"
			cellpadding="0" cellspacing="0" style="position:relative;top:-18px;z-index:0;">
			<tr>
				<td colspan='2'>
					<iframe id="head"
						src="/fck/PagelistViewSvl?pagename=simplefcklist&chkid=${param.fckid}"
						scrolling="no" resize="no" height="${param.height}" width="100%"
						frameborder='0'></iframe>
				</td>
			</tr>
			<tr>
				<c:if test="${param.layout!='right'}">
					<td id='pagelefttd' width="170" height="100%" valign="top" nowrap>
						<c:if test="${param.needhidden=='y'}">
							<div style="display:none;position: absolute; z-index: 999;top:200px;">
								<a href="javascript:hidenDo()" id='hidx'><font size='2'>[隐蔽]</font>
								</a>
								<a href="javascript:showDo()" id='showx' style="display: none"><font
									size='2'>[显] </font> </a>
							</div>
						</c:if>
						<div id='pageleft'>
							<iframe id="proletleft" style="" 
								src="frames.do?listPath=${param.rs}&type=frame" scrolling="no"
								resize="no" height="100%" width="240" frameborder='0'></iframe>
						</div>
					</td>
					<!-- ${fn:replace(param.initurl,'$@','&')} -->
					<td height="100%" align='left'>
					<div id="rightContainer">
						<div style="color:#99BBE8;margin-top:21px;border: 1px solid #a5a5a5;line-height:30px;height:30px;width:960px;background:url('<%=basePath%>/frames/images/topmenu_bgx.gif') repeat-x left top">
							<div id="menuhidebtn" style="float:left;margin-left:15px;">
								<a href="javascript:hidenDo()" id='hid'>
									<font size='2'>&lt;&lt; 隐藏左侧菜单</font>
								</a>
								<a href="javascript:showDo()" id='show' style="display: none">
									<font size='2'>&lt;&lt; 显示左侧菜单 </font> 
								</a>
							</div> 
							<div style="float: right;margin-right:20px;"><font color='red'><rs:logininfo /> </font><font color='blue'><a
									href='<rs:loginout/>'>【注销】</a> </font><a href="<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=moreWorklist&query=1&mode=1&height=430&listtype=01&sortfield=&sort=&psize=20&appname=" target="proletright">[所有待办]</a> <a href="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=430&listtype=02&sortfield=&sort=&psize=20&appname=" target="proletright">[所有已办]</a> <a href="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=430&listtype=03&sortfield=&sort=&psize=20&appname=" target="proletright">[所有归档]</a></div>
							<div id="dialogx" style="height:220px;width:500px;display: none;">
							<iframe name="mydialog" id="mydialog" 
							src="<portal:envget envkey='WEBSER_CMSWEB'/>/PagelistpathRightSvl?pagename=appframelistx&appname=APPFRAME" scrolling="no"
							resize="no" style="height:220px;width:500px;" frameborder='0'></iframe>
							</div>
						</div>
						<div id="mynewcontent" style="margin-top: 6px; border:1px solid #99BBE8;display:none"></div>
						<iframe align="top" id="proletright" name="proletright" style="margin-top:4px;"
							src="<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=moreWorklist&query=1&mode=1&height=430&listtype=01&sortfield=&sort=&psize=20&appname=" scrolling="no"
							resize="no" height="100%" width="960" frameborder='0'></iframe>
					</div>
					</td>
				</c:if>
				<c:if test="${param.layout=='right'}">
					<td height="100%" align='left'>
						<iframe align="top" id="proletright"
							src="${fn:replace(param.initurl,'$@','&')}" scrolling="no"
							resize="no" height="100%" width="900" frameborder='0'></iframe>
					</td>
					<td id='pagelefttd' width="170" height="100%" valign="top" nowrap>
						<c:if test="${param.needhidden=='y'}">
							<div style="position: absolute; z-index: 9999;">
								<a href="javascript:hidenDo()" id='hid'><font size='2'>[隐蔽]</font>
								</a>
								<a href="javascript:showDo()" id='show' style="display: none"><font
									size='2'>[显] </font> </a>
							</div>
						</c:if>
						<div id='pageleft'>
							<iframe id="proletleft"
								src="frames.do?listPath=${param.rs}&type=frame" scrolling="no"
								resize="no" height="100%" width="100%" frameborder='0'></iframe>
						</div>
					</td>

				</c:if>
			</tr>
		</table>
	</body>
</html>
