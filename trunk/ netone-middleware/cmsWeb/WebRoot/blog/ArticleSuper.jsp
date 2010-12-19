
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<script>
var initHTML =""
var partC=""
var partB=""
var partA=""
function start() {
	Editor.document.designMode="ON";
	Editor.document.open();
	Editor.document.close();
}
/*
******************************************************************************************************
conts和contp常常会包含浏览器自动添加的一些\r\n（回车、换行符），这会造成后面“磨”的困难，有必要先进行格式化。但是由于对于pre、textarea、script、style和xmp这几种标签的内容来说\r\n可能是有意义的，所以不能简单地用.replace(/[\r\n]/g,"")的办法去除。必须既要去除浏览器自动添加的\r\n，又要保全pre、textarea、script、style和xmp这几种标签的内容。
******************************************************************************************************
*/
function formatfor(va) {
  var t=va.replace(/\r/g,'');
  t = t.replace(/(<(script|textarea|xmp|pre|style).*?>)([^\r]*?)(<\/\2>)/img,
function (){return arguments[1]+arguments[3].replace(/\n/g, "\r")+arguments[4]})
  t = t.replace(/\n/g, "");
  return t
}
/*
****************************************
Created by Bound0 (bound0@tom.com)
http://bbs.blueidea.com/viewthread.php?tid=2661868
转载或应用时请保留此声明！
****************************************
*/
function first()
{
//首先要取得编辑区的内容
var oSel = Editor.document.selection.createRange();
var conts=oSel.htmlText //内容选中部分对应的代码，首尾可能带有多余标签（就是前面例子中的蓝色标签）。
var textLength = Editor.document.body.innerText.length
oSel.moveStart("character", -1*textLength) //把选择区的开始位置往前闪，再取一次内容
var contp=formatfor(oSel.htmlText) //选中部分及选中部分前的内容，末尾可能带有多余标签。
var conta=formatfor(Editor.document.body.innerHTML) //整个内容
var contpa=''

//接下来用上面取得的三个内容互相“磨”，把多余的标签“磨”掉。
var m=0
m=conta.indexOf(contp.substr(0,3)) //校正对齐contp和conta的开始位置，有时候conta开始处可能会有多余的<p>，造成两者对不齐
var f=contp.length
for(;f>0;f--)
{if(conta.substr(m,f)==contp.substr(0, f)){contpa=contp.substr(0,f);partC=conta.substr(m+f);break}}
var ko=contp.substr(f)
var kol=ko.length
var ty=conta.substr(m+f,kol)
var hu=""
for(var b=1;b<kol;b++)if(ko.substr(b)==ty.substr(0,kol-b)){hu=ko.substr(b);contpa+=hu;partC=partC.substr(kol-b);break}
/*
****************************************************************************************************
有时候浏览器自动补全的标签并非添加在选中区域的最外围。例如有时会把</p>结束标签添在</font>标签之前，而这里的</font>标签应该是保留在partB中的，如果把</font>连同</p>一起“磨”掉就不对了。对此采取的办法是检查被“磨”掉的碎渣部分，把碎渣捡起来“磨”好，再装到partB的末尾。
****************************************************************************************************
*/
var k=contpa.length
cont=conts.replace(/\n/g, "")
var u=cont.length
if(cont==contpa.substr(k-u)){partB=cont;partA=contpa.substr(0,k-u)}else{
for(u=cont.length;u>0;u--)
{if(cont.lastIndexOf(contpa.substr(k-u))!=-1){partB0=contpa.substr(k-u);partA0=contpa.substr(0,k-u);break}}
/*
****************************************************************************************************
对于选中的内容刚好处于一个标签的内部的这种情况，它的具体情况可能又是五花八门的，我个人采取的办法是把格式化和不格式化都尝试一下，除非格式化的结果令partB长度较长（这说明浏览器自动添加的那些\r\n使“磨”出来的partB长度变短），否则就不格式化。
****************************************************************************************************
*/
contt=formatfor(conts)
if(hu!="")if(contt.substr(contt.length-kol)==ko)contt=contt.substr(0,contt.length-kol)+hu
u=contt.length
var youm=contpa.lastIndexOf(contt)
if(youm!=-1){partB=contt;partA=contpa.substr(0,youm);partC=contpa.substr(youm+u)+partC}else{
for(;u>0;u--){if(contt.lastIndexOf(contpa.substr(k-u))!=-1){partB1=contpa.substr(k-u);partA1=contpa.substr(0,k-u);break}}
if(partB1.length>partB0.length){partB=partB1;partA=partA1}else{partB=partB0;partA=partA0}
	}
}
if(partB.substr(partB.length-1)=="<"){partB=partB.substr(0,partB.length-1);partC="<"+partC}
if(partB.substr(partB.length-2)=="</"){partB=partB.substr(0,partB.length-2);partC="</"+partC}
//显示按要求分好的A、B、C三段内容。
//alert(partA)
//alert(partB)
//alert(partC)

}

