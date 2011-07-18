<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="�����" />
		<h:css src="/include/css/oe.css" />
		<h:javascript src="/include/js/design/form/form.js" />
		<h:javascript src="/rsinclude/applist/applist.js" />

	</head>

	<body>
		<h:form action="/design/system/form/createope.do">
			<TABLE width="800" align="left" border="2" cellpadding="5"
				cellspacing="0" bordercolorlight="white" bordercolordark="#FFFFFF">
				<input type='hidden' name='pagepath' value='${param.pagepath}'/>
				<h:hidden property="systemid" />
				<h:hidden property="defaultbut" />
				<h:hidden property="defaultviewbut" />
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;ҵ��ά������  
					</td>
					<td class="btext">
						<input type='text' name='dimlevel' id="dimlevel" style='width:300' value='����[BUSSENV.BUSSENV.DYSER.BUSSLEVEL.AREA]' readonly >
						<input type='button' value='ѡ��' onClick="selectDimType()">
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;ҵ��ά������
					</td>
					<td class="btext">
						<input type='text' name='dimdata' id="dimdata" style='width:300' value='����[AREA]' readonly>
						<input type='button' value='ѡ��' onClick="selectDimData()">
					</td>
				</tr>
				<tr>
					<td>
						ʱ��ά������
					</td>
					<td>
						<select name="timelevel" >
							<option value="1h">
								ʱ
							</option>
							<option value="1m">
								��
							</option>
							<option value="1d">
								��
							</option>

							<option value="1y">
								��
							</option>
						</select>
					</td>
				</tr>

				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;����
					</td>
					<td class="btext">
						<h:text property="formname" require="true" styleClass="btext"
							value="δ������" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;�ӱ�<a href='tip.html' target='_blank'><font color='red'>[����]</font></a>

					</td>
					<td class="bsel">

						<input id='subform' name='subform' type='text' value='' style='width:400'>

						<a href="javascript:viewthismulti('BUSSFORM','dylist');"><font color='blue'>ѡ��</font></a>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;�б���ʾ
					</td>
					<td class="btext">
						<h:text property="listinfo" size="60" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;��ͼ��ť
					</td>
					<td class="btext">
						<h:textarea property="viewbutinfo" cols="70" rows="2" />
					</td>

				</tr>



				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;����ť
					</td>
					<td class="btext">
						<h:textarea property="butinfo" cols="70" rows="3" />
					</td>
				</tr>

				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;������Ϣ
					</td>
					<td class="btext">
						<h:radio property="orderinfo" label="����" value="1" />
						<h:radio property="orderinfo" label="����" value="0" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;��ʽ��Ϣ
					</td>
					<td class="btext">
						<input type='text' name='styleinfo'
							value='��׼��ʽ[CSSFILE.CSSFILE.DEFAULT]' readonly>
						<input type='button' value='ѡ����ʽ' onClick='listStyle()'>
					</td>
				</tr>
								<tr>
					<td class="tdheaddes" width="100">
						&nbsp;��չ����
					</td>
					<td class="btext">
						<h:textarea property="extendattribute" cols="80" rows="5" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;������
					</td>
					<td class="btext">
						<h:textarea property="description" cols="80" rows="2" />
					</td>
				</tr>

				<tr>
					<script>
				function checkDo(){
					if(document.getElementById('dimdata').value==''){
						alert('ά������δѡ��');
						return;
					}
					if(document.getElementById('dimlevel').value==''){
						alert('ά������δѡ��');
						return;
					}
					document.forms[0].submit();
				}
				</script>
					<td colspan="2" align="center">
						<input type='button' value='����' onClick='checkDo()'>
						<input type="reset" name="Submit2" value="����"
							style="buttonOnTable">
					</td>
				</tr>
			</table>
		</h:form>
	</body>
</html>
