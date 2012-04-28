<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'showProcessList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../common/metaExt.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
	<script language="javascript" type="text/javascript"
		src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312">
	</script>

	<script type="text/javascript">
		var chooseResult = 0;
		function next(){
			window.location='<%=basePath%>groupTerminal/groupTerminal.do?method=onShowView&chooseresult='+ chooseResult+'&operatemode=${param.operatemode}';
		}
	</script>


  </head>
  
  <body>
    <div id="box">
    	<div id="operationmenu">
				<div id="operationbt">保存</div>
				<div id="operationbt">上一步</div>
				<span id="operationbt" onClick="next()">下一步</span>
				<span id="operationbt" onClick="window.close()">取消</span>
			
		</div>
    	<div id="steptext">步骤：&nbsp;第&nbsp;<span style="font-weight: bold; font-size: 14px;">2</span>&nbsp;步，共3部</div>
    	<div id="titlebox"><img src="script/theme/main/blue/images/title.gif"/>&nbsp;审核</div>
    	<div id="helptips">帮助提示：这里主要是将表单退回到指定点，确认无误点击完成。如果需修改点击上一步，否取消。</div>

			<div id="processcontentbox">
				<div id="contenttitle"><img src="script/theme/main/blue/images/choosetitleicon.gif"/>&nbsp;处理方式（请选择）</div>
				<div id="chooseapproval">
					<span id="approvalitem"><input type="radio" name="item" value="0" onchange="chooseResult=0"/>通过</span>
					<c:if test="${param.operatemode!='03'}">
					<span id="approvalitem"><input type="radio" name="item" value="1" onchange="chooseResult=1"/>退办</span>
					<span id="approvalitem"><input type="radio" name="item" value="2" onchange="chooseResult=2"/>特送</span>
					</c:if>
				</div>
				<div id="contenttitle"><img src="script/theme/main/blue/images/choosetitleicon.gif"/>&nbsp;填写意见</div>
				<div id="opinionbox">
					<span>&nbsp;&nbsp;意见选择：</span>
					<span>
						<select width="70" >
							<option value="o">已阅</option>
							<option value="1">同意</option>
							<option value="2">通过</option>
						</select>
					</span>
					<span>&nbsp;&nbsp;<input type="checkbox" />加为我的常见意见。</span>
				</div>
				<div id="opiniontext"><textarea cols="55" rows="8"></textarea></div>
			</div>
    </div>
  </body>
</html>
