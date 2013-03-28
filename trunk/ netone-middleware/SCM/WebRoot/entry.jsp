<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="oe.security3a.sso.onlineuser.CA2OnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUser"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

response.setHeader("X-UA-Compatible","IE=EmulateIE8");
OnlineUser oluser = new CA2OnlineUserMgr().getOnlineUser(request);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国人民银行贷款年审系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-right: 0px;
	background-color: #ededed;
	margin-top: 0px;
}
.STYLE2 {
	font-family: "宋体";
	font-size: 12px;
	color: #FFFFFF;
}
-->
a img{
	border:none;
}
span {
	color: #FFF;
}
</style>
</head>

<body>
<table width="1345" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="4"><img src="images/zydk_06.gif" width="1345" height="108" /></td>
  </tr>
  <tr>
    <td width="129" height="33" background="images/zydk_07.gif"><div align="right"><span>当前用户:</span></div></td>
    <td width="1216" background="images/zydk_07.gif"><%=oluser.getUserid() %></td>
  </tr>
</table>
<table width="1345" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="990" valign="top" bgcolor="#FFFFFF"><table width="990" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="465" height="84"><img src="images/zydk_08.gif" width="465" height="84" /></td>
        <td background="images/zydk_13.gif"><table width="406" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.RMYH.DKKNSSQB');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:525px; top:169px; line-height:28px; text-align:center;"><span><strong>新建申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
            <td width="5"></td>
            <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.DKKNSSQB&statusinfo=00');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:661px; top:169px; line-height:28px; text-align:center;"><span><strong>未提交申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
            <td width="5"></td>
            <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.DKKNSSQB&statusinfo=01,04,03');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:798px; top:169px; line-height:28px; text-align:center;"><span><strong>预审结果</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width="990" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="465" height="84"><img src="images/zydk_09.gif" width="465" height="84" /></td>
          <td background="images/zydk_13.gif"><table width="406" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.RMYH.DKKNSSQB');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:525px; top:253px; line-height:28px; text-align:center;"><span><strong>新建申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.DKKNSSQB&statusinfo=00');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:661px; top:253px; line-height:28px; text-align:center;"><span><strong>未提交申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.DKKNSSQB&statusinfo=01,04,03');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:798px; top:253px; line-height:28px; text-align:center;"><span><strong>预审结果</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table width="990" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="465" height="84"><img src="images/zydk_10.gif" width="465" height="84" /></td>
          <td background="images/zydk_13.gif"><table width="406" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.RMYH.XXBG');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:525px; top:337px; line-height:28px; text-align:center;"><span><strong>新建申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.XXBG&statusinfo=00');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:661px; top:337px; line-height:28px; text-align:center;"><span><strong>未提交申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.XXBG&statusinfo=01,04,03');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:798px; top:337px; line-height:28px; text-align:center;"><span><strong>预审结果</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table width="990" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="465" height="84"><img src="images/zydk_11.gif" width="465" height="84" /></td>
          <td background="images/zydk_13.gif"><table width="406" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.RMYH.JWDKSQ');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:525px; top:421px; line-height:28px; text-align:center;"><span><strong>新建申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.JWDKSQ&statusinfo=00');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:661px; top:421px; line-height:28px; text-align:center;"><span><strong>未提交申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.JWDKSQ&statusinfo=01,04,03');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:798px; top:421px; line-height:28px; text-align:center;"><span><strong>预审结果</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table width="990" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="465" height="84"><img src="images/zydk_12.gif" width="465" height="84" /></td>
          <td background="images/zydk_13.gif"><table width="406" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.RMYH.ZRRDK');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:527px; top:504px; line-height:28px; text-align:center;"><span><strong>新建申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.ZRRDK&statusinfo=00');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:663px; top:506px; line-height:28px; text-align:center;"><span><strong>未提交申请</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
                <td width="5"></td>
                <td><a href="javascript:void(0);" onclick="javascript:window.open('/scm/frame.do?method=onMainView&naturalname=APPFRAME.APPFRAME.RMYH.ZRRDK&statusinfo=01,04,03');"><div style="position:absolute; width:132px; height:28px; z-indent:2; left:802px; top:506px; line-height:28px; text-align:center;"><span><strong>预审结果</strong></span></div><img src="images/zydk_19.gif" width="132" height="28" /></a></td>
              </tr>
          </table></td>
        </tr>
      </table></td>
    <td width="355" valign="top" background="images/zydk_15.gif"><img src="images/zydk_14.gif" width="355" height="44" /></td>
  </tr>
</table>
<table width="1345" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30" align="right" bgcolor="#9f9f9f"><span class="STYLE2">中国人民银行福州支行 版权所有   闽ICP备11002381号-9&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
  </tr>
</table>
</body>
</html>
