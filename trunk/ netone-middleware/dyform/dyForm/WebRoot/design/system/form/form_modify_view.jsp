<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<h:meta title="表单设计" />
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
						&nbsp;维度类型
					</td>
					<td class="btext">
						<h:text property='dimlevel' styleClass="btext" readonly="true" />
						<input type='button' value='选择' onClick="selectDimType()">
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;维度来源
					</td>
					<td class="btext">
						<h:text property='dimdata' styleClass="btext" readonly="true" />
						<input type='button' value='选择' onClick="selectDimData()">
					</td>
				</tr>
				<tr>
					<td>
						时间维度类型
					</td>
					<td>
						<select name="timelevel">
							<option value="1h"
								<c:if test="${formForm.timelevel=='1h'}">selected</c:if>>
								时
							</option>
							<option value="1m"
								<c:if test="${formForm.timelevel=='1m'}">selected</c:if>>
								月
							</option>
							<option value="1d"
								<c:if test="${formForm.timelevel=='1d'}">selected</c:if>>
								日
							</option>

							<option value="1y"
								<c:if test="${formForm.timelevel=='1y'}">selected</c:if>>
								年
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;功能名
					</td>
					<td class="btext">
						<h:text property="formname" require="true" styleClass="btext" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;子表单<a href='tip.html' target='_blank'><font color='red'>[帮助]</font></a>

					</td>
					<td class="bsel">

						<h:text property="subform" size="60" />

						<a href="javascript:viewthismulti('BUSSFORM','dylist');"><font
							color='blue'>选择</font> </a>
					</td>
				</tr>

				<tr>
					<td class="tdheaddes">
						&nbsp;数据列表
					</td>
					<td class="btext">
						<h:text property="listinfo" size="50" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;视图按钮
					</td>
					<td class="btext">
						<h:textarea property="viewbutinfo" cols="70" rows="2" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;管理按钮
					</td>
					<td class="btext">
						<h:textarea property="butinfo" cols="76" rows="3" />
					</td>
				</tr>

				<tr>
					<td class="tdheaddes">
						&nbsp;排序信息
					</td>
					<td class="btext">
						<h:radio property="orderinfo" label="升序" value="1" />
						<h:radio property="orderinfo" label="降序" value="0" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;样式信息
					</td>
					<td class="btext">
						<h:text property="styleinfo" size="50" readonly="true" />
						<input type='button' value='选择样式' onClick='listStyle()'>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;描述
					</td>
					<td class="btext">
						<h:textarea property="description" cols="80" rows="5"
							readonly="true" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<h:submit value="修改表单" action="/design/system/form/modifyope.do"
							target="_self" styleClass="buttonOnTable" />
						&nbsp;
						<input type="reset" name="Submit2" value="重置"
							style="buttonOnTable">
					</td>
				</tr>
			</table>
		</h:form>
	</body>
</html>
