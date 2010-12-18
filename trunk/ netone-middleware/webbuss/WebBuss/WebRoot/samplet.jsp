<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>


<c:if test='${param.age<20}'>
you are boy
</c:if>
<c:if test='${param.age>20&&param.age<60}'>
you are men
</c:if>
<c:if test='${param.age>60}'>
you are old
</c:if>


<html>
<form action=''>
<input type='text' name='number' value=''/> <input type='submit'/>
</form>
</html>

<br>


<c:forEach begin='1' end='${param.number}' varStatus='cur'>

${cur.index}<img src='a.jpg'/> &nbsp;
</c:forEach>