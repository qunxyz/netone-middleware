<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
    String tip =(String)request.getParameter("errormsg");
    response.setHeader("X-UA-Compatible","IE=EmulateIE8");	
	fjca.FJCAApps ca = new fjca.FJCAApps();
	//社保4000
	ca.setErrorBase(4000);
	//Windows 验证服务器IP
	ca.setServerURL("202.109.194.166:7000");

	// 产生随机数,正式的应用，随机数要在服务端产生传给客户端，登陆成功后清空。
	String strRandom = ca.FJCA_GenRandom();
	session.setAttribute("RANDOM",strRandom);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>登录页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link type="text/css" rel="stylesheet" href="css/css.css">
<script type="text/javascript" src="include/js/util.js"></script>
<script type="text/javascript">
		function fresh(){
			document.all.imgs.src="ImageCodeSvl?tmp="+Math.random();
		}	
		function nocn(){
			for(i=0;i<document.getElementsByName("username")[0].value.length;i++){
				var c = document.getElementsByName("username")[0].value.substr(i,1);
				var ts = escape(c);
				if(ts.substring(0,2) == "%u"){
					document.getElementsByName("username")[0].value = "";
				}
			}
		}
</script>
	<script type="text/javascript">
		//document.onreadystatechange= lFuncDocumentReady;
		function lFuncDocumentReady(){
		  if (document.readyState =="complete"){
		     try {
		     		//alert("登录前，请先确认是否插入数字证书，并已安装最新版本客户端软件!")
		  			if(doit()){
		  			   document.forms[0].submit();	
		  			 }
		     }catch(e){
		            alert(e.description);
		     }
		  }
		}
		
		function doit()
		{
			try{
			  //这里检测是否安装客户端版本
			  var b = false;
			  try{
				b = gs.OpenUSBKey();
			  }catch(e){
			  	location.href("errorbd.jsp?style=4991");
			  	gs.CloseUSBKey();
			    return false;
			  }
			  if(!b){
			    //alert("打开证书失败");
			    location.href("errorbd.jsp?style=4992");
			    gs.CloseUSBKey();
			    return false;
			  }
			  if(!gs.ReadCertFromKey()){
			    gs.CloseUSBKey();
			   //alert("读证书失败");
			    location.href("errorbd.jsp?style=4993");
			    gs.CloseUSBKey();
			    return false;
			  }
			  document.getElementById("textCert").value = gs.GetCertData();
			  document.getElementById("textOriginData").value = "<%=strRandom%>";
			  var strData = document.getElementById("textOriginData").value;
			  if(!gs.SignDataWithKey(strData)){
			  	//alert("11");
			    gs.CloseUSBKey();
			    location.href("errorbd.jsp?style=4994");
			    //alert("对数据签名失败");
			    return false;
			  }	
			  document.getElementById("textSignData").value = gs.GetSignData();
			
			  gs.CloseUSBKey();
			  //alert("ok");
			  return true; 
			}catch(e){
			  	location.href("errorbd.jsp?style=4991");
			  	gs.CloseUSBKey();
			  	return false;
			  	//alert(e.description);
			}
		}
		function sub(s){
			if(s){
				if(doit()) document.getElementById(s).submit();
			}
		}
	</script>
<style>
a img{ 
	border:none;
}
</style>
</head>
<script type="text/javascript">
		function fresh(){
			document.all.imgs.src="ImageCodeSvl?tmp="+Math.random();
		}	
		function nocn(){
			for(i=0;i<document.getElementsByName("username")[0].value.length;i++){
				var c = document.getElementsByName("username")[0].value.substr(i,1);
				var ts = escape(c);
				if(ts.substring(0,2) == "%u"){
					document.getElementsByName("username")[0].value = "";
				}
			}
		}
</script>
<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<form id="logonForm" name="logonForm" action="LoginSvlCA2" method="post">
<table height="100%" width="100%" border="0">
    <tbody>
      <tr>
        <td align="center" valign="middle">
<!-- ImageReady Slices (5_contact us.psd) -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="219" background="images/zydk_05.gif"><table width="1345" height="219" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="842" rowspan="3"><img src="images/zydk_00.gif" width="842" height="219" /></td>
        <td width="295"><img src="images/zydk_01.gif" width="295" height="64" /></td>
        <td width="208" rowspan="3"><img src="images/zydk_04.gif" width="208" height="219" /></td>
      </tr>
      <tr>
	<INPUT type="hidden" name="textCert" id="textCert" value="" />
 	<INPUT type="hidden" name="textOriginData" id="textOriginData" value="" />
  	<INPUT type="hidden" name="textSignData" id="textSignData" value="" />
        <td><a href="javascript:void(0);" ><img src="images/zydk_03.gif" width="295" height="90" onClick="sub('logonForm');"/></a></td>
      </tr>
      <tr>
        <td><img src="images/zydk_02.gif" width="295" height="65" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="1345" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="70"><div align="center"><span class="STYLE1">中国人民银行福州支行 版权所有 <br />
      技术支持：福建金贸科技信息有限公司<br />
    闽ICP备11002381号-9</span></div></td>
  </tr>
</table>
<!-- End ImageReady Slices -->
</td>
      </tr>
    </tbody>
  </table>

</form>
</BODY>
</html>
<OBJECT id="gs" name="SBFjCAEnAndSign" classid="CLSID:506038C2-52A5-4EA5-8F7D-F39B10265709" codebase="SignAndEncrypt.ocx"  >