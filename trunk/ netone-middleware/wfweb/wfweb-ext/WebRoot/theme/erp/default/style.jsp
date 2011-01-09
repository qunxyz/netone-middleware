<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cssURL = path + "/theme/erp/default";
%>

<style>

.tree_scrolling{
  width:100%;
  overflow:auto;
  scrollbar-face-color: #f1f1f1;
  scrollbar-highlight-color: #ffffff;
  scrollbar-shadow-color: #cccccc;
  scrollbar-3dlight-color: #cccccc;
  scrollbar-arrow-color: #330000;
  scrollbar-track-color: #ffffff;
  scrollbar-darkshadow-color: #ffffff;
}
table{
	border:0;
	/*border-collapse:collapse;*/
	
}
.BODY
{
	BACKGROUND-color: #160202;
	scrollbar-face-color: #f1f1f1;
	scrollbar-highlight-color: #ffffff;
	scrollbar-shadow-color: #cccccc;
	scrollbar-3dlight-color: #cccccc;
	scrollbar-arrow-color: #330000;
	scrollbar-track-color: #ffffff;
	scrollbar-darkshadow-color: #ffffff;
	margin: 0px;
	text-align: center;
}
.buttonOut{
  background-image:url(<%=cssURL%>/style/button.gif);
  background-repeat:no-repeat;
  border-width:0;
  font-size:9pt;
  width:51px;
  height:20px;
  cursor:hand;
}
.buttonOver{
	background-image:url(<%=cssURL%>/style/button.gif);
	background-repeat:no-repeat;
	border-width:0;
	font-size:9pt;
	width:51px;
	height:20px;
	cursor:hand;
	color: #990000;
}
.button{
  background-image:url(<%=cssURL%>/style/button.gif);
  background-repeat:no-repeat;
  border-width:0;
  font-size:9pt;
  width:51px;
  height:20px;
  background-color:#0066CC;
  cursor:hand;
}
.button_long{
  background-image:url(<%=cssURL%>/style/button_longbg.gif);
  background-repeat:no-repeat;
  border-width:0;
  font-size:9pt;
  width:70px;
  height:20px;
  background-color:#ffffff;
  cursor:hand;
}
TD
{
	FONT-SIZE: 12px;
	COLOR: #3B3B3B;
	LINE-HEIGHT: 16px;
	TEXT-DECORATION: none;
	background-position: top;
	vertical-align:top
}
.barblue {
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/bar_blue.gif);
	background-repeat:repeat-x;
	background-position: left bottom;
	height: 10px;
}

/*按钮样式*/

/*页眉页脚样式*/
.Header {
	background-color: #E7F3FF;
	background-attachment: scroll;
	background : url(<%=cssURL%>/style/header.gif);
	background-repeat: repeat;
	background-position: center top;
	height: 66px;
	width: 1000px;
	color: #1270AB;
}
.Footer {
	height: 26px;
	font-size: 12px;
	color: #ffffff;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/footer.gif);
	background-repeat: repeat-x;
	background-position: center top;
	text-align: center;


}
.flash{
	height: 219px;
	width: 786px;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/flash01.gif);
	background-repeat: no-repeat;
	background-position: right top;
	position: relative;
	float: none;
}
.news{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/news.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 177px;
	width: 210px;
}
.newbm{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/newbm.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 110px;
	width: 210px;
}
.newzw{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/newzw.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 110px;
	width: 210px;
}
.newzt{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/newzt.gif);
	background-repeat: no-repeat;
	background-position: center top;
	height: 60px;
	width: 200px;
	padding-bottom: 2px;
}
.Zxt{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/Zxt.gif);
	background-repeat: no-repeat;
	background-position: center top;
	height: 60px;
	width: 200px;
	padding-bottom: 2px;
}
/*超链接的样式*/
A:link
{
	FONT-SIZE:12px;
	COLOR:#6e6e6e;
	text-decoration: none;
}
A:visited
{
	FONT-SIZE:12px;
	COLOR:#6e6e6e;
	text-decoration: none;
}
A:hover
{
	FONT-SIZE:12px;
	color:#177fbe;
	text-decoration: none;

}
A.A01:link
{
	FONT-SIZE:12px;
	color: 000000;
	TEXT-DECORATION:none
}
A.A01:visited
{
	FONT-SIZE:12px;
	COLOR:#F0f0f0;
	TEXT-DECORATION:none
}
A.A01:active
{
	FONT-SIZE:12px;
	color: 000000;
	TEXT-DECORATION:none
}
A.A01:hover
{
	FONT-SIZE:12px;
	color:#FF6600;
	TEXT-DECORATION:underline
}
A.A02:link
{
	FONT-SIZE:12px;
	COLOR:#FF6600;
	TEXT-DECORATION:underline
}
A.A02:visited
{
	FONT-SIZE:12px;
	COLOR:#FF6600;
	TEXT-DECORATION:underline
}
A.A02:active
{
	FONT-SIZE:12px;
	COLOR:#FF6600;
	TEXT-DECORATION:underline
}
A.A02:hover
{
	FONT-SIZE:12px;
	color:#FF0000;
	TEXT-DECORATION:underline
}
A.A03:link
{
	FONT-SIZE:12px;
	COLOR:#7FC5E6;
	TEXT-DECORATION:underline
}
A.A03:visited
{
	FONT-SIZE:12px;
	COLOR:#7FC5E6;
	TEXT-DECORATION:underline
}
A.A03:active
{
	FONT-SIZE:12px;
	COLOR:#7FC5E6;
	TEXT-DECORATION:underline
}
A.A03:hover
{
	FONT-SIZE:12px;
	color:#FF0000;
	TEXT-DECORATION:underline
}
A.A04:link
{
	FONT-SIZE:12px;
	COLOR:#3B3B3B;
	TEXT-DECORATION:none
}
A.A04:visited
{
	FONT-SIZE:12px;
	COLOR:#3B3B3B;
	TEXT-DECORATION:none
}
A.A04:active
{
	FONT-SIZE:12px;
	COLOR:#3B3B3B;
	TEXT-DECORATION:none
}
A.A04:hover
{
	FONT-SIZE:12px;
	color:#3B3B3B;
	TEXT-DECORATION:none
}
A.A05:link
{
	FONT-SIZE:12px;
	COLOR:#0F4981;
	TEXT-DECORATION:none
}
A.A05:visited
{
	FONT-SIZE:12px;
	COLOR:#00000;
	TEXT-DECORATION:none
}
A.A05:active
{
	FONT-SIZE:12px;
	COLOR:#0F4981;
	TEXT-DECORATION:none
}
A.A05:hover
{
	FONT-SIZE:12px;
	color:#0045FF;
	TEXT-DECORATION:none
}
A.A06:link
{
	FONT-SIZE:12px;
	COLOR:#437C98;
	TEXT-DECORATION:none
}
A.A06:visited
{
	FONT-SIZE:12px;
	COLOR:#437C98;
	TEXT-DECORATION:none
}
A.A06:active
{
	FONT-SIZE:12px;
	COLOR:#437C98;
	TEXT-DECORATION:none
}
A.A06:hover
{
	FONT-SIZE:12px;
	color:#FF9E00;
	TEXT-DECORATION:none
}
A.A07:link
{
	FONT-SIZE:12px;
	COLOR:#8C8A8C;
	TEXT-DECORATION:none
}
A.A07:visited
{
	FONT-SIZE:12px;
	COLOR:#8C8A8C;
	TEXT-DECORATION:none
}
A.A07:active
{
	FONT-SIZE:12px;
	COLOR:#8C8A8C;
	TEXT-DECORATION:none
}
A.A07:hover
{
	FONT-SIZE:12px;
	color:#FF9E00;
	TEXT-DECORATION:none
}

