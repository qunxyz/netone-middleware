<%@ page contentType="text/html; charset=gb2312" language="java"  errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%

	String msg = (String)session.getAttribute("msg");
    String flag = (String)request.getParameter("style");

	//int flag = style;
	String style;
	if(flag==null||flag.equals("")){
		 flag = (String)session.getAttribute("style");
	}
    switch(Integer.parseInt(flag))
	{
	case 0:
      	style = "证书验证成功!";
      	break;
    case 1:
      	style ="需要使用福建CA数字证书登录!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
      	break;
	case 9:
		style ="该证书中的单位在系统中不存在!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
		
		
	case 4001:
		style ="内存分配失败或数据格式错误!错误代码为：4001!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806。";
		break;
	case 4002:
		style ="数据格式错误!错误代码为：4002!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4003:
		style ="访问数据库时，发生系统错误!<br>错误代码为：4003!如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4004:
		style ="通讯数据传输错误!错误代码为：4004!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4005:
		style ="无效参数!错误代码为：4005!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4006:
		style ="用户文件错误!错误代码为：4006!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4007:
		style ="证书错误!错误代码为：4007!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;

	case 4011:
		style ="您的数字证书非福建CA签发的数字证书!错误代码为：4011!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4012:
		style ="您的数字证书已过期，请及时更新!请检查您的数字证书是否已过期，"+
		"<br>或者客户本机电脑时间是否正确。错误代码为：4012!"+
		"<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4013:
		style ="您的数字证书已被注销!错误代码为：4013!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4014:
		style ="您的数字证书暂时未开通或者尚未缴纳证书服务费!错误代码为：4014!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4015:
		style ="您的数字证书暂时未开通或者证书信息正在审核整理中!错误代码为：4015!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4016:
		style ="您的数字证书网上服务期限已到，请您及时缴费!错误代码为：4016!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4021:
		style ="验证签名失败，不是证书对应的KEY签名的!错误代码为：4021!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4022:
		style ="签名的证书与认证的证书不是同一张!错误代码为：4022!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4023:
		style ="加密失败!错误代码为：4023!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4024:
		style ="解密失败!错误代码为：4024!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4031:
		style ="时间戳服务器内部错误!请联错误代码为：4031!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4032:
		style ="签发时间戳失败!错误代码为：4032!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4033:
		style ="验证时间戳失败!错误代码为：4033!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 4019:
		style ="需要使用福建CA数字证书登录!错误代码为：4019!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
		
	case 4020:
		style ="您的数字证书网上社保服务期限已到，请您及时缴费 。"+
		"<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;		
		
	case 4992:
		style ="打开证书失败.错误代码为：4992!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;	
	case 4993:
		style ="读证书失败.错误代码为：4993!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;	
	case 4994:
		style ="对数据签名失败.错误代码为：4994!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;	
	case 4991:
		style ="请确认您的电脑上是否已安装“福建CA数字证书客户端软件3.1版本或以上版本，错误代码为：4991!"+
		"<br>如果您还未安装，请您点击<a href='http://www.fjca.com.cn/download/downfile.asp?n=FJCAUserTool.exe'>下 载</a>最新版本的数字证书客户端软件."
		+"<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 9013:
		style="证书错误!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 9016:
		style="验证签名失败!<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806";
		break;
	case 9000:
		style="请确认用户是否已经更换key,如果更换证书请重新登陆后再操作!";
		break;
	case 9999:	
		style="网络故障，获取不到证书信息,请稍后再进行操作!";
		break;	
	default:
		style ="网络故障，请稍后再进行操作!";
	}
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>出错页面</title>
<script Language="JavaScript">
document.onreadystatechange= lFuncDocumentReady;
function lFuncDocumentReady(){
  if (document.readyState =="complete"){
     try {
      var msg=document.all.item("msg");
      var style=document.all.item("style");
      if(style!=null){
     	alert(style.value);
     }
      if (msg!=null)
      {

       // alert(msg.value);
      }

     }catch(e){
            alert(e.description);
     }
  }
}
</script>

</head>

<body>

<form  action="" method="post">
   	<td><INPUT type="hidden" name="msg" value="<%=msg %>" ></td>
   	<div align="center">
  <p align="center">&nbsp</p>
  <p align="center">&nbsp</p>
  <p align="center">登录失败了:</p>
  <p align="center">原因:<%=style %></p>
<div >

</form>

</body>
</html>

