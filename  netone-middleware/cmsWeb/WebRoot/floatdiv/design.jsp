<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String sessionid = request.getRequestedSessionId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>Oesee NetOne ��ҵӦ�÷���ƽ̨</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<SCRIPT type="text/javascript">
    	var contextpath = "<%=path%>";
    	var sessionid = "<%=sessionid%>";
    	var modelid = "${model.modelid}";
    	var modelnaturalname="${model.naturalname}";
    </SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/setInnerHTML.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/floatdiv/floatdiv.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/floatdiv/resizediv.js"></SCRIPT>
		<script type="text/javascript"
			src="<%=path%>/include/js/cell/cellToolsSample.js"></script>
		<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/DownloadSvl2?name=${model.styleinfo}" rel="stylesheet"
			type="text/css">
		<TITLE>Oesee NetOne ��ҵӦ�÷���ƽ̨</TITLE>
	</head>

	<BODY>
		<table width='100%'>
			<tr>
				<td height="30px">
					<strong> <font color='red' size='5'>
							&nbsp;OESEE NETONE-Portal </font> </strong>
				</td>

				<td align='center'>
					��ӭ��: ${userinfo}
				</td>
				<td align='center'>
					<a>������</a>
					<a>������</a>
				</td>
			</tr>
			

		</table>

		<div
			style="width:100%; height:20; border: 1px solid; margin-top: 2px;  background-color:#C0C0C0;">
			<INPUT type='hidden' value='111' name='valueid'>
			<INPUT name='valuedo' style='display:none' type='button' value='��ť'
				onClick='addgroupcore()'>

			<table width='100%'>
				<tr>
					<td colspan='2' nowrap>
						&nbsp;&nbsp;&nbsp;
						<INPUT class="buttds" type='button' value='���ҳ'
							onClick="savelayout();addEleCore('BLKDs','${model.naturalname}')">
						&nbsp;
						<!-- ѡ��ģ�� don add 09-3-12 -->
						<INPUT class="buttds" type='button' value='ѡ��ģ��'
							onClick="savelayout();selectlist('PORTALPG')">
						&nbsp;
						
						<INPUT class="buttds" type='button' value='����ҳ'
							onClick="savelayout();manelementlist('${model.naturalname}')">

						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="buttds" value="�༭ҳ������"
							onclick="savelayout();editmodel()">
						<input type="button" class="buttds" value="����ҳ�鲼��"
							onclick="savelayout()">
						&nbsp;&nbsp;&nbsp;
						<!--  
						<input type='button' class='buttds' value='����ҳ��'
							onclick='exports();'>
						&nbsp;&nbsp;
						<input type='button' class='buttds' value='����ҳ��'
							onclick='inports();'>-->
						&nbsp;&nbsp;
						<input type='button' class='buttds' value='Ԥ��1'
							onclick='viewPortal(1);'>
						<input type='button' class='buttds' value='Ԥ��2'
							onclick='viewPortal(2);'>
						<input type='button' class='buttds' value='Ԥ��3'
							onclick='viewPortal(3);'>
						<input type='button' class='buttds' value='Ԥ��4'
							onclick='viewPortal(5);'>
					</td>

					<td align='right'  nowrap>
						<INPUT name='dispSuper' type='checkbox' value='1'
							onClick="dispLayOut()">
						��ʾ�߼����� &nbsp;&nbsp;

					</td>
				</tr>
				<tr>
					<td colspan='3' id='superDesignHr' style='display:none'>
						<hr>
					</td>
				</tr>
				<tr id='superDesign' style='display:none'>

					<td>
						<!--  
						<table align='left'>
							<tr>
								<td>
									<strong>��ʽ���:</strong>
								</td>
								<td id='DivStyle'></td>
								<td align='left'>
									<INPUT class='buttds' type='button' value='Ӧ��'
										onClick="addgroup()">
									<INPUT class='buttds' type='button' value='ɾ��'
										onClick='delEle();'>
									<INPUT class='buttds' type='button' value='���' onClick="">
								</td>
							<tr>
						</table>
						-->
					</td>


					<td>
						<!--  
						<table align='left'>
							<tr>
								<td>
									<strong>�������:</strong>
								</td>
								<td id='DivBlock'></td>
								<td align='left'>
									<INPUT class='buttds' type='button' value='ʹ��'
										onClick="addgroup()">
									<INPUT class='buttds' type='button' value='ɾ��'
										onClick='delEle();'>
									<INPUT class='buttds' type='button' value='���'
										onClick="addEleCore('BLK')">
								</td>
							<tr>
						</table>
						-->
					</td>



					<td align='left' nowrap>
						<strong>���ַ���:</strong>
						<input type='button' class='buttds' value='���һ��'
							onclick='addBoxdiv();'>
						&nbsp;&nbsp;
						<input type='button' class='buttds' value='ɾ��β��'
							onclick='removeBoxdiv();'>
					</td>

				</tr>
			</table>
		</div>




		<div id="templatediv" name="templatediv" class="floatdiv"
			style="display:none ; float:left;  height: 50px; overflow: visible; border: 0; margin: 1px; word-wrap: break-word; ">
			loading...
		</div>

		<div id="tmpboxdiv" class="boxdiv"
			style="display:none;width:10;height:100%;border:1 solid;float:left;overflow-x: auto;overflow-y: visible">
		</div>

		<div style="width:1024 ; height: 1000; overflow: visible;">
			<div id="container"
				style="width:1024 ; height: 95%; overflow: visible;">
				<c:forEach items="${listmap}" var="entry" varStatus="vs">
					<div id="${entry.key}" class="boxdiv"
						style="width:${widthlist[vs.index]};height:100%;border:1px solid;float:left;overflow-x: auto;overflow-y: visible">
						<c:forEach items="${entry.value}" var="fdiv">
							<script type="text/javascript">
		  						addDiv("${fdiv.infoCellid}","${idnamemap[fdiv.infoCellid]}","${entry.key}");
		  					</script>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
			<c:forEach items="${listmap}" var="entry" varStatus="vs">
				<script type="text/javascript">
					Resize.setResizeAble("${entry.key}",getNextSibling("${entry.key}"));
				</script>
			</c:forEach>
		</div>
	</body>
</html>