A.A09:link
{
	FONT-SIZE:12px;
	COLOR:00007B;
	TEXT-DECORATION:underline
}
A.A09:visited
{
	FONT-SIZE:12px;
	COLOR:63008C;
	TEXT-DECORATION:underline
}
A.A09:hover
{
	FONT-SIZE:12px;
	color:#FF0000;
	TEXT-DECORATION:none
}

A.zixun:link
{
	FONT-SIZE:12px;
	COLOR:#7B7B7B;
	TEXT-DECORATION:none
}
A.zixun:visited
{
	FONT-SIZE:12px;
	COLOR:#616161;
	TEXT-DECORATION:none
}
A.zixun:hover
{
	FONT-SIZE:12px;
	color:#FF9C00;
	TEXT-DECORATION:underline
}
A.title:link
{
	FONT-SIZE:12px;
	COLOR:#8C8A8C;
	TEXT-DECORATION:none
}
A.title:visited
{
	FONT-SIZE:12px;
	COLOR:#8C8A8C;
	TEXT-DECORATION:none
}
A.title:hover
{
	FONT-SIZE:12px;
	color:#FFB71B;
	TEXT-DECORATION:underline
}
A.left:link
{
	FONT-SIZE:12px;
	COLOR:#FF7E00;
	TEXT-DECORATION:none
}
A.left:visited
{
	FONT-SIZE:12px;
	COLOR:#FF7E00;
	TEXT-DECORATION:none
}
A.left:active
{
	FONT-SIZE:12px;
	COLOR:#EA7401;
	TEXT-DECORATION:none
}
A.left:hover
{
	FONT-SIZE:12px;
	color:#FF7E00;
	TEXT-DECORATION:underline
}
A.ZT:link
{
	FONT-SIZE:12px;
	COLOR:#3B3B3B;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION:none
}
A.ZT:visited
{
	FONT-SIZE:16px;
	COLOR:#3B3B3B;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION:none
}
A.ZT:hover
{
	FONT-SIZE:16px;
	color:#FF0000;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION:underline
}
.kuoda 
{
	FONT-FAMILY: "??ì?"; 
	FONT-SIZE: 12px ; 
	line-height:160%;
	color: #000000
}
A.top:link
{
	FONT-SIZE:12px;
	color: 000000;
	TEXT-DECORATION:none
}
A.top:visited
{
	FONT-SIZE:12px;
	color: 000000;
	TEXT-DECORATION:none
}
A.top:active
{
	FONT-SIZE:12px;
	color: 000000;
	TEXT-DECORATION:none
}
A.top:hover
{
	FONT-SIZE:12px;
	color:#FFF300;
	TEXT-DECORATION:underline
}

.a1:link {
font-size: 12px; text-decoration:none;color:#FFFFFF
}
.a1:visited {
font-size: 12px; text-decoration:none;color:#FFFFFF
}
.a1:active {
font-size: 12px; text-decoration:none;color:#FFFFFF
}
.a1:hover {
font-size: 12px; text-decoration:none;color:#FF0000
}

