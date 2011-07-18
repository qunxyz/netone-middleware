
<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cc"%>

<html>
	<head>
		<h:meta title="�ֶ����" />
		<h:css src="/include/css/oe.css" />
	</head>
	<script type="text/javascript" src="/dyForm/include/js/prototype.js"></script>
	<script type="text/javascript"
		src="/dyForm/include/js/design/column/column.js"></script>
	<script type="text/javascript"
		src="/dyForm/include/js/design/column/cellToolsSample.js"></script>
	<script>
	//�����ӵĴ���   2009-2-17
 function done()
 {
    var clunmName=document.getElementById("columname").value;
    if(clunmName=="")
    {
      window.alert("�ֶ�������Ϊ��");
      return ;
    }
    document.form.action="/dyForm/design/system/column/createope.do";
 	document.form.submit();
document.form.action="";
 }
</script>

	<body onLoad="hideFilebutton()">
		<!-- 2009-2-17
		<h:form action="/design/system/column" method="post">-->
		<form action="" id="form" name="form" method="post">
			<TABLE width="100%" height="100%" border="1" cellpadding="0"
				cellspacing="0" bordercolorlight="#000000" bordercolordark="#FFFFFF">
				<h:hidden property="formcode" />
				<h:hidden property="systemid" />
				<h:hidden property="statusinfo" />
				<tr>
					<td class="tdheaddes">
						&nbsp;�ֶ���
					</td>
					<td class="btext">
						<!--2009-2-17
						<h:text property="columname" require="true" styleClass="btext" />-->
						<input type="text" name="columname" id="columname" require="true"
							styleClass="btext">
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;�ֶ�����
					</td>
					<td class="bsel">
						<select id="viewtype" name="viewtype" onchange="choiceFile()">
							<cc:forEach items="${typeinfo}" var="typeinfos">
								<option value="${typeinfos.value.value}">
									${typeinfos.value.label}
								</option>
								
							</cc:forEach>
						</select>
						<!-- 2009-2-17
						<h:select property="viewtype" onchange="choiceFile()"
							require="true" styleClass="bsel">
							<h:option collection="typeinfo" property="value"
								labelProperty="label" />
						</h:select> -->
					</td>
				</tr>
				<tr id='treemantr' style='display: none'>
					<td class="tdheaddes">
						&nbsp;ѡ����ͼ
					</td>
					<td id='treeman'>
						<input type='text' name='dispvalue' id="dispvalue" readonly>
						<input type='button' value='ѡ��' onClick="openTree()">
					</td>
				</tr>


				<tr id='scriptinfo' style='display: none'>
					<td class="tdheaddes">
						&nbsp;Ӧ�ýű�
					</td>
					<td>
						<table>
							<tr>

								<td>
									<input id='userother' class='butt' type='button' value='ˢ��'
										onClick=flashScrpit('');
>
								</td>
								<td id='JppScriptDemoMore'></td>
								<td>
									&nbsp;
									<input id='userother' class='butt' type='button' value='  ���  '
										onClick=designJppDemoMore();>
								</td>
							</tr>
						</table>

					</td>
				</tr>


				<tr id='bkvalue' class="tdheaddes">
					<td class="tdheaddes">
						&nbsp;��ѡֵ
						<a href='tipColumnValue.html' target='_blank'><font
							color='red'>[����]</font> </a>
					</td>
					<td class="btext">
						<input id="valuelist" name="valuelist" type="text" size='60'>
						<!-- 	2009-2-17
						<h:text property="valuelist" require="false" styleClass="btext"
							size='60' /> -->
					</td>
				</tr>

				<tr>
					<td class="tdheaddes">
						&nbsp;ֻ����
					</td>
					<td class="bsel">

						<select id="opemode" name="opemode">
							<cc:forEach items="${booleaninfo}" var="booleaninfos">
								<option value="${booleaninfos.value.value}">
									${booleaninfos.value.label}
								</option>
							</cc:forEach>
						</select>
						<!-- 2009-2-17
						<h:select property="opemode" styleClass="bsel">
							<h:option collection="booleaninfo" property="value"
								labelProperty="label" />
						</h:select>
						 -->
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;�Ƿ����
					</td>
					<td class="bsel">
						<select id="musk" name="musk">
							<cc:forEach items="${booleaninfo}" var="booleaninfos">
								<option value="${booleaninfos.value.value}">
									${booleaninfos.value.label}
								</option>
							</cc:forEach>
						</select>

						<!--  2009-2-17
						<h:select property="musk" styleClass="bsel">
							<h:option collection="booleaninfo" property="value"
								labelProperty="label" />
						</h:select>
						-->
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;�Ƿ�����
					</td>
					<td class="bsel">
						<select id="useable" name="useable">
							<cc:forEach items="${booleaninfo}" var="booleaninfos">
								<option value="${booleaninfos.value.value}">
									${booleaninfos.value.label}
								</option>
							</cc:forEach>
						</select>
						<!--  2009-2-17
						<h:select property="useable" styleClass="bsel">
							<h:option collection="booleaninfo" property="value"
								labelProperty="label" />
						</h:select>
						-->
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;��չ����
						<a href='tip.html' target='_blank'><font color='red'>[����]</font>
						</a>
					</td>
					<td class="btext">
						<h:textarea property="extendattribute" require="false" cols="60"
							rows="5" />
						<h:textarea property="tip" require="false" cols="60" rows="15" />

					</td>

				</tr>
				<tr>
					<td align='left'>
						<h:submit property='fjsc' value="�ϴ�����"
							action="/design/system/column/upload.do" target="add"
							dialog="true" dialogResizable="true" dialogHeight="88"
							dialogWidth="500" styleClass="buttonOnTable" />
						<h:submit property='tpsc' value="�ϴ�ͼƬ"
							action="/design/system/column/upload.do" target="add"
							dialog="true" dialogResizable="true" dialogHeight="88"
							dialogWidth="500" styleClass="buttonOnTable" />
					</td>
					<td colspan="1">

						<input type="submit" value="�����ֶ�" target="_self"
							onclick="javascript:done();">

						<!-- 2009-2-17
						<h:submit value="�����ֶ�" action="/design/system/column/createope.do"
							target="_self" styleClass="buttonOnTable" />-->

						<input type="reset" name="Submit2" value="����" style="">
						<h:button value="����" onclick="goback()" target="_self"
							styleClass="buttonOnTable" />
					</td>
				</tr>
			</TABLE>
		</form>
		<!--</h:form>-->
	</body>
</html>
