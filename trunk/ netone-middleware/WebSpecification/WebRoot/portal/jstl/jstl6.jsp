<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>

		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">

		<c:if test="${1==1}" var="theTruth"/>
		The result of testing for (1==1) is: ${theTruth}
		<h3>
			Conditionally execute the body
		</h3>
		<c:if test="${2>0}">
				It's true that (2>0)!
    		</c:if>
	</BODY>
</HTML>