UL, LI { list-style-type: circle; color: #0863D8; }
.Default-A:link {
	TEXT-DECORATION: none
}
.Default-A:visited {
	TEXT-DECORATION: none
}
.Default-A:hover {
	TEXT-DECORATION: underline
}
.Default-A-Black:link {
	FONT-WEIGHT: bold; 
	FONT-SIZE: 14pt; 
	COLOR: black; 
	TEXT-DECORATION: none
}
.Default-A-Black:hover {
	TEXT-DECORATION: underline
}
.Default-A-Navy:link {
	COLOR: #003399; TEXT-DECORATION: none
}
.Default-A-Navy:hover {
	COLOR: #ff3300; TEXT-DECORATION: underline
}
.map{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/map.gif);
	background-repeat: no-repeat;
	background-position: center top;
	height: 60px;
	width: 200px;
	padding-bottom: 2px;
}
/*导航条的样式*/
.ANaviItem{
	font-size: 12px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: left;
	background-image: url(<%=cssURL%>/style/navIcon.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: left bottom;
	padding-left: 22px;
	padding-right: 10px;
	vertical-align: bottom;
	height: 20px;
	background-color: #1270AB;

}
.ANaviItemOver{
	font-size: 12px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: left;
	background-image: url(<%=cssURL%>/style/navIcon.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: left bottom;
	padding-left: 22px;
	padding-top: 5px;
	padding-right: 10px;
	vertical-align: bottom;
	height: 22px;
	cursor: hand;
}
.ANaviSelect{
	font-size: 12px;
	font-style: normal;
	color: #00309c;
	text-decoration: none;
	text-align: left;
	background-image: url(<%=cssURL%>/style/navgradp.gif);
	background-attachment: scroll;
	background-repeat: repeat-x;
	background-position: left bottom;
	padding-left: 10px;
	padding-top: 5px;
	padding-right: 10px;
	vertical-align: bottom;
	height: 22px;
	cursor: default;
	border-bottom-width: 2px;
	border-bottom-style: solid;
	border-bottom-color: #3982D6;
}
.NaviTopItem a {
	color: #FFFFFF;
}
.NaviTopItem a:link{
	FONT-SIZE:12px;
	color: #FFFFFF;
	text-decoration: none;
}
.NaviTopItem a:visited{
	FONT-SIZE:12px;
	COLOR:#FFFFFF;
	text-decoration: none;
}
.NaviTopItem a:hover{
	FONT-SIZE:12px;
	color:#f4e334;
	text-decoration: none;

}
.NaviItembg{
	font-size: 14px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/navitembg.gif);
	background-attachment: scroll;
	background-repeat: repeat;
	background-position: left center;
	height: 43px;
	width: 120px;
	background-color: #FFFFFF;
	font-family: "黑体";

	vertical-align: middle;
}
.NaviItem{
	font-size: 14px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/navitem.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: left center;
	height: 34px;
	width: 120px;
	background-color: #FFFFFF;
	font-family: "黑体";

	vertical-align: middle;
}
.NaviItemleft{
	font-size: 12px;
	font-style: normal;
	color: #000000;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/naviItemleft.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: left center;
	vertical-align: bottom;
	height: 34px;
	width: 205px;
	background-color: #FFFFFF;

}
.NaviItemRight{
	font-size: 12px;
	font-style: normal;
	color: #000000;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviItemRight.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: right center;
	vertical-align: bottom;
	height: 32px;
	width: 75px;
	background-color: #FFFFFF;

}
.Seach{
	color: #000000;
	background-color: #EEF2F5;
	height: 25px;
	text-align: center;
	vertical-align: bottom;
	padding-top: 5px;
	width: 230px;
}
.R1ATop{
	font-size: 12px;
	font-style: normal;
	color: #000000;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/R1ATop.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: right top;
	vertical-align: bottom;
	height: 14px;
	width: 230px;
	background-color: #1A4F78;

}
.NaviBody{
	
}
.NaviItemOver{
	font-size: 12px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviItemOver.gif);
	background-attachment: scroll;
	background-repeat: repeat-x;
	background-position: left top;
	vertical-align: bottom;
	height: 28px;
	cursor: hand;
	width: 100px;
}
.NaviSelect{
	font-size: 12px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviSelect.gif);
	background-attachment: scroll;
	background-repeat: repeat-x;
	background-position: left top;
	vertical-align: bottom;
	height: 28px;
	cursor: default;
	width: 100px;
}

