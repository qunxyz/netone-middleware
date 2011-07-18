<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="表单设计" />
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
						&nbsp;业务维度类型  
					</td>
					<td class="btext">
						<input type='text' name='dimlevel' id="dimlevel" style='width:300' value='区域[BUSSENV.BUSSENV.DYSER.BUSSLEVEL.AREA]' readonly >
						<input type='button' value='选择' onClick="selectDimType()">
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;业务维度数据
					</td>
					<td class="btext">
						<input type='text' name='dimdata' id="dimdata" style='width:300' value='区域[AREA]' readonly>
						<input type='button' value='选择' onClick="selectDimData()">
					</td>
				</tr>
				<tr>
					<td>
						时间维度类型
					</td>
					<td>
						<select name="timelevel" >
							<option value="1h">
								时
							</option>
							<option value="1m">
								月
							</option>
							<option value="1d">
								日
							</option>

							<option value="1y">
								年
							</option>
						</select>
					</td>
				</tr>

				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;表单名
					</td>
					<td class="btext">
						<h:text property="formname" require="true" styleClass="btext"
							value="未命名表单" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;子表单<a href='tip.html' target='_blank'><font color='red'>[帮助]</font></a>

					</td>
					<td class="bsel">

						<input id='subform' name='subform' type='text' value='' style='width:400'>

						<a href="javascript:viewthismulti('BUSSFORM','dylist');"><font color='blue'>选择</font></a>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;列表显示
					</td>
					<td class="btext">
						<h:text property="listinfo" size="60" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;视图按钮
					</td>
					<td class="btext">
						<h:textarea property="viewbutinfo" cols="70" rows="2" />
					</td>

				</tr>



				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;管理按钮
					</td>
					<td class="btext">
						<h:textarea property="butinfo" cols="70" rows="3" />
					</td>
				</tr>

				<tr>
					<td class="tdheaddes" width="100">
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
						<input type='text' name='styleinfo'
							value='标准样式[CSSFILE.CSSFILE.DEFAULT]' readonly>
						<input type='button' value='选择样式' onClick='listStyle()'>
					</td>
				</tr>
								<tr>
					<td class="tdheaddes" width="100">
						&nbsp;扩展属性
					</td>
					<td class="btext">
						<h:textarea property="extendattribute" cols="80" rows="5" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes" width="100">
						&nbsp;表单描述
					</td>
					<td class="btext">
						<h:textarea property="description" cols="80" rows="2" />
					</td>
				</tr>

				<tr>
					<script>
				function checkDo(){
					if(document.getElementById('dimdata').value==''){
						alert('维度类型未选择');
						return;
					}
					if(document.getElementById('dimlevel').value==''){
						alert('维度数据未选择');
						return;
					}
					document.forms[0].submit();
				}
				</script>
					<td colspan="2" align="center">
						<input type='button' value='创建' onClick='checkDo()'>
						<input type="reset" name="Submit2" value="重置"
							style="buttonOnTable">
					</td>
				</tr>
			</table>
		</h:form>
	</body>
</html>
