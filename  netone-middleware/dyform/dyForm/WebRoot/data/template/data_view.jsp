<%@ page contentType="text/html; charset=UTF8"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="OESEE 动态表单" />
		<h:css src="/include/css/oe.css" />
	</head>
	<script type="text/javascript" src="/dyForm/include/js/prototype.js"></script>
	<h:javascript src="/include/js/data/dataCheck.js" />
	<body onLoad="hidenCheckRef()">
		<h:dyform action="/data/showdata" />
	</body>
</html>