.NaviTopLeft{
	font-size: 12px;
	font-style: normal;
	color: #000000;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviTopLeft.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: right top;
	vertical-align: bottom;
	height: 32px;
	width: 43px;
	

}
.NaviTopRight{
	font-size: 12px;
	font-style: normal;
	color: #000000;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviTopRight.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: right top;
	vertical-align: bottom;
	height: 28px;
	width: 15px;
	background-color: #D2D9E2;

}
.NaviTopItem{
	font-size: 12px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviTopItem.gif);
	background-attachment: scroll;
	background-repeat: repeat-x;
	background-position: left top;
	height: 28px;
	width: 85px;
	background-color: #D2D9E2;
	vertical-align: bottom;
	padding-left: 20px;
}
.NaviTopOver{
	font-size: 12px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviTopOver.gif);
	background-attachment: scroll;
	background-repeat: repeat-x;
	background-position: left top;
	vertical-align: bottom;
	height: 28px;
	cursor: hand;
	width: 100px;
}
.NaviTopSelect{
	font-size: 12px;
	font-style: normal;
	color: #FFFFFF;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/NaviTopSelect.gif);
	background-attachment: scroll;
	background-repeat: repeat-x;
	background-position: left top;
	vertical-align: bottom;
	height: 28px;
	cursor: default;
	width: 100px;
}
/*框架控制的样式*/
.FrameControl{
	BACKGROUND-color: #E7F3FF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
}
/*左边下拉菜单的样式*/
.LeftMenuTitleIcon{
	BACKGROUND-color: #E7F3FF;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/LeftmenuTitleIcon.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: left top;
	height: 26px;
	width: 25px;
}
.LeftMenuTitle{
	BACKGROUND-color: #E7F3FF;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-decoration: none;
	text-align: left;
	background-image: url(<%=cssURL%>/style/LeftmenuTitle.gif);
	background-attachment: scroll;
	background-repeat: repeat-x;
	background-position: left top;
	height: 26px;
	width: 100px;
}
.LeftMenuTitleIconR{
	BACKGROUND-color: #E7F3FF;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
	background-image: url(<%=cssURL%>/style/LeftmenuTitleIconR.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: right top;
	width: 50px;
	height: 26px;
}
.LeftMenuBody{
	background-color: #FFFFFF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: 00309c;
	text-decoration: none;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #B8B8B8;
	border-bottom-color: #B8B8B8;
	border-left-color: #B8B8B8;
	text-align: left;
}
.LeftMenuItemIcon{
	text-decoration: none;
	background-image: url(<%=cssURL%>/style/LeftmenuItemIcon.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: right center;
	height: 22px;
	width: 20px;
	padding-left: 20px;
}
.LeftMenuItem1{
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: #00309c;
	text-decoration: none;
	text-align: left;
	width: 160px;
	padding-left: 0px;
}
.LeftMenuItem2{
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: #00309c;
	text-decoration: none;
	text-align: left;
	width: 120px;
}
/*编辑表单edit的样式*/
.EditTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ListTitleIcon.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 23px;
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #00379A;
}

.EditTitle{
	font-size: 12px;
	font-weight: bold;
	color: #FFFFFF;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ListTitle.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 23px;
}
.EditTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/listTitleIconR.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 23px;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #00379A;
}
.EditItem1{
	BACKGROUND-color: #EFF7FF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-decoration: none;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #003292;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #003292;
	padding-right: 5px;
	padding-left: 5px;
}
.EditItem2{
	BACKGROUND-color: #FFFFFF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-decoration: none;
	text-align: left;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #003292;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #003292;
	padding-left: 5px;
}
.EditBody{
	background-color: #F0F0F0;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-align: center;
	border-top-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-left-style: solid;
	border-top-color: #00379A;
	border-left-color: #00379A;
}
.EditExt{
	BACKGROUND-color: #FFFFFF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: right;
	padding-bottom: 5px;
	padding-right: 10px;
}
/*列表list的样式*/
.GridTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ListTitleIcon.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 23px;
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #00379A;
}

.GridTitle{
	font-size: 12px;
	font-weight: bold;
	color: #FFFFFF;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ListTitle.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 23px;
}
.GridTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/listTitleIconR.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 23px;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #00379A;
}

.GridColumn{
	background-color: #B6D1EF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-decoration: none;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #062E9F;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #062E9F;
	letter-spacing: 1px;
}
.GridBody{
	background-color: #DEE7F0;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-align: center;
	border-top-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #00379A;
	border-bottom-color: #00379A;
	border-left-color: #00379A;
}
.GridItemIcon{
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/LeftmenuItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
}
.GridItem1{
	background-color: #F5FAFF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-decoration: none;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #083694;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #083694;
}
.GridItem2{
	background-color: #ff0f0f0;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: 000000;
	text-decoration: none;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #083694;
}
.GridOpera{
	BACKGROUND-color: #E7F3FF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
}
.GridSet{
	BACKGROUND-color: #E7F3FF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #083694;
}
.GridFunc{
	background-color: #F0F0F0;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #003D97;
}
.GridInsert{
	background-color: #F0F0F0;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
}
.GridExt{
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: #000000;
	text-decoration: none;
	text-align: center;
	background-color: #F0F0F0;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #003D97;
}
/*查询表单query的样式*/

