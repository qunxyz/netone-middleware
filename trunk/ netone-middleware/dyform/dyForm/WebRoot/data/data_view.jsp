<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="OESEE ¶¯Ì¬±íµ¥" />
		<h:css src="/include/css/oe.css" />
		<script type="text/javascript" src="/dyForm/include/js/prototype.js"></script>
		<script type="text/javascript"
			src="/dyForm/include/js/portal/floatdiv.js"></script>
		<script type="text/javascript"
			src="/dyForm/include/js/portal/setInnerHTML.js"></script>
		<script type="text/javascript" src="/dyForm/include/js/portal/util.js"></script>
		<script type="text/javascript"
			src="/dyForm/include/js/portal/floatdiv.js"></script>
		<script type="text/javascript"
			src="/dyForm/include/js/portal/resizediv.js"></script>
		<script type="text/javascript"
			src="/dyForm/include/js/portal/cellToolsSample.js"></script>
		<script type="text/javascript">
		var contextpath='/cmsWeb/';
    	var sessionid = "<%=request.getRequestedSessionId()%>";
    	var modelid = "${cellid}";
		</script>

		<h:javascript src="/include/js/data/dataCheck.js" />
		<link href="/cmsWeb/DownloadSvl2?name=${styleinfo}" rel="stylesheet"
			type="text/css">
	</head>
	<!-- Head properties -->
	
	<body onLoad="hidenCheckRef();refreshdivAll('${cellid}','${onlyview}')">
		<h:dyform action="/data/showdata" />
		<!-- End properties -->
		
	</body>
</html>
