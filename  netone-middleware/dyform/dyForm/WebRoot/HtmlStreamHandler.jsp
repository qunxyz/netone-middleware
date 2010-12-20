<%@ page contentType="text/html; charset=GB2312"%>
<jsp:directive.page import="oe.rmi.client.RmiEntry" />
<jsp:directive.page import="oe.env.client.EnvService" />

<%
	String context = request.getContextPath();
	String url0 ="";
	String url1 ="";
	try{
	EnvService env = (EnvService) RmiEntry.iv("envinfo");
	url0 = env.fetchEnvValue("$dyformSer*");
	url1 = env.fetchEnvValue("$cssx*");
	}catch(Exception e){
		e.printStackTrace();
	}
%>

<html>
	<head>
		<title>OESEE ��̬��</title>
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

	</head>
	<body onLoad="hidenCheckRef()">
		<form name="dyDataForm" method="post"
			action="/dyForm/data/showdata.do">
			<table width="100%">
				<tr style="display:none">
					<td>
						null
					</td>
					<td>
						<input type="hidden" name="formcode"
							value="221dc6c6-5447-11dc-9ac4-7beebbae7269_" />
					</td>
				</tr>
				<tr style="display:none">
					<td>
						null
					</td>
					<td>
						<input type="hidden" name="hidesome" value="" />
					</td>
				</tr>
				<tr style="display:none">
					<td>
						null
					</td>
					<td>
						<input type="hidden" name="lsh" value="1198053671111" />
					</td>
				</tr>
				<tr style="display:none">
					<td>
						null
					</td>
					<td>
						<input type="hidden" name="fatherlsh" value="1" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table>
							<tr>
								<td>
									<span>�ɼ�</span>
								</td>
								<td>
									<select name="column1" "/>
										<option value="A" selected="selected">
											A
										</option>
										<option value="B">
											B
										</option>
										<option value="C">
											C
										</option>
										<option value="D">
											D
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<span>�ۺ�����</span>
								</td>
								<td>
									<input type="text" name="column2" value="fffffff" />
								</td>
							</tr>
							<tr>
								<td>
									<span></span>
								</td>
								<td>
									<select name="checkinSl" "/>
										<option value="column1" selected="selected">
											no
										</option>
										<option value="column2">
											no
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<span></span>
								</td>
								<td>
									<select name="checkinNl" "/>
										<option value="column1" selected="selected">
											�ɼ�
										</option>
										<option value="column2">
											�ۺ�����
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<span></span>
								</td>
								<td>
									<select name="checkinMl" "/>
										<option value="column1" selected="selected">
											0
										</option>
										<option value="column2">
											0
										</option>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table>
							<tr>
								<td>
									<input type="button"
										onclick="checkAndCommit('/dyForm/data/data/createope.do')"
										name="but1" value="����" />
								</td>
								<td>
									<input type="button"
										onclick="location.href='/dyForm/data/data/list.do?formcode=221dc6c6-5447-11dc-9ac4-7beebbae7269_'"
										name="but2" value="����" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