.QueryItem1{
	BACKGROUND-color: #E7F3FF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	color: #000000;
	text-decoration: none;
	text-align: center;
}
.QueryItem2{
	BACKGROUND-color: #E7F3FF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
}
.QueryExt{
	BACKGROUND-color: #E7F3FF;
	line-height: 22px;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	color: 000000;
	text-decoration: none;
	text-align: center;
}
/*用户信息条*/
.UserInfo{
	background-color: #E7F3FF;
	font-size: 12px;
	font-style: normal;
	color: 0D69CE;
	text-decoration: none;
	text-align: left;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/UserInfo.gif);
	background-repeat: repeat-x;
	background-position: left top;
	padding-left: 30px;
	height: 26px;
	padding-bottom: 3px;

}
.loginbg{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/loginbg.gif);
	background-repeat: no-repeat;
	background-position: left top;
	width: 1000px;
	height: 500px;
	line-height: 25px;
	vertical-align: bottom;
}
.loginbutton{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/LoginButton.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 40px;
	width: 43px;
	border: 0px none;
}
/*STree树的样式*/
.STreeBodyTop{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyTop.gif);
	background-repeat: repeat;
	background-position: left top;
	height: 40px;
}
.STreeBodyTopLeft{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyTopLeft.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 40px;
	width: 10px;
	background-color: #DFEEFF;
}
.STreeBodyTopRight{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyTopRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 40px;
	width: 10px;
}
.STreeBodyLeft{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyLeft.gif);
	background-repeat: repeat-y;
	background-position: left top;
	width: 10px;
}
.STreeBodyRight{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyRight.gif);
	background-repeat: repeat-y;
	background-position: right top;
	width: 10px;
	background-color: #FFFFFF;
}
.STreeBodyBottom{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyBottom.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 20px;
	
}
.STreeBodyBottomLeft{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyBottomLeft.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 20px;
	width: 10px;
	
}
.STreeBodyBottomRight{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/BBodyBottomRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 20px;
	width: 10px;
	
}
/*CTree树的样式*/
.CTreeBodyTop{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyTop.gif);
	background-repeat: repeat;
	background-position: left top;
	height: 40px;
}
.CTreeBodyTopLeft{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyTopLeft.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 40px;
	width: 10px;
	background-color: #DFEEFF;
}
.CTreeBodyTopRight{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyTopRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 40px;
	width: 10px;
}
.CTreeBodyLeft{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyLeft.gif);
	background-repeat: repeat-y;
	background-position: left top;
	width: 10px;
}
.CTreeBodyRight{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyRight.gif);
	background-repeat: repeat-y;
	background-position: right top;
	width: 10px;
	background-color: #FFFFFF;
}
.CTreeBodyBottom{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyBottom.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 20px;
	
}
.CTreeBodyBottomLeft{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyBottomLeft.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 20px;
	width: 10px;
	
}
.CTreeBodyBottomRight{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyBottomRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 20px;
	width: 10px;
	
}
/*内容左边部分的样式*/
.L1ATitleBg{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1ATitleBg.gif);
	background-repeat: no-repeat;
	background-position: center top;
	height: 110px;
	width: 200px;
}
.L1ATitleIcon{
	
}
.L1ATitle{
	
}
.L1ATitleIconR{
	
}
.L1AItemIcon{
	
}
.L1AItem1{
	background-color: FFFFFF;
	line-height: 30px;
}
.L1AItem2{
		
	background-color: FFFFFF;
}
.L1ABody{
	background-color: FFFFFF;
	border-right-width: 2px;
	border-left-width: 2px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #DBE2E7;
	border-left-color: #DBE2E7;
	width: 200px;
	text-align: center;
	padding-bottom: 8px;
}

.L1CTopimage{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TOP.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 130px;
	width: 200px;
}

