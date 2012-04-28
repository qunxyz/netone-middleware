<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String cssURL = request.getContextPath()
			+ "/script/theme/main/blue/images";
%>
	<span>
		<center>
			<c:choose>
				<c:when test="${!empty htmlendinfo}">${htmlendinfo}</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</center>
	</span>
