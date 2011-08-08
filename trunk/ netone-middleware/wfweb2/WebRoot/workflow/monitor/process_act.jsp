<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String runtimeid = (String) request.getAttribute("runtimeid");
	String actid = (String) request.getAttribute("actid");
	String forminfo = (String) request.getAttribute("forminfo");
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>流程活动</title>

		<link href="<%=path%>/include/css/wf.css" rel="stylesheet"
			type="text/css">
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/workflow/runtimeUse.js"></SCRIPT>
		<script>
				function selected(){
				window.open('<%=basePath%>MSelectSvl?pagename=human&appname=DEPT.DEPT','_blank', 'height=380, width=600, top=0, left=0,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
				
			}
			
		function mselected(options){
			if(options){
				var users = "";
				var userids = "";
				for(var i=0;i<options.length;i++){
					userids += options[i].value + "[" +options[i].text+ "],";
				}

				document.all.recevier.value = userids  ;
	
			}
		}
		</script>
	</head>
	<body>
		<form method="post" action="">
			<input name="pathroot" type="hidden" value=<%=path%>>
			<table align='left' border='1' width='100%' height='100%'>
				<tr>
					<td colspan='2' width='100%' height='80%'>
						<iframe src='<%=path%>/vdata.do?runtimeid=<%=runtimeid%>&processid=${processid}&actid=${actid}'
							width='100%' height='100%'></iframe>
					</td>
				</tr>

				<tr>

					<td>
					
						<input type="button" value="提交流程"
							onclick="commit('<%=runtimeid%>','<%=actid%>');" class="butt" <c:if test="${permission<3}">disabled</c:if>/>

					</td>
					<td>
						<select name="activityto">
							<option value="none">
								选择跳转点
							</option>
							<c:forEach items="${act}" var="actpre" varStatus="vs">
								<option value="${actpre.id}">
									${actpre.name }
								</option>
							</c:forEach>
						</select>
						<input type="button" value="跳转流程"
							onclick="jump('<%=runtimeid%>','<%=actid%>');" class="butt" <c:if test="${permission<7}">disabled</c:if>/>

					</td>

				</tr>
				<tr>
				<td colspan='2'>
								
					   指定审批者:
					   <input type='text' value='' id='recevier' name='recevier'> 
					   <input type='button' value='选择' onclick='selected()'> &nbsp;
					   <input type="button" value="提交审批"
							onclick="committo'<%=runtimeid%>','<%=actid%>');" class="butt" <c:if test="${permission<3}">disabled</c:if>/>
					   
				
				</td>
				</tr>
			</table>
		</form>
	</body>
</html>