/*内容中间部分的样式*/
.QQ{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/qq.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 209px;
	width: 105px;
}
.CATitleleft{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/CATitleleft.gif);
	background-repeat: repeat-x;
	background-position: left bottom;
	font-size: 12px;
	font-weight: bold;
	color: #198415;
	vertical-align: middle;
	text-align: center;	
}
.CATitleRight{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/CATitleRight.gif);
	background-repeat: repeat-x;
	background-position: left bottom;
	font-size: 12px;
	font-weight: bold;
	color: #198415;
	vertical-align: middle;
	text-align: center;	
}
.CATitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/CATitle.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 24px;
	width: 92px;
	font-size: 12px;
	font-weight: bold;
	color: #198415;
	vertical-align: middle;
	text-align: center;	
}
.CBTitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/CBTitle.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 24px;
	width: 92px;
	font-size: 12px;
	font-weight: bold;
	color: #198415;
	vertical-align: middle;
	text-align: center;	
}
.CABody{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/CABody.gif);
	background-repeat: repeat-x;
	background-position: left top;
	padding-top: 20px;
}
.CAItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/CAItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 25px;
	width: 20px;	

	

}
.CAItem1{
	font-size: 12px;
	color: #666666;
	line-height: 25px;
}
.C1AItem2{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	
}
.C1ATitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1ATitleIcon.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 25px;
	width: 300px;
	font-size: 12px;
	font-weight: bold;
	color: #198415;
	padding-left: 20px;
	vertical-align: middle;
	padding-top: 4px;
}
.C1ATitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1ATitle.gif);
	background-repeat: no-repeat;
	background-position:  center top;
	height: 25px;
	color: #FFFFFF;
	font-size: 12px;
	padding-top: 5px;
	font-weight: bold;
}
.C1ATitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1ATitleIconR.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 25px;
	width: 50px;
}
.C1AItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1AItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 25px;
	width: 20px;	

	

}
.C1AItem1{
	line-height: 25px;
	font-size: 14px;
	color: #42ad3b;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1AItem1.gif);
	background-repeat: no-repeat;
	background-position: left center;
	font-weight: bold;
	border-bottom-width: 1px;
	border-bottom-style: dashed;
	border-bottom-color: #969696;
	padding-left: 25px;
}
.C1AItem1B{
	border-bottom-width: 1px;
	border-bottom-style: dashed;
	border-bottom-color: #969696;
	padding-bottom: 5px;
}
.Next{
	line-height: 25px;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/next.gif);
	background-repeat: no-repeat;
	background-position: left top;
	padding-left: 25px;
	height: 80px;
}
.C1AItem2{
	line-height: 20px;
	font-size: 12px;
	color: #666666;
	padding-top: 3px;	
}
.Bottonlogin{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/botton.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 19px;
	width: 69px;
}
.C1ABody{
	line-height: 25px;
	padding-right: 10px;
	padding-left: 10px;
}
.C1ABodyB{
	line-height: 25px;
	padding-right: 10px;
	padding-left: 10px;
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #a3df8c;
}
.Book{
	background-image: url(<%=cssURL%>/style/book.gif);
	background-attachment: scroll;
	background-repeat: no-repeat;
	background-position: left top;
	height: 198px;
	width: 142px;
}
.Online{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/online.gif);
	background-repeat: no-repeat;
	background-position: center top;
	height: 50px;
	width: 150px;
}
.Down{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/down.gif);
	background-repeat: no-repeat;
	background-position: center top;
	height: 50px;
	width: 150px;
}
.C1BTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1BTitleIcon.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 25px;
	width: 200px;
	font-size: 12px;
	font-weight: bold;
	color: #1A6AA2;
	padding-left: 30px;
	padding-top: 4px;
}
.C1BTitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1BTitle.gif);
	background-repeat: no-repeat;
	background-position:  center top;
	height: 25px;
	
	width: 220px;
	color: #FFFFFF;
	font-size: 12px;
	padding-top: 5px;
	font-weight: bold;
}
.C1BTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/C1BTitleIconR.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 25px;
	
	width: 50px;
}
.C1BItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1AItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 20px;
	width: 20px;	

	

}
.C1BItem1{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	

}
.C1BItem2{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	
}
.C1BBody{
	background-color: FFFFFF;
	width: 470px;
	padding: 2px;
	text-align: left;
	margin-bottom: 2px;
}
.C1CTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyBottomRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 20px;
	
	
}
.C1CTitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyBottomRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 20px;
	
	
}
.C1CTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyBottomRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 20px;
	
	
}
.C1CItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1AItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 20px;
	width: 20px;	
	
}
.C1CItem1{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	
}
.C1CItem2{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	
	
}
.C1CBody{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/ABodyBottomRight.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 20px;
	
	
}
/*内容右边部分的样式*/
.R1ATitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1ATitleIcon.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 25px;
	width: 30px;
}
.R1ATitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1ATitle.gif);
	background-repeat: no-repeat;
	background-position:  center top;
	height: 25px;
	
	width: 170px;
	color: #FFFFFF;
	font-size: 12px;
	padding-top: 5px;
	font-weight: bold;
}
.R1ATitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1ATitleIconR.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 25px;
	
	width: 30px;
}
.R1AItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1AItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 20px;
	width: 20px;	

	

}
.R1AItem1{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	

}
.R1AItem2{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	
}
.R1ABody{
	background-color: F2F7F5;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #269160;
	border-right-color: #D9E6E2;
	border-bottom-color: #D9E6E2;
	border-left-color: #E3EAF0;
	width: 230px;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1ABody.gif);
	background-repeat: no-repeat;
	background-position: right bottom;
	padding: 2px;
	text-align: left;
	margin-bottom: 2px;
}
.R1BTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1BTitleIcon.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 25px;
	width: 30px;
}
.R1BTitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1BTitle.gif);
	background-repeat: no-repeat;
	background-position:  center top;
	height: 25px;
	width: 170px;
	color: #FFFFFF;
	font-size: 12px;
	padding-top: 5px;
	font-weight: bold;
}
.R1BTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1BTitleIconR.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 25px;
	width: 30px;
}
.R1BItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1BItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 20px;
	width: 20px;	


}
.R1BItem1{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	

}
.R1BItem2{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	
}
.R1BBody{
	background-color: E8F5FD;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #6154B1;
	border-right-color: #D9E6E2;
	border-bottom-color: #D9E6E2;
	border-left-color: #E3EAF0;
	width: 230px;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1BBody.gif);
	background-repeat: no-repeat;
	background-position: right bottom;
	padding: 2px;
	margin-bottom: 2px;
}
.R1CTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1CTitleIcon.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 25px;
	width: 30px;
}
.R1CTitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1CTitle.gif);
	background-repeat: no-repeat;
	background-position:  center top;
	height: 25px;
	width: 170px;
	color: #FFFFFF;
	font-size: 12px;
	padding-top: 5px;
	font-weight: bold;
}
.R1CTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1CTitleIconR.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 25px;
	width: 30px;
}
.R1CItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1CItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 20px;
	width: 20px;	

}
.R1CItem1{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	

}
.R1CItem2{
	height: 20px;
	font-size: 12px;
	color: #0B4A8D;	
	
	
}
.R1CBody{
	background-color: FCECF7;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #B784CB;
	border-right-color: #D9E6E2;
	border-bottom-color: #D9E6E2;
	border-left-color: #E3EAF0;
	width: 230px;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/R1CBody.gif);
	background-repeat: no-repeat;
	background-position: right bottom;
	padding: 2px;
}
/*内容部分*/
.LeftTablebody{
	padding-top: 3px;
	padding-right: 5px;
	padding-bottom: 20px;
	padding-left: 5px;
	background: #FADB9D;
}
.LeftList{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/leftlist.gif);
	background-repeat: repeat-y;
	background-position: left top;
	width: 15px;
	text-align: left;
	vertical-align: middle;
}
.LeftImg{
	background-image: url(<%=cssURL%>/style/left.gif);
	background-repeat: no-repeat;
	background-position: left top;
	width: 9px;
	height: 79px;
}
.L1BTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1BTitleIcon.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 24px;
	width: 40px;
}
.L1BTitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1BTitle.gif);
	background-repeat: repeat-x;
	background-position:  center top;
	height: 24px;
	width: 100px;
	color: #FFFFFF;
	font-size: 14px;
	text-align: left;
	padding-left: 5px;
	vertical-align: middle;
	font-weight: bold;
}
.L1BTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1BTitleIconR.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 24px;
	width: 60px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #bae4a5;
	text-align: left;
	vertical-align: middle;
}
.L1BItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1BItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 24px;
	width: 20px;	

	

}
.L1BItem1{
	font-size: 12px;
	color: #6f6f70;
	text-align: left;
	line-height: 24px;
}
.L1BItem1Select{
	font-size: 12px;
	color: #1d7db8;
	text-align: left;
	line-height: 24px;
	font-weight: bold;
}
.L1BItem2{
	height: 30px;
	font-size: 12px;
	color: #005a0c;	
	
}
.L1BBody{
	background-color: FFFFFF;
	padding-bottom: 15px;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1BBody.gif);
	background-repeat: repeat-x;
	background-position: left bottom;
	padding-right: 10px;
	padding-left: 10px;
	padding-top: 15px;
}

