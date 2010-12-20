<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="OESEE ¶¯Ì¬±íµ¥" />
		<h:css src="/include/css/oe.css" />
	</head>
	<h:javascript src="/include/js/data/dataDisplay.js"/>
	<body onLoad="hidenButton()">
		<h:dyform action="/data/showdata" />
	</body>
</html>
