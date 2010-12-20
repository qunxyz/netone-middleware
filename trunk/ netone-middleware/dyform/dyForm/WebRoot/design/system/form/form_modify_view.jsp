<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<h:meta title="�����" />
		<h:css src="/include/css/oe.css" />
		<h:javascript src="/include/js/design/form/form.js" />
		<h:javascript src="/rsinclude/applist/applist.js" />
	</head>

	<body>
		<h:form action="/design/system/form">
			<TABLE width="800" align="left" border="2" cellpadding="5"
				cellspacing="2" bordercolorlight="white" bordercolordark="#FFFFFF">
				<h:hidden property="formcode" />
				<h:hidden property="designer" />
				<h:hidden property="systemid" />
				<h:hidden property="created" />
				<h:hidden property="statusinfo" />
				<h:hidden property="defaultbut" />
				<h:hidden property="defaultviewbut" />
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;ά������
					</td>
					<td class="btext">
						<h:text property='dimlevel' styleClass="btext" readonly="true" />
						<input type='button' value='ѡ��' onClick="selectDimType()">
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;ά����Դ
					</td>
					<td class="btext">
						<h:text property='dimdata' styleClass="btext" readonly="true" />
						<input type='button' value='ѡ��' onClick="selectDimData()">
					</td>
				</tr>
				<tr>
					<td>
						ʱ��ά������
					</td>
					<td>
						<select name="timelevel">
							<option value="1h"
								<c:if test="${formForm.timelevel=='1h'}">selected</c:if>>
								ʱ
							</option>
							<option value="1m"
								<c:if test="${formForm.timelevel=='1m'}">selected</c:if>>
								��
							</option>
							<option value="1d"
								<c:if test="${formForm.timelevel=='1d'}">selected</c:if>>
								��
							</option>

							<option value="1y"
								<c:if test="${formForm.timelevel=='1y'}">selected</c:if>>
								��
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;������
					</td>
					<td class="btext">
						<h:text property="formname" require="true" styleClass="btext" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;�ӱ�<a href='tip.html' target='_blank'><font color='red'>[����]</font></a>

					</td>
					<td class="bsel">

						<h:text property="subform" size="60" />

						<a href="javascript:viewthismulti('BUSSFORM','dylist');"><font
							color='blue'>ѡ��</font> </a>
					</td>
				</tr>

				<tr>
					<td class="tdheaddes">
						&nbsp;�����б�
					</td>
					<td class="btext">
						<h:text property="listinfo" size="50" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;��ͼ��ť
					</td>
					<td class="btext">
						<h:textarea property="viewbutinfo" cols="70" rows="2" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;����ť
					</td>
					<td class="btext">
						<h:textarea property="butinfo" cols="76" rows="3" />
					</td>
				</tr>

				<tr>
					<td class="tdheaddes">
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
						<h:text property="styleinfo" size="50" readonly="true" />
						<input type='button' value='ѡ����ʽ' onClick='listStyle()'>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;����
					</td>
					<td class="btext">
						<h:textarea property="description" cols="80" rows="5"
							readonly="true" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<h:submit value="�޸ı�" action="/design/system/form/modifyope.do"
							target="_self" styleClass="buttonOnTable" />
						&nbsp;
						<input type="reset" name="Submit2" value="����"
							style="buttonOnTable">
					</td>
				</tr>
			</table>
		</h:form>
	</body>
</html>