.Tablebody{
	background-color: #FFFFFF;
	border: 1px solid #8a8c8a;
	margin-bottom: 3px;
	padding-bottom: 3px;
}

.L1CTitleIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1CTitleIcon.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 24px;
	width: 40px;
}
.L1CTitle{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1CTitle.gif);
	background-repeat: repeat-x;
	background-position:  center top;
	height: 24px;
	width: 100px;
	color: #613611;
	font-size: 14px;
	text-align: left;
	padding-left: 5px;
	vertical-align: middle;
	font-weight: bold;
}
.L1CTitleIconR{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1CTitleIconR.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 24px;
	width: 60px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #bae4a5;
	text-align: left;
	vertical-align: middle;
}
.L1CTitleIconUp{
	padding-left:25px;
	background-image: url(<%=cssURL%>/style/up.gif);
	background-repeat: no-repeat;
	background-position: right top;
	vertical-align: middle;
	width: 10px;
	height: 10px;
}
.L1CTitleIconDown{
	padding-left:25px;
	background-image: url(<%=cssURL%>/style/down.gif);
	background-repeat: no-repeat;
	background-position: right top;
	vertical-align: left;
	width: 10px;
	height: 10px;
	/*background: url( '<%=cssURL%>/style/down.gif' ) no-repeat ;*/
}
.L1CItemIcon{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1CItemIcon.gif);
	background-repeat: no-repeat;
	background-position: center center;
	height: 24px;
	width: 20px;	

	

}
.L1CItem1{
	font-size: 12px;
	color: #000000;
	text-align: left;
	line-height: 24px;
	font-weight: normal;
}
.L1CItem2{
	height: 30px;
	font-size: 12px;
	color: #005a0c;	
	
}
.L1CBody{
	background-color: FFFFFF;
	padding-bottom: 15px;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/L1CBody.gif);
	background-repeat: repeat-x;
	background-position: left bottom;
	padding-right: 10px;
	padding-left: 10px;
	padding-top: 15px;
}
.RightTable{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/RightTable.gif);
	background-repeat: repeat-x;
	background-position: left top;
	padding-top: 15px;
	background-color: #DFC68E;
	padding-right: 10px;
	padding-left: 10px;	
}
.RightTableBody{
	padding-left: 10px;
	margin-left: 10px;	
}
.TabLeftA{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TableftB.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 24px;
	width: 15px;
}
.TabA{
	font-size: 12px;
	color: #613611;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TabB.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 24px;
	text-align: center;
	vertical-align: middle;
	font-weight: normal;
}
.TabRightA{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TabrihgtB.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 24px;
	width: 15px;
}
.TabLeftB{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TableftA.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 24px;
	width: 15px;
	font-weight: bold;
	color: 613611;
	font-size: 12px;
}
.TabB{
	font-size: 12px;
	color: #613611;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TabA.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 24px;
	text-align: center;
	vertical-align: middle;
	font-weight: normal;
}
.TabRightB{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TabrihgtA.gif);
	background-repeat: no-repeat;
	background-position: right top;
	height: 24px;
	width: 15px;
}
.TABBOTTON{
	background-attachment: scroll;
	background-color: #FFFFFF;
	background-image: url(<%=cssURL%>/style/TABBOTTON.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 20px;
}

.TabBody{
	background-color: #cfcecc;
	
}
.TabItem1{
	color: #000000;
	background-color: #FFFFFF;
	font-size: 12px;
	text-align: left;
	line-height: 24px;
	padding-left: 20px;
}
.TabItem2{
	color: #000000;
	font-size: 12px;
	text-align: left;
	line-height: 24px;
	padding-left: 20px;
	background: #FADB9D;
}
.TabListTitle{
	color: #613611;
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/TabListTitle.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 26px;
	font-size: 12px;
	line-height: 26px;
	text-align: center;
	vertical-align: middle;
}
.TabListItem1{
	color: #000000;
	background-color: #FFFFFF;
	font-size: 12px;
	text-align: center;
	line-height: 24px;
}
.TabListItem2{
	color: #000000;
	font-size: 12px;
	text-align: center;
	line-height: 24px;
	background: #FADB9D;
}
.Listbottonbody{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/Listbottonbody.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 28px;
	border: 1px solid #ece9d8;
}
.downmenu{
	background-attachment: scroll;
	background-image: url(<%=cssURL%>/style/downmenu.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 28px;
	font-size: 12px;
	color: #000000;
	padding-left: 30px;
}
.login{
	background-attachment: scroll;
	/*background-image: url(<%=cssURL%>/style/login.gif);*/
	background-repeat: no-repeat;
	background-position: center top;
	height: 100px;
	width: 200px;
	padding-bottom: 3px;	
}
.loginItem{
	font-size: 12px;
	font-weight: bold;
	color: #1a6aa2;
	line-height: 26px;
	text-align: center;
	vertical-align: bottom;
}
.bodyStyle{
	border:0;
	background: #DFC68E url( '<%=cssURL%>/style/erp_tb.gif' ) no-repeat 260px 120px;
}

/**-主页面 右边菜单栏样式--start--*/
#nav {
	padding: 0;
	margin: 0;
	list-style: none;
	height: 24px;
	background: #DFC68E;
	color: #613611;
	position: relative;
	z-index: 500;
	font-family: arial, verdana, sans-serif;
}

#nav li.top {
	display: block;
	float: left;
}

