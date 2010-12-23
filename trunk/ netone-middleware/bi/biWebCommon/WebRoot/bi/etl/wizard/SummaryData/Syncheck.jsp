<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
	</HEAD>
	<script type="text/javascript">
		function does(){
			var result = "";
			var checkmap = document.getElementsByName("checkmap");
			for(var i=0;i<checkmap.length;i++){
				if(checkmap[i].checked){
					result += checkmap[i].value+",";
				}
			}
			window.opener.document.all.limitnumber.value = result;
			window.close();
		}
	</script>
	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<table border="1" CELLPADDING="1" CELLSPACING="1">
				<tr>
					<td>
						选择
					</td>
					<c:forEach items="${resultlist}" var="list" begin="0" end="0">
						<c:forEach items="${list}" var="str">
							<td>
								${str}
							</td>
						</c:forEach>
					</c:forEach>
				</tr>
				<c:forEach items="${resultlist}" var="maps" begin="1" varStatus="i">
					<tr>
						<td>
							<c:forEach items="${maps}" var="map">
								<c:if test="${map.key!=null}">
									<input type="checkbox" value="${map.key}" name="checkmap">
								</c:if>
							</c:forEach>
						</td>
						<c:forEach items="${maps}" var="map">
							<td>
								${map.value}
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			<input type="button" name="chked" value="确定" onclick="does();">
			<input type="button" name="retu" value="取消" onclick="window.close();">
		</FORM>
	</BODY>
</HTML>
