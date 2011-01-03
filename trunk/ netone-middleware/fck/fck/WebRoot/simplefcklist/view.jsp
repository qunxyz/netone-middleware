<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="FCK" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		
		<title>œ‘ æ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		
		<link href="/fck/FCKeditor/editor/css/fck_editorarea.css" rel="stylesheet" type="text/css"/>
	
	</head>
	<body>

		<div style="width: 100%;height: 100%">
			
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" id="lie_table">

					<tr>
						
						<td>
							${upo.extendattribute}
						</td>
					</tr>

				</table>
	
		</div>
	</body>
</html>