#nav li a.top_link {
	display: block;
	float: left;
	height: 24px;
	line-height: 24px;
	color: #613611;
	text-align: center;
	vertical-align: middle;
	text-decoration: none;
	font-size: 11px;
	font-weight: bold;
	padding: 0 0 0 5px;
	cursor: pointer;
}

#nav li a.top_link span {
	float: left;
	display: block;
	padding: 0 5px 0 12px;
	height: 30px;
}

#nav li a.top_link span.down {
	float: left;
	display: block;
	padding: 0 5px 0 12px;
	height: 30px;
}

#nav li a.top_link:hover {
	color: #000;
}

#nav li:hover>a.top_link {
	color: #613611;
}

/* Default list styling */
#nav li:hover {
	position: relative;
	z-index: 200;
}

/* keep the 'next' level invisible by placing it off screen. */
#nav ul,#nav li:hover ul ul,#nav li:hover ul li:hover ul ul,#nav li:hover ul li:hover ul li:hover ul ul,#nav li:hover ul li:hover ul li:hover ul li:hover ul ul
	{
	position: absolute;
	left: -9999px;
	top: -9999px;
	width: 0;
	height: 0;
	margin: 0;
	padding: 0;
	list-style: none;
}

#nav li:hover ul.sub {
	left: -15px;
	top: 26px;
	background: #fff;
	padding: 3px;
	border-bottom: 1px solid #613611;
	border-right: 1px solid #613611;
	border-left: 1px solid #613611;
	white-space: nowrap;
	width: 92px;
	height: auto;
	z-index: 300;
}

#nav li:hover ul.sub li {
	display: block;
	height: 20px;
	position: relative;
	float: left;
	width: 90px;
	font-weight: normal;
}

#nav li:hover ul.sub li a {
	display: block;
	font-size: 11px;
	height: 20px;
	width: 90px;
	line-height: 20px;
	text-indent: 5px;
	color: #000;
	text-decoration: none;
}

#nav li ul.sub li a.fly {
	background: #fff;
}

#nav li:hover ul.sub li a:hover {
	background: #613611;
	color: #fff;
}

#nav li:hover ul.sub li a.fly:hover {
	background: #613611;
	color: #fff;
}

#nav li:hover ul li:hover>a.fly {
	background: #613611;
	color: #fff;
}

#nav li:hover ul li:hover ul,#nav li:hover ul li:hover ul li:hover ul,#nav li:hover ul li:hover ul li:hover ul li:hover ul,#nav li:hover ul li:hover ul li:hover ul li:hover ul li:hover ul
	{
	left: 90px;
	top: -4px;
	background: #fff;
	padding: 3px;
	border: 1px solid #613611;
	white-space: nowrap;
	width: 90px;
	z-index: 400;
	height: auto;
}

#nav li ul li a:hover {
	color: #fff;
	background: #613611;
	border: 1px solid #613611;
	font-size: 11px;
}

#nav li ul {
	display: none;
	position: absolute;
	top: 26px;
	left: -15px;
	margin-top: 1px;
	/*width:100px;*/
	background: #fff;
	border: 1px solid #613611;
	text-decoration: none;
}

#nav li ul.sub li a {
	display: block;
	font-size: 11px;
	height: 0px;
	width: 100px;
	color: #000;
	text-decoration: none;
}

#nav li ul li ul {
	display: none;
	position: absolute;
	top: 0px;
	padding: 3px;
	left: 100px;
	width: 90px;
	white-space: nowrap;
	border: 1px solid #613611;
}
/**-主页面 右边菜单栏样式--end--*/

/*-主页面信息展示样式--STRAT-*/
td.info{
	color: #613611; 
	font-weight: bold;
}
/*-主页面信息展示样式--END-*/
/*- 等待扫描 */
.waitScan{
	 text-align: center;
	 background-color: #FADB9D;
	 border: 1;
}
</style>