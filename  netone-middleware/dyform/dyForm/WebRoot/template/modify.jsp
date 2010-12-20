<%@ page contentType="text/html; charset=GB2312"%>
<jsp:directive.page import="oe.rmi.client.RmiEntry" />
<jsp:directive.page import="oe.env.client.EnvService" />
<jsp:directive.page import="oe.cav.bean.logic.bus.TCsBus" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="oe.cav.bean.logic.column.TCsColumn" />
<jsp:directive.page import="org.apache.commons.beanutils.BeanUtils" />
<jsp:directive.page import="oe.midware.dyform.service.DyFormService" />
<jsp:directive.page import="java.util.ArrayList"/>

<%
	String fatherlsh = request.getParameter("fatherlsh");
	String context = request.getContextPath();
	String url0 = "";
	String url1 = "";
	try {
		EnvService env = (EnvService) RmiEntry.iv("envinfo");
		url0 = env.fetchEnvValue("%{dyformSer}");
		url1 = env.fetchEnvValue("%{cssx}");
	} catch (Exception e) {
		e.printStackTrace();
	}
	//modify logic
	String lsh=request.getParameter("lsh");
	String formcode=request.getParameter("formcode");
	// fetch current buss object
	TCsBus busForm = null;
	try {
		DyFormService dys = (DyFormService) RmiEntry.iv("dyhandle");
		busForm = dys.load(lsh, formcode);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// fetch this form's column reference
	List columnListAll = new ArrayList();
	try {
		DyFormService dys = (DyFormService) RmiEntry.iv("dyhandle");
		columnListAll = dys.fetchColumnList(formcode);
	} catch (Exception e) {
		e.printStackTrace();
	}

	List columnListPermission = columnListAll;
	List columnListReadonly = columnListPermission;

	StringBuffer butAll = new StringBuffer();
	for (Iterator iter = columnListAll.iterator(); iter.hasNext();) {
		TCsColumn element = (TCsColumn) iter.next();
		butAll.append(element.getColumnid() + ",");
	}

	StringBuffer butR = new StringBuffer();
	StringBuffer butV = new StringBuffer();
	for (Iterator iter = columnListReadonly.iterator(); iter.hasNext();) {
		TCsColumn element = (TCsColumn) iter.next();
		butR.append(element.getColumnid() + ",");
		String value = (String) BeanUtils.getProperty(busForm, element
		.getColumnid());
		butV.append(value + "$#");
	}

	StringBuffer butP = new StringBuffer();
	for (Iterator iter = columnListPermission.iterator(); iter
			.hasNext();) {
		TCsColumn element = (TCsColumn) iter.next();
		butP.append(element.getColumnid() + ",");
	}

	String allcolumn = butAll.toString();
	String readcolumn = butR.toString();
	String opeacolumn = butP.toString();
	String value = butV.toString();
%>


<html>
	<head>
		<title>OESEE 动态表单</title>
		<!-- System properties -->
		<base href="<%=url0%>/data/" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<meta name="keyword" content="oes" />
		<meta name="description" content="" />
		<meta name="author" content="robanco" />
		<meta name="copyright" content="" />
		<script language="javascript"
			src="<%=url1%>/scripts/web/common/Common.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript">
			win.ContextPath='<%=context%>';
		</script>
		<script language="javascript"
			src="<%=url1%>/scripts/web/validate/Validate.js"
			type="text/javascript"></script>
		<link href="<%=url1%>/css/oe.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=url1%>/prototype.js"></script>
		<script language="javascript"
			src="<%=url1%>/js/data/dataCheck.js" type="text/javascript">
			</script>
		<!-- System properties -->
	</head>
	<body onLoad="hidenCheckRef()">
		<form name="dyDataForm" method="post" action="/dyForm/data/showdata.do;jsessionid=8B0B8CA8C55A831CA83B15E8436FB2BC"><table width="100%"><tr style="display:none">
<td>null</td>
<td><input type="hidden" name="formcode" value="221dc6c6-5447-11dc-9ac4-7beebbae7269_"/></td>
</tr>
<tr style="display:none">
<td>null</td>
<td><input type="hidden" name="hidesome" value=""/></td>
</tr>
<tr style="display:none">
<td>null</td>
<td><input type="hidden" name="lsh" value=""/></td>
</tr>
<tr style="display:none">
<td>null</td>
<td><input type="hidden" name="fatherlsh" value="1"/></td>
</tr>
<tr>
<td colspan="2"><table>
<tr id="column1tr">
<td><span>成绩</span></td>
<td><select name="column1""/>
<option value="A" selected="selected">A</option>
<option value="B">B</option>
<option value="C">C</option>
<option value="D">D</option>
</select></td>
</tr>
<tr id="column2tr">
<td><span>综合评价</span></td>
<td><input type="text" name="column2" value="fffffff"/></td>
</tr>
<tr id="checkinSltr">
<td><span></span></td>
<td><select name="checkinSl""/>
<option value="column1" selected="selected">no</option>
<option value="column2">no</option>
</select></td>
</tr>
<tr id="checkinNltr">
<td><span></span></td>
<td><select name="checkinNl""/>
<option value="column1" selected="selected">成绩</option>
<option value="column2">综合评价</option>
</select></td>
</tr>
<tr id="checkinMltr">
<td><span></span></td>
<td><select name="checkinMl""/>
<option value="column1" selected="selected">0</option>
<option value="column2">0</option>
</select></td>
</tr>
</table>
</td>
</tr>
<tr>
<td colspan="2"><table>
<tr>
<td><input type="button" onclick="checkAndCommit('/dyForm/data/data/createope.do')" name="but1" value="创建"/></td>
<td><input type="button" onclick="location.href='/dyForm/data/data/list.do?formcode=221dc6c6-5447-11dc-9ac4-7beebbae7269_'" name="but2" value="返回"/></td>
</tr>
</table>
</td>
</tr>
</table></form>
			<script language="javascript">
			
			var value='<%=value%>'.split('$#');
			var readcolumn='<%=readcolumn%>'.split(',');
			var opeacolumn='<%=opeacolumn%>'.split(',');
			var allcolumn='<%=allcolumn%>'.split(',');
			
			//先删除隐蔽所有的字段
			for( var i=0;i<allcolumn.length-1;i++){
				document.getElementById(allcolumn[i]).value='';
				document.getElementById(allcolumn[i]+'tr').style.display='none';
			}
			//给可显示的set值,并且显示
			for( var i=0;i<readcolumn.length-1;i++){
				document.getElementById(readcolumn[i]).value=value[i];
				document.getElementById(readcolumn[i]+'tr').style.display='';
				document.getElementById(readcolumn[i]).readOnly=true;
			}
			//给可操作的授予操作权限
			for( var i=0;i<opeacolumn.length-1;i++){
				document.getElementById(opeacolumn[i]).readOnly=false;
			}
			</script>
	</body>
</html>