function updateFontEffect(){
   var effect=document.getElementById("p_valuea").value;
   
   initHTML=partA+"<font text-decoration='"+effect+"'>"+partB+"</font>"+partC;
  
   if(initHTML!=''){
   		Editor.document.write(initHTML);
   }
}
</script>
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
	</head>
	<body SCROLL="no" onload="start()" leftmargin=5 topmargin=5>
		<div>
			<button onClick='first();updateFontEffect();' class='butt'>字体效果</button>
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

			<button onClick='first();start();'>字体</button>
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
			<input type='button' value='字体大小' onClick='first();start();'>
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
			<input type='button' value='字体样式' onClick='first();start();'>
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
							<AREA COORDS="81,72,97,83" HREF="javascript:SendColor('#999966')">
							<AREA COORDS="115,60,127,73"
								HREF="javascript:SendColor('#CCCC00')">
							<AREA COORDS="103,60,116,73"
								HREF="javascript:SendColor('#CCCC33')">
							<AREA COORDS="92,60,104,73"
								HREF="javascript:SendColor('#CCCC66')">
							<AREA COORDS="81,60,93,73" HREF="javascript:SendColor('#CCCC99')">
							<AREA COORDS="117,45,127,61"
								HREF="javascript:SendColor('#FFFF00')">
							<AREA COORDS="108,45,118,61"
								HREF="javascript:SendColor('#FFFF33')">
							<AREA COORDS="99,45,109,61"
								HREF="javascript:SendColor('#FFFF66')">
							<AREA COORDS="90,45,100,61"
								HREF="javascript:SendColor('#FFFF99')">
							<AREA COORDS="81,45,91,61" HREF="javascript:SendColor('#FFFFCC')">
							<!-- ---- Green-Yellow ---- -->
							<AREA COORDS="45,82,82,91" HREF="javascript:SendColor('#336600')">
							<AREA COORDS="63,72,82,83" HREF="javascript:SendColor('#339900')">
							<AREA COORDS="45,72,64,83" HREF="javascript:SendColor('#669933')">
							<AREA COORDS="69,60,82,73" HREF="javascript:SendColor('#66CC00')">
							<AREA COORDS="57,60,70,73" HREF="javascript:SendColor('#99CC33')">
							<AREA COORDS="45,60,58,73" HREF="javascript:SendColor('#99CC66')">
							<AREA COORDS="72,45,82,61" HREF="javascript:SendColor('#99FF00')">
							<AREA COORDS="63,45,73,61" HREF="javascript:SendColor('#99FF33')">
							<AREA COORDS="54,45,64,61" HREF="javascript:SendColor('#99FF66')">
							<AREA COORDS="45,45,55,61" HREF="javascript:SendColor('#CCFF99')">
							<!-- ---- Green ---- -->
							<AREA COORDS="22,82,46,91" HREF="javascript:SendColor('#006600')">
							<AREA COORDS="0,82,23,91" HREF="javascript:SendColor('#336633')">
							<AREA COORDS="30,72,46,83" HREF="javascript:SendColor('#009900')">
							<AREA COORDS="15,72,31,83" HREF="javascript:SendColor('#339933')">
							<AREA COORDS="0,72,16,83" HREF="javascript:SendColor('#669966')">
							<AREA COORDS="34,60,46,73" HREF="javascript:SendColor('#00CC00')">
							<AREA COORDS="22,60,35,73" HREF="javascript:SendColor('#33CC33')">
							<AREA COORDS="11,60,23,73" HREF="javascript:SendColor('#66CC66')">
							<AREA COORDS="0,60,12,73" HREF="javascript:SendColor('#99CC99')">
							<AREA COORDS="36,45,46,61" HREF="javascript:SendColor('#00FF00')">
							<AREA COORDS="27,45,37,61" HREF="javascript:SendColor('#33FF33')">
							<AREA COORDS="18,45,28,61" HREF="javascript:SendColor('#66FF66')">
							<AREA COORDS="9,45,19,61" HREF="javascript:SendColor('#99FF99')">
							<AREA COORDS="0,45,10,61" HREF="javascript:SendColor('#CCFFCC')">
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
							<AREA COORDS="81,27,97,38" HREF="javascript:SendColor('#666699')">
							<AREA COORDS="115,15,127,28"
								HREF="javascript:SendColor('#0000CC')">
							<AREA COORDS="103,15,116,28"
								HREF="javascript:SendColor('#3333CC')">
							<AREA COORDS="92,15,104,28"
								HREF="javascript:SendColor('#6666CC')">
							<AREA COORDS="81,15,93,28" HREF="javascript:SendColor('#9999CC')">
							<AREA COORDS="117,0,127,16"
								HREF="javascript:SendColor('#0000FF')">
							<AREA COORDS="108,0,118,16"
								HREF="javascript:SendColor('#3333FF')">
							<AREA COORDS="99,0,109,16" HREF="javascript:SendColor('#6666FF')">
							<AREA COORDS="90,0,100,16" HREF="javascript:SendColor('#9999FF')">
							<AREA COORDS="81,0,91,16" HREF="javascript:SendColor('#CCCCFF')">
							<!-- ---- Magenta-Blue ---- -->
							<AREA COORDS="45,37,82,46" HREF="javascript:SendColor('#330066')">
							<AREA COORDS="63,27,82,38" HREF="javascript:SendColor('#330099')">
							<AREA COORDS="45,27,64,38" HREF="javascript:SendColor('#663399')">
							<AREA COORDS="69,15,82,28" HREF="javascript:SendColor('#6600CC')">
							<AREA COORDS="57,15,70,28" HREF="javascript:SendColor('#6633CC')">
							<AREA COORDS="45,15,58,28" HREF="javascript:SendColor('#9966CC')">
							<AREA COORDS="72,0,82,16" HREF="javascript:SendColor('#6600FF')">
							<AREA COORDS="63,0,73,16" HREF="javascript:SendColor('#9933FF')">
							<AREA COORDS="54,0,64,16" HREF="javascript:SendColor('#9966FF')">
							<AREA COORDS="45,0,55,16" HREF="javascript:SendColor('#CC99FF')">
							<!-- ---- Magenta ---- -->
							<AREA COORDS="22,37,46,46" HREF="javascript:SendColor('#660066')">
							<AREA COORDS="0,37,23,46" HREF="javascript:SendColor('#663366')">
							<AREA COORDS="30,27,46,38" HREF="javascript:SendColor('#990099')">
							<AREA COORDS="15,27,31,38" HREF="javascript:SendColor('#993399')">
							<AREA COORDS="0,27,16,38" HREF="javascript:SendColor('#996699')">
							<AREA COORDS="34,15,46,28" HREF="javascript:SendColor('#CC00CC')">
							<AREA COORDS="22,15,35,28" HREF="javascript:SendColor('#CC33CC')">
							<AREA COORDS="11,15,23,28" HREF="javascript:SendColor('#CC66CC')">
							<AREA COORDS="0,15,12,28" HREF="javascript:SendColor('#CC99CC')">
							<AREA COORDS="36,0,46,16" HREF="javascript:SendColor('#FF00FF')">
							<AREA COORDS="27,0,37,16" HREF="javascript:SendColor('#FF33FF')">
							<AREA COORDS="18,0,28,16" HREF="javascript:SendColor('#FF66FF')">
							<AREA COORDS="9,0,18,16" HREF="javascript:SendColor('#FF99FF')">
							<AREA COORDS="0,0,9,16" HREF="javascript:SendColor('#FFCCFF')">
						</MAP>
						<IMG SRC="<%=path%>/cms/include/css/colorpicker.gif" ISMAP
							USEMAP="#map_colorpal" width="257" height="91" border="0">
						<BR>
					</TD>
					<TD>
						<input type='button' value='背景色' onClick='first();start();'>
						<input type='button' value='字体色' onClick='first();start();'>
					</TD>
					<TD>
						<INPUT TYPE="text" NAME="p_color" SIZE="7" MAXLENGTH="7"
							VALUE="#336699">
					</TD>
				</TR>

			</TABLE>

		</div>

		<IFRAME id="Editor" Name="Editor" style="WIDTH: 550; HEIGHT: 480"></IFRAME>
	</body>
</html>
