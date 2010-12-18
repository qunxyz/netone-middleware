<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://www.oesee.com/chart" prefix="rs"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<rs:table ds="dailynew" data="list" />

<table border='1' width='50%'>
<c:forEach items='${list}' var='obj'>
<tr><td>
${obj.name}</td>
<td>
${obj.curprice}</td>
<td>
${obj.created}</td>
</tr>
</c:forEach>
</table>
